package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.config.dbconfig.MultiTransaction;
import com.abcft.industrygraphmanagement.dao.neo.CompanyEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.PersonEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.PersonInvestmentRelationshipRepository;
import com.abcft.industrygraphmanagement.dao.neo.PostRelationshipRepository;
import com.abcft.industrygraphmanagement.model.constant.NameType;
import com.abcft.industrygraphmanagement.model.dto.PersonEntryInvestDto;
import com.abcft.industrygraphmanagement.model.dto.PersonEntryParamDto;
import com.abcft.industrygraphmanagement.model.dto.PersonEntryPostDto;
import com.abcft.industrygraphmanagement.model.dto.PersonEntryResultDto;
import com.abcft.industrygraphmanagement.model.entity.CompanyEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.entity.PersonEntity;
import com.abcft.industrygraphmanagement.model.node.CompanyEntryNode;
import com.abcft.industrygraphmanagement.model.node.PersonEntryNode;
import com.abcft.industrygraphmanagement.model.node.PersonInvestmentRelationship;
import com.abcft.industrygraphmanagement.model.node.PostRelationship;
import com.abcft.industrygraphmanagement.service.*;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UserContext;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Service
@Slf4j
public class PersonEntryServiceImpl implements PersonEntryService {

    // ?????????????????????1???????????????
    private static final String CREATED_ENTRY = "1";

    @Autowired
    private PersonEntryRepository personEntryRepository;

    @Autowired
    private PostRelationshipRepository postRepository;

    @Autowired
    private PersonInvestmentRelationshipRepository personInvestmentRepository;

    @Autowired
    private CompanyEntryRepository companyEntryRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EntryInfoSourceService infoSourceService;

    @Autowired
    private EntryLinkService linkService;

    /**
     * ??????????????????
     *
     * @param personEntryResult ??????????????????????????????
     */
    @MultiTransaction
    @Override
    public void createPersonEntry(PersonEntryResultDto personEntryResult) throws Exception {
        // ??????????????????
        PersonEntryNode personEntryNode = personEntryResult.getPersonEntryNode();
        String dateStr = DateExtUtils.getCurrentDateStr();
        String currentUserId = UserContext.getCurrentUserId();
        personEntryNode.setCreateUserId(currentUserId);
        // ??????????????????
        List<EntryInfoSourceEntity> infoSourceList = personEntryResult.getEntryInfoSourceEntityList();
        // ??????????????????
        List<EntryLinkEntity> linkList = personEntryResult.getEntryLinkEntityList();
        // ??????????????????
        List<PersonEntryPostDto> postList = personEntryResult.getPostList();
        // ????????????????????????
        List<PersonEntryInvestDto> investmentList = personEntryResult.getInvestmentList();
        // mysql?????????????????????id
        String personId = personEntryNode.getPersonEntryId();
        PersonEntity personEntity = new PersonEntity();
        if (StringUtils.isEmpty(personId)) {
            // ????????????id???????????????mysql?????????????????????????????????
            personEntryNode.setPersonEntryId(UuidUtils.generate(NameType.PERSON));
            personEntryNode.setCreateTime(dateStr);
            BeanUtils.copyProperties(personEntryNode, personEntity);
            // ???mysql???????????????????????????
            personService.savePerson(personEntity);
            personId = personEntryNode.getPersonEntryId();
        }
        // ??????????????????
        personEntryRepository.save(personEntryNode);

        // ????????????????????????
        String finalPersonId = personId;
        if (!CollectionUtils.isEmpty(infoSourceList)) {
            List<EntryInfoSourceEntity> infoSourceList1 = infoSourceList.stream().map(c -> {
                c.setEntryId(finalPersonId);
                c.setCreateTime(dateStr);
                c.setCreateUserId(currentUserId);
                return c;
            }).collect(Collectors.toList());
            infoSourceService.addEntryInfoSource(infoSourceList1);
        }

        // ????????????????????????
        if (!CollectionUtils.isEmpty(linkList)) {
            List<EntryLinkEntity> linkList1 = linkList.stream().map(c -> {
                c.setEntryId(finalPersonId);
                c.setCreateTime(dateStr);
                c.setCreateUserId(currentUserId);
                return c;
            }).collect(Collectors.toList());
            linkService.addEntryLinkList(linkList1);
        }

        // ????????????->??????????????????
        List<PostRelationship> postRelationshipList = postRepository.findPostRelationshipById(personId);
        if (!CollectionUtils.isEmpty(postRelationshipList)) {
            // ?????????????????????????????????Neo4j??????????????????????????????
            postRepository.deleteAll(postRelationshipList);
        }
        // ????????????-??????????????????
        if (!CollectionUtils.isEmpty(postList)) {
            for (PersonEntryPostDto post : postList) {
                String companyEntryId = post.getId();
                String companyEntryName = post.getName();
                CompanyEntryNode companyEntryNode =
                        this.checkExistsCompanyEntryNode(companyEntryId, companyEntryName);
                // ????????????????????????
                PostRelationship postRelationship = new PostRelationship();
                postRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
                postRelationship.setStartNode(personEntryNode);
                postRelationship.setEndNode(companyEntryNode);
                postRelationship.setPost(post.getPost());
                postRelationship.setShareholdingRatio(post.getShareholdingRatio());
                postRepository.save(postRelationship);
            }
        }

        // ????????????->??????????????????
        List<PersonInvestmentRelationship> personInvestmentRelationshipList =
                personInvestmentRepository.findPersonInvestmentRelationshipById(personId);
        if (!CollectionUtils.isEmpty(personInvestmentRelationshipList)) {
            // ?????????????????????????????????Neo4j??????????????????->??????????????????
            personInvestmentRepository.deleteAll(personInvestmentRelationshipList);
        }
        // ????????????-??????????????????
        if (!CollectionUtils.isEmpty(investmentList)) {
            for (PersonEntryInvestDto invest : investmentList) {
                String companyEntryId = invest.getId();
                String companyEntryName = invest.getName();
                CompanyEntryNode companyEntryNode =
                        this.checkExistsCompanyEntryNode(companyEntryId, companyEntryName);
                PersonInvestmentRelationship personInvestmentRelationship = new PersonInvestmentRelationship();
                personInvestmentRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
                personInvestmentRelationship.setStartNode(personEntryNode);
                personInvestmentRelationship.setEndNode(companyEntryNode);
                personInvestmentRelationship.setInvestmentProportion(invest.getInvestmentProportion());
                personInvestmentRepository.save(personInvestmentRelationship);
            }
        }
        // ????????????????????????????????????mysql????????????????????????????????????
        personEntity.setCreateEntry(CREATED_ENTRY);
        personService.updatePerson(personEntity);
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param companyEntryId   ????????????id
     * @param companyEntryName ??????????????????
     * @return CompanyEntryNode
     */
    private CompanyEntryNode checkExistsCompanyEntryNode(String companyEntryId, String companyEntryName) throws Exception {
        String dateStr = DateExtUtils.getCurrentDateStr();
        if (StringUtils.isEmpty(companyEntryId) && !StringUtils.isEmpty(companyEntryName)) {
            // ?????????????????????id??????, ??????????????????????????????mysql???????????????????????????????????????
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setCompanyId(UuidUtils.generate(NameType.COMPANY));
            companyEntity.setName(companyEntryName);
            companyEntity.setCreateTime(dateStr);
            companyService.addCompanyEntity(companyEntity);
            companyEntryId = companyEntity.getCompanyId();
        }
        CompanyEntryNode companyEntryNode = new CompanyEntryNode();
        if (!companyEntryRepository.existsById(companyEntryId)) {
            if (!StringUtils.isEmpty(companyEntryName)) {
                // ???????????????????????????id????????????????????????
                companyEntryNode.setCompanyEntryId(companyEntryId);
                companyEntryNode.setName(companyEntryName);
                companyEntryNode.setCreateTime(dateStr);
                companyEntryRepository.save(companyEntryNode);
            } else throw new Exception("????????????????????????");
        } else {
            companyEntryNode = companyEntryRepository.findById(companyEntryId).get();
        }

        return companyEntryNode;
    }

    /**
     * ??????????????????id?????????????????????????????????
     *
     * @param personEntryId ????????????id
     * @return ???????????? PersonEntryParamDto
     */
    @Override
    public PersonEntryResultDto queryPersonEntrySource(String personEntryId) {
        PersonEntryResultDto personEntryResult = new PersonEntryResultDto();
        Optional<PersonEntryNode> byId = personEntryRepository.findById(personEntryId);
        if (!byId.isPresent()) {
            return personEntryResult;
        }
        // ????????????????????????
        PersonEntryNode personEntryNode = byId.get();

        // ??????????????????
        List<EntryInfoSourceEntity> infoSourceList = infoSourceService.queryAllByEntryId(personEntryId);

        // ??????????????????
        List<EntryLinkEntity> linkList = linkService.queryEntryLinkList(personEntryId);

        // ??????????????????
        List<PostRelationship> postRelationshipList = postRepository.findPostRelationshipById(personEntryId);
        List<PersonEntryPostDto> postList = postRelationshipList.stream().map(c -> {
            PersonEntryPostDto personEntryPost = new PersonEntryPostDto();
            personEntryPost.setId(c.getEndNode().getCompanyEntryId());
            personEntryPost.setName(c.getEndNode().getName());
            personEntryPost.setPost(c.getPost());
            personEntryPost.setShareholdingRatio(c.getShareholdingRatio());
            return personEntryPost;
        }).collect(Collectors.toList());

        // ??????????????????
        List<PersonInvestmentRelationship> investmentRelationshipList =
                personInvestmentRepository.findPersonInvestmentRelationshipById(personEntryId);
        List<PersonEntryInvestDto> investmentList = investmentRelationshipList.stream().map(c -> {
            PersonEntryInvestDto personEntryInvest = new PersonEntryInvestDto();
            personEntryInvest.setId(c.getEndNode().getCompanyEntryId());
            personEntryInvest.setName(c.getEndNode().getName());
            personEntryInvest.setInvestmentProportion(c.getInvestmentProportion());
            return personEntryInvest;
        }).collect(Collectors.toList());

        // ??????????????????
        personEntryResult.setPersonEntryNode(personEntryNode);
        personEntryResult.setEntryInfoSourceEntityList(infoSourceList);
        personEntryResult.setEntryLinkEntityList(linkList);
        personEntryResult.setPostList(postList);
        personEntryResult.setInvestmentList(investmentList);

        return personEntryResult;
    }
}
