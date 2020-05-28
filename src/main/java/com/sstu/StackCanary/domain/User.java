package com.sstu.StackCanary.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements UserDetails {
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

    @ManyToMany(mappedBy = "votedUpByUsers", fetch = FetchType.EAGER)
    private Set<Question> votedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUsers", fetch = FetchType.EAGER)
    private Set<Question> votedDownQuestions;

    @ManyToMany(mappedBy = "votedUpByUsers", fetch = FetchType.EAGER)
    private Set<Answer> votedUpAnswers;

    @ManyToMany(mappedBy = "votedDownByUsers", fetch = FetchType.EAGER)
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

    public Set<Question> getVotedUpQuestions() {
        return votedUpQuestions;
    }

    public void setVotedUpQuestions(Set<Question> votedUpQuestions) {
        this.votedUpQuestions = votedUpQuestions;
    }

    public Set<Question> getVotedDownQuestions() {
        return votedDownQuestions;
    }

    public void setVotedDownQuestions(Set<Question> votedDownQuestions) {
        this.votedDownQuestions = votedDownQuestions;
    }

    public Set<Answer> getVotedUpAnswers() {
        return votedUpAnswers;
    }

    public void setVotedUpAnswers(Set<Answer> votedUpAnswers) {
        this.votedUpAnswers = votedUpAnswers;
    }

    public Set<Answer> getVotedDownAnswers() {
        return votedDownAnswers;
    }

    public void setVotedDownAnswers(Set<Answer> votedDownAnswers) {
        this.votedDownAnswers = votedDownAnswers;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;

        if (!(that instanceof User))
            return false;

        User thatUser = (User) that;

        return this.id.equals(thatUser.id);
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        return PRIME * id.hashCode();
    }

    //==========================================
    //
    // UserDetails abstract methods implementation
    //
    //==========================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }
}
