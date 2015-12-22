package org.ektqa.webdriver.common;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class WebDriverTestIndivudalNetoSIAN extends WebDriverTest{
	
	public WebDriverTestIndivudalNetoSIAN() {
		super();
	}

	@BeforeClass
	@Parameters( { "selenium-host", "selenium-port", "browser",
		"nesian-app-host", "nesian-user", "nesian-password"})
	public void setUpProperties(String seleniumHostAddress, int seleniumPort,
			String browser, String appHost, String userName, String password)
			throws Exception {
		super.setUpProperties(seleniumHostAddress, seleniumPort, browser, appHost, userName, password);
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		stdout.println("Quitting webdriver from WebDriverTestIndividual class");
		super.tearDown();
	}
}
