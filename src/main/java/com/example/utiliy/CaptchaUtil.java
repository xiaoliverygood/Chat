package com.example.utiliy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.util.Random;
@Component
public class CaptchaUtil {
    @Autowired
    JavaMailSender sender;//springboot已经注册为bean了，加入到Ioc容器里面了，不用new一个对象那么麻烦
    @Autowired
    StringRedisTemplate template;//连接redis，并注册为bean
    public static String getCode() {
       Random random= new Random();
        int randomCode=100000+random.nextInt(899999);
        String s1 = randomCode+"";//将int转换成字符串
        return s1;
    }

   public String RigisterCode(String aimadress) {
       //SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("【Welcome to ChatRoom】");
        String codeTemplate=getCode();
        //设置邮件内容
        message.setText("你的注册验证码:"+codeTemplate);
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo(aimadress);
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("ljz2020comeon@163.com");
        //OK，万事俱备只欠发送
        sender.send(message);
        template.opsForValue().set(aimadress+"Register",codeTemplate);
        return codeTemplate;
    }
    public String findPasswordCode(String aimadress) {
        //SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("【Welcome to ChatRoom】");
        String codeTemplate=getCode();
        //设置邮件内容
        message.setText("你正在找回密码！你的验证码:"+codeTemplate);
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo(aimadress);
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("ljz2020comeon@163.com");
        //OK，万事俱备只欠发送
        sender.send(message);
        template.opsForValue().set(aimadress+"FindPassword", codeTemplate);
        return codeTemplate;
    }


}
