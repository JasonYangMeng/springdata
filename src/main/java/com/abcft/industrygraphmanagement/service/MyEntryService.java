package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.MyCreateEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.MyCreateEntryDto;
import com.abcft.industrygraphmanagement.model.result.Paged;

/**
 * @author WangZhiZhou
 * @date 2021/5/18
 */
public interface MyEntryService {

    Paged<MyCreateEntryDto> getMyEntry(MyCreateEntryCondition condition, String property);
}
