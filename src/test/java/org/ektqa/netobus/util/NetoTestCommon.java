package org.ektqa.netobus.util;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.ektqa.webdriver.common.IndividualTestCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sun.glass.ui.MenuItem;

public class NetoTestCommon {
	private WebDriver webDriver;
	IndividualTestCommon individualTestCommon;
	private PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
	
	public NetoTestCommon(WebDriver webDriver){
		this.webDriver = webDriver;
		this.individualTestCommon = new IndividualTestCommon(webDriver);
	}
	
	/**
	 * Neto SIAN Portal Login Process
	 * */
	public void loginNetoSIAN(String userName, String password){
		individualTestCommon.clearAndSendKeysByElementId(By.id("usuarioId"), userName);
		individualTestCommon.click(By.id("passId"));
		individualTestCommon.clearAndSendKeysByElementId(By.id("passId"), password);
		individualTestCommon.click(By.cssSelector("button.loginBoton"));
	}
	
	/**
	 * Will navigate to a specific Menu Item and search a option to get in to its content
	 * @param menuItem menu item to navigate to
	 * @param bySubmenu sub menu to navigate to (By type)
	 * @param byOption option to navigate to (By type)
	 * */
	public void NavigateToMenuAndClickOption(MenuItemSIAN menuItem, By bySubmenu, By byOption){
		if(menuItem.getCode().equalsIgnoreCase(MenuItemSIAN.CONTABILIDAD.getCode())){
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.CONTABILIDAD.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.CONTABILIDAD.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.INDICADORES.getCode())){
			individualTestCommon.waitForElementToBeClickable(
				By.id(MenuItemSIAN.INDICADORES.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.INDICADORES.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.REPORTES.getCode())){
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.REPORTES.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.REPORTES.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.CAJA.getCode())){
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.CAJA.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.CAJA.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.INVENTARIO.getCode())){
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.INVENTARIO.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.INVENTARIO.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.PORTAL_RH.getCode())){
			displacementOverMenu(By.id("flechaMenuAdelante"), 1);
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.PORTAL_RH.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.PORTAL_RH.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.MONITOREO.getCode())){
			displacementOverMenu(By.id("flechaMenuAdelante"), 2);
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.MONITOREO.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.MONITOREO.getCode()));
		} else if(menuItem.getCode().equals(MenuItemSIAN.GENERICOS.getCode())){
			displacementOverMenu(By.id("flechaMenuAdelante"), 3);
			individualTestCommon.waitForElementToBeClickable(
					By.id(MenuItemSIAN.GENERICOS.getCode()));
			individualTestCommon.performMouseOver(By.id(MenuItemSIAN.GENERICOS.getCode()));
		}
		
		individualTestCommon.waitForElementToBeVisible(By.id("submenuRhPlantilla"));
		individualTestCommon.performMouseOver(bySubmenu);
		
		if(byOption != null){
			individualTestCommon.waitForElementToBeClickable(byOption).click();
		} else {
			individualTestCommon.clickOnCurrentLocation();
		}
	}
	
	/**
	 * Will navigate across the Menu Bar using the left or right arrow
	 * to apply a displace over the Panel
	 * Possible id Value flechaMenuAdelante [->] and flechaMenuAtras [<-]
	 * @param by defined search attribute (By convention id)
	 * @param times number of times the actions is going to be applied
	 * */
	private void displacementOverMenu(By by, int times){
		for(int i = 0; i <=times; i++){
			if(individualTestCommon.patientlyFindElement(by).isEnabled()){
				individualTestCommon.click(by);
			} else{
				break;
			}
		}
	}
	
	/**
	 * 
	 * */
	public enum MenuItemSIAN{
		CONTABILIDAD("opContabilidad"), INDICADORES("opIndicadores"), 
			REPORTES("opReportes"), CAJA("opCaja"), INVENTARIO("opInventario"),
			PORTAL_RH("opRH"), MONITOREO("opMonitoreo"), GENERICOS("opGenericos");
		
		String menuItem;
		
		MenuItemSIAN(String menuItem) {
			this.menuItem = menuItem;
		}
		
		public String getCode(){
			return this.menuItem;
		}
	}
}