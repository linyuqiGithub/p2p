package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Userinfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(Long id);

    List<Userinfo> selectAll();

    int updateByPrimaryKey(Userinfo record);
}