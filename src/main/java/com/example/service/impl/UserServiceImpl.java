package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseResponse;
import com.example.common.ResponMessge;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.model.request.UserRequestRegister;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
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
}




