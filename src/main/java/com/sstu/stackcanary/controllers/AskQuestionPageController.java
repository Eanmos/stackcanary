package com.sstu.stackcanary.controllers;

import java.util.*;

import com.sstu.stackcanary.domain.Question;
import com.sstu.stackcanary.domain.Tag;
import com.sstu.stackcanary.domain.User;
import com.sstu.stackcanary.repositories.QuestionRepository;
import com.sstu.stackcanary.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AskQuestionPageController {
    final private QuestionRepository questionRepository;
    final private TagRepository tagRepository;

    @GetMapping("/askQuestion")
    public String main(@AuthenticationPrincipal User user,
                       Map<String, Object> model) {
        model.put("authorizedUser", user);
        return "askQuestion";
    }

    @PostMapping("/askQuestion")
    public String postQuestion(@AuthenticationPrincipal User user,
                               @RequestParam String title,
                               @RequestParam String body,
                               @RequestParam("tag") String [] tagNames,
                               Map<String, Object> model) {
        // Create empty set of tags.
        HashSet<Tag> tags = new HashSet<Tag>();

        // Fill this set with tags with given name from database.
        // If the tag not exist create such new one.
        for (String name : tagNames) {
            Tag tag = tagRepository.findByName(name);

            if (tag == null)
                tag = new Tag(name);

            tagRepository.save(tag);
            tags.add(tag);
        }

        // Create new question and save it in the database.
        Question q = new Question(title, body, new Date(), user, tags);
        questionRepository.save(q);

        // Redirect to the new question's page.
        return "redirect:/q?id=" + q.getId();
    }
}
