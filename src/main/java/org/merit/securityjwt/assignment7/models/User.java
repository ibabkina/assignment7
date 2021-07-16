package org.merit.securityjwt.assignment7.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users")
//, uniqueConstraints={@UniqueConstraint( name = "username",  columnNames ={"username"}) })
public class User {
	
	@Id  // primary key for DB has to be initialized
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private long userId;
	private String username;
	private String password;
	private String role;
	
	public User() {}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Name: " +  this.getUsername() + " "
				+ "\nuser ID: " + this.getUserId() + " "
				+ "\nuser password: " + this.getPassword() + " "
				+ "\nuser role: " + this.getRole(); 
	}
	
}
