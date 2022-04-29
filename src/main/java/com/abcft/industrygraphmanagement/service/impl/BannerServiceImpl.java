package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.BannerRepository;
import com.abcft.industrygraphmanagement.model.condition.BannerCondition;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.node.BannerNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.BannerService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @Author Created by YangMeng on 2021/6/16 11:56
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 创建Banner
     *
     * @param bannerNode
     */
    @Override
    public void saveNode(BannerNode bannerNode) throws Exception {
        String uuid = bannerNode.getUuid();
        String dateStr = DateExtUtils.getCurrentDateStr();
        String title = bannerNode.getTitle();
        int type = bannerNode.getType();
        BannerNode byTitleAndType = bannerRepository.getByTitleAndType(title, type);

        if (StringUtils.isEmpty(uuid)) {
            if (byTitleAndType != null) {
                throw new Exception("标题已存在，请重新修改");
            }
            uuid = UuidUtils.generate(NameType.BANNER);
            bannerNode.setUuid(uuid);
            bannerNode.setCreateTime(dateStr);
        } else if (byTitleAndType != null) {
            if (!uuid.equals(byTitleAndType.getUuid())) {
                throw new Exception("标题已存在，请重新修改");
            }
        }
        int orderNum = bannerRepository.getMaxNum() + 1;
        bannerNode.setOrderNum(orderNum);
        bannerNode.setModifyTime(dateStr);
        bannerRepository.save(bannerNode);
    }

    /**
     * 获取分页
     *
     * @param bannerCondition
     * @return
     */
    @Override
    public Paged<BannerNode> getPagedList(BannerCondition bannerCondition) {
        Paged<BannerNode> paged = new Paged<>();
        int page = bannerCondition.getPage() < 1 ? 1 : bannerCondition.getPage();
        int size = bannerCondition.getSize() < 1 ? 10 : bannerCondition.getSize();
        int skip = (page - 1) * size;
        String issue = bannerCondition.getIssue();
        int type = StringUtils.isEmpty(bannerCondition.getType()) ? 1 : bannerCondition.getType();
        int total = bannerRepository.total(type);
        List<BannerNode> bannerList = bannerRepository.getBannerList(skip, size, type, issue);
        paged.setTotal(total);
        paged.setRows(bannerList);
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
            bannerRepository.updateStatus(uuid, issue);
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
            bannerRepository.deleteById(uuid);
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
        Optional<BannerNode> byId = bannerRepository.findById(uuid);
        if (!byId.isPresent()) {
            throw new Exception("数据不存在");
        }
        int orderNum = byId.get().getOrderNum();
        int type = byId.get().getType();
        BannerNode compareNode;
        if ("1".equals(upOrDown)) {
            compareNode = bannerRepository.getUpNode(orderNum, type);
        } else {
            compareNode = bannerRepository.getDownNode(orderNum, type);
        }
        if (compareNode == null) {
            throw new Exception("不需要移动");
        }
        int compareOrderNum = compareNode.getOrderNum();
        //上下移动数据
        bannerRepository.updateSortNum(uuid, compareOrderNum);
        bannerRepository.updateSortNum(compareNode.getUuid(), orderNum);
    }

    /**
     * 新闻词条数据回显
     *
     * @param uuid
     * @return BannerNode
     */
    @Override
    public BannerNode getEntryById(String uuid) throws Exception {
        Optional<BannerNode> byId = bannerRepository.findById(uuid);
        if (!byId.isPresent()) {
            throw new Exception("数据不存在");
        }
        return byId.get();
    }
}
