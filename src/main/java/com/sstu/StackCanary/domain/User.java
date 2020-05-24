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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private Boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

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

    //==========================================
    //
    // Getters and Setters
    //
    //==========================================

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
