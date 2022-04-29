package com.abcft.industrygraphmanagement.model.result;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/3 11:27
 */
@Data
public class WebResInfo<T> {
    private String code;
    private T data;
    private String message;

    public WebResInfo(String code) {
        this.code = code;
    }
    public WebResInfo(String code, T data, String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public WebResInfo() {

    }

    public static final WebResInfo successStruct = new WebResInfo(WebResCode.Successful);
    public static final WebResInfo parameterStruct = new WebResInfo(WebResCode.Format_Parameter);
}
