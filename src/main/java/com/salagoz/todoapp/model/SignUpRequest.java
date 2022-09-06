package com.salagoz.todoapp.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class SignUpRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;
}
