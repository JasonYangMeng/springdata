package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.RelationshipDicNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
public interface RelationshipDicRepository extends Neo4jRepository<RelationshipDicNode, String> {

    /**
     * 模糊查询关系字典
     *
     * @param name 名字
     * @return List<RelationshipDicNode>
     */
    @Query("match (n:RelationshipDic) " +
            " where " +
            " case when {name} is not null then n.name contains {name} else 1 = 1 end " +
            " return n " +
            " skip {currentNum}" +
            " limit {pageSize}")
    List<RelationshipDicNode> getRelationshipDicList(String name, Integer currentNum, Integer pageSize);

    @Query("match (n:RelationshipDic) " +
            "where n.uuid in {id} " +
            "delete n ")
    void deleteRelationshipDicList(List<String> id);

    /**
     * 关系字典总数
     *
     * @return int
     */
    @Query("match (n:RelationshipDic) " +
            " where case when {name} is not null then n.name contains {name} else 1 = 1 end " +
            " return count(n) ")
    int getTotalCount(String name);

    @Query("match (n:RelationshipDic) " +
            " where n.name = {name} " +
            "   and n.nameEn = {nameEn} " +
            " return case when count(n) > 0 then true else false end")
    Boolean checkRelationshipDicRepeat(String name, String nameEn);
}
