package com.mgic.rules.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DecisionRequest")
public class DecisionRequest {
	
	protected Loan loan = new Loan();

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
}
