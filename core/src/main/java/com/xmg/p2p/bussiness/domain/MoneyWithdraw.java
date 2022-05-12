package com.xmg.p2p.bussiness.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现申请对象
 *
 * @author Administrator
 */
@Getter
@Setter
public class MoneyWithdraw extends BaseDomain {

    public static final int STATE_NORMAL = 0;// 正常
    public static final int STATE_AUDIT = 1;// 审核通过
    public static final int STATE_REJECT = 2;// 审核拒绝

    private BigDecimal amount;// 提现金额
    private BigDecimal charge;// 提现手续费
    private String bankName;// 银行名称
    private String accountName;// 开户人姓名
    private String accountNumber;// 银行账号
    private String bankForkName;// 开户支行
    private Logininfo applier;
    private Date applyTime;
    private Logininfo auditor;
    private Date auditTime;
    protected String remark;// 审核备注
    protected int state;// 状态

    public String getJsonString() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id", id);
        json.put("username", this.applier.getUsername());
        json.put("realName", accountName);
        json.put("applyTime", DateFormat.getDateTimeInstance().format(applyTime));
        json.put("bankName", bankName);
        json.put("accountNumber", accountNumber);
        json.put("bankforkname", bankForkName);
        json.put("moneyAmount", amount);
        return JSONObject.toJSONString(json);
    }

    public String getStateDisplay() {
        switch (state) {
            case STATE_NORMAL:
                return "待审核";
            case STATE_AUDIT:
                return "审核通过";
            case STATE_REJECT:
                return "审核拒绝";
            default:
                return "";
        }
    }
}
