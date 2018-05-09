# automation_everwise

# Prerequisites:
# Operating System: MAC
# Install Java 8
# Install maven latest version
# Chrome 66
# Firefox 59
# Go the project main directory containing pom.xml (build file for maven, containing the dependencies):
# Execute the following commands for chrome and firefox to run from terminal (Assuming paths have been correctly set)

# mvn install -DsuiteXml=everwise_regression.xml -Dtestng.groups=regression -Peverwise_chrome
# mvn install -DsuiteXml=everwise_regression.xml -Dtestng.groups=regression -Peverwise_firefox
