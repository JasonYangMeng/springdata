package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.model.dto.FtpServiceInfoDto;
import com.abcft.industrygraphmanagement.service.FileService;
import com.abcft.industrygraphmanagement.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author Created by YangMeng on 2021/3/8 14:02
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.ip}")
    private String ip;
    @Value("${file.port}")
    private int port;
    @Value("${file.username}")
    private String username;
    @Value("${file.pwd}")
    private String pwd;

    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        FtpServiceInfoDto ftpServiceInfoDto = new FtpServiceInfoDto(ip, port, username, pwd);
        return FtpUtil.uploadFile(fileName, inputStream, ftpServiceInfoDto);
    }

    /**
     * 下载文件
     *
     * @param filePath ftp文件路径
     * @param loadPath 下载后保存路径
     * @return
     */
    @Override
    public boolean downloadFile(String filePath, String loadPath) {
        FtpServiceInfoDto ftpServiceInfoDto = new FtpServiceInfoDto(ip, port, username, pwd);
        return FtpUtil.downloadFile(ftpServiceInfoDto, filePath, loadPath);
    }

    /**
     * 获取文件
     *
     * @param filePath
     */
    @Override
    public void getFile(String filePath, HttpServletResponse response) {
        FtpServiceInfoDto ftpServiceInfoDto = new FtpServiceInfoDto(ip, port, username, pwd);
        FTPClient ftpClient = FtpUtil.initClient(ftpServiceInfoDto);
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        String name = filePath.substring(filePath.lastIndexOf("/") + 1);
        ServletOutputStream out = null;
        InputStream inputStream = null;
        try {
            boolean b = ftpClient.changeWorkingDirectory(path);// 转移到FTP服务器目录
            if (!b) {
                return;
            }
            out = response.getOutputStream();
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            inputStream = ftpClient.retrieveFileStream(name);
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = inputStream.read(bytes, 0, 1024)) > 0) {
                out.write(bytes, 0, i);
            }
            out.flush();
        } catch (IOException e) {
            log.error("读取不出图片:{}", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    log.error("关闭out流失败", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("关闭inputStream流失败", e);
                }
            }
            try {
                ftpClient.logout();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                log.error("关闭ftpClient失败", e);
            }
        }
    }
}
