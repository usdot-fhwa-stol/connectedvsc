# USDOT FHWA ConnectedVCS Tools
Developed by Leidos initially in support of USDOT Safety Pilot Program
This repository is a monorepo combining the repository histories of each of the 
ConnectedVCS Tool packages. Each subfolder corresponds to one of the original 
repositories used for ConnectedVCS Tools development.

## Build Instructions
1. Install JDK and Maven
2. `cd fedgov-cv-parent` and `mvn install -DskipTests=true`
3. Repeat for  fedgov-cv-message-builder
4. `cd fedgov-cv-rsm-converter` and `./gradlew install`
5. Run `mvn install -DskipTests=true` for  fedgov-cv-ISDcreator-webapp and fedgov-cv-TIMcreator-webapp

## Deployment
1. Locate `isd.war` and `tim.war` in fedgov-cv-ISDcreator-webapp/target and fedgov-cv-TIMcreator-webapp/target respectively.
2. Deploy as servlets in conjunction with Apache Tomcat.


