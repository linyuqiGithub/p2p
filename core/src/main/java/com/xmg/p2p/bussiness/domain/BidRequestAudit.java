package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class BidRequestAudit extends BaseDomain {
    public static int NORMAL_AUDI = 0;//未认证
    public static int PASS_AUDI = 1;//通过认证
    public static int REJECT_AUDI = 2;//拒绝认证

    public static final int PUBLISH_AUDIT = 0;// 发标前审核
    public static final int FULL_AUDIT_1 = 1;// 满标一审
    public static final int FULL_AUDIT_2 = 2;// 满标二审

    private String remark;
    private int state;
    private Logininfo applier;
    private Logininfo auditor;
    private Date applyTime;
    private Date auditTime;
    private Long bidRequestId;
    private int auditType;

    public String getStateDisplay(){
        if(state == NORMAL_AUDI){
            return "审核中";
        }else if(state == PASS_AUDI){
            return "审核通过";
        }else if(state == REJECT_AUDI){
            return "审核拒绝";
        }else {
            return null;
        }
    }
    //显示当前借款审核对象的审核状态
    public String getAuditTypeDisplay(){
        if(auditType == PUBLISH_AUDIT){
            return "发标前审核";
        }else if(auditType == FULL_AUDIT_1){
            return "满标一审";
        }else
            return "满标二审";
    }


}
