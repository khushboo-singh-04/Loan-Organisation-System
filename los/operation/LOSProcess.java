package com.brainmentors.los.operation;

import static com.brainmentors.los.utils.Utility.scanner;
import static com.brainmentors.los.utils.Utility.serialCounter;

import java.util.ArrayList;

import com.brainmentors.los.customer.Customer;
import com.brainmentors.los.customer.LoanDetails;
import com.brainmentors.los.customer.PersonalInformation;
import com.brainmentors.los.utils.CommonConstants;
import com.brainmentors.los.utils.LoanConstants;
import com.brainmentors.los.utils.StageConstants;
import com.brainmentors.los.utils.Utility;

public class LOSProcess implements StageConstants,CommonConstants {
	
//	    private Customer customer[]=new Customer[100];
		private ArrayList<Customer> customers=new ArrayList<>();
		
		public void approval(Customer customer) {
			customer.setStage(APPROVAL);
			int score = customer.getLoanDetails().getScore();
			System.out.println("ID "+customer.getId());
			System.out.println("Name "+customer.getPersonal().getFirstName()
					+" "+customer.getPersonal().getLasttName());
			System.out.println("Score is "+customer.getLoanDetails().getScore());
			System.out.println("Loan "+customer.getLoanDetails().getType()
					+" Amount "+customer.getLoanDetails().getAmount()
					+" Duration "+customer.getLoanDetails().getDuration());
			double approveAmount = (customer.getLoanDetails().getAmount()*score)/100;
//			System.out.println("********* "+approveAmount);
			System.out.println("Loan Approve Amount Is "+approveAmount);
			System.out.println("Do you want to bring this Loan or not");
			char choice = scanner.next().toUpperCase().charAt(0);
			if(choice==NO) {
				customer.setStage(REJECT);
				customer.setRemarks("Customer deny the Approved Amount "+ ""+approveAmount);
				return;
			}
			else {
				showEMI(customer);
			}
			
		}
		public void showEMI(Customer customer) {
//			System.out.println("EMI is ");
			if(customer.getLoanDetails().getType().equalsIgnoreCase(LoanConstants.HOME_LOAN)) {
				customer.getLoanDetails().setRoi(LoanConstants.HOME_LOAN_ROI);
			}
			if(customer.getLoanDetails().getType().equalsIgnoreCase(LoanConstants.PERSONAL_LOAN)) {
				customer.getLoanDetails().setRoi(LoanConstants.PERSONAL_LOAN_ROI);

			}
			if(customer.getLoanDetails().getType().equalsIgnoreCase(LoanConstants.AUTO_LOAN)) {
				customer.getLoanDetails().setRoi(LoanConstants.AUTO_LOAN_ROI);

			}
			double perMonthPrinciple = customer.getLoanDetails().getAmount()/customer.getLoanDetails().getDuration();
			double interest = perMonthPrinciple*customer.getLoanDetails().getRoi();
			double totalEMI = perMonthPrinciple + interest;
			System.out.println("Your EMI IS "+totalEMI);
		}
		
		public void qde(Customer customer) {
			
			customer.setStage(QDE);
			System.out.println("Application number " +customer.getId());
			System.out.println("Name " +customer.getPersonal().getFirstName()
					+ " "+customer.getPersonal().getLasttName());
			System.out.println("You applied for "+customer.getLoanDetails().getType()
					+ " Duration " +customer.getLoanDetails().getDuration() 
					+ " Amount "+customer.getLoanDetails().getAmount());
			System.out.println("Enter the pan card number");
			String panCard=scanner.next();
			System.out.println("Enter the email");
			String email=scanner.next();
			System.out.println("Enter the VoterId");
			String VoterId=scanner.next();
			System.out.println("Enter the income");
			Double Income=scanner.nextDouble();
			System.out.println("Enter the Liability");
			Double Liability=scanner.nextDouble();
			System.out.println("Enter the phone number");
			String phone=scanner.next();
			
			customer.getPersonal().setPanCard(panCard);
			customer.getPersonal().setPhone(phone);
			customer.getPersonal().setEmail(email);
			customer.getPersonal().setVoterCard(VoterId);
			customer.setIncome(Income);
			customer.setLiability(Liability);
//			customer.getPersonal().setVoterCard(VoterId);
					
		}
		
		public void scoring(Customer customer) {
			customer.setStage(SCORING);
//			System.out.println("Scoring Call");
			
			int score=0;
			double totalIncome = customer.getIncome() - customer.getLiability();
			if(customer.getPersonal().getAge()>=21 && customer.getPersonal().getAge()<=35) {
				score+=50;
			}
			if(totalIncome>=200000) {
				score+=50;
			}
			customer.getLoanDetails().setScore(score);
		}
		
		
		public void moveToNextStage(Customer customer) {
			
			while(true) {
			
			if(customer.getStage()==SOURCING) {
				System.out.println("Want to Move to the next stage Y/N ");
				char choice = scanner.next().toUpperCase().charAt(0);
				if(choice == YES) {
					qde(customer);
				}
				else {
					return;
				}
			}

			if(customer.getStage()==QDE) {
				System.out.println("Want to Move to the next stage Y/N ");
				char choice = scanner.next().toUpperCase().charAt(0);
				if(choice == YES) {
					dedupe(customer);
				}
				else {
					return;
				}
			}
			if(customer.getStage()==DEDUPE) {
				System.out.println("Dedupe Want to Move to the next stage Y/N ");
				char choice = scanner.next().toUpperCase().charAt(0);
				if(choice == YES) {
					scoring(customer);
				}
				else {
					return;
				}
			}
			
			if(customer.getStage()==SCORING) {
				System.out.println("Scoring Want to Move to the next stage Y/N ");
				char choice = scanner.next().toUpperCase().charAt(0);
				if(choice == YES) {
					approval(customer);
				}
				else {
					return;
				}
			}
			}
			
		}
		
		
		public void dedupe(Customer customer) {
			customer.setStage(DEDUPE);
			System.out.println("Inside dedupe");
			boolean isNegativeFound = false;
			for(Customer negativeCustomer : DB.getNegativeCustomer()) {
				int negativeScore = isNegative(customer, negativeCustomer);
				if(negativeScore>0) {
					System.out.println("Customer Record found in dedupe and Score is : "+negativeScore);
					isNegativeFound = true;
					break;
				}
				if(isNegativeFound) {
					System.out.println("Do u want to proceed this loan "+customer.getId());
					char choice = scanner.next().toUpperCase().charAt(0);
					if(choice==NO) {
						customer.setRemarks("Loan is Rejected, due to high score in Dedupe Check");
						customer.setStage(REJECT);
						return;
					}
				}
			}
		}
		private int isNegative(Customer customer,Customer negative) {
			int percentageMatch =0;
			if(customer.getPersonal().getPhone().equals(negative.getPersonal().getPhone())) {
				percentageMatch+=20;
			}
			if(customer.getPersonal().getEmail().equals(negative.getPersonal().getEmail())) {
				percentageMatch+=20;
			}
			if(customer.getPersonal().getVoterCard().equals(negative.getPersonal().getVoterCard())) {
				percentageMatch+=20;
			}
			if(customer.getPersonal().getPanCard().equals(negative.getPersonal().getPanCard())) {
				percentageMatch+=20;
			}
			if(customer.getPersonal().getAge()==negative.getPersonal().getAge() && 
					customer.getPersonal().getFirstName().equalsIgnoreCase(negative.getPersonal().getFirstName())) {
				percentageMatch+=20;
			}
			
			return percentageMatch;
		}
		
		public void sourcing() {
			
			Customer customer= new Customer();
			customer.setId(serialCounter);
			customer.setStage(SOURCING);
			System.out.println("Enter your name");
			String FirstName=scanner.next();
			System.out.println("enter your last name");
			String LastName=scanner.next();
			System.out.println("enter your age");
			int Age=scanner.nextInt();
			System.out.println("enter your type of loan HL, AL, PL");
			String type=scanner.next();
			System.out.println("enter amount");
			double amount=scanner.nextDouble();
			System.out.println("Duration of loan");
			int duration=scanner.nextInt();
			
			PersonalInformation pd=new PersonalInformation();
			pd.setFirstName(FirstName);
			pd.setLasttName(LastName);
			pd.setAge(Age);
			customer.setPersonal(pd);
			LoanDetails loanDetails=new LoanDetails();
			loanDetails.setType(type);
			loanDetails.setAmount(amount);
			loanDetails.setDuration(duration);
			customer.setLoanDetails(loanDetails);
			
			customers.add(customer);
			serialCounter++;
			System.out.println("Sourcing done");
			
		}
		public void checkStage(int applicationNumber) {
			
			boolean isStageFound=false;
			if (customers!=null && customers.size()>0) {
			for (Customer customer:customers)
				if (customer.getId()==applicationNumber) {
					System.out.println("You are on " +Utility.getStageName(customer.getStage()));
					isStageFound=true;
					moveToNextStage(customer);
					break;
				}
			}
			
			if(!isStageFound) {
				System.out.println("Invalid Appliaction Number");
			}
		}

}
