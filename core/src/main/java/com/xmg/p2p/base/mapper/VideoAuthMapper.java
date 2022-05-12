package com.xmg.p2p.base.mapper;


import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.query.VideoQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoAuthMapper {

    int insert(VideoAuth record);

    VideoAuth selectByPrimaryKey(Long id);

    int queryForCount(VideoQueryObject qo);

    List<VideoAuth> queryForData(VideoQueryObject qo);


}