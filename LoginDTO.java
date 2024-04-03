package com.example.virtualplantstore.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String userType;
    private String email;
    private String password;
}
