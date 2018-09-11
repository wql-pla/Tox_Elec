package com.tox.utils;

import java.lang.annotation.*;

/**
 * Created by wHolmes on 2017/9/6.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface SystemControllerLog {

    String description()  default "";

}