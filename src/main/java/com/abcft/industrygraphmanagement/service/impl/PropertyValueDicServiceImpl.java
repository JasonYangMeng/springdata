package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.PropertyValueDicRepository;
import com.abcft.industrygraphmanagement.model.constant.ExceptionContent;
import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.PropertyValueDicService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
@Service
public class PropertyValueDicServiceImpl implements PropertyValueDicService {

    @Autowired
    private PropertyValueDicRepository propertyValueDicRepository;

    /**
     * 属性值字典维护
     *
     * @param node 节点
     */
    @Override
    public void updatePropertyValueDic(PropertyValueDicNode node) throws Exception {
        if (node == null) {
            throw new Exception(ExceptionContent.PARAM_NULL_EXCEPTION);
        }
        if (StringUtils.isEmpty(node.getName()) || StringUtils.isEmpty(node.getNameEn())) {
            throw new Exception(ExceptionContent.PARAM_REQUIRE_EXCEPTION);
        }
        // uuid为空,设置uuid
        if (StringUtils.isEmpty(node.getUuid())) {
            node.setUuid(UuidUtils.generate());
        }
        Boolean checkResult = propertyValueDicRepository.checkPropertyValueDicRepeat(node.getName(), node.getNameEn());
        if (!checkResult) {
            propertyValueDicRepository.save(node);
        }
    }

    /**
     * 模糊查询属性值字典
     *
     * @param name 名字
     * @return Page<PropertyValueDicNode>
     */
    @Override
    public Paged<PropertyValueDicNode> getPropertyValueDicList(String name, int pageNum, int pageSize) {
        Paged<PropertyValueDicNode> paged = new Paged<>();
        int currentNum = pageNum == 1 ? 0 : (pageNum - 1) * pageSize;
        List<PropertyValueDicNode> list = propertyValueDicRepository.getPropertyValueDicList(name, currentNum, pageSize);
        paged.setRows(list);
        paged.setTotal(propertyValueDicRepository.getTotalCount(name));

        return paged;
    }

    /**
     * 删除属性字典
     *
     * @param id uuid列表
     */
    @Override
    public void deletePropertyValueDicList(List<String> id) {
        propertyValueDicRepository.deletePropertyValueDicList(id);
    }
}
