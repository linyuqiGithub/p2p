package com.xmg.p2p.bussiness.domain;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台对外公布的账户信息
 */
@Setter
@Getter
public class PlatFormBank extends BaseDomain {
     private String bankName;//银行名称
     private String accountName;//开户人姓名
     private String bankNumber;//银行账号
     private String forkName;//支行名称

     public String getJsonString(){
         Map<String,Object> map = new HashMap<String, Object>();
         map.put("id",id);
         map.put("bankName",bankName);
         map.put("accountName",accountName);
         map.put("bankNumber",bankNumber);
         map.put("forkName",forkName);
         return JSON.toJSONString(map);
     }

}
