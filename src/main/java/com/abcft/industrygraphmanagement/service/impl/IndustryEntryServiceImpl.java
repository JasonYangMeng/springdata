package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysCollectSettingMapper;
import com.abcft.industrygraphmanagement.dao.mysql.SysLikeMapper;
import com.abcft.industrygraphmanagement.dao.mysql.SysSubscribeSettingMapper;
import com.abcft.industrygraphmanagement.dao.neo.*;
import com.abcft.industrygraphmanagement.model.condition.IndustryEntryCondition;
import com.abcft.industrygraphmanagement.model.constant.RelationshipType;
import com.abcft.industrygraphmanagement.model.dto.*;
import com.abcft.industrygraphmanagement.model.node.*;
import com.abcft.industrygraphmanagement.service.IndustryEntryService;
import com.abcft.industrygraphmanagement.service.ProductService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Created by YangMeng on 2021/3/20 14:09
 */
@Service
@Slf4j
public class IndustryEntryServiceImpl implements IndustryEntryService {

    @Autowired
    private IndustryEntryRepository industryEntryRepository;
    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private ArtworkEntryRepository artworkEntryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductionRelationshipRepository productionRelationshipRepository;

    @Autowired
    private MaterialsRelationshipRepository materialsRelationshipRepository;

    @Autowired
    private ManufactureRelationShipRepository manufactureRelationShipRepository;

    @Autowired
    private TechnologyRelationShipRepository technologyRelationShipRepository;

    @Autowired
    private AscriptionRelationshipRepository ascriptionRelationshipRepository;

    @Autowired
    private IndustryRelationshipRepository industryRelationshipRepository;

    @Autowired
    private SysSubscribeSettingMapper sysSubscribeSettingMapper;

    @Autowired
    private SysCollectSettingMapper sysCollectSettingMapper;

    @Autowired
    private SysLikeMapper sysLikeMapper;

    /**
     * 添加产业链词条
     *
     * @param entryIndustryCondition
     * @return
     */
    @Override
    public boolean updateIndustryEntry(IndustryEntryCondition entryIndustryCondition) {
        IndustryEntryNode entryIndustryNode = entryIndustryCondition.getIndustryEntryNode();
        if (StringUtils.isEmpty(entryIndustryNode.getIndustryEntryId())) {
            return false;
        }
        //修改基本信息
        String dateStr = DateExtUtils.getCurrentDateStr();
        entryIndustryNode.setModifyTime(dateStr);
        if (StringUtils.isEmpty(entryIndustryNode.getCreateTime())) {
            entryIndustryNode.setCreateTime(dateStr);
        }
        //TODO 版本叠加
        entryIndustryNode.setVersion("0.2");
        IndustryEntryNode entryIndustryNode1 = industryEntryRepository.save(entryIndustryNode);

        List<ProductToProductDto> productToProductDtoList = entryIndustryCondition.getProductToProductDtoList();
        //删除产业链下的关系
        industryEntryRepository.deleteIndustryRelation(entryIndustryNode.getIndustryEntryId());
        //创建产业链对应产品的产业链关系
        updateProductEntry(entryIndustryNode1);
        if (!CollectionUtils.isEmpty(productToProductDtoList)) {
            //添加关系
            createProductRelationship(productToProductDtoList);
        }
        //修改产业链创建状态
        productService.updateIndustryStatusById(entryIndustryNode.getIndustryEntryId());

        return true;
    }

    /**
     * 获取产业链图谱数据
     *
     * @param productEntryId
     * @return
     */
    @Override
    public List<IndustryToCompanyDto> getIndustryGraphList(String productEntryId, boolean self, int level) throws Exception {
        if (StringUtils.isEmpty(productEntryId)) {
            throw new Exception("参数异常");
        }
        if (level > 10 || level < 1) {
            level = 6;
        }
        List<IndustryToCompanyDto> industryToCompanyDtoList = new ArrayList<>();
        //查找所有产业链
        List<String> industryEntryIds = new ArrayList<>();
        if (self) {
            industryEntryIds.add(productEntryId);
        } else {
            industryEntryIds = industryEntryRepository.getIndustryEntryIds(productEntryId);
        }
        setIndustryGraph(industryEntryIds, industryToCompanyDtoList, true, level);

        //返回结果
        return industryToCompanyDtoList;
    }

    /**
     * 查询产业链id和名称
     *
     * @param name
     * @return
     */
    @Override
    public List<IdAndNameDto> getIdAndName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new ArrayList<>();
        }
        return industryEntryRepository.getIdAndNameList(name);
    }

    /**
     * 获取产业链词条信息
     *
     * @param industryEntryId
     * @return
     */
    @Override
    public IndustryEntryCondition getIndustryEntryList(String industryEntryId) {
        IndustryEntryCondition industryEntryDto = new IndustryEntryCondition();
        Optional<IndustryEntryNode> byId = industryEntryRepository.findById(industryEntryId);
        if (byId.isPresent()) {
            IndustryEntryNode entryNode = byId.get();
            industryEntryDto.setIndustryEntryNode(entryNode);
        } else {
            return industryEntryDto;
        }
        setSelfInfo(industryEntryDto);
        //获取
        List<ProductToProductDto> productToProductDtoList = new ArrayList<>();
        getProductToProductList(Arrays.asList(industryEntryId), productToProductDtoList);
        industryEntryDto.setProductToProductDtoList(productToProductDtoList);
        return industryEntryDto;
    }

    /**
     * 设置收藏，订阅，点赞等信息
     *
     * @param industryEntryDto
     */
    private void setSelfInfo(IndustryEntryCondition industryEntryDto) {
        String entryId = industryEntryDto.getIndustryEntryNode().getIndustryEntryId();
        String userId = UserContext.getCurrentUserId();
        int i = sysLikeMapper.totalByEntryId(entryId);
        industryEntryDto.setLikeNum(i);
        if (StringUtils.isEmpty(userId)) {
            industryEntryDto.setSubscribe(false);
            industryEntryDto.setCollect(false);
            industryEntryDto.setLike(false);
        } else {
            industryEntryDto.setSubscribe(sysSubscribeSettingMapper.countSubscribe(userId, entryId) > 0);
            industryEntryDto.setCollect(sysCollectSettingMapper.countCollect(userId, entryId) > 0);
            industryEntryDto.setLike(sysLikeMapper.countCollect(userId, entryId) > 0);
        }
    }


    /**
     * 获取产业链的所有数据
     *
     * @param productEntryIds
     * @param industryToCompanyDtoList
     */
    @Override
    public void setIndustryGraph(List<String> productEntryIds, List<IndustryToCompanyDto> industryToCompanyDtoList, boolean containsCompany, int level) {
        IndustryToCompanyDto newIndustryToCompanyDto;
        if (CollectionUtils.isEmpty(productEntryIds)) {
            return;
        }
        //根据终端产品id获取产业链信息
        for (String id : productEntryIds) {
            Optional<IndustryEntryNode> byId = industryEntryRepository.findById(id);
            newIndustryToCompanyDto = getIndustryGraph(id, containsCompany, level);
            if (byId.isPresent()) {
                newIndustryToCompanyDto.setIndustryName(byId.get().getName());
                newIndustryToCompanyDto.setIndustryId(byId.get().getIndustryEntryId());
            } else {
                Optional<ArtworkEntryNode> byId1 = artworkEntryRepository.findById(id);
                if (byId1.isPresent()) {
                    newIndustryToCompanyDto.setIndustryName(byId1.get().getName());
                    newIndustryToCompanyDto.setIndustryId(byId1.get().getArtworkEntryId());
                }
            }
            industryToCompanyDtoList.add(newIndustryToCompanyDto);
        }
    }

    /**
     * 根据id获取产业链图谱
     *
     * @param productEntryId
     * @param containsCompany
     * @param level
     * @return
     */
    private IndustryToCompanyDto getIndustryGraph(String productEntryId, boolean containsCompany, int level) {
        IndustryToCompanyDto newIndustryToCompanyDto = new IndustryToCompanyDto();
        List<MaterialsRelationship> productEdgesList = new ArrayList<>();
        List<MaterialsRelationship> productEdgesList1 = materialsRelationshipRepository.getProductEdgesList1(productEntryId, level);
        List<ManufactureRelationship> productEdgesList2 = materialsRelationshipRepository.getProductEdgesList2(productEntryId, level);
        List<TechnologyRelationship> productEdgesList3 = materialsRelationshipRepository.getProductEdgesList3(productEntryId, level);
        productEdgesList.addAll(productEdgesList1);
        MaterialsRelationship materialsRelationship;
        for (ManufactureRelationship item : productEdgesList2) {
            materialsRelationship = new MaterialsRelationship();
            materialsRelationship.setEndNode(item.getEndNode());
            materialsRelationship.setStartNode(item.getStartNode());
            productEdgesList.add(materialsRelationship);
        }
        for (TechnologyRelationship item : productEdgesList3) {
            materialsRelationship = new MaterialsRelationship();
            materialsRelationship.setEndNode(item.getEndNode());
            materialsRelationship.setStartNode(item.getStartNode());
            productEdgesList.add(materialsRelationship);
        }
        List<EdgesDto> edgesDtos = new ArrayList<>();
        List<NodesDto> nodesDtos = new ArrayList<>();
        EdgesDto edgesDto;
        NodesDto nodesDto1;
        NodesDto nodesDto2;
        for (MaterialsRelationship item : productEdgesList) {
            edgesDto = new EdgesDto();
            nodesDto1 = new NodesDto();
            nodesDto2 = new NodesDto();
            ProductEntryNode startNode = item.getStartNode();
            ProductEntryNode endNode = item.getEndNode();
            nodesDto1.setId(startNode.getProductEntryId());
            nodesDto1.setLabel(startNode.getName());
            nodesDto2.setId(endNode.getProductEntryId());
            nodesDto2.setLabel(endNode.getName());
            if (containsCompany) {
                List<DicDto> companyByProductId = productionRelationshipRepository.getCompanyByProductId(startNode.getProductEntryId());
                nodesDto1.setCompanyList(companyByProductId);
                List<DicDto> companyByProductId2 = productionRelationshipRepository.getCompanyByProductId(endNode.getProductEntryId());
                nodesDto2.setCompanyList(companyByProductId2);
            }
            edgesDto.setSource(endNode.getProductEntryId());
            edgesDto.setTarget(startNode.getProductEntryId());
            if (!edgesDtos.contains(edgesDto)) {
                edgesDtos.add(edgesDto);
            }
            if (!nodesDtos.contains(nodesDto1)) {
                nodesDtos.add(nodesDto1);
            }
            if (!nodesDtos.contains(nodesDto2)) {
                nodesDtos.add(nodesDto2);
            }
        }
        newIndustryToCompanyDto.setEdges(edgesDtos);
        newIndustryToCompanyDto.setNodes(nodesDtos);
        return newIndustryToCompanyDto;
    }

    /**
     * @param productToProductDtoList
     */
    @Override
    public void createProductRelationship(List<ProductToProductDto> productToProductDtoList) {
        List<MaterialsRelationship> materialsRelationshipList = new ArrayList<>();
        List<ManufactureRelationship> manufactureRelationshipList = new ArrayList<>();
        List<TechnologyRelationship> technologyRelationshipList = new ArrayList<>();
        List<AscriptionRelationship> ascriptionRelationshipList = new ArrayList<>();
        for (ProductToProductDto item : productToProductDtoList) {
            ProductEntryNode startNode = getProductEntryNode(item.getStartId(), item.getStartName());
            ProductEntryNode endNode = getProductEntryNode(item.getEndId(), item.getEndName());
            switch (item.getRelationType()) {
                case RelationshipType.MATERIALS:
                    MaterialsRelationship materialsRelationship = new MaterialsRelationship();
                    materialsRelationship.setStartNode(startNode);
                    materialsRelationship.setEndNode(endNode);
                    BeanUtils.copyProperties(item, materialsRelationship);
                    materialsRelationship.setUuid(UuidUtils.generate());
                    materialsRelationshipList.add(materialsRelationship);
                    break;
                case RelationshipType.MANUFACTURE:
                    ManufactureRelationship manufactureRelationShip = new ManufactureRelationship();
                    manufactureRelationShip.setStartNode(startNode);
                    manufactureRelationShip.setEndNode(endNode);
                    BeanUtils.copyProperties(item, manufactureRelationShip);
                    manufactureRelationShip.setUuid(UuidUtils.generate());
                    manufactureRelationshipList.add(manufactureRelationShip);
                    break;
                case RelationshipType.TECHNOLOGY:
                    TechnologyRelationship technologyRelationShip = new TechnologyRelationship();
                    technologyRelationShip.setStartNode(startNode);
                    technologyRelationShip.setEndNode(endNode);
                    BeanUtils.copyProperties(item, technologyRelationShip);
                    technologyRelationShip.setUuid(UuidUtils.generate());
                    technologyRelationshipList.add(technologyRelationShip);
                    break;
                case RelationshipType.ASCRIPTION:
                    AscriptionRelationship ascriptionRelationship = new AscriptionRelationship();
                    ascriptionRelationship.setStartNode(startNode);
                    ascriptionRelationship.setEndNode(endNode);
                    BeanUtils.copyProperties(item, ascriptionRelationship);
                    ascriptionRelationship.setUuid(UuidUtils.generate());
                    ascriptionRelationshipList.add(ascriptionRelationship);
                    break;
                default:
                    return;
            }
            if (!CollectionUtils.isEmpty(materialsRelationshipList)) {
                materialsRelationshipRepository.saveAll(materialsRelationshipList);
            }
            if (!CollectionUtils.isEmpty(manufactureRelationshipList)) {
                manufactureRelationShipRepository.saveAll(manufactureRelationshipList);
            }
            if (!CollectionUtils.isEmpty(technologyRelationshipList)) {
                technologyRelationShipRepository.saveAll(technologyRelationshipList);
            }
            if (!CollectionUtils.isEmpty(ascriptionRelationshipList)) {
                ascriptionRelationshipRepository.saveAll(ascriptionRelationshipList);
            }
        }
    }

    /**
     * 获取产品词条
     *
     * @param productEntryId
     * @param name
     * @return
     */
    private ProductEntryNode getProductEntryNode(String productEntryId, String name) {
        if (productEntryRepository.existsById(productEntryId)) {
            return productEntryRepository.findById(productEntryId).get();
        } else {
            ProductEntryNode productEntryNode = new ProductEntryNode();
            productEntryNode.setName(name);
            productEntryNode.setProductEntryId(productEntryId);
            return productEntryRepository.save(productEntryNode);
        }
    }

    /**
     * 获取产品种类-产品种类下的关系
     *
     * @param productEntryIds
     * @param productToProductDtoList
     */
    @Override
    public void getProductToProductList(List<String> productEntryIds, List<ProductToProductDto> productToProductDtoList) {
        if (CollectionUtils.isEmpty(productEntryIds)) {
            return;
        }
        List<ProductToProductDto> items = materialsRelationshipRepository.getProductToProductList(productEntryIds);
        List<ProductToProductDto> curr = new ArrayList<>();
        for (ProductToProductDto item : items) {
            if (productToProductDtoList.contains(item)) {
                continue;
            }
            curr.add(item);
            productToProductDtoList.add(item);
        }
        List<String> ids = curr.stream().map(a -> a.getStartId()).collect(Collectors.toList());
        getProductToProductList(ids, productToProductDtoList);
    }


    /**
     * 创建产业链对应产品的产业链关系
     */
    private void updateProductEntry(IndustryEntryNode industryEntryNode) {
        if (industryRelationshipRepository.existsRelationship(industryEntryNode.getIndustryEntryId())) {
            return;
        }
        String entryIndustryId = industryEntryNode.getIndustryEntryId();
        ProductEntryNode save;
        if (!productEntryRepository.existsById(entryIndustryId)) {
            ProductEntryNode productEntryNode = new ProductEntryNode();
            productEntryNode.setProductEntryId(entryIndustryId);
            productEntryNode.setName(industryEntryNode.getName().replaceAll("产业链图谱", ""));
            save = productEntryRepository.save(productEntryNode);
        } else {
            Optional<ProductEntryNode> byId = productEntryRepository.findById(entryIndustryId);
            save = byId.get();
        }
        IndustryRelationship industryRelationship = new IndustryRelationship();
        industryRelationship.setStartNode(save);
        industryRelationship.setEndNode(industryEntryNode);
        industryRelationship.setUuid(UuidUtils.generate());
        industryRelationshipRepository.save(industryRelationship);
    }
}
