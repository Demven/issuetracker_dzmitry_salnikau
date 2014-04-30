package org.training.issuetracker.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.training.issuetracker.dao.entities.Resolution;
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
    private MessageSource messageSource;
	
	@Autowired
	private ResolutionService resolutionService;
	
	@Autowired
	private AccessLogic accessLogic;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showResolutions(Model model, Locale locale, HttpServletRequest request) {

		String page = ResolutionsPage.NAME;
		
		if(accessLogic.isHaveAdminAccess(request)){
			model.addAttribute(ResolutionsPage.ATTR_PAGE_TITLE, 
					ResolutionsPage.getMessage(ResolutionsPage.MSG_TTL_RESOLUTIONS, messageSource, locale));
			
			List<Resolution> resolutions = resolutionService.getResolutions();
			
			if(resolutions != null){
				model.addAttribute(ResolutionsPage.ATTR_RESOLUTIONS, resolutions);
			} else{
				model.addAttribute(ResolutionsPage.ATTR_ERROR_MESSAGE, ResolutionsPage.getMessage(ResolutionsPage.MSG_ERR_NO_RESOLUTIONS, messageSource, locale));
			}
		} else{
			// we don't have access to this page
			// tell this to a user and forward him to the main page
			model.addAttribute(MainPage.ATTR_ERROR_MESSAGE, ResolutionsPage.getMessage(ResolutionsPage.MSG_ERR_NO_ACCESS, messageSource, locale));
			page = mainController.showMainPage(model, request, locale);
		}
		
		return page;
	}
}
