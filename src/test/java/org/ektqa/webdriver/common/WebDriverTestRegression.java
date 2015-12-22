package org.ektqa.webdriver.common;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class WebDriverTestRegression extends WebDriverTest{

	protected static PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));

	public WebDriverTestRegression() {
		super();
	}

	@BeforeSuite
	@Parameters( { "selenium-host", "selenium-port", "browser",
		"fis-app-host", "fis-user", "fis-password"})
	public void setUpProperties(String seleniumHostAddress, int seleniumPort,
			String browser, String appHost, String userName, String password)
			throws Exception {
		super.setUpProperties(seleniumHostAddress, seleniumPort, browser, appHost, userName, password);
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		stdout.println("Quitting webdriver from WebDriverTestIndividual class");
		super.tearDown();
	}
}
