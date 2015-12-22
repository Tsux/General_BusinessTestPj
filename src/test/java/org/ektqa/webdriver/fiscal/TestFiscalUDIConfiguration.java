package org.ektqa.webdriver.fiscal;

import java.util.List;

import org.ektqa.webdriver.common.WebDriverTestIndividualFis;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class TestFiscalUDIConfiguration extends WebDriverTestIndividualFis{

	@AfterClass
	public void tearDown() throws Exception {
		webDriver.quit();
	}
	
	@Test
	public void selectMexicoAndBAZBus(){
		individualTestCommon.patientlyFindElement(By.id("p1")).click();
		
		individualTestCommon.waitForElementToBeVisible(By.id("j_idt11:2:j_idt13"));
		//individualTestCommon.patientlyFindElement(By.id("j_idt11:0:j_idt13")).click();
		individualTestCommon.patientlyFindElement(By.id("j_idt11:2:j_idt13")).click();
	}
	
	@Test (dependsOnMethods="selectMexicoAndBAZBus")
	public void verifyUDIsAreAvailableForConfiguration() throws Exception{
		individualTestCommon.waitForElementToBeClickable(By.id("btnMenu")).click();
		individualTestCommon.performMouseOver(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/a"));
		individualTestCommon.waitForElementToBeClickable(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/ul/li/a")).click();
		
		individualTestCommon.waitForElementToBeClickable(By.id("j_fechaElegida")).click();
		individualTestCommon.waitForElementToBeClickable(By.xpath("//*[@id=\"ui-monthpicker-div\"]/div[2]/table/tbody/tr[3]/td[3]/a")).click();
		
		Thread.sleep(4000);
	}
	
	/*FIXME: Incomplete Test Flush
	 * It is required to get access to Fiscal's AS400 Database Service
	 *  in order to complete this test flush
	@Test (dependsOnMethods="verifyUDIsAreAvailableForConfiguration")
	public void ConfigureUDIsManually(){
		
	}
	
	@Test (dependsOnGroups="ConfigureUDIsManually")
	public void ConfigureUDIsFromFile(){
		
	}
	
	public void fillUDIsManually(String mes, List<Double> UDIvalues){
		//Logic to get UDIs filled manually
	}*/
}
