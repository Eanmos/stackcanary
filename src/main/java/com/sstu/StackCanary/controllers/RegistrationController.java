package com.sstu.StackCanary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @GetMapping("/registration")
    public String main(Map<String, Object> model) {
        return "registration";
    }
}
