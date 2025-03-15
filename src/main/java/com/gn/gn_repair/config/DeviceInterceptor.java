package com.gn.gn_repair.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class DeviceInterceptor implements HandlerInterceptor {

    private static final String HEADER = "User-Agent";
    private static final String MOBILE_REGEX = ".*(Mobile|Android|iPhone|iPad).*";

    private static final String KEY = "pathPrefix";
    private static final String MOBILE_PATH_PREFIX = "mobile/";
    private static final String PC_PATH_PREFIX = "pc/";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        log.info("deviceInterceptor preHandle called");
        String device = getDeviceInfo(request);

        if (device != null && isMobile(device)) {
            request.setAttribute(KEY, MOBILE_PATH_PREFIX);
            log.info("모바일 기기 감지: 모바일 템플릿 사용");
            return true;
        }

        request.setAttribute(KEY, PC_PATH_PREFIX);
        log.info("PC 기기 감지: PC 템플릿 사용");
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
            String devicePathPrefix = (String) request.getAttribute(KEY);
            String originalViewPath = modelAndView.getViewName();

            if (originalViewPath != null && !originalViewPath.startsWith("redirect:")
                    && !originalViewPath.startsWith("forward:")) {
                String updatedViewName = devicePathPrefix + originalViewPath;
                modelAndView.setViewName(updatedViewName);

                log.info("[Interceptor] 뷰 path 변경: {} → {}", originalViewPath, updatedViewName);
            }
        }
    }

    private String getDeviceInfo(HttpServletRequest request) {
        return request.getHeader(HEADER);
    }

    private boolean isMobile(String userAgent) {
        return userAgent.matches(MOBILE_REGEX);
    }
}
