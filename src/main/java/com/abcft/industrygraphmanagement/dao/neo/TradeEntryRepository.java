package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.TradeCompanyDto;
import com.abcft.industrygraphmanagement.model.dto.TradeSubcategoryDto;
import com.abcft.industrygraphmanagement.model.node.TradeEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/5/8
 */
@Repository
public interface TradeEntryRepository extends Neo4jRepository<TradeEntryNode, String> {

    @Query("match (n:TradeEntry), (c:CompanyEntry)" +
            " where parentTradeId = {tradeEntryId} and c.industry contains {tradeEntryId}" +
            " return n.tradeEntryId as tradeSubcategoryId" +
            "        n.name as name" +
            "        n.introduction as introduction" +
            "        toString(count(distinct c)) as companyQuantity")
    List<TradeSubcategoryDto> getTradeSubcategoryList(String tradeEntryId);

    @Query("match (n:CompanyEntry) " +
            " where n.industry contains {tradeEntryId} " +
            " return    n.companyEntryId as companyId" +
            "           n.name as companyName" +
            "           n.industryName as cascadeTrade")
    List<TradeCompanyDto> getTradeCompanyList(String tradeEntryId);
}
