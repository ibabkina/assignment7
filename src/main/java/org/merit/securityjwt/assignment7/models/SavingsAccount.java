package org.merit.securityjwt.assignment7.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This program creates a savings account for a client.
 * 
 * @author Irina Babkina 
 * 
 */

@Entity
@Table(name = "savings_accounts")
public class SavingsAccount extends BankAccount {
	
	private static final double INTEREST_RATE = 0.01;
	//	private double interestRate = 0.01;
	
	/**
	 * Default constructor 
	 */
	public SavingsAccount() { super(0, INTEREST_RATE); }
	
	/**
	 * @param openingBalance
	 */
	public SavingsAccount(double openingBalance) {
		super(openingBalance, INTEREST_RATE);
	}
	
	/**
	 * @param openingBalance
	 * @param interestRate
	 */
	public SavingsAccount(double openingBalance, double interestRate){
		super(openingBalance, interestRate);
	}
	
	/**
	 * @param accNumber
	 * @param openingBalance
	 * @param interestRate
	 */
	public SavingsAccount(long accNumber, double openingBalance, double interestRate, java.util.Date accountOpenedOn){
		super(accNumber, openingBalance, interestRate, accountOpenedOn);
	}
	
	// Should throw a java.lang.NumberFormatException if String cannot be correctly parsed
	public static SavingsAccount readFromString(String accountData) 
			throws NumberFormatException, ParseException {
		String[] args = accountData.split(",");
		SavingsAccount acc = new SavingsAccount(Long.parseLong(args[0]), Double.parseDouble(args[1]), 
		Double.parseDouble(args[2]), new SimpleDateFormat("MM/dd/yyyy").parse(args[3]));
		return acc;
	}
	
//	/**
//	 * @return the interestRate
//	 */
//	public double getInterestRate() { return INTEREST_RATE; }
	
//	/**
//	 * Calculates the future value of the account balance based on the interest 
//	 * and number of years
//	 * @param years: number of periods in years
//	 * @return the future value
//	 */
//	double futureValue(int years){ return this.balance * pow(1 + interestRate, years); }
	
	@Override
	public String toString() {
		return "\nSavings Account Number: " + this.getAccountNumber()
			+ "\nSavings Account Balance: $" + String.format("%.2f", this.getBalance())
			+ "\nSavings Account Interest Rate: " + String.format("%.4f", this.getInterestRate())
			+ "\nSavings Account Balance in 3 years: " + String.format("%.2f", this.futureValue(3))
			+ "\nSavings Account Opened Date " + this.getOpenedOn();
	}	
	
	@Override
	public String writeToString() {
		return Long.toString(this.getAccountNumber()) + "," 
				+ String.format("%.0f", this.getBalance()) + ","
				+ String.format("%.2f", this.getInterestRate()) + ","
				+ new SimpleDateFormat("MM/dd/yyyy").format(this.openedOn);	
	}
}