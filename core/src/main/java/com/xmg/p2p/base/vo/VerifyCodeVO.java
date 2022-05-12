package com.xmg.p2p.base.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 发送到session中的验证码对象
 * Created by Administrator on 2017/7/10.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeVO {
    //发送时间
    private Date sendTime;
    //验证码
    private String verifyCode;
    //手机号码
    private String phoneNumber;
}
