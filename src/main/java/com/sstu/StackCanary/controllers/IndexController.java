package com.sstu.StackCanary.controllers;

import java.util.Map;

import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Question> questions = questionRepository.findAll();

        for (Question q : questions) {
            q.calculateVotes();
            q.formatCreationDateTime();
        }

        model.put("questions", questions);

        return "index";
    }
}
