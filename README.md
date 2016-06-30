[![Build Status](https://travis-ci.org/fundacionjala/enforce-sonarqube-plugin.svg)](https://travis-ci.org/fundacionjala/enforce-sonarqube-plugin) [![license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/master/LICENSE)

# Note: The current version is a functional prototype of a Apex plugin for SonarQube.
The prototype has support for a subset of Apex language grammar, that means there might be errors during analysis of Apex classes.
The support for more SFDC components is in progress.

# Enforce Sonarqube Plugin
## Description

The plug-in enables analysis of Apex source code with SonarQube.

## Steps to Analyze a Apex Project

1. Install SonarQube Server 
   * [Download](http://www.sonarqube.org/downloads/) and unzip the SonarQube distribution (let's say in "C:\sonarqube" for windows or "/etc/sonarqube/" for linux)
  
2. Install [SonarQube scanner](https://sonarsource.bintray.com/Distribution/sonar-scanner-cli/sonar-scanner-2.6.1.zip) and configure sonar-runner as an enviroment variable
   * For linux use, go to /etc/profile file, and add at the end of the file this code 

               export SONAR_RUNNER_HOME=.../sonar-scanner-2.5.1
               export PATH=$PATH:$SONAR_RUNNER_HOME/bin.

   * For windows use, follow [these](http://www.computerhope.com/issues/ch000549.htm) steps using sonar scanner path and its bin folder as well

3. Install Apex Plug-in; To do this, download the plugin file (apex-plugin.jar) from [here](https://bintray.com/fundacionjala/enforce/enforce-sonar-plugin/view), then copy and paste into "../sonarqube/extensions/plugins" folder.
   
 * Start the SonarQube server:

        ![starting SonarQube server](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/starting-sonarQube.png)

###### Note: These steps must be performed only once after plugin is installed in sonnarqube.
             3.1 Log in as administrator
![sonar login](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/sonar-login.png)

             3.2 Go to Rules tab
![rules tab](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/rules-tab.png)

             3.3 Select Apex in languages in the left panel.
![select apex language](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/select-apex-language.png)

             3.4 Then click on Bulk Change option

             3.5 Click on activate in option
![select Bulk Change](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/select-bulk-change.png)

             3.6 Enter Apex and click on apply
![write apex](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/write-the-languaje.png)

###Scanning projects.
1. Create a sonar-project.properties file at the root of your project

           sonar.projectKey=my:project
           sonar.projectName=My project
           sonar.projectVersion=1.0
           sonar.language=apex
           sonar.sources=.

      > **sonar.projectKey**: must be unique in a given SonarQube instance

      > **sonar.projectName**: this is the name displayed in the SonarQube UI

      > **sonar.language**: specifies the language for analysis

      > **sonar.sources**: this "dot" indicates to sonnar scanner to scan all files from the current level of directories, this is, all files that have an extension ".cls". This extension is defined by plugin scanner.

2. Run **sonar-runner** command from the project root dir.

3. Follow the provided link at the end of the analysis output to browse your project's quality in SonarQube
![project analysis result](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/gh-pages/img/project-in-sonarqube-ui.png)

---
# Enforce plug-in for developers.

## Prerequisites
To build a SonarQube plug-in, you need [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 (or greater) and [Maven](http://maven.apache.org/download.cgi) 3.1 (or greater). 

## Structure
Enforce plug-in divides its structure in the following modules:
```
enforce-sonarqube-plugin
  | - apex-checks
  | - apex-squid
  | - sonar-apex-plugin
  /
```
***[apex-checks](https://github.com/fundacionjala/enforce-sonarqube-plugin/wiki/Apex-Checks):*** Represents all sonarqube rules for analyze Apex language.

***[apex-squid](https://github.com/fundacionjala/enforce-sonarqube-plugin/wiki/Apex-Squid):*** Represents to static source analyzer, which provides an AST.

***[sonar-apex-plugin](https://github.com/fundacionjala/enforce-sonarqube-plugin/wiki/Sonar-Apex-Plugin):*** Represents to implementation of sonarqube plugin for Apex language.
