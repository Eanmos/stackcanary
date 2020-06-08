package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.domain.Vote;
import com.sstu.stackcanary.repositories.QuestionRepository;
import com.sstu.stackcanary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class VoteForQuestionController {
    final private QuestionRepository questionRepository;
    final private UserRepository userRepository;

    @PostMapping("/voteUpForQuestion")
    public String voteUpForQuestion(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        return voteForQuestion(user, id, Vote.UP);
    }

    @PostMapping("/voteDownForQuestion")
    public String voteDownForQuestion(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        return voteForQuestion(user, id, Vote.DOWN);
    }

    @PostMapping("/undoVoteForQuestion")
    public String undoVoteForQuestion(@AuthenticationPrincipal User user, @RequestParam Integer id) {
        return voteForQuestion(user, id, Vote.NONE);
    }

    private String voteForQuestion(User user, Integer id, Vote vote) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if (!questionOptional.isPresent())
            return "redirect:/404";

        Question question = questionOptional.get();

        question.voteByUser(user, vote);
        user.voteForQuestion(question, vote);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + id;
    }
}
