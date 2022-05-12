package com.xmg.p2p.bussiness.query;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 还款查询对象
 */
@Setter
@Getter
public class ReturnMoneyQueryObject extends QueryObject {
    private int state;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    //借款人id
    private Long userinfoId;//自己只能看到自己的还款对象
    public Date getEndDate() {
        return endDate == null ? null : DateUtil.endOfDay(endDate);
    }

}
