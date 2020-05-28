package com.sstu.StackCanary.controllers;

import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.QuestionRepository;
import com.sstu.StackCanary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class VoteUpForQuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/voteUpForQuestion")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam Integer questionId,
                       Map<String, Object> model) {
        // Assuming that the question with given ID
        // always exists.
        Question question = questionRepository.findById(questionId).get();

        question.votedUpByUsers.add(user);
        question.votedDownByUsers.remove(user);

        user.voteUpForQuestion(question);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }
}