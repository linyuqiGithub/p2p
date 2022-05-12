package com.xmg.p2p.base.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoQueryObject;


/**
 * 视频认证服务
 */
public interface IVideoService {
    /**
     * 后台视频审核分页
     * @param qo
     * @return
     */
    PageResult query(VideoQueryObject qo);

    /**
     * 后台视频审核操作
     * @param loginInfoValue
     * @param remark
     * @param state
     */
    void audit(Long loginInfoValue, String remark, int state);


}
