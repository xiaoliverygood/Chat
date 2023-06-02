package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestRegister implements Serializable {
    /**
     * 邮箱
     */
    private String userId;

    /**
     * 用户名称
     */

    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型
     */
    private String role;

    /**
     * 注册验证码
     */
    private String code;
}
