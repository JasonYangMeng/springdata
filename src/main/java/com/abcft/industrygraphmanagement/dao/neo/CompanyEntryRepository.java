package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.CompanyDataDto;
import com.abcft.industrygraphmanagement.model.dto.CompanyLineChartDto;
import com.abcft.industrygraphmanagement.model.dto.SeniorManagerDto;
import com.abcft.industrygraphmanagement.model.dto.ShareholderInformationDto;
import com.abcft.industrygraphmanagement.model.node.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/3 14:59
 */
@Repository
public interface CompanyEntryRepository extends Neo4jRepository<CompanyEntryNode, String> {

    /**
     * 工商数据股东信息(人物->公司 投资关系)
     *
     * @param companyEntryId 公司id
     * @return ShareholderInformationDto列表
     */
    @Query("match (p:PersonEntry)-[r:PersonInvestment]->(c:CompanyEntry) " +
            " where c.companyEntryId = {companyEntryId} " +
            " return    p.personEntryId as id, " +
            "           p.name as name, " +
            "           r.shareHoldStatus as shareHoldStatus, " +
            "           r.termEndRefVal as termEndRefVal, " +
            "           r.shareHoldNum as shareHoldNum, " +
            "           r.shareHoldNumChange as shareHoldNumChange, " +
            "           r.proportionInTotal as proportionInTotal")
    List<ShareholderInformationDto> getShareholderInfoList(String companyEntryId);

    /**
     * 工商数据高管信息(人物->公司 任职关系)
     *
     * @param companyEntryId 公司词条id
     * @return SeniorManagerDto 列表
     */
    @Query("match (p:PersonEntry)-[r:Post]->(c:CompanyEntry) " +
            " where c.companyEntryId = {companyEntryId} " +
            " return    p.personEntryId as id, " +
            "           p.name as name, " +
            "           r.post as post, " +
            "           r.shareHoldNum as shareHoldNum, " +
            "           r.shareHoldRefVal as shareHoldRefVal")
    List<SeniorManagerDto> getSeniorManagerList(String companyEntryId);

    /**
     * 公司行情数据
     *
     * @param companyEntryId 公司词条id
     * @return MarketDataNode
     */
    @Query("match (n:MarketData) " +
            " where n.companyEntryId = {companyEntryId} " +
            " return n " +
            " order by n.endDate desc" +
            " limit 1")
    MarketDataNode getMarketDataNode(String companyEntryId);

    /**
     * 公司经营数据
     *
     * @param companyEntryId 公司词条id
     * @return OperateDataNode
     */
    @Query("match (n:OperateData) " +
            " where n.companyEntryId = {companyEntryId} or n.companyEntryId = {companyEntryId}+'1' " +
            " return n " +
            " order by n.endDate desc " +
            " limit 2")
    List<OperateDataNode> getOperateDataNode(String companyEntryId);

    /**
     * 公司财务数据
     *
     * @param companyEntryId 公司词条id
     * @return FinanceDataNode
     */
    @Query("match (n:FinanceData) " +
            " where n.companyEntryId = {companyEntryId} or n.companyEntryId = {companyEntryId}+'1'" +
            " return n " +
            " order by n.endDate desc" +
            " limit 2")
    List<FinanceDataNode> getFinanceDataNode(String companyEntryId);

    /**
     * 公司资产负债数据
     *
     * @param companyEntryId 公司词条id
     * @return AssetsLiabilitiesNode
     */
    @Query("match (n:AssetsLiabilities) " +
            " where n.companyEntryId = {companyEntryId} or n.companyEntryId = {companyEntryId}+'1'" +
            " return n " +
            " order by n.endDate desc " +
            " limit 2")
    List<AssetsLiabilitiesNode> getAssetsLiabilitiesNode(String companyEntryId);

    /**
     * 公司现金流量数据
     *
     * @param companyEntryId 公司词条id
     * @return CashFlowDataNode
     */
    @Query("match (n:CashFlowData) " +
            " where n.companyEntryId = {companyEntryId} or n.companyEntryId = {companyEntryId}+'1'" +
            " return n " +
            " order by n.endDate desc" +
            " limit 2")
    List<CashFlowDataNode> getCashFlowDataNode(String companyEntryId);

    /**
     * 获取公司经营、财务、资产负债、现金流数据的季度和年度数据
     *
     * @param companyEntryId 公司id
     * @param label          neo4j节点label
     * @param propertyName   节点属性名
     * @return List<CompanyLineChartDto>
     */
    @Query("match (n) " +
            " where labels(n)[0] = {label} and n.companyEntryId = {companyEntryId} " +
            " return n[{propertyName}] as value, n.endDate as endDate" +
            " order by n.endDate")
    List<CompanyLineChartDto> getCompanyLineChartList(String companyEntryId, String label, String propertyName);

    /**
     * 删除所有数据
     * @param uuid
     */
    @Query(" match(n) where n.companyEntryId={uuid} or n.productEntryId={uuid} or n.industryEntryId={uuid}" +
            " detach delete n ")
    void deleteAllById(String uuid);
}
