package org.ektqa.commons;

import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestProperties {
	String seleniumHostAddress;
	int seleniumPort;
	String browser;
	String browVersion;
	String appPath;
	String driver;
	String url; 
	String userName;
	String password;
	String oSystem;
	Date currentTime;
	
	public String getSelenSrvrAddr() {
		return seleniumHostAddress;
	}
	
	public void setSeleniumHostAddress(String seleniumHostAddress) {
		this.seleniumHostAddress = seleniumHostAddress;
	}
	
	public int getSelenSrvrPort() {
		return seleniumPort;
	}
	
	public void setSeleniumPort(int seleniumPort) {
		this.seleniumPort = seleniumPort;
	}
	
	public String getBrowserPath() {
		return browser;
	}
	
	public void setBrowserPath(String browser) {
		this.browser = browser;
	}
	
	public void determineBrowserVersion(WebDriver driver){
		Capabilities driverCaps = ((RemoteWebDriver) driver).getCapabilities();
		if(driverCaps.getBrowserName().equalsIgnoreCase("internet explorer")){
			String uAgent = (String)((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
			if (uAgent.contains("MSIE") && uAgent.contains("Windows")) {
				setBrowVersion(uAgent.substring(uAgent.indexOf("MSIE")+5, uAgent.indexOf("Windows")-2));
			} else if (uAgent.contains("Trident/7.0")) {
				setBrowVersion("11.0");
			} else {
				setBrowVersion("0.0");
			}
		} else{
			setBrowVersion(driverCaps.getVersion());
		}
	}
	
	public void getStartTetsTime(){
		Calendar calendar = Calendar.getInstance();
		this.setCurrentTime(calendar.getTime());
	}
	
	public String getAppPath() {
		return appPath;
	}
	
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getOperativeSystem(){
		oSystem = "";
		return oSystem;
	}

	public String getBrowVersion() {
		return browVersion;
	}

	public void setBrowVersion(String browVersion) {
		this.browVersion = browVersion;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
}
