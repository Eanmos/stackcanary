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
public class Question {
    //==========================================
    //
    // Database Columns
    //
    //==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String body;

    @Column(name = "creationDateTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

    //==========================================
    //
    // Relations
    //
    //==========================================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

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
    public Set<User> votedUpByUsers;

    @ManyToMany
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

    //==========================================
    //
    // Constructors
    //
    //==========================================

    protected Question() {}

    public Question(User author, String title, String body, Set<Tag> tags) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.tags = tags;

        // Assign current date and time.
        this.creationDateTime = new Date();
    }

    //==========================================
    //
    // Getters and Setters
    //
    //==========================================

    public Integer getId() {
        return id;
    }

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
}
