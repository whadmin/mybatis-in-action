package cn.lxj.bookmybatis.chapter1;

import cn.lxj.bookmybatis.chapter1.mapper.RoleMapper;
import cn.lxj.bookmybatis.chapter1.entity.Role;
import cn.lxj.bookmybatis.chapter1.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * MyBatisExample
 * description
 * create by lxj 2018/4/26
 **/
public class MyBatisExample {
    public static void main(String[] args) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            Role role = mapper.selectRoleById(1L);
            System.out.println("role_name==>" + role.getRoleName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}