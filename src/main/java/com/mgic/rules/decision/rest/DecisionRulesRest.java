package com.mgic.rules.decision.rest;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.mgic.rules.model.DecisionResponse;
import com.mgic.rules.model.DecisionRequest;
import com.mgic.rules.services.DecisionRulesService;


@RestController
public class DecisionRulesRest
{

	@Autowired
	private DecisionRulesService decisionRulesService;

	@PostMapping(value = "/decisionRules", produces = { MediaType.APPLICATION_JSON_VALUE })
	public DecisionResponse decisionRules(@RequestBody DecisionRequest decisionRequest)
	{
		DecisionResponse response = decisionRulesService.executeDecisionRules(decisionRequest);
		return response;		
	}
}
