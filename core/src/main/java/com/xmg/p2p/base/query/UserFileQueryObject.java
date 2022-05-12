package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 风控材料查询对象
 */
@Setter
@Getter
public class UserFileQueryObject extends QueryObject {
      private int state = -1;
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      private Date beginDate;
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      private Date endDate;
      private Long applierId;

      public Date getEndDate(){
            return endDate == null ? null : DateUtil.endOfDay(endDate);
      }
}
