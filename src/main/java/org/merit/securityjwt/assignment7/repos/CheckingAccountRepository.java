package org.merit.securityjwt.assignment7.repos;

import org.merit.securityjwt.assignment7.models.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount,Long> {

	CheckingAccount findById(long id); //placeholder
}
