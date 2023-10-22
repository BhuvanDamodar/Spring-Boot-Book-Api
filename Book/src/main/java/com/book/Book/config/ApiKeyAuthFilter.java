package com.book.Book.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String principleRequestHeader;

    public ApiKeyAuthFilter(String principleRequestHeader){
        this.principleRequestHeader = principleRequestHeader;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(principleRequestHeader);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
