package com.monfauna.MonFaunaAPI.dao.impl;



import com.monfauna.MonFaunaAPI.dao.SpecieDao;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.Specie;
import com.monfauna.MonFaunaAPI.model.SpecieType;

import java.sql.*;
import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.ofPattern;
import java.util.ArrayList;
import java.util.List;

class SpecieDaoImpl implements SpecieDao {

    private final Connection conn;

    public SpecieDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Specie save(Specie specie) {

        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "INSERT INTO specie (scientific_name, common_name, specie_type_id) VALUES (?, ?, ?);";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, specie.getScientificName());
            ps.setString(2, specie.getCommonName());
            ps.setInt(3, specie.getSpecieType().getId());

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                return  this.findById(rs.getInt(1));
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
    public List<Specie> findAll() {

        List<Specie> species = new ArrayList<>();
        ResultSet rs = null;

        String sql = "SELECT specie.*, specie_type.name FROM specie " +
                "INNER JOIN specie_type ON specie.specie_type_id = specie_type.id;";

        try {
            rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                Specie specie = getInstanceSpecie(rs);
                species.add(specie);
            }

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return species;
    }


    @Override
    public Specie findById(Integer id) {

        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "SELECT specie.*, specie_type.name FROM specie " +
                "INNER JOIN specie_type ON specie.specie_type_id = specie_type.id " +
                "WHERE specie.id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                return  getInstanceSpecie(rs);
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
    public Specie update(Specie specie) {

        PreparedStatement ps = null;
        String sql = "UPDATE specie SET scientific_name = ?, common_name = ?, updated_at = ?, specie_type_id = ? " +
                "WHERE id =?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, specie.getScientificName());
            ps.setString(2, specie.getCommonName());
            ps.setString(3, specie.getUpdatedAt().format(ofPattern("yyyy-MM-dd HH:mm:ss")));
            ps.setInt(4, specie.getSpecieType().getId());
            ps.setInt(5, specie.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                return specie;
            }
        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closePreparedStatement(ps);
        }

        return null;
    }

    @Override
    public void deleteById(Integer id) throws NotFoundException {

        PreparedStatement ps = null;

        String sql = "DELETE FROM specie WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("unable to remove specie");
            }

        } catch (SQLException e) {
            throw new NotFoundException("Specie Not Found");
        } finally {
            Database.closePreparedStatement(ps);
        }

    }

    private Specie getInstanceSpecie(ResultSet rs) throws SQLException{
        Specie specie = new Specie();
        specie.setId(rs.getInt("id"));
        specie.setScientificName(rs.getString("scientific_name"));
        specie.setCommonName(rs.getString("common_name"));
        specie.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), ofPattern("yyyy-MM-dd HH:mm:ss")));
        specie.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at"), ofPattern("yyyy-MM-dd HH:mm:ss")));
        specie.setSpecieType(getInstanceSpecieType(rs));

        return specie;
    }

    private SpecieType getInstanceSpecieType(ResultSet rs) throws  SQLException{
        SpecieType specieType = new SpecieType();
        specieType.setId(rs.getInt("id"));
        specieType.setName(rs.getString("name"));
        return specieType;
    }
}

