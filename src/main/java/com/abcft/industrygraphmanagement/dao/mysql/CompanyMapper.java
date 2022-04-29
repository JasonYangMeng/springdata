package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@Repository
@Mapper
public interface CompanyMapper {
    /**
     * 公司词条名称模糊查询，支持前十条
     *
     * @param var1 查询参数
     * @return List<CompanyEntryEntity>
     */
    List<CompanyEntity> queryCompanyNameList(String var1);

    /**
     * 修改公司状态
     *
     * @param id
     */
    void updateStatusById(String id);

    /**
     * 修改为初始状态
     *
     * @param id
     */
    void updateStatus(String id);

    void addCompanyEntity(CompanyEntity companyEntity);
}
