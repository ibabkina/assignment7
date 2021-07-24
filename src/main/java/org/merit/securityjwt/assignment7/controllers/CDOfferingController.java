package org.merit.securityjwt.assignment7.controllers;

import java.util.List;

import javax.validation.Valid;

import org.merit.securityjwt.assignment7.exceptions.MissingDataException;
import org.merit.securityjwt.assignment7.exceptions.NotFoundException;
import org.merit.securityjwt.assignment7.models.CDOffering;
import org.merit.securityjwt.assignment7.servises.MeritBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CDOfferingController {
	
private final Logger log = LoggerFactory.getLogger(CDOfferingController.class);
	
	@Autowired private MeritBankService meritBankService;
	
	@GetMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.OK) //Redundant but can do if your team prefers
	public List<CDOffering> getCDOfferings() {
		return meritBankService.getCDOfferings();
	}
	
	@GetMapping(value = "/cdOfferings/{cdOfferingId}")
	@ResponseStatus(HttpStatus.OK) //Don't need to write this
	public CDOffering getCDOfferingById(@PathVariable int cdOfferingId) throws NotFoundException {
		return meritBankService.getCDOffering(cdOfferingId);
	}
	
	@PostMapping(value = "/cdOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCDOffering(@RequestBody @Valid CDOffering cdOffering) throws MissingDataException {
		return meritBankService.addCDOffering(cdOffering); 
	}
}
