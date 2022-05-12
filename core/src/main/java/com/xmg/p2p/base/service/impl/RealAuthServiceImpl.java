package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.bussiness.event.RealAuthSuccessEvent;
import com.xmg.p2p.base.mapper.RealAuthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {

    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private RealAuthMapper realAuthMapper;
    @Autowired
    private ApplicationContext ctx;
    @Value("${jdc.mykey}")
    private String key;

    public void apply(RealAuth vo) {
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
        //用户没有实名认证而且realAuthId为空
        if(userinfo.getRealAuthId() == null && !userinfo.getIsRealAuth()){
              RealAuth realAuth = new RealAuth();
              realAuth.setRealName(vo.getRealName());
              realAuth.setSex(vo.getSex());
              realAuth.setIdNumber(vo.getIdNumber());
              realAuth.setBornDate(vo.getBornDate());
              realAuth.setAddress(vo.getAddress());
              realAuth.setState(RealAuth.NORMAL_AUDI);
              realAuth.setApplier(UserContext.getCurrent());
              realAuth.setApplyTime(new Date());
              realAuth.setImage1(vo.getImage1());
              realAuth.setImage2(vo.getImage2());
              realAuthMapper.insert(realAuth,key);
              userinfo.setRealAuthId(realAuth.getId());
              userinfoService.update(userinfo);
        }

    }

    public RealAuth get(Long id) {
        return realAuthMapper.selectByPrimaryKey(id,key);
    }

    public PageResult query(RealAuthQueryObject qo) {
        int count = realAuthMapper.queryForCount(qo,key);
        if(count != 0){
            List<RealAuth> data = realAuthMapper.queryForData(qo,key);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public void audit(Long id,String remark,int state) {
        //获取审核对象
        RealAuth realAuth = realAuthMapper.selectByPrimaryKey(id,key);
        //获取对应的userinfo对象
        Userinfo userinfo = userinfoService.get(realAuth.getApplier().getId());
        //用户的审核对象处于审核中状态且用户没有实现实名认证
        if(realAuth.getState() == RealAuth.NORMAL_AUDI && !userinfo.getIsRealAuth()){
            realAuth.setRemark(remark);
            realAuth.setState(state);
            realAuth.setAuditor(UserContext.getCurrent());
            realAuth.setAuditTime(new Date());
            realAuthMapper.updateByPrimaryKey(realAuth);
            //审核通过
             if(state == RealAuth.PASS_AUDI){
                 //修改申请用户的状态和完成实名认证
                 userinfo.setBitState(BitStatesUtils.addState(userinfo.getBitState(),BitStatesUtils.REALAUTH));
                 userinfo.setRealName(realAuth.getRealName());
                 userinfo.setIdNumber(realAuth.getIdNumber());
                 //发布一个实名认证通过的消息
                this.ctx.publishEvent(new RealAuthSuccessEvent(this,realAuth));
             }
             else {
              //审核拒绝(将用户的实名认证id设为null)
              userinfo.setRealAuthId(null);
             }
             userinfoService.update(userinfo);
        }
    }
}
