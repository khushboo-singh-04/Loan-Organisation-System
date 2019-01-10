package com.brainmentors.los.customer;

public class PersonalInformation {
		
	private String FirstName;
	private String LasttName;
	private int age;
	private String VoterCard;
	private String PanCard;
	private String Phone;	
	private String Email;

	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLasttName() {
		return LasttName;
	}
	public void setLasttName(String lasttName) {
		LasttName = lasttName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getVoterCard() {
		return VoterCard;
	}
	public void setVoterCard(String voterCard) {
		VoterCard = voterCard;
	}
	public String getPanCard() {
		return PanCard;
	}
	public void setPanCard(String panCard) {
		PanCard = panCard;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}

}
