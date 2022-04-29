package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.ArtworkEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/24 10:22
 */
@Repository
public interface ArtworkEntryRepository extends Neo4jRepository<ArtworkEntryNode, String> {
    /**
     * 删除工艺图关系
     *
     * @param artworkEntryId
     */
    @Query("match p=(pp:ProductEntry)<-[r:Materials|Manufacture|Technology*1..]-(:ProductEntry) " +
            "where pp.productEntryId={artworkEntryId} foreach(n in relationships(p)|delete n)")
    void deleteArtworkRelation(String artworkEntryId);


    /**
     * 根据产品查询他所属工艺图ids
     *
     * @param productEntryId
     * @return
     */
    @Query("match(p:ProductEntry)-[:Materials|Manufacture|Technology*0..15]->(pp:ProductEntry)-[:Artwork]->(i:ArtworkEntry) where p.productEntryId={productEntryId} return distinct i.artworkEntryId")
    List<String> getArtworkEntryIds(String productEntryId);

    /**
     * 根据产品id获取对应的产业链id
     *
     * @param productEntryId
     * @return
     */
    @Query("match(p:ProductEntry)-[:Materials|Manufacture|Technology*0..15]->(pp:ProductEntry)-[:Artwork]->(a:ArtworkEntry)" +
            "where p.productEntryId={productEntryId} with pp " +
            "match (pp)<-[:Ascription*2]-(:ProductEntry)-[:Industry]->(i:IndustryEntry)" +
            " return distinct i.industryEntryId")
    List<String> getIndustryEntryIds(String productEntryId);


    /**
     * 根据产品id和产业链id获取终端产品工艺图id
     *
     * @param productEntryId
     * @param industryEntryId
     * @return
     */
    @Query("match(p:ProductEntry)-[:Materials|Manufacture|Technology*0..15]->(pp:ProductEntry)<-[:Ascription*2]-(ppp:ProductEntry)" +
            "where p.productEntryId={productEntryId} and ppp.productEntryId={industryEntryId}" +
            " return distinct pp.productEntryId")
    List<String> getArtworkEntryIds(String productEntryId, String industryEntryId);
}
