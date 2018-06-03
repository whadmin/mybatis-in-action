package com.mybatis.wuhao.mybatis.utils;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.mybatis.wuhao.mybatis.entity.SysUser;
import com.mybatis.wuhao.mybatis.mapper.SysUserMapper1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import jdk.internal.dynalink.support.NameCodec;


public class SqlSessionFactoryUtil {
    // SqlSessionFactory对象
    private static SqlSessionFactory sqlSessionFactory = null;
    // 类线程锁
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;
    private static Logger log = Logger.getLogger(SqlSessionFactoryUtil.class.getName());

    /**
     * 私有化构造参数
     */
    private SqlSessionFactoryUtil() {
    }

    /**
     * 通过mybatis配置文件生成SqlSessionFactory
     *
     * @return
     */
    public static SqlSessionFactory initSqlSessionFactory() {
    	 InputStream cfgtStream = null;
         Reader cfgReader = null;

         InputStream proStream = null;
         Reader proReader = null;

         Properties properties = null;

         String resource = "mybatis/mybatis-config.xml";
         try {
             // 读入配置文件流
             cfgtStream = Resources.getResourceAsStream(resource);
             cfgReader = new InputStreamReader(cfgtStream);

             // 读入属性文件
//             proStream = Resources.getResourceAsStream("jdbc.properties");
//             proReader = new InputStreamReader(proStream);
//             properties = new Properties();
//             properties.load(proReader);

         } catch (IOException e) {
             log.log(Level.SEVERE, null, e);
         }
         synchronized (CLASS_LOCK) {
             if (sqlSessionFactory == null) {
                 sqlSessionFactory = new SqlSessionFactoryBuilder().build(cfgReader, properties);
             }
         }
         return sqlSessionFactory;
    }
    
	/**
	 * 硬编码生成SqlSessionFactory
	 * 
	 * @return
	 */
	public static SqlSessionFactory initSqlSessionFactory1() {
		/** <configuration> 标签硬编码 **/
		Configuration configuration = new Configuration();
		
		/** <environments> 标签硬编码 **/
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mybatis-plus";
		String username = "root";
		String password = "";
		DataSource dataSource = new PooledDataSource(driver, url, username,
				password);
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development",
				transactionFactory, dataSource);
		configuration.setEnvironment(environment);
		
		/** <mappers> 标签硬编码 **/
		configuration.addMapper(SysUserMapper1.class);
		
		
		configuration.getTypeHandlerRegistry().register(com.mybatis.wuhao.mybatis.enums.TypeEnum.class, org.apache.ibatis.type.EnumOrdinalTypeHandler.class);
		configuration.getTypeHandlerRegistry().register(com.mybatis.wuhao.mybatis.enums.DeleteEnum.class, org.apache.ibatis.type.EnumTypeHandler.class);
		
		
		/** <mappers> 标签硬编码 **/
		configuration.getTypeAliasRegistry().registerAlias("sysUser", SysUser.class);
		
        synchronized (CLASS_LOCK) {
            if (sqlSessionFactory == null) {
            	sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
            }
        }
        return sqlSessionFactory;
	}

    /**
     * 获取SqlSession
     *
     * @return
     */
    public static SqlSession openSqlSession() {
        if (sqlSessionFactory == null) {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
