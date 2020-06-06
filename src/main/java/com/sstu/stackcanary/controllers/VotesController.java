package com.sstu.stackcanary.controllers;

import com.sstu.stackcanary.domain.Answer;
import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.repositories.AnswerRepository;
import com.sstu.stackcanary.repositories.QuestionRepository;
import com.sstu.stackcanary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VotesController {
    final private AnswerRepository answerRepository;
    final private QuestionRepository questionRepository;
    final private UserRepository userRepository;

    @PostMapping("/voteUpForAnswer")
    public String voteUpForAnswer(@AuthenticationPrincipal User user,
                                  @RequestParam Integer questionId,
                                  @RequestParam Integer answerId,
                                  Map<String, Object> model) {
        Answer answer = answerRepository.findById(answerId).get();

        answer.votedUpByUsers.add(user);
        answer.votedDownByUsers.remove(user);

        user.voteUpForAnswer(answer);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/undoVoteUpForAnswer")
    public String undoVoteUpForAnswer(@AuthenticationPrincipal User user,
                                      @RequestParam Integer questionId,
                                      @RequestParam Integer answerId,
                                      Map<String, Object> model) {
        Answer answer = answerRepository.findById(answerId).get();

        answer.votedUpByUsers.remove(user);
        user.getVotedUpAnswers().remove(answer);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/voteDownForAnswer")
    public String voteDownForAnswer(@AuthenticationPrincipal User user,
                                    @RequestParam Integer questionId,
                                    @RequestParam Integer answerId,
                                    Map<String, Object> model) {
        Answer answer = answerRepository.findById(answerId).get();

        answer.votedDownByUsers.add(user);
        answer.votedUpByUsers.remove(user);

        user.voteDownForAnswer(answer);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/undoVoteDownForAnswer")
    public String undoVoteDownForAnswer(@AuthenticationPrincipal User user,
                                        @RequestParam Integer questionId,
                                        @RequestParam Integer answerId,
                                        Map<String, Object> model) {
        Answer answer = answerRepository.findById(answerId).get();

        answer.votedDownByUsers.remove(user);
        user.getVotedDownAnswers().remove(answer);

        answerRepository.save(answer);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/voteUpForQuestion")
    public String voteUpForQuestion(@AuthenticationPrincipal User user,
                                    @RequestParam Integer questionId,
                                    Map<String, Object> model) {
        Question question = questionRepository.findById(questionId).get();

        question.votedUpByUsers.add(user);
        question.votedDownByUsers.remove(user);

        user.voteUpForQuestion(question);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/undoVoteUpForQuestion")
    public String undoVoteUpForQuestion(@AuthenticationPrincipal User user,
                                        @RequestParam Integer questionId,
                                        Map<String, Object> model) {
        Question question = questionRepository.findById(questionId).get();

        question.votedUpByUsers.remove(user);
        user.getVotedUpQuestions().remove(question);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/voteDownForQuestion")
    public String voteDownForQuestion(@AuthenticationPrincipal User user,
                                      @RequestParam Integer questionId,
                                      Map<String, Object> model) {
        Question question = questionRepository.findById(questionId).get();

        question.votedDownByUsers.add(user);
        question.votedUpByUsers.remove(user);

        user.voteDownForQuestion(question);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }

    @PostMapping("/undoVoteDownForQuestion")
    public String undoVoteDownForQuestion(@AuthenticationPrincipal User user,
                                          @RequestParam Integer questionId,
                                          Map<String, Object> model) {
        Question question = questionRepository.findById(questionId).get();

        question.votedDownByUsers.remove(user);
        user.getVotedDownQuestions().remove(question);

        questionRepository.save(question);
        userRepository.save(user);

        return "redirect:/q?id=" + questionId;
    }
}