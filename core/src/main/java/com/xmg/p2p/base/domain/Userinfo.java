package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户基本资料
 */
@Setter@Getter
public class Userinfo extends BaseDomain {
    //乐观锁版本号
    private int version;
    //用户状态值
    private Long bitState;
    //真实姓名
    private String realName;
    //身份证号
    private String idNumber;
    //电话号码
    private String phoneNumber;
    //邮箱
    private String email;
    //材料风控分数
    private int score = 0;
    //收入
    private SystemDictionaryItem incomeGrade;
    //婚姻情况
    private SystemDictionaryItem marriage;
    //子女情况
    private SystemDictionaryItem kidCount;
    //学历
    private SystemDictionaryItem educationBackground;
    //住房条件
    private SystemDictionaryItem houseCondition;
    //用户实名认证对象id
    private Long realAuthId;

    /**
     * 判断该用户是否绑定手机
     * @return
     */
    public boolean getHasBindPhone(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_PHONE);
    }

    /**
     * 判断该用户是否绑定邮箱
     * @return
     */
    public boolean getHasBindEmail(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_EMAIL);
    }

    /**
     * 判断用户是否填写基本资料
     * @return
     */
    public boolean getIsBasicInfo(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.BASICINFO);
    }

    /**
     *判断用户是否完成实名认证
     * @return
     */
    public boolean getIsRealAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.REALAUTH);
    }

    /**
     * 判断用户是否完成视频认证
     * @return
     */
    public boolean getIsVedioAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.VEDIOAUTH);
    }

    /**
     * 判断用户当前是否有一个借款在审核流程中
     * @return
     */
    public boolean getHasbidRequetInProcess(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.HASBINDREQUEST);
    }

    /**
     * 用户是否绑定银行卡
     */
    public boolean getIsBindBank(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_BANK);
    }

    /**
     * 判断用户当前是否有一个提现在审核流程中
     * @return
     */
    public boolean getHasWithdrawProcess(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS);
    }

}


