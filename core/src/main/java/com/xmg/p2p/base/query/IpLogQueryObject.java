package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;


@Setter@Getter
public class IpLogQueryObject extends QueryObject {
      //从My97DatePicker插件中拿到的日期格式是yyyy-MM-dd,而Date的存储格式是yyyy/MM/dd,所以需要改变Date的日期格式
      //否则会出现400的错误
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      private Date beginDate;
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      private Date endDate;
      private int state = -1;
      private String username;
      //用户类型(前台后台用户)
      private int userType = -1;

      //起始时间不需要调用工具类,因为那个日期工具类WdatePicker默认拿的时间是00:00:00
      //获取截止时间的23:59:59
      //防止查询同一天时起始时间和结束时间都是 2022-2-22 00:00:00 ~ 2022-2-22 00:00:00
      public Date getEndDate(){
           return endDate == null ? null : DateUtil.endOfDay(endDate);
      }

      //重写username的getter方法，防止后台管理员高级查询登录记录时提交空字符串的用户名导致空指针异常
      public String getUsername(){
            return StringUtils.hasLength(username) ? username : null;
      }
}
