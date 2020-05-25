package com.sstu.StackCanary.controllers;

import java.util.Map;

import com.sstu.StackCanary.domain.Answer;
import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionPageController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/q")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam Integer id,
                       Map<String, Object> model) {
        // Assuming that the question with
        // given ID always exists.
        Question q = questionRepository.findById(id).get();

        // Prepare transient fields
        //
        // — formattedCreationDateTime
        // — votes
        // — answersCount
        // — bodyInHTML
        //
        // that will be used in the template.
        q.calculateVotes();
        q.calculateAnswersCount();
        q.formatCreationDateTime();
        q.convertBodyFromMarkdownToHTML();

        // Prepare transient fields of the each answer as well
        // as we have done with the question.
        for (Answer a : q.answers) {
            a.formatCreationDateTime();
            a.calculateVotes();
            a.convertBodyFromMarkdownToHTML();
        }

        model.put("question", q);
        model.put("authorized", (user != null));
        return "question";
    }
}
