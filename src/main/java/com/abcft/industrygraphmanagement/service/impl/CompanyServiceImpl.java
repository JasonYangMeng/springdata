package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.CompanyMapper;
import com.abcft.industrygraphmanagement.model.entity.CompanyEntity;
import com.abcft.industrygraphmanagement.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 公司词条名称模糊查询，支持前十条
     *
     * @param var1 查询参数
     * @return List<CompanyEntryEntity>
     */
    @Override
    public List<CompanyEntity> queryCompanyNameList(String var1) {
        return companyMapper.queryCompanyNameList(var1);
    }

    /**
     * 设置公司已创建状态
     *
     * @param id
     * @return
     */
    @Override
    public void updateStatus(String id) {
        companyMapper.updateStatusById(id);
    }

    @Override
    public void addCompanyEntity(CompanyEntity companyEntity) {
        companyMapper.addCompanyEntity(companyEntity);
    }
}
