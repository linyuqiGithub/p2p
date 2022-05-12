package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.MailVerify;


public interface IMailVerifyService {
    void save(MailVerify mailVerify);

    MailVerify selectByUUID(String key);
}
