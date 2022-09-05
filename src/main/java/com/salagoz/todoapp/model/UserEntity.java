package com.salagoz.todoapp.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.repository.Collection;

import java.util.List;

@Collection("users")
@Data
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private String username;
    private String email;
    private String password;
    private List<Todo> todos;
}
