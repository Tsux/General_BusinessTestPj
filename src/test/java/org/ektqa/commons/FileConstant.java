package org.ektqa.commons;

public class FileConstant {
	//In case all business Test will be centralized:
	public static final String ektqaProdHostName = "localhost";
	public static final String ektqaDevHostName = "localhost";
	public static final String ektqaQAHostName = "localhost";
	public static final String ektqaServerName = "localhost";
	//In case non centralized business Test apply: 
	public static final String FisDevHostName = "localhost";
	public static final String FisQAHostName = "localhost";
	public static final String FisProdHostName = "localhost";
	public static final String FisServerName = "SpecifiedServerNameFromPROD";
	public static final String DEXDevHostName = "localhost";
	public static final String DEXQAHostName = "localhost";
	public static final String DEXProdHostName = "localhost";
	public static final String DEXServerName = "SpecifiedServerNameFromPROD";
	public static final String NetoDevHostName = "localhost";
	public static final String NetoQAHostName = "localhost";
	public static final String NetoProdHostName = "localhost";
	public static final String NetoServerName = "SpecifiedServerNameFromPROD";
	
	public static final String URLForFisSeleniumTestFiles = "http://" + FisServerName + ":8000/selenium-tests/";
	public static final String URLForDEXSeleniumTestFiles = "http://" + DEXServerName + ":8000/selenium-tests/";
	public static final String URLForNetoSeleniumTestFiles = "http://" + NetoServerName + ":8000/selenium-tests/";
	public static final String appHostName = "http://srmdmqa03.stjude.org:8080/srm/";

}
