package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.BaseResponse;
import com.example.model.entity.User;
import com.example.model.request.UserRequestRegister;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService extends IService<User> {
    BaseResponse register(@RequestBody UserRequestRegister userRequestRegister);

}
