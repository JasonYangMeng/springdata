package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.ArtworkEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ArtworkRelationshipRepository;
import com.abcft.industrygraphmanagement.dao.neo.IndustryEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductEntryRepository;
import com.abcft.industrygraphmanagement.model.condition.ArtworkEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.ArtworkIndustryDto;
import com.abcft.industrygraphmanagement.model.dto.IndustryToCompanyDto;
import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;
import com.abcft.industrygraphmanagement.model.node.ArtworkEntryNode;
import com.abcft.industrygraphmanagement.model.node.ArtworkRelationship;
import com.abcft.industrygraphmanagement.model.node.IndustryEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.service.ArtworkEntryService;
import com.abcft.industrygraphmanagement.service.IndustryEntryService;
import com.abcft.industrygraphmanagement.service.ProductService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author Created by YangMeng on 2021/3/24 10:28
 */
@Service
public class ArtworkEntryServiceImpl implements ArtworkEntryService {

    @Autowired
    private ArtworkRelationshipRepository artworkRelationshipRepository;

    @Autowired
    private ArtworkEntryRepository artworkEntryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private IndustryEntryService industryEntryService;

    @Autowired
    private IndustryEntryRepository industryEntryRepository;

    /**
     * 获取生产工艺图信息
     *
     * @param artworkEntryId
     * @return
     */
    @Override
    public ArtworkEntryCondition getArtworkEntryList(String artworkEntryId) {
        ArtworkEntryCondition artworkEntryCondition = new ArtworkEntryCondition();
        Optional<ArtworkEntryNode> byId = artworkEntryRepository.findById(artworkEntryId);
        if (byId.isPresent()) {
            ArtworkEntryNode entryNode = byId.get();
            artworkEntryCondition.setArtworkEntryNode(entryNode);
        }
        //获取
        List<ProductToProductDto> productToProductDtoList = new ArrayList<>();
        industryEntryService.getProductToProductList(Arrays.asList(artworkEntryId), productToProductDtoList);
        artworkEntryCondition.setProductToProductDtoList(productToProductDtoList);
        return artworkEntryCondition;
    }

    /**
     * 编辑生产工艺图
     *
     * @param artworkEntryCondition
     * @return
     */
    @Override
    public boolean updateArtworkEntry(ArtworkEntryCondition artworkEntryCondition) {
        ArtworkEntryNode artworkEntryNode = artworkEntryCondition.getArtworkEntryNode();
        String artworkEntryId = artworkEntryNode.getArtworkEntryId();
        if (StringUtils.isEmpty(artworkEntryId)) {
            return false;
        }
        //修改基本信息
        String dateStr = DateExtUtils.getCurrentDateStr();
        artworkEntryNode.setModifyTime(dateStr);
        if (StringUtils.isEmpty(artworkEntryNode.getCreateTime())) {
            artworkEntryNode.setCreateTime(dateStr);
        }
        //TODO 版本叠加
        artworkEntryNode.setVersion("0.2");
        ArtworkEntryNode artworkEntryNode1 = artworkEntryRepository.save(artworkEntryNode);
        List<ProductToProductDto> productToProductDtoList = artworkEntryCondition.getProductToProductDtoList();
        //删除工艺图下的关系
        artworkEntryRepository.deleteArtworkRelation(artworkEntryId);
        //创建工艺对应产品的工艺关系
        updateProductEntry(artworkEntryNode1);
        if (!CollectionUtils.isEmpty(productToProductDtoList)) {
            //添加关系
            industryEntryService.createProductRelationship(productToProductDtoList);
        }
        //修改产品创建状态
        productService.updateArtworkStatusById(artworkEntryId);
        return true;
    }

    /**
     * 创建工艺图对应产品的生产工艺图关系
     */
    private void updateProductEntry(ArtworkEntryNode artworkEntryNode) {
        String artworkEntryId = artworkEntryNode.getArtworkEntryId();
        if (artworkRelationshipRepository.existsRelationship(artworkEntryId)) {
            return;
        }

        ProductEntryNode save;
        if (!productEntryRepository.existsById(artworkEntryId)) {
            ProductEntryNode productEntryNode = new ProductEntryNode();
            productEntryNode.setProductEntryId(artworkEntryId);
            productEntryNode.setName(artworkEntryNode.getName().replaceAll("生产工艺图", ""));
            save = productEntryRepository.save(productEntryNode);
        } else {
            Optional<ProductEntryNode> byId = productEntryRepository.findById(artworkEntryId);
            save = byId.get();
        }
        ArtworkRelationship artworkRelationship = new ArtworkRelationship();
        artworkRelationship.setStartNode(save);
        artworkRelationship.setEndNode(artworkEntryNode);
        artworkRelationship.setUuid(UuidUtils.generate());
        artworkRelationshipRepository.save(artworkRelationship);
    }

    /**
     * 获取生产工艺图族谱数据
     *
     * @param productEntryId
     * @return
     */
    @Override
    public List<IndustryToCompanyDto> getArtworkGraphList(String productEntryId, int level) throws Exception {
        if (StringUtils.isEmpty(productEntryId)) {
            throw new Exception("参数异常");
        }
        List<IndustryToCompanyDto> industryToCompanyDtoList = new ArrayList<>();
        //查找所有工艺图
        List<String> industryEntryIds = artworkEntryRepository.getArtworkEntryIds(productEntryId);
        industryEntryService.setIndustryGraph(industryEntryIds, industryToCompanyDtoList, false, level);
        //返回结果
        return industryToCompanyDtoList;
    }

    /**
     * 获取工艺图产业链族谱
     *
     * @param productEntryId
     */
    @Override
    public List<ArtworkIndustryDto> getArtworkIndustryGraph(String productEntryId,int level) throws Exception {
        List<ArtworkIndustryDto> list = new ArrayList<>();
        //根据产品单元id查找终端产品所属产业链
        List<String> industryEntryIds = artworkEntryRepository.getIndustryEntryIds(productEntryId);
        ArtworkIndustryDto artworkIndustryDto;
        for (String id : industryEntryIds) {
            artworkIndustryDto = new ArtworkIndustryDto();
            Optional<IndustryEntryNode> byId = industryEntryRepository.findById(id);
            if (!byId.isPresent()) {
                continue;
            }
            artworkIndustryDto.setIndustryEntryId(id);
            artworkIndustryDto.setIndustryName(byId.get().getName());
            //根据产业链id和productEntryId查询对应的终端产品id
            List<String> artworkEntryIds = artworkEntryRepository.getArtworkEntryIds(productEntryId, id);
            List<IndustryToCompanyDto> industryToCompanyDtoList = new ArrayList<>();
            for (String artworkEntryId : artworkEntryIds) {
                industryToCompanyDtoList.addAll(getArtworkGraphList(artworkEntryId,level));
            }
            artworkIndustryDto.setIndustryToCompanyDtoList(industryToCompanyDtoList);
            list.add(artworkIndustryDto);
        }

        return list;
    }
}
