package com.sstu.StackCanary.controllers;

import java.util.Map;

import com.sstu.StackCanary.domain.Answer;
import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionPageController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/q")
    public String main(@RequestParam Integer id, Map<String, Object> model) {
        Question q = questionRepository.findById(id).get();

        q.formatCreatingDateTime();
        q.calculateVotes();
        q.calculateAnswersCount();
        q.convertBodyFromMarkdownToHTML();

        for (Answer a : q.answers) {
            a.formatCreatingDateTime();
            a.calculateVotes();
            a.convertBodyFromMarkdownToHTML();
        }

        model.put("question", q);

        return "question";
    }
}
