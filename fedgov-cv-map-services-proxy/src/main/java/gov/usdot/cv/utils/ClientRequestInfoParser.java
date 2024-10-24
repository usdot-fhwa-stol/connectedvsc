package gov.usdot.cv.utils;

import javax.servlet.http.HttpServletRequest;

public class ClientRequestInfoParser {
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
