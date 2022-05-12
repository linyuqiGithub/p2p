package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * 数据字典服务
 */
public interface ISystemDicService {
    /**
     * 查询数据字典分类
     * @param qo
     * @return
     */
    PageResult query(SystemDictionaryQueryObject qo);

    /**
     * 添加或修改数据字典分类
     * @param systemDictionary
     * @return
     */
   int saveOrUpdate(SystemDictionary systemDictionary);

    /**
     * 查询数据字典明细
     * @param qo
     * @return
     */
    PageResult queryItem(SystemDictionaryItemQueryObject qo);

    /**
     * 列出所有的数据字典分类
     * @return
     */
    List<SystemDictionary> getAllDictionary();

    /**
     * 添加或修改数据字典明细
     * @param systemDictionaryItem
     * @return
     */
    int saveOrUpdateItem(SystemDictionaryItem systemDictionaryItem);

    /**
     * 列出所有匹配sn的数据字典分类下的所有数据字典明细
     * @param sn
     * @return
     */
    List<SystemDictionaryItem> listBySn(String sn);
}
