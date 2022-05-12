package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 风控材料对象
 */

@Setter
@Getter
public class UserFile extends BaseDomain {

   /*  static {
         Properties prop = new Properties();
         InputStream inputStream = UserFile.class.getClassLoader().getResourceAsStream("pic.properties");
         try {
             prop.load(inputStream);
         } catch (IOException e) {
             e.printStackTrace();
         }
         hostUrl = prop.getProperty("pic.picUrl");
     }*/

     public static int NORMAL_AUDI = 0;//未认证
     public static int PASS_AUDI = 1;//通过认证
     public static int REJECT_AUDI = 2;//拒绝认证

     private String image;//风控材料图片
     private SystemDictionaryItem fileType;//风控材料类型
     private int state;//状态
     private Logininfo applier;//申请人
     private Logininfo auditor;//审核人
     private Date applyTime;//申请时间
     private Date auditTime;//审核时间
     private int score;//分数
     private String remark;//备注
     //private static String hostUrl;//存储图片的相对路径

    public String getStateDisplay(){
        if(state == NORMAL_AUDI){
            return "审核中";
        }else if(state == PASS_AUDI){
            return "审核通过";
        }else if(state == REJECT_AUDI){
            return "审核拒绝";
        }else {
            return null;
        }
    }

    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("applier",applier.getUsername());
        map.put("fileType",fileType.getTitle());
        map.put("image",image);
        return JSON.toJSONString(map);
    }

}
