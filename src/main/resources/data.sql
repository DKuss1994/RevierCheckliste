-- Zone zuerst → bekommt ID 1
INSERT INTO zones (name) VALUES ('Zone 1');

-- Driver → bekommt ID 1
INSERT INTO drivers (first_name, last_name) VALUES ('Max', 'Mustermann');

-- Driver Zone Zuweisung
INSERT INTO drivers_zones (driver_id, zone_id) VALUES (1, 1);

-- SecurityObject → bekommt ID 1
INSERT INTO security_objects (name, zone_id, street, city, postal_code, inspection_count, inspection_days)
VALUES ('Object A', 1, 'Musterstraße 1', 'Berlin', '10115', 2, 'MONDAY,FRIDAY');

-- Shift → bekommt ID 1
INSERT INTO shifts (driver_id, zone_id, deployment_date, start_time, end_time)
VALUES (1, 1, '2024-03-11', '06:00:00', '14:00:00');