package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.DataSourceNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/7/5 11:21
 */
@Repository
public interface DataSourceRepository  extends Neo4jRepository<DataSourceNode, String> {
    /**
     * 获取分页数据
     *
     * @param skip
     * @param limit
     * @return
     */
    @Query(" match(n:DataSource) " +
            " return n" +
            " order by n.createTime desc" +
            " skip {skip} limit {limit} ")
    List<DataSourceNode> getList(int skip, int limit);

    /**
     * 获取总数
     *
     * @return
     */
    @Query("match(n:DataSource) where n.uuid is not null return count(n)")
    int total();


    /**
     * 获取最大行号
     *
     * @return
     */
    @Query(" match(n:DataSource) where n.dataTemplate={dataTemplate}" +
            " return max(n.type) ")
    int getMaxType(String dataTemplate);
}
