package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 系统账户
 */
@Setter
@Getter
public class SystemAccount extends BaseDomain {
    private int version;//处理乐观锁
    private BigDecimal totalBalance;//可用余额
    private BigDecimal freezedAmount;//冻结金额
}
