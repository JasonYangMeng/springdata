package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.IdAndNameDto;
import com.abcft.industrygraphmanagement.model.node.IndustryEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/20 14:42
 */
@Repository
public interface IndustryEntryRepository extends Neo4jRepository<IndustryEntryNode, String> {

    /**
     * 删除产业链关系
     *
     * @param industryEntryId
     */
    @Query("match p=(pp:ProductEntry)<-[r:Materials|Manufacture|Technology*1..]-(:ProductEntry) " +
            "where pp.productEntryId={industryEntryId} foreach(n in relationships(p)|delete n)")
    void deleteIndustryRelation(String industryEntryId);

    /**
     * 根据产品查询他所属产业链id
     *
     * @param productEntryId
     * @return
     */
    @Query("match(p:ProductEntry)-[:Materials|Manufacture|Technology*0..15]->(pp:ProductEntry)-[:Industry]->(i:IndustryEntry) " +
            "where p.productEntryId={productEntryId} " +
            "return distinct i.industryEntryId")
    List<String> getIndustryEntryIds(String productEntryId);

    /**
     * 查询产业链名称
     *
     * @param name
     * @return
     */
    @Query(" match(n:IndustryEntry) where n.name starts with {name} " +
            " return n.name as name,n.industryEntryId as id " +
            " limit 10 ")
    List<IdAndNameDto> getIdAndNameList(String name);
}
