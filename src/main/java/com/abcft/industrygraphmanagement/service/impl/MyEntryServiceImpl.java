package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.MyEntryRepository;
import com.abcft.industrygraphmanagement.model.condition.MyCreateEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.MyCreateEntryDto;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.MyEntryService;
import com.abcft.industrygraphmanagement.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/5/18
 */
@Service
public class MyEntryServiceImpl implements MyEntryService {

    @Autowired
    private MyEntryRepository myEntryRepository;

    @Override
    public Paged<MyCreateEntryDto> getMyEntry(MyCreateEntryCondition condition, String property) {
        String currentUserId = UserContext.getCurrentUserId();
        currentUserId = "1";
        if (StringUtils.isEmpty(currentUserId)) {
            return null;
        }
        String name = condition.getName();
        String auditStatus = condition.getAuditStatus();
        String label = condition.getLabel();
        String order = condition.getOrder();
        String upOrDown = condition.getUpOrDown();
        int page = condition.getPage();
        int size = condition.getSize();
        int pageNum = page < 1 ? 0 : (page - 1) * size;

        List<MyCreateEntryDto> resultList;
        if ("down".equals(upOrDown)) {
            resultList = myEntryRepository
                    .getMyEntryDesc(property, name, currentUserId, auditStatus, label, order, pageNum, size);
        } else {
            resultList = myEntryRepository
                    .getMyEntry(property, name, currentUserId, auditStatus, label, order, pageNum, size);
        }
        Paged<MyCreateEntryDto> myCreateEntryDtoPaged = new Paged<>();
        myCreateEntryDtoPaged.setRows(resultList);
        myCreateEntryDtoPaged.setTotal(myEntryRepository.getMyEntryTotalCount(property, name, auditStatus, label, currentUserId));
        return myCreateEntryDtoPaged;
    }
}
