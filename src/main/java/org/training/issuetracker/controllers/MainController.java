package org.training.issuetracker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.issuetracker.dao.hibernate.entities.Issue;
import org.training.issuetracker.dao.hibernate.entities.User;
import org.training.issuetracker.logic.ValidationLogic;
import org.training.issuetracker.managers.CookieManager;
import org.training.issuetracker.managers.SessionManager;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.services.IssueService;
import org.training.issuetracker.services.UserService;

@Controller
public class MainController {
 
	@Autowired
	private IssueService issueService; 
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private SessionManager sessionManager; 
	
	@Autowired
	private CookieManager cookieManager; 
	
	@Autowired
	private ValidationLogic validation; 
	
	@RequestMapping(value = { "/", "/main" })
	public String showMainPage(Model model, HttpServletRequest request) {
		List<Issue> allIssues = issueService.getIssues();
		
		User loginUser = (User) sessionManager.getSessionValue(request, SessionManager.NAME_LOGIN_USER);
		
		if(loginUser != null){
			// User is authorized - show him assigned issues
			ArrayList<Issue> assignedIssues = getAssignedIssues(allIssues, loginUser.getUserId());
			
			if(assignedIssues.size() > 0){
				model.addAttribute(MainPage.ATTR_ASSIGNED_ISSUES, assignedIssues);		
				model.addAttribute(MainPage.ATTR_PAGE_TITLE, "Assigned issues");	
			} else{	
				model.addAttribute(MainPage.ATTR_PAGE_TITLE, "No assigned issues");	
			}
		} else{
			// show the latest issues
			ArrayList<Issue> latestIssues = getLatestIssues(allIssues);
			model.addAttribute(MainPage.ATTR_LATEST_ISSUES, latestIssues);
			model.addAttribute(MainPage.ATTR_PAGE_TITLE, "Latest issues");	
		}
		
		return MainPage.NAME;
	}

	@RequestMapping(value = "/auth", method=RequestMethod.POST)
	public String authorize(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(MainPage.PARAM_EMAIL) String email,
			@RequestParam(MainPage.PARAM_PASSWORD) String password) {
		
		boolean success = false;
		
		if(validation.isEmailValid(email) && validation.isPasswordValid(password)){
			// if input data is valid - search in database
			
			if(userService.checkAuth(email, password)){
				// If such a user is really registered in the system - process authorization
				User loginUser = userService.getUserByEmail(email);
        		
        		// Set this object of the authorized user into the session
				sessionManager.setSessionValue(request,
						SessionManager.NAME_LOGIN_USER, loginUser);
				
				// Set cookie
				cookieManager.setCookieValue(response, CookieManager.NAME_LOGIN, email);
				
				success = true;
			}
		}
		
		String page = MainPage.REDIRECT_NAME;
		if(!success){
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "Not correct login or password! Try again please.");
			page = showMainPage(model, request);
		}
		
		return page;
	}
	
	@RequestMapping(value = "/logout")
	public String logout(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		// Delete loginUser from the session
		sessionManager.removeSessionValue(request, SessionManager.NAME_LOGIN_USER);

		// Delete cookie
		cookieManager.removeCookieValue(response, CookieManager.NAME_LOGIN);
		
		return MainPage.REDIRECT_NAME;
	}
	
	/**
	 * Search in the received list of issues the latest of them and form new list, which then returns.
	 * @param allIssues - list of issues
	 * @return ArrayList<Issue> - list of N latest issues
	 */
	private ArrayList<Issue> getLatestIssues(List<Issue> allIssues){
		ArrayList<Issue> latestIssues = new ArrayList<Issue>();
		
		int number = Issue.MAX_SHOWN_NUMBER;
		if(allIssues.size() < Issue.MAX_SHOWN_NUMBER){
			number = allIssues.size();
		}
		
		for(int i=0; i < number; i++){
			latestIssues.add(allIssues.get(i));
		}
		
		return latestIssues;
	}
	
	/**
	 * Search in the received list of issues of the ones, which are assigned to the user, then returns list of them.
	 * @param allIssues - list of issues
	 * @param userId - unique ID of a user
	 * @return ArrayList<Issue> - list of N assigned issues for the user
	 */
	private ArrayList<Issue> getAssignedIssues(List<Issue> allIssues, int userId){
		ArrayList<Issue> assignedIssues = new ArrayList<Issue>();
		
		int number = 0;
		for(Issue issue: allIssues){
			if(number <= Issue.MAX_SHOWN_NUMBER){
				if(issue.getAssignee() != null && issue.getAssignee().getUserId() == userId){
					assignedIssues.add(issue);
					number++;
				}
			} else{
				break;
			}
		}
		
		return assignedIssues;
	}
}
