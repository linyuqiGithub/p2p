package com.xmg.p2p.bussiness.query;

import com.xmg.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class BidRequestAuditQueryObject extends QueryObject {
    private int bidRequestState = -1;
}
