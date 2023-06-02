package com.example.controller;
import com.example.common.BaseResponse;
import com.example.model.request.UserRequestFindPassword;
import com.example.model.request.UserRequestLogin;
import com.example.model.request.UserRequestRegister;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 注册
     * @param userRequestRegister
     * @return
     */
    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserRequestRegister userRequestRegister){
        return userService.register(userRequestRegister);
    }

    /**
     * 找回密码
     * @param userRequestFindPassword
     * @return
     */
    @PutMapping("/findPassword")
    public BaseResponse findPassword(@RequestBody UserRequestFindPassword userRequestFindPassword){
        return userService.findPassword(userRequestFindPassword);
    }

    /**
     * 登录
     * @param userRequestLogin
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserRequestLogin userRequestLogin){
        return userService.login(userRequestLogin);
    }

    /**
     * 展示我的信息
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/showMyMessage")
    public BaseResponse showMyMessage(HttpServletRequest httpServletRequest){
        return userService.showMyMessage(httpServletRequest);
    }

    /**
     * 添加好友
     * @param httpServletRequest
     * @param friendId
     * @return
     */
    @PostMapping("/addFriend")
    public BaseResponse addFriend(HttpServletRequest httpServletRequest,String friendId){
       return userService.addFriend(httpServletRequest,friendId);
    }

    /**
     * 退出登录
     * @param httpServletRequest
     * @return
     */

    @PostMapping("/logout")
    public BaseResponse logout(HttpServletRequest httpServletRequest){
        return userService.logout(httpServletRequest);
    }


//    @GetMapping("/findOnlineUser")
//    public BaseResponse findOnlineUser(HttpServletRequest httpServletRequest){
//        return null;
//    }

    /**
     * 查找我的朋友
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/findMyfriend")
    public BaseResponse findMyfriend(HttpServletRequest httpServletRequest){
        return userService.findMyfriend(httpServletRequest);
    }

}
