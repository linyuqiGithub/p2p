package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.UserFileQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserFileMapper {

    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserFile record);

    List<UserFile> listNoFileType(Long id);

    List<SystemDictionaryItem> listFileTypeBySn(String userFileType);

    List<UserFile> listHasFileType(Long id);

    int queryForCount(UserFileQueryObject qo);

    List<UserFile> queryForData(UserFileQueryObject qo);

}