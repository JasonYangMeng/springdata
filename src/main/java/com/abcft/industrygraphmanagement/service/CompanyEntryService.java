package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.CompanyEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.CompanyEntryDto;
import com.abcft.industrygraphmanagement.model.dto.CompanyLineChartQuarterAndYearDto;
import com.abcft.industrygraphmanagement.model.dto.GraphDto;
import com.abcft.industrygraphmanagement.model.dto.ProductionDto;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/3 15:04
 */
public interface CompanyEntryService {

    /**
     * 查看词条信息
     *
     * @param uuid
     * @return
     */
    CompanyEntryDto getCompanyEntryById(String uuid) throws Exception;

    /**
     * 编辑词条
     *
     * @param companyEntryCondition
     */
    String updateCompanyEntry(CompanyEntryCondition companyEntryCondition) throws Exception;

    /**
     * 删除数据
     *
     * @param uuid
     */
    void deleteById(String uuid);

    /**
     * 获取公司产品的族谱
     *
     * @param companyEntryId
     * @param productEntryId
     * @return
     * @throws Exception
     */
    GraphDto getGraph(String companyEntryId, String productEntryId) throws Exception;

    /**
     * 插入mysql数据库并获取id
     *
     * @param companyEntryName
     * @return
     */
    String getCompanyIdByMysql(String companyEntryName);

    /**
     * 获取公司经营、财务、资产负债、现金流数据的季度和年度数据
     *
     * @param companyEntryId 公司id
     * @param label          neo4j节点label
     * @param propertyName   节点属性名
     * @return CompanyLineChartQuarterAndYearDto
     */
    CompanyLineChartQuarterAndYearDto getCompanyLineChartList(String companyEntryId, String label, String propertyName);

    void addProduction(List<ProductionDto> productionDtoList);

}
