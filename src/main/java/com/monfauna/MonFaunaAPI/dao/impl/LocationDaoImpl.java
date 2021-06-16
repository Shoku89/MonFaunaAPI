package com.monfauna.MonFaunaAPI.dao.impl;




import com.monfauna.MonFaunaAPI.dao.LocationDao;
import com.monfauna.MonFaunaAPI.exception.NotFoundException;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class LocationDaoImpl implements LocationDao {

    private final Connection conn;

    public LocationDaoImpl(Connection conn){
        this.conn = conn;
    }

    @Override
    public Location save(Location location) {
        PreparedStatement ps;
        ResultSet rs = null;
        String sql = "insert into location(name, latitude, longitude) values(?, ?, ?);";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getName());
            ps.setString(2, location.getLatitude());
            ps.setString(3, location.getLongitude());

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                location.setId(rs.getInt(1));
                return location;
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
    public List<Location> findAll()  {

        List<Location> locations = new ArrayList<>();

        ResultSet rs  = null;

        try{
            String sql = "SELECT * FROM location";
            rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()){
                Location location = getInstanceLocation(rs);
                locations.add(location);
            }

        } catch (SQLException e){
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return locations;

    }

    @Override
    public Location findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs  = null;

        try{
            String sql = "SELECT * FROM location WHERE id = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                return getInstanceLocation(rs);

            }

        } catch (SQLException e){
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return null;
    }

    @Override
    public Location update(Location location) {
        PreparedStatement ps = null;
        String sql = "UPDATE location SET name = ?, latitude = ?, longitude = ? " +
                "WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, location.getName());
            ps.setString(2, location.getLatitude());
            ps.setString(3, location.getLongitude());
            ps.setInt(4, location.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                return location;
            }
            throw new SQLException("location not found");

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

        try {
            String sql = "DELETE FROM location WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected ==0) {
                throw new SQLException("location id not found");
            }
        } catch (SQLException e) {
            throw new NotFoundException("Location Not Found");
        } finally {
            Database.closePreparedStatement(ps);
        }


    }

    private Location getInstanceLocation(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("id"));
        location.setName(rs.getString("name"));
        location.setLatitude(rs.getString("latitude"));
        location.setLongitude(rs.getString("longitude"));
        return location;
    }

}

