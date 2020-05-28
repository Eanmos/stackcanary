package com.sstu.StackCanary.controllers;

import com.sstu.StackCanary.domain.Answer;
import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.AnswerRepository;
import com.sstu.StackCanary.repositories.QuestionRepository;
import com.sstu.StackCanary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class VoteUpForAnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/voteUpForAnswer")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam Integer questionId,
                       @RequestParam Integer answerId,
                       Map<String, Object> model) {
        // Assuming that the question with given ID
        // always exists.
        Answer answer = answerRepository.findById(answerId).get();

        answer.votedUpByUsers.add(user);
        answer.votedDownByUsers.remove(user);

        user.voteUpForAnswer(answer);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }
}