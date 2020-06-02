package com.sstu.stackcanary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sstu.stackcanary.domain.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {}
