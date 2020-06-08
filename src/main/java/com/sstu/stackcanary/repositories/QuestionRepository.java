package com.sstu.stackcanary.repositories;

import com.sstu.stackcanary.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    public List<Question> findAllByOrderByCreationDateTimeDesc();
}
