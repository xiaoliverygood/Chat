package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.BaseResponse;
import com.example.model.entity.User;
import com.example.model.request.UserRequestFindPassword;
import com.example.model.request.UserRequestLogin;
import com.example.model.request.UserRequestRegister;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    BaseResponse register(@RequestBody UserRequestRegister userRequestRegister);
    BaseResponse findPassword(@RequestBody UserRequestFindPassword userRequestFindPassword);
    BaseResponse login(@RequestBody UserRequestLogin userRequestLogin);
    BaseResponse showMyMessage(HttpServletRequest httpServletRequest);

    BaseResponse logout(HttpServletRequest httpServletRequest);

}
