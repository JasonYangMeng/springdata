package com.abcft.industrygraphmanagement.service;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @Author Created by YangMeng on 2021/3/8 13:56
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    String uploadFile(InputStream inputStream, String fileName);

    /**
     * 下载文件
     *
     * @param filePath ftp文件路径
     * @param loadPath 下载后保存路径
     * @return
     */
    boolean downloadFile(String filePath, String loadPath);

    /**
     * 获取文件
     *
     * @param filePath
     */
    void getFile(String filePath, HttpServletResponse response);
}
