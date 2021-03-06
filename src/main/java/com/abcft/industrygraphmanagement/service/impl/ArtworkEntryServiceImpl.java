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
     * ???????????????????????????
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
        //??????
        List<ProductToProductDto> productToProductDtoList = new ArrayList<>();
        industryEntryService.getProductToProductList(Arrays.asList(artworkEntryId), productToProductDtoList);
        artworkEntryCondition.setProductToProductDtoList(productToProductDtoList);
        return artworkEntryCondition;
    }

    /**
     * ?????????????????????
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
        //??????????????????
        String dateStr = DateExtUtils.getCurrentDateStr();
        artworkEntryNode.setModifyTime(dateStr);
        if (StringUtils.isEmpty(artworkEntryNode.getCreateTime())) {
            artworkEntryNode.setCreateTime(dateStr);
        }
        //TODO ????????????
        artworkEntryNode.setVersion("0.2");
        ArtworkEntryNode artworkEntryNode1 = artworkEntryRepository.save(artworkEntryNode);
        List<ProductToProductDto> productToProductDtoList = artworkEntryCondition.getProductToProductDtoList();
        //???????????????????????????
        artworkEntryRepository.deleteArtworkRelation(artworkEntryId);
        //???????????????????????????????????????
        updateProductEntry(artworkEntryNode1);
        if (!CollectionUtils.isEmpty(productToProductDtoList)) {
            //????????????
            industryEntryService.createProductRelationship(productToProductDtoList);
        }
        //????????????????????????
        productService.updateArtworkStatusById(artworkEntryId);
        return true;
    }

    /**
     * ???????????????????????????????????????????????????
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
            productEntryNode.setName(artworkEntryNode.getName().replaceAll("???????????????", ""));
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
     * ?????????????????????????????????
     *
     * @param productEntryId
     * @return
     */
    @Override
    public List<IndustryToCompanyDto> getArtworkGraphList(String productEntryId, int level) throws Exception {
        if (StringUtils.isEmpty(productEntryId)) {
            throw new Exception("????????????");
        }
        List<IndustryToCompanyDto> industryToCompanyDtoList = new ArrayList<>();
        //?????????????????????
        List<String> industryEntryIds = artworkEntryRepository.getArtworkEntryIds(productEntryId);
        industryEntryService.setIndustryGraph(industryEntryIds, industryToCompanyDtoList, false, level);
        //????????????
        return industryToCompanyDtoList;
    }

    /**
     * ??????????????????????????????
     *
     * @param productEntryId
     */
    @Override
    public List<ArtworkIndustryDto> getArtworkIndustryGraph(String productEntryId,int level) throws Exception {
        List<ArtworkIndustryDto> list = new ArrayList<>();
        //??????????????????id?????????????????????????????????
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
            //???????????????id???productEntryId???????????????????????????id
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
