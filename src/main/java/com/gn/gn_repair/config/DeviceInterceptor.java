package com.gn.gn_repair.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DeviceInterceptor implements HandlerInterceptor {

    private static final String HEADER = "User-Agent";

    private static final String MOBILE_REGEX = ".*(Mobile|Android|iPhone|iPad).*";

    private static final String KEY = "deviceView";
    private static final String MOBILE_PATH = "/mobile/home";
    private static final String PC_PATH = "/pc/home";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String device = getDeviceInfo(request);

        if (device != null && isMobile(device)) {
            request.setAttribute(KEY, MOBILE_PATH);
            return true;
        }

        request.setAttribute(KEY, PC_PATH);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) {
        if (modelAndView != null) {
            String deviceView = (String) request.getAttribute(KEY);
            modelAndView.setViewName(deviceView);  // Interceptor 에서 뷰 변경
        }
    }

    private String getDeviceInfo(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    private boolean isMobile(String userAgent) {
        return userAgent.matches(MOBILE_REGEX);
    }
}
