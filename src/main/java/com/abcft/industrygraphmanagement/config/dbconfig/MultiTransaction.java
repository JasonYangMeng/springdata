package com.abcft.industrygraphmanagement.config.dbconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Created by YangMeng on 2021/3/16 11:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiTransaction {
    //这里可以自定义参数
}
