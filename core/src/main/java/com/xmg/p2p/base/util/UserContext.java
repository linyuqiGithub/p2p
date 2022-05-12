package com.xmg.p2p.base.util;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 当前用户上下文
 */
public class UserContext {

    public static final String USER_IN_SESSION = "logininfo";
    public static final String VERIFYCODE_IN_SESSION = "vo";

    public static HttpSession getSession(){
       return ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
    }

    /**
     * 缓存当前用户
     * @param logininfo
     */
    public static void setCurrent(Logininfo logininfo){
        getSession().setAttribute(USER_IN_SESSION,logininfo);
    }

    /**
     * 获取当前用户
     * @return
     */
    public static Logininfo getCurrent(){
       return (Logininfo)getSession().getAttribute(USER_IN_SESSION);
    }

    /**
     * 缓存当前验证码对象
     * @param vo
     */
    public static void setVerifyCodeVO(VerifyCodeVO vo){
          getSession().setAttribute(VERIFYCODE_IN_SESSION,vo);
    }

    /**
     * 获取当前验证码对象
     * @return
     */
    public static VerifyCodeVO getVerifyVO(){
        return (VerifyCodeVO) getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }

    /**
     * 注销当前用户
     */
    public static void invalidateSession(){
        getSession().invalidate();
    }
}
