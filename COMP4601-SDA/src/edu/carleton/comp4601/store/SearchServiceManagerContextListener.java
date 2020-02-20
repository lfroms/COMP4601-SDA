package edu.carleton.comp4601.store;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.carleton.comp4601.utility.SearchServiceManager;

public final class SearchServiceManagerContextListener implements ServletContextListener {
	
	@Override
	 public void contextInitialized(ServletContextEvent sce) {
		try {
			SearchServiceManager.getInstance().start();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not connect to SSM.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SearchServiceManager.getInstance().stop();
	}
}
