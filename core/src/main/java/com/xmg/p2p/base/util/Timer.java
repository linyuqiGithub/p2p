package com.xmg.p2p.base.util;

import com.xmg.p2p.base.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 定时器:完成对account的数据检查
 */
public class Timer {

    @Autowired
    private IAccountService accountService;

    public void timeTask() {
        List<Long> ids = accountService.getIds();
        try {
            for (Long id : ids) {
                accountService.get(id);
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            return;
        }
        System.out.println(new Date().toLocaleString()+"数据检查完毕，没有发现异常！");
    }
}
