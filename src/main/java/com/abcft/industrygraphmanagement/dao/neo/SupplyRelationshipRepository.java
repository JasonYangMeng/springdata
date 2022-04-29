package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;
import com.abcft.industrygraphmanagement.model.dto.SupplyParentDto;
import com.abcft.industrygraphmanagement.model.node.SupplyRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/9 14:15
 */
@Repository
public interface SupplyRelationshipRepository extends Neo4jRepository<SupplyRelationship, String> {
    /**
     * 删除某个公司-公司
     *
     * @param id 公司id
     */
    @Query("match(c:CompanyEntry)<-[sr:Supply]-(cc:CompanyEntry) where c.companyEntryId={id}   delete sr")
    void deleteParentByIds(String id);

    /**
     * 删除某个公司-公司 除ids以外的供应商关系
     *
     * @param id 公司id
     */
    @Query("match(c:CompanyEntry)<-[sr:Supply]-(cc:CompanyEntry) where c.companyEntryId={id} and not sr.uuid in{ids}   delete sr")
    void deleteParentByIds(String id,List<String> ids);

    /**
     * 删除某个公司-公司
     *
     * @param id 公司id
     */
    @Query("match(c:CompanyEntry)-[sr:Supply]->(cc:CompanyEntry) where c.companyEntryId={id}  delete sr")
    void deleteChildByIds(String id);
    /**
     * 删除某个公司-公司 除ids以外的下游关系
     *
     * @param id 公司id
     */
    @Query("match(c:CompanyEntry)-[sr:Supply]->(cc:CompanyEntry) where c.companyEntryId={id} and  not sr.uuid  in{ids} delete sr")
    void deleteChildByIds(String id,List<String> ids);

    /**
     * 查询公司某个产品下的供应商
     *
     * @param companyEntryId 公司id
     * @param productEntryId 公司产品id
     * @return
     */
    @Query("match (pp:ProductEntry)<-[:Production]-(c:CompanyEntry)-[sr:Supply]->(cc:CompanyEntry)-[:Production]->(p:ProductEntry)" +
            " where cc.companyEntryId={companyEntryId} and sr.terminalProductId ={productEntryId} and  p.productEntryId={productEntryId} and sr.supplyProductId=pp.productEntryId  " +
            " return distinct sr.uuid as uuid,c.companyEntryId as companyEntryId,c.name as companyName,pp.productEntryId as productEntryId," +
            "pp.name as productName,sr.terminalProductId as currentProductId,p.name as currentProductName,sr.supplyProportion as supplyProportion,sr.purchaseProportion as purchaseProportion," +
            " sr.price as price,sr.amount as amount,sr.sum as sum ")
    List<SupplyParentDto> getSupplyParentList(String companyEntryId, String productEntryId);

    /**
     * 查询公司某个产品下的供应商
     *
     * @param companyEntryId 公司id
     * @param productEntryId 公司产品id
     * @return
     */
    @Query("match (p:ProductEntry)<-[pr:Production]-(c:CompanyEntry)-[sr:Supply]->(c1:CompanyEntry)-[pr1:Production]->(p1:ProductEntry)" +
            " where c1.companyEntryId={companyEntryId} and sr.terminalProductId={productEntryId} and p1.productEntryId={productEntryId} and p1.productEntryId={productEntryId}" +
            " and sr.supplyProductId=p.productEntryId "  +
            "return sr.uuid as uuid, c.companyEntryId as startId,c.name as startName,p.productEntryId as supplyProductId,p.name as supplyProductName," +
            " c1.companyEntryId as endId,c1.name as endName,p1.productEntryId as terminalProductId,p1.name as terminalProductName, " +
            " type(sr) as relationType,sr.supplyProportion as supplyProportion,sr.purchaseProportion as purchaseProportion,sr.startTime as startTime,sr.endTime as endTime ")
    List<RelationshipEntryDto> getRelationshipList(String companyEntryId, String productEntryId);

    /**
     * 查询公司某个产品的下游公司
     *
     * @param companyEntryId 公司id
     * @param productEntryId 公司产品id
     * @return
     */
    @Query("match (pp:ProductEntry)<-[:Production]-(c:CompanyEntry)-[sr:Supply]->(cc:CompanyEntry)-[:Production]->(p:ProductEntry)" +
            " where c.companyEntryId={companyEntryId} and sr.supplyProductId ={productEntryId} and  pp.productEntryId={productEntryId} and sr.terminalProductId=p.productEntryId  " +
            " return sr.uuid as uuid,cc.companyEntryId as companyEntryId,cc.name as companyName,p.productEntryId as productEntryId," +
            " p.name as productName,sr.supplyProductId as currentProductId,pp.name as currentProductName,sr.supplyProportion as supplyProportion,sr.purchaseProportion as purchaseProportion," +
            " sr.price as price,sr.amount as amount,sr.sum as sum ")
    List<SupplyParentDto> getSupplyChildList(String companyEntryId, String productEntryId);

    /**
     * 通过开始节点公司id、结束节点公司id、供给产品id、终端产品id
     * 删除供给关系
     *
     * @param startCompanyEntryId 开始节点公司id
     * @param endCompanyEntryId   结束节点公司id
     * @param supplyProductId     供给产品id
     * @param terminalProductId   终端产品id
     */
    @Query("match (c:CompanyEntry)-[r:Supply]->(cc:CompanyEntry) " +
            "where c.companyEntryId={startCompanyEntryId} " +
            "and cc.companyEntryId={endCompanyEntryId}" +
            "and r.supplyProductId={supplyProductId} " +
            "and r.terminalProductId={terminalProductId} " +
            "delete r")
    void deleteSupplyRelationship(String startCompanyEntryId, String endCompanyEntryId, String supplyProductId, String terminalProductId);

    /**
     * 通过开始节点公司id、结束节点公司id、供给产品id、终端产品id
     * 查询供给关系
     *
     * @param startCompanyEntryId 开始节点公司id
     * @param endCompanyEntryId   结束节点公司id
     * @param supplyProductId     供给产品id
     * @param terminalProductId   终端产品id
     * @return SupplyRelationship
     */
    @Query("match supplyRelationship=(c:CompanyEntry)-[r:Supply]->(cc:CompanyEntry) " +
            "where c.companyEntryId={startCompanyEntryId} " +
            "and cc.companyEntryId={endCompanyEntryId} " +
            "and r.supplyProductId={supplyProductId} " +
            "and r.terminalProductId={terminalProductId} " +
            "return supplyRelationship")
    SupplyRelationship findSupplyRelationship(String startCompanyEntryId, String endCompanyEntryId, String supplyProductId, String terminalProductId);
}
