package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.dto.MyCreateEntryDto;
import com.abcft.industrygraphmanagement.model.node.CompanyEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/5/18
 */
@Repository
public interface MyEntryRepository extends Neo4jRepository<CompanyEntryNode, String> {

    /**
     * 升序排序
     */
    @Query("match (n) " +
            " where " +
            "   n[{property}] = {currentUserId} " +
            "   and case when {name} is null then 1 = 1 when {name} = '' then 1 = 1 " +
            "            else n.name contains {name} end " +
            "   and case when {auditStatus} is null then 1 = 1 when {auditStatus} = '' then 1 = 1 " +
            "            else n.auditStatus = {auditStatus} end " +
            "   and case when {label} is null then 1 = 1 when {label} = '' then 1 = 1 " +
            "            else labels(n)[0] = {label} end " +
            " return n.name as name, labels(n)[0] as label, " +
            "        case when n.auditStatus is null then '1' else n.auditStatus end as auditStatus, " +
            "        n.auditMind as auditMind, n.createTime as createTime, n.modifyTime as modifyTime " +
            " order by n[{order}] " +
            " skip {pageNum}" +
            " limit {size}")
    List<MyCreateEntryDto> getMyEntry(String property, String name, String currentUserId, String auditStatus, String label,
                                            String order, int pageNum, int size);


    /**
     * 降序排序
     */
    @Query("match (n) " +
            " where " +
            "   n[{property}] = {currentUserId} " +
            "   and case when {name} is null then 1 = 1 when {name} = '' then 1 = 1 " +
            "            else n.name contains {name} end" +
            "   and case when {auditStatus} is null then 1 = 1 when {auditStatus} = '' then 1 = 1 " +
            "            else n.auditStatus = {auditStatus} end " +
            "   and case when {label} is null then 1 = 1 when {label} = '' then 1 = 1 " +
            "            else labels(n)[0] = {label} end " +
            " return n.name as name, labels(n)[0] as label, " +
            "        case when n.auditStatus is null then '1' else n.auditStatus end as auditStatus, " +
            "        n.auditMind as auditMind, n.createTime as createTime, n.modifyTime as modifyTime " +
            " order by n[{order}] desc " +
            " skip {pageNum}" +
            " limit {size}")
    List<MyCreateEntryDto> getMyEntryDesc(String property, String name, String currentUserId, String auditStatus,
                                          String label,String order, int pageNum, int size);

    /**
     * 查询词条总数
     */
    @Query("match (n) " +
            " where n[{property}] = {currentUserId} " +
            "   and case when {name} is null then 1 = 1 when {name} = '' then 1 = 1 " +
            "            else n.name contains {name} end " +
            "   and case when {label} is null then 1 = 1 when {label} = '' then 1 = 1 " +
            "            else labels(n)[0] = {label} end " +
            "   and case when {auditStatus} is null then 1 = 1 when {auditStatus} = '' then 1 = 1 " +
            "            else n.auditStatus = {auditStatus} end " +
            " return count(n)")
    int getMyEntryTotalCount(String property, String name, String auditStatus, String label, String currentUserId);
}
