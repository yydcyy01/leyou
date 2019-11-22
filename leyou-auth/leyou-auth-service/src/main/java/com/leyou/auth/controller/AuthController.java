package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    private Logger logger= LoggerFactory.getLogger(AuthController.class);
    /**
     * 登录表单提交的登录认证请求
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> authentication(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 登录校验
        String token = this.authService.authentication(username, password);

        if (StringUtils.isBlank(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // 将token写入cookie,并指定httpOnly为true，防止通过JS获取和修改
        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(),
                token, jwtProperties.getCookieMaxAge(), null, true);
        return ResponseEntity.ok().build();
    }

    /**
     * 验证是否已登录,已登录则刷新token
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //从cookie取出token,并从中取得JWT中的用户数据
            String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            //token 过期, 没有刷新方法, 直接重新设置.
            //重新生成token(刷新过期时间),由cookie带回客户端
            String newToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), newToken,
                    jwtProperties.getCookieMaxAge(), null, true);  //指定httpOnly防止通过js获取和修改
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            logger.error("鉴权失败，用户未登录:"+request.getRemoteUser());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

