package org.merit.securityjwt.assignment7.servises;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.lang.Exception;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NegativeAmountException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.BankAccount;
import org.merit.securityjwt.assignment7.models.CDAccount;
import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.merit.securityjwt.assignment7.repos.AccountHolderRepository;
import org.merit.securityjwt.assignment7.repos.CDAccountRepository;
import org.merit.securityjwt.assignment7.repos.CDOfferingRepository;
import org.merit.securityjwt.assignment7.repos.CheckingAccountRepository;
import org.merit.securityjwt.assignment7.repos.SavingsAccountRepository;
import org.merit.securityjwt.assignment7.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This program creates account holders for a bank  and adds his accounts. It provides information 
 * about an account holder and their accounts.
 * 
 * @author Irina Babkina 
 */

@Service
public class MeritBankService {
	
	@Autowired AccountHolderRepository accHolderRepository;
	@Autowired CDOfferingRepository cdOfferingRepository;
	@Autowired CheckingAccountRepository checkingAccountRepository;
	@Autowired SavingsAccountRepository savingsAccountRepository;
	@Autowired CDAccountRepository cdAccountRepository;
	
	private static AccountHolder[] accountHolders = new AccountHolder[0]; 
	private static CDOffering[] cdOfferings; 
	private static long nextAccountNumber = 0;
	private static final double MAX_TRANSACTION_AMOUNT = 1000.00;
//	private static FraudQueue fraudQueue = new FraudQueue();

//	static boolean readFromFile(String fileName) {
//		clearAccountHolders();	
//		clearCDOfferings();
//		try {
//			Scanner sc = new Scanner(new File(fileName));
//			while(sc.hasNextLine()) {
//				String line = sc.nextLine();
//				setNextAccountNumber(Integer.parseInt(line));
//				line = sc.nextLine();
//				int numOfCDOfferings = Integer.parseInt(line);
//				cdOfferings = new CDOffering[numOfCDOfferings];
//				for (int i = 0; i < numOfCDOfferings; i++) {
//					if(!sc.hasNextLine()) { break; }
//					line = sc.nextLine();
//					cdOfferings[i] = CDOffering.readFromString(line);
//				}
//				line = sc.nextLine();
//				int numOfAccholders = Integer.parseInt(line);
//				accountHolders = new AccountHolder[0];
//				for (int i = 0; i < numOfAccholders; i++) {
//					line = sc.nextLine();
//					addAccountHolder(AccountHolder.readFromString(line));
//					line = sc.nextLine();
//					int numOfChckAccounts = Integer.parseInt(line);
//					for (int j = 0; j < numOfChckAccounts; j++) {
//						line = sc.nextLine();
////						accountHolders[i].addCheckingAccount(CheckingAccount.readFromString(line)); 
//						CheckingAccount acc = (CheckingAccount.readFromString(line));
//						accountHolders[i].addCheckingAccount(acc); 
//						line = sc.nextLine();
//						int numOfTransactions = Integer.parseInt(line);
//						for (int k = 0; k < numOfTransactions; k++) {
//							line = sc.nextLine();
//							Transaction t = Transaction.readFromString(line);
//							acc.addTransaction(t);
//						}
//					}
//					line = sc.nextLine(); 
//					int numOfSvgsAccounts = Integer.parseInt(line);
//					for (int j = 0; j < numOfSvgsAccounts; j++) {
//						line = sc.nextLine();
//						SavingsAccount acc = (SavingsAccount.readFromString(line));
//						accountHolders[i].addSavingsAccount(acc); 
//						line = sc.nextLine();
//						int numOfTransactions = Integer.parseInt(line);
//						for (int k = 0; k < numOfTransactions; k++) {
//							line = sc.nextLine();
//							Transaction t = Transaction.readFromString(line);
//							acc.addTransaction(t);
//						}		
//					}
//					line = sc.nextLine();
//					
//					int numOfCDAccounts = Integer.parseInt(line);
//					for (int j = 0; j < numOfCDAccounts; j++) {
//						line = sc.nextLine();
//						CDAccount acc = (CDAccount.readFromString(line));
//						accountHolders[i].addCDAccount(acc); 
//						line = sc.nextLine();
//						int numOfTransactions = Integer.parseInt(line);
//						for (int k = 0; k < numOfTransactions; k++) {
//							line = sc.nextLine();
//							Transaction t = Transaction.readFromString(line);
//							acc.addTransaction(t);
//						}				
//					}
//				}
//			
//				for(AccountHolder accH: accountHolders) {
//					for(CheckingAccount acc : accH.getCheckingAccounts()) {
//						for(Transaction t : acc.getTransactions()) {
//							if(t.sourceNum == -1) {
//								t.setSourceAccount(MeritBank.getBankAccount(t.targetNum));
//							}
//							else { 
//								t.setSourceAccount(MeritBank.getBankAccount(t.sourceNum));
//							}
//							t.setTargetAccount(MeritBank.getBankAccount(t.targetNum));
//						}
//					}
//					for(SavingsAccount acc : accH.getSavingsAccounts()) {
//						for(Transaction t : acc.getTransactions()) {
//							if(t.sourceNum == -1) {
//								t.setSourceAccount(MeritBank.getBankAccount(t.targetNum));
//							}
//							else { 
//								t.setSourceAccount(MeritBank.getBankAccount(t.sourceNum));
//							}
//							t.setTargetAccount(MeritBank.getBankAccount(t.targetNum));
//						}
//					}
//					for(CDAccount acc : accH.getCDAccounts()) {
//						for(Transaction t : acc.getTransactions()) {
//							if(t.sourceNum == -1) { t.setSourceAccount(MeritBank.getBankAccount(t.targetNum)); }
//							else { t.setSourceAccount(MeritBank.getBankAccount(t.sourceNum)); }
//							t.setTargetAccount(MeritBank.getBankAccount(t.targetNum));
//						}
//					}
//				}
//				line = sc.nextLine();
//				int numOfFraudTrans = Integer.parseInt(line);
//				for (int i = 0; i < numOfFraudTrans; i++) {
//					line = sc.nextLine();
//					
//					Transaction t = Transaction.readFromString(line);
//					
//					// set source account
//					if(t.sourceNum == -1) { t.setSourceAccount(MeritBank.getBankAccount(t.targetNum)); }
//					else { t.setSourceAccount(MeritBank.getBankAccount(t.sourceNum)); }
//					
//					// set target account
//					t.setTargetAccount(MeritBank.getBankAccount(t.targetNum));
//					
//					fraudQueue.addTransaction(t);	
//				}			
//			}
//		sc.close(); 
//		}
//		catch(IOException e) {
//			System.out.println("IO Issue");
//			return false;
//		}
//		catch(NumberFormatException e) {
//			System.out.println("Number Format Issue");
//			return false;
//		}
//		catch(ParseException e) {
//			System.out.println("Parse Issue");
//			return false;
//		}
//		catch(Exception e) {
//			System.out.println("Some Other Issue when read the file");
//			return false;
//		}
//		return true;
//	}
		
//	static boolean writeToFile(String fileName) {
//		try {
//			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
//			bw.write(String.valueOf(getNextAccountNumber()));
//			bw.newLine();
//			bw.write(String.valueOf(cdOfferings.length));
//			bw.newLine();
//			for(int i = 0; i < cdOfferings.length; i++) {
//				bw.write(cdOfferings[i].writeToString());
//				bw.newLine();
//			}
//			bw.write(String.valueOf(accountHolders.length));
//			bw.newLine();
//			for(int i = 0; i < accountHolders.length; i++) {
//				bw.write(accountHolders[i].writeToString());
//				bw.newLine();
//				bw.write(String.valueOf(accountHolders[i].getNumberOfCheckingAccounts()));
//				bw.newLine();
//				for(int j = 0; j < accountHolders[i].getNumberOfCheckingAccounts(); j++) {
//					bw.write(accountHolders[i].getCheckingAccounts()[j].writeToString());
//					bw.newLine();
//					bw.write(String.valueOf(accountHolders[i].getCheckingAccounts()[j].getTransactions().size()));
//					bw.newLine();
//					for(int k = 0; k < accountHolders[i].getCheckingAccounts()[j].getTransactions().size(); k++) {
//						bw.write(accountHolders[i].getCheckingAccounts()[j].getTransactions().get(k).writeToString());
//						bw.newLine();
//					}
//				}
//				bw.write(String.valueOf(accountHolders[i].getNumberOfSavingsAccounts()));
//				bw.newLine();
//				for(int j = 0; j < accountHolders[i].getNumberOfSavingsAccounts(); j++) {
//					bw.write(accountHolders[i].getSavingsAccounts()[j].writeToString());
//					bw.newLine();
//					bw.write(String.valueOf(accountHolders[i].getSavingsAccounts()[j].getTransactions().size()));
//					bw.newLine();
//					for(int k = 0; k < accountHolders[i].getSavingsAccounts()[j].getTransactions().size(); k++) {
//						bw.write(accountHolders[i].getSavingsAccounts()[j].getTransactions().get(k).writeToString());
//						bw.newLine();
//					}
//				}
//				
//				bw.write(String.valueOf(accountHolders[i].getNumberOfCDAccounts()));
//				bw.newLine();
//				for(int j = 0; j < accountHolders[i].getNumberOfCDAccounts(); j++) {
//					
//					bw.write(accountHolders[i].getCDAccounts()[j].writeToString());
//					bw.newLine();
//					bw.write(String.valueOf(accountHolders[i].getCDAccounts()[j].getTransactions().size()));
//					bw.newLine();
//					for(int k = 0; k < accountHolders[i].getCDAccounts()[j].getTransactions().size(); k++) {
//						bw.write(accountHolders[i].getCDAccounts()[j].getTransactions().get(k).writeToString());
//						bw.newLine();
//					}
//				}
//			}
//			
//			bw.write(String.valueOf(fraudQueue.getFraudQueueSize()));
//			int qSize = fraudQueue.getFraudQueueSize();
//			for(int i = 0; i < qSize; i++) { 
//				bw.newLine();
//				bw.write(fraudQueue.getTransaction().writeToString());	
//			}
//			bw.close();
//		}
//		
//		catch (Exception e) {
//            System.out.println(e);
//            return false;
//        }
//		return true;	
//	}
		
//	/**
//	 * @param accountHolder
//	 */
//	Code before refactoring into a Service class:
//	public static void addAccountHolder(AccountHolder accountHolder) {
//		AccountHolder[] temp = new AccountHolder[accountHolders.length + 1]; 
//		for(int i = 0; i < accountHolders.length; i++) {
//			temp[i] = accountHolders[i];
//		}
//		temp[temp.length - 1] = accountHolder;
//		accountHolders = temp;

	/**
	 * @param accountHolder
	 */
	public void addAccountHolder(AccountHolder accountHolder) 
					throws MissingDataException {
			
		if(accountHolder == null || accountHolder.getAccountHolderContactDetails() == null) { 
				throw new MissingDataException("Some data is missing or in the wrong format"); }
		accHolderRepository.save(accountHolder); // id only generates when saved to DB
		long accHolderId = accountHolder.getId(); // how to handle if .getId() == null? Can it be null or 0?
		accountHolder.getAccountHolderContactDetails().setAccountHolderId(accHolderId);
		accHolderRepository.save(accountHolder); 
		// Should this method return accHolderRepository.findById(id); 
	}
	
//	/**
//	 * @return the accountHolder[] 
//	 */
//	public static AccountHolder[]getAccountHolders() { return accountHolders; }
	
	
	/**
	 * @return the accountHolders 
	 */
	public List<AccountHolder> getAccountHolders(){ return accHolderRepository.findAll(); }
	
	/**
	 * @return the accountHolder 
	 */
	public AccountHolder getAccountHolder(long id) throws NotFoundException { 
		AccountHolder accountHolder = accHolderRepository.findById(id); 
		if(accountHolder == null) { throw new NotFoundException("Account Holder Not Found"); }
		return accountHolder; 
	}
    	
	/**
	 * @param depositAmount
	 * @return the bestOffering
	 */
	public static CDOffering getBestCDOffering(double depositAmount) {
		if(cdOfferings == null) {
			System.out.println("Sorry there are no offerings at this time");
			return null;
		}
		CDOffering best = null;
		for(CDOffering offering : cdOfferings) {
			if(best == null) { best = offering;}
			if (offering.getInterestRate() >= best.getInterestRate()) { best = offering; }
		}
		System.out.println("The Best is : " + best.getInterestRate());
		return best;
	}	

	/**
	 * @param depositAmount
	 * @return the secondBestCDOffering
	 */
	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		CDOffering best = getBestCDOffering(depositAmount);
		if(best == null) { return null; }
		CDOffering secondBest = null;
		for(CDOffering offering : cdOfferings) {
			if(secondBest == null) { secondBest = offering; }
			if (offering.getInterestRate() >= secondBest.getInterestRate() 
				&& offering.getInterestRate() < best.getInterestRate()) {
			secondBest = offering;	
			}
		}	
		System.out.println("Second best is : " + secondBest.getInterestRate());
		return secondBest;
	}
	
	/**
	 * @param id
	 * @return the CDOffering
	 */
	public CDOffering getCDOffering(int id) 
			throws NotFoundException {
		CDOffering cdOffering = cdOfferingRepository.findById(id);
		if(cdOffering == null) { throw new NotFoundException("Offering Not Found"); }
		return cdOffering; 
	 }
	
	/**
	 * @return the cdOfferings
	 */
	public List<CDOffering> getCDOfferings() { return cdOfferingRepository.findAll(); }
	
	/**
	 * @param cdOfferings the cdOfferings to set
	 */
	public static void setCDOfferings(CDOffering[] offerings) { MeritBankService.cdOfferings = offerings; }
		
//	public static CDOffering addCDOffering(CDOffering cdOffering) {
//		if(cdOfferings == null) { cdOfferings = new CDOffering[0]; }
//		CDOffering[] temp = new CDOffering[cdOfferings.length + 1];
//		for (int i = 0 ; i < cdOfferings.length; i++) {
//			temp[i] = cdOfferings[i];
//		}
//		temp[temp.length - 1] = cdOffering;
//		cdOfferings = temp;
//		
//		return cdOffering;
//	}
	
	public CDOffering addCDOffering(CDOffering cdOffering) 
			throws MissingDataException {
		if(cdOffering == null) { throw new MissingDataException("Some data is missing or in the wrong format"); } 
		return cdOfferingRepository.save(cdOffering);
	}
		
//	/**
//	 * @param cdOfferings the cdOfferings to set
//	 */
//	public static void setCDOfferings(List<CDOffering> offerings) { MeritBank.cdOfferings = offerings; }
	
	/**
	 * Removes all offerings
	 */
	public static void clearCDOfferings() { cdOfferings = null; }
	
	/**
	 * Removes all account holders
	 */
	public static void clearAccountHolders() { accountHolders = null; }
	
	/**
	 * @return the nextAccountNumber
	 */
	public static long getNextAccountNumber() {
//		nextAccountNumber++;
//		return nextAccountNumber++; //first returns, then increments
		return ++nextAccountNumber; 
		
	}
	
	/**
	 * @param accountNumber the nextAccountNumber to set
	 */
	public static void setNextAccountNumber(long accountNumber) { nextAccountNumber = accountNumber; }
						
	/**
	 * @return the balance of all accounts
	 */
	public static double totalBalances() {
		double balance = 0.0;
		for(AccountHolder ah : accountHolders) {
			balance += ah.getCombinedBalance();
		}
		return balance;
	}
	
	/**
	 * Calculates the future value of the account balance based on the interest 
	 * and number of years
	 * @param presentValue
	 * @param interestRate
	 * @param term: number of periods in years
	 * @return the future value
	 */
	public static double futureValue(double presentValue, int years, double intRate) {
		
		    if(years == 0) {
		    	return 1;
		    }
		    else if (years == 1) {
		    	return presentValue * (1 + intRate);
		    }
			// recursive call to futureValue()
		    else {
		    	return (1 + intRate) * futureValue(presentValue, years - 1, intRate);
		    }
	
//		return presentValue * Math.pow(1 + interestRate, term); 
	}
	
	/**
	 * Sorts the account holders in ascending order by the combined balance of their accounts 
	 * 
	 * @return the account holders
	 */
	public static AccountHolder[] sortAccountHolders() {
		Arrays.sort(accountHolders);
		return accountHolders;
	}	
	
//	public static boolean processTransaction(Transaction trans) //{
//			throws NegativeAmountException, 
//					ExceedsAvailableBalanceException,
//					ExceedsFraudSuspicionLimitException {
//		
//			if(trans.getAmount() < 0) { 
//				throw new NegativeAmountException("Amount must be > $0.00"); 
//			} 
//			
//			if(trans.getAmount() > MAX_TRANSACTION_AMOUNT) { 
//				getFraudQueue().addTransaction(trans);
//				throw new ExceedsFraudSuspicionLimitException("Amount must be <= $1000.00"); 
//			} 
//			
//			if(trans instanceof WithdrawTransaction) {
//				if(trans.getAmount() > trans.getTargetAccount().getBalance()) {
//					throw new ExceedsAvailableBalanceException("Withdraw amount > Account Balance");
//				}		
//			}
//			
//			if(trans instanceof TransferTransaction) {
//				if(trans.getAmount() > trans.getSourceAccount().getBalance()) {
//					throw new ExceedsAvailableBalanceException("Transfer amount > Source Account Balance");
//				}
//			}	
//
//			trans.process();
//		
//		return true;
//	}
	
//	public static FraudQueue getFraudQueue() {
//		FraudQueue q = new FraudQueue();
//		return q;
//	}
	
	public CheckingAccount addCheckingAccount(long customerId, CheckingAccount checkingAccount) 
			 throws NotFoundException, ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getAccountHolder(customerId); 
		accountHolder.addCheckingAccount(checkingAccount);
		return checkingAccountRepository.save(checkingAccount);
	}
	
	public CheckingAccount[] getCheckingAccounts(long customerId)
			 throws NotFoundException {
		AccountHolder accountHolder = getAccountHolder(customerId);
		return accountHolder.getCheckingAccounts();
	}
	
	public SavingsAccount addSavingsAccount(long customerId, SavingsAccount savingsAccount) 
			 throws NotFoundException, ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getAccountHolder(customerId); 
		accountHolder.addSavingsAccount(savingsAccount);
		return savingsAccountRepository.save(savingsAccount);
	}
	
	public SavingsAccount[] getSavingsAccounts(long customerId)
			 throws NotFoundException {
		AccountHolder accountHolder = getAccountHolder(customerId);
		return accountHolder.getSavingsAccounts();
	}
	
	public CDAccount addCDAccount(long customerId, CDAccount cdAccount) 
			 throws NotFoundException, ExceedsFraudSuspicionLimitException {
		AccountHolder accountHolder = getAccountHolder(customerId); 
		CDOffering cdOffering = getCDOffering(cdAccount.getCdOffering().getId());
		cdAccount.setTerm(cdOffering.getTerm());
		cdAccount.setInterestRate(cdOffering.getInterestRate());
		cdAccount.setCdOffering(cdOffering);	
	//	accountHolder.addCDAccount(cdAccount); //Then it's not checked for suspicionLimit
		accountHolder.addCDAccount(cdOffering, cdAccount.getBalance());
		return cdAccountRepository.save(cdAccount);
	}	
	
	public CDAccount[] getCDAccounts(long customerId)
			 throws NotFoundException {
		AccountHolder accountHolder = getAccountHolder(customerId);
		return accountHolder.getCDAccounts();
	}
	
	public static BankAccount getBankAccount(long accountID) {
		for(AccountHolder accH : accountHolders){
			for(CheckingAccount acc : accH.getCheckingAccounts()){
				if(accountID == acc.getAccountNumber()){ return acc; }
			}
			for(SavingsAccount acc : accH.getSavingsAccounts()){
				if(accountID == acc.getAccountNumber()){ return acc; }
			}
			for(CDAccount acc : accH.getCDAccounts()){
				if(accountID == acc.getAccountNumber()){ return acc; }
			}
		}
		
		return null; 	
	}
}
