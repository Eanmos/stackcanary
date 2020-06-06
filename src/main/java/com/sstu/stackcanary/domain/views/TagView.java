package com.sstu.stackcanary.domain.views;

import com.sstu.stackcanary.domain.Tag;

public class TagView {
    final private String name;

    public TagView(final Tag t) {
        this.name = t.getName();
    }
}
