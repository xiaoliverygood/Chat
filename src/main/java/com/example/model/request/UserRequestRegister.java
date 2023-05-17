package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestRegister implements Serializable {
    private String userId;

    private String userName;

    private String password;

    private String role;

    private String code;
}
