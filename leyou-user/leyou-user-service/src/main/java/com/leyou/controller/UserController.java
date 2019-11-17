package com.leyou.controller;

import com.leyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity <Boolean> checkUser(@PathVariable("data") String data,@PathVariable("type") Integer type){
        Boolean bool = userService.checkUser(data, type);

        if  (bool == null){
            //404
            return ResponseEntity.badRequest().build();
        }

        //200
        return ResponseEntity.ok(bool);
    }

    /**
     * 发送短信验证码
     * 文档中说 POST 方式, 传 String. => 是 @RequestParam(" ")
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone){
        Boolean result=this.userService.sendVerifyCode(phone);
        if(result==null||!result){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);//202，请求被接受
    }
}