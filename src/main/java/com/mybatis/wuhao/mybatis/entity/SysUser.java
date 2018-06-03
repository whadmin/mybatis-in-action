package com.mybatis.wuhao.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

import com.mybatis.wuhao.mybatis.enums.DeleteEnum;
import com.mybatis.wuhao.mybatis.enums.TypeEnum;

@Data
@ToString
public class SysUser implements Serializable {
    private Long id;

    private String name;

    private Integer age;

    private TypeEnum type;

    private Byte version;

    private DeleteEnum isdelete;

    private Date ctime;

    private Date utime;

    private static final long serialVersionUID = 1L;

}