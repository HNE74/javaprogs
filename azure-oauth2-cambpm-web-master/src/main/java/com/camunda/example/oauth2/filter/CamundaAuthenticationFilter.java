package com.camunda.example.oauth2.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.webapp.impl.security.auth.Authentications;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.camunda.bpm.webapp.impl.security.auth.UserAuthentication;
import org.camunda.bpm.webapp.impl.util.ServletContextUtil;

public class CamundaAuthenticationFilter extends ContainerBasedAuthenticationFilter {

    @Override
    protected String getRequestUri(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        int contextPathLength = contextPath.length();
        if (contextPathLength > 0) {
            requestURI = requestURI.substring(contextPathLength);
        }

        ServletContext servletContext = request.getServletContext();
        String applicationPath = ServletContextUtil.getAppPath(servletContext);
        int applicationPathLength = applicationPath.length();

        if (applicationPathLength > 0 && applicationPathLength < requestURI.length()) {
            requestURI = requestURI.substring(applicationPathLength);
        }
       
        return requestURI;
    }
}
