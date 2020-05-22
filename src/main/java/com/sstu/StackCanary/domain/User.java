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

    @ManyToMany(mappedBy = "ratedUpByUser")
    private Set<Question> questionsRatedUpQuestions;

    @ManyToMany(mappedBy = "ratedDownByUser")
    private Set<Question> questionsRatedDownQuestions;

    @ManyToMany(mappedBy = "ratedUpByUser")
    private Set<Question> answersRatedUpQuestions;

    @ManyToMany(mappedBy = "ratedDownByUser")
    private Set<Question> answersRatedDownQuestions;

    protected User() { }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
