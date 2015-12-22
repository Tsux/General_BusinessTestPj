package org.ektqa.fiscalbus.util;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.ektqa.fiscalbus.util.FiscalTestCommon.tipoEjercicio;
import org.ektqa.webdriver.common.IndividualTestCommon;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sun.prism.paint.Stop;

public class FiscalTestCommon {
	private WebDriver webDriver;
	IndividualTestCommon individualTestCommon;
	private PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));

	public FiscalTestCommon(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.individualTestCommon = new IndividualTestCommon(webDriver);
	}
	
	/**
	 * Method will login using test credentials
	 * @param webDriver
	 * @throws Exception
	 */
	public void loginFis(WebDriver webDriver, String userName, String password) throws Exception {

		WebElement userNameElement = individualTestCommon.patientlyFindElement(By.xpath("//input[@id='j_username']"));
		userNameElement.sendKeys(userName);

		WebElement passwordElement = individualTestCommon.patientlyFindElement(By.xpath("//input[@id='j_password']"));
		passwordElement.sendKeys(password);

		individualTestCommon.patientlyFindElement(By.id("j_submit")).click();
		individualTestCommon.patientlyFindElement(By.cssSelector("BODY"));
	}
	
	public void gettingToMainPortalPage(){
		
	}
	
	/**
	 * Method will fill a sheet to generate a new Fiscal Exercise 
	 * for PerdidasFiscalesLISR and PerdidasFiscalesInformativo
	 * exercise year of fiscal exercise
	 * @perdHist amount for historic lost
	 * @ejAmort year for amortization specification
	 * @tipoEjercicio Exercise type
	 * @amortization amount of amortization for item's period
	 * */
	public void fillEjercicioSheet(String exercise, String perdHist, String ejAmort, tipoEjercicio tipoEjercicio, String amortization) throws Exception{
		//It is recommended to define a standard mark off place to perform context action 
		//now it can be used: tablaPerdidas|| tablaPerdidas_head || tablaPerdidas_data
		individualTestCommon.waitForElementToBeVisible(By.id("j_commandButton_buscar"));
		individualTestCommon.performContextClickOnElement(webDriver.findElement(By.id("tablaPerdidas_data")));
		
		if(individualTestCommon.patientlyFindElement(By.xpath("//*[@id=\"idcontextVacio\"]/ul/li/a")).isDisplayed()){
			individualTestCommon.click(By.xpath("//*[@id=\"idcontextVacio\"]/ul/li/a"));
		} else{
			individualTestCommon.click(By.xpath("//*[@id=\"tablaPerdidas_data\"]/tr"));
			individualTestCommon.performContextClickOnElement
				(webDriver.findElement
					(By.xpath("//*[@id=\"tablaPerdidas_data\"]/tr")));
			Thread.sleep(10000);
			individualTestCommon.performMouseOver(By.xpath("//*[@id=\"idcontextPerdidas\"]/ul/li[1]/a/span[contains(.,'Agregar')"));
			individualTestCommon.performMouseOver(By.xpath("//*[@id=\"idcontextPerdidas\"]/ul/li[1]/a/span"));
			individualTestCommon.clickOnCurrentLocation();
			//individualTestCommon.click(By.xpath("(//*[@id=\"idcontextPerdidas\"]/ul/li[1]/a/span[contains(.,'Agregar')])[14]"));
			/*individualTestCommon.fireJavascriptEventForElement(By.xpath("(//*[@id=\"idcontextPerdidas\"]/ul/li[1]/a/span[contains(.,'Agregar')])"), 
					"var medida = argument[0].size;"
					+ "argument[0][medida - 1]");*/
		}

		individualTestCommon.waitForElementToBeClickable(By.id("j_ejercicio_label")).click();
		individualTestCommon.clickElementCollectionByVisibleText(By.xpath("//*[@id=\"j_ejercicio_panel\"]/div/ul"), exercise);
		Thread.sleep(3000);
//		individualTestCommon.isTextPresentInElementValue(By.id("j_ejercicio_label"), exercise);
		individualTestCommon.click(By.id("j_perdida_input"));
		if(individualTestCommon.searchElementAfterPageChange(By.id("j_perdida_input")).isEnabled()){
			webDriver.findElement(By.id("j_perdida_input")).sendKeys(perdHist);
		} else{
			stdout.println("\"Historic Lost\" for this \"Fiscal Exercise\" has already defined.");
			Thread.sleep(10000);
		}
		
		individualTestCommon.click(By.id("j_ejercicioAmort_label"));
		if(individualTestCommon.searchElementAfterPageChange(By.id("j_ejercicioAmort_panel")).isDisplayed()){
			individualTestCommon.clickElementCollectionByVisibleText(By.id("j_ejercicioAmort_panel"), ejAmort);
		} else{
			stdout.println("This is the first \"Historic Lost\" item. It can't be a Amortization period");
			Thread.sleep(10000);
		}
		
		if(tipoEjercicio.getCode().equals(tipoEjercicio.REGULAR.getCode())){
			individualTestCommon.click((By.xpath("//*[@id=\"radioRegularidad\"]/tbody/tr/td[3]/div/div[2]/span")));
		} else{
			individualTestCommon.click(By.xpath("//*[@id=\"radioRegularidad\"]/tbody/tr/td[3]/div/div[2]/span"));
		}
		
		individualTestCommon.click(By.id("j_amortizacion_input"));
		if(webDriver.findElement(By.id("j_amortizacion_input")).isEnabled()){
			individualTestCommon.clearAndSendKeysByElementId("j_amortizacion_input", amortization);
		}else{
			stdout.println("This is the first \"Historic Lost\" item. It can't be a Amortization value");
			Thread.sleep(4000);
		}
		
		individualTestCommon.click(By.id("j_idt236"));
		Assert.assertTrue(individualTestCommon.isTextPresent("Registro guardado"));
	}
	
	public enum tipoEjercicio {
		REGULAR("Regular"), IRREGULAR("Irregular");
		
		String tipo;
		
		tipoEjercicio(String type) {
			this.tipo = type;
		}
		
		public String getCode(){
			return this.tipo;
		}
	}
}
