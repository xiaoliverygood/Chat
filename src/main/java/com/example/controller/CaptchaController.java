package com.example.controller;
import com.example.common.BaseResponse;
import com.example.utiliy.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {

    @Autowired
    CaptchaUtil captchaUtil;
    @GetMapping("/captcha")
    public BaseResponse captcha(String email){
        System.out.println(email);
        return BaseResponse.success(captchaUtil.RigisterCode(email));
    }
    @GetMapping("/findPasswordCaptcha")
    public BaseResponse findpasswordCaptcha(String emailAddress){
        return BaseResponse.success(captchaUtil.findPasswordCode(emailAddress));
    }
    @GetMapping("/ud3whuq")
    public BaseResponse ud3whuq(String emailAddress){
        return null;
    }

}
