package com.abcft.industrygraphmanagement.util;

import com.abcft.industrygraphmanagement.model.dto.FtpServiceInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Created by YangMeng on 2021/3/8 11:49
 */
@Slf4j
public class FtpUtil {


    /**
     * 上传文件到ftp服务器
     *
     * @param fileName
     * @param inputStream
     * @param ftpServiceInfoDto
     * @return
     */
    public static String uploadFile(String fileName, InputStream inputStream, FtpServiceInfoDto ftpServiceInfoDto) {
        String result = "";
        FTPClient ftpClient = initClient(ftpServiceInfoDto);
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UuidUtils.generate() + suffix;
        try {
            Date currentDate = new Date();
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
            String path = "u-data/";
            if (".pdf".equalsIgnoreCase(suffix)) {
                path += "ftp/pdf/" + dateStr + "/";
            } else {
                path += "ftp/img/" + dateStr + "/";
            }
            boolean flag = ftpClient.changeWorkingDirectory(path);
            if (!flag) {
                String[] dirs = path.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        if (!ftpClient.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (!ftpClient.storeFile(newFileName, inputStream)) {
                log.error("上传文件失败");
                return result;
            }
            result = path + newFileName;
            result = "/" + result.replaceAll("/", "_");
        } catch (Exception e) {
            log.error("uploadFile fail:{}", e);
        } finally {
            try {
                inputStream.close();
                ftpClient.logout();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                log.error("close stream fail:{}", e);
            }
        }
        return result;
    }

    /**
     * 下载文件
     *
     * @param ftpServiceInfoDto
     * @param filePath
     * @return
     */
    public static boolean downloadFile(FtpServiceInfoDto ftpServiceInfoDto, String filePath, String loadPath) {
        boolean result = false;
        FTPClient ftpClient = initClient(ftpServiceInfoDto);
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        String name = filePath.substring(filePath.lastIndexOf("/") + 1);
        try {
            ftpClient.changeWorkingDirectory(path);// 转移到FTP服务器目录

            File localFile = new File(loadPath + name);
            OutputStream outputStream = new FileOutputStream(localFile);
            ftpClient.retrieveFile(name, outputStream);
            outputStream.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ioe) {
            }
        }
        return result;
    }

    /**
     * 获取数据流
     *
     * @param ftpServiceInfoDto
     * @param filePath
     * @return
     */
    public static InputStream getResource(FtpServiceInfoDto ftpServiceInfoDto, String filePath) {
        FTPClient ftpClient = initClient(ftpServiceInfoDto);
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        String name = filePath.substring(filePath.lastIndexOf("/") + 1);
        InputStream inputStream = null;
        try {
            boolean b = ftpClient.changeWorkingDirectory(path);// 转移到FTP服务器目录
            if (!b) {
                return null;
            }
            inputStream = ftpClient.retrieveFileStream(name);
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.logout();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ioe) {
            }
        }
        return inputStream;
    }

    /**
     * 初始化
     *
     * @return
     */
    public static FTPClient initClient(FtpServiceInfoDto ftpServiceInfoDto) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpServiceInfoDto.getIp(), ftpServiceInfoDto.getPort());
            ftpClient.setRemoteVerificationEnabled(false);
            ftpClient.login(ftpServiceInfoDto.getUsername(), ftpServiceInfoDto.getPwd());
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                log.error("connect failed...ftp服务器:" + ftpServiceInfoDto.getIp() + ftpServiceInfoDto.getPort());
            }
        } catch (Exception e) {
            log.error("connect ftp failed...{}", e);
        }
        return ftpClient;
    }


}
