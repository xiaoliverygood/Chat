package com.example.controller;
import com.example.common.BaseResponse;
import com.example.utiliy.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {
   @Autowired
    CaptchaUtil captchaUtil;
    @GetMapping ("/captcha")
    public BaseResponse<String> getCaptcha(@RequestBody String email){
       return BaseResponse.success(captchaUtil.RigisterCode(email));
    }
    @GetMapping("/findPasswordCaptcha")
    public BaseResponse<String> findpasswordCaptcha(@RequestBody String emailAddress){
        return BaseResponse.success(captchaUtil.findPasswordCode(emailAddress));
    }

}
