package com.leyou.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate ;

    @Autowired
    private AmqpTemplate amqpTemplate;

    static final String CODE_PRIFIX = "leyou_user";
    //static Logger logger = new LoggerFactory.get...(UserService.class);
    //factory 不是 new 的, 是静态类, 直接引用
    static Logger logger= LoggerFactory.getLogger(UserService.class);

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

    public Boolean sendVerifyCode(String phone) {
        //生成验证码
        String code = NumberUtils.generateCode(5);
        System.out.println("UserService.sendVerifyCode    code :: " + code);
        try {
            //发送消息通知 发验证码短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone",phone);
            msg.put("code",code);
            amqpTemplate.convertAndSend("leyou.sms.exchange", "sms.verify.code", msg);
            //将验证码存入redis,设置失效时间
            redisTemplate.opsForValue().set(CODE_PRIFIX+phone,code,5, TimeUnit.MINUTES);
            return true;
        } catch (AmqpException e) {
            logger.error("发送短信失败,phone：{}， code：{}", phone, code);
            e.printStackTrace();
        }
        return false;
    }

    public Boolean register(User user, String code) {

        // 校验短信验证码
        String cacheCode = this.redisTemplate.opsForValue().get(CODE_PRIFIX + user.getPhone());
        if (!StringUtils.equals(code, cacheCode)) {
            return false;
        }

        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        // 强制设置不能指定的参数为null
        user.setId(null);
        user.setCreated(new Date());
        // 添加到数据库
        boolean bool = this.userMapper.insertSelective(user) == 1;

        if(bool){
            // 注册成功，删除redis中的记录
            this.redisTemplate.delete(CODE_PRIFIX + user.getPhone());
        }
        return bool;
    }

    public User login(String username, String password) {
        if(StringUtils.isBlank(password)||StringUtils.isBlank(username)){
            return null;
        }
        User queryUser = new User();
        queryUser.setUsername(username);
        // 由用户名查询 slat
        User storeUser = this.userMapper.selectOne(queryUser);
        if(storeUser==null){
            return null;
        }
        String salt = storeUser.getSalt();
        if(!storeUser.getPassword().equals(CodecUtils.md5Hex(password, salt))) {
            // 不等, 返回 null
            return null;
        }
        return storeUser;
    }
}

