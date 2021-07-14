package org.merit.securityjwt.assignment7.controllers;

import java.util.List;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.AuthenticationRequest;
import org.merit.securityjwt.assignment7.models.AuthenticationResponse;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.servises.MeritBankService;
import org.merit.securityjwt.assignment7.servises.MyUserDetailsService;
import org.merit.securityjwt.assignment7.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeritBankController {
	
	private final Logger log = LoggerFactory.getLogger(MeritBankController.class);
	
	@Autowired MeritBankService meritBankService;
	
//	------- assignment 7 -----------------
	
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
	
	
//	-----------------------------------------
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public String greetMe() { return "<html><h2>Welcome to Merit Bank Assignment 7</h2></html>"; }

	@PostMapping(value = "/accountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) throws MissingDataException {
		meritBankService.addAccountHolder(accountHolder); 
		return accountHolder;
	}

	@GetMapping(value = "/accountHolders")
	@ResponseStatus(HttpStatus.OK) 
	public List<AccountHolder> getAccountHolders() { 
		return meritBankService.getAccountHolders(); 
	}

	@GetMapping(value = "/accountHolders/{customerId}")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccountHolderById(@PathVariable long customerId) throws NotFoundException {
		return meritBankService.getAccountHolder(customerId);
	}

	@GetMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public List<CDOffering> getCDOfferings() {
		return meritBankService.getCDOfferings();
	}
	
	@GetMapping(value = "/cdOfferings/{cdOfferingId}")
	@ResponseStatus(HttpStatus.OK) //Don't need to write this
	public CDOffering getCDOfferingById(@PathVariable int cdOfferingId) throws NotFoundException {
		return meritBankService.getCDOffering(cdOfferingId);
	}
	
	@PostMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDOffering(@RequestBody @Valid CDOffering cdOffering) throws MissingDataException {
		return meritBankService.addCDOffering(cdOffering); 
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/checkingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAcc(@PathVariable long customerId, @RequestBody @Valid CheckingAccount checkingAccount) 
			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
		return meritBankService.addCheckingAccount(customerId, checkingAccount);
	}
	
	@GetMapping(value = "/accountHolders/{customerId}/checkingAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CheckingAccount[] getCheckingAccounts(@PathVariable long customerId) throws NotFoundException {
		return meritBankService.getCheckingAccounts(customerId);
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/savingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAcc(@PathVariable long customerId, @RequestBody @Valid SavingsAccount savingsAccount) 
			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
		return meritBankService.addSavingsAccount(customerId, savingsAccount);
	}
	
	@GetMapping(value = "/accountHolders/{customerId}/savingsAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public SavingsAccount[] getSavingsAccounts(@PathVariable long customerId) throws NotFoundException {
		return meritBankService.getSavingsAccounts(customerId);
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/cdAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAcc(@PathVariable long customerId, @RequestBody CDAccount cdAccount) 
			throws NotFoundException, ExceedsFraudSuspicionLimitException {
		return meritBankService.addCDAccount(customerId, cdAccount);
	}	
			
	@GetMapping(value = "/accountHolders/{customerId}/cdAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CDAccount[] getCDAccounts(@PathVariable long customerId) throws NotFoundException {	
		return meritBankService.getCDAccounts(customerId);
	}
}