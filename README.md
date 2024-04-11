# USDOT FHWA ConnectedVCS Tools
Developed by Leidos initially in support of USDOT Safety Pilot Program
This repository is a monorepo combining the repository histories of each of the 
ConnectedVCS Tool packages. Each subfolder corresponds to one of the original 
repositories used for ConnectedVCS Tools development.

## Setup Path
1. Set up LD_LIBRARY_PATH by running `LD_LIBRARY_PATH="[path_to_connectedvcs-tools]/fedgov-cv-lib-asn1c/third_party_lib"`.
2. Then, run `export LD_LIBRARY_PATH`.

## Build Instructions
1. Install JDK and Maven.
2. `cd fedgov-cv-parent` and `mvn install -DskipTests=true`.
3. Next, run `cd fedgov-cv-lib-asn1c` and `mvn install -DskipTests=true`.
4. Then, run `cd fedgov-cv-mapencoder` and `mvn install -DskipTests=true`.
5. Repeat the same for fedgov-cv-message-builder.
6. Run `mvn install -DskipTests=true` for  fedgov-cv-ISDcreator-webapp and fedgov-cv-TIMcreator-webapp.

## Deployment
1. Locate `isd.war` and `tim.war` in fedgov-cv-ISDcreator-webapp/target and fedgov-cv-TIMcreator-webapp/target respectively.
2. Deploy as servlets in conjunction with Apache Tomcat.