package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysIndustryMapper;
import com.abcft.industrygraphmanagement.model.entity.SysIndustryEntity;
import com.abcft.industrygraphmanagement.service.SysIndustryService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.POIUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <>
 * <功能描述>
 *
 * @date 2021/3/9
 */
@Service
@Slf4j
public class SysIndustryServiceImpl implements SysIndustryService {
    private static final String FILE_NAME = "file";
    private static final String CONTENT_TYPE = "text/plain";
    private static final String FIRST_LEVEL = "1";
    private static final String SECOND_LEVEL = "2";
    private static final String THIRD_LEVEL = "3";

    @Autowired
    private SysIndustryMapper sysIndustryMapper;

    /**
     * 通过行业excel模板文件导入行业数据
     *
     * @param filePath 行业excel模板文件路径
     */
    public void addIndustryList(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream input = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, file.getName(), CONTENT_TYPE, IOUtils.toByteArray(input));
            POIUtils.checkFile(multipartFile);
            ArrayList<SysIndustryEntity> sysIndustryList = new ArrayList<>();
            List<String[]> strings = POIUtils.readExcel(multipartFile);

            for (int i = 0; i < strings.size(); i++) {
                String[] arr = strings.get(i);
                String[] parentIdAndLevel = getParentIdAndLevel(arr);
                String parentId = parentIdAndLevel[0];
                String level = parentIdAndLevel[1];
                SysIndustryEntity sysIndustryEntity = getSysIndustryEntity(arr, parentId, level);
                sysIndustryList.add(sysIndustryEntity);
            }
            sysIndustryMapper.addIndustryList(sysIndustryList);
        } catch (Exception e) {
            log.error("addIndustryList -> exception:", e);
        }
    }

    /**
     * 查询所有行业信息
     *
     * @return 所有行业信息 (三级行业目录）
     */
    @Override
    public List<SysIndustryEntity> querySysIndustryList() {
        // 根行业list
        List<SysIndustryEntity> rootList = sysIndustryMapper.queryIndustryByLevel(FIRST_LEVEL);
        // 子行业list
        List<SysIndustryEntity> bodyList = sysIndustryMapper.queryIndustryByLevels(SECOND_LEVEL, THIRD_LEVEL);

        return this.getTree(rootList, bodyList);
    }

    /**
     * 根据行业名称模糊查询行业信息
     *
     * @param name 行业名称
     * @return 行业信息
     */
    @Override
    public List<SysIndustryEntity> querySysIndustryListByName(String name) {
        return sysIndustryMapper.querySysIndustryListByName(name);
    }

    /**
     * 根据行业名称和行业级别模糊查询行业信息
     *
     * @param name 行业名称
     * @param level 行业级别
     * @return 行业信息
     */
    @Override
    public List<SysIndustryEntity> querySysIndustryListByNameAndLevel(String name, String level) {
        int i = Integer.parseInt(level) -1;
        level = String.valueOf(i);
        return sysIndustryMapper.querySysIndustryListByNameAndLevel(name, level);
    }

    @Override
    public SysIndustryEntity querySysIndustryById(String id) {
        return sysIndustryMapper.querySysIndustryById(id);
    }

    @Override
    public List<SysIndustryEntity> queryParentTradeList(String level) {
        return sysIndustryMapper.queryIndustryByLevel(level);
    }

    /**
     * 获取封装好的行业树结构
     *
     * @param rootList 根行业list
     * @param bodyList 子行业list
     * @return
     */
    private List<SysIndustryEntity> getTree(List<SysIndustryEntity> rootList, List<SysIndustryEntity> bodyList) {
        if (bodyList != null && !bodyList.isEmpty()) {
            //声明一个map，用来过滤已操作过的数据
            Map<String, String> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree, map, bodyList));
            return rootList;
        }

        return null;
    }

    /**
     * 获取子行业
     *
     * @param treeDto
     * @param map
     * @param bodyList
     */
    public void getChild(SysIndustryEntity treeDto, Map<String, String> map, List<SysIndustryEntity> bodyList) {
        List<SysIndustryEntity> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getId()))
                .filter(c -> c.getParentId().equals(treeDto.getId()))
                .forEach(c -> {
                    map.put(c.getId(), c.getParentId());
                    // 再次获取子行业
                    getChild(c, map, bodyList);
                    childList.add(c);
                });
        // 设置子行业list
        treeDto.setSysIndustryList(childList);
    }


    /**
     * 封装行业实体类
     *
     * @param arr      表格每行数据封装成的字符串数组
     * @param parentId 父id
     * @param level    行业级别 1:一级 2:二级 3:三级
     * @return SysIndustryEntity 行业实体类
     */
    private SysIndustryEntity getSysIndustryEntity(String[] arr, String parentId, String level) {
        // 行业编码
        String code = arr[0];
        // 行业名称
        String value = arr[1];
        SysIndustryEntity entity = new SysIndustryEntity();
        entity.setId(UuidUtils.generate());
        entity.setParentId(parentId);
        entity.setLevel(level);
        entity.setCode(code);
        entity.setValue(value);
        entity.setCreateTime(DateExtUtils.getCurrentDateStr());
        return entity;
    }

    /**
     * 获取父id和行业级别
     *
     * @param arr 表格每行数据封装的字符串数组
     * @return String[] 父id和行业级别
     */
    //TODO 方法需要优化，减少不必要的数据库交互次数
    private String[] getParentIdAndLevel(String[] arr) {
        String[] parentIdAndLevel = new String[2];
        String parentId = "";
        String code = arr[0];
        // 编码末尾2位数字
        String str1 = code.substring(4, 6);
        // 编码末尾4位数字
        String str2 = code.substring(2, 6);
        // 二级目录
        if ("00".equals(str1) && !"0000".equals(str2)) {
            String firstCode = code.substring(0, 2) + "0000";
            parentId = sysIndustryMapper.queryParentId(firstCode);
            parentIdAndLevel[0] = parentId;
            parentIdAndLevel[1] = SECOND_LEVEL;
            return parentIdAndLevel;
        }
        // 三级目录
        if (!"00".equals(str1)) {
            String secondCode = code.substring(0, 4) + "00";
            parentId = sysIndustryMapper.queryParentId(secondCode);
            parentIdAndLevel[0] = parentId;
            parentIdAndLevel[1] = THIRD_LEVEL;
            return parentIdAndLevel;
        }
        // 一级目录
        parentIdAndLevel[0] = "0";
        parentIdAndLevel[1] = FIRST_LEVEL;
        return parentIdAndLevel;
    }
}
