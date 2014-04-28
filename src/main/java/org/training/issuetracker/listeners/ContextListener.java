package org.training.issuetracker.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

	private static String serverRealPath;

	public static String getServerPath() {
		return serverRealPath;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		serverRealPath = event.getServletContext().getRealPath("/");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}
}
