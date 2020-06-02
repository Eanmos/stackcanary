package com.sstu.stackcanary.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User implements UserDetails {
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

    @ManyToMany(mappedBy = "votedUpByUsers", fetch = FetchType.EAGER)
    private Set<Question> votedUpQuestions;

    @ManyToMany(mappedBy = "votedDownByUsers", fetch = FetchType.EAGER)
    private Set<Question> votedDownQuestions;

    @ManyToMany(mappedBy = "votedUpByUsers", fetch = FetchType.EAGER)
    private Set<Answer> votedUpAnswers;

    @ManyToMany(mappedBy = "votedDownByUsers", fetch = FetchType.EAGER)
    private Set<Answer> votedDownAnswers;

    public void voteUpForQuestion(Question q) {
        votedUpQuestions.add(q);
        votedDownQuestions.remove(q);
    }

    public void voteDownForQuestion(Question q) {
        votedDownQuestions.add(q);
        votedUpQuestions.remove(q);
    }

    public void voteUpForAnswer(Answer q) {
        votedUpAnswers.add(q);
        votedDownAnswers.remove(q);
    }

    public void voteDownForAnswer(Answer q) {
        votedDownAnswers.add(q);
        votedUpAnswers.remove(q);
    }

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
