package com.leyou.controller;

import com.leyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}