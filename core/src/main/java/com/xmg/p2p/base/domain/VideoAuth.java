package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 视频认证对象
 */
@Setter
@Getter
public class VideoAuth extends BaseDomain {

    public static int NORMAL_AUDI = 0;//未认证
    public static int PASS_AUDI = 1;//通过认证
    public static int REJECT_AUDI = 2;//拒绝认证


    private int state;
    private String remark;
    private Logininfo applier;
    private Logininfo auditor;
    private Date applyTime;
    private Date auditTime;

    public String getStateDisplay() {
        if (state == NORMAL_AUDI) {
            return "审核中";
        } else if (state == PASS_AUDI) {
            return "审核通过";
        } else if (state == REJECT_AUDI) {
            return "审核拒绝";
        } else {
            return null;
        }
    }
}
