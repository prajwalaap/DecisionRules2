package com.mgic.rules.decision.utils;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.definition.type.FactType;

import com.mgic.rules.decision.engine.FactTypeProvider;
import com.mgic.rules.model.DecisionResponse;
import com.mgic.rules.model.DecisionRequest;
import com.mgic.rules.model.Loan;

public class DecisionFactUtils {
	
	 public static final String LOAN_DETAILS = "LoanDetails";	 
	 public static final String DECISION_PACKAGE = "com.myteam.mgic";	 
	 public static final String COM_MGIC_DECISION_LOAN_DETAILS = "com.myteam.mgic.LoanDetails";
	 
	 private static final String LOANAMOUNT = "loanAmount";
	 private static final String LTV = "ltv";
	 private static final String PROPERTY_TYPE = "propertyType";
	 private static final String FICO = "fico";
	 private static final String DTI = "dti";
	
	
	 public static List<Object> createFactsFromDecisionRequest(final DecisionRequest decisionRequest, FactTypeProvider factTypeProvider) {
	        List<Object> facts = new ArrayList<Object>();
   
	        try {
	          facts.add(getLoanFact(factTypeProvider, decisionRequest.getLoan()));          
	        } catch (Exception e) {
	          
	            throw new RuntimeException("Exception at createFactsFromDecisionRulesRequest", e);
	        }
	        return facts;
	    } 


	    private static Object getLoanFact(final FactTypeProvider factTypeProvider, final Loan loan)
	            throws InstantiationException, IllegalAccessException {
	        FactType loanDetailsType = factTypeProvider.getFactType(DECISION_PACKAGE, LOAN_DETAILS);
	        Object loanDetails = loanDetailsType.newInstance();
	        FactUtils.getFactFromObject(loanDetailsType, loanDetails, loan);
/*	        if (null != loan.getLoanAmount()) {
	        	loanDetailsType.set(loanDetails, LOANAMOUNT, loan.getLoanAmount().toUpperCase());
	        }
	        if (null != loan.getLoanAmount()) {
	        	loanDetailsType.set(loanDetails, PROPERTY_TYPE, loan.getPropertyType().toUpperCase());
	        }
	        if (null != loan.getPropertyType()) {
	        	loanDetailsType.set(loanDetails, LTV, loan.getLtv().toUpperCase());
	        }
	        if (null != loan.getLtv()) {
	        	loanDetailsType.set(loanDetails, FICO, loan.getFico().toUpperCase());
	        }
	        if (null != loan.getFico()) {
	        	loanDetailsType.set(loanDetails, DTI, loan.getDti().toUpperCase());
	        }*/
	        
	        if (null == loan.getStatus()) {
	        	loanDetailsType.set(loanDetails, "status", "DECLINED");
	        }
	        if (null == loan.getProductType()) {
	        	loanDetailsType.set(loanDetails, "productType", "Non AUS Product");
	        }
	        
	        
	        return loanDetails;
	    	
	    }
	    
	    private static Loan getLoanDetails(final FactTypeProvider factTypeProvider, final Object fact) {
	        FactType factType = factTypeProvider.getFactType(DECISION_PACKAGE, LOAN_DETAILS);
	        return FactUtils.getObjectFromFact(factType, fact, new Loan());
	    }
	    
	    
		public static DecisionResponse createDecisionResponseFromFacts(final List<Object> outputFacts,
	            final FactTypeProvider factTypeProvider) {
	        DecisionResponse decisionResponse = new DecisionResponse();

	        for (Object fact : outputFacts) {
	            if (fact.getClass().getName().equals(COM_MGIC_DECISION_LOAN_DETAILS)) {
	            	Loan loan = getLoanDetails(factTypeProvider, fact);
	            	decisionResponse.setProductType(loan.getProductType());	            	
	            	decisionResponse.setLoanDecision(loan.getStatus());	            		            	
	            } 

	        }
	        return decisionResponse;
	    }
	    
	    

}
