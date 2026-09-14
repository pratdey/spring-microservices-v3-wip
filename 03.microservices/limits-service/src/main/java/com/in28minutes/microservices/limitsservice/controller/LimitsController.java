package com.in28minutes.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.microservices.limitsservice.bean.Limits;
import com.in28minutes.microservices.limitsservice.configuration.Configuration;

@RestController
public class LimitsController {

	//@Autowired
	private Configuration configuration;

/* changing autowired - field injection to constructor injection */

	public LimitsController( Configuration configuration){
		this.configuration= configuration;
	}

	@GetMapping("/limits")
	public Limits retrieveLimits() {
		return new Limits(configuration.getMinimum(), 
				configuration.getMaximum());

		/*   dynamically get the values from configuration service , from application.prop , configuration is autoconfigured here . 
		and assign  this values as a imput to Limits constructor
		  */

		//return new Limits(1,1000);


	}
}
