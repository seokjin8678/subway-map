package com.brainbackdoor.subwaymap.config;

import static org.springframework.http.HttpHeaders.CACHE_CONTROL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.HandlerInterceptor;

public class CacheInterceptor implements HandlerInterceptor {

    private static final String CACHE_HEADER = CacheControl.noCache().cachePrivate().getHeaderValue();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        response.addHeader(CACHE_CONTROL, CACHE_HEADER);
        return true;
    }
}
