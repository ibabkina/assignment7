package org.merit.securityjwt.assignment7.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.DataMissingException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.NegativeAmountException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.MeritBank;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.repos.AccountHolderRepository;
import org.merit.securityjwt.assignment7.repos.CDAccountRepository;
import org.merit.securityjwt.assignment7.repos.CDOfferingRepository;
import org.merit.securityjwt.assignment7.repos.CheckingAccountRepository;
import org.merit.securityjwt.assignment7.repos.SavingsAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountHolderController {
	
	private final Logger log = LoggerFactory.getLogger(AccountHolderController.class);
	
	
	@Autowired AccountHolderRepository accHolderRepository;
	@Autowired CheckingAccountRepository checkingAccountRepository;
	@Autowired SavingsAccountRepository savingsAccountRepository;
	@Autowired CDAccountRepository cdAccountRepository;
	@Autowired CDOfferingRepository cdOfferingRepository;
	
	@PostMapping(value = "/accountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) 
			throws DataMissingException {
		
//		accountHolder.setAccountHolderContactDetails(accountHolder.getAccountHolderContactDetails());
//		if(accountHolder.getAccountHolderContactDetails() == null) { 
//			throw new DataMissingException("Account Holder Contact Details are mandatory"); }
		accHolderRepository.save(accountHolder); // id only generates when saved to DB
		long accHolderId = accountHolder.getId();
		accountHolder.getAccountHolderContactDetails().setAccountHolderId(accHolderId);
		MeritBank.addAccountHolder(accountHolder);
		accHolderRepository.save(accountHolder); 
		return accountHolder;
	}
	
	@GetMapping(value = "/accountHolders")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public List<AccountHolder> getAccountHolders(){
		
		return accHolderRepository.findAll();
	}
	
	@GetMapping(value = "/accountHolders/{customerId}")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public AccountHolder getAccountHolderById(@PathVariable long customerId) 
			throws NotFoundException {
		
		AccountHolder accountHolder = this.getAccountHolderById(customerId);
		if(accountHolder == null) { throw new NotFoundException("Account Holder Not Found"); }
		return accountHolder;
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/checkingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addCheckingAcc(@PathVariable long customerId, 
			@RequestBody @Valid CheckingAccount checkingAccount) 
			throws NotFoundException, NegativeAmountException, ExceedsCombinedBalanceLimitException	{
		AccountHolder accountHolder = this.getAccountHolderById(customerId);
		if(checkingAccount.getBalance() < 0) { throw new NegativeAmountException("Balance can't be negative"); }
		log.info(checkingAccount.toString());
		if(accountHolder.getCombinedBalance() + checkingAccount.getBalance() > 250000) {
			log.info("Here in Chk to throw an exception");
			throw new ExceedsCombinedBalanceLimitException("Combined Balance can't exceed $250K");
		}				
		accountHolder.addCheckingAccount(checkingAccount); 
		checkingAccountRepository.save(checkingAccount);
		return checkingAccount;	
	}
	
	@GetMapping(value = "/accountHolders/{customerId}/checkingAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CheckingAccount[] getAccHolderCheckingAccounts(@PathVariable long customerId) 
			throws NotFoundException {
			
		AccountHolder accountHolder = this.getAccountHolderById(customerId);
		CheckingAccount[] checkingAccounts = accountHolder.getCheckingAccounts();
		if(checkingAccounts.length <= 0) { throw new NotFoundException("Checking Accounts Not Found"); }
		return checkingAccounts;
	}
	
	@PostMapping(value = "/accountHolders/{customerId}/savingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSavingsAcc(@PathVariable long customerId, 
			@RequestBody @Valid SavingsAccount savingsAccount) 
			throws NotFoundException, NegativeAmountException, ExceedsCombinedBalanceLimitException {
			
		AccountHolder accountHolder = this.getAccountHolderById(customerId);
		if(savingsAccount.getBalance() < 0) { throw new NegativeAmountException("Balance can't be negative"); }
		if(accountHolder.getCombinedBalance() + savingsAccount.getBalance()> 250000) {
			throw new ExceedsCombinedBalanceLimitException("Combined Balance can't be exceed $250K");
		}
		accountHolder.addSavingsAccount(savingsAccount);
		savingsAccountRepository.save(savingsAccount);
		return savingsAccount;
	}
	
	@GetMapping(value = "/accountHolders/{customerId}/savingsAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public SavingsAccount[] getAccHolderSavingsAccounts(@PathVariable long customerId) 
			throws NotFoundException {
			
		AccountHolder accountHolder = this.getAccountHolderById(customerId);
		SavingsAccount[] savingsAccounts = accountHolder.getSavingsAccounts();
		if(savingsAccounts.length <= 0) { throw new NotFoundException("Savings Accounts Not Found"); }
		return savingsAccounts;
	}
	
//	@PostMapping(value = "/accountHolders/{customerId}/cdAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public CDAccount addCDAccount(@PathVariable long customerId, 
//			@RequestBody @Valid CDAccount cdAccount) 
//			throws NotFoundException, ExceedsFraudSuspicionLimitException {
//			
//		AccountHolder accountHolder = this.getAccountHolderById(customerId);
//		accountHolder.addCDAccount(cdAccount);
//		return cdAccount;
//	}
	
	@PostMapping(value = "/accountHolders/{customerId}/cdAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAcc(@PathVariable long customerId, 
			@RequestBody CDAccount cdAccount) 
			throws NotFoundException, NegativeAmountException, ExceedsCombinedBalanceLimitException,
			ExceedsFraudSuspicionLimitException {
//		cdAccountRepository.save(cdAccount); 
		System.out.println("CD Account = " + cdAccount.toString());
//		cdAccountRepository.save(cdAccount); 
//		System.out.println("CD Account = " + cdAccountRepository.findAll().get(0).toString());
		CDOffering cdOffering = cdOfferingRepository.findById(cdAccount.getCdOffering().getId());
		if(cdOffering == null) { throw new NotFoundException("CD Offering Not Found"); }
//		System.out.println("CD Offering = " + cdOffering.toString());
		AccountHolder accountHolder = accHolderRepository.findById(customerId);
//		cdAccountRepository.save(cdAccount); 
		if(cdAccount.getBalance() < 0) { throw new NegativeAmountException("Balance can't be negative"); }
//		if(accountHolder.getCombinedBalance() + cdAccount.getBalance()> 250000) {
//			throw new ExceedsCombinedBalanceLimitException("Combined Balance can't be exceed $250K");
//		}

		
		cdAccount.setTerm(cdOffering.getTerm());
		cdAccount.setInterestRate(cdOffering.getInterestRate());
		cdAccount.setCdOffering(cdOffering);
		System.out.println("CD Account = " + cdAccount.toString());
//		cdAccountRepository.save(cdAccount); //?
		accountHolder.addCDAccount(cdAccount);
		cdAccountRepository.save(cdAccount);
//		
//		int term = cdAccount.getCdOffering().getTerm();
//		double interestRate = cdAccount.getCdOffering().getInterestRate();
//		cdAccount.setTerm(term);
//		cdAccount.setInterestRate(interestRate);
//		accountHolder.addCDAccount(cdAccount);
		return cdAccount;
	}
		
	@GetMapping(value = "/accountHolders/{customerId}/cdAccounts")
	@ResponseStatus(HttpStatus.OK) 
	public CDAccount[] getAccHolderCDAccounts(@PathVariable long customerId) 
			throws NotFoundException {
			
		AccountHolder accountHolder = this.getAccountHolderById(customerId); 
		CDAccount[] cdAccounts = accountHolder.getCDAccounts();
		if(cdAccounts.length <= 0) { throw new NotFoundException("CD Accounts Not Found"); }
		return cdAccounts;
	}
}
