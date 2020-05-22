package com.sstu.StackCanary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sstu.StackCanary.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
