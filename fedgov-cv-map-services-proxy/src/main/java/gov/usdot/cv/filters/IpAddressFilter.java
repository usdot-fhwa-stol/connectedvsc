package gov.usdot.cv.filters;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import gov.usdot.cv.utils.ClientRequestInfoParser;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
public class IpAddressFilter implements Filter{
    private static final Logger logger = LogManager.getLogger(IpAddressFilter.class);
    @Override
    public void init(FilterConfig filterConfig){
        logger.info("IpAddressFilter init");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest)request;
        String ipAddress = new ClientRequestInfoParser(req).getClientIPAddress();
        logger.info("Client IP {}", ipAddress);
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        logger.info("IpAddressFilter destroy");
    }
}
