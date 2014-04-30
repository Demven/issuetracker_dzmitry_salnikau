package org.training.issuetracker.pages;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class AbstractPage {
	
	public static final String ATTR_PAGE_TITLE = "pageTitle";
	public static final String ATTR_SUCCESS_MESSAGE = "successMessage";
	public static final String ATTR_ERROR_MESSAGE = "errorMessage";
	
	public static String getMessage(String code, MessageSource messageSource, Locale locale){
		return messageSource.getMessage(code, new String[0], locale);
	}
}
