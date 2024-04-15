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
1. Locate `root.war`,`isd.war` and `tim.war` in `connectedvcs-tools/`, `connectedvcs-tools/fedgov-cv-ISDcreator-webapp/target` and `connectedvcs-tools/fedgov-cv-TIMcreator-webapp/target` respectively.
2. Deploy as servlets in conjunction with Apache Tomcat.

## Contribution
Welcome to the CARMA contributing guide. Please read this guide to learn about our development process, how to propose pull requests and improvements, and how to build and test your changes to this project. [CARMA Contributing Guide](Contributing.md) 

## Code of Conduct 
Please read our [CARMA Code of Conduct](Code_of_Conduct.md) which outlines our expectations for participants within the CARMA community, as well as steps to reporting unacceptable behavior. We are committed to providing a welcoming and inspiring community for all and expect our code of conduct to be honored. Anyone who violates this code of conduct may be banned from the community.

## Attribution
The development team would like to acknowledge the people who have made direct contributions to the design and code in this repository. [CARMA Attribution](ATTRIBUTION.md) 

## License
By contributing to the Federal Highway Administration (FHWA) ConnectedVCS Tools, you agree that your contributions will be licensed under its Apache License 2.0 license. [CARMA License](<LICENSE.md>) 

## Contact
For more information, contact CAVSupportServices@dot.gov.

## Support
For technical support, please contact the CAV Support Services at CAVSupportServices@dot.gov.


