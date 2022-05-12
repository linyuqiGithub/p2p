package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.util.Consts;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class ExpAccount extends BaseDomain {
    private int version;
    private BigDecimal usableAmount = Consts.ZERO;
    private BigDecimal freezedAmount = Consts.ZERO;
    private BigDecimal unReturnExpAmount = Consts.ZERO;
}
