package com.example.mapper;

import com.example.model.entity.UserFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author L
* @description 针对表【user_friend】的数据库操作Mapper
* @createDate 2023-05-19 20:33:28
* @Entity com.example.model.entity.UserFriend
*/
@Mapper
public interface UserFriendMapper extends BaseMapper<UserFriend> {

}




