package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.TopSearchNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@Repository
public interface TopSearchRepository extends Neo4jRepository<TopSearchNode, String> {

    /**
     * 修改状态
     *
     * @param uuid
     * @param issue
     */
    @Query(" match(n:TopSearch) " +
            " where n.uuid = {uuid} " +
            " set n.issue={issue} ")
    void updateStatus(String uuid, String issue);

    /**
     * 获取最大行号
     *
     * @return
     */
    @Query(" match(n:TopSearch)" +
            " return max(n.orderNum) ")
    int getMaxNum();

    /**
     * 获取分页数据
     *
     * @param skip
     * @param limit
     * @return
     */
    @Query(" match(n:TopSearch) where n.type={type} and n.issue contains({issue}) " +
            " return n" +
            " order by n.issue desc,n.orderNum" +
            " skip {skip} limit {limit} ")
    List<TopSearchNode> getList(int skip, int limit, String issue, int type);

    /**
     * 获取总数
     *
     * @return
     */
    @Query("match(n:TopSearch) where n.type={type} and n.uuid is not null return count(n)")
    int total(int type);

    /**
     * 获取上移节点
     *
     * @param orderNum
     * @return
     */
    @Query(" match(n:TopSearch) " +
            " where n.type={type} and n.orderNum<{orderNum}" +
            " return n " +
            " order by n.orderNum desc limit 1")
    TopSearchNode getUpNode(int orderNum, int type);

    /**
     * 获取下移节点
     *
     * @param orderNum
     * @return
     */
    @Query(" match(n:TopSearch) " +
            " where n.type={type} and n.orderNum>{orderNum}" +
            " return n " +
            " order by n.orderNum  limit 1")
    TopSearchNode getDownNode(int orderNum, int type);

    /**
     * 修改序号
     *
     * @param uuid
     * @param orderNum
     */
    @Query(" match(n:TopSearch) " +
            " where n.uuid = {uuid} " +
            " set n.orderNum={orderNum} ")
    void updateSortNum(String uuid, int orderNum);

    /**
     * 根据type删除数据
     *
     * @param type
     */
    @Query(" match(n:TopSearch) " +
            " where n.type = {type} " +
            " delete n ")
    void deleteByType(int type);

    /**
     * @param title
     * @param type
     * @return
     */
    @Query("match(n:TopSearch) where n.name={title} and n.type={type} return n limit 1")
    TopSearchNode getByTitleAndType(String title, int type);
}
