package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.mapper.VideoAuthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoQueryObject;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVideoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoAuthMapper videoAuthMapper;

    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private LogininfoMapper logininfoMapper;

    public PageResult query(VideoQueryObject qo) {
        int count = videoAuthMapper.queryForCount(qo);
        if(count > 0){
            List<VideoAuth> data = videoAuthMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public void audit(Long id, String remark, int state) {
        //获取对应id的用户信息
        Userinfo userinfo = userinfoService.get(id);
        //如果用户还没有通过视频认证
        if(userinfo != null && !userinfo.getIsVedioAuth()){
            VideoAuth va = new VideoAuth();
            Logininfo logininfo = new Logininfo();
            logininfo.setId(id);
            //VideoAuth只需要一个logininfo的id,不需要其他信息，所以不需要重新去数据库查logininfo，直接创建一个logininfo对象并设置id就好了
            va.setApplier(logininfo);
            va.setApplyTime(new Date());
            va.setAuditor(UserContext.getCurrent());
            va.setAuditTime(new Date());
            va.setRemark(remark);
            va.setState(state);
            videoAuthMapper.insert(va);
            //如果审核通过，添加用户的视频审核状态码
            if(state == VideoAuth.PASS_AUDI){
                userinfo.setBitState(BitStatesUtils.addState(userinfo.getBitState(),BitStatesUtils.VEDIOAUTH));
                userinfoService.update(userinfo);
            }

        }
    }


}
