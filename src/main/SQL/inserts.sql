/*
* scripts to adding fuel types in database
*/
insert into fuel_types(fuel_type_ua, fuel_type_en) value ('Дизель','DIESEl');
insert into fuel_types(fuel_type_ua, fuel_type_en) value ('Бензин','BENZINE');
insert into fuel_types(fuel_type_ua, fuel_type_en) value ('Гібрид','HYBRID');
insert into fuel_types(fuel_type_ua, fuel_type_en) value ('Електрика','ELECTRO');
/*
* scripts to adding transmission types in database
*/

insert into transmission_types(transmission_type_en, transmission_type_ua) value ('Automatic','Автомат');
insert into transmission_types(transmission_type_en, transmission_type_ua) value ('Manual','Механіка');
/*
* scripts to adding body_styles
*/

/*
 * scripts to adding car classes
 */

insert into car_classes(id, car_class) values (1, 'PREMIUM');
insert into car_classes(id, car_class) values (2,'ECONOMY');
insert into car_classes(id, car_class) values (3, 'MIDDLE');
insert into car_classes(id, car_class) values (4, 'BUSINESS');
insert into car_classes(id, car_class) values (5, 'SUV');
insert into car_classes(id, car_class) values (6, 'MINIVAN');
