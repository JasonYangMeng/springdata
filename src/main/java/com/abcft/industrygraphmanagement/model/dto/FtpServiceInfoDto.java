package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/8 15:57
 */
@Data
public class FtpServiceInfoDto {
    private String ip;
    private int port;
    private String username;
    private String pwd;

    public FtpServiceInfoDto(String ip, int port, String username, String pwd) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.pwd = pwd;
    }
}
