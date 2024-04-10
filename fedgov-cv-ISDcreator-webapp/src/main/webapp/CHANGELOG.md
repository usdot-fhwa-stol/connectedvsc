# HEAD (MARCH 3, 2017)

### Revision 2224 - Directory Listing
* Modified Thu Mar 2 15:38:41 2017 EST (21 hours, 58 minutes ago) by martzth

Changed zoom level array to be slightly better.

### Revision 2221 - Directory Listing
* Modified Thu Mar 2 13:44:54 2017 EST (23 hours, 52 minutes ago) by martzth

Added zoom to cookie. Changed a field name for encoder. Cleaned up some code and began adding comments.

### Revision 2214 - Directory Listing
* Modified Tue Feb 28 15:57:55 2017 EST (2 days, 21 hours ago) by martzth

Fixed tile age issue and minor visual.

### Revision 2211 - Directory Listing
* Modified Tue Feb 28 13:39:45 2017 EST (2 days, 23 hours ago) by martzth

Made search bar work - https vs http. Also got additional zoom levels.

### Revision 2209 - Directory Listing
* Modified Tue Feb 28 12:31:47 2017 EST (3 days, 1 hour ago) by martzth

Added geocomplete for better location searching.

### Revision 2203 - Directory Listing
* Modified Sun Feb 26 12:13:17 2017 EST (5 days, 1 hour ago) by kuninl

Update to Java 1.8

### Revision 2191 - Directory Listing
* Modified Wed Feb 22 13:13:23 2017 EST (9 days ago) by martzth

Added tile age to ISD map.

### Revision 2189 - Directory Listing
* Modified Wed Feb 22 11:31:31 2017 EST (9 days, 2 hours ago) by martzth

Added https to bing call and added deposit options to index. Also updated version.

### Revision 2186 - Directory Listing
* Modified Thu Feb 16 12:29:39 2017 EST (2 weeks, 1 day ago) by martzth

Fixed CORS issue for location finder - jsonp.

### Revision 2184 - Directory Listing
* Modified Thu Feb 16 12:18:39 2017 EST (2 weeks, 1 day ago) by martzth

Made several updates including making the layer selector more visible, adding search of streets, storing center point location as a cookie, and updating the depositor

### Revision 2180 - Directory Listing
* Modified Mon Feb 6 11:42:16 2017 EST (3 weeks, 4 days ago) by hamiltoneri

Updated Versioning text in index.html

### Revision 2179 - Directory Listing
* Modified Mon Feb 6 10:36:51 2017 EST (3 weeks, 4 days ago) by martzth

Fixed issue where temp stored values were overwriting fields on "done" click

### Revision 2159 - Directory Listing
* Modified Wed Jan 18 08:46:48 2017 EST (6 weeks, 2 days ago) by hamiltoneri

Added jetty-env.xml to WEB-INF to ensure proper class loading when run on jetty server
Locked Jetty's dependency versions in poms to match production server to avoid issues with class loading

### Revision 2145 - Directory Listing
* Modified Mon Dec 19 14:47:58 2016 EST (2 months, 1 week ago) by martzth

Chnaged velocity to include floats

### Revision 2142 - Directory Listing
* Modified Thu Dec 15 15:26:43 2016 EST (2 months, 2 weeks ago) by kuninl

Updated version and release date

### Revision 2140 - Directory Listing
* Modified Thu Dec 15 11:31:27 2016 EST (2 months, 2 weeks ago) by martzth

Added validation to the lane connection boxes. Added laneConnections removal when builder is closed.

### Revision 2139 - Directory Listing
* Modified Thu Dec 15 11:18:57 2016 EST (2 months, 2 weeks ago) by martzth

Added sheepit to allow for multiple speed limit types to be entered using the same form

### Revision 2138 - Directory Listing
* Modified Tue Dec 13 14:35:24 2016 EST (2 months, 2 weeks ago) by kuninl

Updated version and release date

### Revision 2137 - Directory Listing
* Modified Tue Dec 13 13:23:25 2016 EST (2 months, 2 weeks ago) by martzth

Fixed issue with approach name

### Revision 2135 - Directory Listing
* Modified Mon Dec 12 17:33:48 2016 EST (2 months, 2 weeks ago) by martzth

Fixed approach bug where numbers were being removed from dropdown

### Revision 2134 - Directory Listing
* Modified Mon Dec 12 15:54:53 2016 EST (2 months, 2 weeks ago) by martzth

Fixed encoder not loading. Fixed layerID not being editable on child node.

### Revision 2133 - Directory Listing
* Modified Mon Dec 12 13:20:36 2016 EST (2 months, 2 weeks ago) by martzth

Changed the table to have it be 3 columns instead of 4. Moved the layer ID to the reference point

### Revision 2132 - Directory Listing
* Modified Fri Dec 9 16:11:52 2016 EST (2 months, 3 weeks ago) by kleinke1

added lane connection visualization. added semicolon

### Revision 2131 - Directory Listing
* Modified Fri Dec 9 15:16:42 2016 EST (2 months, 3 weeks ago) by kleinke1

Added connections box

### Revision 2128 - Directory Listing
* Modified Wed Dec 7 16:03:07 2016 EST (2 months, 3 weeks ago) by martzth

Added section for ingress/egress table

### Revision 2113 - Directory Listing
* Modified Wed Nov 30 16:04:42 2016 EST (3 months ago) by martzth

removed bug where driving lanes would include all driving lanes, not just those listed in an approach. Cleared array and added temp counter to allow for appropriate placement within array (removes nulls)

### Revision 2112 - Directory Listing
* Modified Tue Nov 29 16:12:57 2016 EST (3 months ago) by martzth

updated key

### Revision 2107 - Directory Listing
* Modified Mon Nov 28 13:09:04 2016 EST (3 months ago) by martzth

Fixed bug that wouldn't allow lane width to go negative

### Revision 2085 - Directory Listing
* Modified Wed Nov 16 10:06:49 2016 EST (3 months, 2 weeks ago) by martzth

Removed direction 

### Revision 2084 - Directory Listing
* Modified Wed Nov 16 09:51:56 2016 EST (3 months, 2 weeks ago) by martzth

Moved the update elevation call to the correct layer and had to add a new method for it

### Revision 2082 - Directory Listing
* Modified Tue Nov 15 18:20:47 2016 EST (3 months, 2 weeks ago) by kuninl

Updated version information

### Revision 2079 - Directory Listing
* Modified Tue Nov 15 13:55:26 2016 EST (3 months, 2 weeks ago) by martzth

Elevation call for all types, no error on shared with, message encoding type 

### Revision 2078 - Directory Listing
* Modified Tue Nov 15 12:30:37 2016 EST (3 months, 2 weeks ago) by kuninl

Changed lane width description

### Revision 2076 - Directory Listing
* Modified Mon Nov 14 14:05:13 2016 EST (3 months, 2 weeks ago) by martzth

Fixed parsleyjs not resetting after field was fixed

### Revision 2075 - Directory Listing
* Modified Mon Nov 14 14:02:30 2016 EST (3 months, 2 weeks ago) by martzth

Fixed the depositor to deal with elevation on each node

### Revision 2074 - Directory Listing
* Modified Mon Nov 14 14:01:23 2016 EST (3 months, 2 weeks ago) by martzth

Fixed the config for lane width delta and changed elev_delta to just elev

### Revision 2072 - Directory Listing
* Modified Thu Nov 10 10:30:51 2016 EST (3 months, 3 weeks ago) by martzth

Added elevation and lane width delta to each node

### Revision 2071 - Directory Listing
* Modified Thu Nov 10 09:02:43 2016 EST (3 months, 3 weeks ago) by martzth

Removed some deprecated validation code, changed the way the encoder handled the lane attribute array, changed the lane width to lane width delta

### Revision 2070 - Directory Listing
* Modified Mon Nov 7 17:02:58 2016 EST (3 months, 3 weeks ago) by martzth

Changed threshold for verified points.

### Revision 2065 - Directory Listing
* Modified Mon Oct 31 15:28:37 2016 EDT (4 months ago) by martzth

Fixed a logic error with validation

### Revision 2064 - Directory Listing
* Modified Mon Oct 31 14:14:10 2016 EDT (4 months ago) by martzth

Added validation for most fields - checking for required, numeric values, and ranges

### Revision 2062 - Directory Listing
* Modified Tue Oct 25 18:32:30 2016 EDT (4 months, 1 week ago) by kuninl

Minor changes to switch BER to UPER in dialogs and help, etc. Also added simple About and Contact alert based popup.

### Revision 2061 - Directory Listing
* Modified Tue Oct 25 16:30:41 2016 EDT (4 months, 1 week ago) by martzth

Change to ensure shared with and type attributes array's loaded correctly

### Revision 2059 - Directory Listing
* Modified Tue Oct 25 13:09:40 2016 EDT (4 months, 1 week ago) by martzth

Added check for state confidence to prevent breaking of encoder if empty

### Revision 2058 - Directory Listing
* Modified Tue Oct 25 10:16:08 2016 EDT (4 months, 1 week ago) by martzth

Fixed issue causing encoder to not load

### Revision 2056 - Directory Listing
* Modified Mon Oct 24 15:54:31 2016 EDT (4 months, 1 week ago) by martzth

Added tooltips for verified point, moved layer ID

### Revision 2055 - Directory Listing
* Modified Mon Oct 24 15:44:29 2016 EDT (4 months, 1 week ago) by kuninl

Added missing descriptions and changed title for Signal Group ID

### Revision 2054 - Directory Listing
* Modified Wed Oct 19 14:48:59 2016 EDT (4 months, 1 week ago) by martzth

Massive commit - added help notes for each field using a config file, changed speed limit field usage, added child features to reference point, added placeholders and tooltips

### Revision 2053 - Directory Listing
* Modified Tue Oct 18 12:05:04 2016 EDT (4 months, 2 weeks ago) by martzth

Changed ISD lane attribute images

### Revision 2048 - Directory Listing
* Modified Mon Oct 10 16:01:39 2016 EDT (4 months, 3 weeks ago) by kuninl

Renamed the log file

### Revision 2046 - Directory Listing
* Modified Mon Oct 10 10:37:27 2016 EDT (4 months, 3 weeks ago) by martzth

Got rid of disabled field for SPaT ID and fixed the encoding for typeAttribute

### Revision 2044 - Directory Listing
* Modified Fri Oct 7 16:37:27 2016 EDT (4 months, 3 weeks ago) by martzth

Added spat ### Revision number and the vehicle lane type attribute as an array of numbers using the multiselect tool

### Revision 2036 - Directory Listing
* Modified Wed Oct 5 11:32:12 2016 EDT (4 months, 3 weeks ago) by martzth

Made the confidence menu scrollable

### Revision 2035 - Directory Listing
* Modified Wed Oct 5 11:29:47 2016 EDT (4 months, 3 weeks ago) by martzth

Added bootstrap multiselect to allow for multiple "shared with" options. Also added back the phase field. Adjusted the intersectionID field to produce the appropriate range.

### Revision 2029 - Directory Listing
* Modified Mon Oct 3 14:37:15 2016 EDT (4 months, 4 weeks ago) by martzth

Added additional elements for the new 2016 spec. Changed SPaT. Fixed graphic issues by placing everything in bootstrap rows.

### Revision 2028 - Directory Listing
* Modified Fri Sep 30 15:10:45 2016 EDT (5 months ago) by kuninl

Fixed CAS problem by changing the server name

### Revision 2019 - Directory Listing
* Modified Tue Sep 27 10:26:46 2016 EDT (5 months ago) by hamiltoneri

* Modified CAS endpoints to use DNS

### Revision 2009 - Directory Listing
* Modified Wed Sep 14 10:35:21 2016 EDT (5 months, 2 weeks ago) by martzth

Fixed display bugs

### Revision 2006 - Directory Listing
* Modified Thu Sep 8 13:43:55 2016 EDT (5 months, 3 weeks ago) by martzth

Changed some fields to support j2735 spec better and styled the front end specifically for chrome

### Revision 2001 - Directory Listing
* Modified Thu Sep 1 14:41:31 2016 EDT (6 months ago) by martzth

Upgrades to mapping application for new j2735 standard

### Revision 1809 - Directory Listing
* Modified Mon Jul 27 11:52:06 2015 EDT (19 months, 1 week ago) by martzth

Changed the way the ID's are calculated

### Revision 1801 - Directory Listing
* Modified Wed Jul 15 10:28:43 2015 EDT (19 months, 2 weeks ago) by martzth

Added validation and the new ITIS content

### Revision 1796 - Directory Listing
* Modified Fri Jul 10 13:48:20 2015 EDT (19 months, 3 weeks ago) by martzth

Changed the id formula to fit within bounds of the spec

### Revision 1795 - Directory Listing
* Modified Fri Jul 10 12:53:58 2015 EDT (19 months, 3 weeks ago) by martzth

Added new menus and SPaT

### Revision 1771 - Directory Listing
* Modified Thu Jun 18 12:46:18 2015 EDT (20 months, 2 weeks ago) by martzth

Updates to parent/child save and load functions

### Revision 1765 - Directory Listing
* Modified Wed Jun 17 16:53:46 2015 EDT (20 months, 2 weeks ago) by martzth

Added configuration for parent/child maps

### Revision 1717 - Directory Listing
* Modified Wed May 27 16:44:09 2015 EDT (21 months, 1 week ago) by martzth

Fixed click issue on load of files (well, a hacky fix anyway...)

### Revision 1714 - Directory Listing
* Modified Wed May 27 15:29:19 2015 EDT (21 months, 1 week ago) by martzth

Updated web.xml

### Revision 1713 - Directory Listing
* Modified Wed May 27 15:26:52 2015 EDT (21 months, 1 week ago) by martzth

Fixed logic error with lane counts in boxes

### Revision 1700 - Directory Listing
* Modified Wed May 13 13:09:39 2015 EDT (21 months, 3 weeks ago) by martzth

Fixed the persisting error message on the reference node

### Revision 1695 - Directory Listing
* Modified Fri May 8 14:10:06 2015 EDT (21 months, 3 weeks ago) by martzth

Added feedback link

### Revision 1692 - Directory Listing
* Modified Wed May 6 12:55:56 2015 EDT (21 months, 4 weeks ago) by martzth

Fixed the ajax response for encoding, changed the title to ISD, and removed unnecessary images

### Revision 1691 - Directory Listing
* Modified Tue May 5 10:06:45 2015 EDT (21 months, 4 weeks ago) by martzth

Changed the instructions and the display of buttons when control is selected

### Revision 1687 - Directory Listing
* Modified Fri May 1 12:04:31 2015 EDT (22 months ago) by martzth

Jamal told me too

### Revision 1686 - Directory Listing
* Modified Fri May 1 12:04:07 2015 EDT (22 months ago) by martzth

Jamal told me too

### Revision 1685 - Directory Listing
* Modified Fri May 1 12:03:06 2015 EDT (22 months ago) by martzth
Copied from: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp ### Revision 1684

### Revision 1684 - Directory Listing
* Modified Fri May 1 11:57:25 2015 EDT (22 months ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Renamed project

### Revision 1683 - Directory Listing
* Modified Fri May 1 09:49:08 2015 EDT (22 months ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Updated the instructions for the new buttons

### Revision 1681 - Directory Listing
* Modified Thu Apr 30 14:58:24 2015 EDT (22 months ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Added tooltips to the buttons

### Revision 1680 - Directory Listing
* Modified Thu Apr 30 11:38:07 2015 EDT (22 months ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Made error checking more robust

### Revision 1679 - Directory Listing
* Modified Thu Apr 30 11:26:26 2015 EDT (22 months ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Added error checking to lat/long

### Revision 1678 - Directory Listing
* Modified Wed Apr 29 16:27:55 2015 EDT (22 months ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Swapped the bootstrap switches for a more sleek button look

### Revision 1675 - Directory Listing
* Modified Mon Apr 20 13:53:20 2015 EDT (22 months, 1 week ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Removed websocket dependency.  Added control toolbar prototype. 

### Revision 1674 - Directory Listing
* Modified Mon Apr 20 13:28:34 2015 EDT (22 months, 1 week ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Fixed bug with dragging verified/reference point and having the incorrect window update. Also added ASN.1 readable text to depositor

### Revision 1669 - Directory Listing
* Modified Fri Apr 17 13:32:04 2015 EDT (22 months, 2 weeks ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Added warning labels to deposit panel to help prevent bad messages

### Revision 1668 - Directory Listing
* Modified Fri Apr 17 12:13:42 2015 EDT (22 months, 2 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

renamed Intersection Builder to Builder

### Revision 1667 - Directory Listing
* Modified Thu Apr 16 16:43:20 2015 EDT (22 months, 2 weeks ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Updated change functions from 'on' to 'one' to prevent event propagation

### Revision 1666 - Directory Listing
* Modified Thu Apr 16 15:28:07 2015 EDT (22 months, 2 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

added exception check for saving file via the downloadlink.click for IE

### Revision 1665 - Directory Listing
* Modified Thu Apr 16 15:26:08 2015 EDT (22 months, 2 weeks ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Added a re-centering function for loaded maps

### Revision 1664 - Directory Listing
* Modified Thu Apr 16 14:07:39 2015 EDT (22 months, 2 weeks ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Added confirm and map clearance when loading new file

### Revision 1662 - Directory Listing
* Modified Wed Apr 15 16:32:15 2015 EDT (22 months, 2 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

removed target

### Revision 1661 - Directory Listing
* Modified Wed Apr 15 16:29:46 2015 EDT (22 months, 2 weeks ago) by martzth
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Added functionality for loading map in IE 

### Revision 1658 - Directory Listing
* Modified Wed Apr 15 10:58:26 2015 EDT (22 months, 2 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

attributes stored in an array

### Revision 1656 - Directory Listing
* Modified Mon Apr 13 13:56:30 2015 EDT (22 months, 2 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

fixed issue with IE save.  Fixed issue with wrong lat/long values on markers when generating json to be deposited. 

### Revision 1655 - Directory Listing
* Modified Fri Apr 10 12:37:16 2015 EDT (22 months, 3 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Created Instructions modal

### Revision 1654 - Directory Listing
* Modified Wed Apr 8 12:42:14 2015 EDT (22 months, 3 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

summed attribute value now available for depositor

### Revision 1653 - Directory Listing
* Modified Wed Apr 8 11:49:42 2015 EDT (22 months, 3 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

verified survey point for marker is now initialized on placement

### Revision 1652 - Directory Listing
* Modified Mon Apr 6 11:57:52 2015 EDT (22 months, 3 weeks ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

added click functionality to drag handler (for markers)

### Revision 1650 - Directory Listing
* Modified Thu Apr 2 14:39:05 2015 EDT (23 months ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

updated ajax urls to call backend when deployed on server (to avoid http/https mixing issue)

### Revision 1648 - Directory Listing
Added Thu Apr 2 11:59:13 2015 EDT (23 months ago) by lewisstet
Original Path: digitaledge-plugins/trunk/fedgov-cv-TIMcreator-webapp

Removed old message deposit window.  Fixed display of attribute window on startup.  Moved deposit into the tools dropdown.  Changed name of built war to 'isd'


