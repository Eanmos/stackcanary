package com.sstu.StackCanary.controllers;

import com.sstu.StackCanary.domain.Answer;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.AnswerRepository;
import com.sstu.StackCanary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UndoVoteUpForAnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/undoVoteUpForAnswer")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam Integer answerId,
                       Map<String, Object> model) {
        // Assuming that the answer with given ID
        // always exists.
        Answer answer = answerRepository.findById(answerId).get();

        answer.votedUpByUsers.remove(user);
        user.getVotedUpAnswers().remove(answer);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + answerId;
    }
}