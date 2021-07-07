package org.merit.securityjwt.assignment7.models;

import java.text.ParseException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.merit.securityjwt.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import org.merit.securityjwt.assignment7.exceptions.ExceedsFraudSuspicionLimitException;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.lang.Exception;

/**
 * This program creates an account holder and adds his accounts. It provides information 
 * about an account holder and their accounts.
 * 
 * @author Irina Babkina 
 */

@Entity
@Table(name = "account_holders")
public class AccountHolder implements Comparable<AccountHolder> {
	
	@Id  // primary key for DB has to be initialized
	@GeneratedValue(strategy=GenerationType.AUTO) //Is generated automatically and can be omitted
	private long id;
	
//	static long nextId = 1;
	
	@NotBlank
	private String firstName;
	
	private String middleName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	@OneToOne(cascade = CascadeType.ALL) //, mappedBy = "accountHolder")
	private AccountHolderContactDetails accountHolderContactDetails;	
/* 
 * mappedBy designates the property/field in the entity that is the owner of the relationship. 
 * There no @JoinColumn annotation here. It's only needed on the owning side of the foreign key
 * relationship. Whoever owns the foreign key column gets the @JoinColumn annotation.
 */
		
	@NotBlank
	private String ssn; 
	private static final double MAX_COMBINED_BALANCE = 250000.00;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
//	, mappedBy="accountHolder" - doesn't work
//	,fetch=FetchType.EAGER - doesn't work
//	,orphanRemoval = true, - doesn't work 
	@OrderColumn 
	private CheckingAccount[] checkingAccounts = new CheckingAccount[0]; 
	
	@OneToMany
	(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
//	(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER, mappedBy="accountHolder")
	@OrderColumn 
	private SavingsAccount[] savingsAccounts = new SavingsAccount[0];
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
//	(mappedBy="accountHolder", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	@OrderColumn 
	private CDAccount[] cdAccounts = new CDAccount[0]; 
	
	private static final double MAX_TRANSACTION_AMOUNT = 1000;

	/**
	 * Default constructor 
	 */
	public AccountHolder() {
		super();
//		this.id = nextId++;
	}
	
	/**
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param ssn
	 */
	public AccountHolder(String firstName, 
							String middleName, 
							String lastName,
							String ssn) { 
		
//		this.id = nextId++;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
	}

	public static AccountHolder readFromString(String accountHolderData) 
			throws ParseException, Exception {
		String[] args = accountHolderData.split(",");
		AccountHolder accHolder = new AccountHolder(args[0], args[1], args[2], args[3]);
		return accHolder;
	}
	
	public long getId() { return id; }

	public void setId(long id) { this.id = id; }

	/**
	 * @return the firstName
	 */
	public String getFirstName() { return firstName; }

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) { 
		this.firstName = firstName; 
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() { return middleName; }

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) { this.middleName = middleName; }

	/**
	 * @return the lastName
	 */
	public String getLastName() { return lastName; }

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) { this.lastName = lastName; }

	/**
	 * @return the ssn
	 */
	public String getSsn() { return ssn; }

	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) { this.ssn = ssn; }
	
	public AccountHolderContactDetails getAccountHolderContactDetails() { return accountHolderContactDetails;}

	public void setAccountHolderContactDetails(AccountHolderContactDetails accountHolderContactDetails) {
		this.accountHolderContactDetails = accountHolderContactDetails;
	}
	
	/**
	 * @param openingBalance
	 * @return the CheckingAccount
	 */
	public CheckingAccount addCheckingAccount(double openingBalance) {
		return addCheckingAccount(new CheckingAccount(openingBalance)); 
	}

	/**
	 * @param checkingAccount
	 * @return the CheckingAccount
	 */
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) {
		try {
			double sum = this.getCheckingBalance() + this.getSavingsBalance(); 
			sum += checkingAccount.getBalance();
			
			if(sum > MAX_COMBINED_BALANCE) {
				throw new ExceedsCombinedBalanceLimitException(
						"You cannot add a checking account at this time. "
						+ "\nPlease wait until combined balance is under 250k!"); 
			}
			
			CheckingAccount[] temp = new CheckingAccount[checkingAccounts.length + 1];
			for (int i = 0 ; i < checkingAccounts.length; i++) {
				temp[i] = checkingAccounts[i];
			}
			temp[temp.length - 1] = checkingAccount;
			checkingAccounts = temp;
			
		}
		catch(ExceedsCombinedBalanceLimitException e){
			System.out.println(e) ;
		}
		return checkingAccount;		
	}		

	/**
	 * @return the CheckingAccount[]
	 */
//	@JsonManagedReference
	public CheckingAccount[] getCheckingAccounts() { return checkingAccounts; }
	
	public void setCheckingAccounts(CheckingAccount[] checkingAccounts) {
		this.checkingAccounts = checkingAccounts;
	}
	
	/**
	 * @param openingBalance
	 * @return the SavingsAccount
	 */
	SavingsAccount addSavingsAccount(double openingBalance) {
		return addSavingsAccount(new SavingsAccount(openingBalance)); 
	}
	
	/**
	 * @param savingsAccount
	 * @return the SavingsAccount
	 */
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) {
		try {
			double sum = this.getCheckingBalance() + this.getSavingsBalance(); 
			sum += savingsAccount.getBalance();
			
			if(sum > MAX_COMBINED_BALANCE) {
				throw new ExceedsCombinedBalanceLimitException(
						"You cannot add a checking account at this time. "
						+ "\nPlease wait until combined balance is under 250k!"); 
			}
			
			SavingsAccount[] temp = new SavingsAccount[savingsAccounts.length + 1];
			for (int i = 0 ; i < savingsAccounts.length; i++) {
				temp[i] = savingsAccounts[i];
			}
			temp[temp.length - 1] = savingsAccount;
			savingsAccounts = temp;
			
		}
		catch(ExceedsCombinedBalanceLimitException e){
			System.out.println(e) ;
		}
		return savingsAccount;			
	}	
	
	/**
	 * @return the SavingsAccount[]
	 */
	public SavingsAccount[] getSavingsAccounts() { return savingsAccounts; }
	
	public void setSavingsAccounts(SavingsAccount[] savingsAccounts) { this.savingsAccounts = savingsAccounts; }
	
	/**
	 * @param openingBalance
	 * @param offering
	 * @return the CDAccount
	 */
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException { 
		if (offering == null) { // || MeritBank.cdOfferings.length <= 0 ) { 
			System.out.println("The CD account could not be created! No CD offerings are available.");
			return null;
			}

		if (openingBalance > MAX_TRANSACTION_AMOUNT) {
			throw new ExceedsFraudSuspicionLimitException("You cannot deposit > $1000 to CD Account"); 
		}
	
		return addCDAccount(new CDAccount(offering, openingBalance)); 
	}
	
	/**
	 * @param cdAccount
	 * @return the cdAccount
	 */
	public CDAccount addCDAccount(CDAccount cdAccount) {
		CDAccount[] temp = new CDAccount[cdAccounts.length + 1];
		for (int i = 0 ; i < cdAccounts.length; i++) {
			temp[i] = cdAccounts[i];
		}
		temp[temp.length - 1] = cdAccount;
		cdAccounts = temp;
		
		return cdAccount;		
	}	
			
	/**
	 * @return the CDAccount[]
	 */
	public CDAccount[] getCDAccounts() { return cdAccounts; }
	
	public void setCDAccounts(CDAccount[] cdAccounts) { this.cdAccounts = cdAccounts; }
	
	/**
	 * @return the number of checking accounts
	 */
	public int getNumberOfCheckingAccounts() { return this.checkingAccounts.length; }
	
	/**
	 * @return the combined balance of checking accounts
	 */
	public double getCheckingBalance() {
		double total = 0;
		if(checkingAccounts != null) {
			for (CheckingAccount a : checkingAccounts) {
				total += a.getBalance();
			}
		}
		return total;
	}
	
	/**
	 * @return the number of savings accounts
	 */
	public int getNumberOfSavingsAccounts() { return this.savingsAccounts.length; }
	
	/**
	 * @return the combined balance of savings accounts
	 */
	public double getSavingsBalance() {
		double total = 0;
		if(savingsAccounts != null) {
			for (SavingsAccount a : savingsAccounts) {
				total += a.getBalance();
			}
		}
		return total; 
	}
	
	/**
	 * @return the number of cd accounts
	 */
	public int getNumberOfCDAccounts() { return this.cdAccounts.length; }
	
	/**
	 * @return the combined balance of cd accounts
	 */
	public double getCDBalance() {
		double total = 0;
		if(cdAccounts != null) {
			for (CDAccount a : cdAccounts) {
				total += a.getBalance();
			}
		}
		return total;
	}
	
	/**
	 * @return the combined balance of checking, savings and cd accounts 
	 */
	public double getCombinedBalance() {
		return this.getCheckingBalance()
				+this.getSavingsBalance()
				+this.getCDBalance();	
	}
	
	public int compareTo(AccountHolder otherAccountHolder) {	
	return Double.compare(this.getCombinedBalance(), otherAccountHolder.getCombinedBalance());	
	}
	
	@Override
	public String toString() {
		return "Name: " +  this.getFirstName() + " " + this.getMiddleName() + " " + this.getLastName()
				+ "\nSSN: " + this.getSsn() + "\n"
				+ this.getAccountHolderContactDetails(); 
	}
	
	public String writeToString() {
		return  this.getId() + "," 
				+ this.getFirstName() + "," 
				+ this.getMiddleName() + "," 
				+ this.getLastName() + "," 
				+ this.getSsn();	
	}
}