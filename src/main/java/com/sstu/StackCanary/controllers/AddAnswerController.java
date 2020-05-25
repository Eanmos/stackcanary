package com.sstu.StackCanary.controllers;

import java.util.*;

import com.sstu.StackCanary.domain.Answer;
import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.AnswerRepository;
import com.sstu.StackCanary.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddAnswerController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("/q")
    public String postQuestion(@AuthenticationPrincipal User user,
                               @RequestParam Integer questionId,
                               @RequestParam String body,
                               Map<String, Object> model) {
        // Assuming that the question with given ID always exists.
        Question q = questionRepository.findById(questionId).get();

        // Add new answer to the database.
        answerRepository.save(new Answer(user, q, body));

        // Redirect to the question page.
        return "redirect:/q?id=" + questionId;
    }
}
