package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexController {
    final private QuestionRepository questionRepository;

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
        questions.forEach(Question::calculateVotes);
        questions.forEach(Question::formatCreationDateTime);

        model.put("questions", questions);
        model.put("authorized", (user != null));
        return "index";
    }
}
