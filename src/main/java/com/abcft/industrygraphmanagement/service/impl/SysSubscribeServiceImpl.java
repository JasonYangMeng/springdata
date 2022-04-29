package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysSubscribeSettingMapper;
import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysSubscribeSettingEntity;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.SysSubscribeService;
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
public class SysSubscribeServiceImpl implements SysSubscribeService {

    @Autowired
    private SysSubscribeSettingMapper sysSubscribeSettingMapper;

    /**
     * 添加
     *
     * @param sysSubscribeSettingEntity
     */
    @Override
    public void insert(SysSubscribeSettingEntity sysSubscribeSettingEntity) throws Exception {
        try {
            String userId = UserContext.getCurrentUserId();
            if (StringUtils.isEmpty(userId)) {
                log.error("用户获取失败");
                throw new Exception("插入失败");
            }
            sysSubscribeSettingEntity.setCreateTime(DateExtUtils.getCurrentDateStr());
            sysSubscribeSettingEntity.setId(UuidUtils.generate());
            sysSubscribeSettingEntity.setUserId(UserContext.getCurrentUserId());
            sysSubscribeSettingMapper.add(sysSubscribeSettingEntity);
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
            log.error("用户获取失败");
            throw new Exception("删除失败");
        }
        try {
            sysSubscribeSettingMapper.deleteByUserIdAndTypeId(userId, entryId);
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
    public Paged<SysSubscribeSettingEntity> getPagedListByCondition(SysCollectCondition sysCollectCondition) {
        Paged<SysSubscribeSettingEntity> paged = new Paged<>();
        sysCollectCondition.setUserId(UserContext.getCurrentUserId());
        int page = (sysCollectCondition.getPage() - 1) * sysCollectCondition.getSize();
        sysCollectCondition.setPage(page);
        List<SysSubscribeSettingEntity> listByCondition = sysSubscribeSettingMapper.getListByCondition(sysCollectCondition);
        int total = sysSubscribeSettingMapper.totalByCondition(sysCollectCondition);
        paged.setRows(listByCondition);
        paged.setTotal(total);
        return paged;
    }
}
