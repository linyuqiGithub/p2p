package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 视频认证查询对象
 */
@Setter
@Getter
public class VideoQueryObject extends QueryObject {
    private int state = -1;
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Date getEndDate(){
        return endDate == null ? null : DateUtil.endOfDay(endDate);
    }
}
