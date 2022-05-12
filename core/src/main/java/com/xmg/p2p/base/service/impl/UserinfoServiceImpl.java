package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserinfoServiceImpl implements IUserinfoService {
    @Autowired
    private UserinfoMapper userinfoMapper;

    public int update(Userinfo userinfo) {
        //row为SQL影响行数，用于判断乐观锁是否生效
        int row = userinfoMapper.updateByPrimaryKey(userinfo);
        if(row != 0){
            return 1;
        }
          //影响行数为0，乐观锁生效，抛出异常，让事务回滚
          throw new RuntimeException("乐观锁失败"+userinfo.getId());
    }

    public void save(Userinfo userinfo) {
        userinfoMapper.insert(userinfo);
    }

    public Userinfo get(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    public void updateBaseInfo(Userinfo vo) {
        //获取当前用户
        Userinfo userinfo = userinfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
        //只存储个人用户信息允许更新的数据
        userinfo.setEducationBackground(vo.getEducationBackground());
        userinfo.setIncomeGrade(vo.getIncomeGrade());
        userinfo.setHouseCondition(vo.getHouseCondition());
        userinfo.setKidCount(vo.getKidCount());
        userinfo.setMarriage(vo.getMarriage());
        //判断该用户是否为第一次个人信息认证,是则改变为已完成个人信息认证状态码,不是第一次认证则不需要改变
        if(!userinfo.getIsBasicInfo()){
            userinfo.setBitState(BitStatesUtils.addState(userinfo.getBitState(),BitStatesUtils.BASICINFO));
        }
        userinfoMapper.updateByPrimaryKey(userinfo);
    }

    @Override
    public Userinfo getCurrent() {
        return userinfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
    }
}
