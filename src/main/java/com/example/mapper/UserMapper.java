package com.example.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.User;
import com.example.model.respond.FriendUser;
import com.example.model.respond.NoReadMessage;
import org.apache.ibatis.annotations.*;


import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 这个是获取好友的id号而已（列表）根据我的userName
     * @param //userName用户名必须唯一
     * @return
     */
    @Select("SELECT user_friend.friend_id FROM `user` LEFT JOIN user_friend ON user_friend.user_id=`user`.user_id WHERE user_name=#{userName}")
    List<String> findMyfriend(@Param("userName") String userName);

    /**
     * 根据我的userid查找我的朋友，并且是以User形式输出
     * @param userId
     * @return
     */
    @Select("SELECT `user`.* FROM user_friend LEFT JOIN `user` ON `user`.user_id=user_friend.friend_id WHERE user_friend.user_id=#{userId}")
    List<FriendUser> findMyfriendsAllMessage(@Param("userId") String userId);

    /**
     * 当对方不在线时，发送消息保存到数据库
     * @param userId
     * @param friendId
     * @param content
     */
    @Insert("INSERT INTO friend_message (user_id,friend_id, content,time) VALUES (#{userId},#{friend_Id}, #{content},#{time})")
    void insertFriendMessage(@Param("userId") String userId,@Param("friend_Id") String friendId,@Param("content") String content,@Param("time") Date time);

    /**
     * 根据名字查询到对应的id
     */
    @Select("SELECT user_id FROM `user` WHERE `user`.user_name=#{userName}")
    String selectUserIdByName(@Param("userName") String userName);

    @Select("select user_id,content,time FROM friend_message where friend_id=#{friendId}")
    List<NoReadMessage> getNoReadMessages(@Param("friendId")String userId);

    @Delete("DELETE FROM friend_message WHERE friend_id = #{friendId}")
    void deleteFriendMessage(@Param("friendId")String friendId);

    @Select("SELECT `user`.user_name FROM `user` WHERE user_id=#{userId}")
    String selectUserByUserId(@Param("userId")String userId);
}




