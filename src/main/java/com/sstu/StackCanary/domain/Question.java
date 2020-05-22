package com.sstu.StackCanary.domain;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "creatingDateTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatingDateTime;

    // This field must be initialized and updated manual by
    // calling the formatCreatingDateTime() method.
    @Transient
    public String formattedCreatingDateTime;

    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "question_answer",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    public Set<Answer> answers;

    @ManyToMany
    @JoinTable(
            name = "question_vote_up",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> votedUpByUser;

    @ManyToMany
    @JoinTable(
            name = "question_vote_down",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> votedDownByUser;

    // This field must be initialized and updated manual by
    // calling the calculateVotes() method.
    @Transient
    public Integer votes;

    // This field must be initialized and updated manual by
    // calling the calculateAnswersCount() method.
    @Transient
    public Integer answersCount;

    // This field must be initialized and updated manual by
    // calling the convertBodyToHTML() method.
    @Transient
    public String bodyInHTML;

    protected Question() {
    }

    public void formatCreatingDateTime() {
        DateFormat d = new SimpleDateFormat("MMM d ''yy 'at' HH:mm");
        formattedCreatingDateTime = d.format(creatingDateTime);
    }

    public void calculateVotes() {
        votes = votedUpByUser.size() - votedDownByUser.size();
    }

    public void calculateAnswersCount() {
        answersCount = this.answers.size();
    }

    public void convertBodyToHTML() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);
        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();
        bodyInHTML = renderer.render(document);
    }
}
