package org.merit.securityjwt.assignment7.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "account_holder_contact_details")
public class AccountHolderContactDetails {
	
	@Id  // primary key for DB
	@GeneratedValue(strategy=GenerationType.AUTO) //Generates automatically and can be omitted
	private long id;
	
//	@NotBlank
	private String street;
	
//	@NotBlank
	private String city;
	
//	@NotBlank
	private String state;
	
//	@NotBlank
	private String zip;
	
//	@NotBlank @Email 
	private String email;
	
//	@NotBlank @Digits(integer=10, fraction=0)
	private String phone;
	
	private long accountHolderId;
	

//	@OneToOne
//	@JoinColumn(name = "account_holder_id", referencedColumnName = "id") 
//	private AccountHolder accountHolder;
	//, nullable = false
//	@JoinColumn is used to configure the name of the column in the 
//	account_holder_contact_details table that maps to the primary key in the 
//	account_holders table. If we don't provide a name, Hibernate will follow some rules to select 
//	a default one. 
//	referencedColumnName is an id in AccountHolder class
//	@JsonIgnore  //used to ignore the logical property used in serialization and deserialization
	

	/**
	 * Default constructor 
	 */
	public AccountHolderContactDetails() { super(); }

	public long getId() { return id; }
	
	public void setId(long id) { this.id = id; }
		
//	public AccountHolderContactDetails setId(long id) {
//		this.id = id;
//		return this;
//	}
	
	public String getStreet() { return street; }

	public void setStreet(String street) { this.street = street; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }

	public String getState() { return state; }

	public void setState(String state) { this.state = state; }

	public String getZip() { return zip; }

	public void setZip(String zip) { this.zip = zip; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

//	public AccountHolderContactDetails setEmail(String email) {
//		this.email = email;
//		return this;
//	}

	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }
	
	public long getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(long accountHolderId) {
		this.accountHolderId = accountHolderId;
	}
	
//	public AccountHolder getAccountHolder() { return accountHolder; }
//	
//	public void setAccountHolder(AccountHolder accountHolder) { this.accountHolder = accountHolder; }

//	public AccountHolderContactDetails setAccountHolder(AccountHolder accountHolder) {
//		this.accountHolder = accountHolder;
//		return this;
//	}
	
	public String toString() {
		return  "Address: " + this.getStreet() + "," + this.getCity() + "," + this.getState() + "," + this.getZip() 
				+ "\nEmail: " + this.getEmail() 
				+ "\nPhone: " + this.getPhone();			
	}
	
}