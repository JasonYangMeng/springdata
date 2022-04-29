package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.NewsEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@Repository
public interface NewsEntryRepository extends Neo4jRepository<NewsEntryNode, String> {

    @Query(" match(n:NewsEntry) " +
            " where n.title = {title} " +
            " return n")
    NewsEntryNode findNewsEntryNodeByName(String title);

    /**
     * 获取最大行号
     *
     * @return
     */
    @Query(" match(n:NewsEntry)" +
            " return max(n.orderNum) ")
    int getMaxNum();

    /**
     * 获取分页数据
     *
     * @param skip
     * @param limit
     * @return
     */
    @Query(" match(n:NewsEntry) where n.type={type} and n.issue contains({issue}) and n.title contains({name}) " +
            " return n" +
            " order by n.issue desc,n.orderNum" +
            " skip {skip} limit {limit} ")
    List<NewsEntryNode> getList(int skip, int limit, String issue, String name, int type);

    /**
     * 获取总数
     *
     * @return
     */
    @Query("match(n:NewsEntry) where n.issue contains({issue}) and n.type={type} and n.newsEntryId is not null  and n.title contains({name})  " +
            " return count(n)")
    int total(String name, String issue, int type);

    /**
     * 修改状态
     *
     * @param newsEntryId
     * @param issue
     */
    @Query(" match(n:NewsEntry) " +
            " where n.newsEntryId = {newsEntryId} " +
            " set n.issue={issue} ")
    void updateNewsStatus(String newsEntryId, String issue);

    /**
     * 获取上移节点
     *
     * @param orderNum
     * @return
     */
    @Query(" match(n:NewsEntry) " +
            " where n.orderNum<{orderNum} and n.type={type} " +
            " return n " +
            " order by n.orderNum desc limit 1")
    NewsEntryNode getUpNode(int orderNum, int type);

    /**
     * 获取下移节点
     *
     * @param orderNum
     * @return
     */
    @Query(" match(n:NewsEntry) " +
            " where n.orderNum>{orderNum} and n.type={type} " +
            " return n " +
            " order by n.orderNum  limit 1")
    NewsEntryNode getDownNode(int orderNum, int type);

    /**
     * 修改序号
     *
     * @param newsEntryId
     * @param orderNum
     */
    @Query(" match(n:NewsEntry) " +
            " where n.newsEntryId = {newsEntryId} " +
            " set n.orderNum={orderNum} ")
    void updateSortNum(String newsEntryId, int orderNum);

    /**
     * 根据type删除数据
     *
     * @param type
     */
    @Query(" match(n:NewsEntry) " +
            " where n.type = {type} " +
            " delete n ")
    void deleteByType(int type);

    /**
     * @param title
     * @param type
     * @return
     */
    @Query("match(n:NewsEntry) where n.title={title} and n.type={type} return n limit 1")
    NewsEntryNode getByTitleAndType(String title, int type);
}
