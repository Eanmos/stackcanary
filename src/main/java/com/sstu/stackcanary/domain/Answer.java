package com.sstu.stackcanary.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "LONGTEXT")
    @NonNull
    private String body;

    @Column(name = "creationDateTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date creationDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    @NonNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "question", nullable = false)
    @NonNull
    private Question question;

    @ManyToMany
    @JoinTable(
            name = "answer_vote_up",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> votedUpByUsers;

    @ManyToMany
    @JoinTable(
            name = "answer_vote_down",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> votedDownByUsers;
}
