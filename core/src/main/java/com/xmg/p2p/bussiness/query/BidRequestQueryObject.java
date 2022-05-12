package com.xmg.p2p.bussiness.query;

import com.xmg.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 借款查询对象
 */
@Setter
@Getter
public class BidRequestQueryObject extends QueryObject {
    private int bidRequestState = -1;
    private int[] bidRequestStates;
    private String orderBy;
    private String orderByType;
    private Long createUserId;

}
