package com.sstu.StackCanary.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nickname;

    @ManyToMany(mappedBy = "votedUpByUser")
    private Set<Question> questionsVotedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUser")
    private Set<Question> questionsVotedDownQuestions;

    @ManyToMany(mappedBy = "votedUpByUser")
    private Set<Question> answersVotedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUser")
    private Set<Question> answersVotedDownQuestions;

    protected User() { }
}
