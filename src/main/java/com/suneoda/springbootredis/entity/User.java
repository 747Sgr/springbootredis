package com.suneoda.springbootredis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author shiguorang
 * @date 2021-04-21 18:36:18
 */
@Data
public class User implements Serializable {

    private String name;

    private String sex;

    private Integer age;
}
