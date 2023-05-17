package com.example.model.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespondLogin implements Serializable {
    private String userId;

    private String userName;

    private String password;

    private String avatar;

    private String tag;

    private Date createTime;

    private String role;

    private String token;
}
