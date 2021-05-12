package com.monfauna.MonFaunaAPI.dao.impl;



import com.monfauna.MonFaunaAPI.dao.UserDao;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

class UserDaoImpl implements UserDao {

    private final Connection conn;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User save(User user) {
        PreparedStatement ps;
        ResultSet rs = null;
        String sql = "INSERT INTO user (name, email, password, admin) VALUES (?, ?, ?, ?);";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isAdmin());

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                return findById(rs.getInt(1));

            }

        } catch (SQLException e) {
            System.out.println("unable to save data");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }
        return null;
    }


    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM user";
            rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                User user = getInstanceUser(rs);
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return users;

    }

    @Override
    public User findById(Integer id) {

        PreparedStatement ps;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return getInstanceUser(rs);

            }

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return null;
    }

    @Override
    public User update(User user) {
        PreparedStatement ps = null;
        String sql = "UPDATE user SET name = ?, email = ?, password = ?, admin = ?, updated_at = ? " +
                "WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isAdmin());
            ps.setString(5, user.getUpdatedAt().format(ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setInt(6, user.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                return user;
            }
            throw new SQLException("user not found");

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closePreparedStatement(ps);
        }

        return null;
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            String sql = "DELETE FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("unable to remove user");
            }

        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        } finally {
            Database.closePreparedStatement(ps);
        }

    }

    private User getInstanceUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAdmin(rs.getBoolean("admin"));
        user.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), ofPattern("yyyy-MM-dd HH:mm:ss")));
        user.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at"), ofPattern("yyyy-MM-dd HH:mm:ss")));
        return user;
    }
}

