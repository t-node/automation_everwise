# automation_everwise

# Prerequisites:
 Operating System: MAC <br/>
 Install Java 8 <br/>
 Install maven latest version <br/>
 Chrome 66 <br/>
 Firefox 59 <br/>
 Go the project main directory containing pom.xml (build file for maven, containing the dependencies):
 Execute the following commands for chrome and firefox to run from terminal (Assuming paths have been correctly set)

 mvn install -DsuiteXml=everwise_regression.xml -Dtestng.groups=regression -Peverwise_chrome
 mvn install -DsuiteXml=everwise_regression.xml -Dtestng.groups=regression -Peverwise_firefox
