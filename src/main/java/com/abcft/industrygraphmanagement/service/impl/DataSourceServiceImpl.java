package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.BannerRepository;
import com.abcft.industrygraphmanagement.dao.neo.DataSourceRepository;
import com.abcft.industrygraphmanagement.dao.neo.NewsEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.TopSearchRepository;
import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.DataSourceNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.DataSourceService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @Author Created by YangMeng on 2021/7/5 11:25
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private TopSearchRepository topSearchRepository;

    @Autowired
    private NewsEntryRepository newsEntryRepository;

    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 创建
     *
     * @param dataSourceNode
     */
    @Override
    public void saveNode(DataSourceNode dataSourceNode) {
        String uuid = dataSourceNode.getUuid();
        String dateStr = DateExtUtils.getCurrentDateStr();

        if (StringUtils.isEmpty(uuid)) {
            uuid = UuidUtils.generate();
            dataSourceNode.setUuid(uuid);
            dataSourceNode.setCreateTime(dateStr);
            int type = dataSourceRepository.getMaxType(dataSourceNode.getDataTemplate());
            dataSourceNode.setType(type == 0 ? 1 : type + 1);
        }
        dataSourceRepository.save(dataSourceNode);
    }

    /**
     * 获取分页
     *
     * @param pageCondition
     * @return
     */
    @Override
    public Paged<DataSourceNode> getPagedList(PageCondition pageCondition) {
        Paged<DataSourceNode> paged = new Paged<>();
        int page = pageCondition.getPage() < 1 ? 1 : pageCondition.getPage();
        int size = pageCondition.getSize() < 1 ? 10 : pageCondition.getSize();
        int skip = (page - 1) * size;
        int total = dataSourceRepository.total();
        List<DataSourceNode> list = dataSourceRepository.getList(skip, size);
        paged.setTotal(total);
        paged.setRows(list);
        return paged;
    }

    /**
     * 获取所有数据源
     *
     * @return
     */
    @Override
    public List<DataSourceNode> getAllList() {
        return dataSourceRepository.getList(0, 1000);
    }

    /**
     * 删除数据
     *
     * @param uuids
     */
    @Override
    public void deleteNode(String[] uuids) {
        for (String uuid : uuids) {
            Optional<DataSourceNode> byId = dataSourceRepository.findById(uuid);
            if (byId.isPresent()) {
                //删除对应的数据
                String dataTemplate = byId.get().getDataTemplate();
                int type = byId.get().getType();
                if ("1".equals(dataTemplate)) {
                    bannerRepository.deleteByType(type);
                } else if ("2".equals(dataTemplate)) {
                    newsEntryRepository.deleteByType(type);
                } else if ("3".equals(dataTemplate)) {
                    topSearchRepository.deleteByType(type);
                }
            }
            dataSourceRepository.deleteById(uuid);
        }
    }
}
