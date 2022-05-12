package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;

public interface IRealAuthService {
    void apply(RealAuth vo);

    RealAuth get(Long id);

    PageResult query(RealAuthQueryObject qo);

    void audit(Long id,String remark,int state);
}
