package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.domain.views.QuestionView;
import com.sstu.stackcanary.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class IndexController {
    final private QuestionRepository questionRepository;

    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                       Map<String, Object> model) {
        final Iterable<Question> questions = questionRepository.findAll();
        Set<QuestionView> questionViews = new HashSet<>();

        for (final Question q : questions)
            questionViews.add(new QuestionView(q, user));

        model.put("questions", questionViews);
        model.put("authorized", (user != null));

        return "index";
    }
}
