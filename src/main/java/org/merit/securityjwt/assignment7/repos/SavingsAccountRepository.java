package org.merit.securityjwt.assignment7.repos;

import org.merit.securityjwt.assignment7.models.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
	
	SavingsAccount findById(long id); // placeholder
}
