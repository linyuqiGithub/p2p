package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.mapper.MailVerifyMapper;
import com.xmg.p2p.base.service.IMailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailVerifyServiceImpl implements IMailVerifyService {
    @Autowired
    private MailVerifyMapper mailVerifyMapper;

    public void save(MailVerify mailVerify) {
        mailVerifyMapper.insert(mailVerify);
    }

    public MailVerify selectByUUID(String key) {
        return mailVerifyMapper.selectByUUID(key);
    }
}
