package com.xmg.p2p.bussiness.query;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 提现申请查询对象
 * 
 * @author Administrator
 * 
 */
@Getter
@Setter
public class MoneyWithdrawQueryObject extends QueryObject {
	private int state = -1;
	private Date beginDate;
	private Date endDate;
	private Long applierId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate == null ? null : DateUtil.endOfDay(endDate);
	}
}
