package com.xmg.p2p.base.service;

/**
 * 验证码相关服务
 */
public interface IVerifyCodeService {
    /**
     * 发送验证码
     * @param phoneNumber
     *
     */
    void sendVerifyCode(String phoneNumber);

    /**
     * 绑定手机
     * @param phoneNumber
     * @param verifyCode
     */
    void bindPhone(String phoneNumber, String verifyCode);

    /**
     * 发送验证邮箱
     * @param email
     * @return
     */
    boolean sendEmail(String email);

    /**
     * 绑定邮箱
     * @param uuid
     * @return
     */
    boolean bindEmail(String uuid);
}
