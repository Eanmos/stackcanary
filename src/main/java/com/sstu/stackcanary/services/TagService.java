package com.sstu.stackcanary.services;

import com.sstu.stackcanary.domain.Tag;
import com.sstu.stackcanary.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    final private TagRepository tagRepository;

    public Tag getTagCreateIfNotExists(String name) {
        Tag tag = tagRepository.findByName(name);

        if (tag == null)
            tag = new Tag(name);

        tagRepository.save(tag);

        return tag;
    }
}
