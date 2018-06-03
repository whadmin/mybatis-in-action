package com.mybatis.wuhao.mybatis.mapper;

import com.mybatis.wuhao.mybatis.entity.SysUser;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

public interface SysUserMapper1 {
    @Delete({
        "delete from sys_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sys_user (id, name, ",
        "age, type, version, ",
        "isdelete, ctime, ",
        "utime)",
        "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{age,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, #{version,jdbcType=TINYINT}, ",
        "#{isdelete,jdbcType=VARCHAR}, #{ctime,jdbcType=TIMESTAMP}, ",
        "#{utime,jdbcType=TIMESTAMP})"
    })
    int insert(SysUser record);

    @InsertProvider(type=SysUserSqlProvider.class, method="insertSelective")
    int insertSelective(SysUser record);

    @Select({
        "select",
        "id, name, age, type, version, isdelete, ctime, utime",
        "from sys_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER,typeHandler=EnumOrdinalTypeHandler.class),
        @Result(column="version", property="version", jdbcType=JdbcType.TINYINT),
        @Result(column="isdelete", property="isdelete", jdbcType=JdbcType.VARCHAR,typeHandler=EnumTypeHandler.class),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    SysUser selectByPrimaryKey(Long id);

    @UpdateProvider(type=SysUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SysUser record);

    @Update({
        "update sys_user",
        "set name = #{name,jdbcType=VARCHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "version = #{version,jdbcType=TINYINT},",
          "isdelete = #{isdelete,jdbcType=VARCHAR},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SysUser record);
}