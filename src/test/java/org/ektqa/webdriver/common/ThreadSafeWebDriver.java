package org.ektqa.webdriver.common;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ThreadSafeWebDriver {
	private static final ThreadLocal<WebDriver> threadLocalSelenium = new ThreadLocal<WebDriver>();

	public static void startWebDriverSession (String seleniumHostAddress, String appHost, int seleniumPort, String browser, DesiredCapabilities desiredCapabilities) throws Exception {
		threadLocalSelenium.set(new RemoteWebDriver(new URL("http://" + seleniumHostAddress + ":" + seleniumPort + "/wd/hub"), desiredCapabilities));
		session().get(appHost);
	}

	public static WebDriver session() {
		return threadLocalSelenium.get();
	}

	public static void resetSession() {
		threadLocalSelenium.set(null);
	}
}
