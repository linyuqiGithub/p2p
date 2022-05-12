package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统账户流水
 */
@Setter
@Getter
public class SystemAccountFlow extends BaseDomain {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private int accountActionType;
    private BigDecimal amount;
    private String note;
    private BigDecimal balance;//变化后的可用余额
    private BigDecimal freezedAmount;//变化后的冻结金额
}
