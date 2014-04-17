package org.training.issuetracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.TypesPage;
import org.training.issuetracker.services.TypeService;

@Controller
@RequestMapping("/types")
public class TypesController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showStatuses(Model model, HttpServletRequest request) {

		String page = TypesPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(TypesPage.ATTR_PAGE_TITLE, "Statuses");
			
			List<Type> types = typeService.getTypes();
			
			if(types != null){
				model.addAttribute(TypesPage.ATTR_TYPES, types);
			} else{
				model.addAttribute(TypesPage.ATTR_ERROR_MESSAGE, "There is no types or some error occured!");
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
