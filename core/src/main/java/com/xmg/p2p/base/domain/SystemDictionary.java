package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典
 */
@Setter@Getter
public class SystemDictionary extends BaseDomain {
    private String sn;
    private String title;

    /**
     * 将当前类的数据封装到JSON对象中，前台页面需要修改当前类的数据时，直接从当前的JSON对象中拿到相应的数据回显在编辑框中
     * @return
     */
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("sn",sn);
        map.put("title",title);
        return JSON.toJSONString(map);
    }
}
