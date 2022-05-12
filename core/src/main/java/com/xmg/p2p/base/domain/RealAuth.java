package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 */
@Setter@Getter
public class RealAuth extends BaseDomain{
    public static int NORMAL_AUDI = 0;//审核中
    public static int PASS_AUDI = 1;//审核通过
    public static int REJECT_AUDI = 2;//审核拒绝

    public static int MALE = 0;//男性
    public static int FEMALE = 1;//女性

    private String realName;//真实姓名
    private int sex;//性别
    private String idNumber;//身份证号
    private String bornDate;//出生日期
    private String address;//地址
    private String image1;//证件照正面
    private String image2;//证件照反面
    private int state;//审核状态
    private Logininfo applier;//申请人
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;//申请时间
    private Logininfo auditor;//审核人
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;//审核时间
    private String remark;//审核备注


    public String getSexDisplay(){
        return sex == MALE ? "男性" : "女性";
    }

    public String getStateDisplay(){
        //也可以使用switch()
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
        map.put("realName",realName);
        map.put("idNumber",idNumber);
        map.put("bornDate",bornDate);
        map.put("sex",getSexDisplay());
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);
        map.put("remark",remark);
        return JSON.toJSONString(map);
    }


}
