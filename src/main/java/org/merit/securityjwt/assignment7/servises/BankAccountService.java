package org.merit.securityjwt.assignment7.servises;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.repos.CDAccountRepository;
import org.merit.securityjwt.assignment7.repos.CheckingAccountRepository;
import org.merit.securityjwt.assignment7.repos.SavingsAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
	
	@Autowired private MeritBankService meritBankService;
	@Autowired private CheckingAccountRepository checkingAccountRepository;
	@Autowired private SavingsAccountRepository savingsAccountRepository;
	@Autowired private CDAccountRepository cdAccountRepository;
	
	private final Logger log = LoggerFactory.getLogger(BankAccountService.class);
	
	public CheckingAccount addCheckingAccount(long customerId, CheckingAccount checkingAccount) 
			 throws NotFoundException, ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId); 
		accountHolder.addCheckingAccount(checkingAccount);
		return checkingAccountRepository.save(checkingAccount);
	}
	
	public CheckingAccount addCheckingAccount(String auth, CheckingAccount checkingAccount) 
			 throws NotFoundException, ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(auth); 
		accountHolder.addCheckingAccount(checkingAccount);
		return checkingAccountRepository.save(checkingAccount);
	}
	
	public CheckingAccount[] getCheckingAccounts(long customerId)
			 throws NotFoundException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId);
		return accountHolder.getCheckingAccounts();
	}
	
	public SavingsAccount addSavingsAccount(long customerId, SavingsAccount savingsAccount) 
			 throws NotFoundException, ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId); 
		accountHolder.addSavingsAccount(savingsAccount);
		return savingsAccountRepository.save(savingsAccount);
	}
	
	public SavingsAccount[] getSavingsAccounts(long customerId)
			 throws NotFoundException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId);
		return accountHolder.getSavingsAccounts();
	}
	
	public CDAccount addCDAccount(long customerId, CDAccount cdAccount) 
			 throws NotFoundException, ExceedsFraudSuspicionLimitException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId); 
		CDOffering cdOffering = meritBankService.getCDOffering(cdAccount.getCdOffering().getId());
		cdAccount.setTerm(cdOffering.getTerm());
		cdAccount.setInterestRate(cdOffering.getInterestRate());
		cdAccount.setCdOffering(cdOffering);	
	//	accountHolder.addCDAccount(cdAccount); //Then it's not checked for suspicionLimit
		accountHolder.addCDAccount(cdOffering, cdAccount.getBalance());
		return cdAccountRepository.save(cdAccount);
	}	
	
	public CDAccount[] getCDAccounts(long customerId)
			 throws NotFoundException {
		AccountHolder accountHolder = meritBankService.getAccountHolder(customerId);
		return accountHolder.getCDAccounts();
	}


}
