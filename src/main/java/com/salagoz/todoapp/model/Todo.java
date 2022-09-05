package com.salagoz.todoapp.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.repository.Collection;

@Collection("todos")
@Data
@Builder
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private String title;

    private String description;

    private boolean done;
}
