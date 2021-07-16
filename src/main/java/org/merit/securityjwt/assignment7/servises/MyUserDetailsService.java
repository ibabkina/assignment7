package org.merit.securityjwt.assignment7.servises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.merit.securityjwt.assignment7.models.User;
import org.merit.securityjwt.assignment7.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired private UserRepository userRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		User user = getUser(userName);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); 
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);   
    }
	
	public User addUser(User user) throws MissingDataException {
		if(user == null) { throw new MissingDataException("Some data is missing or in the wrong format"); }
		return userRepository.save(user);
	}
	
	/**
	 * @return the accountHolders 
	 */
	public List<User> getUsers(){ return userRepository.findAll(); }
	
	/**
	 * @return the accountHolder 
	 */
	public User getUser(String userName) throws UsernameNotFoundException { 
		
		User user = userRepository.findByUsername(userName); 
		if(user == null) { throw new UsernameNotFoundException("User not Found"); }
		return user; 
	}
	
}
