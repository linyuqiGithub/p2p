package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登录日志
 */
@Setter@Getter
public class IpLog extends BaseDomain {

    public static final int LOGIN_FAILIED = 0;
    public static final int LOGIN_SUCCESS = 1;
    public static final int TYPE_CLIENT = 0;
    public static final int TYPE_MANAGER = 1;

    //登录用户
    private String username;
    //登陆时间
    private Date loginTime;
    //ip地址
    private String ip;
    //登陆状态
    private int state;
    //用户类型
    private int userType;

    //展示登陆状态
    public String getDisplay(){
        return state == LOGIN_FAILIED ? "登陆失败": "登陆成功";
    }
    //展示用户类型
    public String getTypeDisplay(){
        return userType == TYPE_CLIENT ? "前台用户" : "后台用户";
    }
}
