package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.PersonMapper;
import com.abcft.industrygraphmanagement.model.entity.PersonEntity;
import com.abcft.industrygraphmanagement.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    /**
     * 通过人物名称模糊查询人物信息
     * @param name 人物名
     * @return List<PersonEntity>
     */
    @Override
    public List<PersonEntity> queryPersonByName(String name) {
        return personMapper.queryPersonByName(name);
    }

    /**
     * 保存人物信息
     *
     * @param personEntity 人物
     */
    @Override
    public void savePerson(PersonEntity personEntity) {
        personMapper.savePerson(personEntity);
    }

    /**
     * 更新人物信息
     *
     * @param personEntity 人物
     */
    @Override
    public void updatePerson(PersonEntity personEntity) {
        personMapper.updatePerson(personEntity);
    }
}
