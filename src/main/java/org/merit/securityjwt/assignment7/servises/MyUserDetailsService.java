package org.merit.securityjwt.assignment7.servises;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.merit.securityjwt.assignment7.models.User;
import org.merit.securityjwt.assignment7.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired 
	private UserRepository userRepository; // make private

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// need to load your user from DB
	
		Optional<User> user = userRepository.findByUsername(userName);

//        if (user.isPresent()){
//            return user.get();
//        }else{
//            throw new UsernameNotFoundException(String.format("Username[%userName] not found"));
//        }
        User u = user.orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        return new SecurityUser(u);
    }
}
