package com.monfauna.MonFaunaAPI.dao.impl;



import com.monfauna.MonFaunaAPI.dao.AnimalMeasurementDao;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.AnimalMeasurement;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class AnimalMeasurementDaoImpl implements AnimalMeasurementDao {

    private final Connection conn;

    public AnimalMeasurementDaoImpl(Connection conn) {this.conn = conn; }

    @Override
    public AnimalMeasurement save(AnimalMeasurement animalMeasurement, Integer animalId) {

        PreparedStatement ps;
        ResultSet rs = null;
        String sql = "INSERT INTO animal_measurement (length, width, height, weight, description, animal_id) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, animalMeasurement.getLength());
            ps.setDouble(2, animalMeasurement.getWidth());
            ps.setDouble(3, animalMeasurement.getHeight());
            ps.setDouble(4, animalMeasurement.getWeight());
            ps.setString(5, animalMeasurement.getDescription());
            ps.setInt(6, animalId);

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                animalMeasurement.setId(rs.getInt(1));
                return animalMeasurement;

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
    public List<AnimalMeasurement> findAll() {

        List<AnimalMeasurement> animalMeasurements = new ArrayList<>();
        ResultSet rs = null;

        String sql = "SELECT * FROM animal_measurement ";

        try {
            rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                AnimalMeasurement animalMeasurement = getInstanceAnimalMeasurement(rs);
                animalMeasurements.add(animalMeasurement);
            }

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return animalMeasurements;

    }


    @Override
    public AnimalMeasurement findById(Integer id) {

        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "SELECT * FROM animal_measurement WHERE id = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                return  getInstanceAnimalMeasurement(rs);
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
    public AnimalMeasurement update(AnimalMeasurement animalMeasurement) {

        PreparedStatement ps = null;
        String sql = "UPDATE animal_measurement SET length = ?, width = ?, height = ?, weight = ?, description = ? " +
                "WHERE id =?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, animalMeasurement.getLength());
            ps.setDouble(2, animalMeasurement.getWidth());
            ps.setDouble(3, animalMeasurement.getHeight());
            ps.setDouble(4, animalMeasurement.getWeight());
            ps.setString(5, animalMeasurement.getDescription());
            ps.setInt(6, animalMeasurement.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                return animalMeasurement;
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

        String sql = "DELETE FROM animal_measurement WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("unable to remove measurement");
            }

        } catch (SQLException e) {
            throw new NotFoundException("Measurement Not Found");
        } finally {
            Database.closePreparedStatement(ps);
        }

    }

    private AnimalMeasurement getInstanceAnimalMeasurement(ResultSet rs) throws  SQLException{
        AnimalMeasurement animalMeasurement = new AnimalMeasurement();
        animalMeasurement.setId(rs.getInt("id"));
        animalMeasurement.setLength(rs.getDouble("length"));
        animalMeasurement.setWidth(rs.getDouble("width"));
        animalMeasurement.setHeight(rs.getDouble("height"));
        animalMeasurement.setWeight(rs.getDouble("weight"));
        animalMeasurement.setDescription(rs.getString("description"));

        return animalMeasurement;
    }
}

