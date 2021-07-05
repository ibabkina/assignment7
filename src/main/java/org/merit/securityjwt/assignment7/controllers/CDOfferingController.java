package org.merit.securityjwt.assignment7.controllers;

import java.util.List;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.models.MeritBank;
import org.merit.securityjwt.assignment7.repos.CDOfferingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CDOfferingController {
	
	@Autowired CDOfferingRepository cdOfferingRepository;
//	@Autowired AccountHolderRepository accHolderRepository;
//	@Autowired CheckingAccountRepository checkingAccountRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String greetMe() {
		return "<html><h2>Welcome to Merit Bank Assignment 6</h2></html>"; 
	}

	@GetMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public List<CDOffering> getCDOfferings() { // throws NotFoundException {
//		CDOffering[] cdOfferings = MeritBank.getCDOfferings();
//		if(cdOfferings == null) { throw new NotFoundException("Offerings Not Found"); }
		return cdOfferingRepository.findAll();
	}
	
	@GetMapping(value = "/cdOfferings/{cdOfferingId}")
	@ResponseStatus(HttpStatus.OK) //Don't need to write this
	public CDOffering getCDOfferingById(@PathVariable int cdOfferingId) 
			throws NotFoundException {
		CDOffering cdOffering = cdOfferingRepository.findById(cdOfferingId);
		if(cdOffering == null) { throw new NotFoundException("Offering Not Found"); }
		return cdOffering;  
	}
	
	@PostMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDOffering(@RequestBody @Valid CDOffering cdOffering) {
		MeritBank.addCDOffering(cdOffering); 
		cdOfferingRepository.save(cdOffering);
		return cdOffering;
	}
}