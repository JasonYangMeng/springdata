package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.ArtworkRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/3/24 10:23
 */
@Repository
public interface ArtworkRelationshipRepository extends Neo4jRepository<ArtworkRelationship, String> {

    /**
     * 判断关系是否存在
     * @param artworkEntryId
     * @return
     */
    @Query("match (p:ProductEntry)-[r]->(e:ArtworkEntry) where e.artworkEntryId={artworkEntryId}" +
            "return count(r)>0 ")
    boolean existsRelationship(String artworkEntryId);
}
