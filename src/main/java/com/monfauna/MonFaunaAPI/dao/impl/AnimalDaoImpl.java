package com.monfauna.MonFaunaAPI.dao.impl;


import com.monfauna.MonFaunaAPI.dao.AnimalDao;
import com.monfauna.MonFaunaAPI.infrastructure.Database;
import com.monfauna.MonFaunaAPI.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnimalDaoImpl implements AnimalDao {

    private final Connection conn;

    public AnimalDaoImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Animal save(Animal animal, Integer projectId) {

        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "INSERT INTO animal (specie_id, tag, sex, image_url, register_date, location_id, project_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, animal.getSpecie().getId());
            ps.setString(2, animal.getTag());
            ps.setString(3, String.valueOf(animal.getSex()));
            ps.setString(4, animal.getImageUrl());
            ps.setDate(5, Date.valueOf(animal.getRegisterDate()));
            ps.setInt(6, animal.getLocation().getId());
            ps.setInt(7, projectId);

            int rowsAffected = ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rowsAffected != 0 && rs.next()) {
                return this.findById(rs.getInt(1));
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
    public List<Animal> findAll() {

        ResultSet rs = null;

        String sql = "select animal.*, " +
                "specie.id as specieId, specie.common_name, specie.scientific_name, " +
                "specie.created_at as specieCreatedAt, specie.updated_at as specieUpdatedAt," +
                "specie_type.id as specieTypeId, specie_type.name as specieTypeName, " +
                "animal_measurement.id as animalMeasurementId, animal_measurement.length, animal_measurement.width, " +
                "animal_measurement.height, animal_measurement.weight, animal_measurement.description,  " +
                "location.id as locationId, location.name as locationName, location.latitude, location.longitude " +
                "from animal " +
                "left join specie on specie.id = animal.specie_id " +
                "left join specie_type on specie_type.id = specie.specie_type_id " +
                "left join animal_measurement on animal_measurement.animal_id = animal.id " +
                "left join location on location.id = animal.location_id";

        try {

            rs = conn.prepareStatement(sql).executeQuery();

            //List(ArrayList) vs Map(HashMap):
            //list: tipo de estrutura de dados em que se armazenam itens/objetos do mesmo tipo
            //e garante a ordem em que foram adicionados
            //Map: tipo de estrutura de dados em que se utiliza chave e valor para adicionar os objetos
            //sua principal vantagem é a busca por indexação, já que cada item/valor possui sua chave unica
            Map<Integer, Animal> animalMap = new HashMap<>();


            while (rs.next()) {
                int animalId = rs.getInt("id");
                Animal animal = animalMap.get(animalId);

                if (animal == null) {
                    animal = getInstanceAnimal(rs);
                    animal.setSpecie(getInstanceSpecie(rs));
                    animal.getSpecie().setSpecieType(getInstanceSpecieType(rs));
                    animal.setLocation(getInstanceLocation(rs));
                }
                // add animalMeasurements into list
//               List<AnimalMeasurement> measurements = animal.getMeasurements();
//               measurements.add(getInstanceAnimalMeasurement(rs));
                animal.getMeasurements().add(getInstanceAnimalMeasurement(rs));
                //add animal instance on animalMap
                animalMap.put(animalId, animal);
            }
            //convert animalMap into list and return list
            return new ArrayList<>(animalMap.values());

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }


        return null;
    }

    @Override
    public List<Animal> findAllByProject(Integer idProject) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select animal.*, " +
                "specie.id as specieId, specie.common_name, specie.scientific_name, " +
                "specie.created_at as specieCreatedAt, specie.updated_at as specieUpdatedAt," +
                "specie_type.id as specieTypeId, specie_type.name as specieTypeName, " +
                "animal_measurement.id as animalMeasurementId, animal_measurement.length, animal_measurement.width, " +
                "animal_measurement.height, animal_measurement.weight, animal_measurement.description,  " +
                "location.id as locationId, location.name as locationName, location.latitude, location.longitude " +
                "from animal " +
                "left join specie on specie.id = animal.specie_id " +
                "left join specie_type on specie_type.id = specie.specie_type_id " +
                "left join animal_measurement on animal_measurement.animal_id = animal.id " +
                "left join location on location.id = animal.location_id " +
                "where animal.project_id = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,idProject);
            rs = ps.executeQuery();

            Map<Integer, Animal> animalMap = new HashMap<>();

            while (rs.next()) {
                int animalId = rs.getInt("id");
                Animal animal = animalMap.get(animalId);

                if (animal == null) {
                    animal = getInstanceAnimal(rs);
                    animal.setSpecie(getInstanceSpecie(rs));
                    animal.getSpecie().setSpecieType(getInstanceSpecieType(rs));
                    animal.setLocation(getInstanceLocation(rs));
                }

                animal.getMeasurements().add(getInstanceAnimalMeasurement(rs));
                animalMap.put(animalId, animal);
            }

            return  new ArrayList<>(animalMap.values());

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            Database.closeResultSet(rs);
        }

        return null;
    }


    @Override
    public Animal findById(Integer id) {

        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "select animal.*, " +
                "specie.id as specieId, specie.common_name, specie.scientific_name, " +
                "specie.created_at as specieCreatedAt, specie.updated_at as specieUpdatedAt," +
                "specie_type.id as specieTypeId, specie_type.name as specieTypeName, " +
                "animal_measurement.id as animalMeasurementId, animal_measurement.length, animal_measurement.width, " +
                "animal_measurement.height, animal_measurement.weight, animal_measurement.description,  " +
                "location.id as locationId, location.name as locationName, location.latitude, location.longitude " +
                "from animal " +
                "left join specie on specie.id = animal.specie_id " +
                "left join specie_type on specie_type.id = specie.specie_type_id " +
                "left join animal_measurement on animal_measurement.animal_id = animal.id " +
                "left join location on location.id = animal.location_id " +
                "where animal.id = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            Animal animal = null;


            while (rs.next()) {


                if (animal == null) {
                    animal = getInstanceAnimal(rs);
                    animal.setSpecie(getInstanceSpecie(rs));
                    animal.getSpecie().setSpecieType(getInstanceSpecieType(rs));
                    animal.setLocation(getInstanceLocation(rs));
                }

                animal.getMeasurements().add(getInstanceAnimalMeasurement(rs));


            }
            return animal;

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();

        } finally {
            Database.closeResultSet(rs);
        }



        return null;
    }

    @Override
    public Animal update(Animal animal) {

        PreparedStatement ps = null;

        String sql = "UPDATE animal SET specie_id = ?, tag = ?, sex = ?, image_url = ?, register_date = ?, location_id = ? " +
                "WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, animal.getSpecie().getId());
            ps.setString(2, animal.getTag());
            ps.setString(3, String.valueOf(animal.getSex()));
            ps.setString(4, animal.getImageUrl());
            ps.setDate(5, Date.valueOf(animal.getRegisterDate()));
            ps.setInt(6, animal.getLocation().getId());
            ps.setInt(7, animal.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 0) {
                return animal;
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
    public void deleteById(Integer id) {

        PreparedStatement ps = null;

        String sql = "DELETE FROM animal WHERE id = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("unable to remove animal");
            }

        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        } finally {
            Database.closePreparedStatement(ps);
        }

    }

    private Animal getInstanceAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setId(rs.getInt("id"));
        animal.setTag(rs.getString("tag"));
        animal.setSex(rs.getString("sex").charAt(0));
        animal.setImageUrl(rs.getString("image_url"));
        animal.setCreatedAt(rs.getDate("created_at").toLocalDate().atStartOfDay());
        animal.setUpdatedAt(rs.getDate("updated_at").toLocalDate().atStartOfDay());
        animal.setRegisterDate(rs.getDate("register_date").toLocalDate());
        return animal;
    }

    private Specie getInstanceSpecie(ResultSet rs) throws SQLException {
        Specie specie = new Specie();
        specie.setId(rs.getInt("specieId"));
        specie.setScientificName(rs.getString("scientific_name"));
        specie.setCommonName(rs.getString("common_name"));
        specie.setCreatedAt(rs.getDate("specieCreatedAt").toLocalDate().atStartOfDay());
        specie.setUpdatedAt(rs.getDate("specieUpdatedAt").toLocalDate().atStartOfDay());
        return specie;
    }

    private SpecieType getInstanceSpecieType(ResultSet rs) throws SQLException {
        SpecieType specieType = new SpecieType();
        specieType.setId(rs.getInt("specieTypeId"));
        specieType.setName(rs.getString("specieTypeName"));
        return specieType;
    }

    private Location getInstanceLocation(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("locationId"));
        location.setName(rs.getString("locationName"));
        location.setLatitude(rs.getString("latitude"));
        location.setLongitude(rs.getString("longitude"));
        return location;
    }

    private AnimalMeasurement getInstanceAnimalMeasurement(ResultSet rs) throws SQLException {
        AnimalMeasurement animalMeasurement = new AnimalMeasurement();
        animalMeasurement.setId(rs.getInt("animalMeasurementId"));
        animalMeasurement.setLength(rs.getDouble("length"));
        animalMeasurement.setWidth(rs.getDouble("width"));
        animalMeasurement.setHeight(rs.getDouble("height"));
        animalMeasurement.setWeight(rs.getDouble("weight"));
        animalMeasurement.setDescription(rs.getString("description"));
        return animalMeasurement;
    }

}

