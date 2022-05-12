package com.xmg.p2p.mgrsite.util;

import com.xmg.p2p.base.util.Consts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 同步图片过滤器
 */

public class SynImgFilter implements Filter {
    public ServletContext ctx;
    public void init(FilterConfig filterConfig) throws ServletException {
        ctx = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //得到此次要请求的图片(/upload/2342hij32i3j42.jpg)
        String file = req.getRequestURI();
        //到本地应用找该图片(/upload/2342hij32i3j42.jpg)
        String fileFullPath = ctx.getRealPath(file);
        // 如果图片存在，放行(在mgrsite本地的upload文件夹查看有无这个文件)
        File localFile = new File(fileFullPath);
        if (localFile.exists()){
            //本地存在该图片，直接在本地upload文件夹取该图片，直接放行
            filterChain.doFilter(req,resp);
        }else {
            // 如果不存在，去公共文件夹拷贝
            File publicFile = new File(Consts.PUBLIC_IMG_SHARE_PATH, FilenameUtils.getName(file));
            if(publicFile.exists()){
                //将公共文件夹中的图片拷贝到本地的文件夹中
                FileUtils.copyFile(publicFile,localFile);
                // 然后放行(放行后有可能文件还在拷贝中，这样取图片的时候可能仍取不到)
                //filterChain.doFilter(req,resp);
                //直接将公共文件夹中的图片响应给请求，这样取图片的响应不需要直接在本地的upload文件夹中取，直接将获取的公共文件夹中的图片返回给他
                //这样也就不需要等到图片拷贝完成了才能获取了
                resp.setHeader("Cache-Control", "no-store");
                resp.setHeader("Pragma", "no-cache");
                resp.setDateHeader("Expires", 0);
                ServletOutputStream responseOutputStream = servletResponse.getOutputStream();
                responseOutputStream.write(FileUtils.readFileToByteArray(publicFile));
                responseOutputStream.flush();
                responseOutputStream.close();
            }

        }
    }

    public void destroy() {

    }
}
