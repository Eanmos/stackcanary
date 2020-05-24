package com.sstu.StackCanary.controllers;

import com.sstu.StackCanary.domain.Role;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String main(Map<String, Object> model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
