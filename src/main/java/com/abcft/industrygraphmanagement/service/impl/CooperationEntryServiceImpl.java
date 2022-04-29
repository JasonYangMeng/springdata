package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.CompanyEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductionRelationshipRepository;
import com.abcft.industrygraphmanagement.dao.neo.SupplyRelationshipRepository;
import com.abcft.industrygraphmanagement.model.condition.CooperationCondition;
import com.abcft.industrygraphmanagement.model.constant.RelationshipType;
import com.abcft.industrygraphmanagement.model.dto.CooperationDto;
import com.abcft.industrygraphmanagement.model.dto.GraphDto;
import com.abcft.industrygraphmanagement.model.dto.ProductionDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;
import com.abcft.industrygraphmanagement.model.node.CompanyEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductionRelationship;
import com.abcft.industrygraphmanagement.model.node.SupplyRelationship;
import com.abcft.industrygraphmanagement.service.CompanyEntryService;
import com.abcft.industrygraphmanagement.service.CooperationEntryService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Created by YangMeng on 2021/3/24 10:28
 */
@Service
@Slf4j
public class CooperationEntryServiceImpl implements CooperationEntryService {

    @Autowired
    private ProductionRelationshipRepository productionRelationshipRepository;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private CompanyEntryRepository companyEntryRepository;

    @Autowired
    private SupplyRelationshipRepository supplyRelationshipRepository;

    @Autowired
    private CompanyEntryService companyEntryService;

    /**
     * 获取生产协作图信息
     *
     * @param companyEntryId
     * @return
     */
    @Override
    public CooperationCondition getCooperationEntryList(String companyEntryId, String productEntryId) {
        CooperationCondition cooperationCondition = new CooperationCondition();
        ProductionRelationship productionRelationship = productionRelationshipRepository.findProductionRelationship(companyEntryId, productEntryId, RelationshipType.PRODUCTION);
        if (productionRelationship == null) {
            return cooperationCondition;
        }
        CooperationDto cooperationDto = new CooperationDto();
        cooperationDto.setName(productionRelationship.getRelationEntryName());
        cooperationDto.setAliasName(productionRelationship.getAliasName());
        cooperationDto.setIntroduction(productionRelationship.getIntroduction());
        cooperationDto.setCompanyEntryId(companyEntryId);
        cooperationDto.setProductEntryId(productEntryId);
        cooperationDto.setImagePath(productionRelationship.getImagePath());
        List<RelationshipEntryDto> relationshipEntryDtoList = new ArrayList<>();
        getRelationshipDtoList(companyEntryId, productEntryId, relationshipEntryDtoList);
        cooperationCondition.setRelationshipEntryDtoList(relationshipEntryDtoList);
        cooperationCondition.setCooperationDto(cooperationDto);
        return cooperationCondition;
    }

    /**
     * 编辑生产工艺图
     *
     * @param cooperationCondition
     * @return
     */
    @Override
    public boolean updateCooperation(CooperationCondition cooperationCondition) throws Exception {
        //保存当前公司产品关系
        saveProductionRelationship(cooperationCondition);
        //保存公司和公司之间的关系
        List<RelationshipEntryDto> relationshipEntryDtoList = cooperationCondition.getRelationshipEntryDtoList();
        deleteCompanyAndCompanyRelation(cooperationCondition.getCooperationDto().getCompanyEntryId(), cooperationCondition.getCooperationDto().getProductEntryId());

        List<ProductionDto> productionDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(relationshipEntryDtoList)) {
            List<SupplyRelationship> supplyRelationshipList = new ArrayList<>();
            SupplyRelationship supplyRelationship;

            for (RelationshipEntryDto relationshipEntryDto : relationshipEntryDtoList) {
                supplyRelationship = getSupplyRelationship(relationshipEntryDto);
                productionDtoList.add(new ProductionDto(relationshipEntryDto.getStartId(), relationshipEntryDto.getSupplyProductId(), relationshipEntryDto.getSupplyProductName()));
                productionDtoList.add(new ProductionDto(relationshipEntryDto.getEndId(), relationshipEntryDto.getTerminalProductId(), relationshipEntryDto.getTerminalProductName()));
                supplyRelationshipList.add(supplyRelationship);
            }
            if (!CollectionUtils.isEmpty(supplyRelationshipList)) {
                supplyRelationshipRepository.saveAll(supplyRelationshipList);
            }
            companyEntryService.addProduction(productionDtoList);
        }
        return true;
    }

    /**
     * 删除公司下所有关联关系
     *
     * @param companyEntryId
     * @param productEntryId
     */
    private void deleteCompanyAndCompanyRelation(String companyEntryId, String productEntryId) {
        List<RelationshipEntryDto> relationshipEntryDtoList = new ArrayList<>();
        getRelationshipDtoList(companyEntryId, productEntryId, relationshipEntryDtoList);
        if (!CollectionUtils.isEmpty(relationshipEntryDtoList)) {
            for (RelationshipEntryDto relationshipEntryDto : relationshipEntryDtoList) {
                supplyRelationshipRepository.deleteById(relationshipEntryDto.getUuid());
            }
        }
    }


    /**
     * 获取生产协作图集合
     *
     * @param companyEntryId
     * @param productEntryId
     * @return
     */
    @Override
    public void getRelationshipDtoList(String companyEntryId, String productEntryId, List<RelationshipEntryDto> relationshipEntryDtoList) {
        List<RelationshipEntryDto> relationshipList = supplyRelationshipRepository.getRelationshipList(companyEntryId, productEntryId);
        if (CollectionUtils.isEmpty(relationshipList)) {
            return;
        }
        relationshipEntryDtoList.addAll(relationshipList);
        for (RelationshipEntryDto item : relationshipList) {
            getRelationshipDtoList(item.getStartId(), item.getSupplyProductId(), relationshipEntryDtoList);
        }
    }

    /**
     * 获取生产协作图图谱
     *
     * @param companyEntryId
     * @param productEntryId
     * @return
     */
    @Override
    public GraphDto getCooperationGraph(String companyEntryId, String productEntryId) {
        GraphDto graphDto = new GraphDto();
        List<RelationshipEntryDto> relationshipEntryDtoList = new ArrayList<>();
        getRelationshipDtoList(companyEntryId, productEntryId, relationshipEntryDtoList);

        if (!CollectionUtils.isEmpty(relationshipEntryDtoList)) {
            Optional<RelationshipEntryDto> first = relationshipEntryDtoList.stream().filter(a -> a.getEndId().equals(companyEntryId) && a.getTerminalProductId().equals(productEntryId)).findFirst();
            if (first.isPresent()) {
                RelationshipEntryDto relationshipEntryDto = first.get();
                graphDto.setCompanyEntryId(relationshipEntryDto.getEndId());
                graphDto.setCompanyName(relationshipEntryDto.getEndName());
                graphDto.setProductEntryId(relationshipEntryDto.getTerminalProductId());
                graphDto.setProductName(relationshipEntryDto.getTerminalProductName());
                graphDto.setParentGraphList(getGraphDtoList(companyEntryId, productEntryId, relationshipEntryDtoList));
            }
        }
        return graphDto;

    }

    /**
     * 查询协作图图谱
     *
     * @param companyEntryId
     * @param productEntryId
     * @param relationshipEntryDtoList
     * @return
     */
    private List<GraphDto> getGraphDtoList(String companyEntryId, String productEntryId, List<RelationshipEntryDto> relationshipEntryDtoList) {
        List<GraphDto> graphDtoList = new ArrayList<>();
        List<RelationshipEntryDto> collect = relationshipEntryDtoList.stream().filter(a -> a.getEndId().equals(companyEntryId) && a.getTerminalProductId().equals(productEntryId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return graphDtoList;
        }
        GraphDto graphDto;
        for (RelationshipEntryDto item : collect) {
            graphDto = new GraphDto();
            graphDto.setCompanyEntryId(item.getStartId());
            graphDto.setCompanyName(item.getStartName());
            graphDto.setProductEntryId(item.getSupplyProductId());
            graphDto.setProductName(item.getSupplyProductName());
            List<GraphDto> parentList = getGraphDtoList(item.getStartId(), item.getSupplyProductId(), relationshipEntryDtoList);
            graphDto.setParentGraphList(parentList);
            graphDtoList.add(graphDto);
        }
        return graphDtoList;
    }

    /**
     * 获取公司-公司供应关系
     *
     * @param relationshipEntryDto
     * @return
     * @throws Exception
     */
    private SupplyRelationship getSupplyRelationship(RelationshipEntryDto relationshipEntryDto) throws Exception {
        SupplyRelationship supplyRelationship = new SupplyRelationship();
        String startId = relationshipEntryDto.getStartId();
        String startName = relationshipEntryDto.getStartName();
        String endId = relationshipEntryDto.getEndId();
        String endName = relationshipEntryDto.getEndName();
        // 供给产品id
        String supplyProductId = relationshipEntryDto.getSupplyProductId();
        // 供给产品名称
        String supplyProductName = relationshipEntryDto.getSupplyProductName();
        // 终端产品id
        String terminalProductId = relationshipEntryDto.getTerminalProductId();
        // 终端产品名称
        String terminalProductName = relationshipEntryDto.getTerminalProductName();
        if (StringUtils.isEmpty(supplyProductId) || StringUtils.isEmpty(supplyProductName)
                || StringUtils.isEmpty(terminalProductId) || StringUtils.isEmpty(terminalProductName)) {
            throw new Exception("入参缺少必填参数！");
        }
        Optional<CompanyEntryNode> startById = companyEntryRepository.findById(startId);
        Optional<CompanyEntryNode> endById = companyEntryRepository.findById(endId);
        CompanyEntryNode startCompanyNode;
        CompanyEntryNode endCompanyNode;
        if (!startById.isPresent()) {
            CompanyEntryNode companyEntryNode1 = new CompanyEntryNode();
            companyEntryNode1.setCompanyEntryId(startId);
            companyEntryNode1.setName(startName);
            startCompanyNode = companyEntryRepository.save(companyEntryNode1);
        } else {
            startCompanyNode = startById.get();
        }
        if (!endById.isPresent()) {
            CompanyEntryNode companyEntryNode1 = new CompanyEntryNode();
            companyEntryNode1.setCompanyEntryId(endId);
            companyEntryNode1.setName(endName);
            endCompanyNode = companyEntryRepository.save(companyEntryNode1);
        } else {
            endCompanyNode = endById.get();
        }

        supplyRelationship.setUuid(UuidUtils.generate());
        // 创建 公司->公司 供给关系
        supplyRelationship.setStartNode(startCompanyNode);
        supplyRelationship.setEndNode(endCompanyNode);
        supplyRelationship.setSupplyProductId(supplyProductId);
        supplyRelationship.setTerminalProductId(terminalProductId);
        supplyRelationship.setSupplyProportion(relationshipEntryDto.getSupplyProportion());
        supplyRelationship.setPurchaseProportion(relationshipEntryDto.getPurchaseProportion());
        supplyRelationship.setStartTime(relationshipEntryDto.getStartTime());
        supplyRelationship.setEndTime(relationshipEntryDto.getEndTime());
        return supplyRelationship;
    }

    /**
     * 获取产品关系
     *
     * @param cooperationCondition
     * @return
     */
    private void saveProductionRelationship(CooperationCondition cooperationCondition) {
        CooperationDto cooperationDto = cooperationCondition.getCooperationDto();
        if (cooperationDto == null) {
            return;
        }
        String companyEntryId = cooperationDto.getCompanyEntryId();
        String productEntryId = cooperationDto.getProductEntryId();
        //查询是否存在生产关系
        ProductionRelationship productionRelationship1 = productionRelationshipRepository.findProductionRelationship(companyEntryId, productEntryId, RelationshipType.PRODUCTION);
        //不存在新建关联关系
        if (productionRelationship1 == null) {
            productionRelationship1 = new ProductionRelationship();
            productionRelationship1.setUuid(UuidUtils.generate());
            if (!companyEntryRepository.existsById(companyEntryId)) {
                CompanyEntryNode companyEntryNode = new CompanyEntryNode();
                companyEntryNode.setCompanyEntryId(companyEntryId);
                companyEntryNode.setName(cooperationDto.getCompanyName());
                CompanyEntryNode save = companyEntryRepository.save(companyEntryNode);
                productionRelationship1.setStartNode(save);
            }
            if (!productEntryRepository.existsById(productEntryId)) {
                ProductEntryNode productEntryNode = new ProductEntryNode();
                productEntryNode.setProductEntryId(productEntryId);
                productEntryNode.setName(cooperationDto.getProductName());
                ProductEntryNode save = productEntryRepository.save(productEntryNode);
                productionRelationship1.setEndNode(save);
            }
        }
        productionRelationship1.setAliasName(cooperationDto.getAliasName());
        productionRelationship1.setIntroduction(cooperationDto.getIntroduction());
        productionRelationship1.setRelationEntryName(cooperationDto.getCompanyName() + "生产" + cooperationDto.getProductName() + "生产协作图");
        productionRelationship1.setName(cooperationDto.getCompanyName() + "生产" + cooperationDto.getProductName() + "生产协作图");
        productionRelationship1.setImagePath(cooperationDto.getImagePath());
        productionRelationshipRepository.save(productionRelationship1);
    }
}
