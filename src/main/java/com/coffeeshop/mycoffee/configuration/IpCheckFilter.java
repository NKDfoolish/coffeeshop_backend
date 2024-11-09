package com.coffeeshop.mycoffee.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

@Slf4j
public class IpCheckFilter extends HttpFilter {

//    private static final String IP_RANGE_PATTERN = "^192\\.168\\.187\\.[0-9]{1,3}$";
    private static final String IPV6_RANGE_PATTERN = "^2001:ee0:2d7:aab6:.*$";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String remoteAddr = request.getRemoteAddr();
        if (Pattern.matches(IPV6_RANGE_PATTERN, remoteAddr) || remoteAddr.equals(LOCALHOST_IPV6)) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
