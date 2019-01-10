package com.brainmentors.los.customer;

public class Customer {
	
	private int id;
	private int stage;
	PersonalInformation personal;
	private Address address; //Has-A
	private double income; 	//Has-A relationship
	private double liability;
	private LoanDetails loanDetails;
	private String remarks;
	
	public int getStage() {
		return stage;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public LoanDetails getLoanDetails() {
		return loanDetails;
	}
	public void setLoanDetails(LoanDetails loanDetails) {
		this.loanDetails = loanDetails;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PersonalInformation getPersonal() {
		return personal;
	}
	public void setPersonal(PersonalInformation personal) {
		this.personal = personal;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getLiability() {
		return liability;
	}
	public void setLiability(double liability) {
		this.liability = liability;
	}

}
