package com.sstu.StackCanary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sstu.StackCanary.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {}
