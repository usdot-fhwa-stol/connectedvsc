Map Tool Release Notes
----------------------------

Version 2.0.1, released Nov Xth, 2024
----------------------------------------

### **Summary**
This Hotfix 2.0.1 focuses on addressing security issues related to the Bing Map API usage and includes several improvements related to security of the Map tool websites. 

**<ins>Fixes:</ins>** 
- Epic 203 Implement ConnectedVCS Tool Security Fixes:  Investigation revealed that ConnectedVCS ISD Tool could no longer load map tiles from Bing due to Bing API keys being suspended due to abnormal usage. The changes included below were applied to the webappopen.connectedvcs.com and webapptest.connectedvcs.com (test site). 
   - Implementing backend monitoring of API calls 

   - Switching to Google Maps API for elevation data 

   - Adding protection from bots and common DDOS attacks 

- These security fixes apply to the open-source sites: 
   - webappopen.connectedvcs.com 
   - webapptest.connectedvcs.com (test site) 

**<ins>Pull Requests:</ins>**

- https://github.com/usdot-fhwa-stol/connectedvcs-tools/pull/39, (use session keys) 
- https://github.com/usdot-fhwa-stol/connectedvcs-tools/pull/40, (backend monitoring) 
- https://github.com/usdot-fhwa-stol/connectedvcs-tools/pull/43  (error log fix) 

 

**<ins>Known Issues:</ins>**  

- (N/A) 
 
In addition to the security fixes above, we have implemented AWS security features for all three map tool websites: 
   - webapp.connectedvcs.com (legacy site) 
   - webappopen.connectedvcs.com 
   - webapptest.connectedvcs.com (test site) 

Version 2.0.0, released April 25th, 2024
----------------------------------------

### **Summary**

In this release, the MAP (also known as ISD) tool packages have been updated to use the open-source ASN1C compiler. These updates are validated with new and enhanced unit tests. UI updates include the removal of TIM and Message Validator buttons from the landing page, along with updated text in the ASN.1 text box of the MAP tool. After removing all traces of the proprietary tool, a public GitHub repository was created to release the code. All code has been added to this new repo, the README has been updated with build instructions, and the CI/CD pipeline has been revised. Lastly, a production environment has been established, and the code is now live at https://webappopen.connectedvcs.com. 

This release of the Map Tool introduces several significant updates:

- **<ins> Integration of Open-Source ASN1C Compiler:</ins>** After integrating the open-source ASN1c compiler, the message-builder and map-encoder packages have been updated to support all mandatory and selected optional intersection fields used in the existing MAP tool.

- **<ins> Enhanced Testing:</ins>** Added and updated unit tests for message-builder and map-encoder to cover the new functionalities.

- **<ins> Server Logs:</ins>** The message builder package now logs the generated MAP message and its encoded hex string to a server-side file for further verification purposes.

- **<ins> UI Adjustments:</ins>**
    - Removed buttons for TIM and message validator from the landing page as these features are not implemented in this version.
    - Updated the ASN.1 text box in the MAP tool to reflect that it will not be populated in this release.

- **<ins> Removal of Proprietay Components:</ins>** All components of the proprietary tool have been removed.

- **<ins> Repository and CI/CD Updates:</ins>**
    - Created the connectedvcs-tools GitHub repository in preparation for public release.
    - Updated the README file with command line build instructions.
    - Enhanced GitHub Actions CI/CD workflows to include a sonar scanner for source code analysis.

- **<ins> Production Environment:</ins>** Established a production environment based on the connectedvcs-tools (MAP tool, etc.) GitHub repository. The production site is now live at https://webappopen.connectedvcs.com.

Known Issues related to this release:

- **<ins>Partial Implementation of ASN1C:</ins>** The open-source ASN1C compiler has been integrated only within the  MAP tool. Features such as TIM and Message Validator remain disabled pending further updates. The original implementation of the CVCS Tools is still available and accessible at https://www.webapp.connectedvcs.com.
