package org.merit.securityjwt.assignment7.controllers;

import java.util.List;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NegativeAmountException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.repos.CDAccountRepository;
import org.merit.securityjwt.assignment7.repos.CDOfferingRepository;
import org.merit.securityjwt.assignment7.servises.MeritBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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