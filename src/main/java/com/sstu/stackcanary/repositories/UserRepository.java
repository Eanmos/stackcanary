package com.sstu.stackcanary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sstu.stackcanary.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
