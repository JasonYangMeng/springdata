package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.TopSearchRepository;
import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.node.NewsEntryNode;
import com.abcft.industrygraphmanagement.model.node.TopSearchNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.TopSearchService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @Author Created by YangMeng on 2021/6/17 10:53
 */
@Service
public class TopSearchServiceImpl implements TopSearchService {

    @Autowired
    private TopSearchRepository topSearchRepository;

    /**
     * 创建Banner
     *
     * @param topSearchNode
     */
    @Override
    public void saveNode(TopSearchNode topSearchNode) throws Exception {
        String uuid = topSearchNode.getUuid();
        String dateStr = DateExtUtils.getCurrentDateStr();
        String title = topSearchNode.getName();
        int type = topSearchNode.getType();
        TopSearchNode byTitleAndType = topSearchRepository.getByTitleAndType(title, type);
        if (StringUtils.isEmpty(uuid)) {
            if (byTitleAndType != null) {
                throw new Exception("标题已存在，请重新修改");
            }
            uuid = UuidUtils.generate(NameType.TOPSEARCH);
            topSearchNode.setUuid(uuid);
            topSearchNode.setCreateTime(dateStr);
        }else if (byTitleAndType != null) {
            if (!uuid.equals(byTitleAndType.getUuid())) {
                throw new Exception("标题已存在，请重新修改");
            }
        }
        int orderNum = topSearchRepository.getMaxNum() + 1;
        topSearchNode.setOrderNum(orderNum);
        topSearchNode.setModifyTime(dateStr);
        topSearchRepository.save(topSearchNode);
    }

    /**
     * 获取分页
     *
     * @param pageCondition
     * @return
     */
    @Override
    public Paged<TopSearchNode> getPagedList(PageCondition pageCondition) {
        Paged<TopSearchNode> paged = new Paged<>();
        int page = pageCondition.getPage() < 1 ? 1 : pageCondition.getPage();
        int size = pageCondition.getSize() < 1 ? 10 : pageCondition.getSize();
        String issue = pageCondition.getIssue();
        int type = pageCondition.getType();
        int skip = (page - 1) * size;
        int total = topSearchRepository.total(type);
        List<TopSearchNode> list = topSearchRepository.getList(skip, size, issue, type);
        paged.setTotal(total);
        paged.setRows(list);
        return paged;
    }

    /**
     * 修改状态
     *
     * @param uuids
     * @param issue
     */
    @Override
    public void updateStatus(String[] uuids, String issue) {
        for (String uuid : uuids) {
            topSearchRepository.updateStatus(uuid, issue);
        }
    }

    /**
     * 删除数据
     *
     * @param uuids
     */
    @Override
    public void deleteNode(String[] uuids) {
        for (String uuid : uuids) {
            topSearchRepository.deleteById(uuid);
        }
    }

    /**
     * 上移下移
     *
     * @param uuid
     * @param upOrDown 1上移 0 下移
     */
    @Override
    public void sortById(String uuid, String upOrDown) throws Exception {
        Optional<TopSearchNode> byId = topSearchRepository.findById(uuid);
        if (!byId.isPresent()) {
            throw new Exception("数据不存在");
        }
        int orderNum = byId.get().getOrderNum();
        int type = byId.get().getType();
        TopSearchNode compareNode;
        if ("1".equals(upOrDown)) {
            compareNode = topSearchRepository.getUpNode(orderNum, type);
        } else {
            compareNode = topSearchRepository.getDownNode(orderNum, type);
        }
        if (compareNode == null) {
            throw new Exception("不需要移动");
        }
        int compareOrderNum = compareNode.getOrderNum();
        //上下移动数据
        topSearchRepository.updateSortNum(uuid, compareOrderNum);
        topSearchRepository.updateSortNum(compareNode.getUuid(), orderNum);
    }

    /**
     * 词条数据回显
     *
     * @param uuid
     * @return TopSearchNode
     */
    @Override
    public TopSearchNode getEntryById(String uuid) throws Exception {
        Optional<TopSearchNode> byId = topSearchRepository.findById(uuid);
        if (!byId.isPresent()) {
            throw new Exception("数据不存在");
        }
        return byId.get();
    }
}
