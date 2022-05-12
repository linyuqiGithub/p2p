package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface LogininfoMapper {

    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Logininfo record);

    int selectByName(String username);

    Logininfo selectByNameAndPassword(@Param("username")String username,@Param("password")String password,@Param("userType") int userType);

    List<Map<String,Object>> autocomplate(String keyword);
}