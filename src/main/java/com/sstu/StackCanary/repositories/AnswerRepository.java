package com.sstu.StackCanary.repositories;

import com.sstu.StackCanary.domain.Question;
import org.springframework.data.repository.CrudRepository;
import com.sstu.StackCanary.domain.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {}
