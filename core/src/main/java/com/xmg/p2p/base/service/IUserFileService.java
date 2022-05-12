package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;

import java.util.List;

/**
 * 风控材料服务
 */
public interface IUserFileService {
    /**
     * 申请风控材料
     * @param fileName
     */
    void apply(String fileName);

    /**
     * 列出当前用户没有分组的风控文件
     * @param id
     * @return
     */
    List<UserFile> listNoFileType(Long id);

    /**
     * 列出所有风控文件分类组别
     * @param userFileType
     * @return
     */
    List<SystemDictionaryItem> listFileTypeBySn(String userFileType);

    /**
     * 列出当前用户已经完成分类的风控文件
     * @param id
     * @return
     */
    List<UserFile> listHasFileType(Long id);

    /**
     * 更新风控文件的分组类别
     * @param ids
     * @param fileTypes
     */
    void updateFileType(Long[] ids, Long[] fileTypes);

    /**
     * 后台风控材料分页
     * @param qo
     * @return
     */
    PageResult query(UserFileQueryObject qo);

    /**
     * 后台风控材料的审核操作
     * @param id
     * @param state
     * @param score
     * @param remark
     */
    void audit(Long id, int state, int score, String remark);

    /**
     * 根据qo查询对象查出相应的UserFile集合,不做分页
     * @param qo
     * @return
     */
    List<UserFile> queryForList(UserFileQueryObject qo);
}
