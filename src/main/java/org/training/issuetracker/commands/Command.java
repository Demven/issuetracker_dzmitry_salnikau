package org.training.issuetracker.commands;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command { 
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException, IOException; 
}
