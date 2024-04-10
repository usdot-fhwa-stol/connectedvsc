# HEAD (MARCH 3, 2017)

### Revision 2223 - Directory Listing
* Modified Thu Mar 2 15:38:16 2017 EST (20 hours, 56 minutes ago) by martzth

Changed zoom level array to be slightly better.

### Revision 2220 - Directory Listing
* Modified Thu Mar 2 13:41:19 2017 EST (22 hours, 53 minutes ago) by martzth

Working on cleaning up the code to make it load quicker and be easier to read. Also changed the deposit functionality. Added zoom as a cookie.

### Revision 2213 - Directory Listing
* Modified Tue Feb 28 15:56:15 2017 EST (2 days, 20 hours ago) by martzth

Fixed tile age bug, wrapping of ITIS codes, ITIS code duplication bug, and other small visual elements

### Revision 2212 - Directory Listing
* Modified Tue Feb 28 13:40:22 2017 EST (2 days, 22 hours ago) by martzth

Made search bar work - https vs http. Also got additional zoom levels.

### Revision 2208 - Directory Listing
* Modified Tue Feb 28 12:17:30 2017 EST (3 days ago) by martzth

Added geocomplete for better processing of locations.

### Revision 2207 - Directory Listing
* Modified Mon Feb 27 16:15:58 2017 EST (3 days, 20 hours ago) by martzth

Fixed minor things regarding the ITIS codes. Should load and save correctly now.

### Revision 2202 - Directory Listing
* Modified Sun Feb 26 12:12:10 2017 EST (5 days ago) by kuninl

Converted to Java 1.8

### Revision 2192 - Directory Listing
* Modified Wed Feb 22 13:14:29 2017 EST (8 days, 23 hours ago) by martzth

Added tile age to TIM map

### Revision 2190 - Directory Listing
* Modified Wed Feb 22 11:41:14 2017 EST (9 days ago) by martzth

Added sheepIt to allow for multiple ITIS code blocks. Moved non-custom js to the external folder. Updated the index with additional codes. Moved content and speed limit to a tagged based system rather than a dropdown. Fixed the https issue with bing maps loading. Added all the ITIS codes programatically. Sorry for the massive update.

### Revision 2187 - Directory Listing
* Modified Thu Feb 16 12:30:05 2017 EST (2 weeks, 1 day ago) by martzth

Fixed CORS issue for location finder - jsonp.

### Revision 2185 - Directory Listing
* Modified Thu Feb 16 12:19:24 2017 EST (2 weeks, 1 day ago) by martzth

Made several updates including making the layer selector more visible, adding search of streets, storing center point location as a cookie, and updating the depositor

### Revision 2159 - Directory Listing
* Modified Wed Jan 18 08:46:48 2017 EST (6 weeks, 2 days ago) by hamiltoneri

Added jetty-env.xml to WEB-INF to ensure proper class loading when run on jetty server
Locked Jetty's dependency versions in poms to match production server to avoid issues with class loading

### Revision 2143 - Directory Listing
* Modified Fri Dec 16 18:20:30 2016 EST (2 months, 2 weeks ago) by kuninl

Updated Help and other misc changes per customer feedback

### Revision 2125 - Directory Listing
* Modified Tue Dec 6 08:14:12 2016 EST (2 months, 3 weeks ago) by kuninl

Changed release date

### Revision 2124 - Directory Listing
* Modified Mon Dec 5 16:11:06 2016 EST (2 months, 3 weeks ago) by martzth

Fixed the issues with signs not storing content and info type on save/load. Content speed is now a float value instead of integer.

### Revision 2121 - Directory Listing
* Modified Sun Dec 4 09:19:06 2016 EST (2 months, 4 weeks ago) by kuninl

Renamed log file to tim.log

### Revision 2118 - Directory Listing
* Modified Sun Dec 4 08:46:02 2016 EST (2 months, 4 weeks ago) by kuninl

Updated TIM builder to 2016 standard

### Revision 2111 - Directory Listing
* Modified Mon Nov 28 17:41:21 2016 EST (3 months ago) by martzth

Re-added openlayers thinking there was an issue in the source code. Fixed the following: 1. lane width delta can go negative now 2. lane width delta stored on secondary nodes 3. elevation stored on secondary nodes 4. lane moving when adjusting data on secondary node 5. validation not showing on marker node 6. directionality broken after cancel

### Revision 2106 - Directory Listing
* Modified Mon Nov 28 10:17:38 2016 EST (3 months ago) by martzth

Moved fields to appropriate levels

### Revision 2099 - Directory Listing
* Modified Mon Nov 21 12:09:53 2016 EST (3 months, 1 week ago) by martzth

Added several new fields to index, mapping, and depositor, added config for help buttons, added help buttons

### Revision 2015 - Directory Listing
* Modified Fri Sep 16 10:35:05 2016 EDT (5 months, 2 weeks ago) by martzth

Removed bad check

### Revision 2014 - Directory Listing
* Modified Fri Sep 16 09:32:32 2016 EDT (5 months, 2 weeks ago) by martzth

Fixed code standards

### Revision 2013 - Directory Listing
* Modified Thu Sep 15 16:27:16 2016 EDT (5 months, 2 weeks ago) by martzth

Fixed directionality loading correctly on map open

### Revision 2012 - Directory Listing
* Modified Thu Sep 15 15:39:09 2016 EDT (5 months, 2 weeks ago) by martzth

Fixed ITIS code selection disappearing on load

### Revision 2011 - Directory Listing
* Modified Thu Sep 15 12:14:04 2016 EDT (5 months, 2 weeks ago) by martzth

Fixed elevation bug

### Revision 2010 - Directory Listing
* Modified Thu Sep 15 10:41:20 2016 EDT (5 months, 2 weeks ago) by martzth

Fixed lat/lon bug in verified point and fixed a bug with repeated dialog boxes on save

### Revision 1837 - Directory Listing
* Modified Tue Mar 22 13:49:54 2016 EDT (11 months, 1 week ago) by BRUNNERDT

latest bing key

### Revision 1805 - Directory Listing
* Modified Fri Jul 17 14:24:30 2015 EDT (19 months, 2 weeks ago) by martzth

Changed ITIS Codes

### Revision 1802 - Directory Listing
* Modified Wed Jul 15 10:31:41 2015 EDT (19 months, 2 weeks ago) by martzth

Added dropdown for lanes to improve validation

### Revision 1764 - Directory Listing
* Modified Wed Jun 17 11:18:36 2015 EDT (20 months, 2 weeks ago) by martzth

Fixed bugs related to save/load

### Revision 1758 - Directory Listing
* Modified Tue Jun 16 10:58:41 2015 EDT (20 months, 2 weeks ago) by martzth

Fixed minor bug with builder

### Revision 1757 - Directory Listing
* Modified Mon Jun 15 16:59:04 2015 EDT (20 months, 2 weeks ago) by martzth

Updated the map to allow polygon regions

### Revision 1744 - Directory Listing
* Modified Tue Jun 2 15:11:21 2015 EDT (21 months ago) by martzth

Made extent a truly optional field

### Revision 1742 - Directory Listing
* Modified Tue Jun 2 14:28:42 2015 EDT (21 months ago) by martzth

Added some things for Leonid - preference items really

### Revision 1740 - Directory Listing
* Modified Tue Jun 2 13:57:03 2015 EDT (21 months ago) by martzth

Disabled the encode button when errors approach

### Revision 1739 - Directory Listing
* Modified Tue Jun 2 12:37:39 2015 EDT (21 months ago) by martzth

Fixed the circle reset

### Revision 1738 - Directory Listing
* Modified Tue Jun 2 11:37:04 2015 EDT (21 months ago) by martzth

Fixed bugs when clearing the map

### Revision 1736 - Directory Listing
* Modified Mon Jun 1 17:53:54 2015 EDT (21 months ago) by martzth

"Deposit area"  should be called "Applicable Region" 
TimeToLive is actually an optional field
Start Year, Start Time, and Duration in min is too tedious  we should replace this with Start time (that has year, date, and time) and End Time (with year, date, and time) and derive the three values from that.
I expected to see NW & SE corners with lat/lon like we do for nodes, etc

### Revision 1733 - Directory Listing
* Modified Mon Jun 1 17:27:17 2015 EDT (21 months ago) by martzth

"Deposit area"  should be called "Applicable Region" 
TimeToLive is actually an optional field
Start Year, Start Time, and Duration in min is too tedious  we should replace this with Start time (that has year, date, and time) and End Time (with year, date, and time) and derive the three values from that.
I expected to see NW & SE corners with lat/lon like we do for nodes, etc

### Revision 1732 - Directory Listing
* Modified Mon Jun 1 12:21:00 2015 EDT (21 months ago) by martzth

Added error checking and updated instructions

### Revision 1723 - Directory Listing
* Modified Thu May 28 16:40:57 2015 EDT (21 months ago) by martzth

Massive changes adding ability to define deposit area and deposit code through websocket to SDW

### Revision 1716 - Directory Listing
* Modified Wed May 27 15:49:54 2015 EDT (21 months, 1 week ago) by martzth

Updated with new info_type field to match the schema

### Revision 1711 - Directory Listing
* Modified Tue May 26 15:24:50 2015 EDT (21 months, 1 week ago) by martzth

Fixed response call

### Revision 1708 - Directory Listing
* Modified Thu May 21 14:43:18 2015 EDT (21 months, 1 week ago) by martzth

Changed datetime picker to have month and day

### Revision 1706 - Directory Listing
* Modified Tue May 19 14:11:13 2015 EDT (21 months, 2 weeks ago) by martzth

Changed circle rotation and fixed heading persistence

### Revision 1705 - Directory Listing
* Modified Tue May 19 12:55:41 2015 EDT (21 months, 2 weeks ago) by martzth

Added elevation and allowed extent data to persist

### Revision 1704 - Directory Listing
* Modified Mon May 18 16:18:30 2015 EDT (21 months, 2 weeks ago) by martzth

Fixed the save and load functionality

### Revision 1703 - Directory Listing
* Modified Mon May 18 14:39:51 2015 EDT (21 months, 2 weeks ago) by martzth

Finished persisting data and added data layer for deposit

### Revision 1702 - Directory Listing
* Modified Fri May 15 17:36:46 2015 EDT (21 months, 2 weeks ago) by martzth

Updated the data persistence and changed the way the marker placement occurs

### Revision 1699 - Directory Listing
* Modified Tue May 12 16:43:41 2015 EDT (21 months, 3 weeks ago) by martzth

Updated some of the code regarding the sidebars

### Revision 1698 - Directory Listing
Added Mon May 11 18:13:26 2015 EDT (21 months, 3 weeks ago) by martzth

Initial Commit x2

