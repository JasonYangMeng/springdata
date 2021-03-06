package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.config.dbconfig.MultiTransaction;
import com.abcft.industrygraphmanagement.config.dbconfig.NotesValue;
import com.abcft.industrygraphmanagement.dao.mysql.*;
import com.abcft.industrygraphmanagement.dao.neo.*;
import com.abcft.industrygraphmanagement.model.condition.CompanyEntryCondition;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.constant.RelationshipType;
import com.abcft.industrygraphmanagement.model.dto.*;
import com.abcft.industrygraphmanagement.model.entity.CompanyEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.entity.ProductEntity;
import com.abcft.industrygraphmanagement.model.node.*;
import com.abcft.industrygraphmanagement.service.*;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Created by YangMeng on 2021/3/3 15:05
 */
@Service
@Slf4j
public class CompanyEntryServiceImpl implements CompanyEntryService {

    @Autowired
    private CompanyEntryRepository companyEntryRepository;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private ProductionRelationshipRepository productionRelationshipRepository;

    @Autowired
    private SupplyRelationshipRepository supplyRelationshipRepository;

    @Autowired
    private EntryInfoSourceService entryInfoSourceService;

    @Autowired
    private EntryLinkService entryLinkService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SysSubscribeSettingMapper sysSubscribeSettingMapper;

    @Autowired
    private SysCollectSettingMapper sysCollectSettingMapper;

    @Autowired
    private SysLikeMapper sysLikeMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private AssetsLiabilitiesRepository assetsLiabilitiesRepository;

    @Autowired
    private CashFlowDataRepository cashFlowDataRepository;

    @Autowired
    private FinanceDataRepository financeDataRepository;

    @Autowired
    private OperateDataRepository operateDataRepository;

    /**
     * ??????????????????
     *
     * @param companyEntryId
     * @return
     */
    @Override
    public CompanyEntryDto getCompanyEntryById(String companyEntryId) throws Exception {
        if (StringUtils.isEmpty(companyEntryId)) {
            throw new Exception("??????????????????");
        }
        if (!companyEntryRepository.existsById(companyEntryId)) {
            return new CompanyEntryDto();
        }
        CompanyEntryDto companyEntryDto = new CompanyEntryDto();
        CompanyEntryNode companyEntryNode = companyEntryRepository.findById(companyEntryId).get();
        //????????????
        List<EntryLinkEntity> entryLinkEntities = entryLinkService.queryEntryLinkInnerList(companyEntryId);
        List<EntryInfoSourceEntity> entryInfoSourceEntities = entryInfoSourceService.queryAllByEntryId(companyEntryId);

        //????????????
        List<ProductDto> productDtoList = productionRelationshipRepository.getProductListByCompanyEntryId(companyEntryId);

        //??????????????? ?????????
        List<ProductToCompanyDto> productToCompanyDtoList = new ArrayList<>();
        List<ProductToCompanyDto> productToChildCompanyDtoList = new ArrayList<>();
        ProductToCompanyDto productToCompanyDto;
        ProductToCompanyDto productToCompanyDto1;
        for (ProductDto item : productDtoList) {
            productToCompanyDto = new ProductToCompanyDto();
            productToCompanyDto1 = new ProductToCompanyDto();
            //?????????????????????id???????????????id
            List<SupplyParentDto> supplyParentDtoList = supplyRelationshipRepository.getSupplyParentList(companyEntryId, item.getProductEntryId());
            productToCompanyDto.setSupplyParentDtoList(supplyParentDtoList);
            productToCompanyDto.setProductEntryId(item.getProductEntryId());
            productToCompanyDtoList.add(productToCompanyDto);

            List<SupplyParentDto> supplyChildDtoList = supplyRelationshipRepository.getSupplyChildList(companyEntryId, item.getProductEntryId());
            productToCompanyDto1.setSupplyParentDtoList(supplyChildDtoList);
            productToCompanyDto1.setProductEntryId(item.getProductEntryId());
            productToChildCompanyDtoList.add(productToCompanyDto1);
        }

        // ????????????????????????(??????->??????  ????????????)
        List<ShareholderInformationDto> shareHolderList = companyEntryRepository.getShareholderInfoList(companyEntryId);
        // ????????????????????????(??????->??????  ????????????) TODO
        // ????????????????????????(??????->??????  ????????????)
        List<SeniorManagerDto> seniorManagerList = companyEntryRepository.getSeniorManagerList(companyEntryId);

        // ??????????????????
        MarketDataNode marketDataNode = companyEntryRepository.getMarketDataNode(companyEntryId);

        // ????????????????????????
        List<OperateDataNode> operateNode = companyEntryRepository.getOperateDataNode(companyEntryId);
        if (CollectionUtils.isEmpty(operateNode)) {
            operateNode.add(new OperateDataNode());
        }
        List<CompanyDataDto> operateDataList = this.getCompanyDataList(operateNode);

        // ??????????????????
        List<FinanceDataNode> financeNode = companyEntryRepository.getFinanceDataNode(companyEntryId);
        if (CollectionUtils.isEmpty(financeNode)) {
            financeNode.add(new FinanceDataNode());
        }
        List<CompanyDataDto> financeDataList = this.getCompanyDataList(financeNode);
        // ????????????????????????
        List<AssetsLiabilitiesNode> assetsNode = companyEntryRepository.getAssetsLiabilitiesNode(companyEntryId);
        if (CollectionUtils.isEmpty(assetsNode)) {
            assetsNode.add(new AssetsLiabilitiesNode());
        }
        List<CompanyDataDto> assetDataList = this.getCompanyDataList(assetsNode);
        // ????????????????????????
        List<CashFlowDataNode> cashFlowNode = companyEntryRepository.getCashFlowDataNode(companyEntryId);
        if (CollectionUtils.isEmpty(cashFlowNode)) {
            cashFlowNode.add(new CashFlowDataNode());
        }
        List<CompanyDataDto> cashFlowDataList = this.getCompanyDataList(cashFlowNode);

        companyEntryDto.setProductToParentCompanyDtoList(productToCompanyDtoList);
        companyEntryDto.setProductToChildCompanyDtoList(productToChildCompanyDtoList);
        companyEntryDto.setProductDtoList(productDtoList);
        companyEntryDto.setEntryLinkEntityList(entryLinkEntities);
        companyEntryDto.setEntryInfoSourceEntityList(entryInfoSourceEntities);
        companyEntryDto.setCompanyEntryNode(companyEntryNode);
        companyEntryDto.setShareholderInfoList(shareHolderList);
        companyEntryDto.setSeniorManagerList(seniorManagerList);
        companyEntryDto.setMarketDataNode(marketDataNode);
        companyEntryDto.setOperateDataList(operateDataList);
        companyEntryDto.setFinanceDataList(financeDataList);
        companyEntryDto.setAssetsLiabilitiesList(assetDataList);
        companyEntryDto.setCashFlowDataList(cashFlowDataList);
        //????????????????????????
        setSelfInfo(companyEntryDto);

        return companyEntryDto;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param companyEntryDto
     */
    private void setSelfInfo(CompanyEntryDto companyEntryDto) {
        String entryId = companyEntryDto.getCompanyEntryNode().getCompanyEntryId();
        String userId = UserContext.getCurrentUserId();
        int i = sysLikeMapper.totalByEntryId(entryId);
        companyEntryDto.setLikeNum(i);
        if (StringUtils.isEmpty(userId)) {
            companyEntryDto.setSubscribe(false);
            companyEntryDto.setCollect(false);
            companyEntryDto.setLike(false);
        } else {
            companyEntryDto.setSubscribe(sysSubscribeSettingMapper.countSubscribe(userId, entryId) > 0);
            companyEntryDto.setCollect(sysCollectSettingMapper.countCollect(userId, entryId) > 0);
            companyEntryDto.setLike(sysLikeMapper.countCollect(userId, entryId) > 0);
        }
    }

    /**
     * ???????????????????????????????????????????????????????????????????????? List<CompanyDataDto>??????
     *
     * @param objects ????????????
     * @return companyDataList
     */
    private List<CompanyDataDto> getCompanyDataList(Object objects) {
        List<CompanyDataDto> companyDataList = new ArrayList<>();
        if (objects == null) {
            return companyDataList;
        }
        List<Object> objs = new ArrayList<>();
        if (objects instanceof ArrayList<?>) {
            for (Object o : (List<?>) objects) {
                objs.add(o);
            }
        }

        CompanyDataDto companyDataDto;
        try {
            Object obj1 = objs.get(0);
            Class aClass1 = obj1.getClass();
            Field[] fields1 = aClass1.getDeclaredFields();
            for (Field field1 : fields1) {
                companyDataDto = new CompanyDataDto();
                // ??????????????????
                field1.setAccessible(true);
                // ????????????
                String notesName = "";
                if (field1.isAnnotationPresent(NotesValue.class)) {
                    NotesValue annotation = field1.getAnnotation(NotesValue.class);
                    notesName = annotation.value();
                }
                // ???????????????
                String propertyName1 = field1.getName();
                // ???????????????
                Object value1 = field1.get(obj1);
                companyDataDto.setName(notesName);
                companyDataDto.setPropertyName(propertyName1);
                companyDataDto.setValue((String) value1);
                if (objs.size() == 2) {
                    Object obj2 = objs.get(1);
                    // ???????????????
                    Class aClass2 = obj2.getClass();
                    Field[] fields2 = aClass2.getDeclaredFields();
                    for (Field field2 : fields2) {
                        // ??????????????????
                        field2.setAccessible(true);
                        String propertyName2 = field2.getName();
                        // ???????????????
                        if (propertyName1.equals(propertyName2)) {
                            Object value2 = field2.get(obj2);
                            companyDataDto.setValue1((String) value2);
                        }
                    }
                }
                if (!StringUtils.isEmpty(notesName)) {
                    companyDataList.add(companyDataDto);
                }
            }
        } catch (Exception e) {
            log.error("????????????getCompanyDataList, ??????????????????:", e);
        }
        return companyDataList;
    }

    /**
     * ?????????????????????????????????
     *
     * @param companyEntryId
     * @param productEntryId
     * @return
     * @throws Exception
     */
    @Override
    public GraphDto getGraph(String companyEntryId, String productEntryId) throws Exception {
        GraphDto graphDto = new GraphDto();
        if (StringUtils.isEmpty(companyEntryId) || StringUtils.isEmpty(productEntryId)) {
            throw new Exception("?????????????????????????????????");
        }
        if (!companyEntryRepository.existsById(companyEntryId) || !productEntryRepository.existsById(productEntryId)) {
            return new GraphDto();
        }
        CompanyEntryNode companyEntryNode = companyEntryRepository.findById(companyEntryId).get();
        ProductEntryNode productEntryNode = productEntryRepository.findById(productEntryId).get();

        //??????????????? ?????????
        List<GraphDto> parentGraphList = new ArrayList<>();
        List<GraphDto> childGraphList = new ArrayList<>();
        GraphDto item;
        GraphDto item1;

        //?????????????????????id?????????id ??????
        List<SupplyParentDto> supplyParentDtoList = supplyRelationshipRepository.getSupplyParentList(companyEntryId, productEntryId);
        for (SupplyParentDto a : supplyParentDtoList) {
            item = new GraphDto();
            BeanUtils.copyProperties(a, item);
            parentGraphList.add(item);
        }

        List<SupplyParentDto> supplyChildDtoList = supplyRelationshipRepository.getSupplyChildList(companyEntryId, productEntryId);
        for (SupplyParentDto a : supplyChildDtoList) {
            item1 = new GraphDto();
            BeanUtils.copyProperties(a, item1);
            childGraphList.add(item1);
        }

        graphDto.setCompanyEntryId(companyEntryId);
        graphDto.setProductEntryId(productEntryId);
        graphDto.setCompanyName(companyEntryNode.getName());
        graphDto.setProductName(productEntryNode.getName());
        graphDto.setChildGraphList(childGraphList);
        graphDto.setParentGraphList(parentGraphList);
        return graphDto;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     *
     * @param companyEntryId ??????id
     * @param label          neo4j??????label
     * @param propertyName   ???????????????
     * @return CompanyLineChartQuarterAndYearDto
     */
    @Override
    public CompanyLineChartQuarterAndYearDto getCompanyLineChartList(String companyEntryId, String label, String propertyName) {

        CompanyLineChartQuarterAndYearDto companyLineChart = new CompanyLineChartQuarterAndYearDto();
        // ???????????????
        List<CompanyLineChartDto> quarterLineChartList = companyEntryRepository.getCompanyLineChartList(companyEntryId,
                label, propertyName);
        if (CollectionUtils.isEmpty(quarterLineChartList)) {
            return companyLineChart;
        }
        // ???????????????
        List<CompanyLineChartDto> yearLineChartList = quarterLineChartList.stream().filter(c ->
                c.getEndDate().contains("-12-31")).collect(Collectors.toList());

        companyLineChart.setQuarterLineChartList(quarterLineChartList);
        companyLineChart.setYearLineChartList(yearLineChartList);

        return companyLineChart;
    }

    /**
     * ????????????
     *
     * @param companyEntryCondition
     */
    @Override
    @MultiTransaction
    public String updateCompanyEntry(CompanyEntryCondition companyEntryCondition) throws Exception {
        CompanyEntryNode companyEntryNode = companyEntryCondition.getCompanyEntryNode();
        String companyEntryId = companyEntryNode.getCompanyEntryId();
        if (StringUtils.isEmpty(companyEntryNode.getName())) {
            throw new Exception("??????????????????");
        }
        if (StringUtils.isEmpty(companyEntryId)) {
            //??????????????????
            companyEntryId = getCompanyIdByMysql(companyEntryNode.getName());
            companyEntryNode.setCompanyEntryId(companyEntryId);
        }
        //??????????????????
        String dateStr = DateExtUtils.getCurrentDateStr();
        companyEntryNode.setModifyTime(dateStr);
        if (StringUtils.isEmpty(companyEntryNode.getCreateTime())) {
            companyEntryNode.setCreateTime(dateStr);
        }
        String currentUserId = UserContext.getCurrentUserId();
        if (StringUtils.isEmpty(companyEntryNode.getCreateUserId())) {
            companyEntryNode.setCreateUserId(currentUserId);
        }
        companyEntryNode.setModifyUserId(currentUserId);

        //TODO ????????????
        companyEntryNode.setVersion("0.2");
        CompanyEntryNode companyEntryNode1 = companyEntryRepository.save(companyEntryNode);

        //??????????????????
        companyService.updateStatus(companyEntryId);

        //??????????????? ????????????
        List<EntryInfoSourceEntity> entryInfoSourceEntityList = companyEntryCondition.getEntryInfoSourceEntityList();
        modifyEntryInfoSource(entryInfoSourceEntityList, companyEntryNode1.getCompanyEntryId());

        List<EntryLinkEntity> entryLinkEntityList = companyEntryCondition.getEntryLinkEntityList();
        modifyEntryLink(entryLinkEntityList, companyEntryNode1.getCompanyEntryId());

        //?????? ??????-????????????
        List<ProductDto> productionRelationshipList = companyEntryCondition.getProductDtoList();

        if (!CollectionUtils.isEmpty(productionRelationshipList)) {
            productionRelationshipRepository.deleteByIds(companyEntryNode.getCompanyEntryId());
            addProductionRelationship(productionRelationshipList, companyEntryNode1);
        } else {
            productionRelationshipRepository.deleteByIds(companyEntryNode.getCompanyEntryId());
        }
        //?????? ??????-?????? ???????????????
        List<SupplyParentDto> supplyRelationshipParentList = companyEntryCondition.getSupplyRelationshipParentList();

        if (!CollectionUtils.isEmpty(supplyRelationshipParentList)) {
            List<String> collect = supplyRelationshipParentList.stream().map(a -> a.getUuid()).collect(Collectors.toList());
            supplyRelationshipRepository.deleteParentByIds(companyEntryNode.getCompanyEntryId(), collect);
            //.????????????
            addSupplyRelationshipParent(supplyRelationshipParentList, companyEntryNode1);
        } else {
            supplyRelationshipRepository.deleteParentByIds(companyEntryNode.getCompanyEntryId());
        }
        //?????? ??????-?????? ????????????
        List<SupplyParentDto> supplyRelationshipChildList = companyEntryCondition.getSupplyRelationshipChildList();
        //.????????????-?????? ???????????????
        if (!CollectionUtils.isEmpty(supplyRelationshipChildList)) {
            List<String> collect = supplyRelationshipChildList.stream().map(a -> a.getUuid()).collect(Collectors.toList());
            supplyRelationshipRepository.deleteChildByIds(companyEntryNode.getCompanyEntryId(), collect);
            //????????????
            addSupplyRelationshipChild(supplyRelationshipChildList, companyEntryNode1);
        } else {
            supplyRelationshipRepository.deleteChildByIds(companyEntryNode.getCompanyEntryId());
        }

        //??????????????????
        AssetsLiabilitiesNode assetsLiabilitiesNode = companyEntryCondition.getAssetsLiabilitiesNode();
        assetsLiabilitiesNode.setCompanyEntryId(companyEntryId);
        assetsLiabilitiesNode.setEndDate("2020");
        assetsLiabilitiesNode.setName(companyEntryNode1.getName());
        assetsLiabilitiesRepository.save(assetsLiabilitiesNode);
        AssetsLiabilitiesNode assetsLiabilitiesNode1 = companyEntryCondition.getAssetsLiabilitiesNode1();
        assetsLiabilitiesNode1.setCompanyEntryId(companyEntryId + "1");
        assetsLiabilitiesNode1.setEndDate("2019");
        assetsLiabilitiesNode1.setName(companyEntryNode1.getName());
        assetsLiabilitiesRepository.save(assetsLiabilitiesNode1);

        FinanceDataNode financeDataNode = companyEntryCondition.getFinanceDataNode();
        financeDataNode.setCompanyEntryId(companyEntryId);
        financeDataNode.setEndDate("2020");
        financeDataNode.setName(companyEntryNode1.getName());
        financeDataRepository.save(financeDataNode);
        FinanceDataNode financeDataNode1 = companyEntryCondition.getFinanceDataNode1();
        financeDataNode1.setCompanyEntryId(companyEntryId + "1");
        financeDataNode1.setEndDate("2019");
        financeDataNode1.setName(companyEntryNode1.getName());
        financeDataRepository.save(financeDataNode1);

        OperateDataNode operateDataNode = companyEntryCondition.getOperateDataNode();
        operateDataNode.setCompanyEntryId(companyEntryId);
        operateDataNode.setEndDate("2020");
        operateDataNode.setName(companyEntryNode1.getName());
        operateDataRepository.save(operateDataNode);
        OperateDataNode operateDataNode1 = companyEntryCondition.getOperateDataNode1();
        operateDataNode1.setCompanyEntryId(companyEntryId + "1");
        operateDataNode1.setEndDate("2019");
        operateDataNode1.setName(companyEntryNode1.getName());
        operateDataRepository.save(operateDataNode1);

        CashFlowDataNode cashFlowDataNode = companyEntryCondition.getCashFlowDataNode();
        cashFlowDataNode.setCompanyEntryId(companyEntryId);
        cashFlowDataNode.setEndDate("2020");
        cashFlowDataNode.setName(companyEntryNode1.getName());
        cashFlowDataRepository.save(cashFlowDataNode);
        CashFlowDataNode cashFlowDataNode1 = companyEntryCondition.getCashFlowDataNode1();
        cashFlowDataNode1.setCompanyEntryId(companyEntryId + "1");
        cashFlowDataNode1.setEndDate("2019");
        cashFlowDataNode1.setName(companyEntryNode1.getName());
        cashFlowDataRepository.save(cashFlowDataNode1);

        return companyEntryId;
    }

    /**
     * ????????????
     *
     * @param uuid
     */
    @Override
    public void deleteById(String uuid) {
        companyEntryRepository.deleteAllById(uuid);
        productMapper.updateProductStatus(uuid);
        companyMapper.updateStatus(uuid);
    }

    /**
     * @param companyEntryName
     * @return
     */
    @Override
    public String getCompanyIdByMysql(String companyEntryName) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyId(UuidUtils.generate(NameType.COMPANY));
        companyEntity.setName(companyEntryName);
        companyEntity.setCreateTime(DateExtUtils.getCurrentDateStr());
        companyService.addCompanyEntity(companyEntity);
        return companyEntity.getCompanyId();
    }

    /**
     * ????????????-?????? ????????????
     *
     * @param productionRelationshipList ????????????
     * @param companyEntryNode1          ??????
     */
    private void addProductionRelationship(List<ProductDto> productionRelationshipList, CompanyEntryNode companyEntryNode1) throws Exception {
        List<ProductionRelationship> productionRelationshipList1 = new ArrayList<>();
        String currentUserId = UserContext.getCurrentUserId();
        String dateStr = DateExtUtils.getCurrentDateStr();
        for (ProductDto item : productionRelationshipList) {
            ProductionRelationship productionRelationship = new ProductionRelationship();
            BeanUtils.copyProperties(item, productionRelationship);
            productionRelationship.setStartNode(companyEntryNode1);
            if (StringUtils.isEmpty(item.getUuid())) {
                productionRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
            }
            String productEntryId = item.getProductEntryId();
            String productName = item.getProductName();
            // ??????????????????id??????????????????mysql????????????????????????????????????????????????????????????????????????
            if (StringUtils.isEmpty(productEntryId)) {
                if (StringUtils.isEmpty(productName)) {
                    throw new Exception("?????????????????????????????????");
                }
                productEntryId = UuidUtils.generate(NameType.PRODUCT);
                ProductEntity productEntity = new ProductEntity();
                productEntity.setProductId(productEntryId);
                productEntity.setProductName(productName);
                productEntity.setProductTreeStatus("0");
                productEntity.setIndustryStatus("0");
                productEntity.setArtworkStatus("0");
                productEntity.setType("1");
                productEntity.setCreateEntry("1");
                productEntity.setAuditStatus("0");
                productEntity.setCreateUserId(currentUserId);
                productEntity.setCreateTime(dateStr);
                productService.addProduct(productEntity);
            }
            //???????????????
            Optional<ProductEntryNode> byId = productEntryRepository.findById(productEntryId);
            if (byId.isPresent()) {
                ProductEntryNode productEntryNode = byId.get();
                productionRelationship.setEndNode(productEntryNode);
            } else {
                ProductEntryNode productEntryNode = new ProductEntryNode();
                productEntryNode.setProductEntryId(productEntryId);
                productEntryNode.setName(productName);
                productEntryNode.setType("1");
                productEntryNode.setCreateUserId(currentUserId);
                ProductEntryNode save = productEntryRepository.save(productEntryNode);
                productionRelationship.setEndNode(save);
            }
            productionRelationshipList1.add(productionRelationship);
        }
        // ????????????
        productionRelationshipRepository.saveAll(productionRelationshipList1);
    }

    /**
     * ????????????-?????? ????????? ??????
     *
     * @param supplyRelationshipList
     * @param companyEntryNode1
     */
    private void addSupplyRelationshipParent(List<SupplyParentDto> supplyRelationshipList, CompanyEntryNode companyEntryNode1) {
        List<SupplyRelationship> supplyRelationshipList1 = new ArrayList<>();
        SupplyRelationship supplyRelationship;
        List<ProductionDto> productionDtoList = new ArrayList<>();
        for (SupplyParentDto item : supplyRelationshipList) {
            supplyRelationship = new SupplyRelationship();
            BeanUtils.copyProperties(item, supplyRelationship);
            supplyRelationship.setEndNode(companyEntryNode1);
            if (StringUtils.isEmpty(item.getUuid())) {
                supplyRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
            }
            supplyRelationship.setSupplyProductId(item.getProductEntryId());
            supplyRelationship.setTerminalProductId(item.getCurrentProductId());
            if (StringUtils.isEmpty(item.getCompanyEntryId())) {
                log.error("????????????-?????????????????????????????????");
                continue;
            }
            productionDtoList.add(new ProductionDto(companyEntryNode1.getCompanyEntryId(), item.getCurrentProductId(), item.getCurrentProductName()));
            Optional<CompanyEntryNode> byId = companyEntryRepository.findById(item.getCompanyEntryId());
            if (byId.isPresent()) {
                supplyRelationship.setStartNode(byId.get());
                productionDtoList.add(new ProductionDto(byId.get().getCompanyEntryId(), item.getProductEntryId(), item.getProductName()));
            } else {
                CompanyEntryNode startNode = new CompanyEntryNode();
                startNode.setCompanyEntryId(item.getCompanyEntryId());
                startNode.setName(item.getCompanyName());
                CompanyEntryNode save = companyEntryRepository.save(startNode);
                supplyRelationship.setStartNode(save);
                productionDtoList.add(new ProductionDto(startNode.getCompanyEntryId(), item.getProductEntryId(), item.getProductName()));
            }

            supplyRelationshipList1.add(supplyRelationship);
        }
        supplyRelationshipRepository.saveAll(supplyRelationshipList1);
        //????????????????????????
        addProduction(productionDtoList);
    }

    /**
     * ????????????-?????? ?????? ??????
     *
     * @param supplyRelationshipList
     * @param companyEntryNode1
     */
    private void addSupplyRelationshipChild(List<SupplyParentDto> supplyRelationshipList, CompanyEntryNode companyEntryNode1) {
        List<SupplyRelationship> supplyRelationshipList1 = new ArrayList<>();
        SupplyRelationship supplyRelationship;
        List<ProductionDto> productionDtoList = new ArrayList<>();
        for (SupplyParentDto item : supplyRelationshipList) {
            supplyRelationship = new SupplyRelationship();
            BeanUtils.copyProperties(item, supplyRelationship);
            if (StringUtils.isEmpty(item.getUuid())) {
                supplyRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
            }
            supplyRelationship.setStartNode(companyEntryNode1);
            supplyRelationship.setSupplyProductId(item.getCurrentProductId());
            supplyRelationship.setTerminalProductId(item.getProductEntryId());
            if (StringUtils.isEmpty(item.getCompanyEntryId())) {
                log.error("????????????-?????? ?????? ??????????????????");
                continue;
            }
            productionDtoList.add(new ProductionDto(companyEntryNode1.getCompanyEntryId(), item.getCurrentProductId(), item.getCurrentProductName()));
            Optional<CompanyEntryNode> byId = companyEntryRepository.findById(item.getCompanyEntryId());
            if (byId.isPresent()) {
                supplyRelationship.setEndNode(byId.get());
                productionDtoList.add(new ProductionDto(byId.get().getCompanyEntryId(), item.getProductEntryId(), item.getProductName()));
            } else {
                CompanyEntryNode endNode = new CompanyEntryNode();
                endNode.setCompanyEntryId(item.getCompanyEntryId());
                endNode.setName(item.getCompanyName());
                CompanyEntryNode save = companyEntryRepository.save(endNode);
                supplyRelationship.setEndNode(save);
                productionDtoList.add(new ProductionDto(endNode.getCompanyEntryId(), item.getProductEntryId(), item.getProductName()));
            }
            supplyRelationshipList1.add(supplyRelationship);
        }
        supplyRelationshipRepository.saveAll(supplyRelationshipList1);
        addProduction(productionDtoList);
    }


    /**
     * ??????????????????
     *
     * @param productionDtoList
     */
    public void addProduction(List<ProductionDto> productionDtoList) {
        if (CollectionUtils.isEmpty(productionDtoList)) {
            return;
        }
        List<ProductionRelationship> productionRelationshipList = new ArrayList<>();
        productionDtoList = productionDtoList.stream().distinct().collect(Collectors.toList());
        ProductionRelationship productionRelationship1;
        for (ProductionDto productionDto : productionDtoList) {
            productionRelationship1 = new ProductionRelationship();
            String companyId = productionDto.getCompanyId();
            String productId = productionDto.getProductId();
            String name = productionDto.getProductName();
            //??????????????????????????????????????????
            ProductionRelationship productionRelationship = productionRelationshipRepository.findProductionRelationship(companyId, productId, RelationshipType.PRODUCTION);
            if (productionRelationship != null) {
                continue;
            }
            Optional<CompanyEntryNode> byId = companyEntryRepository.findById(companyId);
            Optional<ProductEntryNode> byId1 = productEntryRepository.findById(productId);
            if (!byId1.isPresent()) {
                ProductEntryNode productEntryNode = new ProductEntryNode();
                productEntryNode.setProductEntryId(productId);
                productEntryNode.setName(name);
                ProductEntryNode save = productEntryRepository.save(productEntryNode);
                productionRelationship1.setEndNode(save);
            } else {
                productionRelationship1.setEndNode(byId1.get());
            }
            productionRelationship1.setUuid(UuidUtils.generate());
            productionRelationship1.setStartNode(byId.get());
            productionRelationshipList.add(productionRelationship1);
        }
        if (!CollectionUtils.isEmpty(productionRelationshipList)) {
            productionRelationshipRepository.saveAll(productionRelationshipList);
        }
    }

    /**
     * ??????????????????
     *
     * @param entryInfoSourceEntityList
     * @param entryId
     */
    private void modifyEntryInfoSource(List<EntryInfoSourceEntity> entryInfoSourceEntityList, String entryId) {
        entryInfoSourceService.deleteEntryInfoSource(entryId);
        if (!CollectionUtils.isEmpty(entryInfoSourceEntityList)) {
            entryInfoSourceEntityList.forEach(a -> {
                a.setEntryId(entryId);
            });
            entryInfoSourceService.addEntryInfoSource(entryInfoSourceEntityList);
        }
    }

    /**
     * ??????????????????
     *
     * @param entryLinkEntityList
     * @param entryId
     */
    private void modifyEntryLink(List<EntryLinkEntity> entryLinkEntityList, String entryId) {
        entryLinkService.deleteEntryLinkList(entryId);
        if (!CollectionUtils.isEmpty(entryLinkEntityList)) {
            entryLinkEntityList.forEach(a -> {
                a.setEntryId(entryId);
            });
            entryLinkService.addEntryLinkList(entryLinkEntityList);
        }
    }

}
