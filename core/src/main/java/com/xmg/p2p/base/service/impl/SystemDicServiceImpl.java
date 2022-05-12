package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryItemMapper;
import com.xmg.p2p.base.mapper.SystemDictionaryMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SystemDicServiceImpl implements ISystemDicService{
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    public PageResult query(SystemDictionaryQueryObject qo) {
        int count = systemDictionaryMapper.queryForCount(qo);
        if(count >0){
            List<SystemDictionary> data = systemDictionaryMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public int saveOrUpdate(SystemDictionary systemDictionary) {
          //根据提交上来的数据字典对象是否有id确定是保存还是更新
          if(systemDictionary.getId()==null){
              return systemDictionaryMapper.insert(systemDictionary);
          }else {
               return systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
          }
    }

    public PageResult queryItem(SystemDictionaryItemQueryObject qo) {
        int count = systemDictionaryItemMapper.queryForCount(qo);
        if(count != 0){
            List<SystemDictionaryItem> data = systemDictionaryItemMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public List<SystemDictionary> getAllDictionary() {
        return systemDictionaryMapper.selectAll();
    }

    public int saveOrUpdateItem(SystemDictionaryItem systemDictionaryItem) {
        if(systemDictionaryItem.getId()==null){
            return systemDictionaryItemMapper.insert(systemDictionaryItem);
        }
        return systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }

    public List<SystemDictionaryItem> listBySn(String sn) {
        return systemDictionaryItemMapper.listBySn(sn);
    }
}
