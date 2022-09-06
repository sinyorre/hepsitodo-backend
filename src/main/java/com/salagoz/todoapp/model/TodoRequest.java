package com.salagoz.todoapp.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TodoRequest {
    @NotNull
    @Size(min = 3, max = 255)
    private String title;
    @NotNull
    @Size(min = 3, max = 1024)
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
