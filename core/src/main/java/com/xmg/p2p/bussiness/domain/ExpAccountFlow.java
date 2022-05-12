package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
public class ExpAccountFlow extends BaseDomain {
    private Long expAccountId;
    private Date actionTime;
    private BigDecimal amount;
    private int actionType;
    private String note;
    private BigDecimal usableAmount;
    private BigDecimal freezedAmount;
}
