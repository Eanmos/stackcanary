package com.sstu.StackCanary.controllers;

import java.util.*;

import com.sstu.StackCanary.domain.Question;
import com.sstu.StackCanary.domain.Tag;
import com.sstu.StackCanary.domain.User;
import com.sstu.StackCanary.repositories.QuestionRepository;
import com.sstu.StackCanary.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AskQuestionPageController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

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
        Question q = new Question(user, title, body, tags);
        questionRepository.save(q);

        // Redirect to the new question's page.
        return "redirect:/q?id=" + q.getId();
    }
}
