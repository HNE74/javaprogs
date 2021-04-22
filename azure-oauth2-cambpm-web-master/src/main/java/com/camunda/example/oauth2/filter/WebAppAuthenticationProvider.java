package com.camunda.example.oauth2.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult;
import org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider;
import org.camunda.bpm.webapp.impl.security.auth.Authentications;
import org.camunda.bpm.webapp.impl.security.auth.UserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebAppAuthenticationProvider extends ContainerBasedAuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(WebAppAuthenticationProvider.class.getName());

    @Override
    public AuthenticationResult extractAuthenticatedUser(HttpServletRequest request, ProcessEngine engine) {

        logger.info("++ WebAppAuthenticationProvider.extractAuthenticatedUser()....");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("++ authentication == null...return unsuccessful.");
            return AuthenticationResult.unsuccessful();
        }

        logger.debug("++ authentication IS NOT NULL");
        String name = authentication.getName();
        if (name == null || name.isEmpty()) {
            return AuthenticationResult.unsuccessful();
        }

        logger.debug("++ name = " + name);
        AuthenticationResult authenticationResult = new AuthenticationResult(name, true);
        authenticationResult.setGroups(getUserGroups(authentication));
                
        return authenticationResult;
    }

    private List<String> getUserGroups(Authentication authentication) {

        logger.info("++ WebAppAuthenticationProvider.getUserGroups()....");
        List<String> groupIds;

        groupIds = authentication.getAuthorities().stream()
                .map(res -> res.getAuthority())
                .map(res -> res.substring(5)) // Strip "ROLE_"
                .collect(Collectors.toList());

        logger.debug("++ groupIds = " + groupIds.toString());

        return groupIds;

    }

}