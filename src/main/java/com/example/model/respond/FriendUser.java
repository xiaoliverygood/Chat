package com.example.model.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendUser {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户的名字
     */
    private String userName;
}
