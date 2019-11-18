package com.auth.common.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "/Users/yuyang/Downloads/JavaWeb/Test/rsa.pub";

    private static final String priKeyPath = "/Users/yuyang/Downloads/JavaWeb/Test/rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 注 : 运行此生成类前, 先注释掉 @Before 这个类
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU3NDA4MDY3NX0.Y6XvcoSklC_82ehVAvTAU_NIF7h_6UMY4lyOyA6Tfw05q80LpNR57tbi3OSSp8jOACcUv2J1BgUokh6JqUbvfeKxvctQ_LwdOFPXhRIBD6LdJr63C3p-SENcAlX9w84CArp0ccpJHDRFxM3cvnIqu7iUFOup7X1RTnB0JYuJqfw";
        //String token2= "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU3NDA1ODE1MX0.Y2nMKrz9ata-kSbqbNFZy_yi5a9Fyec72deR2mRcFbFVW6i0_LUiBOmDc0H-40wz1Yn7SXJ3j7cRhz2CgndOlPy2hqXH7rxcvk6TYtdd5o042aexJZrbh0-wRt0zuk1peTnRUpGK8WiR4IkwXGaYi36cAtw0eHNW3NrikOSa5ms";
        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}