package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.mapper.IpLogMapper;
import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIpLogService;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.DataSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IpLogServiceImpl implements IIpLogService {
    @Autowired
    private IpLogMapper ipLogMapper;

    public PageResult query(IpLogQueryObject qo) {
        //设置此次查询为到从数据库中查询
        DataSourceContext.set(Consts.SLAVE_DS);
        Long count = ipLogMapper.queryForCount(qo);
        if(count != 0){
            List<IpLog> data = ipLogMapper.query(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count.intValue(),data);
        }
         return PageResult.getEmpty(qo.getPageSize());
    }
}
