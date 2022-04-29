package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
@Repository
public interface PropertyValueDicRepository extends Neo4jRepository<PropertyValueDicNode, String> {

    /**
     * 模糊查询属性值字典 分页
     *
     * @param name 名字
     * @return List<PropertyValueDicNode>
     */
    @Query("match (n:PropertyValueDic) " +
            " where " +
            " case when {name} is not null then n.name contains {name} else 1 = 1 end " +
            " return n " +
            " skip {currentNum}" +
            " limit {pageSize}")
    List<PropertyValueDicNode> getPropertyValueDicList(String name, int currentNum, int pageSize);

    /**
     * 删除属性字典
     *
     * @param id uuid列表
     */
    @Query("match (n:PropertyValueDic) " +
            "where n.uuid in {id} " +
            "delete n ")
    void deletePropertyValueDicList(List<String> id);

    /**
     * 属性字典总数
     *
     * @return
     */
    @Query("match (n:PropertyValueDic) " +
            " where case when {name} is not null then n.name contains {name} else 1 = 1 end " +
            " return count(n) ")
    int getTotalCount(String name);

    @Query("match (n:PropertyValueDic) " +
            " where n.name = {name} " +
            "   and n.nameEn = {nameEn} " +
            " return case when count(n) > 0 then true else false end")
    Boolean checkPropertyValueDicRepeat(String name, String nameEn);
}
