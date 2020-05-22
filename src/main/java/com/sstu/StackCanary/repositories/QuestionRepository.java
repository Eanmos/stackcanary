package com.sstu.StackCanary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sstu.StackCanary.domain.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {}
