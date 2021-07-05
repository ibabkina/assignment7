package org.merit.securityjwt.assignment7.repos;

import org.merit.securityjwt.assignment7.models.CDAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDAccountRepository extends JpaRepository<CDAccount, Long> {

//	CDAccount findById(long id);	// placeholder
}
