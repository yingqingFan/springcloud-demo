package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @RequestMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("name", "Dear");
        return "hello";
    }
}
