package com.xmg.p2p.bussiness.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;



@Setter
@Getter
public class ExpAccountGrantRecord extends BaseDomain {
    public static final int STATE_NORMAL = 0;
    public static final int START_RETURN = 1;

    private Long grantUserId;
    private BigDecimal amount;
    private Date grantDate;
    private Date returnDate;
    private int grantType;
    private String note;
    private int state;
}
