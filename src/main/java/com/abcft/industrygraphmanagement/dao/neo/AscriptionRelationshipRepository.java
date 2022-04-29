package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.NodeRelationDto;
import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;
import com.abcft.industrygraphmanagement.model.entity.ProductGenealogyEntity;
import com.abcft.industrygraphmanagement.model.node.AscriptionRelationship;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/11
 */
@Repository
public interface AscriptionRelationshipRepository extends Neo4jRepository<AscriptionRelationship, String> {
    @Query("MATCH p=(p1:ProductEntry)-[r:Ascription*..2]->(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} and p1.type = {type} " +
            "RETURN p ")
    List<AscriptionRelationship> queryAllAscriptionRelationship(String productEntryId, String type);

    /**
     * 通过产品种类节点id查询产品类型node列表；通过产品类型节点id查询产品单元node列表
     *
     * @param productEntryId
     * @return
     */
    @Query("MATCH (p1:ProductEntry)-[r:Ascription]->(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId}" +
            "RETURN p2.productEntryId as productEntryId, p2.name as productEntryName")
    List<ProductGenealogyEntity> findNodeListByProductEntryId(String productEntryId);

    /**
     * 获取产品 归属关系的 产品
     *
     * @param industryEntryIds
     * @return
     */
    @Query("match re=(p:ProductEntry)-[r:Ascription]->(pp:ProductEntry) where p.productEntryId in {industryEntryIds}" +
            "return pp.productEntryId as endId,pp.name as endName,type(r) as  relationType,p.productEntryId as startId,p.name as startName,r.uuid as uuid")
    List<ProductToProductDto> getProductToProductList(List<String> industryEntryIds);

    /**
     * 通过产品类型节点id查询产品种类node列表；通过产品类型节点id查询产品单元node列表
     *
     * @param productEntryId
     * @return
     */
    @Query("MATCH (p1:ProductEntry)-[r:Ascription]->(p2:ProductEntry) " +
            "where p2.productEntryId = {productEntryId}" +
            "RETURN p1")
    ProductEntryNode findCategoryNodeByTypeProductEntryId(String productEntryId);

    /**
     * 查询产品种类下的产品类型节点，或者查询产品类型下的产品单元节点
     *
     * @param productEntryId 创建该产品词条的id
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p1.productEntryId ={productEntryId} " +
            "and type(r) = {relationName} " +
            "return p2.productEntryId as searchedId, p2.name as searchedName, {relationName} as relationType")
    List<NodeRelationDto> findNodeByEntryId(String productEntryId, String relationName);

    /**
     * 创建 产品种类-产品类型，产品类型-产品单元 归属关系
     *
     * @param productEntryId1
     * @param productEntryId2
     * @param ascriptionUuid
     */
    @Query("match (p1:ProductEntry),(p2:ProductEntry)" +
            "where p1.productEntryId = {productEntryId1} and p2.productEntryId = {productEntryId2}" +
            "create (p1)-[r:Ascription {uuid:{ascriptionUuid}}]->(p2)" +
            "return r")
    void createAscription(String productEntryId1, String productEntryId2, String ascriptionUuid);

    /**
     * 删除 开始节点 -> 结束节点 的关系
     *
     * @param productEntryId
     * @param relationName
     */
    @Query("MATCH (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} and type(r) = {relationName}" +
            "delete r ")
    void deleteStartProductRelation(String productEntryId, String relationName);

    /**
     * 删除 结束节点 -> 开始节点 的关系
     *
     * @param productEntryId
     * @param relationName
     */
    @Query("MATCH (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p2.productEntryId = {productEntryId} and type(r) = {relationName}" +
            "delete r ")
    void deleteEndProductRelation(String productEntryId, String relationName);

    /**
     * 通过开始产品词条节点id、结束产品词条节点id、关系类型名
     * 查询归属关系
     *
     * @param startProductEntryId 开始产品词条节点id
     * @param endProductEntryId   结束产品词条节点id
     * @param relationName        关系类型名
     */
    @Query("MATCH ascriptionRelationship=(p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where type(r) = {relationName} " +
            "and p1.productEntryId = {startProductEntryId} " +
            "and p2.productEntryId = {endProductEntryId}" +
            "return ascriptionRelationship ")
    AscriptionRelationship findAscriptionRelationship(String startProductEntryId, String endProductEntryId, String relationName);
}
