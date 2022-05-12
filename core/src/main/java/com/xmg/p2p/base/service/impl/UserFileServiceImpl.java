package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserFileServiceImpl implements IUserFileService {

    @Autowired
    private UserFileMapper userFileMapper;

    @Autowired
    private IUserinfoService userinfoService;


    public void apply(String fileName) {
        UserFile userFile = new UserFile();
        userFile.setImage(fileName);
        userFile.setApplier(UserContext.getCurrent());
        userFile.setApplyTime(new Date());
        //设置认证状态
        userFile.setState(UserFile.NORMAL_AUDI);
        userFileMapper.insert(userFile);
    }

    public List<UserFile> listNoFileType(Long id) {

        return userFileMapper.listNoFileType(id);
    }

    public List<SystemDictionaryItem> listFileTypeBySn(String userFileType) {
        return userFileMapper.listFileTypeBySn(userFileType);
    }

    public List<UserFile> listHasFileType(Long id) {

        return userFileMapper.listHasFileType(id);
    }

    public void updateFileType(Long[] ids, Long[] fileTypes) {
        for (int i = 0;i<ids.length;i++){
            UserFile userFile = userFileMapper.selectByPrimaryKey(ids[i]);
            SystemDictionaryItem fileType = new SystemDictionaryItem();
            fileType.setId(fileTypes[i]);
            //userFile只需要fileType的id，不需要fileType的其他数据，所以直接new一个fileType，不需要去数据库查询fileType的完整数据
            userFile.setFileType(fileType);
            userFileMapper.updateByPrimaryKey(userFile);
        }
    }

    public PageResult query(UserFileQueryObject qo) {
        int count = userFileMapper.queryForCount(qo);
        if(count != 0){
            List<UserFile> data = userFileMapper.queryForData(qo);
            return new PageResult(qo.getCurrentPage(),qo.getPageSize(),count,data);
        }
        return PageResult.getEmpty(qo.getPageSize());
    }

    public void audit(Long id, int state, int score, String remark) {
        UserFile userFile = userFileMapper.selectByPrimaryKey(id);
        //未审核
        if(userFile != null && userFile.getState()==UserFile.NORMAL_AUDI){
             userFile.setAuditor(UserContext.getCurrent());
             userFile.setAuditTime(new Date());
             userFile.setState(state);
             userFile.setScore(score);
             //更新风控文件
             userFileMapper.updateByPrimaryKey(userFile);
             Userinfo userinfo = userinfoService.get(userFile.getApplier().getId());
             //如果审核通过，累加申请用户的风控总得分，并更新
             //如果审核拒绝，即使风控文件有得分，也不计入用户的风控总分中
             if(state == UserFile.PASS_AUDI){
                 userinfo.setScore(userinfo.getScore()+score);
                 userinfoService.update(userinfo);
             }
        }
    }


    @Override
    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return userFileMapper.queryForData(qo);
    }
}
