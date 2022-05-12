package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.IpLogMapper;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;



@Service
public class LogininfoServiceImpl implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IpLogMapper ipLogMapper;

    public Logininfo regist(String username, String password) {
        int count = logininfoMapper.selectByName(username);
        //如果用户名不存在，创建新用户
        if(count==0){
            Logininfo lofo = new Logininfo();
            lofo.setUsername(username);
            lofo.setPassword(MD5.encode(password+"["+username+"]"));
            lofo.setState(Logininfo.NOMAL);
            lofo.setUserType(Logininfo.TYPE_CLIENT);
            logininfoMapper.insert(lofo);
            //int i  =  1 / 0;
            //注册成功的时候创建用户的Account和Userinfo对象
            Account account = new Account();
            account.setId(lofo.getId());
            accountService.save(account);
            Userinfo userinfo = new Userinfo();
            userinfo.setId(lofo.getId());
            userinfoService.save(userinfo);
            return lofo;
        }else {
            //用户已存在，抛出异常
            throw new RuntimeException("用户名已经存在");
        }
    }

    public boolean check(String username){
        int count = logininfoMapper.selectByName(username);
        if(count != 0){
           return false;
        }else {
           return true;
        }
    }

    public Logininfo login(String username, String password,String ip,int userType) {
        password = MD5.encode(password+"["+username+"]");
        //该logininfo可能是前台用户也可能是后台用户,
        Logininfo logininfo = logininfoMapper.selectByNameAndPassword(username,password,userType);
        //创建登陆日志对象
        IpLog ipLog = new IpLog();
        ipLog.setUsername(username);
        ipLog.setLoginTime(new Date());//本来存入数据库是日期格式不是中国风格的,全是因为,Navicat
        ipLog.setIp(ip);
        //记录当前用户是前台用户还是后台用户(即使使用前台用户的账号登陆后台，无法匹配logininfo,为null,此记录仍要记录在ipLog中)
        ipLog.setUserType(userType);
        if(logininfo != null){
            UserContext.setCurrent(logininfo);
            //设置登陆成功状态
            ipLog.setState(1);
        }else{
            //设置登陆失败状态
            ipLog.setState(0);
         }
        //保存登陆日志
        ipLogMapper.insert(ipLog);
       return logininfo;
  }

    public List<Map<String, Object>> autocomplate(String keyword) {
        return logininfoMapper.autocomplate(keyword);
    }


}
