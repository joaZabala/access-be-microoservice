INSERT INTO visitors (name, lastname, doc_type, doc_number, birth_date, is_active) VALUES
('Juan', 'Pérez', 'DNI', 12345678, '1980-05-20', TRUE),
('María', 'Gómez', 'DNI', 23456789, '1992-11-15', TRUE),
('Carlos', 'Fernández', 'CUIL', 34567890, '1985-03-10', FALSE),
('Laura', 'Rodríguez', 'CUIL', 45678901, '1990-07-25', TRUE),
('Pedro', 'Martínez', 'CUIL', 56789012, '1978-12-30', TRUE),
('Sofía', 'López', 'DNI', 67890123, '1995-02-18', TRUE),
('Diego', 'Hernández', 'DNI', 78901234, '1988-09-05', TRUE),
('Ana', 'Ramírez', 'CUIL', 89012345, '1982-12-12', FALSE),
('Jorge', 'Castro', 'CUIL', 90123456, '1975-06-22', TRUE),
('Lucía', 'Morales', 'DNI', 12345679, '1990-04-01', TRUE);


INSERT INTO auths (visitor_id, visitor_type, external_id, is_active, plot_id, created_user, created_date, last_updated_user, last_updated_date)
VALUES
(1, 'OWNER', 1001, true, 1, 1, NOW(), 1, NOW()),
(2, 'VISITOR', 1002, true, 2, 1, NOW(), 1, NOW()),
(3, 'EMPLOYEE', 1003, true, 3, 1, NOW(), 1, NOW()),
(4, 'SUPPLIER', 1004, true, 4, 1, NOW(), 1, NOW()),
(5, 'OWNER', 1005, false, 5, 1, NOW(), 1, NOW()),
(6, 'VISITOR', 1006, true, 1, 1, NOW(), 1, NOW());


INSERT INTO auth_ranges (auth_id, date_from, date_to, hour_from, hour_to, days, comment, is_active, created_user, created_date, last_updated_user, last_updated_date)
VALUES
(1, '2024-11-15', '2024-11-18', '00:00:00', '23:00:00', 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SUNDAY,SATURDAY', '', true, 1, NOW(), 1, NOW()),
(2, '2024-11-16', '2024-11-20', '10:00:00', '16:00:00', 'SATURDAY,SUNDAY', '', true, 1, NOW(), 1, NOW()),
(3, '2024-11-16', '2024-11-16', '09:00:00', '12:00:00', 'SATURDAY', 'Supplier delivery', true, 1, NOW(), 1, NOW()),
(4, '2024-11-17', '2024-11-19', '08:30:00', '17:30:00', 'SUNDAY,MONDAY,TUESDAY', '', true, 1, NOW(), 1, NOW()),
(5, '2024-11-15', '2024-11-22', '06:00:00', '20:00:00', 'MONDAY,TUESDAY,FRIDAY', '', true, 1, NOW(), 1, NOW()),
(6, '2024-11-16', '2024-11-16', '14:00:00', '18:00:00', 'SATURDAY', '', true, 1, NOW(), 1, NOW());


INSERT INTO accesses (auth_id, action, action_date, vehicle_type, vehicle_reg, vehicle_description, plot_id, supplier_employee_id, comments, notified, is_inconsistent, is_late, created_user, created_date, last_updated_user, last_updated_date)
VALUES
(1, 'ENTRY', '2024-11-16 08:15:00', 'CAR', 'ABC123', 'Sedán negro', 1, NULL, 'Entrada regular para el lote 1', false, false, false, 1, NOW(), 1, NOW()),
(1, 'EXIT', '2024-11-16 18:10:00', 'CAR', 'ABC123', 'Sedán negro', 1, NULL, 'Salida regular para el lote 1', false, false, false, 1, NOW(), 1, NOW()),
(2, 'ENTRY', '2024-11-16 10:05:00', 'MOTORCYCLE', 'XYZ987', 'Motocicleta roja', 2, NULL, 'Entrada de visitante en fin de semana', false, false, false, 1, NOW(), 1, NOW()),
(3, 'ENTRY', '2024-11-16 09:10:00', 'TRUCK', 'TRK555', 'Camión de reparto blanco', 3, 123, 'Entrega de proveedor', false, false, false, 1, NOW(), 1, NOW()),
(3, 'EXIT', '2024-11-16 12:30:00', 'TRUCK', 'TRK555', 'Camión de reparto blanco', 3, 123, 'Salida del proveedor después de la entrega', false, false, false, 1, NOW(), 1, NOW()),
(4, 'ENTRY', '2024-11-16 14:30:00', 'CAR', 'HJK345', 'SUV azul', 4, NULL, 'Entrada de empleado para capacitación', true, false, true, 1, NOW(), 1, NOW()),
(4, 'EXIT', '2024-11-16 18:00:00', 'CAR', 'HJK345', 'SUV azul', 4, NULL, 'Salida de empleado después de capacitación', true, false, true, 1, NOW(), 1, NOW()),
(5, 'ENTRY', '2024-11-16 06:00:00', 'BICYCLE', NULL, 'Bicicleta del visitante', 5, NULL, 'Entrada de visitante por la mañana temprano', false, false, false, 1, NOW(), 1, NOW()),
(6, 'ENTRY', '2024-11-16 14:00:00', 'CAR', 'LMN444', 'Sedán plateado', 1, NULL, 'Participante de visita guiada', false, false, false, 1, NOW(), 1, NOW());