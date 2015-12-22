package org.ektqa.webdriver.common;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.ektqa.commons.TestProperties;
import org.ektqa.fiscalbus.util.FiscalTestCommon;
import org.ektqa.netobus.util.NetoTestCommon;
import org.ektqa.switchbus.util.SwitchTestCommon;
import org.ektqa.webdriver.common.IndividualTestCommon;
import org.ektqa.webdriver.common.ThreadSafeWebDriver;

public class WebDriverTest {

	protected WebDriver webDriver;
	protected TestProperties testProperties;
	protected IndividualTestCommon individualTestCommon;
	protected FiscalTestCommon fiscalCommon;
	protected NetoTestCommon netoCommon;
	protected SwitchTestCommon switchCommon;
	
	static PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
	
	public WebDriverTest() { }
	
	public void setUpProperties(String seleniumHostAddress, int seleniumPort,
			String browser, String appHost, String userName, String password)
			throws Exception {

		DesiredCapabilities desiredCapabilities = null;
		if (browser.contains("*firefox")) {
			desiredCapabilities = DesiredCapabilities.firefox();
		}

		else if (browser.equalsIgnoreCase("*chrome")) {
			desiredCapabilities = DesiredCapabilities.chrome();
		}

		else if (browser.equalsIgnoreCase("*iexplore")) {
			desiredCapabilities = DesiredCapabilities.internetExplorer();
		}
		
		stdout.println("http://" + seleniumHostAddress + ":" + seleniumPort + "");
		
		ThreadSafeWebDriver.startWebDriverSession(seleniumHostAddress, appHost, seleniumPort, browser, desiredCapabilities);
		webDriver = ThreadSafeWebDriver.session();
		
		webDriver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		
		testProperties = new TestProperties();
		testProperties.setSeleniumHostAddress(seleniumHostAddress);
		testProperties.setSeleniumPort(seleniumPort);
		testProperties.setBrowserPath(browser);
		testProperties.setAppPath(appHost);
		testProperties.setUserName(userName);
		testProperties.setPassword(password);
		
		individualTestCommon = new IndividualTestCommon(webDriver);
		
		webDriver.manage().window().maximize();
		applyLogin(appHost, userName, password);
	}
	
	public void applyLogin(String appHost, String userName, String password)
			throws Exception{
		if(appHost.contains("Fiscal")){
			fiscalCommon = new FiscalTestCommon(webDriver);
			fiscalCommon.loginFis(webDriver, userName, password);
		} else if(appHost.contains("SIAN")){
			netoCommon = new NetoTestCommon(webDriver);
			netoCommon.loginNetoSIAN(userName, password);
		} else if(appHost.contains("DEX") && appHost.contains("Operacion")){
			
		} else if(appHost.contains("Switch")){
			switchCommon = new SwitchTestCommon(webDriver);
			switchCommon.loginSwitch(userName, password);
		} else{
			stdout.println("No Valid Application Host was provided for statisfactory Login process");
		}
	}
	
	public void tearDown() throws Exception {
		if(webDriver != null){
			webDriver.quit();
		}
	}
}
