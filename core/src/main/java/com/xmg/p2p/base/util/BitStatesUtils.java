package com.xmg.p2p.base.util;

/**
 * 用户状态码工具类 ，记录用户在平台使用系统中所有的状态。
 * 
 * @author Administrator
 */
public class BitStatesUtils {

	public final static Long OP_BIND_PHONE = 1L << 0; // 用户绑定手机状态码
	public final static Long OP_BIND_EMAIL = 1L << 1; // 用户绑定邮箱状态码
	public final static Long BASICINFO = 1L << 2; //填写个人信息状态码
    public final static Long REALAUTH = 1L << 3; //实名认证状态码
    public final static Long VEDIOAUTH = 1L << 4; //视频认证状态码
    public final static Long HASBINDREQUEST = 1L << 5; //用户当前有一个借款在审核流程中
	public final static Long OP_BIND_BANK = 1L << 6; //用户绑定银行卡状态码
	public final static Long OP_HAS_MONEYWITHDRAW_PROCESS = 1L << 7;// 用户是否有一个提现申请在处理中


	/**
	 * 判断是否拥有该状态
	 * @param states
	 *            所有状态值
	 * @param value
	 *            需要判断状态值
	 * @return 是否存在
	 */
	public static boolean hasState(long states, long value) {
		return (states & value) != 0;
	}

	/**
	 * 添加状态值
	 * @param states
	 *            已有状态值
	 * @param value
	 *            需要添加状态值
	 * @return 新的状态值
	 */
	public static long addState(long states, long value) {
		if (hasState(states, value)) {
			//如果已经存在该状态value，直接返回该状态值states
			return states;
		}
		//如果不存在该状态值value，添加该状态值并返回添加后新的状态值states
		return (states | value);
	}

	/**
	 * 删除状态值
	 * @param states
	 *            已有状态值
	 * @param value
	 *            需要删除状态值
	 * @return 新的状态值
	 */
	public static long removeState(long states, long value) {
		if (!hasState(states, value)) {
			//如果不存在该状态值value，直接返回该状态值states
			return states;
		}
		//如果存在该状态值value，删除该状态值value并返回删除后新的状态值states
		return states ^ value;
	}

}
