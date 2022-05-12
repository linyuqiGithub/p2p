package com.xmg.p2p.base.test;

import com.xmg.p2p.base.mapper.AccountMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试方法用完之后记得注释，否则打包的时候会不断的运行
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestApp {
    @Autowired
    private AccountMapper accountMapper;

   /* @Test
    public void test(){
        List<Account> accounts = accountMapper.selectAll();
        for (Account account : accounts){
            accountMapper.updateByPrimaryKey(account);
        }
    }*/
}
