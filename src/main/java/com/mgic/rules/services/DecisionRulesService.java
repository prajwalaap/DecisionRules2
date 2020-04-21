package com.mgic.rules.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgic.rules.decision.engine.DecisionRulesEngine;
import com.mgic.rules.decision.engine.KieStatefulSessionHandler;
//import com.mgic.rules.decision.engine.KieStatefulSessionHandler;
import com.mgic.rules.decision.utils.DecisionFactUtils;
import com.mgic.rules.decision.utils.DecisionConstant;
import com.mgic.rules.model.DecisionResponse;
import com.mgic.rules.model.DecisionRequest;

@Service
public class DecisionRulesService {
		
	@Autowired
    private DecisionRulesEngine rulesEngine;
	
	
	public DecisionResponse executeDecisionRules(DecisionRequest decisionRequest){
		
		 List<Object> inputFacts =
				 DecisionFactUtils.createFactsFromDecisionRequest(decisionRequest, rulesEngine);
		
		 KieStatefulSessionHandler sessionHandler = new KieStatefulSessionHandler(inputFacts);
	       rulesEngine.executeRules(sessionHandler, DecisionConstant.MGIC_PACKAGE);

	        return DecisionFactUtils
	                .createDecisionResponseFromFacts(sessionHandler.getAllSessionObjects(), rulesEngine);
	  	        
	}

}
