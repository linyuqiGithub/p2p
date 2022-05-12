package com.xmg.p2p.bussiness.domain;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线下充值单对象
 * Created by Administrator on 2017/7/15.
 */
@Setter
@Getter
public class RechargeOffline extends BaseDomain {

    public static int NORMAL_AUDI = 0;//未认证
    public static int PASS_AUDI = 1;//通过认证
    public static int REJECT_AUDI = 2;//拒绝认证

    private int state;//充值状态
    private String remark;//说明
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;
    private Logininfo auditor;
    private Logininfo applier;
    private String tradeCode;//交易号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;//交易时间
    private BigDecimal amount;//数额
    private String note;//说明
    private PlatFormBank bankinfo;//充值的银行账户

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

    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",applier.getUsername());
        map.put("tradeCode",tradeCode);
        map.put("amount",amount);
        //需要将时间继续格式化
        map.put("tradeTime", DateFormat.getDateInstance().format(tradeTime));
        return JSON.toJSONString(map);
    }
}
