package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseResponse;
import com.example.common.ResponMessge;
import com.example.mapper.UserFriendMapper;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.model.request.UserRequestFindPassword;
import com.example.model.request.UserRequestLogin;
import com.example.model.request.UserRequestRegister;
import com.example.model.respond.UserRespondLogin;
import com.example.service.UserFriendService;
import com.example.service.UserService;
import com.example.utiliy.EmailRegularExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    StringRedisTemplate template;//连接redis，并注册为bean
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserFriendService userFriendService;

    @Override
    public BaseResponse register(UserRequestRegister userRequestRegister) {
        if (!EmailRegularExpression.RegularEmailPattern(userRequestRegister.getUserId())) {
            return BaseResponse.Error(ResponMessge.EmailError);
        }
        if (userRequestRegister.getCode()
                .equals(template.opsForValue().get(userRequestRegister.getUserId() + "Register"))) {
            User user = new User(userRequestRegister.getUserId(), userRequestRegister.getUserName(), userRequestRegister.getPassword(), null, null, null, userRequestRegister.getRole());
            userMapper.insert(user);
            template.delete(userRequestRegister.getUserId() + "Register");
            return BaseResponse.success(user);
        } else {
            return BaseResponse.Error(ResponMessge.CaptchaError.getMessage());
        }
    }

    @Override
    public BaseResponse findPassword(UserRequestFindPassword userRequestFindPassword) {
        if (!EmailRegularExpression.RegularEmailPattern(userRequestFindPassword.getUserId())) {
            return BaseResponse.Error(ResponMessge.EmailError);
        }

        if (userRequestFindPassword.getCode()
                .equals(template.opsForValue().get(userRequestFindPassword.getUserId() + "FindPassword"))) {
            User user = userMapper.selectById(userRequestFindPassword.getUserId());
            user.setPassword(userRequestFindPassword.getNewPassword());
            userMapper.updateById(user);
            template.delete(userRequestFindPassword.getUserId() + "FindPassword");
            return BaseResponse.success(user);
        } else {
            return BaseResponse.Error(ResponMessge.CaptchaError.getMessage());
        }
    }

    @Override
    public BaseResponse login(UserRequestLogin userRequestLogin) {
        if (!EmailRegularExpression.RegularEmailPattern(userRequestLogin.getUserId())) {
            return BaseResponse.Error(ResponMessge.EmailError);
        }

        if (userRequestLogin.getPassword().equals(userMapper.selectById(userRequestLogin.getUserId()).getPassword())) {

            String token = UUID.randomUUID().toString();

            template.opsForValue().set(token, userRequestLogin.getUserId());

            User user = userMapper.selectById(userRequestLogin.getUserId());

            return BaseResponse.success(new UserRespondLogin(user.getUserId(), user.getUserName(), user.getPassword(), user.getAvatar(), user.getTag(), user.getCreateTime(), user.getRole(), token));
        }
        return BaseResponse.Error(ResponMessge.UserOrPasswordError.getMessage());
    }


    @Override
    public BaseResponse showMyMessage(HttpServletRequest httpServletRequest) {
        User user = userMapper.selectById(template.opsForValue().get(httpServletRequest.getHeader("token")));
        return BaseResponse.success(user);
    }

    @Override
    public BaseResponse logout(HttpServletRequest httpServletRequest) {
        template.delete(httpServletRequest.getHeader("token"));
        return BaseResponse.success(ResponMessge.Logoutsuccess.getMessage());
    }

    @Override
    public BaseResponse addFriend(HttpServletRequest httpServletRequest, String friendId) {
        return BaseResponse.success(userFriendService.addFriend(template.opsForValue().get(httpServletRequest.getHeader("token")),friendId));
    }
}




