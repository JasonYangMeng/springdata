package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.PersonEntity;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
public interface PersonService {

    /**
     * 通过人物名称模糊查询人物信息
     * @param name 人物名
     * @return List<PersonEntity>
     */
    List<PersonEntity> queryPersonByName(String name);

    /**
     * 保存人物信息
     *
     * @param personEntity 人物
     */
    void savePerson(PersonEntity personEntity);

    /**
     * 更新人物信息
     *
     * @param personEntity 人物
     */
    void updatePerson(PersonEntity personEntity);
}
