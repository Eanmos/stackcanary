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
public class IndexController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                       Map<String, Object> model) {
        Iterable<Question> questions = questionRepository.findAll();

        // Prepare transient fields
        //
        // — formattedCreationDateTime
        // — votes
        //
        // that will be used in the template.
        for (Question q : questions) {
            q.calculateVotes();
            q.formatCreationDateTime();
        }

        model.put("questions", questions);
        model.put("authorized", (user != null));
        return "index";
    }
}
