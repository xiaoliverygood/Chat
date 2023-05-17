package com.example.controller;
import com.example.common.BaseResponse;
import com.example.model.request.UserRequestFindPassword;
import com.example.model.request.UserRequestLogin;
import com.example.model.request.UserRequestRegister;
import com.example.service.UserService;
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
    @PutMapping("/findPassword")
    public BaseResponse findPassword(@RequestBody UserRequestFindPassword userRequestFindPassword){
        return userService.findPassword(userRequestFindPassword);
    }
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserRequestLogin userRequestLogin){
        return userService.login(userRequestLogin);
    }

}
