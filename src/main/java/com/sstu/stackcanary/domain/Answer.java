package com.sstu.stackcanary.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
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

    //==========================================
    //
    // Transient Fields
    //
    // This fields must be initialized manually by
    // calling the corresponding entity's method.
    //==========================================

    @Transient
    private String formattedCreationDateTime;

    @Transient
    public Integer votes;

    @Transient
    public String bodyInHTML;

    @Transient
    public boolean votedUpByActiveUser;

    @Transient
    public boolean votedDownByActiveUser;

    //==========================================
    //
    // Methods
    //
    //==========================================

    public void formatCreationDateTime() {
        DateFormat d = new SimpleDateFormat("MMM d ''yy 'at' HH:mm");
        formattedCreationDateTime = d.format(creationDateTime);
    }

    public void calculateVotes() {
        votes = votedUpByUsers.size() - votedDownByUsers.size();
    }

    public void convertBodyFromMarkdownToHTML() {
        Node           document  =  Parser.builder().build().parse(body);
        HtmlRenderer   renderer  =  HtmlRenderer.builder().escapeHtml(true).build();
        bodyInHTML               =  renderer.render(document);
    }

    public void setVotedByActiveUser(User user) {
        if (user == null) {
            this.votedUpByActiveUser = false;
            this.votedDownByActiveUser = false;
        } else if (user.getVotedUpAnswers().contains(this)) {
            this.votedUpByActiveUser = true;
            this.votedDownByActiveUser = false;
        } else if (user.getVotedDownAnswers().contains(this)) {
            this.votedUpByActiveUser = false;
            this.votedDownByActiveUser = true;
        } else {
            this.votedUpByActiveUser = false;
            this.votedDownByActiveUser = false;
        }
    }
}
