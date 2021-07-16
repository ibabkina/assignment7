package org.merit.securityjwt.assignment7.repos;

import java.util.Optional;

import org.merit.securityjwt.assignment7.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
		
//	Optional<User> findByUsername(String userName);	
	User findByUsername(String userName); //placeholder
}

