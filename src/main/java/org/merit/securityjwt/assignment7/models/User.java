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
	private long id;
	private String username;
	private String password;
	boolean active;
	private String role;
	
	public User() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
				+ "\nuser ID: " + this.getId() + " "
				+ "\nuser password: " + this.getPassword() + " "
				+ "\nuser role: " + this.getRole(); 
	}
	
}
