package com.xmg.p2p.base.util;

/**
 * 存放当前线程需要访问的DS的名字
 */

public class DataSourceContext {
    private static ThreadLocal<String> datasourcePool = new ThreadLocal<String>();

    public static void set(String dsName) {
        datasourcePool.set(dsName);
    }

    public static String get() {
        return datasourcePool.get();
    }

    /**
     * 标记读库
     */
    public static void setSlaveDS() {
        datasourcePool.set(Consts.SLAVE_DS);
    }

    /**
     * 标记写库
     */
    public static void setMasterDS() {
        datasourcePool.set(Consts.MASTER_DS);
    }



}
