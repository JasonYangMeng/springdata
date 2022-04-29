package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysCollectSettingEntity;
import com.abcft.industrygraphmanagement.model.entity.SysSubscribeSettingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/5/12 13:52
 */
@Mapper
@Repository
public interface SysSubscribeSettingMapper extends IBaseMapper<SysSubscribeSettingEntity, String> {
    /**
     * 删除订阅
     *
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
    int countSubscribe(String userId,String entryId);

    /**
     * 获取收藏
     *
     * @param sysCollectCondition
     * @return
     */
    List<SysSubscribeSettingEntity> getListByCondition(SysCollectCondition sysCollectCondition);

    /**
     * 统计个数
     *
     * @param sysCollectCondition
     * @return
     */
    int totalByCondition(SysCollectCondition sysCollectCondition);
}
