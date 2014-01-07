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
		if(email != "" && email != null){
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
		if(password != "" && password != null){
			password = password.trim().toLowerCase();
			String regexp = "/[a-z0-9]{6,30}/i";
			String regexp_depricated = "\\!|\\@|\\#|\\�|\\$|\\%|\\^|\\&|\\*|\\(|\\)|\\-|\\_|\\=|\\+|\\?|\\'|\\;|\\<|\\>|\\,|\\.|\\`|\\~";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(password);
			Pattern pattern_depricated = Pattern.compile(regexp_depricated);
			Matcher matcher_depricated = pattern_depricated.matcher(password);
			if(password.length() < 6){
				isPasswordValid = false;
			} else if(password.length() > 30){
				isPasswordValid = false;
			} else if(matcher.matches()){
				isPasswordValid = false;
			} else if(matcher_depricated.find()){
				isPasswordValid = false;
			}
		} else {
			isPasswordValid = false;
		}
		return isPasswordValid;
	}
}
