package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysLikeMapper;
import com.abcft.industrygraphmanagement.model.entity.SysLikeEntity;
import com.abcft.industrygraphmanagement.service.SysLikeService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author Created by YangMeng on 2021/5/17 10:45
 */
@Service
@Slf4j
public class SysLikeServiceImpl implements SysLikeService {

    @Autowired
    private SysLikeMapper sysLikeMapper;

    /**
     * 添加
     *
     * @param sysLikeEntity
     */
    @Override
    public int insert(SysLikeEntity sysLikeEntity) throws Exception {
        try {
            String userId = UserContext.getCurrentUserId();
            if (StringUtils.isEmpty(userId)) {
                log.error("用户获取失败");
                throw new Exception("插入失败");
            }
            sysLikeEntity.setCreateTime(DateExtUtils.getCurrentDateStr());
            sysLikeEntity.setId(UuidUtils.generate());
            sysLikeEntity.setUserId(UserContext.getCurrentUserId());
            sysLikeMapper.add(sysLikeEntity);
            return sysLikeMapper.totalByEntryId(sysLikeEntity.getEntryId());
        } catch (Exception e) {
            log.error("insert error:{}", e);
            throw new Exception(e);
        }
    }

    /**
     * 删除数据
     *
     * @param entryId
     */
    @Override
    public int delete(String entryId) throws Exception {
        String userId = UserContext.getCurrentUserId();
        if (StringUtils.isEmpty(userId)) {
            log.error("用户获取失败");
            throw new Exception("删除失败");
        }
        try {
            sysLikeMapper.deleteByUserIdAndTypeId(userId, entryId);
            return sysLikeMapper.totalByEntryId(entryId);
        } catch (Exception e) {
            log.error("delete error:{}", e);
            throw new Exception("删除失败");
        }
    }
}
