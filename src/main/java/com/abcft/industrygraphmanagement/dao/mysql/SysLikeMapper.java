package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.SysLikeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/5/17 10:48
 */
@Mapper
@Repository
public interface SysLikeMapper extends IBaseMapper<SysLikeEntity, String> {
    /**
     * 删除收藏
     *
     * @param userId
     * @param entryId
     * @return
     */
    boolean deleteByUserIdAndTypeId(String userId, String entryId);

    /**
     * 根据人和词条统计数量
     *
     * @param userId
     * @param entryId
     * @return
     */
    int countCollect(String userId, String entryId);

    /**
     * 查询点赞个数
     *
     * @param entryId
     * @return
     */
    int totalByEntryId(String entryId);
}
