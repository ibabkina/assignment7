package org.merit.securityjwt.assignment7.controllers;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.servises.BankAccountService;
import org.merit.securityjwt.assignment7.servises.MeritBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

private final Logger log = LoggerFactory.getLogger(MeController.class);
	
	@Autowired private MeritBankService meritBankService;
	@Autowired private BankAccountService bankAccountService;
	
	
	@GetMapping(value = "/Me")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccountHolderById(@RequestHeader("Authorization") String auth) throws NotFoundException {
		return meritBankService.getAccountHolder(auth);
	}

	@PostMapping(value = "/Me/checkingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAccount(@RequestHeader("Authorization") String auth, @RequestBody @Valid CheckingAccount checkingAccount)
			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
		return bankAccountService.addCheckingAccount(auth, checkingAccount);
	}
	
	@GetMapping(value = "Me/checkingAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CheckingAccount[] getCheckingAccounts(@RequestHeader("Authorization") String auth) throws NotFoundException {
		return bankAccountService.getCheckingAccounts(auth);
	}
	
	@PostMapping(value = "/Me/savingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAccount(@RequestHeader("Authorization") String auth, @RequestBody @Valid SavingsAccount savingsAccount)
			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
		return bankAccountService.addSavingsAccount(auth, savingsAccount);
	}
	
	@GetMapping(value = "Me/savingsAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public SavingsAccount[] getSavingsAccounts(@RequestHeader("Authorization") String auth) throws NotFoundException {
		return bankAccountService.getSavingsAccounts(auth);
	}
	
	@PostMapping(value = "/Me/cdAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@RequestHeader("Authorization") String auth, @RequestBody CDAccount cdAccount)
			throws NotFoundException, ExceedsFraudSuspicionLimitException {
		return bankAccountService.addCDAccount(auth, cdAccount);
	}
	
	@GetMapping(value = "Me/cdAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CDAccount[] getCDAccounts(@RequestHeader("Authorization") String auth) throws NotFoundException {
		return bankAccountService.getCDAccounts(auth);
	}	
}
