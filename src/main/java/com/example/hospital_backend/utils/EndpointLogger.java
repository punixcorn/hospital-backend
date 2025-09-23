package com.example.hospital_backend.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.util.Map;
@Component
public class EndpointLogger implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent event) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = 
            requestMappingHandlerMapping.getHandlerMethods();
        
        handlerMethods.forEach((mapping, method) -> {
            System.out.println(mapping + " -> " + method);
        });
    }
}