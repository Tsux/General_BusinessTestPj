package org.ektqa.switchbus.util;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.ektqa.webdriver.common.IndividualTestCommon;
import org.openqa.selenium.WebDriver;

public class SwitchTestCommon {
	private WebDriver webDriver;
	IndividualTestCommon individualTestCommon;
	private PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
	
	public SwitchTestCommon(WebDriver webDriver){
		this.webDriver = webDriver;
		this.individualTestCommon = new IndividualTestCommon(webDriver);
	}
	
	public void loginSwitch(String usName, String psw){
		
	}
}
