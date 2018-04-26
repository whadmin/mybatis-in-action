package cn.lxj.bookmybatis.chapter1.utils;

import cn.lxj.bookmybatis.chapter1.mapper.RoleMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * MybatisUtil
 * description TODO
 * create by lxj 2018/4/26
 **/
public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory = null;
    public static SqlSessionFactory getSqlSessionFactory(){
        InputStream inputStream = null;
        if (sqlSessionFactory == null){
            try {
                String resource = "chapter1/mybatis.cfg.xml";
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource));
//                sqlSessionFactory.getConfiguration().addMapper(RoleMapper.class);
                return  sqlSessionFactory;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return sqlSessionFactory;
    }
}
