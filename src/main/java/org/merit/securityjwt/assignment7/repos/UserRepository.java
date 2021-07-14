package org.merit.securityjwt.assignment7.repos;

import org.merit.securityjwt.assignment7.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//	public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
		
		User findById(long userId); //placeholder
}

