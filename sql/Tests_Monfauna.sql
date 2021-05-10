select specie_type.name, specie.common_name, specie.scientific_name, animal.*   from specie
inner join animal on animal.specie_id= specie.id
inner join specie_type on specie.specie_type_id= specie_type.id;
select * from location;
drop schema monfauna;
use monfauna;
select specie.*, specie_type.name from specie
inner join specie_type on specie.specie_type_id = specie_type.id;
DELETE FROM specie WHERE id >6;
ALTER TABLE animal_measurement
RENAME COLUMN lenght TO length;
select  animal.id, animal.specie_id, animal.number, animal_measurement.*   from animal_measurement
inner join animal on animal.id= animal_measurement.animal_id;

select animal.*, 
specie.id as specieId, specie.common_name, specie.scientific_name, specie.created_at, specie.updated_at,
specie_type.id as specieTypeId, specie_type.name,
animal_measurement.id as animalMeasurementId, animal_measurement.length, animal_measurement.width,
animal_measurement.height, animal_measurement.weight, animal_measurement.description,  
location.id as locationId, location.name, location.latitude, location.longitude
from animal
left join specie on specie.id = animal.specie_id
left join specie_type on specie_type.id = specie.specie_type_id
left join animal_measurement on animal_measurement.animal_id = animal.id
left join location on location.id = animal.location_id;

