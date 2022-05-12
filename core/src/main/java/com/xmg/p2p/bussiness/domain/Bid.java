package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标对象
 */
@Setter
@Getter
public class Bid extends BaseDomain {
     private BigDecimal actualRate;//实际年利率(应该是等同于标的的利率)
     private BigDecimal availableAmount;//投标有效金额(就是投标金额)
     private Long bidRequestId;//来自于哪个借款标
     private String bidRequestTitle;//标的名称
     private Logininfo bidUser;//投标人
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date bidTime;//投标时间
     private int bidRequestState;//标的状态
}
