package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealAuthMapper {


    int insert(@Param("record")RealAuth record,@Param("key")String key);

    RealAuth selectByPrimaryKey(@Param("id")Long id,@Param("key")String key);

    int updateByPrimaryKey(RealAuth record);

    int queryForCount(@Param("qo")RealAuthQueryObject qo,@Param("key")String key);

    List<RealAuth> queryForData(@Param("qo")RealAuthQueryObject qo,@Param("key")String key);


}