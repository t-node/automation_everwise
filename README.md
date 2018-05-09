# Project execution

Test script is included in the package tests.everwise.regression (src/test/java)<br/>
All Page object corresponding to the test scenario can be found in the package ui.pages inside (src/main/java)<br/>


# Prerequisites:
 Operating System: MAC <br/>
 Install Java 8 <br/>
 Install maven latest version <br/>
 Chrome 66 <br/>
 Firefox 59 <br/>
 
# Execution: 
 Go the project main directory containing pom.xml (build file for maven, containing the dependencies): <br/>
 Execute the following commands for chrome and firefox to run from terminal (Assuming paths have been correctly set)

 mvn install -DsuiteXml=everwise_regression.xml -Dtestng.groups=regression -Peverwise_chrome <br/>
 mvn install -DsuiteXml=everwise_regression.xml -Dtestng.groups=regression -Peverwise_firefox <br/>

# Automation Report
  -Report is available in the target folder with the file name TestReport.html <br/>
  -Screenshots are included in the TestReport.html. (They are accessed from the Test-Screenshots folder) <br/>
  -Video of the test case execution is generated (Assuming the Browser is visible on the desktop window, Video is present in   
   the Videos folder) <br/>
  -Test-Screenshots and Videos folders are auto created if not present <br/>
