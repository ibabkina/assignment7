package org.merit.securityjwt.assignment7.controllers;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.servises.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
	
	private final Logger log = LoggerFactory.getLogger(BankAccountController.class);
	
	@Autowired private BankAccountService bankAccountService;
	
	@PostMapping(value = "/accountHolders/{customerId}/checkingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAcc(@PathVariable long customerId, @RequestBody @Valid CheckingAccount checkingAccount) 
			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
		return bankAccountService.addCheckingAccount(customerId, checkingAccount);
	}
	
	@GetMapping(value = "/accountHolders/{customerId}/checkingAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CheckingAccount[] getCheckingAccounts(@PathVariable long customerId) throws NotFoundException {
		return bankAccountService.getCheckingAccounts(customerId);
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/savingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAcc(@PathVariable long customerId, @RequestBody @Valid SavingsAccount savingsAccount) 
			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
		return bankAccountService.addSavingsAccount(customerId, savingsAccount);
	}
	
	@GetMapping(value = "/accountHolders/{customerId}/savingsAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public SavingsAccount[] getSavingsAccounts(@PathVariable long customerId) throws NotFoundException {
		return bankAccountService.getSavingsAccounts(customerId);
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/cdAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAcc(@PathVariable long customerId, @RequestBody CDAccount cdAccount) 
			throws NotFoundException, ExceedsFraudSuspicionLimitException {
		return bankAccountService.addCDAccount(customerId, cdAccount);
	}	
			
	@GetMapping(value = "/accountHolders/{customerId}/cdAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CDAccount[] getCDAccounts(@PathVariable long customerId) throws NotFoundException {	
		return bankAccountService.getCDAccounts(customerId);
	}

}
