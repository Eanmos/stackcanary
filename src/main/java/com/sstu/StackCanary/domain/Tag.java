package com.sstu.StackCanary.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tag {
    //==========================================
    //
    // Database Columns
    //
    //==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    //==========================================
    //
    // Relations
    //
    //==========================================

    @ManyToMany(mappedBy = "tags")
    private Set<Question> questions;

    //==========================================
    //
    // Constructors
    //
    //==========================================

    protected Tag() {}

    public Tag(String name) {
        this.name = name;
    }
}
