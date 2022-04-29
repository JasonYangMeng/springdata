package com.abcft.industrygraphmanagement.config.dbconfig;

import java.lang.annotation.*;

/**
 * @author  WangZhiZhou
 * @date 2021-05-08 14:00:00
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotesValue {
    String value() default "";
}
