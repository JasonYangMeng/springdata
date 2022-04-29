package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.EntryInfoSourceMapper;
import com.abcft.industrygraphmanagement.dao.mysql.EntryLinkMapper;
import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.service.EntryInfoSourceService;
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
public class EntryInfoSourceServiceImpl implements EntryInfoSourceService {

    @Autowired
    private EntryInfoSourceMapper entryInfoSourceMapper;

    @Autowired
    private EntryLinkMapper entryLinkMapper;

    /**
     * 新增词条信息源数据接口
     *
     * @param entryInfoSourceList EntryInfoSourceEntity 词条信息来源对象
     * @return Integer 插入条数
     */
    @Override
    public Integer addEntryInfoSource(List<EntryInfoSourceEntity> entryInfoSourceList) {
        for (EntryInfoSourceEntity entity : entryInfoSourceList) {
            entity.setEntryInfoSourceId(UuidUtils.generate());
            entity.setCreateTime(DateExtUtils.getCurrentDateStr());
        }

        return entryInfoSourceMapper.addEntryInfoSource(entryInfoSourceList);
    }

    /**
     * 单条保存词条来源
     *
     * @param entryInfoSourceEntity
     * @return
     */
    @Override
    public Integer addEntryInfoSource1(EntryInfoSourceEntity entryInfoSourceEntity) {
        entryInfoSourceEntity.setEntryInfoSourceId(UuidUtils.generate());
        entryInfoSourceEntity.setCreateTime(DateExtUtils.getCurrentDateStr());
        return entryInfoSourceMapper.addEntryInfoSource1(entryInfoSourceEntity);
    }

    @Override
    public Integer deleteEntryInfoSource(String entryId) {
        // 根据词条id来删除词条链接
        entryLinkMapper.deleteEntryLinkList(entryId);
        return entryInfoSourceMapper.deleteEntryInfoSource(entryId);
    }

    /**
     * 根据entryId查询数据
     *
     * @param entryId
     * @return
     */
    @Override
    public List<EntryInfoSourceEntity> queryAllByEntryId(String entryId) {
        return entryInfoSourceMapper.queryAllByEntryId(entryId);
    }

    /**
     * 单条更新词条来源
     *
     * @param entryInfoSourceEntity
     */
    @Override
    public void updateEntryInfoSource1(EntryInfoSourceEntity entryInfoSourceEntity) {
        entryInfoSourceMapper.updateEntryInfoSource1(entryInfoSourceEntity);
    }
}
