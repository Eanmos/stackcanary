package com.sstu.stackcanary.repositories;

import com.sstu.stackcanary.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
