package com.xmg.p2p.bussiness.mapper;

import com.xmg.p2p.bussiness.domain.PaymentScheduleDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

    List<PaymentScheduleDetail> selectAll();

    int updateByPrimaryKey(PaymentScheduleDetail record);

    /**
     * 设置还款时间
     * @param paymentscheduleId
     * @param paydate
     */
    void updatePayDate(@Param("paymentscheduleId") Long paymentscheduleId, @Param("paydate") Date paydate);
}