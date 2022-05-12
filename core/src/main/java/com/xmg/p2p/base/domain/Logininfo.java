package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 登陆信息
 */
@Setter@Getter
public class Logininfo  extends BaseDomain{
    private static final long serialVersionUID = 8796002174559354457L;
    //正常状态
    public static Byte NOMAL = 0;
    //锁定状态
    public static Byte LOCK = 1;

    public static final int TYPE_CLIENT = 0;
    public static final int TYPE_MANAGER = 1;

    private String username;
    private String password;
    //状态
    private Byte state;
    private int userType;
}