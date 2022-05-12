package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.MailVerify;
import org.springframework.stereotype.Repository;

@Repository
public interface MailVerifyMapper {

    int insert(MailVerify record);

    MailVerify selectByUUID(String key);

}