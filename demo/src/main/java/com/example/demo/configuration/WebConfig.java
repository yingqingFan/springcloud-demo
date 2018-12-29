package com.example.demo.configuration;

import com.example.demo.servlet.MyServlet;
import com.example.demo.servlet.UserPasswordServlet;
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

    @Bean
    public ServletRegistrationBean getStrengthLevel() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new UserPasswordServlet(), "/getStrengthLevel/*");
        return bean;
    }
}
