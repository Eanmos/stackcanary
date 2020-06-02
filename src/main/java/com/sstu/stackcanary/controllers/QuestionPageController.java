package com.sstu.stackcanary.controllers;

import java.util.Map;

import com.sstu.stackcanary.domain.Answer;
import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.repositories.QuestionRepository;
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
        q.setVotedByActiveUser(user);

        // Prepare transient fields of the each answer as well
        // as we have done with the question.
        q.answers.forEach(Answer::formatCreationDateTime);
        q.answers.forEach(Answer::calculateVotes);
        q.answers.forEach(Answer::convertBodyFromMarkdownToHTML);
        q.answers.forEach(a -> a.setVotedByActiveUser(user));

        model.put("question", q);
        model.put("authorized", (user != null));

        return "question";
    }
}
