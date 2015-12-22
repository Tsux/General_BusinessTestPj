package org.ektqa.commons;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.ektqa.webdriver.common.ThreadSafeWebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ManageTestResult {

	private int Count = 0;
	PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));

	public void ScreenShot(ITestResult testRes, String testState) {
		try {

			String NewFileNamePath;
			
			InetAddress address = InetAddress.getLocalHost();
			String hostname = address.getHostName();
			
			stdout.println("Host Name is: " + hostname);
			
			String fileNameWithFolder;

			//Get the dir path
			File directory = new File (".");
			stdout.println("Canonical Path is: " + directory.getCanonicalPath()); 

			//get current date time with Date() to create unique file name
			DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
			//get current date time with Date()
			Date date = new Date();

			//To identify the system
			InetAddress ownIP=InetAddress.getLocalHost();
			if(testRes != null){
				fileNameWithFolder = "/ScreenShots/"+ dateFormat.format(date)+"_"+ownIP.getHostAddress()+"_"+testRes.getName()+ ".png";
			} else{
				fileNameWithFolder = "/ScreenShots/"+ dateFormat.format(date)+"_"+ownIP.getHostAddress() + ".png";
			}
			
			stdout.println("File location inside SRM project: " + fileNameWithFolder);
			
			NewFileNamePath = directory.getCanonicalPath() + fileNameWithFolder;
			stdout.println("File with canonical path: " + NewFileNamePath);
			
			try {
				String base64Screenshot = session().captureEntirePageScreenshotToString(""); //Method only works with Moz and Chrome
			    byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
			    FileOutputStream fos = new FileOutputStream(new File(NewFileNamePath));
			    fos.write(decodedScreenshot);
			    fos.close();
			}
			catch(Exception e) {
				stdout.println(e);
				WebDriver driver = ThreadSafeWebDriver.session();
				driver = new Augmenter().augment(driver);
				
				File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcfile, new File(NewFileNamePath));
			}

			Count++; //Assign each screen shot a number
			NewFileNamePath = getFileNamePathByBusiness(hostname) + fileNameWithFolder;
			if(testRes != null){
				NewFileNamePath = Count + ". " + testState + "! " + "<a href=\"" + NewFileNamePath + "\" target=\"_blank\">" + getTestClassNameWithoutPackageStucture(testRes) + " > " + testRes.getName() + "</a>";
			} else{
				NewFileNamePath = Count + ". " + testState + "! " + "<a href=\"" + NewFileNamePath + "\" target=\"_blank\">See ScreenShot here</a>";
			}
			stdout.println("Screenshot for " + testState + " test is at: " + NewFileNamePath);
				//Place the reference in TestNG web report 
				Reporter.log(NewFileNamePath);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getFileNamePathByBusiness(String currentHostName){
		String fileNamePath;
		if(currentHostName.equalsIgnoreCase(FileConstant.FisProdHostName)){
			fileNamePath = "http://" + FileConstant.FisServerName + ":8080/job/Fiscal/ws";
			stdout.println("* This is running from " + FileConstant.FisProdHostName+ " jenkins server *");
		} else if(currentHostName.equalsIgnoreCase(FileConstant.FisDevHostName)){
			fileNamePath = "http://" + FileConstant.FisDevHostName + ":8080/job/Fiscal/ws";
			stdout.println("* This is running from " + FileConstant.FisDevHostName + " jenkins server *");
		} else if(currentHostName.equalsIgnoreCase(FileConstant.FisQAHostName)){
			fileNamePath = "http://" + FileConstant.FisQAHostName + ":8080/job/Fiscal/ws";
			stdout.println("* This is running from " + FileConstant.FisQAHostName + " jenkins server *");
			//-------------------------------Fiscal fileServer Block-------------------------------------
		} else if(currentHostName.equalsIgnoreCase(FileConstant.DEXProdHostName)){
			fileNamePath = "http://" + FileConstant.DEXServerName + ":8080/job/DEX/ws";
			stdout.println("* This is running from " + FileConstant.DEXProdHostName+ " jenkins server *");
		} else if (currentHostName.equalsIgnoreCase(FileConstant.DEXDevHostName)){
			fileNamePath = "http://" + FileConstant.FisServerName + ":8080/job/DEX/ws";
			stdout.println("* This is running from " + FileConstant.FisProdHostName+ " jenkins server *");
		} else if(currentHostName.equalsIgnoreCase(FileConstant.DEXQAHostName)){
			fileNamePath = "http://" + FileConstant.FisServerName + ":8080/job/DEX/ws";
			stdout.println("* This is running from " + FileConstant.FisProdHostName+ " jenkins server *");
			//-------------------------------DEX fileServer Block-----------------------------------------
		} else if (currentHostName.equalsIgnoreCase(FileConstant.NetoProdHostName)){
			fileNamePath = "http://" + FileConstant.NetoServerName + ":8080/job/Fiscal/ws";
			stdout.println("* This is running from " + FileConstant.NetoProdHostName+ " jenkins server *");
		} else if (currentHostName.equalsIgnoreCase(FileConstant.NetoDevHostName)){
			fileNamePath = "http://" + FileConstant.NetoDevHostName + ":8080/job/Neto/ws";
			stdout.println("* This is running from " + FileConstant.NetoDevHostName+ " jenkins server *");
		} else if (currentHostName.equalsIgnoreCase(FileConstant.NetoQAHostName)){
			fileNamePath = "http://" + FileConstant.NetoQAHostName + ":8080/job/Neto/ws";
			stdout.println("* This is running from " + FileConstant.NetoQAHostName+ " jenkins server *");
			//-------------------------------Neto fileServer Block---------------------------------------
		} else{
			fileNamePath ="file:///C:/Users/emaldonadom/Documents/workspace-sts-3.7.0.RELEASE/GeneralBusiness_test";
			stdout.println(">>There is a problem getting filePath: Unrecognized Host Name");
		}
		
		return fileNamePath;
	}

	public String getTestClassNameWithoutPackageStucture(ITestResult tr) {
		String testClassNameWithPackageStructure = tr.getTestClass().getName();
		String[] testClassNameWithoutPackageStuctureArray = testClassNameWithPackageStructure.split("\\.");
		int lastIdex = testClassNameWithoutPackageStuctureArray.length - 1;
		return testClassNameWithoutPackageStuctureArray[lastIdex];
	}
}
