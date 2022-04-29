package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.DicDto;
import com.abcft.industrygraphmanagement.model.dto.NodeRelationDto;
import com.abcft.industrygraphmanagement.model.dto.ProductDto;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductionRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/4 15:34
 */
@Repository
public interface ProductionRelationshipRepository extends Neo4jRepository<ProductionRelationship, String> {

    /**
     * 删除某个公司 公司-产品关系
     *
     * @param id 公司id
     */
    @Query("match(c:CompanyEntry)-[pr:Production]->(p:ProductEntry) where c.companyEntryId={id}  delete pr")
    void deleteByIds(String id);

    /**
     * 删除某个公司 公司-产品关系 除ids以外的关系
     *
     * @param id 公司id
     */
    @Query("match(c:CompanyEntry)-[pr:Production]->(p:ProductEntry) where c.companyEntryId={id} and not pr.uuid in {ids}  delete pr")
    void deleteByIds(String id, List<String> ids);

    /**
     * 根据公司id获取 产品词条以及关系属性信息
     *
     * @param companyEntryId 公司词条id
     * @return List<ProductDto>
     */
    @Query("match (c:CompanyEntry)-[pr:Production]->(p:ProductEntry) where c.companyEntryId={companyEntryId}" +
            " return pr.uuid as uuid,pr.incomeProportion as incomeProportion ,pr.productGross as productGross,p.createTime as createTime, " +
            " pr.productPrice as productPrice,pr.capacity as capacity,pr.capacityRatio as capacityRatio,pr.capacityProportion as capacityProportion," +
            " p.productEntryId as productEntryId,p.name as productName,pr.marketShares as marketShares" +
            " order by toFloat(incomeProportion) desc ")
    List<ProductDto> getProductListByCompanyEntryId(String companyEntryId);

    /**
     * 根据产品获取供应商
     *
     * @param productEntryId 产品词条id
     * @return List<DicDto>
     */
    @Query("match (c:CompanyEntry)-[:Production]->(p:ProductEntry) where p.productEntryId={productEntryId} return  c.companyEntryId as companyEntryId,c.name as companyName")
    List<DicDto> getCompanyByProductId(String productEntryId);

    /**
     * 查询生产关系的公司节点
     *
     * @param productEntryId 创建该产品词条的id
     * @param relationName   关系类型名称
     * @return List<CompanyEntryNode>
     */
    @Query("match (c:CompanyEntry)-[r]->(p:ProductEntry) " +
            "where type(r) = {relationName} and p.productEntryId = {productEntryId} " +
            "return c.companyEntryId as searchedId, c.name as searchedName, " +
            "r.incomeProportion as incomeProportion, {relationName} as relationType, " +
            "r.productTotalIncome as productTotalIncome, r.revenueGrowth as revenueGrowth, " +
            "r.productPrice as productPrice, r.productGross as productGross, " +
            "r.capacity as capacity, r.industryCapacity as industryCapacity, " +
            "r.capacityRatio as capacityRatio, r.capacityProportion as capacityProportion")
    List<NodeRelationDto> findCompanyNodeByEntryId(String productEntryId, String relationName);

    /**
     * 通过产品词条id 删除 公司节点 -> 产品节点 的关系
     *
     * @param productEntryId 产品词条id
     * @param relationName   关系类型名称
     */
    @Query("MATCH (c:CompanyEntry)-[r]->(p:ProductEntry) " +
            "where p.productEntryId = {productEntryId} and type(r) = {relationName}" +
            "delete r ")
    void deleteCompanyProductRelation(String productEntryId, String relationName);

    /**
     * 通过公司词条id和产品词条id 删除 公司节点->产品节点 生产关系
     *
     * @param companyEntryId 公司词条id
     * @param productEntryId 产品词条id
     * @param relationName   关系类型名称
     */
    @Query("MATCH (c:CompanyEntry)-[r]->(p:ProductEntry) " +
            "where type(r) = {relationName} " +
            "and c.companyEntryId={companyEntryId} " +
            "and p.productEntryId = {productEntryId}" +
            "delete r ")
    void deleteProductionRelationship(String companyEntryId, String productEntryId, String relationName);

    /**
     * 通过公司词条id和产品词条id 查询 ProductionRelationship
     *
     * @param companyEntryId 公司词条id
     * @param productEntryId 产品词条id
     * @param relationName   关系类型名称
     * @return ProductionRelationship
     */
    @Query("MATCH productionRelationship = (c:CompanyEntry)-[r]->(p:ProductEntry) " +
            "where type(r) = {relationName} " +
            "and c.companyEntryId={companyEntryId} " +
            "and p.productEntryId = {productEntryId}" +
            "return productionRelationship ")
    ProductionRelationship findProductionRelationship(String companyEntryId, String productEntryId, String relationName);

    /**
     * 通过公司词条id查询该公司下的所有产品
     *
     * @param companyEntryId 公司词条id
     * @return List<ProductEntryNode>
     */
    @Query("MATCH (c:CompanyEntry)-[r:Production]->(p:ProductEntry) " +
            "where  c.companyEntryId={companyEntryId}" +
            "return p")
    List<ProductEntryNode> findProductEntryNodeList(String companyEntryId);

}
