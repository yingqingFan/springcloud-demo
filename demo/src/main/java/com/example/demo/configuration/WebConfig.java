package com.example.demo.configuration;

import com.example.demo.servlet.MyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ServletRegistrationBean myServlet1(){
        return new ServletRegistrationBean(new MyServlet(),"/mysev");
    }

//    @Bean
//    public ServletRegistrationBean logout(){
//        return new ServletRegistrationBean(new MyServlet(),"/mysev");
//    }
}
