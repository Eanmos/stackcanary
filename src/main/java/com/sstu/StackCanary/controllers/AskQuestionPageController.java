package com.sstu.StackCanary.controllers;

import java.util.Map;

import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AskQuestionPageController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/askQuestion")
    public String main(@AuthenticationPrincipal User user,
                       Map<String, Object> model) {
        model.put("authorizedUser", user);
        return "askQuestion";
    }
}
