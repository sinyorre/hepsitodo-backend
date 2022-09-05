package com.salagoz.todoapp.repository;

import com.salagoz.todoapp.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String s);

    boolean existsByUsername(String username);
}
