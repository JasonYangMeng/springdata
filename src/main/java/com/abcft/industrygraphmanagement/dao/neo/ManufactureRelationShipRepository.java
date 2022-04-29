package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.NodeRelationDto;
import com.abcft.industrygraphmanagement.model.node.ManufactureRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/23 10:38
 */
@Repository
public interface ManufactureRelationShipRepository extends Neo4jRepository<ManufactureRelationship, String> {
    /**
     * 查询上游产品节点
     *
     * @param productEntryId 创建该产品词条的id
     * @param relationName 创建关系的名字
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p2.productEntryId = {productEntryId} " +
            "and type(r) = {relationName} " +
            "return p1.productEntryId as searchedId, p1.name as searchedName, " +
            "r.costProportion as costProportion, {relationName} as relationType, " +
            "r.startTime as startTime, r.endTime as endTime")
    List<NodeRelationDto> findUpStreamUnitNodeByEntryId(String productEntryId, String relationName);

    /**
     * 查询下游产品节点
     *
     * @param productEntryId 创建该产品词条的id
     * @param relationName 创建关系的名字
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} " +
            "and type(r) = {relationName}" +
            "return p2.productEntryId as searchedId, p2.name as searchedName, " +
            "r.costProportion as costProportion, {relationName} as relationType, " +
            "r.startTime as startTime, r.endTime as endTime")
    List<NodeRelationDto> findDownStreamUnitNodeByEntryId(String productEntryId, String relationName);

    /**
     * 创建 产品种类-产品种类 制造设备关系
     *
     * @param uuid
     * @param startNodeId
     * @param endNodeId
     */
    @Query("match (p1:ProductEntry),(p2:ProductEntry)" +
            "where p1.productEntryId = {startNodeId} and p2.productEntryId = {endNodeId}" +
            "create (p1)-[r:Manufacture {uuid:{uuid}}]->(p2)" +
            "return r")
    void createManufacture(String uuid, String startNodeId, String endNodeId);

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
    @Query("MATCH (p1:ProductEntry)<-[r]-(p2:ProductEntry) " +
            "where p1.productEntryId = {productEntryId} and type(r) = {relationName}" +
            "delete r ")
    void deleteEndProductRelation(String productEntryId, String relationName);

    /**
     * 通过开始产品词条节点id、结束产品词条节点id、关系类型名
     * 查询 产品-产品 制造设备关系
     *
     * @param startProductEntryId 开始产品词条节点id
     * @param endProductEntryId   结束产品词条节点id
     * @param relationName        关系类型名
     */
    @Query("MATCH manufactureRelationship=(p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where type(r) = {relationName} " +
            "and p1.productEntryId = {startProductEntryId} " +
            "and p2.productEntryId = {endProductEntryId}" +
            "return manufactureRelationship ")
    ManufactureRelationship findManufactureRelationship(String startProductEntryId, String endProductEntryId, String relationName);
}
