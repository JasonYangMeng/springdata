package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.*;
import com.abcft.industrygraphmanagement.model.node.ManufactureRelationship;
import com.abcft.industrygraphmanagement.model.node.MaterialsRelationship;
import com.abcft.industrygraphmanagement.model.node.TechnologyRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/15 14:53
 */
@Repository
public interface MaterialsRelationshipRepository extends Neo4jRepository<MaterialsRelationship, String> {

    @Query("match (p:ProductEntry)-[:Materials]->(pp:ProductEntry) where pp.productEntryId={productId} return p.productEntryId as productEntryId,p.name as productName")
    List<ProductDto> queryParentProductById(String productId);

    @Query("match (p:ProductEntry)-[:Materials]->(pp:ProductEntry) where p.productEntryId={productId} return pp.productEntryId as productEntryId,pp.name as productName")
    List<ProductDto> queryChildProductById(String productId);

    /**
     * 获取当前词条与列表
     *
     * @param industryEntryIds 产业词条id列表
     * @return List<ProductToProductDto>
     */
    @Query("match re=(p:ProductEntry)<-[r:Materials|:Manufacture|:Technology]-(pp:ProductEntry) where p.productEntryId in {industryEntryIds}" +
            "return p.productEntryId as endId,p.name as endName,type(r) as  relationType,pp.productEntryId as startId,pp.name as startName,r.uuid as uuid")
    List<ProductToProductDto> getProductToProductList(List<String> industryEntryIds);

    /**
     * 获取产品上游数据的线条
     *
     * @param productEntryId 产品词条id
     * @return List<IndustryToCompanyDto>
     */
    @Query("match (p:ProductEntry) where p.productEntryId={productEntryId}" +
            " call apoc.path.expand(p,'<Materials|<Manufacture|<Technology','',1,{level}) yield path" +
            " return path")
    List<MaterialsRelationship> getProductEdgesList1(String productEntryId, int level);
    /**
     * 获取产品上游数据的线条
     *
     * @param productEntryId 产品词条id
     * @return List<IndustryToCompanyDto>
     */
    @Query("match (p:ProductEntry) where p.productEntryId={productEntryId}" +
            " call apoc.path.expand(p,'<Materials|<Manufacture|<Technology','',1,{level}) yield path" +
            " return path")
    List<ManufactureRelationship> getProductEdgesList2(String productEntryId, int level);
    /**
     * 获取产品上游数据的线条
     *
     * @param productEntryId 产品词条id
     * @return List<IndustryToCompanyDto>
     */
    @Query("match (p:ProductEntry) where p.productEntryId={productEntryId}" +
            " call apoc.path.expand(p,'<Materials|<Manufacture|<Technology','',1,{level}) yield path" +
            " return path")
    List<TechnologyRelationship> getProductEdgesList3(String productEntryId, int level);
    /**
     * 查询上游产品节点
     *
     * @param productEntryId 创建该产品词条的id
     * @param relationName   创建关系的名字
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p2.productEntryId = {productEntryId} " +
            "and type(r) = {relationName} " +
            " optional match (i:IndustryEntry)<-[*..10]-(p1) " +
            "return distinct p1.productEntryId as searchedId, p1.name as searchedName, " +
            "r.costProportion as costProportion, {relationName} as relationType, " +
            "r.startTime as startTime, r.endTime as endTime,collect(distinct i.industryEntryId+','+i.name) as industries")
    List<NodeRelationDto> findUpStreamUnitNodeByEntryId(String productEntryId, String relationName);

    /**
     * 查询下游产品节点
     *
     * @param productEntryId 创建该产品词条的id
     * @param relationName   创建关系的名字
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} " +
            "and type(r) = {relationName}" +
            "  optional match (i:IndustryEntry)<-[*..10]-(p2)  " +
            "return p2.productEntryId as searchedId, p2.name as searchedName, " +
            "r.costProportion as costProportion, {relationName} as relationType, " +
            "r.startTime as startTime, r.endTime as endTime,collect(distinct i.industryEntryId+','+i.name) as industries")
    List<NodeRelationDto> findDownStreamUnitNodeByEntryId(String productEntryId, String relationName);

    /**
     * 创建 产品种类-产品种类 原材料关系
     *
     * @param uuid           关系唯一id
     * @param startNodeId    开始产品词条节点id
     * @param endNodeId      结束产品词条节点id
     * @param costProportion 原材料成本占比
     * @param startTime      开始时间
     * @param endTime        结束时间
     */
    @Query("match (p1:ProductEntry),(p2:ProductEntry)" +
            "where p1.productEntryId = {startNodeId} and p2.productEntryId = {endNodeId}" +
            "create (p1)-[r:Materials {uuid:{uuid},costProportion:{costProportion}," +
            "startTime:{startTime},endTime:{endTime}}]->(p2)" +
            "return r")
    void createMaterials(String uuid, String startNodeId, String endNodeId, String costProportion,
                         String startTime, String endTime);

    /**
     * 删除 开始节点 -> 结束节点 的关系
     *
     * @param productEntryId 产品词条节点id
     * @param relationName   关系类型名
     */
    @Query("MATCH (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} and type(r) = {relationName}" +
            "delete r ")
    void deleteStartProductRelation(String productEntryId, String relationName);

    /**
     * 删除 结束节点 -> 开始节点 的关系
     *
     * @param productEntryId 产品词条节点id
     * @param relationName   关系类型名
     */
    @Query("MATCH (p1:ProductEntry)<-[r]-(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} and type(r) = {relationName}" +
            "delete r ")
    void deleteEndProductRelation(String productEntryId, String relationName);

    /**
     * 删除开始产品节点->结束产品节点的 relationName关系
     *
     * @param startProductEntryId 开始产品词条节点id
     * @param endProductEntryId   结束产品词条节点id
     * @param relationName        关系类型名
     */
    @Query("MATCH (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where type(r) = {relationName} " +
            "and p1.productEntryId = {startProductEntryId} " +
            "and p2.productEntryId = {endProductEntryId}" +
            "delete r ")
    void deleteMaterialsRelationship(String startProductEntryId, String endProductEntryId, String relationName);

    /**
     * 通过开始产品词条节点id、结束产品词条节点id、关系类型名
     * 查询原材料关系
     *
     * @param startProductEntryId 开始产品词条节点id
     * @param endProductEntryId   结束产品词条节点id
     * @param relationName        关系类型名
     */
    @Query("MATCH materialsRelationship=(p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where type(r) = {relationName} " +
            "and p1.productEntryId = {startProductEntryId} " +
            "and p2.productEntryId = {endProductEntryId}" +
            "return materialsRelationship ")
    MaterialsRelationship findMaterialsRelationship(String startProductEntryId, String endProductEntryId, String relationName);
}
