package org.training.issuetracker.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.issuetracker.dao.hibernate.entities.Type;
import org.training.issuetracker.services.TypeService;

@Controller
public class TestController {
	
	private static final Logger logger = Logger.getLogger(TestController.class);
	
	public static String PAGE_VIEW_TYPES = "types";
 
	@Autowired
	private TypeService typeService; 
	 
	@RequestMapping(value = "/types")
	public String getTypes(Model model) {  
		System.out.println("!!!!!!!!!!!"); 
		logger.warn("types :)");
		List<Type> types = typeService.getTypes();
		logger.warn("size =" + types.size());
		model.addAttribute("types", types);
		model.addAttribute("pageTitle", "Types");
		
		return PAGE_VIEW_TYPES;
	}
}
