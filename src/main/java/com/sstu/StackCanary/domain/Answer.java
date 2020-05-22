package com.sstu.StackCanary.domain;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private User author;

    @Column(name = "creatingDateTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatingDateTime;

    // This field must be initialized manual by
    // calling the formatCreatingDateTime() method.
    @Transient
    private String formattedCreatingDateTime;

    @ManyToMany(mappedBy = "answers")
    private Set<Question> questions;

    @ManyToMany
    @JoinTable(
            name = "answer_rating_up",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> ratedUpByUser;

    @ManyToMany
    @JoinTable(
            name = "answer_rating_down",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    public Set<User> ratedDownByUser;

    // This field must be initialized and updated manual by
    // calling the calculateRating() method.
    @Transient
    public Integer rating;

    protected Answer() { }

    public void formatCreatingDateTime() {
        DateFormat d = new SimpleDateFormat("MMM d ''yy 'at' HH:mm");
        this.formattedCreatingDateTime = d.format(this.creatingDateTime);
    }

    public void calculateRating() {
        this.rating = ratedUpByUser.size() - ratedDownByUser.size();
    }
}
