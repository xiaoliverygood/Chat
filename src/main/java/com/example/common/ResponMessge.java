package com.example.common;

public enum ResponMessge {

    Logoutsuccess("成功退出登录"),//注意使用逗号隔开
    NologError("请登录再进行访问！"),
    EmailError("邮箱错误"),
    UserOrPasswordError("用户名或者密码错误"),
    CaptchaError("验证码错误！");

    private  String message;

    ResponMessge(String s) {
        this.message = s;
    }
    public String getMessage() {
        return message;
    }
}
