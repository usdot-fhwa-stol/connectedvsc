# USDOT FHWA ConnectedVCS Tools
Developed by Leidos initially in support of USDOT Safety Pilot Program
This repository is a monorepo combining the repository histories of each of the 
ConnectedVCS Tool packages. Each subfolder corresponds to one of the original 
repositories used for ConnectedVCS Tools development.

## Setup Path
1. After cloning the repo, set up LD_LIBRARY_PATH by running `LD_LIBRARY_PATH="[path_to_connectedvcs-tools]/fedgov-cv-lib-asn1c/third_party_lib"`.
2. Then, run `export LD_LIBRARY_PATH`.

## Build Instructions
1. Install JDK and Maven.
2. Run `sudo ./build.sh`.

## Deployment
1. Locate `isd.war` and `tim.war` in fedgov-cv-ISDcreator-webapp/target and fedgov-cv-TIMcreator-webapp/target respectively.
2. Deploy as servlets in conjunction with Apache Tomcat.