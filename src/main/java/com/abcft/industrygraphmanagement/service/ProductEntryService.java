package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.dto.ProductEntryParamDto;
import com.abcft.industrygraphmanagement.model.dto.ProductEntryResultDto;
import com.abcft.industrygraphmanagement.model.entity.ProductGenealogyEntity;
import com.abcft.industrygraphmanagement.model.node.AscriptionRelationship;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/11
 */
public interface ProductEntryService {

    List<ProductEntryNode> queryProductEntryNode(String name);

    ProductEntryNode queryProductEntry(String name);

    List<AscriptionRelationship> queryAscriptionRelationship(String productEntryId, String type);

    ProductGenealogyEntity queryProductGenealogy(String productEntryId) throws Exception;

    void deleteProductEntry(String productEntryId) throws Exception;

    void addProductEntry(ProductEntryParamDto paramDto) throws Exception;

    String addOrUpdateProductEntry(ProductEntryParamDto paramDto) throws Exception;

    ProductEntryResultDto queryProductEntryByTypeAndName(String productEntryId) throws Exception;
}
