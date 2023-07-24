package com.security.springsecurityproject.data.dto.request;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String username;
    private String password;
}
