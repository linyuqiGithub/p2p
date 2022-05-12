package com.xmg.p2p.bussiness.query;

import com.xmg.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 线下充值查询对象
 */
@Setter
@Getter
public class RechargeOfflineQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int state = -1;
    private int bankinfoId = -1;
    private String tradeCode;
    private Long userId;

    public String getTradeCode(){
        if(tradeCode == null){
            return "";
        }else {
            return tradeCode;
        }
    }
}
