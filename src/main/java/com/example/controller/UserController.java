package com.example.controller;

import com.example.common.BaseResponse;
import com.example.model.entity.User;
import com.example.model.request.UserRequestRegister;
import com.example.service.UserService;
import com.example.utiliy.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("register")
    public BaseResponse register(@RequestBody UserRequestRegister userRequestRegister){
        return userService.register(userRequestRegister);
    }

}
