package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.domain.views.QuestionView;
import com.sstu.stackcanary.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class QuestionPageController {
    final private QuestionRepository questionRepository;

    @GetMapping("/q")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam Integer id,
                       Map<String, Object> model) {
        // Assuming that the question with
        // given ID always exists.
        Question q = questionRepository.findById(id).get();
        QuestionView qv = new QuestionView(q, user);

        model.put("question", qv);
        model.put("authorized", (user != null));

        return "question";
    }
}
