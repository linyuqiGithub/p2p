package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Userinfo;


public interface IUserinfoService {
    /**
     * 所有的用户信息的修改都必须调用这个方法，这里面有乐观锁的判断
     * @param userinfo
     * @return
     */
    int update(Userinfo userinfo);
    void save(Userinfo userinfo);
    Userinfo get(Long id);

    /**
     * 个人用户特定资料的更新
     * @param vo
     */
    void updateBaseInfo(Userinfo vo);

    /**
     * 获取当前用户的个人信息对象
     * @return
     */
    Userinfo getCurrent();
}
