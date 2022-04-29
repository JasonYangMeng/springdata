package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.NewsEntryRepository;
import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.node.BannerNode;
import com.abcft.industrygraphmanagement.model.node.NewsEntryNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.NewsEntryService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@Service
public class NewsEntryServiceImpl implements NewsEntryService {

    @Autowired
    private NewsEntryRepository newsEntryRepository;

    /**
     * 创建新闻词条
     *
     * @param newsEntryNode 新闻词条入参dto NewsEntryParamDto
     */
    @Override
    public void saveNewsEntry(NewsEntryNode newsEntryNode) throws Exception {
        String newsEntryId = newsEntryNode.getNewsEntryId();
        String dateStr = DateExtUtils.getCurrentDateStr();
        String title = newsEntryNode.getTitle();
        int type = newsEntryNode.getType();
        NewsEntryNode byTitleAndType = newsEntryRepository.getByTitleAndType(title, type);
        // id空，新建新闻词条
        if (StringUtils.isEmpty(newsEntryId)) {
            if (byTitleAndType != null) {
                throw new Exception("标题已存在，请重新修改");
            }
            newsEntryId = UuidUtils.generate(NameType.NEWS);
            newsEntryNode.setNewsEntryId(newsEntryId);
            newsEntryNode.setCreateTime(dateStr);
            newsEntryNode.setModifyTime(dateStr);
        } else if (byTitleAndType != null) {
            if (!newsEntryId.equals(byTitleAndType.getNewsEntryId())) {
                throw new Exception("标题已存在，请重新修改");
            }
        }

        int orderNum = newsEntryRepository.getMaxNum() + 1;
        newsEntryNode.setOrderNum(orderNum);
        newsEntryNode.setModifyTime(dateStr);
        newsEntryRepository.save(newsEntryNode);
    }

    /**
     * 获取分页
     *
     * @param pageCondition
     * @return
     */
    @Override
    public Paged<NewsEntryNode> getPagedList(PageCondition pageCondition) {
        Paged<NewsEntryNode> paged = new Paged<>();
        int page = pageCondition.getPage() < 1 ? 1 : pageCondition.getPage();
        int size = pageCondition.getSize() < 1 ? 10 : pageCondition.getSize();
        String name = pageCondition.getName();
        int type = pageCondition.getType();
        int skip = (page - 1) * size;
        String issue = pageCondition.getIssue();
        int total = newsEntryRepository.total(name, issue, type);

        List<NewsEntryNode> list = newsEntryRepository.getList(skip, size, issue, name, type);
        paged.setTotal(total);
        paged.setRows(list);
        return paged;
    }

    /**
     * 修改新闻状态
     *
     * @param newsEntryIds
     */
    @Override
    public void updateNewsStatus(String[] newsEntryIds, String issue) {
        for (String newsEntryId : newsEntryIds) {
            newsEntryRepository.updateNewsStatus(newsEntryId, issue);
        }
    }

    /**
     * 上移下移
     *
     * @param newsEntryId
     * @param upOrDown    1上移 0 下移
     */
    @Override
    public void sortById(String newsEntryId, String upOrDown) throws Exception {
        Optional<NewsEntryNode> byId = newsEntryRepository.findById(newsEntryId);
        if (!byId.isPresent()) {
            throw new Exception("数据不存在");
        }
        int orderNum = byId.get().getOrderNum();
        int type = byId.get().getType();
        NewsEntryNode compareNode;
        if ("1".equals(upOrDown)) {
            compareNode = newsEntryRepository.getUpNode(orderNum, type);
        } else {
            compareNode = newsEntryRepository.getDownNode(orderNum, type);
        }
        if (compareNode == null) {
            throw new Exception("不需要移动");
        }
        int compareOrderNum = compareNode.getOrderNum();
        //上下移动数据
        newsEntryRepository.updateSortNum(newsEntryId, compareOrderNum);
        newsEntryRepository.updateSortNum(compareNode.getNewsEntryId(), orderNum);

    }

    /**
     * 删除数据
     *
     * @param newEntryIds
     */
    @Override
    public void deleteNode(String[] newEntryIds) {
        for (String newEntryId : newEntryIds) {
            newsEntryRepository.deleteById(newEntryId);
        }
    }

    /**
     * 新闻词条详情(数据回显)
     *
     * @param newsEntryId 新闻词条id
     * @return NewsEntryParamDto
     */
    @Override
    public NewsEntryNode getNewsEntryParam(String newsEntryId) throws Exception {
        Optional<NewsEntryNode> byId = newsEntryRepository.findById(newsEntryId);
        if (!byId.isPresent()) {
            throw new Exception("该新闻不存在！");
        }
        return byId.get();
    }


    /**
     * 新闻词条标题校验
     *
     * @param name 新闻标题名字
     * @return NewsEntryNode
     */
    @Override
    public Boolean getNewsEntryByName(String name) {
        return newsEntryRepository.findNewsEntryNodeByName(name) == null;
    }
}
