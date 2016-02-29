# General_BusinessTestPj
Salinas Branch for All Business targeted Test
Selenium WebDriver Frameworks based Test resource repo

#Build Tool
This is a built in gradle Project based on testng Framework for Unit and Selenium WebDriver GUI Tests. Based on individual Modules
Testing and Regression Testing. All the test are mainly (but not limitted to) functional.

#Structure and Compilation
This Project is organized, compiled and constructed using Gradle specification. It is necessary read the build.gradle file specification
in order to understand the project's structure and sure it's being used approperly. Some command options are predefined to get the Test
run, letting the Tester the capability to decide the "mode of Run".

#Project Tests
Unit Test are not implemented since every single item needs to be based on the project's source code of the Application that it is going 
to be Tested.  The Selenium Test are organized in Suites,  every single Suite is based on an  Application Module  or a different kind of 
Application Component (depending on the component complexity ) and there is a set of Test Groups that the Suite incorporates. At the time, 
each Test Group includes a collection of Test (as it is expected).

Suite                                                                       
____|->Test Group                                                   
______________|->Test                                               

#Install Instructions
This project needs some tools to run.  These tools have to be installed in your environment  before you can compile and run any Project 
Resource.

#Windows
1) Create a repository right in the root of your local disk (Example: TestCoreApps)                     
2) Download Gradle and paste its resources inside this repo. (Official Page: http://gradle.org/gradle-download/)                
___2.1) Create an environment variable on windows to get the Gradle running in global context.              
___2.2) Add the variable to the general Path in Windows.                       
3) Use Eclipse (or your preferenced IDE) and paste all content inside same repo. (This project was built in STS, get it from https://spring.io/tools)              
4) Download Selenium Standalone Server (Official Page: http://www.seleniumhq.org/download/)             
___4.1) Create a folder inside same repo and paste the Server you just download               
___4.2) Create an environment variable on windows to get the Selenium Server running in global context.             
___4.3) Add the variable to the general Path in Windows.                
5) Optional: Get the Selenium plugin for FireFox browser. (Get it from https://addons.mozilla.org/en-US/firefox/addon/selenium-ide/) 
This tool may be useful when creating a  Test from scratch and you are not  familiar with the  Web-App structure and assets you are 
about to Test.                                                                                                
6) Open the STS app, and go to the Eclipse Market Place, it is required some Gradle integration tools for Eclipse in order to get the
project 100% functional.                                                                                          
7) Import the project using the Eclipse wizard for it.                                                                  
8) Run the Selenium Server.                                                                                         
9) Open a command prompt and apply the command gradle clean.
