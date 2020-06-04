package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Role;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    final private UserRepository userRepository;

    @GetMapping
    public String main(Map<String, Object> model) {
        return "registration";
    }

    @PostMapping
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
