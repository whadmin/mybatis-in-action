package cn.lxj.bookmybatis.chapter1;

import cn.lxj.bookmybatis.chapter1.entity.Role;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * JdbcExample
 * description TODO
 * create by lxj 2018/4/26
 **/
public class JdbcExample {
    /**
     * 获取数据库连接
     *
     * @return
     */
    private Connection getConnection() {

        Logger log = Logger.getLogger(JdbcExample.class);
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/migo2?zeroDateTimeBehavior=convertToNull";
            String user = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
        return connection;
    }

    /**
     * 根据id获取角色
     *
     * @param id
     * @return
     */
    public Role getRole(long id) {
        Connection connection = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("select id,role_name,note from t_role where id=?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Long roleId = rs.getLong("id");
                String userName = rs.getString("role_name");
                String note = rs.getString("note");
                Role role = new Role();
                role.setId(roleId);
                role.setNote(note);
                role.setRoleName(userName);
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(rs, ps, connection);
        }
        return null;
    }

    /**
     * 关闭资源
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    private void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JdbcExample jdbcExample = new JdbcExample();
        Role role = jdbcExample.getRole(1L);
        System.out.println("role_name==>" + role.getRoleName());
    }
}
