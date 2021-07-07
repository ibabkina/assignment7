package org.merit.securityjwt.assignment7.repos;

import org.merit.securityjwt.assignment7.models.CDOffering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDOfferingRepository extends JpaRepository<CDOffering, Integer> {
	
	CDOffering findById(int id); // placeholder
}