package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.dto.PersonEntryParamDto;
import com.abcft.industrygraphmanagement.model.dto.PersonEntryResultDto;

/**
 * @author WangZhiZhou
 * @date 2021/3/31
 */
public interface PersonEntryService {

    /**
     * 创建人物词条
     *
     * @param personEntryResult 创建人物词条入参对象
     */
    void createPersonEntry(PersonEntryResultDto personEntryResult) throws Exception;

    /**
     * 根据人物词条id来回显创建人物词条数据
     * @param personEntryId 人物词条id
     * @return 回显数据 PersonEntryResultDto
     */
    PersonEntryResultDto queryPersonEntrySource(String personEntryId);
}
