package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

    int queryForCount(SystemDictionaryItemQueryObject qo);


    List<SystemDictionaryItem> queryForData(SystemDictionaryItemQueryObject qo);

    List<SystemDictionaryItem> listBySn(String sn);
}