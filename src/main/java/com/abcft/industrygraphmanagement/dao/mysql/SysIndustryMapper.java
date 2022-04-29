package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.SysIndustryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <>
 * <功能描述>
 *
 * @date 2021/3/9
 */
@Repository
@Mapper
public interface SysIndustryMapper {

    void addIndustryList(ArrayList<SysIndustryEntity> sysIndustryList);

    String queryParentId(String firstCode);

    List<SysIndustryEntity> queryIndustryByLevel(String var1);

    List<SysIndustryEntity> queryIndustryByLevels(String var1, String var2);

    /**
     * 根据行业名称模糊查询行业信息
     *
     * @param name 行业名称
     * @return 行业信息
     */
    List<SysIndustryEntity> querySysIndustryListByName(String name);

    /**
     * 根据行业名称和行业级别模糊查询行业信息
     *
     * @param name 行业名称
     * @param level 行业级别
     * @return 行业信息
     */
    List<SysIndustryEntity> querySysIndustryListByNameAndLevel(String name, String level);

    SysIndustryEntity querySysIndustryById(String id);
}
