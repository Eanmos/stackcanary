package com.sstu.stackcanary.repositories;

import com.sstu.stackcanary.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer> {}
