package org.training.issuetracker.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that provides convenient methods to process search
 * @author Dmitry Salnikau
 * @since 11.02.2014
 */
public class SearchLogic {

	private String filter;
	
	public SearchLogic(String filter){
		this.filter = "(" + filter.toLowerCase() + ")";
	}
	
	/**
	 * Checks matching filter-string to your a some String.
	 * @param someString - some String, where you need to find filter-string
	 * @return boolean - true, if filter-string exists in a your someString
	 */
	public boolean isMatchesToFilter(String someString){
		Pattern pt = Pattern.compile(filter);
        Matcher mt = pt.matcher(someString.toLowerCase());
        return mt.find();
	}
}
