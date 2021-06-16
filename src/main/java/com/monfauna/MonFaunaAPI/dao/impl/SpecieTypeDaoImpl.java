package com.monfauna.MonFaunaAPI.dao.impl;


import com.monfauna.MonFaunaAPI.dao.SpecieTypeDao;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.SpecieType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class SpecieTypeDaoImpl implements SpecieTypeDao {

    private final Connection conn;

    public SpecieTypeDaoImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public SpecieType save(SpecieType specieType) {
        PreparedStatement ps;
        ResultSet rs = null;
        String sql = "INSERT INTO specie_type (name) VALUES (?);";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, specieType.getName());

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                specieType.setId(rs.getInt(1));
                return specieType;
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
    public List<SpecieType> findAll() {

        List<SpecieType> specieTypes = new ArrayList<>();
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM specie_type";
            rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                SpecieType specieType = getInstanceSpecieType(rs);
                specieTypes.add(specieType);
            }

        } catch (SQLException e){
            System.out.println("fail");
            e.printStackTrace();

        } finally {
            Database.closeResultSet(rs);
        }

        return specieTypes;
    }



    @Override
    public SpecieType findById(Integer id) {

        PreparedStatement ps;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM specie_type WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return  getInstanceSpecieType(rs);
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
    public SpecieType update(SpecieType specieType) {
        PreparedStatement ps = null;
        String sql = "UPDATE specie_type SET name = ? WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, specieType.getName());
            ps.setInt(2, specieType.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                return specieType;
            }
            throw new SQLException("specie type not found");

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();

        }finally {
            Database.closePreparedStatement(ps);

        }

        return null;
    }

    @Override
    public void deleteById(Integer id) throws NotFoundException {
        PreparedStatement ps = null;

        try {
            String sql = "DELETE FROM specie_type WHERE id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected ==0) {
                throw new SQLException("unable to remove specie type");
            }

        } catch (SQLException e) {
            throw new NotFoundException("Specie Type Not Found");
        } finally {
            Database.closePreparedStatement(ps);
        }


    }

    private SpecieType getInstanceSpecieType(ResultSet rs) throws SQLException {
        SpecieType specieType = new SpecieType();
        specieType.setId(rs.getInt("id"));
        specieType.setName(rs.getString("name"));

        return specieType;
    }
}

