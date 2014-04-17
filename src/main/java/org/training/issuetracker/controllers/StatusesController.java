package org.training.issuetracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.hibernate.entities.Status;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.StatusesPage;
import org.training.issuetracker.services.StatusService;

@Controller
@RequestMapping("/statuses")
public class StatusesController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showStatuses(Model model, HttpServletRequest request) {

		String page = StatusesPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(StatusesPage.ATTR_PAGE_TITLE, "Statuses");
			
			List<Status> statuses = statusService.getStatuses();
			
			if(statuses != null){
				model.addAttribute(StatusesPage.ATTR_STATUSES, statuses);
			} else{
				model.addAttribute(StatusesPage.ATTR_ERROR_MESSAGE, "There is no statuses or some error occured!");
			}
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, "You don't have access to this page!");
			page = mainController.showMainPage(model, request);
		}
		
		return page;
	}
}
