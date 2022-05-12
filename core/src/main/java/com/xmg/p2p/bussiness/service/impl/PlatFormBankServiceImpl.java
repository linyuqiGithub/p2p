package com.xmg.p2p.bussiness.service.impl;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.bussiness.domain.PlatFormBank;
import com.xmg.p2p.bussiness.mapper.PlatFormBankMapper;
import com.xmg.p2p.bussiness.query.PlatFormBankQueryObject;
import com.xmg.p2p.bussiness.service.IPlatFormBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlatFormBankServiceImpl implements IPlatFormBankService {
    @Autowired
    private PlatFormBankMapper platFormBankMapper;

    public PageResult query(PlatFormBankQueryObject qo) {
        int count = platFormBankMapper.queryForCount(qo);
        if(count > 0){
          List<PlatFormBank> data =  platFormBankMapper.queryForData(qo);
          return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public void saveOrUpdate(PlatFormBank platFormBank) {
         if(platFormBank.getId() == null){
             platFormBankMapper.insert(platFormBank);
         }else{
             platFormBankMapper.updateByPrimaryKey(platFormBank);
         }
    }

    public List<PlatFormBank> listAll() {
        return platFormBankMapper.selectAll();
    }
}
