package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.config.dbconfig.MultiTransaction;
import com.abcft.industrygraphmanagement.dao.neo.*;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.constant.RelationshipType;
import com.abcft.industrygraphmanagement.model.dto.QueryRelationDetailsParamDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryParamDto;
import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.*;
import com.abcft.industrygraphmanagement.service.RelationshipEntryService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author WangZhiZhou
 * @date 2021/3/24
 */
@Service
@Slf4j
public class RelationshipEntryServiceImpl implements RelationshipEntryService {

    @Autowired
    private CompanyEntryRepository companyEntryRepository;

    @Autowired
    private ProductEntryRepository productEntryRepository;
    @Autowired
    private AscriptionRelationshipRepository ascriptionRepository;

    @Autowired
    private SupplyRelationshipRepository supplyRepository;

    @Autowired
    private ProductionRelationshipRepository productionRepository;

    @Autowired
    private TechnologyRelationShipRepository technologyRepository;

    @Autowired
    private MaterialsRelationshipRepository materialsRepository;

    @Autowired
    private ManufactureRelationShipRepository manufactureRepository;


    /**
     * 关系词条创建关系接口
     *
     * @param relationshipEntryParamDto RelationshipEntryParamDto
     */
    @Override
    public void createRelationship(RelationshipEntryParamDto relationshipEntryParamDto) throws Exception {
        // 词条信息来源对象列表
        List<EntryInfoSourceEntity> entryInfoSourceEntityList = relationshipEntryParamDto.getEntryInfoSourceEntityList();
        // 词条链接对象列表
        List<EntryLinkEntity> entryLinkEntityList = relationshipEntryParamDto.getEntryLinkEntityList();
        // 关系词条dto
        RelationshipEntryDto relationshipEntryDto = relationshipEntryParamDto.getRelationshipEntryDto();
        String relationType = relationshipEntryDto.getRelationType();
        String startId = relationshipEntryDto.getStartId();
        String startName = relationshipEntryDto.getStartName();
        String endId = relationshipEntryDto.getEndId();
        String endName = relationshipEntryDto.getEndName();
        if (StringUtils.isEmpty(relationType) || StringUtils.isEmpty(startId)
                || StringUtils.isEmpty(startName) || StringUtils.isEmpty(endId)
                || StringUtils.isEmpty(endName)) {
            log.error("RelationshipEntryParamDto -> param:{}", relationshipEntryParamDto);
            throw new Exception("入参缺少必填参数！");
        }
        switch (relationType) {
            case RelationshipType.SUPPLY: // 公司-公司 供给关系
                this.createSupplyRelationship(relationshipEntryDto);
                break;
            case RelationshipType.DISTRIBUTION: // 公司->公司  经销关系
                break;
            case RelationshipType.INVESTMENT: // 公司->公司  投资关系  人物->公司  投资关系
                break;
            case RelationshipType.PRODUCTION: // 公司-产品 生产关系
                this.createProductionRelationship(relationshipEntryDto);
                break;
            case RelationshipType.MATERIALS: // 产品-产品 原材料关系
                this.createMaterialsRelationship(relationshipEntryDto);
                break;
            case RelationshipType.MANUFACTURE: // 产品-产品 制造设备关系
                break;
            case RelationshipType.TECHNOLOGY: // 产品-产品 加工工艺关系
                break;
            case RelationshipType.POST: // 人物-公司 任职关系
                break;
            default:
                break;
        }
    }

    /**
     * 查询关系详情接口
     *
     * @param queryRelationDetailsDto 查询关系详情入参dto
     * @return 关系详情
     */
    @Override
    public RelationshipEntryDto queryRelationDetails(QueryRelationDetailsParamDto queryRelationDetailsDto) throws Exception {
        String relationType = queryRelationDetailsDto.getRelationType();
        String startId = queryRelationDetailsDto.getStartId();
        String startName = queryRelationDetailsDto.getStartName();
        String endId = queryRelationDetailsDto.getEndId();
        String endName = queryRelationDetailsDto.getEndName();
        String supplyProductId = queryRelationDetailsDto.getSupplyProductId();
        String supplyProductName = queryRelationDetailsDto.getSupplyProductName();
        String terminalProductId = queryRelationDetailsDto.getTerminalProductId();
        String terminalProductName = queryRelationDetailsDto.getTerminalProductName();
        if (StringUtils.isEmpty(relationType) || StringUtils.isEmpty(startId)
                || StringUtils.isEmpty(endId)) {
            throw new Exception("入参缺少必填参数！");
        }
        RelationshipEntryDto relationshipEntryDto = new RelationshipEntryDto();
        switch (relationType) {
            case RelationshipType.SUPPLY: // 公司-公司 供给关系
                if (StringUtils.isEmpty(supplyProductId) ||
                        StringUtils.isEmpty(terminalProductId)) {
                    throw new Exception("入参缺少必填参数！");
                }
                SupplyRelationship supplyRelationship = supplyRepository.
                        findSupplyRelationship(startId, endId, supplyProductId, terminalProductId);
                if (supplyRelationship != null) {
                    BeanUtils.copyProperties(supplyRelationship, relationshipEntryDto);
                    relationshipEntryDto.setStartId(startId);
                    relationshipEntryDto.setStartName(startName);
                    relationshipEntryDto.setEndId(endId);
                    relationshipEntryDto.setEndName(endName);
                    relationshipEntryDto.setSupplyProductId(supplyProductId);
                    relationshipEntryDto.setSupplyProductName(supplyProductName);
                    relationshipEntryDto.setTerminalProductId(terminalProductId);
                    relationshipEntryDto.setTerminalProductName(terminalProductName);
                    relationshipEntryDto.setRelationType(RelationshipType.SUPPLY);
                }
                break;
            case RelationshipType.INVESTMENT: // 公司->公司  投资关系
                //TODO
                break;
            case RelationshipType.DISTRIBUTION: // 公司->公司  经销关系
                //TODO
                break;
            case RelationshipType.PRODUCTION: // 公司-产品 生产关系
                ProductionRelationship productionRelationship = productionRepository.
                        findProductionRelationship(startId, endId, RelationshipType.PRODUCTION);
                if (productionRelationship != null) {
                    BeanUtils.copyProperties(productionRelationship, relationshipEntryDto);
                    relationshipEntryDto.setStartId(startId);
                    relationshipEntryDto.setStartName(startName);
                    relationshipEntryDto.setEndId(endId);
                    relationshipEntryDto.setEndName(endName);
                    relationshipEntryDto.setRelationType(RelationshipType.PRODUCTION);
                }
                break;
            case RelationshipType.MATERIALS: // 产品-产品 原材料关系
                MaterialsRelationship materialsRelationship = materialsRepository.
                        findMaterialsRelationship(startId, endId, RelationshipType.MATERIALS);
                if (materialsRelationship != null) {
                    BeanUtils.copyProperties(materialsRelationship, relationshipEntryDto);
                    relationshipEntryDto.setStartId(startId);
                    relationshipEntryDto.setStartName(startName);
                    relationshipEntryDto.setEndId(endId);
                    relationshipEntryDto.setEndName(endName);
                    relationshipEntryDto.setRelationType(RelationshipType.MATERIALS);
                }
                break;
            case RelationshipType.MANUFACTURE: // 产品-产品 制造设备关系
                ManufactureRelationship manufactureRelationship = manufactureRepository.
                        findManufactureRelationship(startId, endId, RelationshipType.MANUFACTURE);
                if (manufactureRelationship != null) {
                    BeanUtils.copyProperties(manufactureRelationship, relationshipEntryDto);
                    relationshipEntryDto.setStartId(startId);
                    relationshipEntryDto.setStartName(startName);
                    relationshipEntryDto.setEndId(endId);
                    relationshipEntryDto.setEndName(endName);
                    relationshipEntryDto.setRelationType(RelationshipType.MANUFACTURE);
                }
                break;
            case RelationshipType.TECHNOLOGY: // 产品-产品 加工工艺关系
                //TODO
                break;
            case RelationshipType.ASCRIPTION: // 产品种类-产品类型，产品类型-产品单元 归属关系
                AscriptionRelationship ascriptionRelationship = ascriptionRepository.
                        findAscriptionRelationship(startId, endId, RelationshipType.ASCRIPTION);
                if (ascriptionRelationship != null) {
                    BeanUtils.copyProperties(ascriptionRelationship, relationshipEntryDto);
                    relationshipEntryDto.setStartId(startId);
                    relationshipEntryDto.setStartName(startName);
                    relationshipEntryDto.setEndId(endId);
                    relationshipEntryDto.setEndName(endName);
                    relationshipEntryDto.setRelationType(RelationshipType.ASCRIPTION);
                }
                break;
            case RelationshipType.POST: // 人物-公司 任职关系
                //TODO
                break;
            case RelationshipType.PERSON_INVESTMENT: // 人物->公司  投资关系
                //TODO
                break;
            default:
                break;
        }

        return relationshipEntryDto;
    }

    /**
     * 通过公司词条id 查询 该公司下的所有产品
     *
     * @param companyEntryId 公司词条id
     * @return List<ProductEntryNode>
     */
    @Override
    public List<ProductEntryNode> queryProductsByCompanyEntryId(String companyEntryId) {

        return productionRepository.findProductEntryNodeList(companyEntryId);
    }


    /**
     * 如果Noe4j不存在公司词条id的节点
     * 那么创建该公司词条节点
     *
     * @param companyEntryId   公司词条id
     * @param companyEntryName 公司词条名字
     */
    private void saveCompanyEntryNodeById(String companyEntryId, String companyEntryName) {
        if (!companyEntryRepository.existsById(companyEntryId)) {
            // 不存在该公司节点则创建公司节点
            CompanyEntryNode companyEntryNode = new CompanyEntryNode();
            companyEntryNode.setCompanyEntryId(companyEntryId);
            companyEntryNode.setName(companyEntryName);
            companyEntryRepository.save(companyEntryNode);
        }
    }

    /**
     * 如果Noe4j不存在产品词条id的节点
     * 那么创建该产品词条节点
     *
     * @param productEntryId   公司词条id
     * @param productEntryName 公司词条名字
     */
    private void saveProductEntryNodeById(String productEntryId, String productEntryName) throws Exception {
        if (!productEntryRepository.existsById(productEntryId)) {
            // 不存在该公司节点则创建公司节点
            ProductEntryNode productEntryNode = new ProductEntryNode();
            productEntryNode.setProductEntryId(productEntryId);
            productEntryNode.setName(productEntryName);
            productEntryRepository.save(productEntryNode);
        }
    }

    /**
     * 创建关系词条的 公司->公司 供给关系  (多对多)
     *
     * @param relationshipEntryDto 入参 RelationshipEntryDto
     * @throws Exception 入参异常
     */
    @MultiTransaction
    private void createSupplyRelationship(RelationshipEntryDto relationshipEntryDto) throws Exception {
        String startId = relationshipEntryDto.getStartId();
        String startName = relationshipEntryDto.getStartName();
        String endId = relationshipEntryDto.getEndId();
        String endName = relationshipEntryDto.getEndName();
        // 终端产品id
        String terminalProductId = relationshipEntryDto.getTerminalProductId();
        // 终端产品名称
        String terminalProductName = relationshipEntryDto.getTerminalProductName();
        // 供给产品id
        String supplyProductId = relationshipEntryDto.getSupplyProductId();
        // 供给产品名称
        String supplyProductName = relationshipEntryDto.getSupplyProductName();
        if (StringUtils.isEmpty(supplyProductId) || StringUtils.isEmpty(supplyProductName)
                || StringUtils.isEmpty(terminalProductId) || StringUtils.isEmpty(terminalProductName)) {
            throw new Exception("入参缺少必填参数！");
        }
        this.saveCompanyEntryNodeById(startId, startName);
        this.saveCompanyEntryNodeById(endId, endName);
        this.saveProductEntryNodeById(supplyProductId, supplyProductName);
        this.saveProductEntryNodeById(terminalProductId, terminalProductName);
        Optional<CompanyEntryNode> startById = companyEntryRepository.findById(startId);
        if (!startById.isPresent()) {
            log.error("createSupplyRelationship 找不到公司词条id为：{}的节点", startId);
            throw new Exception("系统异常");
        }
        CompanyEntryNode startCompanyNode = startById.get();
        Optional<CompanyEntryNode> endById = companyEntryRepository.findById(endId);
        if (!endById.isPresent()) {
            log.error("createSupplyRelationship 找不到公司词条id为：{}的节点", endId);
            throw new Exception("系统异常");
        }
        CompanyEntryNode endCompanyNode = endById.get();
        // 创建 公司->公司 供给关系
        SupplyRelationship supplyRelationship = new SupplyRelationship();
        // 通过开始节点公司id、结束节点公司id、供给产品id、终端产品id , 查询 公司->公司 供给关系
        SupplyRelationship supplyRelationship1 = supplyRepository.findSupplyRelationship(startId, endId, supplyProductId, terminalProductId);
        if (supplyRelationship1 != null) {
            supplyRelationship = supplyRelationship1;
        }
        supplyRelationship.setStartNode(startCompanyNode);
        supplyRelationship.setEndNode(endCompanyNode);
        supplyRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
        supplyRelationship.setRelationEntryName(relationshipEntryDto.getRelationEntryName());
        supplyRelationship.setSupplyProductId(supplyProductId);
        supplyRelationship.setTerminalProductId(terminalProductId);
        supplyRelationship.setSupplyProportion(relationshipEntryDto.getSupplyProportion());
        supplyRelationship.setPurchaseProportion(relationshipEntryDto.getPurchaseProportion());
        supplyRelationship.setStartTime(relationshipEntryDto.getStartTime());
        supplyRelationship.setEndTime(relationshipEntryDto.getEndTime());
        // 通过开始节点公司id、结束节点公司id、供给产品id、终端产品id , 删除 公司->公司 供给关系
        supplyRepository.deleteSupplyRelationship(startId, endId, supplyProductId, terminalProductId);
        supplyRepository.save(supplyRelationship);

        // 检索 供应公司->供应产品 生产关系
        ProductionRelationship supplyProductionRelationship = productionRepository.
                findProductionRelationship(startId, supplyProductId, RelationshipType.PRODUCTION);
        // 检索 采购公司->终端产品 生产关系
        ProductionRelationship terminalProductionRelationship = productionRepository.
                findProductionRelationship(endId, terminalProductId, RelationshipType.PRODUCTION);
        ProductionRelationship productionRelationship = new ProductionRelationship();
        // 检索 供应公司->供应产品 生产关系不存在，创建生产关系
        if (supplyProductionRelationship == null) {
            Optional<ProductEntryNode> supplyById = productEntryRepository.findById(supplyProductId);
            if (!supplyById.isPresent()) {
                log.error("createSupplyRelationship 找不到产品词条id为：{}的节点", supplyProductId);
                throw new Exception("系统异常");
            }
            ProductEntryNode supplyProductEntryNode = supplyById.get();
            productionRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
            productionRelationship.setEndNode(supplyProductEntryNode);
            productionRelationship.setStartNode(startCompanyNode);
            productionRepository.save(productionRelationship);
        }
        // 检索 采购公司->终端产品 生产关系不存在，创建生产关系
        if (terminalProductionRelationship == null) {
            Optional<ProductEntryNode> terminalById = productEntryRepository.findById(terminalProductId);
            if (!terminalById.isPresent()) {
                log.error("createSupplyRelationship 找不到产品词条id为：{}的节点", terminalProductId);
                throw new Exception("系统异常");
            }
            ProductEntryNode terminalProductEntryNode = terminalById.get();
            productionRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
            productionRelationship.setStartNode(endCompanyNode);
            productionRelationship.setEndNode(terminalProductEntryNode);
            productionRepository.save(productionRelationship);
        }
    }

    /**
     * 创建关系词条的 公司->产品 生产关系   (一对多)
     *
     * @param relationshipEntryDto 入参 RelationshipEntryDto
     */
    @MultiTransaction
    private void createProductionRelationship(RelationshipEntryDto relationshipEntryDto) throws Exception {
        String startId = relationshipEntryDto.getStartId();
        String startName = relationshipEntryDto.getStartName();
        String endId = relationshipEntryDto.getEndId();
        String endName = relationshipEntryDto.getEndName();
        this.saveCompanyEntryNodeById(startId, startName);
        this.saveProductEntryNodeById(endId, endName);
        // 创建 公司->产品 生产关系
        ProductionRelationship productionRelationship = new ProductionRelationship();
        Optional<CompanyEntryNode> startById = companyEntryRepository.findById(startId);
        Optional<ProductEntryNode> endById = productEntryRepository.findById(endId);
        if (!startById.isPresent() || !endById.isPresent()) {
            log.error("创建关系词条的 公司->公司 供给关系过程中，在Noe4j中没有检索到公司或者产品节点！");
            throw new Exception("业务异常！");
        }
        // 通过公司词条id和产品词条id 查询 ProductionRelationship
        ProductionRelationship productionRelationship1 = productionRepository.findProductionRelationship(startId, endId, RelationshipType.PRODUCTION);
        if (productionRelationship1 != null) {
            productionRelationship = productionRelationship1;
        }
        CompanyEntryNode companyEntryNode = startById.get();
        ProductEntryNode productEntryNode = endById.get();
        productionRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
        productionRelationship.setStartNode(companyEntryNode);
        productionRelationship.setRelationEntryName(relationshipEntryDto.getRelationEntryName());
        productionRelationship.setEndNode(productEntryNode);
        productionRelationship.setIncomeProportion(relationshipEntryDto.getIncomeProportion());
        productionRelationship.setProductGross(relationshipEntryDto.getProductGross());
        productionRelationship.setProductPrice(relationshipEntryDto.getProductPrice());
        // 通过公司词条id、产品词条id 删除 公司->产品 生产关系
        productionRepository.deleteProductionRelationship(startId, endId, RelationshipType.PRODUCTION);
        productionRepository.save(productionRelationship);
    }

    /**
     * 创建关系词条的 产品->产品 原材料关系   (一对多)
     *
     * @param relationshipEntryDto 入参 RelationshipEntryDto
     */
    @MultiTransaction
    private void createMaterialsRelationship(RelationshipEntryDto relationshipEntryDto) throws Exception {
        String startId = relationshipEntryDto.getStartId();
        String startName = relationshipEntryDto.getStartName();
        String endId = relationshipEntryDto.getEndId();
        String endName = relationshipEntryDto.getEndName();
        this.saveProductEntryNodeById(startId, startName);
        this.saveProductEntryNodeById(endId, endName);

        Optional<ProductEntryNode> startById = productEntryRepository.findById(startId);
        Optional<ProductEntryNode> endById = productEntryRepository.findById(endId);
        if (!startById.isPresent() || !endById.isPresent()) {
            log.error("创建关系词条的 产品->产品 原材料关系过程中，在Noe4j中没有检索到产品节点," +
                    "产品节点id为：{}、{}！", startId, endId);
            throw new Exception("业务异常！");
        }
        // 创建 产品->产品 原材料关系
        MaterialsRelationship materialsRelationship = new MaterialsRelationship();
        // 通过开始产品词条节点id、结束产品词条节点id、关系类型名 查询 materialsRelationship
        MaterialsRelationship materialsRelationship1 = materialsRepository.findMaterialsRelationship(startId, endId, RelationshipType.MATERIALS);
        if (materialsRelationship1 != null) {
            materialsRelationship = materialsRelationship1;
        }
        materialsRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
        materialsRelationship.setRelationEntryName(relationshipEntryDto.getRelationEntryName());
        materialsRelationship.setStartNode(startById.get());
        materialsRelationship.setEndNode(endById.get());
        materialsRelationship.setCostProportion(relationshipEntryDto.getCostProportion());
        materialsRelationship.setStartTime(relationshipEntryDto.getStartTime());
        materialsRelationship.setEndTime(relationshipEntryDto.getEndTime());
        // 通过开始节点产品id、结束节点产品id, 删除 产品->产品 原材料关系
        materialsRepository.deleteMaterialsRelationship(startId, endId, RelationshipType.MATERIALS);
        materialsRepository.save(materialsRelationship);
    }
}
