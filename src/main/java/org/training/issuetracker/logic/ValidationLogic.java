package org.training.issuetracker.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for checking user input
 * @author Dmitry Dementor Salnikov
 * @since 19.09.2013
 */
public class ValidationLogic {
	
	/**
	 * Checks the first name is correct
	 * @param firstName
	 * @return boolean isFirstNameValid
	 */
	public boolean isFirstNameValid(String firstName){
		boolean isFirstNameValid = true;

		if(firstName != null && !firstName.equals("")){
			firstName = firstName.trim();
			String regexp_forbidden = "\\!|\\@|\\#|\\№|\\$|\\%|\\ |\\^|\\&|\\*|\\(|\\)|\\-|\\_|\\=|\\+|\\?|\\'|\\;|\\<|\\>|\\,|\\.|\\`|\\~";
			Pattern pattern_forbidden = Pattern.compile(regexp_forbidden);
			Matcher matcher_forbidden = pattern_forbidden.matcher(firstName);
			if(firstName.length() < 2){
				isFirstNameValid = false;
			} else if(firstName.length() > 20){
				isFirstNameValid = false;
			} else if(matcher_forbidden.find()){
				isFirstNameValid = false;
			}
		} else{
			isFirstNameValid = false;
		}
		return isFirstNameValid;
	}
	
	/**
	 * Checks the last name is correct
	 * @param lastName
	 * @return boolean isLastNameValid
	 */
	public boolean isLastNameValid(String lastName){
		boolean isLastNameValid = true;

		if(lastName != null && !lastName.equals("")){
			lastName = lastName.trim();
			String regexp_forbidden = "\\!|\\@|\\#|\\№|\\$|\\%|\\ |\\^|\\&|\\*|\\(|\\)|\\-|\\_|\\=|\\+|\\?|\\'|\\;|\\<|\\>|\\,|\\.|\\`|\\~";
			Pattern pattern_forbidden = Pattern.compile(regexp_forbidden);
			Matcher matcher_forbidden = pattern_forbidden.matcher(lastName);
			if(lastName.length() < 2){
				isLastNameValid = false;
			} else if(lastName.length() > 40){
				isLastNameValid = false;
			} else if(matcher_forbidden.find()){
				isLastNameValid = false;
			}
		} else{
			isLastNameValid = false;
		}
		return isLastNameValid;
	}
	
	/**
	 * Verifies the correctness of email address
	 * @param email
	 * @return boolean
	 */
	public boolean isEmailValid(String email){
		boolean isEmailValid = false;
		email = email.trim().toLowerCase();
		String regexp = "[a-z0-9]+[\\_]?[a-z0-9]*@[a-z0-9]+\\.[a-z0-9]+[\\.[a-z0-9]*]*";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(email);
		if(!email.equals("") && email != null){
			if(matcher.matches()){
				isEmailValid = true;
			}
		}
		return isEmailValid;
	}
	
	/**
	 * Checks the password is correct
	 * @param password
	 * @return boolean
	 */
	public boolean isPasswordValid(String password){
		boolean isPasswordValid = true;
		if(!password.equals("") && password != null){
			password = password.trim().toLowerCase();
			String regexp = "/[a-z0-9]{5,40}/i";
			String regexp_forbidden = "\\#|\\�|\\^|\\&|\\*|\\(|\\)|\\=|\\+|\\'|\\<|\\>|\\`|\\~";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(password);
			Pattern pattern_forbidden = Pattern.compile(regexp_forbidden);
			Matcher matcher_forbidden = pattern_forbidden.matcher(password);
			if(password.length() < 6){
				isPasswordValid = false;
			} else if(password.length() > 30){
				isPasswordValid = false;
			} else if(matcher.matches()){
				isPasswordValid = false;
			} else if(matcher_forbidden.find()){
				isPasswordValid = false;
			}
		} else {
			isPasswordValid = false;
		}
		return isPasswordValid;
	}
}
