package com.sstu.stackcanary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sstu.stackcanary.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByName(String name);
}
