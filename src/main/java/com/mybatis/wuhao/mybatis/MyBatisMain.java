package com.mybatis.wuhao.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.mybatis.wuhao.mybatis.entity.SysUser;
import com.mybatis.wuhao.mybatis.enums.DeleteEnum;
import com.mybatis.wuhao.mybatis.enums.TypeEnum;
import com.mybatis.wuhao.mybatis.mapper.SysUserMapper;
import com.mybatis.wuhao.mybatis.utils.SqlSessionFactoryUtil;

public class MyBatisMain {

	@Test
	public void testSqlMapperQuery() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtil.initSqlSessionFactory()
					.openSession();
			SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
			SysUser user = mapper.selectByPrimaryKey(11L);
			System.out.println("user==>" + user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSqlMapperInsert() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtil.initSqlSessionFactory()
					.openSession();
			SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
			SysUser user = new SysUser();
			user.setId(11L);
			user.setAge(1);
			user.setName("wuhao.w");
			user.setType(TypeEnum.DISABLED);
			user.setIsdelete(DeleteEnum.N);
			int result = mapper.insert(user);
			System.out.println(result);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testSqlSession() {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionFactoryUtil.initSqlSessionFactory1()
					.openSession();
			SysUser user = sqlSession
					.selectOne(
							"com.mybatis.wuhao.mybatis.mapper.SysUserMapper1.selectByPrimaryKey",
							11L);
			System.out.println("user==>" + user);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	/*
	 * 一级缓存: 也就Session级的缓存(默认开启)
	 * 一级缓存: 基于PerpetualCache 的 HashMap本地缓存，其存储作用域为 Session，当 Session flush 或 close 之后，该Session中的所有 Cache 就将清空。
	 */
	@Test
	public void testCache1() {
		SqlSession session = SqlSessionFactoryUtil.initSqlSessionFactory1()
				.openSession();
		String statement = "com.mybatis.wuhao.mybatis.mapper.SysUserMapper1.selectByPrimaryKey";
		SysUser user = session.selectOne(statement, 11L);
		System.out.println(user);

		/*
		 * 一级缓存默认就会被使用
		 */
		user = session.selectOne(statement, 11L);
		System.out.println(user);
		session.close();
		/*
		 * 1. 必须是同一个Session,如果session对象已经close()过了就不可能用了
		 */
		session = SqlSessionFactoryUtil.initSqlSessionFactory1()
				.openSession();
		user = session.selectOne(statement, 11l);
		System.out.println(user);

		/*
		 * 2. 查询条件是一样的
		 */
		user = session.selectOne(statement, 10l);
		System.out.println(user);

		/*
		 * 3. 执行过session.clearCache()清理缓存
		 */
		 session.clearCache();
		user = session.selectOne(statement, 10L);
		System.out.println(user);

	}
	
	
	/**
	 * 开启二级缓存，在userMapper.xml文件中添加如下配置
	 * 

<mapper namespace="me.gacl.mapping.userMapper">
<!-- 开启二级缓存 -->
<cache/>


	 */
	@Test
    public void testCache2() {
		String statement = "com.mybatis.wuhao.mybatis.mapper.SysUserMapper1.selectByPrimaryKey";;
        SqlSessionFactory factory = SqlSessionFactoryUtil.initSqlSessionFactory1();
        //开启两个不同的SqlSession
        SqlSession session1 = factory.openSession();
        SqlSession session2 = factory.openSession();
        //使用二级缓存时，User类必须实现一个Serializable接口===> User implements Serializable
        SysUser user = session1.selectOne(statement, 1);
        session1.commit();//不懂为啥，这个地方一定要提交事务之后二级缓存才会起作用
        System.out.println("user="+user);
        
        //由于使用的是两个不同的SqlSession对象，所以即使查询条件相同，一级缓存也不会开启使用
        user = session2.selectOne(statement, 1);
        //session2.commit();
        System.out.println("user2="+user);
    }
	
	
	
	@Test
    public void testCallBack(){
        SqlSession sqlSession = SqlSessionFactoryUtil.initSqlSessionFactory()
				.openSession();
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUserCount是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "com.mybatis.wuhao.mybatis.mapper.SysUserMapper1.getUserCount";//映射sql的标识字符串
        Map<String, Integer> parameterMap = new HashMap<String, Integer>();
        parameterMap.put("sexid", 1);
        parameterMap.put("usercount", -1);
        sqlSession.selectOne(statement, parameterMap);
        Integer result = parameterMap.get("usercount");
        System.out.println(result);
        sqlSession.close();
    }
}