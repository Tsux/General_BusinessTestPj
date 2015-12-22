package org.ektqa.webdriver.common;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Common Test Methods. Utilities to apply test on generic components.
 * Several Test Modules may implement features from this specification
 * @author TsuneMaldonado
 * */

public class IndividualTestCommon {
	private WebDriver webDriver;
	private PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));

	public IndividualTestCommon(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	public void loginDEXOperaciones(WebDriver webDriver, String userName, String password){
		webDriver.manage().window().maximize();

		//Login Logic for this portal
	}

	/**
	 * Will try to find the WebElement within 30 seconds.
	 * If the WebElement is not found within 30 seconds, a
	 * TimeoutException will be thrown. Once found thread will 
	 * sleep for milliseconds specified in variable 'timeToWaitAfterElementFound'
	 * to give some time for browser to bind event listeners to the element (if there
	 * happened to be one)
	 * 
	 * @param by The locating mechanism
	 * @return The first matching element on the current page
	 * @throws TimeoutException If no matching elements are found
	 */
	public WebElement patientlyFindElement(final By by) {
		return patientlyFindElement(by, 30);
	}

	/**
	 * Will try to find the WebElement within seconds specified in 'waitFor'.
	 * If the WebElement is not found within 'waitFor' seconds, a
	 * TimeoutException will be thrown. Once found thread will 
	 * sleep for milliseconds specified in variable 'timeToWaitAfterElementFound'
	 * to give some time for browser to bind event listeners to the element (if there
	 * happened to be one)
	 * 
	 * @param by The locating mechanism
	 * @return The first matching element on the current page
	 * @throws TimeoutException If no matching elements are found
	 */
	public WebElement patientlyFindElement(final By by, int waitFor) {
		WebDriverWait wait = new WebDriverWait(webDriver, waitFor);
		return wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver input) {
				print("Waiting for: " + by.toString());
				WebElement element = webDriver.findElement(by);
				print("Found: " + by.toString());
				return element;
			}
		});
	}

	/**
	 * Checking IF an element is visible and enabled so that you
	 * can click it o interact with it.
	 */
	public void waitForElementToBeVisible(By by) {
		WebDriverWait wait = new WebDriverWait(webDriver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public WebElement waitForElementToBeClickable(By by) {
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		return element;
	}

	/**
	 * Checking IF an element is no longer visible on screen
	 * so you can confirm a process continuity or end of status / task
	 * */
	public void waitForElementToBeHidden(By by) {
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * Look for an option on a select element.
	 * @param driverElement located element to interact with
	 * @param selectValue text to find
	 */
	public void waitForSelectOptionsToBePopulated(WebElement driverElement, String selectValue) {
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(driverElement, selectValue));
	}

	/**
	 * Method will return true/false if value is available in element's value attribute
	 * @param driverElement located element to interact with
	 * @param value String to match with
	 * @return true/false
	 */
	public boolean isTextPresentInElementValue(WebElement driverElement, String selectValue){
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		return wait.until(ExpectedConditions.textToBePresentInElementValue(driverElement, selectValue));
	}

	/**
	 * Method will return true/false if value is available in element's value attribute
	 * @param by locator type to get element
	 * @param value string to match with
	 * @return true/false
	 */
	public boolean isTextPresentInElementValue(By by, String value) {
		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		return wait.until(ExpectedConditions.textToBePresentInElementValue(by, value));
	}

	/**
	 * finding out if text is present on current web page
	 * @param text
	 * @return boolean
	 */
	public boolean isTextPresent(String text) {
		WebElement bodyTag = patientlyFindElement(By.tagName("body"));
		if(bodyTag.getText().contains(text)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * finding out if link is present on current web page
	 * @param textLink
	 * @return boolean
	 */
	 public boolean isLinkPresent(String textLink){
		boolean linkPresent = false;	
		if(webDriver.findElement(By.linkText(textLink)).isDisplayed()) {
			linkPresent= true;
		}
		return linkPresent;
	}

	/**
	 * Check for Element Existence in the page
	 * @param by
	 * @return
	 */
	public boolean isElementPresent(By by) {
		boolean elementPresent = false;
		if(webDriver.findElements(by).size() != 0) {
			elementPresent = true;
		} 
		return elementPresent;
	}

	/**
	 * This method will return the class for the element
	 * @param elementXPath
	 * @return
	 */
	public String getElementClassByXPath(String elementXPath) {
		return patientlyFindElement(By.xpath(elementXPath)).getAttribute("class");
	}

	/**
	 * Clears and sets value of an specific web Element
	 * @param by
	 * @param value
	 */
	public void clearAndSendKeysByElementId(By by, String value) {
		patientlyFindElement(by).clear();
		patientlyFindElement(by).sendKeys(value);
	}

	/**
	 * Clears and sets value by elementId
	 * @param elementId
	 * @param value
	 */
	public void clearAndSendKeysByElementId(String elementId, String value) {
		patientlyFindElement(By.id(elementId)).clear();
		
		patientlyFindElement(By.id(elementId)).sendKeys(value);
	}

	/**
	 * Clears and sets value by elementName
	 * @param elementName
	 * @param value
	 */
	public void clearAndSendKeysByElementName(String elementName, String value) {
		patientlyFindElement(By.name(elementName)).clear();
		patientlyFindElement(By.name(elementName)).sendKeys(value);
	}

	/**
	 * Will search and select a value in a ComboBox component
	 * @param by gets the web component via specific identifier
	 * @param value specified search argument
	 * */	
	public void selectValueInComboBox(final By by, final String value) {
		WebElement select = patientlyFindElement(by);
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if(value.equals(option.getText())){
				option.click();
				}
			}
	}
	
	/**
	 * Will apply a Mouse Over action over a specific Element
	 * @param webElement specify the element
	 * */
	public void performMouseOver(By by){
		Actions action = new Actions(webDriver);
		action.moveToElement(patientlyFindElement(by)).build().perform();
	}
	
	/**
	 * Will move the cursor position to coordinates referenced  
	 * from current position to displacement specified by:
	 * @param Xcoord : X axis displacement (negative value will move DOWN)
	 * @param Ycoord : Y axis displacement (negative value will move UP)
	 * */
	public void performMoveCursorToLocation(int Xcoord, int Ycoord){
		Actions action = new Actions(webDriver);
		action.moveByOffset(Xcoord, Ycoord).build().perform();
	}
	
	/**
	 * Will click on current mouse location.
	 * NOTE: be sure you get the cursor position in the place you need,
	 * it is recommended to use the performMoveCursorToLocation method before this action
	 * */
	public void clickOnCurrentLocation(){
		Actions action = new Actions(webDriver);
		action.click().build().perform();
	}
	
	/**
	 * Will apply a Double Click action over a specific Element
	 * @param webElement specify the element
	 * */
	public void performDoubleClickOnElement(WebElement webElement){
		Actions action = new Actions(webDriver);
		action.doubleClick(webElement).perform();
	}
	
	/**
	 * Will apply a Context Click action over a specific Element
	 * @param webElement specify the element
	 * */
	public void performContextClickOnElement(WebElement webElement){
		Actions action = new Actions(webDriver);
		action.contextClick(webElement).perform();
	}

	/**
	 * Will search and select a element value in a DropDown menu
	 * @param by gets the web component via specific attribute or identifier
	 * @param value specified search argument
	 * */
	public void selectValueInDropDown(final By by, final String value) {
		Select selectElement = new Select(patientlyFindElement(By.xpath("//select[@name='"+ by +"']")));
		selectElement.selectByVisibleText(value);
	}
	
	/**
	 * Will select a li element inside a panel containing specific value provided by the user.  
	 * Useful for panels that emulates and replaces the a Combo Box widget behavior.
	 * @param  by : webElement parent of the list in which the search will be performed
	 * @param value : containing value of the li element that method will search
	 * */
	public void clickElementCollectionByVisibleText(By by, final String value){
		stdout.println("Searching for element inside panel: "+".//li[contains(.,'"+value+"')]");
		WebElement webElement = patientlyFindElement(by);
		webElement.findElement(By.xpath(".//li[contains(.,'"+value+"')]")).click();
		/*for(WebElement element: patientlyFindElement(by).findElements(By.tagName("li"))){
			if(element.getAttribute("value").equals(value)){
				element.click();
			}
		}*/
	}
	
	/**
	 * Will search an element after a change applied on the page
	 * as a result of an Asynchronous Custom Action
	 * @param by : attribute used to get the web Element
	 * */
	public WebElement searchElementAfterPageChange(By by){
		WebDriverWait wait = new WebDriverWait(webDriver, 15);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
		
	}

	/**
	 * Return the list of options available for the 'list box' component via specific attribute or identifier
	 * @param by
	 * @return options list
	 */
	public List<String> getSelectOptionsById(By by) {
		WebElement select = patientlyFindElement(by);
		//Verify if this case applies for all test cases in different Portal Implementations
	    List<WebElement> options = select.findElements(By.tagName("option"));
	    List<String> selectOptions = new ArrayList<String>();
	    for (WebElement option : options) {
	    	selectOptions.add(option.getText());
	    }
	   return selectOptions;
	}

	/**
	 * types a date to fill up Calendar Format Fields
	 * @param by gets the web component via specific attribute or identifier
	 * */	
	public void selectDateForCalendarFields(By by){
	     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar fromCal = Calendar.getInstance();
		 fromCal.add(Calendar.DATE, 1);
		 patientlyFindElement(by).sendKeys(dateFormat.format(fromCal.getTime()));
	}

	/**
	 * Useful method to apply Filter selection over auto complete fields.
	 * Based on the initial values keyed in, list of choice options are displayed 
	 *  exactName will be selected from the options displayed.
	 * @param getFieldBy specific attribute or identifier to get the filtered values
	 * @param initialCharacters filter pattern
	 * @param exactValue target value
	 * */
	public void performAutoComplete(By getFieldBy, String initialCharacters, String exactValue) {
		patientlyFindElement(getFieldBy).clear();
		patientlyFindElement(getFieldBy).sendKeys(initialCharacters);
		waitForProcessToComplete();
		// Following process will probably need adequacy 
		// depending on the way auto complete fields are being implemented on EKT webPortals
		patientlyFindElement(By.xpath("//li[contains(.,'" + exactValue + "')]")).click();
	}

	/**
	 * This method will search and return all enable check boxes 
	 * from a specified table by its classname
	 * 
	 * @param className Name class of Collection checkbox components 
	 * */
	public List<WebElement> getCheckedElementsInTable(String className){
		List<WebElement> checkedComponents = new ArrayList<WebElement>(); 
		for (WebElement component : webDriver.findElements(By.className(className))) {
			if(component.getAttribute("checked ").equals("true")){
				checkedComponents.add(component);
			}
		}
		return checkedComponents;
	}
	
	/**
	 * This method will retrieve value from table cell 
	 * @param header - Column Heading
	 * @param row - row from which you want to read value
	 * @return String
	 */
	public String getCellValue(String header, int row) {
		return getCellValue("//table", header, row);
	}

	/**
	 * Overloading method getCellValue for multiple tables. 
	 * In case there are multiple tables in a screen
	 * @param xpathToTable
	 * @param header
	 * @param row
	 * @return
	 */
	public String getCellValue(String xpathToTable, String header, int row) {
		int column = getHeaderColumnIndex(xpathToTable,header);
		if(column > 0 ) {
			return webDriver.findElement(By.xpath(xpathToTable + "[contains(., '" + header +"')]//tbody//tr[" + row +"]//td[" + column + "]")).getText();
		}
		else { 
			return "";
		}
	}

	/**
	 * method to return column index of a header title
	 * @param xpath
	 * @param header
	 * @return
	 */
	public int getHeaderColumnIndex(String xpath, String header) {
		int column = 0;
		List<WebElement>headElements = webDriver.findElements(By.xpath(xpath + "//thead/tr/th"));
		for(int i=0; i< headElements.size(); i++) {
			if(headElements.get(i).getText().trim().equalsIgnoreCase(header.trim())) {
				column = i + 1;
				break;
			}
		}
		return column;
	}

	/**
	 * Finds total number of rows on a specific Table
	 * @param locating mechanism
	 * @return number of rows
	 */
	public int getTableRowCount(By by) {
		List<WebElement> rows = webDriver.findElements(by);
		return rows.size();
	}

	/**
	 * Returns list of rows from a specified table. It is suggested to use xPath or cssSelector
	 * @param locating mechanism
	 * @return number of rows
	 */
	public List<WebElement> getTableRows(By by) {
		return webDriver.findElements(by);
	}

	/**
	 * To click button or hyper link on the page
	 * @param locating mechanism
	 */
	public void click(By by) {
		webDriver.findElement(by).click();
		patientlyFindElement(By.cssSelector("BODY"));
	}

	/**
	 * Wait for Spinner (Any implicit Async call) to finish
	 */
	public void waitForProcessToComplete() {
		waitForProcessToComplete(10);
	}

	public void waitForProcessToComplete(int timeoutInSeconds) {
		final int FREQUENCY = 2;
		double seconds = 0;
		while (seconds < timeoutInSeconds) {
			sleep(1000 / FREQUENCY);
			//class="ui-widget-content pe-blockui-content"
			if (webDriver.findElement(By.id("loadingSpinner")).isDisplayed()) {
				seconds = seconds + (1.0 / FREQUENCY);
			} else {
				return;
			}
		}
		Assert.fail("Async (AJAX) request did not complete within " + timeoutInSeconds + " seconds.");
	}

	public void sleep(int millis, String message) {
		print(message);
		sleep(millis);
	}

	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Closes current visible Pop up or modal windows and return control to main Frame
	 * */
	public void closePopupAndReturnToDefaultFrame() {
		webDriver.switchTo().defaultContent();
		patientlyFindElement(By.className("closebox")).click();
	}

	/**
	 * Change to specified frame using xPath
	 * @param xpath  
	 * */
	public void goToFrame(String xpath) {
		webDriver.switchTo().frame(webDriver.findElement(By.xpath(xpath)));
	}

	/**
	 * This method closes the alert and get the text
	 * @return
	 */
	public String closeAlertAndGetItsText() {
	    Alert alert = webDriver.switchTo().alert();
	    String alertText = alert.getText().trim();
	    alert.accept();
	    return alertText;
	}

	/**
	 * Closes panel for a MultiList widget
	 * @param by 
	 * @throws InterruptedException 
	 */
	public void closeSelectPanel(By by) throws InterruptedException {
		patientlyFindElement(by).click();
		patientlyFindElement(by).isDisplayed();
	}

	/**
	 * This method closes the Browser Generated Alerts
	 * As there is no clean way assert the alert text. This will close the alert through script.
	 * In order to know there is an alert or not 
	 * returning the true or false for the alert existence and Asserting the returned value
	 * @return boolean
	 */
	public boolean closeBrowserGeneratedAlert() {
		boolean alertPresent = true;
		try {
			((JavascriptExecutor )webDriver).executeScript( "window.onbeforeunload = function(e){};" );
		} catch (Exception e) {
			alertPresent = false;
		}
		return alertPresent;
	}

	/**
	 * This method fires a Javascript event for certain element.
	 * Since Selenium's "fireEvent" method does not exist in WebDriver, we use a JavascriptExecutor to substitute it
	 * Example for blur event: "arguments[0].focus(); arguments[0].blur(); return true"
	 * @param by
	 */
	public void fireJavascriptEventForElement(By by, String event) {
		WebElement element = webDriver.findElement(by);
		((JavascriptExecutor) webDriver).executeScript(event, element);
	}

	public void print(String string) {
		stdout.println(string);
	}

	/*
	 * Generic processes (like a form fulfill and submition, or
	 * follow a specific path of actions through a functionality) 
	 * can be managed with 'Utilities' Methods.
	 * All that's needed is to standardize naming, classification
	 * and identification of webComponents
	 * Here some samples:
	 * **/

	/** [Place Laboratory Order Form submition by two steps]
	 * After filling up order form this method will click on review and submit button
	 */
	public void submitAndConfirmOrder() {
		patientlyFindElement(By.name("_eventId_review")).click(); //Click on 'Review Order' button
		patientlyFindElement(By.name("_eventId_submit")).click(); //Click on 'Submit Order' button
		patientlyFindElement(By.cssSelector("BODY")); //Wait until page ends its reload process
	}

	/** [Choose a Service in two steps: from link 'Services Access' to SericeName link]
	 * This will choose service from main Page menu
	 * @param link [can contain Order, Billing, Configuration]
	 * @param serviceNames
	 * @throws Exception
	 */
	public void chooseService(String link, String serviceName) throws Exception {
		patientlyFindElement(By.linkText(link)).click();
		patientlyFindElement(By.id("wrapper"));
		webDriver.findElement(By.linkText(serviceName)).click();
		patientlyFindElement(By.id("wrapper"));
	}

	/** [Select values for following entities ]
	 * select value of  Groups, PA's, Costs Centers and Peer Review drop down
	 * @param serviceName
	 */
	public void fillOrderAttributes(String serviceName) {
		patientlyFindElement(By.linkText("Ordering")).click();
		print("Service name is: =========="+serviceName);
		Assert.assertTrue(patientlyFindElement(By.linkText(serviceName)).isDisplayed());
		patientlyFindElement(By.linkText(serviceName)).click();
		
		Select groupIdSelect = new Select(patientlyFindElement(By.xpath("//select[@id='Order_groupId']")));
		groupIdSelect.selectByVisibleText("admonTestGroup");
		waitForProcessToComplete();
		
		Select aministratorIdSelect = new Select(patientlyFindElement(By.xpath("//select[@id='Order_administratorUserId ']")));
		aministratorIdSelect.selectByVisibleText("Bortinelli, Adrian");
		waitForProcessToComplete();
		
		if(patientlyFindElement(By.xpath("//select[@id='Order_costCenterProgramCode ']")).isDisplayed()) {
			Select costCenterProgramCodeSelect = new Select(patientlyFindElement(By.xpath("//select[@id='Order_costCenterProgramCode ']")));
			costCenterProgramCodeSelect.selectByVisibleText("None");
		}
		
		if(patientlyFindElement(By.xpath("//select[@id='Order_peerReview ']")).isDisplayed()) {
			Select peerReviewSelect = new Select(patientlyFindElement(By.xpath("//select[@id='Order_peerReview ']")));
			peerReviewSelect.selectByVisibleText("Yes");
		}
	}

	/**
	 * Method to Upload an MultiFile along with other properties
	 * @param elementId
	 * @param fileUploadPath
	 * @param fileName
	 * @param fileType
	 * @param dataType
	 * @throws InterruptedException
	 */
	public void uploadMultiFileWithProperties(String elementId, String fileUploadPath, String fileName, String fileType, String dataType) throws InterruptedException {
		for(String winHandle : webDriver.getWindowHandles()){
			webDriver.switchTo().window(winHandle);
		}
		//Positioning on frame level 1 to perform the main action
		webDriver.switchTo().frame(1);
		//File Upload
		patientlyFindElement(By.xpath("//input[@id='fileContent']")).sendKeys(fileUploadPath+fileName);
		webDriver.switchTo().defaultContent();
		if(!fileType.equals(null)) {
			new Select(patientlyFindElement(By.cssSelector("#" + elementId + " > span.innerField > select[name=\"Filetype\"]"))).selectByVisibleText("Others");
		}
		if(!dataType.equals(null)) {
			new Select(patientlyFindElement(By.cssSelector("#" + elementId + " > span.innerField > select[name=\"Datatype\"]"))).selectByVisibleText("Others");
		}
		patientlyFindElement(By.xpath("//fieldset[@id='" + elementId + "']/span/span/span")).click();
     
		patientlyFindElement(By.xpath("//tbody/tr[contains(.,'"+ fileName +"')]"), 60);
	}
}
