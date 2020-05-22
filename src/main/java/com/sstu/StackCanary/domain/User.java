package com.sstu.StackCanary.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    //==========================================
    //
    // Database Columns
    //
    //==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nickname;

    //==========================================
    //
    // Relations
    //
    //==========================================

    @ManyToMany(mappedBy = "votedUpByUser")
    private Set<Question> questionsVotedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUser")
    private Set<Question> questionsVotedDownQuestions;

    @ManyToMany(mappedBy = "votedUpByUser")
    private Set<Question> answersVotedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUser")
    private Set<Question> answersVotedDownQuestions;

    //==========================================
    //
    // Constructors
    //
    //==========================================

    protected User() {}
}
