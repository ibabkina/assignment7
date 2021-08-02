package org.merit.securityjwt.assignment7.controllers;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.AlreadyExistsException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.models.User;
import org.merit.securityjwt.assignment7.servises.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@PostMapping("/authenticate/createUser")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody @Valid User user) throws MissingDataException, AlreadyExistsException {
		userDetailsService.addUser(user); 
		return user;
	}
	
	@PostMapping("/enrollUser")
	@ResponseStatus(HttpStatus.CREATED)
	public User enrollUser(@RequestBody @Valid User user) throws MissingDataException, AlreadyExistsException {
		userDetailsService.addUser(user); 
		return user;
	}

}
