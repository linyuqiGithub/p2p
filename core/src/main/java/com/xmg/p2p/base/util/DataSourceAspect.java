package com.xmg.p2p.base.util;

import org.aspectj.lang.JoinPoint;
import org.springframework.util.StringUtils;

public class DataSourceAspect {

    public void before(JoinPoint joinPoint){
        //获取当前方法
        String methodName = joinPoint.getSignature().getName();
        if (isSlave(methodName)){
            DataSourceContext.setSlaveDS();
        }else {
            DataSourceContext.setMasterDS();
        }
    }

    /**
     * 判断是否为读库
     */
    public boolean isSlave(String methodName){
        return StringUtils.startsWithIgnoreCase(methodName,"query");
    }
}
