package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysTemplateMapper;
import com.abcft.industrygraphmanagement.model.entity.SysTemplateEntity;
import com.abcft.industrygraphmanagement.service.SysTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Created by YangMeng on 2021/3/9 16:12
 */
@Service
public class SysTemplateServiceImpl implements SysTemplateService {

    @Autowired
    private SysTemplateMapper sysTemplateMapper;

    /**
     * 通过id获取模板
     *
     * @param id
     * @return
     */
    @Override
    public SysTemplateEntity getTemplateById(String id) {
        return sysTemplateMapper.getOne(id);
    }
}
