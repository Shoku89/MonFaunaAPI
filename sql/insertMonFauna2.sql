insert into location (name) values ('modulo1');
insert into location (name) values ('modulo2');
insert into location (name) values ('modulo3');
insert into location (name) values ('modulo4');
insert into location (name) values ('modulo5');

insert into user (name, email, password, admin) values ('Charles Darwin', 'cdevolution@gmail.com', 'nomorelamarck8', 1);
insert into user (name, email, password, admin) values ('George Hammer', 'geogehmm@gmail.com', 'neckfordays%00', 0);
insert into user (name, email, password, admin) values ('Fyra Lawter', 'lytnaw@gmail.com', 'laracrofter34', 0);

insert into project (name, owner_user_id) values ('Diversidade Parque Iguacu', 1);
insert into project (name, owner_user_id) values ('Itaipu', 2);

insert into collaborator_project (user_id, project_id) values (1, 2);
insert into collaborator_project (user_id, project_id) values (2, 1);
insert into collaborator_project (user_id, project_id) values (3, 1);
insert into collaborator_project (user_id, project_id) values (3, 2);


insert into specie_type (name) values ('Pholopoda');
insert into specie_type (name) values ('Mustelidae');
insert into specie_type (name) values ('Marsuloidae');
insert into specie_type (name) values ('Wolfierderae');

insert into specie (scientific_name, common_name, specie_type_id) values ('Biggus dickus', 'Bananasaur', 1);
insert into specie (scientific_name, common_name, specie_type_id) values ('Gulo gulo', 'Carcaju', 2);
insert into specie (scientific_name, common_name, specie_type_id) values ('Macropus giganteos', 'Canguru-vermelho', 3);
insert into specie (scientific_name, common_name, specie_type_id) values ('Squaro sponjus', 'Bob esponja', 1);
insert into specie (scientific_name, common_name, specie_type_id) values ('Bravarus Heratus', 'Garurumon', 4);

insert into animal (specie_id, tag, sex, register_date, location_id, project_id) values (1, 69, 'F', '2015-08-09', 2, 1);
insert into animal (specie_id, tag, sex, register_date, location_id, project_id) values (2, 02, 'M', '2015-08-11', 5, 1);
insert into animal (specie_id, tag, sex, register_date, location_id, project_id) values (3, 09, 'F', '2015-08-13', 3, 1);
insert into animal (specie_id, tag, sex, register_date, location_id, project_id) values (4, 07, 'M', '2015-08-15', 1, 2);
insert into animal (specie_id, tag, sex, register_date, location_id, project_id) values (5, 08, 'M', '2015-08-01', 1, 2);

insert into animal_measurement ( length, width, height, weight, description, animal_id) values (40, 5, 5 , 258.500, 'medidas padrao', 1);
insert into animal_measurement ( length, description, animal_id) values (69, 'comprimento da asa', 1);
insert into animal_measurement ( length, width, height, weight, description, animal_id) values (1.2 , 52, 40, 25, 'medidas padrao', 2);
insert into animal_measurement ( length, width, height, weight, description, animal_id) values (2.1, 74, 2.2, 64, 'medidas padrao', 3);
insert into animal_measurement ( length, width, height, weight, description, animal_id) values (7, 5, 5, 300, 'medidas padrao', 4);
insert into animal_measurement ( length, width, height, weight, description, animal_id) values (8, 5, 5, 1270, 'medidas padrao', 5);