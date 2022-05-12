package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典明细
 */
@Setter@Getter
public class SystemDictionaryItem extends BaseDomain {
    private Long parentId;
    private String title;
    //数字字典顺序
    private Integer sequence;

    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("parentId",parentId);
        map.put("title",title);
        map.put("sequence",sequence);
        return JSON.toJSONString(map);
    }
}
