package org.ektqa.webdriver.fiscal;

import org.ektqa.fiscalbus.util.FiscalTestCommon.tipoEjercicio;
import org.ektqa.webdriver.common.WebDriverTestIndividualFis;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestReportePerdidasFiscalesLISR extends WebDriverTestIndividualFis{
	
	@Test
	public void selectMexicoAndBAZBus(){
		individualTestCommon.patientlyFindElement(By.id("p1")).click();
		
		individualTestCommon.waitForElementToBeVisible(By.id("j_idt11:2:j_idt13"));
		
		individualTestCommon.patientlyFindElement(By.id("j_idt11:2:j_idt13")).click();
	}
	
	@Test (dependsOnMethods="selectMexicoAndBAZBus")
	public void changeNegociofromPanel(){
		individualTestCommon.performMouseOver(By.xpath("//*[@id=\"j_idt112\"]"));
		individualTestCommon.waitForElementToBeVisible(By.id("j_idt118"));
		individualTestCommon.click(By.id("j_idt118"));

		//individualTestCommon.waitForElementToBeVisible(By.xpath("//*[@id=\"j_idt112\"]"));
		Assert.assertTrue(individualTestCommon.isTextPresent("NOTIFICACIONES"));
	}
	
	@Test (dependsOnMethods="changeNegociofromPanel")
	public void accessToPageReportedePerdidasFiscalesLISR() throws Exception{
		Thread.sleep(5000);
		individualTestCommon.performMoveCursorToLocation(-400, -200);
		Thread.sleep(3000);
		//individualTestCommon.click(By.id("btnMenu"));
		individualTestCommon.fireJavascriptEventForElement(By.id("btnMenu"), "arguments[0].click();");
		individualTestCommon.waitForElementToBeVisible(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/a"));
		individualTestCommon.performMouseOver(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/a"));
		
		individualTestCommon.waitForElementToBeVisible(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/ul/li/a"));
		individualTestCommon.performMouseOver(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/ul/li/a"));
		
		individualTestCommon.waitForElementToBeVisible(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/ul/li/ul/li[1]/a"));
		individualTestCommon.performMouseOver(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/ul/li/ul/li[1]/a"));
		
		individualTestCommon.waitForElementToBeClickable(By.xpath("//*[@id=\"MenuBar1\"]/ul/li[2]/ul/li/ul/li[1]/ul/li[1]/a")).click();
	}
	
	@Test (dependsOnMethods="accessToPageReportedePerdidasFiscalesLISR")
	public void performSearchBySociedad(){
		individualTestCommon.performDoubleClickOnElement(individualTestCommon.waitForElementToBeClickable(By.id("j_sociedad")));
		individualTestCommon.waitForElementToBeClickable(By.id("datatablaSoc:globalFilter")).sendKeys("07");
		individualTestCommon.waitForElementToBeClickable(By.xpath("//*[@id=\"datatablaSoc_data\"]/tr[1]/td[2]")).click();
		individualTestCommon.waitForElementToBeClickable(By.id("j_sociedad"));
		Assert.assertTrue(individualTestCommon.isTextPresentInElementValue(webDriver.findElement(By.id("j_sociedad")), "0107"));
		
		webDriver.findElement(By.id("j_commandButton_buscar")).click();
		individualTestCommon.waitForElementToBeVisible(By.id("tablaPerdidas"));
	}
	
	@Test (dependsOnMethods="performSearchBySociedad")
	public void addEjercicios() throws Exception{
		fiscalCommon.fillEjercicioSheet("2006", "14000", "2006", tipoEjercicio.REGULAR, null);
		fiscalCommon.fillEjercicioSheet("2007", "10000", "2006", tipoEjercicio.REGULAR, null);
		fiscalCommon.fillEjercicioSheet("2007", null, "2006", tipoEjercicio.IRREGULAR, "3000");
		fiscalCommon.fillEjercicioSheet("2008", "16000", "2008", tipoEjercicio.REGULAR, null);
		fiscalCommon.fillEjercicioSheet("2008", null, "2008", tipoEjercicio.REGULAR, "7000");
		fiscalCommon.fillEjercicioSheet("2008", null, "2008", tipoEjercicio.IRREGULAR, "5000");
		fiscalCommon.fillEjercicioSheet("2008", "21000", "2008", tipoEjercicio.IRREGULAR, null);
		fiscalCommon.fillEjercicioSheet("2008", null, "2008", tipoEjercicio.REGULAR, "6000");
	}
}
