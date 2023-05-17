package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseResponse;
import com.example.common.ResponMessge;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.model.request.UserRequestFindPassword;
import com.example.model.request.UserRequestLogin;
import com.example.model.request.UserRequestRegister;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    StringRedisTemplate template;//连接redis，并注册为bean
    @Autowired
    UserMapper userMapper;
    @Override
    public BaseResponse register(UserRequestRegister userRequestRegister) {
        if(userRequestRegister.getCode()
                .equals(template.opsForValue().get(userRequestRegister.getUserId()+"Register"))){
            User user = new User(userRequestRegister.getUserId(), userRequestRegister.getUserName(), userRequestRegister.getPassword(), null, null, null, "User");
            userMapper.insert(user);
            template.delete(userRequestRegister.getUserId()+"Register");
            return BaseResponse.success(user);
        }else {
            return BaseResponse.Error(ResponMessge.CaptchaError.getMessage());
        }

    }

    @Override
    public BaseResponse findPassword(UserRequestFindPassword userRequestFindPassword) {
        if(userRequestFindPassword.getCode()
                .equals(template.opsForValue().get(userRequestFindPassword.getUserId()+"FindPassword"))){
            User user=userMapper.selectById(userRequestFindPassword.getUserId());
            user.setPassword(userRequestFindPassword.getNewPassword());
            userMapper.updateById(user);
            return BaseResponse.success(user);
        }else {
            return BaseResponse.Error(ResponMessge.CaptchaError.getMessage());
        }
    }

    @Override
    public BaseResponse login(UserRequestLogin userRequestLogin) {
        if (userRequestLogin.getPassword().equals(userMapper.selectById(userRequestLogin.getUserId()).getPassword())) {

            String token= UUID.randomUUID().toString();

            template.opsForValue().set(token,userRequestLogin.getUserId());
            return BaseResponse.success(userMapper.selectById(userRequestLogin.getUserId()));
        }
        return BaseResponse.Error(ResponMessge.UserOrPasswordError.getMessage());
    }
}




