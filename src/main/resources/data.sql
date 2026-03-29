-- Zone einfügen
INSERT INTO zones (id, name) VALUES (1, 'Zone 1');

-- Driver einfügen
INSERT INTO drivers (id, first_name, last_name) VALUES (1, 'Max', 'Mustermann');

-- Driver Zone Zuweisung
INSERT INTO drivers_zones (driver_id, zone_id) VALUES (1, 1);

-- SecurityObject einfügen
INSERT INTO security_objects (id, name, zone_id, street, city, postal_code, inspection_count, inspection_days)
VALUES (1, 'Object A', 1, 'Musterstraße 1', 'Berlin', '10115', 2, 'MONDAY,FRIDAY');

-- Shift einfügen
INSERT INTO shifts (id, driver_id, zone_id, deployment_date, start_time, end_time)
VALUES (1, 1, 1, '2024-03-11', '06:00:00', '14:00:00');