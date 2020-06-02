package com.sstu.stackcanary.domain;

import lombok.*;
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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @NonNull
    private String title;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "question_tag",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @NonNull
    private Set<Tag> tags;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    public Set<Answer> answers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_vote_up",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> votedUpByUsers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_vote_down",
            joinColumns = @JoinColumn(name = "question_id"),
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
    public String formattedCreationDateTime;

    @Transient
    public Integer votes;

    @Transient
    public Integer answersCount;

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

    public void calculateAnswersCount() {
        answersCount = this.answers.size();
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
        } else if (user.getVotedUpQuestions().contains(this)) {
            this.votedUpByActiveUser = true;
            this.votedDownByActiveUser = false;
        } else if (user.getVotedDownQuestions().contains(this)) {
            this.votedUpByActiveUser = false;
            this.votedDownByActiveUser = true;
        } else {
            this.votedUpByActiveUser = false;
            this.votedDownByActiveUser = false;
        }
    }
}
