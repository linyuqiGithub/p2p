package com.xmg.p2p.website.util;

import com.xmg.p2p.base.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆检查拦截器
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{

    //处理之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果当前请求是属于某一个controller的方法，则SpringMVC会将方法包装成HandlerMethod
        if(handler instanceof HandlerMethod){
            //得到当前请求的方法
            HandlerMethod hm = (HandlerMethod) handler;
            //拿到当前方法的注解(RequiredLogin)
            RequiredLogin annotation = hm.getMethodAnnotation(RequiredLogin.class);
            if(annotation != null && UserContext.getCurrent() == null){
                //判断该方法有必须登陆的注解，且没有经过登陆
                response.sendRedirect("/login.html");
            return false;
            }
        }
         //通过了登陆检查，放行
         return super.preHandle(request,response,handler);
    }
}
