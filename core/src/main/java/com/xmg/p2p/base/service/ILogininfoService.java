package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Logininfo;

import java.util.List;
import java.util.Map;


public interface ILogininfoService {
     Logininfo regist(String username, String password);
     boolean check(String usertname);

    Logininfo login(String username, String password,String ip,int userType);

    List<Map<String,Object>> autocomplate(String keyword);
}
