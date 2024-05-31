# Running ConnectedVCS Tools Locally
If you are running ConnectedVCS Tools locally, you can use the following instructions:

## Prerequisites
ConnectedVCS Tools has been developed using Ubuntu 20.04 and Ubuntu 22.04. Further testing with other operating systems is needed before guidance is created. For the moment, please use Ubuntu 20.04 or later [Ubuntu LTS Release](https://releases.ubuntu.com/).

1. Install JDK (openjdk-8-jdk).
```
sudo apt-get install openjdk-8-jdk
```
2. Install Maven (3.6.3).
```
sudo apt-get install maven
```

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
   - **NOTE**: Change the above path to point to the correct [third_party_lib path](/fedgov-cv-lib-asn1c/third_party_lib).

## Local Build Instructions

1. Create a new Bing Maps API Key (Basic, Website) using https://www.bingmapsportal.com/.
    - Please read the [Bing Maps API Key Guidance](/docs/BingMaps_API_Key_Guidance.md).
2. Enter Bing Maps API key and any arbitrary username in [ISDcreator-webapp-keys](/private-resources/js/ISDcreator-webapp-keys.js).
3. Run:
```
sudo ./build.sh
```

## Local Deployment

1. Locate `root.war`, `private-resources.war`, `isd.war`, and `tim.war` in `connectedvcs-tools/`, `connectedvcs-tools/fedgov-cv-ISDcreator-webapp/target` and `connectedvcs-tools/fedgov-cv-TIMcreator-webapp/target` respectively.
2. Deploy as servlets in conjunction with Apache Tomcat.
