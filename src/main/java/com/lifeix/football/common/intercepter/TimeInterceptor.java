package com.lifeix.football.common.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 查看一个Request的请求时间
 */
public class TimeInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);

    private ThreadLocal<StopWatch> threadLocal = new ThreadLocal<StopWatch>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        StopWatch watch = new StopWatch();
        threadLocal.set(watch);
        watch.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView view) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) throws Exception {
        StopWatch watch = threadLocal.get();
        watch.stop();
        logger.info("this request used time :--->" + watch.getLastTaskTimeMillis());
    }

}
