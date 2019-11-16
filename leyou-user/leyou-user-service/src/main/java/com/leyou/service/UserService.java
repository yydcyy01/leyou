package com.leyou.service;

import com.leyou.mapper.UserMapper;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * data, 要校验的数据，type要校验的类型 1、用户名 2、手机
     *
     * @param data
     * @param type
     * @return
     */
    public Boolean checkUser(String data, Integer type) {
        //localhost:8085/check/13600527634/2   return false : 说明不允许注册了, 数据库存在数据
        User user = new User();
        if (type == 1){
            user.setUsername(data);
        } else if (type == 2){
            user.setPhone(data);
        } else {
            return null;
        }
        return userMapper.selectCount(user) == 0;
    }

    public Boolean sendVerifyCode(String phone){
        // 生成验证码
        return true;
    }
}

