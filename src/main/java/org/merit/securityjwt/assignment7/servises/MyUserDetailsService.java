package org.merit.securityjwt.assignment7.servises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.merit.securityjwt.assignment7.controllers.AccountHolderController;
import org.merit.securityjwt.assignment7.exceptions.AlreadyExistsException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.User;
import org.merit.securityjwt.assignment7.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jdk.internal.org.jline.utils.Log;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired private UserRepository userRepository; 
	
	private final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		if (userName == "" || userName.isEmpty()) {
			throw new UsernameNotFoundException(String.format("User %s is invalid!", userName));
	    }
	
		User user = getUser(userName);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); 
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);   
    }
	
	public User addUser(User user) throws MissingDataException, AlreadyExistsException {
		if(user == null) { throw new MissingDataException("Some data is missing or in the wrong format"); }
		if(userRepository.findByUsername(user.getUsername()) != null){ throw new AlreadyExistsException("This Username is not available"); }
		userRepository.save(user);
		return userRepository.findByUsername(user.getUsername());	
	}
	
	/**
	 * @return the users 
	 */
	public List<User> getUsers(){ return userRepository.findAll(); }
	
	/**
	 * @return the user
	 */
	public User getUser(String userName) throws UsernameNotFoundException { 
		
		User user = userRepository.findByUsername(userName); 
		if(user == null) { throw new UsernameNotFoundException("User not Found"); }
		return user; 
	}
	
	/**
	 * @return the user
	 */
	public User getUser(Long id) throws EntityNotFoundException { 
		
		User user = userRepository.findById(id).get(); //get() converts from Optional <User> to User
		if(user == null) { throw new EntityNotFoundException("User not Found"); }
		return user; 
	}
}
