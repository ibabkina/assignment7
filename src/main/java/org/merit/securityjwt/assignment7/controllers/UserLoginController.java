package org.merit.securityjwt.assignment7.controllers;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.AlreadyExistsException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.models.AuthenticationRequest;
import org.merit.securityjwt.assignment7.models.AuthenticationResponse;
import org.merit.securityjwt.assignment7.models.User;
import org.merit.securityjwt.assignment7.servises.MyUserDetailsService;
import org.merit.securityjwt.assignment7.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {
	
private final Logger log = LoggerFactory.getLogger(UserLoginController.class);
	
	/*
	 * In order to authenticate I need an authenticationManager so we create a member 
	 * variable of authentication manager. Will be used to authenticate a new userName 
	 * and password of auth token from auhtenticationRequest
	 * 
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping({"/hello" })
	public String hello() { return "<html><h2>Hello. Welcome to Merit Bank Assignment 7</h2></html>"; }

	
	/* Authentication endpoint that does authentication.
	 * Creates authenticate endpoint which is mapped to the createAuthenticationToken 
	 * that takes in an authenticationRequest which is basically the payload in the PostBody
	 * which contains the userName and the password. We use authenticationManager to authenticate 
	 * the userName and password that is passed in.
	 * We cannot call it without authenticating first. Spring Security puts authentication around
	 * everything. Need to go to Security Configurer and spesify/override configure HTTP security method.
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		/*
		 * UsernamePasswordAuthenticationToken is standard token that Spring MVC uses for 
		 * userName and password.
		 * If this authentication fails, the Exception is thrown
		 * If auth is successful, the JWT is returned
		 */
		try {
			log.info("auth username is: " + authenticationRequest.getUsername() + " auth password is: "+ authenticationRequest.getPassword());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		/*
		 * Fetches user details from the userDetailsService to create JWT. 
		 */
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		/*
		 * Now after having userDetails can use JwtUtil to create JWT. 
		 */
		final String jwt =jwtTokenUtil.generateToken(userDetails);
		
		/* 
		 * ResponseEntity is a standard REST API. OK method means that Spring 
		 * MVC is gonna return a 200 ok, and the payload of the response is going 
		 * to be new AuthenticationResponse of JWT
		 */
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	
	}
	
	@PostMapping("/authenticate/createUser")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody @Valid User user) throws MissingDataException, AlreadyExistsException {
		userDetailsService.addUser(user); 
		return user;
	}
}
