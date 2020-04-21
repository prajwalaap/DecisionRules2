package com.mgic.rules.decision.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The Class DecisionRulesApplication.
 */
@SpringBootApplication(scanBasePackages= {"com.mgic.rules"})
public class DecisionRulesApplication {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(DecisionRulesApplication.class, args);
	}
	
	
}
