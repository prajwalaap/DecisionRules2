package com.mgic.rules.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mgic.rules.decision.annotations.Fact;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {
	
	@Fact(fieldName = "lenderProgram")
	protected String lenderProgram;
	
	@Fact(fieldName = "loanType")
	protected String loanType;
	
	@Fact(fieldName = "duRecommendation")
	protected String duRecommendation;
	
	@Fact(fieldName = "propertyType")
	protected String propertyType;		
	
	@Fact(fieldName = "loanAmount")
	protected BigDecimal loanAmount;
	
	@Fact(fieldName = "fico")
	protected Integer fico;
	
	@Fact(fieldName = "dti")
	protected BigDecimal dti;
	
	@Fact(fieldName = "ltv")
	protected BigDecimal ltv;
	
	@Fact(fieldName = "productType")
	protected String productType;
	
	@Fact(fieldName = "status")
	protected String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLenderProgram() {
		return lenderProgram;
	}

	public void setLenderProgram(String lenderProgram) {
		this.lenderProgram = lenderProgram;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getDuRecommendation() {
		return duRecommendation;
	}

	public void setDuRecommendation(String duRecommendation) {
		this.duRecommendation = duRecommendation;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getFico() {
		return fico;
	}

	public void setFico(Integer fico) {
		this.fico = fico;
	}

	public BigDecimal getDti() {
		return dti;
	}

	public void setDti(BigDecimal dti) {
		this.dti = dti;
	}

	public BigDecimal getLtv() {
		return ltv;
	}

	public void setLtv(BigDecimal ltv) {
		this.ltv = ltv;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	


	
}