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
    private String username;

    //==========================================
    //
    // Relations
    //
    //==========================================

    @ManyToMany(mappedBy = "votedUpByUsers")
    private Set<Question> votedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUsers")
    private Set<Question> votedDownQuestions;

    @ManyToMany(mappedBy = "votedUpByUsers")
    private Set<Answer> votedUpAnswers;

    @ManyToMany(mappedBy = "votedDownByUsers")
    private Set<Answer> votedDownAnswers;

    //==========================================
    //
    // Constructors
    //
    //==========================================

    protected User() {}
}
