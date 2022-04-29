package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.CompanyEntity;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
public interface CompanyService {

    List<CompanyEntity> queryCompanyNameList(String var1);

    /**
     * 设置公司已创建状态
     * @param id
     * @return
     */
    void updateStatus(String id);

    void addCompanyEntity(CompanyEntity companyEntity);
}
