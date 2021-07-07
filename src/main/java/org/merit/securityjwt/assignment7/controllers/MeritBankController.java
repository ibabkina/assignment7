package org.merit.securityjwt.assignment7.controllers;

import java.util.List;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NegativeAmountException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
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
//	@Autowired CDOfferingRepository cdOfferingRepository;
//	@Autowired AccountHolderRepository accHolderRepository;
//	@Autowired CheckingAccountRepository checkingAccountRepository;
//	@Autowired CDAccountRepository cdAccountRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String greetMe() {
		return "<html><h2>Welcome to Merit Bank Assignment 7</h2></html>"; 
	}
	
//@PostMapping(value = "/accountHolders")
//@ResponseStatus(HttpStatus.CREATED)
//public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) 
//		throws DataMissingException {
//	
//	accHolderRepository.save(accountHolder); // id only generates when saved to DB
//	long accHolderId = accountHolder.getId();
//	accountHolder.getAccountHolderContactDetails().setAccountHolderId(accHolderId);
//	MeritBank.addAccountHolder(accountHolder);
//	accHolderRepository.save(accountHolder); 
//	return accountHolder;
//}

	@PostMapping(value = "/accountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) 
			throws MissingDataException {
		
		if(accountHolder == null || accountHolder.getAccountHolderContactDetails() == null) { 
			throw new MissingDataException("Some data is missing or in a wrong format"); 
		}
		else { meritBankService.addAccountHolder(accountHolder); } 
		return accountHolder;
	}

//@GetMapping(value = "/accountHolders")
//@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
//public List<AccountHolder> getAccountHolders(){
//	
//	return accHolderRepository.findAll();
//}

	@GetMapping(value = "/accountHolders")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public List<AccountHolder> getAccountHolders() {
		
		return meritBankService.getAccountHolders();
	}

//@GetMapping(value = "/accountHolders/{customerId}")
//@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
//public AccountHolder getAccountHolderById(@PathVariable long customerId) 
//		throws NotFoundException {
//	
//	AccountHolder accountHolder = accHolderRepository.findById(customerId);
//	if(accountHolder == null) { throw new NotFoundException("Account Holder Not Found"); }
//	return accountHolder;
//}

	@GetMapping(value = "/accountHolders/{customerId}")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccountHolderById(@PathVariable long customerId) 
			throws NotFoundException {
		
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId);
		if(accountHolder == null) { throw new NotFoundException("Account Holder Not Found"); }
		return accountHolder;
	}

//	@GetMapping(value = "/cdOfferings")
//	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
//	public List<CDOffering> getCDOfferings() { // throws NotFoundException {
////		CDOffering[] cdOfferings = MeritBank.getCDOfferings();
////		if(cdOfferings == null) { throw new NotFoundException("Offerings Not Found"); }
//		return cdOfferingRepository.findAll();
//	}
	
	@GetMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public List<CDOffering> getCDOfferings() { // throws NotFoundException {
		return meritBankService.getCDOfferings();
	}
	
	@GetMapping(value = "/cdOfferings/{cdOfferingId}")
	@ResponseStatus(HttpStatus.OK) //Don't need to write this
	public CDOffering getCDOfferingById(@PathVariable int cdOfferingId) 
			throws NotFoundException {
		CDOffering cdOffering = meritBankService.getCDOffering(cdOfferingId);
		if(cdOffering == null) { throw new NotFoundException("Offering Not Found"); }
		return cdOffering;  
	}
	
	@PostMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDOffering(@RequestBody @Valid CDOffering cdOffering) 
			throws MissingDataException {
		if(cdOffering == null) { throw new MissingDataException("Some data is missing or in the wrong format"); } 
		return meritBankService.addCDOffering(cdOffering); 
	}
	
//	@PostMapping(value = "/accountHolders/{customerId}/checkingAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public CheckingAccount addCheckingAcc(@PathVariable long customerId, 
//			@RequestBody @Valid CheckingAccount checkingAccount) 
//			throws NotFoundException, NegativeAmountException, ExceedsCombinedBalanceLimitException	{
//		AccountHolder accountHolder = this.getAccountHolderById(customerId);
//		if(checkingAccount.getBalance() < 0) { throw new NegativeAmountException("Balance can't be negative"); }
//		log.info(checkingAccount.toString());
//		if(accountHolder.getCombinedBalance() + checkingAccount.getBalance() > 250000) {
//			log.info("Combined balance = " + accountHolder.getCombinedBalance() + checkingAccount.getBalance());
//			throw new ExceedsCombinedBalanceLimitException("Combined Balance can't exceed $250K");
//		}				
//		accountHolder.addCheckingAccount(checkingAccount); 
////		checkingAccountRepository.save(checkingAccount);
//		return checkingAccount;	
//	}
	
//	@PostMapping(value = "/accountHolders/{customerId}/checkingAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public CheckingAccount addCheckingAcc(@PathVariable long customerId, 
//			@RequestBody @Valid CheckingAccount checkingAccount) 
//			throws NotFoundException, ExceedsCombinedBalanceLimitException	{
//		AccountHolder accountHolder = this.getAccountHolderById(customerId);			
//		return accountHolder.addCheckingAccount(checkingAccount); 
////		checkingAccountRepository.save(checkingAccount);
////		return checkingAccount;	
////		NegativeAmountException, 
//	}
//	
//	@GetMapping(value = "/accountHolders/{customerId}/checkingAccounts")
//	@ResponseStatus(HttpStatus.OK) 
//	public List<CheckingAccount> getCheckingAccounts(@PathVariable long customerId) 
//			throws NotFoundException {
//			
//		AccountHolder accountHolder = this.getAccountHolderById(customerId);
//		return accountHolder.getCheckingAccounts();
//	}
}