package com.salagoz.todoapp.model;

import lombok.Data;

@Data
public class TodoRequest {
    private String title;
    private String description;

    public Todo toModel() {
        return Todo
                .builder()
                .title(title)
                .description(description)
                .done(false)
                .build();
    }
}
