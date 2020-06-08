package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Answer;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.domain.Vote;
import com.sstu.stackcanary.repositories.AnswerRepository;
import com.sstu.stackcanary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class VoteForAnswerController {
    final private AnswerRepository answerRepository;
    final private UserRepository userRepository;

    @PostMapping("/voteUpForAnswer")
    public String voteUpForAnswer(@AuthenticationPrincipal User user,
                                  @RequestParam Integer questionId,
                                  @RequestParam Integer answerId) {
        return voteForAnswer(user, questionId, answerId, Vote.UP);
    }

    @PostMapping("/voteDownForAnswer")
    public String voteDownForAnswer(@AuthenticationPrincipal User user,
                                    @RequestParam Integer questionId,
                                    @RequestParam Integer answerId) {
        return voteForAnswer(user, questionId, answerId, Vote.DOWN);
    }

    @PostMapping("/undoVoteForAnswer")
    public String undoVoteForAnswer(@AuthenticationPrincipal User user,
                                    @RequestParam Integer questionId,
                                    @RequestParam Integer answerId) {
        return voteForAnswer(user, questionId, answerId, Vote.NONE);
    }

    private String voteForAnswer(User user, Integer questionId, Integer answerId, Vote vote) {
        Optional<Answer> answerOptional = answerRepository.findById(answerId);

        if (!answerOptional.isPresent())
            return "redirect:/404";

        Answer answer = answerOptional.get();

        answer.voteByUser(user, vote);
        user.voteForAnswer(answer, vote);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }
}
