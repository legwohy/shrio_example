package com.shiro.chapter16.service;

import com.shiro.chapter16.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();// 随机数生成器

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";// 算法名称
    @Value("${password.hashIterations}")
    private int hashIterations = 2;// 生成hash值的迭代次数

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * 加密
     * @param user
     */
    public void encryptPassword(User user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());// 设置的盐为 随机的bytes(-126,127) 转为16进制

        String newPassword = new SimpleHash(
                algorithmName,// 算法
                user.getPassword(),// 待加密的文本
                ByteSource.Util.bytes(user.getCredentialsSalt()),// 盐转换为字节
                hashIterations// 迭代次数
        ).toHex();

        user.setPassword(newPassword);
    }
}
