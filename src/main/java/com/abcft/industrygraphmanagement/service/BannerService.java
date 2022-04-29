package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.BannerCondition;
import com.abcft.industrygraphmanagement.model.node.BannerNode;
import com.abcft.industrygraphmanagement.model.result.Paged;

/**
 * @Author Created by YangMeng on 2021/6/16 11:53
 */
public interface BannerService {
    /**
     * 创建Banner
     *
     * @param bannerNode
     */
    void saveNode(BannerNode bannerNode) throws Exception;

    /**
     * 获取分页
     * @return
     */
    Paged<BannerNode> getPagedList(BannerCondition bannerCondition);

    /**
     * 修改状态
     *
     * @param uuids
     */
    void updateStatus(String[] uuids, String issue);

    /**
     * 删除数据
     * @param uuids
     */
    void deleteNode(String[] uuids);

    /**
     * 上移下移
     * @param uuid
     * @param upOrDown  1上移 0 下移
     */
    void sortById(String uuid,String upOrDown) throws Exception;

    /**
     * 新闻词条数据回显
     *
     * @param uuid
     * @return NewsEntryParamDto
     */
    BannerNode getEntryById(String uuid) throws Exception;

}
