# Running Dockerized ConnectedVCS Tools
If you are running ConnectedVCS Tools using a docker image, you can use the following instructions:

## Prerequisites
ConnectedVCS Tools has been developed using Ubuntu 20.04 and Ubuntu 22.04. Further testing with other operating systems is needed before guidance is created. For the moment, please use Ubuntu 20.04 or later [Ubuntu LTS Release](https://releases.ubuntu.com/).

### Install Docker CE
Instructions for installing Docker may change, so please use the current instructions at the Docker website:
https://docs.docker.com/desktop/install/linux-install/.

## Run the ConnectedVCS Tools Image

### Build a Custom Image

1. Clone the ConnectedVCS Tools respository:
```
git clone https://github.com/usdot-fhwa-stol/connectedvcs-tools.git
```
2. Create a new Bing Maps API Key (Basic, Website) using https://www.bingmapsportal.com/.
    - Please read the [Bing Maps API Key Guidance](/docs/BingMaps_API_Key_Guidance.md).
3. Enter Bing Maps API key and any arbitrary username in [ISDcreator-webapp-keys](/private-resources/js/ISDcreator-webapp-keys.js).

4. Using SSL vs not using SSL:

    - If using SSL certificates, you may look up instructions to generate a keystore and SSL certficiates with your certificate authority (CA) of choice. In this case, the [Dockerfile SSL Example](Dockerfile_SSL_example) will need to be updated to copy your applicable keystore information to the image. **NOTE**: Only the last two lines in the Dockerfile will need to be updated.

    - If running the tool without certificates, the default Dockerfile may be used as is [Dockerfile](../Dockerfile).

5. After all changes/updates have been made, build the image:
```
sudo docker build -t udsotfhwastol/connectedvcs-tools:<tag> .
```

### Run Image with SSL certificate
```
sudo docker run -d -p 443:443 udsotfhwastol/connectedvcs-tools:<tag>
```

### Run Image without SSL certificate
```
sudo docker run -d -p 8080:8080 udsotfhwastol/connectedvcs-tools:<tag>
```

## Access the ConnectedVCS Tools Interface

1.  In your browser, navigate to:
    - with SSL: https://127.0.0.1:443/
    - without SSL: http://127.0.0.1:8080/
