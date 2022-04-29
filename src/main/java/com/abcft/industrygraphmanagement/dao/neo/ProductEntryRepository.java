package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.NodeRelationDto;
import com.abcft.industrygraphmanagement.model.node.AscriptionRelationship;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/4 16:08
 */
@Repository
public interface ProductEntryRepository extends Neo4jRepository<ProductEntryNode, String> {
    @Query("match (p:ProductEntry) where p.name = {name} return p")
    List<ProductEntryNode> queryProductEntryNode(String name);

    @Query("match (p:ProductEntry) where p.name = {name} return p")
    ProductEntryNode queryProductEntry(String name);

    @Query("MATCH p=(p1:ProductEntry)-[r:Ascription*..2]->(p2:ProductEntry) " +
            "where p1.productEntryId = {categoryId} and p1.type = {categoryType} and p2.productEntryId = {uuid} " +
            "RETURN p ")
    List<AscriptionRelationship> queryAscriptionRelationship(String categoryId, String categoryType, String uuid);


    /**
     * 查询产品单元节点对应的产品类型节点
     *
     * @param productEntryId 创建该产品词条的id
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p2.productEntryId ={productEntryId} " +
            "and type(r) = {relationName} " +
            "return p1.productEntryId as searchedId, p1.name as searchedName, {relationName} as relationType")
    List<NodeRelationDto> findTypeNodeByUnitEntryId(String productEntryId, String relationName);

    /**
     * 查询产品类型的产品产品种类节点
     *
     * @param productEntryId 创建该产品词条的id
     * @return List<ProductEntryNode>
     */
    @Query("match (p1:ProductEntry)-[r]->(p2:ProductEntry) " +
            "where p2.productEntryId ={productEntryId} " +
            "and type(r) = {relationName}" +
            "return p1.productEntryId as searchedId, p1.name as searchedName, {relationName} as relationType")
    List<NodeRelationDto> findCategoryNodeByTypeEntryId(String productEntryId, String relationName);
}
