package com.example.service;

import com.example.model.entity.UserFriend;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author L
* @description 针对表【user_friend】的数据库操作Service
* @createDate 2023-05-19 20:33:28
*/
public interface UserFriendService extends IService<UserFriend> {

    Boolean addFriend(String myId,String friendId);
}
