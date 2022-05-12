package com.xmg.p2p.base.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * P2P中的路由DataSource
 */
public class P2PRoutingDataSource extends AbstractRoutingDataSource {
    /**
     * 这个方法需要返回当前目标的DS的名字
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.get();
    }
}
