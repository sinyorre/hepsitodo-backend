package com.salagoz.todoapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
}
