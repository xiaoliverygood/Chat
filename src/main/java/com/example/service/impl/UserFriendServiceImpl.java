package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.model.entity.UserFriend;
import com.example.service.UserFriendService;
import com.example.mapper.UserFriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author L
* @description 针对表【user_friend】的数据库操作Service实现
* @createDate 2023-05-19 20:33:28
*/
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend>
    implements UserFriendService{
    @Autowired
    UserFriendMapper userFriendMapper;
    @Override
    public Boolean addFriend(String myId, String friendId) {
        UserFriend userFriend = new UserFriend(null,friendId,myId);
        userFriendMapper.updateById(userFriend);
        return true;
    }
}




