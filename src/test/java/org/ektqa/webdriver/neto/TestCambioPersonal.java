package org.ektqa.webdriver.neto;

import org.ektqa.netobus.util.NetoTestCommon.MenuItemSIAN;
import org.ektqa.webdriver.common.WebDriverTestIndivudalNetoSIAN;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class TestCambioPersonal extends WebDriverTestIndivudalNetoSIAN{
	
	@AfterClass
	public void tearDown() throws Exception {
		webDriver.quit();
	}
	
	@Test
	public void GoToPageCambioDePersonal(){
		netoCommon.NavigateToMenuAndClickOption(MenuItemSIAN.PORTAL_RH, 
			By.id("submenuRhPlantilla"), 
			By.xpath("(//a[contains(text(),'Cambios de Personal')])[2]"));
	}
	
	@Test (dependsOnMethods="GoToPageCambioDePersonal")
	public void searchUser(){
		individualTestCommon.waitForElementToBeClickable(By.id("rhAlta-selEmpleado")).click();
		//User to be reached and added if there is no instance previously added
		individualTestCommon.clearAndSendKeysByElementId(By.id("rhAlta-selEmpleado"), "157980");
		individualTestCommon.click(By.id("rhAlta-botonBuscar"));
		
		individualTestCommon.waitForElementToBeVisible(By.tagName("body"));
	}
	
	@Test (dependsOnMethods="")
	public void CheckExistanceAndAddUser(){
		if(webDriver.findElement(By.id("dataNotFoundClose")).isDisplayed()){
			individualTestCommon.print("Usuario Registrado. La prueba Terminará en este punto");
		} else{
			
		}
	}
}
