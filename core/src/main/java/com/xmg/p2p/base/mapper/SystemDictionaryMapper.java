package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SystemDictionaryMapper {

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionary> queryForData(SystemDictionaryQueryObject qo);
}