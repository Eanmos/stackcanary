package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Role;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.repositories.UserRepository;
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
    public String registerUser(User user, Map<String, Object> model) {
        if (userWithThisUsernameAlreadyExists(user)) {
            model.put("userWithThisUsernameAlreadyExistsMessage", "User with this username already exists.");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }

    private boolean userWithThisUsernameAlreadyExists(User u) {
        return userRepository.findByUsername(u.getUsername()) != null;
    }
}
