package org.merit.securityjwt.assignment7.repos;

import org.merit.securityjwt.assignment7.models.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
	
	AccountHolder findById(long id); //placeholder
	AccountHolder findByUserId(long userId);
}
