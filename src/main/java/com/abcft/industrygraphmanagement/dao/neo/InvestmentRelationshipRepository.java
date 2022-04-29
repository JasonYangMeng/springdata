package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.InvestmentRelationship;
import com.abcft.industrygraphmanagement.model.node.ManufactureRelationship;
import com.abcft.industrygraphmanagement.model.node.SupplyRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author WangZhiZhou
 * @date 2021/3/30
 */
@Repository
public interface InvestmentRelationshipRepository extends Neo4jRepository<ManufactureRelationship, String> {

    /**
     * 通过开始节点公司id、结束节点公司id、供给产品id、终端产品id
     * 查询供给关系
     *
     * @param startCompanyEntryId 开始节点公司id
     * @param endCompanyEntryId   结束节点公司id
     * @param supplyProductId     供给产品id
     * @param terminalProductId   终端产品id
     * @return InvestmentRelationship
     */
    @Query("match investmentRelationship=(c:CompanyEntry)-[r:Investment]->(cc:CompanyEntry) " +
            "where c.companyEntryId={startCompanyEntryId} " +
            "and cc.companyEntryId={endCompanyEntryId} " +
            "and r.supplyProductId={supplyProductId} " +
            "and r.terminalProductId={terminalProductId} " +
            "return investmentRelationship")
    InvestmentRelationship findInvestmentRelationship(String startCompanyEntryId, String endCompanyEntryId, String supplyProductId,
                                                      String terminalProductId);


}
