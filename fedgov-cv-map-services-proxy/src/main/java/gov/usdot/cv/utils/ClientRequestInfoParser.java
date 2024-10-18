package gov.usdot.cv.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientRequestInfoParser {
    private static final Logger logger = LogManager.getLogger(ClientRequestInfoParser.class);
    private HttpServletRequest request;


    public ClientRequestInfoParser(HttpServletRequest request) {
        this.request = request;
    }    

    public String getClientIPAddress(){
        String ipAddress = this.request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = this.request.getRemoteAddr();
        }
        return ipAddress;
    }
}
