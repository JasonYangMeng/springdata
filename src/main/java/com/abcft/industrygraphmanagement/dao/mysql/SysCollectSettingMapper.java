package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysCollectSettingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/5/12 13:52
 */
@Mapper
@Repository
public interface SysCollectSettingMapper extends IBaseMapper<SysCollectSettingEntity, String> {
    /**
     * 删除收藏
     *
     * @param userId
     * @param entryId
     * @return
     */
    boolean deleteByUserIdAndTypeId(String userId, String entryId);

    /**
     * 根据订阅人和词条统计数量
     * @param userId
     * @param entryId
     * @return
     */
    int countCollect(String userId,String entryId);

    /**
     * 获取收藏
     *
     * @param sysCollectCondition
     * @return
     */
    List<SysCollectSettingEntity> getListByCondition(SysCollectCondition sysCollectCondition);
    /**
     * 统计个数
     * @param sysCollectCondition
     * @return
     */
    int totalByCondition(SysCollectCondition sysCollectCondition);
}
