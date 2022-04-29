package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysCollectSettingMapper;
import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysCollectSettingEntity;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.SysCollectService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/5/12 15:14
 */
@Service
@Slf4j
public class SysCollectServiceImpl implements SysCollectService {

    @Autowired
    private SysCollectSettingMapper sysCollectSettingMapper;

    /**
     * 添加
     *
     * @param sysCollectSettingEntity
     */
    @Override
    public void insert(SysCollectSettingEntity sysCollectSettingEntity) throws Exception {
        try {
            String userId = UserContext.getCurrentUserId();
            if (StringUtils.isEmpty(userId)) {
                log.error("用户获取失败");
                throw new Exception("插入失败");
            }
            sysCollectSettingEntity.setCreateTime(DateExtUtils.getCurrentDateStr());
            sysCollectSettingEntity.setId(UuidUtils.generate());
            sysCollectSettingEntity.setUserId(userId);
            sysCollectSettingMapper.add(sysCollectSettingEntity);
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
    public void delete(String entryId) throws Exception {
        String userId = UserContext.getCurrentUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new Exception("删除失败");
        }
        try {
            sysCollectSettingMapper.deleteByUserIdAndTypeId(userId, entryId);
        } catch (Exception e) {
            log.error("delete error:{}", e);
            throw new Exception("删除失败");
        }
    }

    /**
     * 分页获取列表
     *
     * @param sysCollectCondition
     * @return
     */
    @Override
    public Paged<SysCollectSettingEntity> getPagedListByCondition(SysCollectCondition sysCollectCondition) {
        Paged<SysCollectSettingEntity> paged = new Paged<>();
        sysCollectCondition.setUserId(UserContext.getCurrentUserId());
        int page = (sysCollectCondition.getPage() - 1) * sysCollectCondition.getSize();
        sysCollectCondition.setPage(page);
        List<SysCollectSettingEntity> listByCondition = sysCollectSettingMapper.getListByCondition(sysCollectCondition);
        int total = sysCollectSettingMapper.totalByCondition(sysCollectCondition);
        paged.setRows(listByCondition);
        paged.setTotal(total);
        return paged;
    }
}
