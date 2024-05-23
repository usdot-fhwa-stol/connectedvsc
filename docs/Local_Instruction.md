# Running ConnectedVCS Tools Locally

If you are running ConnectedVCS Tools locally, you can use the following instructions:

## Clone repository

1. Clone the ConnectedVCS Tools respository:
```
git clone https://github.com/usdot-fhwa-stol/connectedvcs-tools.git
```

## Local Setup Path

1. Set up LD_LIBRARY_PATH by running:
```
echo export LD_LIBRARY_PATH="[path_to_connectedvcs-tools]/fedgov-cv-lib-asn1c/third_party_lib" >> ~/.bashrc
```
   - **NOTE**: Change the above path to point to the correct third_party_lib path.

## Local Build Instructions

1. Install JDK and Maven.
2. Create a new Bing Maps API Key (Basic, Website) using https://www.bingmapsportal.com/ 
3. Enter API key in private-resources/js/ISDcreator-webapp-keys.js
4. Run:
```
sudo ./build.sh
```

## Local Deployment

1. Locate `root.war`, `private-resources.war`, `isd.war`, and `tim.war` in `connectedvcs-tools/`, `connectedvcs-tools/fedgov-cv-ISDcreator-webapp/target` and `connectedvcs-tools/fedgov-cv-TIMcreator-webapp/target` respectively.
2. Deploy as servlets in conjunction with Apache Tomcat.
