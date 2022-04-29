package com.abcft.industrygraphmanagement.dao.mysql;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2020/3/26.
 */
@Repository
@Mapper
public interface IBaseMapper<T, DbId>{
    /**
     * 获取所有
     *
     * @return
     */
    List<T> getAll();

    /**
     * 修改数据
     *
     * @param t
     */
    void modify(T t);

    /**
     * 添加数据
     *
     * @param t
     * @return
     */
    int add(T t);

    /**
     * 查询基本信息
     *
     * @param id
     * @return
     */
    T getOne(@Param("id") DbId id);

    /**
     * 删除数据
     *
     * @param id
     */
    void deleteById(@Param("ids") List<DbId> ids);
}
