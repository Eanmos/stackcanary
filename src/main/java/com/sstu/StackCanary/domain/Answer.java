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
public class Answer {
    //==========================================
    //
    // Database Columns
    //
    //==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
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

    @ManyToOne
    @JoinColumn(name = "question", nullable = false)
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

    //==========================================
    //
    // Constructors
    //
    //==========================================

    protected Answer() {}

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
}
