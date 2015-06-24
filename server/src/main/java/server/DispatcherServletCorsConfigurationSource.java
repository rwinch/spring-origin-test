package server;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

/**
 * This is a terrible implementation but it should get the idea across. My
 * thoughts on how it would actually be implemented:
 *
 * <ul>
 * <li>DispatcherServlet could implement CorsConfigurationSource</li>
 * <li>The HandlerExecutionChain would optionally implement CorsConfigurationSource</li>
 * <li>
 * DispatcherServlet would lookup the HandlerExecutionChain, if it implemented
 * CorsConfigurationSource it would cast it and return the result of
 * delegating to it
 * </li>
 * </ul>
 *
 *
 * @author Rob Winch
 *
 */
@Component
public class DispatcherServletCorsConfigurationSource implements CorsConfigurationSource {

    DispatcherServlet dispatcherServlet;

    @Autowired
    public DispatcherServletCorsConfigurationSource(
            DispatcherServlet dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        if(!CorsUtils.isCorsRequest(request)) {
            return null;
        }
        try {
            HandlerExecutionChain chain = getHandler(request);
            return getCorsConfiguration(chain);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CorsConfiguration getCorsConfiguration(HandlerExecutionChain chain) {
        for(HandlerInterceptor interceptor : chain.getInterceptors()) {
            CorsConfiguration result = getCorsField(interceptor);
            if(result != null) {
                return result;
            }
        }
        Object handler = chain.getHandler();

        return getCorsField(handler);
    }

    private CorsConfiguration getCorsField(Object object) {
        Field corsConfig = ReflectionUtils.findField(object.getClass(), "config");
        if(corsConfig != null) {
            ReflectionUtils.makeAccessible(corsConfig);
            return (CorsConfiguration) ReflectionUtils.getField(corsConfig, object);
        }
        return null;
    }

    private HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        Field field = ReflectionUtils.findField(DispatcherServlet.class, "handlerMappings");
        ReflectionUtils.makeAccessible(field);
        List<HandlerMapping> handlerMappings = (List<HandlerMapping>) ReflectionUtils.getField(field, dispatcherServlet);
        for (HandlerMapping hm : handlerMappings) {
            HandlerExecutionChain handler = hm.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }
}
