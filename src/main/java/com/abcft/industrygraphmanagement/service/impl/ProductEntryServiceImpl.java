package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysCollectSettingMapper;
import com.abcft.industrygraphmanagement.dao.mysql.SysLikeMapper;
import com.abcft.industrygraphmanagement.dao.mysql.SysSubscribeSettingMapper;
import com.abcft.industrygraphmanagement.dao.neo.*;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.constant.RelationshipType;
import com.abcft.industrygraphmanagement.model.dto.*;
import com.abcft.industrygraphmanagement.model.entity.*;
import com.abcft.industrygraphmanagement.model.node.AscriptionRelationship;
import com.abcft.industrygraphmanagement.model.node.CompanyEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductionRelationship;
import com.abcft.industrygraphmanagement.service.*;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangZhiZhou
 * @date 2021/3/11
 */
@Service
@Slf4j
public class ProductEntryServiceImpl implements ProductEntryService {

    private static final String PRODUCT = "产品";
    /**
     * 1：已创建词条
     */
    private static final String CREATED_ENTRY = "1";

    /**
     * 0:待审核
     */
    private static final String AUDIT_STATUS = "0";
    /**
     * 1:产品种类
     */
    private static final String PRODUCT_CATEGORY = "1";
    /**
     * 2:产品类型
     */
    private static final String PRODUCT_TYPE = "2";
    /**
     * 3:产品单元
     */
    private static final String PRODUCT_UNIT = "3";

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private AscriptionRelationshipRepository ascriptionRepository;

    @Autowired
    private TechnologyRelationShipRepository technologyRepository;

    @Autowired
    private MaterialsRelationshipRepository materialsRepository;

    @Autowired
    private ManufactureRelationShipRepository manufactureRepository;

    @Autowired
    private ProductionRelationshipRepository productionRepository;

    @Autowired
    private CompanyEntryRepository companyEntryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private EntryInfoSourceService entryInfoSourceService;

    @Autowired
    private EntryLinkService entryLinkService;

    @Autowired
    private SysSubscribeSettingMapper sysSubscribeSettingMapper;

    @Autowired
    private SysCollectSettingMapper sysCollectSettingMapper;

    @Autowired
    private SysLikeMapper sysLikeMapper;


    /**
     * 根据产品词条名称 精确查询产品词条节点
     *
     * @param name 产品词条名称
     * @return List<ProductEntryNode>
     */
    @Override
    public List<ProductEntryNode> queryProductEntryNode(String name) {
        return productEntryRepository.queryProductEntryNode(name);
    }

    /**
     * 根据产品词条名称 精确查询产品词条节点
     *
     * @param name 产品词条名称
     * @return ProductEntryNode
     */
    @Override
    public ProductEntryNode queryProductEntry(String name) {
        return productEntryRepository.queryProductEntry(name);
    }

    @Override
    public List<AscriptionRelationship> queryAscriptionRelationship(String productEntryId, String type) {
        return ascriptionRepository.queryAllAscriptionRelationship(productEntryId, type);
    }

    /**
     * 查询产品族谱信息
     *
     * @param productEntryId 当前产品词条id
     * @return ProductGenealogyEntity
     */
    @Override
    public ProductGenealogyEntity queryProductGenealogy(String productEntryId) throws Exception {
        Optional<ProductEntryNode> byId = productEntryRepository.findById(productEntryId);
        if (!byId.isPresent()) {
            log.error("入参产品词条id为：{}，在Noe4j中不存在！", productEntryId);
            throw new Exception("业务异常！");
        }
        ProductEntryNode productEntryNode = byId.get();
        if (productEntryNode == null) {
            log.error("入参productEntryId：{}, 没有查询到该产品词条节点！", productEntryId);
            throw new Exception("没有查询到该产品词条节点！");
        }
        ProductEntryNode startEntryNode = productEntryNode;
        while (true) {
            ProductEntryNode node = ascriptionRepository.
                    findCategoryNodeByTypeProductEntryId(startEntryNode.getProductEntryId());
            if (node != null) {
                startEntryNode = node;
            }
            if (node == null) {
                break;
            }
        }
        // categoryProductEntryId 产品种类词条id
        String categoryProductEntryId = startEntryNode.getProductEntryId();
        // categoryProductEntryName 产品种类词条名称
        String categoryProductEntryName = startEntryNode.getName();

        return this.getProductGenealogy(categoryProductEntryId, categoryProductEntryName);
    }

    /**
     * 获取产品族谱信息
     *
     * @param categoryProductEntryId   产品种类词条id
     * @param categoryProductEntryName 产品种类词条名称
     * @return ProductGenealogyEntity 产品族谱信息
     */
    private ProductGenealogyEntity getProductGenealogy(String categoryProductEntryId, String categoryProductEntryName) {
        ProductGenealogyEntity productGenealogy = new ProductGenealogyEntity();
        // 产品种类
        productGenealogy.setProductEntryId(categoryProductEntryId);
        productGenealogy.setProductEntryName(categoryProductEntryName);
        // 产品类型
        List<ProductGenealogyEntity> typeNodeList = ascriptionRepository.findNodeListByProductEntryId(categoryProductEntryId);
        List<ProductGenealogyEntity> list = new ArrayList<>(typeNodeList.size());
        // 封装产品族谱树形结构
        for (ProductGenealogyEntity node : typeNodeList) {
            String typeProductEntryId = node.getProductEntryId();
            String typeProductEntryName = node.getProductEntryName();
            // 产品单元
            List<ProductGenealogyEntity> unitNodeList = ascriptionRepository.findNodeListByProductEntryId(typeProductEntryId);
            ProductGenealogyEntity genealogy = new ProductGenealogyEntity();
            genealogy.setProductEntryId(typeProductEntryId);
            genealogy.setProductEntryName(typeProductEntryName);
            genealogy.setList(unitNodeList);
            list.add(genealogy);
        }
        productGenealogy.setList(list);
        return productGenealogy;
    }

    /**
     * 产品词条业务删除关系功能接口
     *
     * @param productEntryId 词条入参对象 ProductEntryParamDto
     */
    @Transactional(transactionManager = "neo4jTransactionManager")
    @Override
    public void deleteProductEntry(String productEntryId) throws Exception {
        if (StringUtils.isEmpty(productEntryId)) {
            log.error("deleteProductEntry, 入参缺少必填参数");
            throw new Exception("入参缺少必填参数！");
        }
        if (!productEntryRepository.existsById(productEntryId)) {
            return;
        }
        // 删除该节点归属关系
        ascriptionRepository.deleteStartProductRelation(productEntryId, RelationshipType.ASCRIPTION);
        ascriptionRepository.deleteEndProductRelation(productEntryId, RelationshipType.ASCRIPTION);
        // 删除该节点原材料关系
        materialsRepository.deleteStartProductRelation(productEntryId, RelationshipType.MATERIALS);
        materialsRepository.deleteEndProductRelation(productEntryId, RelationshipType.MATERIALS);
        // 删除该节点制造设备关系
        manufactureRepository.deleteStartProductRelation(productEntryId, RelationshipType.MANUFACTURE);
        manufactureRepository.deleteEndProductRelation(productEntryId, RelationshipType.MANUFACTURE);
        // 删除该节点加工工艺关系
        technologyRepository.deleteStartProductRelation(productEntryId, RelationshipType.TECHNOLOGY);
        technologyRepository.deleteEndProductRelation(productEntryId, RelationshipType.TECHNOLOGY);
        // 删除该节点 公司-产品 生产关系
        productionRepository.deleteCompanyProductRelation(productEntryId, RelationshipType.PRODUCTION);
    }

    /**
     * 产品词条业务编辑功能接口
     *
     * @param paramDto 添加产品词条、编辑产品词条入参对象 ProductEntryParamDto
     */
    @Override
    public String addOrUpdateProductEntry(ProductEntryParamDto paramDto) throws Exception {
        ProductEntryNode currentNode = paramDto.getCurrentNode();
        String currentUserId = UserContext.getCurrentUserId();
        String dateStr = DateExtUtils.getCurrentDateStr();
        String productEntryId = currentNode.getProductEntryId();
        String type = currentNode.getType();
        String name = currentNode.getName();
        if (StringUtils.isEmpty(name)) {
            throw new Exception("产品名称不能为空！");
        }
        // 入参 productEntryId 为空， 新建产品
        if (StringUtils.isEmpty(productEntryId)) {
            productEntryId = UuidUtils.generate(NameType.PRODUCT);
            // 在mysql中创建该产品，审核状态为待审核，词条状态为未创建0
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductId(productEntryId);
            productEntity.setProductName(name);
            productEntity.setType(type);
            productEntity.setProductTreeStatus("0");
            productEntity.setIndustryStatus("0");
            productEntity.setArtworkStatus("0");
            productEntity.setCreateEntry("0");
            productEntity.setAuditStatus(AUDIT_STATUS);
            productEntity.setCreateUserId(currentUserId);
            productEntity.setCreateTime(dateStr);
            productService.addProduct(productEntity);
            // 设置当前产品节点
            currentNode.setProductEntryId(productEntryId);
            currentNode.setCreateUserId(currentUserId);
            currentNode.setCreateTime(dateStr);
            paramDto.setCurrentNode(currentNode);
        }
        // 修改mysql原有产品的产品名称
        if (!StringUtils.isEmpty(productEntryId)) {
            productService.updateProductName(productEntryId, name);
        }
        // ProductToProductDto列表
        String finalProductEntryId = productEntryId;
        List<ProductToProductDto> productToProductDtoList = paramDto.getProductToProductDtoList();
        if (!CollectionUtils.isEmpty(productToProductDtoList)) {
            List<ProductToProductDto> collect = productToProductDtoList.stream().map(c -> {
                if (StringUtils.isEmpty(c.getStartId())) {
                    c.setStartId(finalProductEntryId);
                }
                if (StringUtils.isEmpty(c.getEndId())) {
                    c.setEndId(finalProductEntryId);
                }
                return c;
            }).collect(Collectors.toList());

            paramDto.setProductToProductDtoList(collect);
        }

        // 调用删除关系接口
        this.deleteProductEntry(productEntryId);
        // 调用产品词条业务添加功能接口
        this.addProductEntry(paramDto);

        return productEntryId;
    }

    /**
     * 产品词条业务添加功能接口
     *
     * @param paramDto 添加产品词条、编辑产品词条入参对象 ProductEntryParamDto
     */
    @Transactional(transactionManager = "neo4jTransactionManager")
    @Override
    public void addProductEntry(ProductEntryParamDto paramDto) throws Exception {
        ProductEntryNode currentNode = paramDto.getCurrentNode();
        String dateStr = DateExtUtils.getCurrentDateStr();
        currentNode.setCreateTime(dateStr);
        currentNode.setModifyTime(dateStr);
        String productEntryId = currentNode.getProductEntryId();
        String type = currentNode.getType();
        String name = currentNode.getName();
        if (StringUtils.isEmpty(productEntryId) || StringUtils.isEmpty(type)
                || StringUtils.isEmpty(name)) {
            log.error("addProductEntry -> param[ productEntryId:{}, type:{}, name:{} ] " +
                    "入参缺少必填参数！", productEntryId, type, name);
            throw new Exception("addProductEntry 入参缺少必填参数！");
        }

        // 在Noe4j中保存当前产品词条 currentNode
        productEntryRepository.save(currentNode);
        // 在mysql中更新该产品
        productService.updateProductById(productEntryId, CREATED_ENTRY, type);
        // 入参产品词条信息来源
        List<EntryInfoSourceEntity> entryInfoSourceList = paramDto.getEntryInfoSourceEntityList();
        // 如果入参词条信息来源不为空，保存
        if (!CollectionUtils.isEmpty(entryInfoSourceList)) {
            for (EntryInfoSourceEntity entryInfoSourceEntity : entryInfoSourceList) {
                String entryInfoSourceId = entryInfoSourceEntity.getEntryInfoSourceId();
                entryInfoSourceEntity.setType(PRODUCT);
                entryInfoSourceEntity.setEntryId(productEntryId);
                if (StringUtils.isEmpty(entryInfoSourceId)) {
                    // 不存在，新建词条来源
                    entryInfoSourceEntity.setEntryInfoSourceId(UuidUtils.generate());
                    entryInfoSourceEntity.setCreateTime(dateStr);
                    entryInfoSourceService.addEntryInfoSource1(entryInfoSourceEntity);
                    continue;
                }
                // 存在，保存词条来源
                entryInfoSourceService.updateEntryInfoSource1(entryInfoSourceEntity);
            }
        }
        // 入参产品词条链接
        List<EntryLinkEntity> entryLinkList = paramDto.getEntryLinkEntityList();
        // 如果入参产品词条链接不为空，保存
        if (!CollectionUtils.isEmpty(entryLinkList)) {
            for (EntryLinkEntity entryLinkEntity : entryLinkList) {
                String entryLinkId = entryLinkEntity.getId();
                entryLinkEntity.setEntryId(productEntryId);
                entryLinkEntity.setType(PRODUCT);
                if (StringUtils.isEmpty(entryLinkId)) {
                    entryLinkEntity.setId(UuidUtils.generate());
                    entryLinkEntity.setCreateTime(dateStr);
                    // 不存在，新建添加链接来源
                    entryLinkService.addEntryLink(entryLinkEntity);
                    continue;
                }
                entryLinkService.updateEntryLink(entryLinkEntity);
            }
        }

        // ProductToProductDto列表
        List<ProductToProductDto> productToProductDtoList = paramDto.getProductToProductDtoList();
        if (!CollectionUtils.isEmpty(productToProductDtoList)) {
            // 遍历productToProductDtoList，进行保存并且建立关系操作
            for (ProductToProductDto dto : productToProductDtoList) {
                // 开始节点id
                String startNodeId = dto.getStartId();
                // 开始节点名称
                String starNodeName = dto.getStartName();
                // 结束节点id
                String endNodeId = dto.getEndId();
                // 结束节点名称
                String endNodeName = dto.getEndName();
                // 关系类型  生产关系，供给关系，归属关系，......(必传)
                String relationType = dto.getRelationType();
                if (!productEntryId.equals(startNodeId) && !productEntryRepository.existsById(startNodeId)
                        && !RelationshipType.PRODUCTION.equals(relationType)) {
                    // startNodeId不存在，在Noe4j中创建该产品节点
                    ProductEntryNode productEntryNode = new ProductEntryNode();
                    productEntryNode.setName(starNodeName);
                    productEntryNode.setProductEntryId(startNodeId);
                    productEntryRepository.save(productEntryNode);
                }
                if (!productEntryId.equals(endNodeId) && !productEntryRepository.existsById(endNodeId)
                        && !RelationshipType.PRODUCTION.equals(relationType)) {
                    // endNodeId不存在，在Noe4j中创建该产品节点
                    ProductEntryNode productEntryNode = new ProductEntryNode();
                    productEntryNode.setProductEntryId(endNodeId);
                    productEntryNode.setName(endNodeName);
                    productEntryRepository.save(productEntryNode);
                }
                // relationType的值是Production，说明ProductToProductDto是生产关系
                if (RelationshipType.PRODUCTION.equals(relationType)) {
                    if (!productEntryId.equals(startNodeId) && !companyEntryRepository.existsById(startNodeId)) {
                        // startNodeId不存在，创建该公司节点
                        CompanyEntryNode companyEntryNode = new CompanyEntryNode();
                        companyEntryNode.setCompanyEntryId(startNodeId);
                        companyEntryNode.setName(starNodeName);
                        companyEntryRepository.save(companyEntryNode);
                    }
                    if (!productEntryId.equals(endNodeId) && !companyEntryRepository.existsById(endNodeId)) {
                        // endNodeId不存在，创建该公司节点
                        CompanyEntryNode companyEntryNode = new CompanyEntryNode();
                        companyEntryNode.setCompanyEntryId(startNodeId);
                        companyEntryNode.setName(starNodeName);
                        companyEntryRepository.save(companyEntryNode);
                    }
                }
                // 根据 relationType 创建关系
                switch (relationType) {
                    case RelationshipType.ASCRIPTION:
                        // 产品种类-产品类型，产品类型-产品单元 归属关系
                        ProductEntryNode node = ascriptionRepository.
                                findCategoryNodeByTypeProductEntryId(endNodeId);
                        if (node != null) {
                            // endNodeId 存在上级归属节点，删除与上一归属节点的归属关系(产品和产品之间归属关系只能有一个)
                            ascriptionRepository.deleteEndProductRelation(endNodeId, RelationshipType.ASCRIPTION);
                        }
                        ascriptionRepository.createAscription(startNodeId, endNodeId, UuidUtils.generate(NameType.RELATION));
                        break;
                    case RelationshipType.MATERIALS:
                        // 产品种类-产品种类 原材料关系
                        String costProportion = dto.getCostProportion();
                        String startTime = dto.getStartTime();
                        String endTime = dto.getEndTime();
                        materialsRepository.createMaterials(UuidUtils.generate(NameType.RELATION), startNodeId, endNodeId, costProportion, startTime, endTime);
                        break;
                    case RelationshipType.MANUFACTURE:
                        // 产品种类-产品种类 制造设备关系
                        manufactureRepository.createManufacture(UuidUtils.generate(NameType.RELATION), startNodeId, endNodeId);
                        break;
                    case RelationshipType.TECHNOLOGY:
                        // 产品种类-产品种类 加工工艺关系
                        technologyRepository.createTechnology(UuidUtils.generate(NameType.RELATION), startNodeId, endNodeId);
                        break;
                    case RelationshipType.PRODUCTION:
                        // 公司-产品 生产关系
                        CompanyEntryNode startNode = companyEntryRepository.findById(startNodeId).get();
                        ProductEntryNode endNode = productEntryRepository.findById(endNodeId).get();
                        ProductionRelationship productionRelationship = new ProductionRelationship();
                        productionRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
                        productionRelationship.setStartNode(startNode);
                        productionRelationship.setEndNode(endNode);
                        productionRelationship.setIncomeProportion(dto.getIncomeProportion());
                        productionRelationship.setProductTotalIncome(dto.getProductTotalIncome());
                        productionRelationship.setRevenueGrowth(dto.getRevenueGrowth());
                        productionRelationship.setProductPrice(dto.getProductPrice());
                        productionRelationship.setProductGross(dto.getProductGross());
                        productionRelationship.setCapacity(dto.getCapacity());
                        productionRelationship.setIndustryCapacity(dto.getIndustryCapacity());
                        productionRelationship.setCapacityRatio(dto.getCapacityRatio());
                        productionRelationship.setCapacityProportion(dto.getCapacityProportion());
                        productionRepository.save(productionRelationship);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    /**
     * 创建产品词条的数据回显接口
     * 点击创建产品词条按钮后调用该接口
     *
     * @param productEntryId 创建产品词条的id
     * @return WebResInfo
     */
    @Override
    public ProductEntryResultDto queryProductEntryByTypeAndName(String productEntryId)
            throws Exception {
        ProductEntryResultDto productEntryResultDto = new ProductEntryResultDto();
        Optional<ProductEntryNode> byId = productEntryRepository.findById(productEntryId);
        // 如果Noe4j不存在该产品词条返回 空productEntryParamDto
        if (!byId.isPresent()) {
            return productEntryResultDto;
        }
        ProductEntryNode currentNode = byId.get();


        // 当前产品词条的信息来源对象列表
        List<EntryInfoSourceEntity> entryInfoSourceList = entryInfoSourceService.queryAllByEntryId(productEntryId);
        // 当前产品词条的词条链接对象列表
        List<EntryLinkEntity> entryLinkList = entryLinkService.queryEntryLinkList(productEntryId);
        // 产品种类节点关系对象列表
        List<NodeRelationDto> proCategoryRelList;
        // 产品类型节点关系对象列表
        List<NodeRelationDto> typeRelList;
        // 产品单元节点关系对象列表
        List<NodeRelationDto> proUnitRelList;
        // 上游产品节点关系对象列表
        List<NodeRelationDto> upStreamRelList = new ArrayList<>();
        // 下游产品节点关系对象列表
        List<NodeRelationDto> downStreamRelList = new ArrayList<>();
        // 生产商节点关系对象列表
        List<NodeRelationDto> companyRelList;
        // 产品族谱
        ProductGenealogyEntity productGenealogy = this.queryProductGenealogy(currentNode.getProductEntryId());

        String type = currentNode.getType();

        // 判断入参产品区分类型
        // type 1:产品种类
        if (PRODUCT_CATEGORY.equals(type)) {
            // 该产品词条节点的所有产品类型节点
            typeRelList = ascriptionRepository.findNodeByEntryId(productEntryId, RelationshipType.ASCRIPTION);
            // 该产品词条节点的所有上游产品节点(原材料、生产设备、生产工艺)
            // 上游 原材料
            upStreamRelList.addAll(materialsRepository.findUpStreamUnitNodeByEntryId(productEntryId, RelationshipType.MATERIALS));
            // 上游 生产设备
            upStreamRelList.addAll(materialsRepository.findUpStreamUnitNodeByEntryId(productEntryId, RelationshipType.MANUFACTURE));
            // 上游 生产工艺
            upStreamRelList.addAll(materialsRepository.findUpStreamUnitNodeByEntryId(productEntryId, RelationshipType.TECHNOLOGY));
            // 下游产品节点关系对象列表
            // 下游 原材料
            downStreamRelList.addAll(materialsRepository.findDownStreamUnitNodeByEntryId(productEntryId, RelationshipType.MATERIALS));
            // 下游 生产设备
            downStreamRelList.addAll(materialsRepository.findDownStreamUnitNodeByEntryId(productEntryId, RelationshipType.MANUFACTURE));
            // 下游 生产工艺
            downStreamRelList.addAll(materialsRepository.findDownStreamUnitNodeByEntryId(productEntryId, RelationshipType.TECHNOLOGY));
            // 生产商节点关系对象列表
            companyRelList = productionRepository.findCompanyNodeByEntryId(productEntryId, RelationshipType.PRODUCTION);

            // 产品族谱
            productEntryResultDto.setProGenealogy(productGenealogy);
            productEntryResultDto.setEntryInfoSourceEntityList(entryInfoSourceList);
            productEntryResultDto.setEntryLinkEntityList(entryLinkList);
            productEntryResultDto.setProTypeRelList(typeRelList);
            productEntryResultDto.setProUpStreamRelList(upStreamRelList);
            productEntryResultDto.setProDownStreamRelList(downStreamRelList);
            productEntryResultDto.setProCompanyRelList(companyRelList);
        }
        // type 2:产品类型
        if (PRODUCT_TYPE.equals(type)) {
            // 产品种类节点关系对象列表
            proCategoryRelList = productEntryRepository.findCategoryNodeByTypeEntryId(productEntryId, RelationshipType.ASCRIPTION);
            // 产品单元节点关系对象列表
            proUnitRelList = ascriptionRepository.findNodeByEntryId(productEntryId, RelationshipType.ASCRIPTION);
            productEntryResultDto.setEntryInfoSourceEntityList(entryInfoSourceList);
            productEntryResultDto.setEntryLinkEntityList(entryLinkList);
            // 产品族谱
            productEntryResultDto.setProGenealogy(productGenealogy);
            productEntryResultDto.setProCategoryRelList(proCategoryRelList);
            productEntryResultDto.setProUnitRelList(proUnitRelList);
        }
        // type 3:产品单元
        if (PRODUCT_UNIT.equals(type)) {
            // 下游产品节点关系对象列表
            // 下游 原材料
            downStreamRelList.addAll(materialsRepository.findDownStreamUnitNodeByEntryId(productEntryId, RelationshipType.MATERIALS));
            // 下游 生产设备
            downStreamRelList.addAll(materialsRepository.findDownStreamUnitNodeByEntryId(productEntryId, RelationshipType.MANUFACTURE));
            // 下游 生产工艺
            downStreamRelList.addAll(materialsRepository.findDownStreamUnitNodeByEntryId(productEntryId, RelationshipType.TECHNOLOGY));
            // 产品类型节点关系对象列表
            typeRelList = productEntryRepository.findTypeNodeByUnitEntryId(productEntryId, RelationshipType.ASCRIPTION);
            // 该产品词条节点的所有上游产品节点(原材料、生产设备、生产工艺)
            // 上游 原材料
            upStreamRelList.addAll(materialsRepository.findUpStreamUnitNodeByEntryId(productEntryId, RelationshipType.MATERIALS));
            // 上游 生产设备
            upStreamRelList.addAll(materialsRepository.findUpStreamUnitNodeByEntryId(productEntryId, RelationshipType.MANUFACTURE));
            // 上游 生产工艺
            upStreamRelList.addAll(materialsRepository.findUpStreamUnitNodeByEntryId(productEntryId, RelationshipType.TECHNOLOGY));
            productEntryResultDto.setEntryInfoSourceEntityList(entryInfoSourceList);
            productEntryResultDto.setEntryLinkEntityList(entryLinkList);
            productEntryResultDto.setProTypeRelList(typeRelList);
            // 产品族谱
            productEntryResultDto.setProGenealogy(productGenealogy);
            productEntryResultDto.setProUpStreamRelList(upStreamRelList);
            productEntryResultDto.setProDownStreamRelList(downStreamRelList);
        }
        productEntryResultDto.setCurrentNode(currentNode);
        setSelfInfo(productEntryResultDto);
        return productEntryResultDto;
    }


    /**
     * 设置收藏，订阅，点赞等信息
     *
     * @param productEntryResultDto
     */
    private void setSelfInfo(ProductEntryResultDto productEntryResultDto) {
        ProductEntryNode currentNode = productEntryResultDto.getCurrentNode();
        String entryId = currentNode == null ? "" : currentNode.getProductEntryId();
        String userId = UserContext.getCurrentUserId();
        int i = sysLikeMapper.totalByEntryId(entryId);
        productEntryResultDto.setLikeNum(i);
        if (StringUtils.isEmpty(userId)) {
            productEntryResultDto.setSubscribe(false);
            productEntryResultDto.setCollect(false);
            productEntryResultDto.setLike(false);
        } else {
            productEntryResultDto.setSubscribe(sysSubscribeSettingMapper.countSubscribe(userId, entryId) > 0);
            productEntryResultDto.setCollect(sysCollectSettingMapper.countCollect(userId, entryId) > 0);
            productEntryResultDto.setLike(sysLikeMapper.countCollect(userId, entryId) > 0);
        }
    }

}
