package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.EntryLinkMapper;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.service.EntryLinkService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/10
 */
@Service
public class EntryLinkServiceImpl implements EntryLinkService {

    @Autowired
    private EntryLinkMapper entryLinkMapper;

    /**
     * 最近添加链接 查询接口
     *
     * @return List<EntryLinkEntity>
     */
    @Override
    public List<EntryLinkEntity> queryEntryLinkRecentlyList(String createUserId) {
        return entryLinkMapper.queryEntryLinkRecentlyList(createUserId);
    }

    @Override
    public List<EntryLinkEntity> queryEntryLinkInnerList(String var) {
        return entryLinkMapper.queryEntryLinkInnerList(var);
    }

    @Override
    public List<EntryLinkEntity> queryEntryLinkList(String entryId) {
        return entryLinkMapper.queryEntryLinkList(entryId);
    }

    @Override
    public Integer addEntryLinkList(List<EntryLinkEntity> entryLinkList) {
        for (EntryLinkEntity entity : entryLinkList) {
            entity.setId(UuidUtils.generate());
            entity.setCreateTime(DateExtUtils.getCurrentDateStr());
        }
        return entryLinkMapper.addEntryLinkList(entryLinkList);
    }

    @Override
    public void deleteEntryLinkList(String entryId) {
        entryLinkMapper.deleteEntryLinkList(entryId);
    }

    @Override
    public void addEntryLink(EntryLinkEntity entryLinkEntity) {
        entryLinkMapper.addEntryLink(entryLinkEntity);
    }

    @Override
    public void updateEntryLink(EntryLinkEntity entryLinkEntity) {
        entryLinkMapper.updateEntryLink(entryLinkEntity);
    }
}
