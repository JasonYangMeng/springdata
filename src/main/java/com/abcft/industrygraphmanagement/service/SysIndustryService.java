package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.SysIndustryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
public interface SysIndustryService {

    void addIndustryList(String filePath);

    List<SysIndustryEntity> querySysIndustryList();

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
     * @param name  行业名称
     * @param level 行业级别
     * @return 行业信息
     */
    List<SysIndustryEntity> querySysIndustryListByNameAndLevel(String name, String level);

    /**
     * 根据id查询某行业
     *
     * @param id
     * @return
     */
    SysIndustryEntity querySysIndustryById(String id);

    /**
     * 根据行业等级查询父行业列表
     *
     * @param level 等级
     * @return List<SysIndustryEntity>
     */
    List<SysIndustryEntity> queryParentTradeList(String level);
}
