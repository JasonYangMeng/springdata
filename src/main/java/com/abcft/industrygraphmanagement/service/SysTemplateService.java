package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.SysTemplateEntity;

/**
 * @Author Created by YangMeng on 2021/3/9 16:09
 */
public interface SysTemplateService {
    /**
     * 通过id获取模板
     *
     * @param id
     * @return
     */
    SysTemplateEntity getTemplateById(String id);
}
