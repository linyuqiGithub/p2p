package com.xmg.p2p.base.service.impl;


import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IMailVerifyService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.Consts;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.internet.MimeMessage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;


@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    //使用@Value标签注入简单值,类似于@Autowire用于注入对象
    //模拟的短信平台地址
    @Value("${sms.url}")
    private String url;
    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.apikey}")
    private String apikey;

    //云之讯短信平台
    /*@Value("${yunzhixun.sid}")
    private String sid;
    @Value("${yunzhixun.token}")
    private String token;
    @Value("${yunzhixun.appid}")
    private String appid;*/

    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IMailVerifyService mailVerifyService;

    @Value("${email.hostUrl}")
    private String hostUrl;
    @Value("${email.host}")
    private String host;
    @Value("${email.useraddress}")
    private String useraddress;
    @Value("${email.pwd}")
    private String pwd;


    public void sendVerifyCode(String phoneNumber) {
        //String result = null;
        //判断是否能够发送验证码(已经发送过验证码且发送间隔没有超过90s不能再次发送验证码)
        if (UserContext.getVerifyVO() != null && (DateUtil.secondsBetween(new Date(), UserContext.getVerifyVO().getSendTime()) <= Consts.MESSAGE_SEND_INTERVAL)) {
            throw new RuntimeException("发送短信过于频繁，请稍后再试");
        }
        try {
            //创建一个验证码
            String verifyCode = UUID.randomUUID().toString().substring(0, 4);
            Date sendTime = new Date();

            //创建一个URL对象
            URL url = new URL(this.url);
            //使用url创建连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置相关属性
            conn.setRequestMethod("POST");
            //设置要输出实体内容
            conn.setDoOutput(true);
            //设置输出的内容
            //拼接短信内容
            String content = "你收到的验证码是" + verifyCode + ",有效时间为" + Consts.MESSAGE_VAILID_TIME + "分钟";
            //拼接请求的参数
            StringBuilder sb = new StringBuilder(100).append("username=").append(username).append("&password=").append(password)
                    .append("&apikey=").append(apikey).append("phoneNumber=").append(phoneNumber).append("&content=" + URLEncoder.encode(content, "UTF-8"));
            conn.getOutputStream().write(sb.toString().getBytes());
            conn.connect();

            //获取响应内容
            String ret = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("UTF-8"));
            //判断是否发送成功
            if (ret.indexOf("success:") == 0) {
                VerifyCodeVO vo = new VerifyCodeVO(sendTime, verifyCode, phoneNumber);
                //将验证码对象存入session中
                UserContext.setVerifyCodeVO(vo);
            } else {
                throw new RuntimeException();
            }
            /*JsonReqClient jsonReqClient = new JsonReqClient();
            result = jsonReqClient.sendSms(sid, token, appid, "62222", verifyCode, phoneNumber, "1");
            return result;*/
        } catch (Exception e) {
            throw new RuntimeException("发送失败,请重新发送");
        }
    }

    public void bindPhone(String phoneNumber, String verifyCode) {
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
        //判断当前用户是否已经绑定了手机号码(没有绑定过才往下执行)
        if (!userinfo.getHasBindPhone()) {
            //从session中获取验证码对象(保证确实已经点击了发送验证码的按钮)
            VerifyCodeVO vo = UserContext.getVerifyVO();
            //已发送验证码&&判断验证码没有失效(没有超过3分钟)&&用户收到验证码的手机号和发送过来的手机号一致&&用户收到的验证码和发送过来的验证码一致
            if (vo != null && DateUtil.secondsBetween(new Date(), vo.getSendTime()) < Consts.MESSAGE_VAILID_TIME * 60 && phoneNumber.equals(vo.getPhoneNumber()) && verifyCode.equals(vo.getVerifyCode())) {
                //添加绑定状态码返回新的状态码
                long state = BitStatesUtils.addState(userinfo.getBitState(), BitStatesUtils.OP_BIND_PHONE);
                userinfo.setBitState(state);
                userinfo.setPhoneNumber(phoneNumber);
                userinfoService.update(userinfo);
            } else {
                throw new RuntimeException("验证失败,请重新验证");
            }
        } else {
            throw new RuntimeException("手机已绑定,不能重复绑定");
        }
    }

    public boolean sendEmail(String email) {
        try {
            if (email != null) {
                Long userId = UserContext.getCurrent().getId();
                Userinfo userinfo = userinfoService.get(userId);
                //只有用户还没有绑定过邮箱，才继续执行
                if (!userinfo.getHasBindEmail()) {
                    //创建UUID
                    String uuid = UUID.randomUUID().toString();
                    Date sendTime = new Date();
                    //保存一个发送邮件的历史
                    MailVerify mv = new MailVerify();
                    mv.setUserId(userId);
                    mv.setSendTime(sendTime);
                    mv.setEmail(email);
                    mv.setUuid(uuid);
                    mailVerifyService.save(mv);
                    //构建邮件内容
                    StringBuilder content = new StringBuilder().append("请点击<a href='").append(hostUrl).append("bindEmail.do?key=").append(uuid).append("'>这里</a>进行绑定,有效期为").append(Consts.MAIL_VALID_DAYS).append("天");
                    //String content = "请点击<a href='http://localhost:8081/bindEmail.do?key=" + uuid + "'>这里</a>进行绑定";


                    JavaMailSenderImpl sender = new JavaMailSenderImpl();
                    //设置mail服务器地址
                    sender.setHost(host);
                    //建立邮件消息
                    MimeMessage message = sender.createMimeMessage();
                    //使用Spring的MailHelper助手对javamail的MimeMessage进行包装(使用utf-8防止中文出现乱码)
                    MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                    //收件邮箱地址
                    helper.setTo(email);
                    //发件邮箱地址
                    helper.setFrom(useraddress);
                    //邮箱主题
                    helper.setSubject("验证邮箱邮件");
                    //邮箱内容
                    helper.setText(content.toString(), true);
                    //发件邮箱用户名
                    sender.setUsername(useraddress);
                    //发件邮箱密码(授权码)
                    sender.setPassword(pwd);


                    Properties p = new Properties();
                    //需要对smtp服务器进行认证
                    p.put("mail.smtp.auth", true);
                    //smtp服务器的超时时间
                    p.put("mail.smtp.timeout", "25000");

                    sender.setJavaMailProperties(p);
                    //发送邮件
                    sender.send(message);
                }
            } else {
                throw new RuntimeException("邮箱不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送邮件失败");
        }
        return true;
    }

    public boolean bindEmail(String key) {
        //根据传递过来的uuid查询发送邮件时存储的MailVerify，确定uuid是否相同
        MailVerify mv = mailVerifyService.selectByUUID(key);
        if (mv != null) {
            //用户是否还没有绑定邮箱
            Userinfo userinfo = userinfoService.get(mv.getUserId());
            if (!userinfo.getHasBindEmail()) {
                //邮件是否在有效期内
                if (DateUtil.secondsBetween(new Date(), mv.getSendTime()) <= 24 * 3600) {
                    String email = mv.getEmail();
                    userinfo.setEmail(email);
                    userinfo.setBitState(BitStatesUtils.addState(userinfo.getBitState(), BitStatesUtils.OP_BIND_EMAIL));
                    userinfoService.update(userinfo);
                    return true;
                } else {
                    throw new RuntimeException("邮件已经失效,请重新发送");
                }
            } else {
                throw new RuntimeException("绑定失败,该邮箱已被使用");
            }
        }
        throw new RuntimeException("绑定失败,请确认邮箱是否正确");
    }
}
