package org.training.issuetracker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.hibernate.entities.Resolution;
import org.training.issuetracker.logic.AccessLogic;
import org.training.issuetracker.pages.MainPage;
import org.training.issuetracker.pages.ResolutionsPage;
import org.training.issuetracker.services.ResolutionService;

@Controller
@RequestMapping("/resolutions")
public class ResolutionsController {

	@Autowired
	private MainController mainController;
	
	@Autowired
	private ResolutionService resolutionService;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showResolutions(Model model, HttpServletRequest request) {

		String page = ResolutionsPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(ResolutionsPage.ATTR_PAGE_TITLE, "Resolutions");
			
			List<Resolution> resolutions = resolutionService.getResolutions();
			
			if(resolutions != null){
				model.addAttribute(ResolutionsPage.ATTR_RESOLUTIONS, resolutions);
			} else{
				model.addAttribute(ResolutionsPage.ATTR_ERROR_MESSAGE, "There is no resolutions or some error occured!");
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
