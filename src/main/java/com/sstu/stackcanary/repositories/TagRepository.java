package com.sstu.stackcanary.repositories;

import com.sstu.stackcanary.domain.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByName(String name);
}
