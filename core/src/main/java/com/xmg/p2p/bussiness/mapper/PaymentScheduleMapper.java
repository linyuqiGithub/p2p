package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.PaymentSchedule;
import com.xmg.p2p.bussiness.query.ReturnMoneyQueryObject;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentScheduleMapper {

    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    List<PaymentSchedule> selectAll();

    int updateByPrimaryKey(PaymentSchedule record);

    int queryForCount(ReturnMoneyQueryObject qo);

    List<PaymentSchedule> queryForData(ReturnMoneyQueryObject qo);

    List<PaymentSchedule> selectByUserId(Long id);
}