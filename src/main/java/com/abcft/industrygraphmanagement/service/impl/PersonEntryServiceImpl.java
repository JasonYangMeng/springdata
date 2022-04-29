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

    // 词条创建状态为1表示已创建
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
     * 创建人物词条
     *
     * @param personEntryResult 创建人物词条入参对象
     */
    @MultiTransaction
    @Override
    public void createPersonEntry(PersonEntryResultDto personEntryResult) throws Exception {
        // 人物词条节点
        PersonEntryNode personEntryNode = personEntryResult.getPersonEntryNode();
        String dateStr = DateExtUtils.getCurrentDateStr();
        String currentUserId = UserContext.getCurrentUserId();
        personEntryNode.setCreateUserId(currentUserId);
        // 词条来源列表
        List<EntryInfoSourceEntity> infoSourceList = personEntryResult.getEntryInfoSourceEntityList();
        // 词条链接列表
        List<EntryLinkEntity> linkList = personEntryResult.getEntryLinkEntityList();
        // 任职关系列表
        List<PersonEntryPostDto> postList = personEntryResult.getPostList();
        // 人物投资关系列表
        List<PersonEntryInvestDto> investmentList = personEntryResult.getInvestmentList();
        // mysql中人物表的人物id
        String personId = personEntryNode.getPersonEntryId();
        PersonEntity personEntity = new PersonEntity();
        if (StringUtils.isEmpty(personId)) {
            // 入参人物id为空，说明mysql人物表中没有查到该人物
            personEntryNode.setPersonEntryId(UuidUtils.generate(NameType.PERSON));
            personEntryNode.setCreateTime(dateStr);
            BeanUtils.copyProperties(personEntryNode, personEntity);
            // 在mysql人物表中新建该人物
            personService.savePerson(personEntity);
            personId = personEntryNode.getPersonEntryId();
        }
        // 保存人物词条
        personEntryRepository.save(personEntryNode);

        // 保存词条来源列表
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

        // 保存词条链接列表
        if (!CollectionUtils.isEmpty(linkList)) {
            List<EntryLinkEntity> linkList1 = linkList.stream().map(c -> {
                c.setEntryId(finalPersonId);
                c.setCreateTime(dateStr);
                c.setCreateUserId(currentUserId);
                return c;
            }).collect(Collectors.toList());
            linkService.addEntryLinkList(linkList1);
        }

        // 删除人物->公司任职关系
        List<PostRelationship> postRelationshipList = postRepository.findPostRelationshipById(personId);
        if (!CollectionUtils.isEmpty(postRelationshipList)) {
            // 根据入参任职关系，删除Neo4j中对应的人物任职关系
            postRepository.deleteAll(postRelationshipList);
        }
        // 创建人物-公司任职关系
        if (!CollectionUtils.isEmpty(postList)) {
            for (PersonEntryPostDto post : postList) {
                String companyEntryId = post.getId();
                String companyEntryName = post.getName();
                CompanyEntryNode companyEntryNode =
                        this.checkExistsCompanyEntryNode(companyEntryId, companyEntryName);
                // 创建人物任职关系
                PostRelationship postRelationship = new PostRelationship();
                postRelationship.setUuid(UuidUtils.generate(NameType.RELATION));
                postRelationship.setStartNode(personEntryNode);
                postRelationship.setEndNode(companyEntryNode);
                postRelationship.setPost(post.getPost());
                postRelationship.setShareholdingRatio(post.getShareholdingRatio());
                postRepository.save(postRelationship);
            }
        }

        // 删除人物->公司投资关系
        List<PersonInvestmentRelationship> personInvestmentRelationshipList =
                personInvestmentRepository.findPersonInvestmentRelationshipById(personId);
        if (!CollectionUtils.isEmpty(personInvestmentRelationshipList)) {
            // 根据入参投资关系，删除Neo4j中对应的人物->公司投资关系
            personInvestmentRepository.deleteAll(personInvestmentRelationshipList);
        }
        // 创建人物-公司投资关系
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
        // 人物词条创建成功后，更新mysql人物表的词条状态为已创建
        personEntity.setCreateEntry(CREATED_ENTRY);
        personService.updatePerson(personEntity);
    }

    /**
     * 检查是否存在公司词条，返回公司词条
     *
     * @param companyEntryId   公司词条id
     * @param companyEntryName 公司词条名称
     * @return CompanyEntryNode
     */
    private CompanyEntryNode checkExistsCompanyEntryNode(String companyEntryId, String companyEntryName) throws Exception {
        String dateStr = DateExtUtils.getCurrentDateStr();
        if (StringUtils.isEmpty(companyEntryId) && !StringUtils.isEmpty(companyEntryName)) {
            // 如果入参的公司id为空, 公司名称不为空，则在mysql中新建该公司，状态为待审核
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
                // 如果不存在公司词条id，则创建公司词条
                companyEntryNode.setCompanyEntryId(companyEntryId);
                companyEntryNode.setName(companyEntryName);
                companyEntryNode.setCreateTime(dateStr);
                companyEntryRepository.save(companyEntryNode);
            } else throw new Exception("公司名称不能为空");
        } else {
            companyEntryNode = companyEntryRepository.findById(companyEntryId).get();
        }

        return companyEntryNode;
    }

    /**
     * 根据人物词条id来回显创建人物词条数据
     *
     * @param personEntryId 人物词条id
     * @return 回显数据 PersonEntryParamDto
     */
    @Override
    public PersonEntryResultDto queryPersonEntrySource(String personEntryId) {
        PersonEntryResultDto personEntryResult = new PersonEntryResultDto();
        Optional<PersonEntryNode> byId = personEntryRepository.findById(personEntryId);
        if (!byId.isPresent()) {
            return personEntryResult;
        }
        // 人物词条基本信息
        PersonEntryNode personEntryNode = byId.get();

        // 词条来源列表
        List<EntryInfoSourceEntity> infoSourceList = infoSourceService.queryAllByEntryId(personEntryId);

        // 词条链接列表
        List<EntryLinkEntity> linkList = linkService.queryEntryLinkList(personEntryId);

        // 任职公司列表
        List<PostRelationship> postRelationshipList = postRepository.findPostRelationshipById(personEntryId);
        List<PersonEntryPostDto> postList = postRelationshipList.stream().map(c -> {
            PersonEntryPostDto personEntryPost = new PersonEntryPostDto();
            personEntryPost.setId(c.getEndNode().getCompanyEntryId());
            personEntryPost.setName(c.getEndNode().getName());
            personEntryPost.setPost(c.getPost());
            personEntryPost.setShareholdingRatio(c.getShareholdingRatio());
            return personEntryPost;
        }).collect(Collectors.toList());

        // 投资公司列表
        List<PersonInvestmentRelationship> investmentRelationshipList =
                personInvestmentRepository.findPersonInvestmentRelationshipById(personEntryId);
        List<PersonEntryInvestDto> investmentList = investmentRelationshipList.stream().map(c -> {
            PersonEntryInvestDto personEntryInvest = new PersonEntryInvestDto();
            personEntryInvest.setId(c.getEndNode().getCompanyEntryId());
            personEntryInvest.setName(c.getEndNode().getName());
            personEntryInvest.setInvestmentProportion(c.getInvestmentProportion());
            return personEntryInvest;
        }).collect(Collectors.toList());

        // 封装回显数据
        personEntryResult.setPersonEntryNode(personEntryNode);
        personEntryResult.setEntryInfoSourceEntityList(infoSourceList);
        personEntryResult.setEntryLinkEntityList(linkList);
        personEntryResult.setPostList(postList);
        personEntryResult.setInvestmentList(investmentList);

        return personEntryResult;
    }
}
