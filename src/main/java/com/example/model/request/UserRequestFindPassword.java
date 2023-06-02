package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestFindPassword implements Serializable {
    /**
     * 用户邮箱
     */
    private String userId;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 找回密码验证码
     */
    private String code;
}
