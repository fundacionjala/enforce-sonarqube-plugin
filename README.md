[![Build Status](https://travis-ci.org/fundacionjala/enforce-sonarqube-plugin.svg)](https://travis-ci.org/fundacionjala/enforce-sonarqube-plugin) [![license](http://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat)](https://github.com/fundacionjala/enforce-sonarqube-plugin/blob/master/LICENSE)

# Enforce Sonarqube Plugin
## Description

The plug-in enables analysis of Apex source code with SonarQube.

## Steps to Analyze a Apex Project

    Note: It's recommended to compile apex plug-in from a stable release

1. Install SonarQube Server (see [Setup and Upgrade](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) for more details).

2. Install [SonarQube Runner](http://docs.sonarqube.org/display/SONAR/Installing+and+Configuring+SonarQube+Runner) and be sure your can call sonar-runner from the directory where you have your source code.

3. Install Apex Plug-in (see [Installing a Plugin](http://docs.sonarqube.org/display/SONAR/Installing+a+Plugin) for more details).

4. Create a sonar-project.properties file at the root of your project (a sample project, available on GitHub).

5. Run sonar-runner command from the project root dir.

6. Follow the link provided at the end of the analysis to browse your project's quality in SonarQube UI (see: [Browsing SonarQube](http://docs.sonarqube.org/display/SONAR/Browsing+SonarQube)).

---
# Enforce plug-in for developers.

## Prerequisites
To build a SonarQube plug-in, you need [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 (or greater) and [Maven](http://maven.apache.org/download.cgi) 3.1 (or greater). 

## Structure
Enforce plug-in divides its structure in the following modules:
```
enforce-sonarqube-plugin
  | - sonar-checks
  | - sonar-squid
  | - sonar-plugins
  /
```
***sonar-checks:*** Represents all sonarqube rules for analyze Apex language.

***sonar-squid:*** Represents to Static source analyzer, which provides an AST.

***sonar-plugins:*** Represents to implementation of sonarqube plugin for Apex language.
