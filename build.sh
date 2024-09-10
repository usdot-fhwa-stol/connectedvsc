LD_LIBRARY_PATH="/home/vboxuser/connectedvcs-tools/fedgov-cv-lib-asn1c/third_party_lib"
export LD_LIBRARY_PATH
# Build Steps
cd ./fedgov-cv-parent
mvn clean install -U

cd ../fedgov-cv-lib-asn1c/
mvn clean install -U

cd ../fedgov-cv-mapencoder/
mvn clean install -U

# cd ../fedgov-cv-rgaencoder/
# mvn clean install -U

cd ../fedgov-cv-message-builder/
mvn clean install -U

# cd ../fedgov-cv-ISDcreator-webapp/
# mvn clean install -U

# cd ../fedgov-cv-TIMcreator-webapp/
# mvn clean install -U

jar cvf ../root.war -C ../root .