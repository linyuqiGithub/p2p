package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 邮件验证对象
 */
@Setter@Getter
public class MailVerify extends BaseDomain{
    private Long userId;
    private String email;
    private Date sendTime;
    private String uuid;
}
