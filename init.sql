SET NAMES utf8mb4;

CREATE DATABASE accesses;

USE accesses;
CREATE DATABASE IF NOT EXISTS accesses;
GRANT ALL PRIVILEGES ON accesses.* TO 'utn'@'%';
FLUSH PRIVILEGES;
-- Tabla de Visitors
CREATE TABLE visitors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    doc_type ENUM('DNI', 'PASSPORT', 'CUIL', 'CUIT') NOT NULL,
    doc_number BIGINT NOT NULL,
    birth_date DATE,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Tabla de Auths
CREATE TABLE auths (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT,
    visitor_type ENUM('OWNER', 'WORKER', 'VISITOR', 'EMPLOYEE', 'PROVIDER', 'PROVIDER_ORGANIZATION', 'COHABITANT', 'EMERGENCY') NOT NULL,
    plot_id BIGINT,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_visitor FOREIGN KEY (visitor_id) REFERENCES visitors(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Tabla de Auth_Ranges
CREATE TABLE auth_ranges (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    auth_id BIGINT NULL,
    date_from DATE NULL,
    date_to DATE NULL,
    hour_from TIME NULL,
    hour_to TIME NULL,
    days VARCHAR(50) NULL,
    comment VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_auth FOREIGN KEY (auth_id) REFERENCES auths(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE accesses (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_date DATETIME(6) DEFAULT NULL,
    created_user BIGINT DEFAULT NULL,
    last_updated_date DATETIME(6) DEFAULT NULL,
    last_updated_user BIGINT DEFAULT NULL,
    action ENUM('ENTRY', 'EXIT') DEFAULT NULL,
    action_date DATETIME(6) DEFAULT NULL,
    comments VARCHAR(255) DEFAULT NULL,
    plot_id BIGINT DEFAULT NULL,
    supplier_employee_id BIGINT DEFAULT NULL,
    vehicle_description VARCHAR(255) DEFAULT NULL,
    vehicle_reg VARCHAR(255) DEFAULT NULL,
    vehicle_type ENUM('CAR', 'FOOT', 'MOTORBIKE', 'PICKUP', 'TRUCK') DEFAULT NULL,
    auth_id BIGINT DEFAULT NULL,
    is_inconsistent BIT(1) DEFAULT b'0',
    notified BIT(1) DEFAULT b'0',
    PRIMARY KEY (id),
    KEY FK_authvisit (auth_id),
    CONSTRAINT FK_authvisit FOREIGN KEY (auth_id) REFERENCES auths(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insertar datos en Visitors
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

-- Insertar datos en Auths
INSERT INTO auths (visitor_id, visitor_type, plot_id, is_active) VALUES
(1, 'OWNER', 1,TRUE),
(2 ,'WORKER',2 ,TRUE),
(3, 'VISITOR',3 ,TRUE),
(4, 'EMPLOYEE',4, FALSE),
(5, 'PROVIDER', 5,TRUE),
(6, 'OWNER', 6,TRUE),
(7 ,'WORKER',7 ,TRUE),
(8, 'VISITOR',8 ,TRUE),
(9, 'VISITOR',9, FALSE),
(10, 'VISITOR', 10,TRUE);



        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-29 18:16:01.507831', 12, '2024-09-19 09:09:23.485591', 12,
         '2024-02-06 08:19:47', 8, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-25 16:52:13.860108', 12, '2024-06-04 15:09:52.738031', 12,
         '2024-06-22 12:25:28', 10, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'IU7784');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-14 06:25:56.623788', 12, '2024-02-05 18:30:40.732544', 12,
         '2024-06-14 00:41:36', 3, 'TRUCK', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-12 05:55:26.663559', 12, '2024-06-19 04:08:17.075464', 12,
         '2024-08-31 16:58:48', 7, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-27 14:37:08.267464', 12, '2024-06-20 01:51:11.179384', 12,
         '2024-02-06 12:50:14', 10, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-12 05:55:26.663559', 12,
             '2024-03-19 15:03:59.771993', 12, '2024-08-31 20:58:48', 1,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-14 17:53:01.294780', 12, '2024-08-28 17:13:02.644421', 12,
         '2024-03-27 22:14:55', 1, 'MOTORBIKE', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-26 21:36:47.269630', 12, '2024-02-14 08:14:48.557717', 12,
         '2024-08-12 00:29:01', 6, 'PICKUP', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-24 20:13:35.304252', 12, '2024-05-18 11:24:52.987818', 12,
         '2024-07-20 01:53:01', 4, 'FOOT', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-26 21:36:47.269630', 12,
             '2024-07-25 16:15:42.078065', 12, '2024-08-12 01:29:01', 8,
             'PICKUP', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-17 04:29:03.716248', 12, '2024-10-09 23:41:21.795281', 12,
         '2024-04-27 12:57:43', 5, 'PICKUP', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-20 22:22:52.282123', 12, '2024-01-11 04:01:10.883552', 12,
         '2024-06-30 11:07:01', 5, 'PICKUP', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-28 03:16:35.022862', 12, '2024-04-13 18:06:00.506680', 12,
         '2024-09-22 13:31:54', 3, 'PICKUP', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-25 16:52:13.860108', 12,
             '2024-08-12 19:38:22.756827', 12, '2024-06-22 16:25:28', 1,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-17 04:29:03.716248', 12,
             '2024-07-19 06:10:21.207100', 12, '2024-04-27 13:57:43', 4,
             'PICKUP', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-23 09:09:57.264898', 12, '2024-06-06 03:04:54.087868', 12,
         '2024-04-02 13:03:11', 10, 'FOOT', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-03 04:17:03.041197', 12, '2024-10-28 17:02:40.092590', 12,
         '2024-02-29 23:02:15', 7, 'TRUCK', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-14 18:59:40.305687', 12, '2024-09-20 00:33:15.423981', 12,
         '2024-03-28 18:45:27', 8, 'FOOT', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-10 12:20:13.626307', 12, '2024-04-01 00:04:21.348669', 12,
         '2024-01-20 21:07:29', 1, 'CAR', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'YR7788');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-12 01:11:43.491654', 12, '2024-03-25 01:17:11.965287', 12,
         '2024-08-10 06:17:39', 2, 'MOTORBIKE', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-23 09:09:57.264898', 12,
             '2024-01-16 15:38:46.455560', 12, '2024-04-02 15:03:11', 9,
             'FOOT', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-12 01:11:43.491654', 12,
             '2024-07-18 02:41:33.772553', 12, '2024-08-10 07:17:39', 3,
             'MOTORBIKE', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-05 09:15:12.012703', 12, '2024-07-03 15:19:46.481590', 12,
         '2024-10-11 14:36:07', 4, 'MOTORBIKE', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-27 14:37:08.267464', 12,
             '2024-10-16 19:37:02.461288', 12, '2024-02-06 13:50:14', 4,
             'FOOT', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-10 10:57:10.831922', 12, '2024-09-30 11:37:04.256552', 12,
         '2024-10-23 00:48:12', 3, 'MOTORBIKE', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-24 11:46:18.258288', 12, '2024-05-04 05:59:09.269170', 12,
         '2024-03-22 17:11:06', 7, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-19 13:36:09.711882', 12, '2024-07-25 12:47:50.009170', 12,
         '2024-05-17 07:21:55', 3, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-10 12:20:13.626307', 12,
             '2024-05-10 22:35:12.269971', 12, '2024-01-21 00:07:29', 10,
             'CAR', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-28 03:16:35.022862', 12,
             '2024-03-19 07:43:49.621325', 12, '2024-09-22 15:31:54', 1,
             'PICKUP', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-13 21:16:52.210893', 12, '2024-02-11 05:57:23.712964', 12,
         '2024-11-01 23:33:42', 6, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-22 06:48:24.347827', 12, '2024-07-23 20:02:00.594632', 12,
         '2024-07-14 13:36:12', 1, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'FG9364');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-05 17:59:11.586218', 12, '2024-05-06 19:37:33.369408', 12,
         '2024-02-27 10:39:57', 8, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-23 17:01:55.689088', 12, '2024-05-17 10:17:17.248363', 12,
         '2024-03-17 10:37:52', 1, 'TRUCK', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-31 20:37:22.032586', 12, '2024-05-22 17:26:05.536137', 12,
         '2024-09-16 13:19:35', 5, 'CAR', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'EM5943');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-14 06:25:56.623788', 12,
             '2024-01-22 10:20:13.910774', 12, '2024-06-14 04:41:36', 9,
             'TRUCK', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-01 03:06:11.288726', 12, '2024-09-03 21:48:46.710579', 12,
         '2024-10-02 14:32:30', 1, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-06 00:55:22.801327', 12, '2024-08-02 03:44:50.144444', 12,
         '2024-09-06 13:19:52', 2, 'PICKUP', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-13 01:45:05.549209', 12, '2024-05-25 09:18:00.865274', 12,
         '2024-08-29 22:15:55', 3, 'PICKUP', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-07 09:17:26.523136', 12, '2024-09-27 17:55:07.666372', 12,
         '2024-10-13 21:15:25', 8, 'MOTORBIKE', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-16 08:54:41.005348', 12, '2024-06-15 19:52:59.445856', 12,
         '2024-02-10 18:54:17', 4, 'PICKUP', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-10 10:57:10.831922', 12,
             '2024-07-16 09:48:14.892106', 12, '2024-10-23 03:48:12', 6,
             'MOTORBIKE', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-30 10:03:38.554816', 12, '2024-10-02 04:12:50.552258', 12,
         '2024-09-05 17:15:18', 5, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-01 03:06:11.288726', 12,
             '2024-06-09 04:38:17.554784', 12, '2024-10-02 16:32:30', 2,
             'PICKUP', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-03 04:17:03.041197', 12,
             '2024-06-12 14:31:40.735709', 12, '2024-03-01 02:02:15', 5,
             'TRUCK', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-16 08:54:41.005348', 12,
             '2024-09-07 21:42:28.086944', 12, '2024-02-10 22:54:17', 4,
             'PICKUP', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-14 18:59:40.305687', 12,
             '2024-09-24 15:10:50.932011', 12, '2024-03-28 22:45:27', 4,
             'FOOT', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-31 20:37:22.032586', 12,
             '2024-02-09 20:46:07.358992', 12, '2024-09-16 14:19:35', 6,
             'CAR', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'EM5943');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-08 20:09:28.572925', 12, '2024-11-01 04:17:04.217995', 12,
         '2024-08-06 08:24:42', 6, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'IB2794');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-31 10:44:25.088219', 12, '2024-05-25 03:10:38.646536', 12,
         '2024-05-11 06:05:58', 3, 'PICKUP', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-05 09:15:12.012703', 12,
             '2024-09-01 18:40:05.525218', 12, '2024-10-11 17:36:07', 8,
             'MOTORBIKE', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-11 07:59:22.607527', 12, '2024-01-11 18:28:33.370591', 12,
         '2024-05-30 09:12:26', 10, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-16 23:32:46.824462', 12, '2024-03-28 12:03:54.910928', 12,
         '2024-02-19 09:06:14', 5, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'PZ1195');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-19 19:17:21.284713', 12, '2024-04-18 14:03:21.419563', 12,
         '2024-05-29 16:14:08', 3, 'PICKUP', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-06 00:55:22.801327', 12,
             '2024-05-28 16:52:13.883827', 12, '2024-09-06 14:19:52', 2,
             'PICKUP', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-05 02:07:48.396516', 12, '2024-05-10 13:01:33.665066', 12,
         '2024-07-14 11:23:05', 7, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-06 11:10:44.139928', 12, '2024-02-04 08:07:59.073853', 12,
         '2024-05-03 14:52:28', 2, 'PICKUP', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-18 02:42:32.582426', 12, '2024-04-07 13:55:05.019397', 12,
         '2024-04-30 04:04:11', 9, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-07 06:10:30.884308', 12, '2024-01-29 06:19:36.685110', 12,
         '2024-10-26 18:36:14', 6, 'FOOT', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-03 20:44:50.991949', 12, '2024-03-18 19:23:19.552160', 12,
         '2024-09-16 14:08:52', 10, 'PICKUP', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-07 16:41:34.182705', 12, '2024-07-10 10:15:18.523237', 12,
         '2024-06-18 17:04:25', 4, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-20 22:22:52.282123', 12,
             '2024-10-21 03:12:12.536383', 12, '2024-06-30 15:07:01', 4,
             'PICKUP', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-11 07:59:22.607527', 12,
             '2024-07-16 15:02:45.319712', 12, '2024-05-30 11:12:26', 9,
             'PICKUP', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-05 19:16:00.799479', 12, '2024-08-03 12:40:34.962895', 12,
         '2024-04-30 17:51:31', 8, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-06 07:29:39.448048', 12, '2024-07-04 07:24:35.154376', 12,
         '2024-06-21 02:33:06', 2, 'MOTORBIKE', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-12 17:12:36.029494', 12, '2024-05-21 09:07:05.555848', 12,
         '2024-10-04 15:01:57', 3, 'CAR', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'HB2584');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-19 13:36:09.711882', 12,
             '2024-09-14 02:46:13.456143', 12, '2024-05-17 09:21:55', 9,
             'PICKUP', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-28 01:06:55.211203', 12, '2024-10-08 18:16:31.714257', 12,
         '2024-06-12 16:45:35', 10, 'PICKUP', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-28 09:17:07.869401', 12, '2024-07-03 19:01:07.537833', 12,
         '2024-06-09 04:35:53', 9, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-07 22:06:08.092186', 12, '2024-03-03 08:52:38.140411', 12,
         '2024-01-25 21:02:10', 4, 'CAR', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'SW1694');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-13 01:45:05.549209', 12,
             '2024-05-15 10:56:41.937872', 12, '2024-08-30 01:15:55', 6,
             'PICKUP', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-03 20:44:50.991949', 12,
             '2024-10-31 06:37:46.243098', 12, '2024-09-16 16:08:52', 9,
             'PICKUP', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-27 18:37:00.045873', 12, '2024-09-21 08:17:31.964243', 12,
         '2024-06-01 19:52:28', 5, 'TRUCK', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-16 23:32:46.824462', 12,
             '2024-08-01 06:57:43.504340', 12, '2024-02-19 10:06:14', 3,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-12 17:12:36.029494', 12,
             '2024-02-15 05:54:50.971520', 12, '2024-10-04 19:01:57', 1,
             'CAR', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-29 18:16:01.507831', 12,
             '2024-04-08 01:06:08.600703', 12, '2024-02-06 10:19:47', 4,
             'PICKUP', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-24 11:46:18.258288', 12,
             '2024-08-02 20:28:37.447949', 12, '2024-03-22 19:11:06', 5,
             'TRUCK', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-25 09:32:02.030906', 12, '2024-06-17 04:35:49.262967', 12,
         '2024-01-25 06:55:42', 6, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'LK6911');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-07 06:10:30.884308', 12,
             '2024-02-12 12:15:13.910629', 12, '2024-10-26 19:36:14', 2,
             'FOOT', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-12 15:36:17.235058', 12, '2024-06-29 17:44:34.126586', 12,
         '2024-10-16 06:22:54', 1, 'MOTORBIKE', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-06 07:29:39.448048', 12,
             '2024-04-08 19:59:49.244965', 12, '2024-06-21 03:33:06', 5,
             'MOTORBIKE', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-30 16:34:35.174801', 12, '2024-01-14 04:57:30.482761', 12,
         '2024-01-03 15:31:09', 1, 'MOTORBIKE', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-28 01:06:55.211203', 12,
             '2024-08-04 15:18:57.275331', 12, '2024-06-12 18:45:35', 5,
             'PICKUP', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-08 20:09:28.572925', 12,
             '2024-04-07 10:01:39.646425', 12, '2024-08-06 12:24:42', 5,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-05 02:07:48.396516', 12,
             '2024-10-26 07:32:50.051485', 12, '2024-07-14 13:23:05', 1,
             'TRUCK', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-11 16:49:23.961711', 12, '2024-05-28 03:57:44.447509', 12,
         '2024-07-17 21:04:23', 6, 'MOTORBIKE', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-25 09:32:02.030906', 12,
             '2024-09-18 10:31:17.640623', 12, '2024-01-25 10:55:42', 10,
             'CAR', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-22 14:21:35.956604', 12, '2024-01-25 06:41:55.688046', 12,
         '2024-11-03 07:37:40', 7, 'MOTORBIKE', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-19 02:46:47.783125', 12, '2024-04-02 15:43:06.521646', 12,
         '2024-06-17 21:22:39', 9, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-07 16:41:34.182705', 12,
             '2024-07-03 14:17:56.711733', 12, '2024-06-18 19:04:25', 3,
             'FOOT', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-15 14:12:51.666555', 12, '2024-10-26 19:11:36.951030', 12,
         '2024-09-26 12:06:26', 1, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-04 03:58:14.755508', 12, '2024-04-06 00:57:08.086633', 12,
         '2024-08-06 09:28:34', 7, 'TRUCK', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-04 13:29:59.496174', 12, '2024-07-08 21:11:19.943846', 12,
         '2024-06-18 18:48:40', 9, 'CAR', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'BQ1394');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-16 20:24:56.534094', 12, '2024-10-05 01:30:35.970563', 12,
         '2024-02-03 10:52:14', 7, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-21 07:52:09.518595', 12, '2024-06-10 14:23:11.370318', 12,
         '2024-10-25 13:39:44', 1, 'PICKUP', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-15 14:12:51.666555', 12,
             '2024-06-19 00:55:12.221962', 12, '2024-09-26 16:06:26', 3,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-13 21:16:52.210893', 12,
             '2024-07-23 13:28:53.330989', 12, '2024-11-02 02:33:42', 6,
             'PICKUP', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-07 06:13:06.314261', 12, '2024-04-05 16:44:44.253427', 12,
         '2024-02-29 14:43:21', 3, 'TRUCK', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-01 05:46:13.335456', 12, '2024-10-24 09:50:54.668877', 12,
         '2024-08-02 03:18:23', 3, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'JX3731');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-14 17:53:01.294780', 12,
             '2024-10-04 18:24:50.457358', 12, '2024-03-28 02:14:55', 3,
             'MOTORBIKE', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-05 19:16:00.799479', 12,
             '2024-10-31 18:03:26.097460', 12, '2024-04-30 19:51:31', 2,
             'PICKUP', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-24 20:13:35.304252', 12,
             '2024-04-12 11:02:39.055422', 12, '2024-07-20 05:53:01', 2,
             'FOOT', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-09 13:23:54.663733', 12, '2024-08-13 11:10:47.817692', 12,
         '2024-01-11 21:42:32', 1, 'CAR', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'AP3793');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-14 00:18:35.291797', 12, '2024-08-30 16:08:06.244024', 12,
         '2024-10-19 00:18:01', 2, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-22 14:21:35.956604', 12,
             '2024-07-13 06:33:04.555077', 12, '2024-11-03 11:37:40', 3,
             'MOTORBIKE', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-23 17:01:55.689088', 12,
             '2024-03-07 08:15:47.940907', 12, '2024-03-17 14:37:52', 3,
             'TRUCK', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-16 20:24:56.534094', 12,
             '2024-04-11 19:24:08.635623', 12, '2024-02-03 13:52:14', 7,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-31 10:44:25.088219', 12,
             '2024-09-28 05:10:15.503370', 12, '2024-05-11 09:05:58', 1,
             'PICKUP', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-19 02:46:47.783125', 12,
             '2024-04-04 14:20:08.938458', 12, '2024-06-17 23:22:39', 4,
             'PICKUP', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-11 23:44:29.779609', 12, '2024-06-25 19:47:13.445445', 12,
         '2024-08-18 03:25:39', 4, 'TRUCK', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-21 11:33:34.857762', 12, '2024-09-06 07:53:02.995786', 12,
         '2024-06-16 01:14:06', 5, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-28 09:17:07.869401', 12,
             '2024-03-05 06:11:09.973592', 12, '2024-06-09 07:35:53', 8,
             'TRUCK', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-02 23:51:54.615737', 12, '2024-01-08 14:30:18.393968', 12,
         '2024-09-19 01:44:32', 7, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-23 02:36:37.008428', 12, '2024-09-20 02:29:26.104365', 12,
         '2024-02-28 22:54:47', 9, 'CAR', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'HE9106');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-09 08:09:44.732406', 12, '2024-08-06 20:07:25.209527', 12,
         '2024-03-02 07:52:54', 6, 'CAR', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'FR2148');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-06 02:39:56.352273', 12, '2024-02-28 07:05:21.594611', 12,
         '2024-02-09 18:07:39', 9, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-22 23:38:13.715289', 12, '2024-04-27 12:12:35.449340', 12,
         '2024-10-22 17:28:01', 5, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-09 13:23:54.663733', 12,
             '2024-01-19 19:44:49.541283', 12, '2024-01-11 22:42:32', 7,
             'CAR', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-01 22:34:38.606076', 12, '2024-10-24 07:07:35.047594', 12,
         '2024-09-29 10:25:58', 3, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-09 20:27:16.304212', 12, '2024-05-30 18:06:30.991969', 12,
         '2024-02-29 12:53:05', 6, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-17 12:54:18.280627', 12, '2024-05-24 10:52:56.787953', 12,
         '2024-04-10 10:24:00', 4, 'TRUCK', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-22 06:48:24.347827', 12,
             '2024-05-27 11:27:25.218869', 12, '2024-07-14 16:36:12', 10,
             'CAR', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'FG9364');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-23 02:36:37.008428', 12,
             '2024-09-28 12:22:22.473949', 12, '2024-02-29 01:54:47', 7,
             'CAR', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-27 20:20:09.452286', 12, '2024-10-27 07:39:22.042276', 12,
         '2024-06-27 22:29:32', 3, 'FOOT', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-11 18:31:59.522180', 12, '2024-05-11 22:20:26.516216', 12,
         '2024-08-06 01:54:09', 3, 'MOTORBIKE', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-08 06:04:27.207377', 12, '2024-07-07 18:03:29.869361', 12,
         '2024-09-25 06:19:15', 4, 'MOTORBIKE', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-17 01:28:42.364779', 12, '2024-04-20 03:08:34.706252', 12,
         '2024-05-24 07:40:01', 4, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'CJ8659');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-17 12:54:18.280627', 12,
             '2024-02-16 18:00:11.076245', 12, '2024-04-10 11:24:00', 6,
             'TRUCK', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-02 23:51:54.615737', 12,
             '2024-07-14 02:47:40.042482', 12, '2024-09-19 03:44:32', 2,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-08 06:04:27.207377', 12,
             '2024-10-10 06:30:14.599719', 12, '2024-09-25 10:19:15', 1,
             'MOTORBIKE', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-07 22:06:08.092186', 12,
             '2024-09-06 22:24:40.003422', 12, '2024-01-25 23:02:10', 7,
             'CAR', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-21 11:33:34.857762', 12,
             '2024-05-29 04:04:29.100949', 12, '2024-06-16 03:14:06', 4,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-01 14:38:29.308234', 12, '2024-03-15 13:53:46.457791', 12,
         '2024-02-21 03:11:04', 9, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-26 08:25:50.902466', 12, '2024-06-28 01:41:37.185059', 12,
         '2024-02-05 19:38:09', 10, 'TRUCK', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-11 23:44:29.779609', 12,
             '2024-10-03 00:10:14.858914', 12, '2024-08-18 06:25:39', 4,
             'TRUCK', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-16 18:09:35.761758', 12, '2024-10-15 21:23:04.618016', 12,
         '2024-04-14 13:58:14', 2, 'MOTORBIKE', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-24 18:16:51.634571', 12, '2024-09-19 09:43:15.680908', 12,
         '2024-09-18 14:00:21', 3, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'ZG9311');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-19 19:17:21.284713', 12,
             '2024-06-09 16:24:44.899966', 12, '2024-05-29 17:14:08', 2,
             'PICKUP', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-11 18:31:59.522180', 12,
             '2024-01-09 14:14:20.306088', 12, '2024-08-06 05:54:09', 1,
             'MOTORBIKE', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-04 03:58:14.755508', 12,
             '2024-10-18 00:49:45.442127', 12, '2024-08-06 11:28:34', 9,
             'TRUCK', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-04 13:29:59.496174', 12,
             '2024-01-22 10:41:44.037536', 12, '2024-06-18 19:48:40', 10,
             'CAR', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'BQ1394');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-02 04:50:07.613451', 12, '2024-09-19 10:26:11.677616', 12,
         '2024-10-18 17:14:40', 3, 'MOTORBIKE', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-06 00:06:22.716473', 12, '2024-02-04 12:06:45.320057', 12,
         '2024-06-28 17:05:00', 2, 'PICKUP', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-24 18:16:51.634571', 12,
             '2024-10-30 21:44:38.866618', 12, '2024-09-18 16:00:21', 2,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-27 02:02:43.397013', 12, '2024-06-27 15:46:24.004786', 12,
         '2024-10-05 16:01:59', 5, 'TRUCK', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-28 01:27:08.234452', 12, '2024-06-23 13:39:10.421660', 12,
         '2024-06-02 22:41:19', 3, 'TRUCK', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-04 11:09:20.840542', 12, '2024-05-02 13:40:50.982437', 12,
         '2024-06-17 19:20:10', 5, 'FOOT', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-22 23:38:13.715289', 12,
             '2024-04-24 11:46:34.421581', 12, '2024-10-22 19:28:01', 7,
             'TRUCK', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-08 21:43:48.378203', 12, '2024-10-22 02:39:20.834117', 12,
         '2024-01-01 09:44:54', 4, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NI1494');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-11-03 19:00:31.215410', 12, '2024-10-10 10:38:16.071964', 12,
         '2024-02-01 17:44:56', 5, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'OB3356');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-06 11:10:44.139928', 12,
             '2024-06-17 15:22:26.413048', 12, '2024-05-03 17:52:28', 2,
             'PICKUP', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-10 11:25:26.991509', 12, '2024-05-19 01:50:22.987779', 12,
         '2024-05-15 08:46:17', 3, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-16 11:30:17.570379', 12, '2024-01-20 10:35:32.593489', 12,
         '2024-04-05 10:45:17', 6, 'TRUCK', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-17 01:04:48.199166', 12, '2024-08-15 23:37:59.100991', 12,
         '2024-07-06 20:01:16', 1, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-27 11:48:01.188742', 12, '2024-09-22 04:36:55.163306', 12,
         '2024-03-08 03:09:24', 7, 'CAR', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'IO4490');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-09 08:09:44.732406', 12,
             '2024-06-28 22:21:41.128296', 12, '2024-03-02 10:52:54', 9,
             'CAR', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-06 00:06:22.716473', 12,
             '2024-04-29 19:05:33.811463', 12, '2024-06-28 18:05:00', 3,
             'PICKUP', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-14 09:41:36.005370', 12, '2024-08-15 06:02:53.601695', 12,
         '2024-07-02 14:33:37', 7, 'FOOT', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-11 16:49:23.961711', 12,
             '2024-09-02 02:00:24.934535', 12, '2024-07-18 00:04:23', 1,
             'MOTORBIKE', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-19 14:16:01.687289', 12, '2024-06-06 06:16:34.797783', 12,
         '2024-08-02 03:14:27', 5, 'TRUCK', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-28 23:07:14.922996', 12, '2024-06-03 01:43:51.937687', 12,
         '2024-07-20 18:16:09', 1, 'CAR', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'KT8807');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-06 02:39:56.352273', 12,
             '2024-03-10 15:03:18.375156', 12, '2024-02-09 22:07:39', 4,
             'FOOT', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-09 14:08:34.482874', 12, '2024-02-14 23:04:13.224606', 12,
         '2024-01-13 23:07:53', 4, 'PICKUP', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-07 06:13:06.314261', 12,
             '2024-10-22 17:25:59.719706', 12, '2024-02-29 18:43:21', 10,
             'TRUCK', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-26 08:25:50.902466', 12,
             '2024-03-04 13:06:28.642122', 12, '2024-02-05 22:38:09', 1,
             'TRUCK', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-02 04:50:07.613451', 12,
             '2024-05-20 16:27:26.839503', 12, '2024-10-18 19:14:40', 5,
             'MOTORBIKE', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-28 01:27:08.234452', 12,
             '2024-02-22 02:47:10.960956', 12, '2024-06-02 23:41:19', 6,
             'TRUCK', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-09 20:27:16.304212', 12,
             '2024-03-31 19:16:50.686249', 12, '2024-02-29 15:53:05', 8,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-10 13:31:43.933657', 12, '2024-08-13 17:29:19.188440', 12,
         '2024-02-11 06:19:36', 5, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-17 19:28:23.273791', 12, '2024-06-11 07:47:14.390636', 12,
         '2024-02-06 11:37:29', 8, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-16 18:09:35.761758', 12,
             '2024-10-07 22:21:39.208971', 12, '2024-04-14 16:58:14', 4,
             'MOTORBIKE', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-19 14:16:01.687289', 12,
             '2024-08-29 14:43:35.369090', 12, '2024-08-02 05:14:27', 8,
             'TRUCK', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-15 13:08:46.008492', 12, '2024-01-19 00:33:00.185648', 12,
         '2024-03-11 08:47:06', 8, 'MOTORBIKE', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-05 17:59:11.586218', 12,
             '2024-01-17 23:28:53.156615', 12, '2024-02-27 14:39:57', 2,
             'FOOT', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-07 23:27:26.336926', 12, '2024-03-30 07:58:28.306816', 12,
         '2024-07-10 11:48:27', 4, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-04 11:09:20.840542', 12,
             '2024-01-30 04:32:35.169444', 12, '2024-06-17 22:20:10', 8,
             'FOOT', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-31 21:00:14.396508', 12, '2024-04-26 22:25:09.774440', 12,
         '2024-01-24 06:37:26', 10, 'FOOT', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-07 09:43:52.372344', 12, '2024-07-19 18:21:58.501060', 12,
         '2024-09-12 22:34:07', 8, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-20 06:05:02.144446', 12, '2024-05-10 17:18:16.174705', 12,
         '2024-01-18 22:41:38', 9, 'MOTORBIKE', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-01 14:38:29.308234', 12,
             '2024-04-16 06:06:42.961863', 12, '2024-02-21 07:11:04', 8,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-14 20:24:05.865576', 12, '2024-05-18 10:17:54.502600', 12,
         '2024-03-26 01:49:46', 3, 'CAR', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'YO4017');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-27 02:02:43.397013', 12,
             '2024-09-14 01:52:11.742209', 12, '2024-10-05 19:01:59', 5,
             'TRUCK', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-28 07:06:50.699128', 12, '2024-02-05 05:02:13.291874', 12,
         '2024-04-10 06:15:57', 3, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-12 01:18:30.930169', 12, '2024-08-19 22:50:55.752419', 12,
         '2024-10-23 05:51:24', 7, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-12 11:13:35.074635', 12, '2024-08-16 00:18:58.669621', 12,
         '2024-07-12 18:34:00', 7, 'MOTORBIKE', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-28 23:07:14.922996', 12,
             '2024-07-09 09:43:44.505751', 12, '2024-07-20 19:16:09', 5,
             'CAR', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-15 13:08:46.008492', 12,
             '2024-02-04 21:47:11.131618', 12, '2024-03-11 10:47:06', 2,
             'MOTORBIKE', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-15 21:30:58.304305', 12, '2024-03-10 20:22:22.055526', 12,
         '2024-09-20 03:24:53', 9, 'FOOT', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-01 08:43:31.191960', 12, '2024-09-07 20:43:43.914597', 12,
         '2024-04-09 18:04:20', 3, 'TRUCK', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-13 15:52:42.035695', 12, '2024-09-03 11:46:24.775560', 12,
         '2024-05-01 03:26:37', 3, 'PICKUP', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-12 01:18:30.930169', 12,
             '2024-01-14 11:47:26.549897', 12, '2024-10-23 09:51:24', 5,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-10 09:17:36.577886', 12, '2024-10-12 22:29:58.918901', 12,
         '2024-08-08 08:42:49', 3, 'MOTORBIKE', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-18 23:03:48.444991', 12, '2024-04-10 00:09:04.406749', 12,
         '2024-02-04 14:23:43', 9, 'CAR', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'CR5886');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-27 12:22:49.811508', 12, '2024-04-29 20:47:13.441047', 12,
         '2024-03-30 07:16:52', 5, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-17 01:04:48.199166', 12,
             '2024-07-10 21:21:15.899843', 12, '2024-07-07 00:01:16', 1,
             'MOTORBIKE', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-26 14:59:53.954434', 12, '2024-04-22 23:36:16.322427', 12,
         '2024-02-04 17:01:08', 7, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'SE1275');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-14 12:23:45.486918', 12, '2024-08-02 20:17:18.071585', 12,
         '2024-06-08 21:06:47', 5, 'MOTORBIKE', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-31 09:46:14.771872', 12, '2024-10-05 02:26:28.634592', 12,
         '2024-02-16 00:58:25', 10, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-08 05:26:44.208197', 12, '2024-03-21 06:59:41.553611', 12,
         '2024-06-02 17:06:00', 7, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-13 01:24:04.614751', 12, '2024-07-25 08:21:49.680848', 12,
         '2024-01-03 06:45:59', 3, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-30 17:35:51.748278', 12, '2024-10-29 04:07:47.837996', 12,
         '2024-01-16 04:45:39', 8, 'MOTORBIKE', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-26 02:05:10.801774', 12, '2024-01-10 07:36:52.835954', 12,
         '2024-06-09 21:54:40', 9, 'TRUCK', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-27 21:40:24.048826', 12, '2024-05-31 11:19:58.345275', 12,
         '2024-04-27 00:40:44', 1, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-30 16:34:35.174801', 12,
             '2024-03-10 20:04:38.936724', 12, '2024-01-03 18:31:09', 5,
             'MOTORBIKE', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-26 02:05:10.801774', 12,
             '2024-01-19 09:13:09.864381', 12, '2024-06-10 01:54:40', 8,
             'TRUCK', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-09 13:02:44.870314', 12, '2024-07-03 21:23:06.856699', 12,
         '2024-02-16 22:34:29', 8, 'PICKUP', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-08 21:43:48.378203', 12,
             '2024-01-22 23:41:56.503244', 12, '2024-01-01 11:44:54', 1,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NI1494');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-07 09:17:26.523136', 12,
             '2024-10-07 04:12:11.405607', 12, '2024-10-13 22:15:25', 7,
             'MOTORBIKE', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-30 15:25:41.488257', 12, '2024-05-11 22:04:50.508459', 12,
         '2024-03-22 22:51:22', 8, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-07 09:43:52.372344', 12,
             '2024-06-06 02:37:44.762040', 12, '2024-09-12 23:34:07', 4,
             'PICKUP', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-27 11:48:01.188742', 12,
             '2024-09-09 15:49:05.540318', 12, '2024-03-08 06:09:24', 5,
             'CAR', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-10 09:17:36.577886', 12,
             '2024-08-19 02:20:25.155440', 12, '2024-08-08 10:42:49', 2,
             'MOTORBIKE', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-14 09:41:36.005370', 12,
             '2024-02-12 01:01:48.925363', 12, '2024-07-02 15:33:37', 9,
             'FOOT', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-11-03 19:00:31.215410', 12,
             '2024-08-21 00:27:13.658202', 12, '2024-02-01 21:44:56', 5,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-18 23:03:48.444991', 12,
             '2024-03-16 15:14:32.721495', 12, '2024-02-04 18:23:43', 2,
             'CAR', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-05 16:29:10.076348', 12, '2024-04-01 20:14:02.742896', 12,
         '2024-08-12 11:06:20', 2, 'FOOT', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-20 06:05:02.144446', 12,
             '2024-08-30 20:52:16.064131', 12, '2024-01-19 01:41:38', 8,
             'MOTORBIKE', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-06 11:14:25.145890', 12, '2024-07-31 00:25:16.905180', 12,
         '2024-07-03 17:46:41', 5, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-09 14:08:34.482874', 12,
             '2024-03-18 05:25:17.144801', 12, '2024-01-14 00:07:53', 4,
             'PICKUP', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-28 14:03:14.943039', 12, '2024-09-19 05:54:36.512257', 12,
         '2024-02-28 21:54:19', 3, 'TRUCK', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-19 18:02:57.335590', 12, '2024-07-19 19:13:56.760908', 12,
         '2024-08-15 22:19:09', 8, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-30 17:35:51.748278', 12,
             '2024-09-16 10:51:34.547827', 12, '2024-01-16 07:45:39', 4,
             'MOTORBIKE', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-05 16:29:10.076348', 12,
             '2024-08-31 19:50:34.225842', 12, '2024-08-12 13:06:20', 8,
             'FOOT', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-10 13:31:43.933657', 12,
             '2024-04-16 15:48:07.126108', 12, '2024-02-11 08:19:36', 4,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-28 14:03:14.943039', 12,
             '2024-10-02 21:30:28.466850', 12, '2024-02-29 00:54:19', 10,
             'TRUCK', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-14 08:44:25.640939', 12, '2024-06-23 01:49:55.923345', 12,
         '2024-09-16 23:43:16', 1, 'PICKUP', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-28 22:21:18.608538', 12, '2024-04-29 19:43:56.519961', 12,
         '2024-08-31 22:00:23', 3, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'BY7406');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-23 17:56:01.815934', 12, '2024-07-18 11:51:43.992525', 12,
         '2024-06-24 05:36:44', 1, 'TRUCK', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-10 11:25:26.991509', 12,
             '2024-05-21 06:37:33.215944', 12, '2024-05-15 12:46:17', 3,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-22 00:25:14.345150', 12, '2024-11-02 08:48:32.033880', 12,
         '2024-10-26 02:46:52', 10, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-07 23:27:26.336926', 12,
             '2024-02-02 00:54:54.732016', 12, '2024-07-10 13:48:27', 2,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-07 00:33:32.707590', 12, '2024-02-01 18:57:15.834842', 12,
         '2024-08-04 04:02:36', 7, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'HT3387');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-09 06:31:05.037968', 12, '2024-04-30 15:58:24.682640', 12,
         '2024-06-14 03:52:20', 3, 'TRUCK', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-09 06:31:05.037968', 12,
             '2024-03-02 10:37:36.352220', 12, '2024-06-14 04:52:20', 6,
             'TRUCK', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-08 23:04:10.467121', 12, '2024-01-23 13:31:43.757039', 12,
         '2024-08-30 06:19:48', 1, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'HI8627');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-08 10:22:41.938963', 12, '2024-01-25 21:43:45.440622', 12,
         '2024-08-09 16:47:38', 2, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-20 11:29:13.905781', 12, '2024-01-18 05:01:17.115822', 12,
         '2024-09-14 21:50:02', 7, 'CAR', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NE9377');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-12 14:59:54.541545', 12, '2024-08-15 22:03:40.765967', 12,
         '2024-08-12 00:18:25', 9, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-06 04:16:36.558266', 12, '2024-08-04 15:02:13.652074', 12,
         '2024-06-15 03:51:41', 8, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-25 04:11:06.935834', 12, '2024-03-09 09:19:53.347235', 12,
         '2024-04-05 04:36:59', 10, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-19 14:36:11.751469', 12, '2024-10-27 18:58:12.017268', 12,
         '2024-10-22 16:03:51', 9, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'PG1536');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-06 12:33:10.588821', 12, '2024-05-22 05:06:38.163143', 12,
         '2024-03-28 18:41:53', 4, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-09 13:02:44.870314', 12,
             '2024-07-21 11:38:30.028698', 12, '2024-02-17 01:34:29', 5,
             'PICKUP', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-14 20:24:05.865576', 12,
             '2024-03-18 16:50:03.102569', 12, '2024-03-26 03:49:46', 5,
             'CAR', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-15 21:30:58.304305', 12,
             '2024-10-10 12:54:37.158423', 12, '2024-09-20 06:24:53', 2,
             'FOOT', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-30 10:03:38.554816', 12,
             '2024-08-02 10:03:01.663898', 12, '2024-09-05 18:15:18', 5,
             'FOOT', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-26 20:45:22.000236', 12, '2024-09-06 11:34:31.036923', 12,
         '2024-10-08 03:57:27', 9, 'PICKUP', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-22 09:21:52.403764', 12, '2024-10-01 08:14:39.426693', 12,
         '2024-06-26 21:41:15', 2, 'PICKUP', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-18 14:33:46.003296', 12, '2024-01-17 21:51:12.908473', 12,
         '2024-08-15 00:09:47', 2, 'TRUCK', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-08 05:26:44.208197', 12,
             '2024-05-09 18:40:17.728508', 12, '2024-06-02 18:06:00', 7,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-07 23:52:13.730826', 12, '2024-07-29 05:17:15.585693', 12,
         '2024-07-22 13:05:15', 8, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-03 05:11:33.149560', 12, '2024-05-01 22:38:08.447578', 12,
         '2024-05-18 08:47:07', 9, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'WL9225');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-13 08:47:13.309350', 12, '2024-05-13 03:51:22.642456', 12,
         '2024-05-26 04:38:39', 6, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-16 03:26:22.046247', 12, '2024-01-25 00:40:46.955239', 12,
         '2024-03-04 07:07:02', 7, 'CAR', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'SQ4649');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-31 21:00:14.396508', 12,
             '2024-04-03 00:48:06.611886', 12, '2024-01-24 10:37:26', 1,
             'FOOT', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-31 05:56:09.975694', 12, '2024-03-23 20:16:42.906382', 12,
         '2024-02-16 03:16:19', 1, 'FOOT', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-19 06:52:55.185324', 12, '2024-10-21 11:22:22.388063', 12,
         '2024-04-11 17:24:49', 1, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-18 14:33:46.003296', 12,
             '2024-04-30 04:33:28.536283', 12, '2024-08-15 04:09:47', 9,
             'TRUCK', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-12 13:07:08.811527', 12, '2024-01-22 20:55:00.098991', 12,
         '2024-08-16 01:00:07', 1, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-29 16:01:42.996965', 12, '2024-06-02 04:57:39.082429', 12,
         '2024-09-03 10:29:48', 8, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-14 08:44:25.640939', 12,
             '2024-03-03 09:38:21.298850', 12, '2024-09-17 00:43:16', 8,
             'PICKUP', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-27 13:58:25.336275', 12, '2024-10-16 06:10:59.350908', 12,
         '2024-06-17 20:11:08', 4, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-21 07:52:09.518595', 12,
             '2024-04-11 06:20:23.917538', 12, '2024-10-25 15:39:44', 8,
             'PICKUP', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-18 02:42:32.582426', 12,
             '2024-01-12 07:01:34.965131', 12, '2024-04-30 05:04:11', 6,
             'PICKUP', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-14 12:23:45.486918', 12,
             '2024-04-29 04:18:59.339644', 12, '2024-06-09 01:06:47', 2,
             'MOTORBIKE', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-04 13:25:35.604544', 12, '2024-07-04 16:19:16.098609', 12,
         '2024-03-21 11:08:59', 9, 'MOTORBIKE', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-17 20:41:10.459120', 12, '2024-07-14 11:40:08.530469', 12,
         '2024-01-03 01:20:21', 7, 'MOTORBIKE', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-29 16:01:42.996965', 12,
             '2024-04-17 21:26:36.203785', 12, '2024-09-03 12:29:48', 6,
             'FOOT', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-20 11:29:13.905781', 12,
             '2024-07-27 18:55:53.133112', 12, '2024-09-14 22:50:02', 1,
             'CAR', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-06 07:00:28.672360', 12, '2024-02-08 15:58:42.532307', 12,
         '2024-04-23 06:07:11', 8, 'FOOT', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-08 01:47:45.709128', 12, '2024-05-21 04:20:57.175325', 12,
         '2024-03-23 14:29:13', 1, 'FOOT', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-30 15:25:41.488257', 12,
             '2024-01-01 02:52:37.869178', 12, '2024-03-23 00:51:22', 1,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-15 10:31:44.439170', 12, '2024-01-27 11:24:20.504921', 12,
         '2024-02-10 08:36:50', 8, 'MOTORBIKE', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-07 07:01:23.931044', 12, '2024-10-25 02:09:04.514181', 12,
         '2024-10-04 15:18:10', 3, 'PICKUP', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-04 04:16:01.407514', 12, '2024-03-02 00:54:49.820588', 12,
         '2024-01-09 20:48:05', 6, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-23 17:56:01.815934', 12,
             '2024-07-26 01:59:42.314364', 12, '2024-06-24 08:36:44', 6,
             'TRUCK', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-02 12:03:44.780591', 12, '2024-11-02 21:42:36.229551', 12,
         '2024-02-19 20:44:17', 6, 'MOTORBIKE', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-27 18:37:00.045873', 12,
             '2024-06-04 04:28:27.548944', 12, '2024-06-01 22:52:28', 10,
             'TRUCK', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-08 23:04:10.467121', 12,
             '2024-02-29 01:28:10.391716', 12, '2024-08-30 07:19:48', 8,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'HI8627');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-23 02:45:20.053523', 12, '2024-10-06 10:37:12.732098', 12,
         '2024-09-29 00:49:54', 5, 'MOTORBIKE', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-14 00:18:35.291797', 12,
             '2024-10-26 10:58:35.864919', 12, '2024-10-19 01:18:01', 2,
             'TRUCK', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-06 11:14:25.145890', 12,
             '2024-02-23 01:57:56.787296', 12, '2024-07-03 21:46:41', 10,
             'TRUCK', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-26 22:31:14.245238', 12, '2024-04-09 14:12:48.355390', 12,
         '2024-10-06 03:56:46', 5, 'CAR', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'OH4927');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-12 15:36:17.235058', 12,
             '2024-03-30 02:26:34.408314', 12, '2024-10-16 08:22:54', 4,
             'MOTORBIKE', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-16 03:26:22.046247', 12,
             '2024-05-27 09:07:04.224500', 12, '2024-03-04 11:07:02', 10,
             'CAR', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-31 05:56:09.975694', 12,
             '2024-02-09 01:20:27.300502', 12, '2024-02-16 07:16:19', 4,
             'FOOT', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-04 04:16:01.407514', 12,
             '2024-02-18 23:54:57.778125', 12, '2024-01-09 23:48:05', 3,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-11 17:54:51.021984', 12, '2024-08-01 16:03:52.555176', 12,
         '2024-10-05 17:01:38', 6, 'PICKUP', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-15 10:31:44.439170', 12,
             '2024-06-16 23:05:00.154676', 12, '2024-02-10 10:36:50', 3,
             'MOTORBIKE', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-25 11:22:58.778021', 12, '2024-03-06 09:58:02.683919', 12,
         '2024-01-19 00:18:33', 1, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-02 07:50:07.178941', 12, '2024-05-03 12:49:28.012229', 12,
         '2024-03-04 01:11:28', 7, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-26 20:45:22.000236', 12,
             '2024-03-17 10:55:16.093650', 12, '2024-10-08 07:57:27', 7,
             'PICKUP', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-06 16:14:51.725413', 12, '2024-05-14 00:36:20.516741', 12,
         '2024-05-12 17:53:44', 4, 'TRUCK', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-13 06:21:16.060767', 12, '2024-02-04 08:19:09.682160', 12,
         '2024-09-27 05:08:51', 10, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-08 01:47:45.709128', 12,
             '2024-02-10 12:34:22.849904', 12, '2024-03-23 15:29:13', 2,
             'FOOT', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-07 01:16:40.942330', 12, '2024-08-03 22:13:13.567571', 12,
         '2024-08-31 19:59:58', 9, 'TRUCK', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-01 05:46:13.335456', 12,
             '2024-09-26 06:28:45.291710', 12, '2024-08-02 06:18:23', 10,
             'CAR', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-07 07:01:23.931044', 12,
             '2024-08-17 12:59:50.138610', 12, '2024-10-04 18:18:10', 6,
             'PICKUP', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-19 18:02:57.335590', 12,
             '2024-02-06 06:23:01.328687', 12, '2024-08-16 00:19:09', 8,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-07 01:16:40.942330', 12,
             '2024-07-19 04:39:44.090952', 12, '2024-08-31 23:59:58', 6,
             'TRUCK', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-19 14:36:11.751469', 12,
             '2024-07-29 16:36:27.257882', 12, '2024-10-22 19:03:51', 6,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-16 01:02:09.743246', 12, '2024-03-21 14:18:56.483316', 12,
         '2024-05-26 11:27:11', 6, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-15 15:34:31.703970', 12, '2024-10-03 00:47:52.540697', 12,
         '2024-01-11 02:08:52', 6, 'MOTORBIKE', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-22 00:25:14.345150', 12,
             '2024-06-03 22:36:13.795072', 12, '2024-10-26 05:46:52', 6,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-02 12:03:44.780591', 12,
             '2024-03-01 05:29:54.702340', 12, '2024-02-20 00:44:17', 2,
             'MOTORBIKE', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-23 02:10:52.673370', 12, '2024-05-14 18:09:19.307322', 12,
         '2024-07-06 21:35:42', 9, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-25 11:22:58.778021', 12,
             '2024-03-21 18:04:53.265161', 12, '2024-01-19 04:18:33', 3,
             'PICKUP', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-16 00:12:07.124109', 12, '2024-01-21 21:11:34.740207', 12,
         '2024-03-20 14:35:04', 5, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-23 02:45:20.053523', 12,
             '2024-09-16 05:28:46.709498', 12, '2024-09-29 03:49:54', 6,
             'MOTORBIKE', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-17 02:11:35.413721', 12, '2024-08-22 23:11:35.253770', 12,
         '2024-08-30 05:15:56', 9, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-16 11:55:36.793395', 12, '2024-10-02 07:29:58.150127', 12,
         '2024-05-26 14:32:23', 8, 'TRUCK', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-23 07:53:40.487509', 12, '2024-02-23 08:03:26.111715', 12,
         '2024-09-09 12:30:06', 6, 'FOOT', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-21 08:56:17.323618', 12, '2024-09-21 07:38:06.353427', 12,
         '2024-07-14 00:50:03', 6, 'TRUCK', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-10 23:44:30.061052', 12, '2024-09-18 13:58:57.350295', 12,
         '2024-08-23 05:49:22', 3, 'PICKUP', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-01 22:34:38.606076', 12,
             '2024-10-02 11:15:21.476642', 12, '2024-09-29 14:25:58', 2,
             'TRUCK', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-27 08:47:09.819160', 12, '2024-10-02 06:22:55.883771', 12,
         '2024-01-23 03:33:13', 5, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-06 05:02:20.666321', 12, '2024-06-01 06:14:40.270867', 12,
         '2024-07-10 20:01:35', 10, 'TRUCK', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-22 16:12:28.184069', 12, '2024-05-05 03:21:44.466839', 12,
         '2024-03-14 14:36:15', 8, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-16 11:55:36.793395', 12,
             '2024-08-10 22:40:03.620423', 12, '2024-05-26 17:32:23', 10,
             'TRUCK', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-22 18:38:47.156368', 12, '2024-09-04 10:38:33.179531', 12,
         '2024-06-25 04:40:14', 3, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-22 09:21:52.403764', 12,
             '2024-10-16 08:22:21.035147', 12, '2024-06-27 01:41:15', 5,
             'PICKUP', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-03 20:22:15.437033', 12, '2024-02-10 10:32:55.758474', 12,
         '2024-02-21 07:44:31', 6, 'MOTORBIKE', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-06 04:16:36.558266', 12,
             '2024-06-02 04:19:01.860644', 12, '2024-06-15 06:51:41', 10,
             'TRUCK', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-10 02:19:55.675759', 12, '2024-02-16 15:50:33.606692', 12,
         '2024-07-21 17:49:12', 5, 'MOTORBIKE', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-25 04:11:06.935834', 12,
             '2024-03-11 02:23:58.384142', 12, '2024-04-05 06:36:59', 7,
             'MOTORBIKE', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-17 20:41:10.459120', 12,
             '2024-01-16 03:30:07.775952', 12, '2024-01-03 03:20:21', 1,
             'MOTORBIKE', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-06 12:33:10.588821', 12,
             '2024-09-09 14:40:48.247081', 12, '2024-03-28 19:41:53', 8,
             'MOTORBIKE', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-28 21:34:45.827510', 12, '2024-09-07 02:59:28.900939', 12,
         '2024-06-28 20:54:23', 9, 'FOOT', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-10 02:19:55.675759', 12,
             '2024-06-25 09:28:01.655933', 12, '2024-07-21 19:49:12', 6,
             'MOTORBIKE', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-20 18:01:46.867133', 12, '2024-09-06 19:25:43.283128', 12,
         '2024-09-04 06:54:19', 2, 'TRUCK', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-08 22:43:15.041683', 12, '2024-07-21 03:49:10.888466', 12,
         '2024-06-20 06:46:38', 4, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-15 04:53:21.278270', 12, '2024-05-17 03:56:26.875638', 12,
         '2024-05-20 10:44:31', 3, 'PICKUP', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-11 17:54:51.021984', 12,
             '2024-04-20 05:29:04.197065', 12, '2024-10-05 21:01:38', 8,
             'PICKUP', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-25 18:05:29.296331', 12, '2024-04-10 15:12:18.752445', 12,
         '2024-01-17 05:45:37', 7, 'TRUCK', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-29 14:37:50.527039', 12, '2024-03-15 21:06:05.101709', 12,
         '2024-04-03 07:50:17', 7, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-15 04:53:21.278270', 12,
             '2024-10-02 05:40:36.119635', 12, '2024-05-20 11:44:31', 6,
             'PICKUP', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-23 07:53:40.487509', 12,
             '2024-07-21 07:09:49.756111', 12, '2024-09-09 13:30:06', 1,
             'FOOT', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-06 16:14:51.725413', 12,
             '2024-03-24 09:20:23.986211', 12, '2024-05-12 19:53:44', 9,
             'TRUCK', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-06 05:02:20.666321', 12,
             '2024-01-07 01:18:42.084453', 12, '2024-07-10 23:01:35', 2,
             'TRUCK', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-03 21:04:36.544803', 12, '2024-04-05 13:19:47.703197', 12,
         '2024-07-29 20:41:57', 1, 'FOOT', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-05 18:06:12.117968', 12, '2024-03-02 03:47:30.044121', 12,
         '2024-07-22 09:45:20', 5, 'FOOT', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-15 15:34:31.703970', 12,
             '2024-05-18 12:09:11.104546', 12, '2024-01-11 03:08:52', 3,
             'MOTORBIKE', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-26 06:01:03.608948', 12, '2024-02-28 15:09:40.666370', 12,
         '2024-09-11 08:22:24', 10, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-25 09:56:17.013341', 12, '2024-04-15 18:36:28.566039', 12,
         '2024-08-04 17:09:20', 3, 'MOTORBIKE', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-29 04:32:25.715113', 12, '2024-03-12 14:00:04.643020', 12,
         '2024-01-30 23:00:01', 7, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-24 09:39:09.465170', 12, '2024-05-03 10:24:03.125629', 12,
         '2024-04-07 08:59:55', 4, 'MOTORBIKE', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-13 08:47:13.309350', 12,
             '2024-09-07 19:55:26.038025', 12, '2024-05-26 05:38:39', 2,
             'TRUCK', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-27 08:47:09.819160', 12,
             '2024-10-11 05:25:31.907281', 12, '2024-01-23 06:33:13', 4,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-20 16:40:00.697789', 12, '2024-02-18 08:25:11.659598', 12,
         '2024-01-07 09:02:09', 2, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-27 20:20:09.452286', 12,
             '2024-07-28 00:28:48.228105', 12, '2024-06-27 23:29:32', 1,
             'FOOT', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-24 10:38:18.704049', 12, '2024-03-28 19:24:37.003911', 12,
         '2024-06-11 01:14:33', 4, 'PICKUP', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-02 12:01:17.037400', 12, '2024-09-14 02:35:02.487914', 12,
         '2024-03-24 19:32:45', 7, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-03 20:22:15.437033', 12,
             '2024-10-27 20:34:19.063522', 12, '2024-02-21 11:44:31', 5,
             'MOTORBIKE', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-11-02 13:25:10.407541', 12, '2024-01-30 11:12:34.233540', 12,
         '2024-09-16 21:54:54', 6, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-27 11:00:13.930670', 12, '2024-10-10 08:34:05.314212', 12,
         '2024-07-09 12:50:46', 1, 'MOTORBIKE', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-13 01:58:50.164503', 12, '2024-06-20 22:08:55.981291', 12,
         '2024-01-15 13:26:43', 7, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-10 23:44:30.061052', 12,
             '2024-05-28 20:49:07.315621', 12, '2024-08-23 06:49:22', 3,
             'PICKUP', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-17 01:28:42.364779', 12,
             '2024-10-30 06:51:13.584264', 12, '2024-05-24 08:40:01', 8,
             'CAR', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-25 11:01:00.388446', 12, '2024-08-10 12:56:39.318935', 12,
         '2024-02-15 15:42:33', 10, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-24 09:39:09.465170', 12,
             '2024-01-03 01:31:32.682515', 12, '2024-04-07 09:59:55', 5,
             'MOTORBIKE', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-19 21:51:57.273698', 12, '2024-04-24 19:38:59.901710', 12,
         '2024-06-17 18:21:17', 1, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'UX3765');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-19 18:27:06.591296', 12, '2024-06-17 06:06:16.958509', 12,
         '2024-10-26 10:27:22', 10, 'FOOT', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-25 18:05:29.296331', 12,
             '2024-01-02 13:10:10.119429', 12, '2024-01-17 08:45:37', 4,
             'TRUCK', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-20 18:01:46.867133', 12,
             '2024-08-18 11:14:59.185577', 12, '2024-09-04 09:54:19', 2,
             'TRUCK', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-04 12:18:55.029933', 12, '2024-08-08 08:01:44.052986', 12,
         '2024-08-10 20:41:15', 3, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'MW2355');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-25 17:43:23.693251', 12, '2024-09-05 21:06:14.877211', 12,
         '2024-02-28 15:28:29', 8, 'CAR', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'ZW6807');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-10 22:27:04.314565', 12, '2024-03-19 13:34:50.527355', 12,
         '2024-10-11 08:04:36', 1, 'FOOT', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-29 14:16:01.737431', 12, '2024-05-01 20:37:07.775257', 12,
         '2024-06-06 16:53:06', 6, 'PICKUP', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-28 21:34:45.827510', 12,
             '2024-04-26 12:57:54.053315', 12, '2024-06-28 21:54:23', 9,
             'FOOT', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-25 09:56:17.013341', 12,
             '2024-09-04 09:43:23.841176', 12, '2024-08-04 18:09:20', 3,
             'MOTORBIKE', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-16 19:44:06.303382', 12, '2024-04-04 11:46:05.559677', 12,
         '2024-06-04 10:50:46', 5, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'ZZ5035');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-27 11:16:45.290488', 12, '2024-09-20 07:29:49.876283', 12,
         '2024-10-30 12:49:58', 6, 'FOOT', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-12 11:13:35.074635', 12,
             '2024-10-23 11:09:50.462423', 12, '2024-07-12 19:34:00', 10,
             'MOTORBIKE', 4, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-16 11:30:17.570379', 12,
             '2024-01-11 00:03:23.576077', 12, '2024-04-05 14:45:17', 1,
             'TRUCK', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-13 15:52:42.035695', 12,
             '2024-10-20 02:37:16.570518', 12, '2024-05-01 06:26:37', 7,
             'PICKUP', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-11 16:41:00.777181', 12, '2024-05-05 02:53:01.972555', 12,
         '2024-06-17 19:17:53', 10, 'MOTORBIKE', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-03 00:39:30.656207', 12, '2024-01-06 17:15:25.665576', 12,
         '2024-01-30 18:34:33', 8, 'PICKUP', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-02 12:01:17.037400', 12,
             '2024-07-30 19:32:03.102755', 12, '2024-03-24 23:32:45', 6,
             'PICKUP', 7, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-11 16:41:00.777181', 12,
             '2024-10-21 23:22:16.240517', 12, '2024-06-17 22:17:53', 8,
             'MOTORBIKE', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-11-02 13:25:10.407541', 12,
             '2024-04-29 03:33:58.281338', 12, '2024-09-17 01:54:54', 10,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-18 02:10:51.363216', 12, '2024-04-07 04:25:43.287404', 12,
         '2024-03-27 21:44:55', 5, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'TI4239');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-20 21:49:54.277238', 12, '2024-07-26 07:18:24.888782', 12,
         '2024-02-13 04:13:55', 1, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-25 11:01:00.388446', 12,
             '2024-06-25 17:19:13.591463', 12, '2024-02-15 16:42:33', 1,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-03 00:39:30.656207', 12,
             '2024-09-11 03:27:54.013698', 12, '2024-01-30 19:34:33', 3,
             'PICKUP', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-03 23:35:46.624783', 12, '2024-09-01 09:59:05.357681', 12,
         '2024-04-18 09:23:32', 3, 'CAR', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'FX5691');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-19 07:45:33.706357', 12, '2024-05-11 07:27:51.878747', 12,
         '2024-05-09 00:29:01', 3, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-21 03:07:46.513113', 12, '2024-04-26 14:08:45.131519', 12,
         '2024-09-02 23:58:00', 6, 'MOTORBIKE', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-17 19:28:23.273791', 12,
             '2024-02-29 12:46:53.462032', 12, '2024-02-06 13:37:29', 2,
             'PICKUP', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-03 21:04:36.544803', 12,
             '2024-04-08 20:40:34.846533', 12, '2024-07-30 00:41:57', 4,
             'FOOT', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-01 00:01:15.361341', 12, '2024-01-04 18:31:22.277119', 12,
         '2024-06-03 18:22:27', 1, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'KX4725');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-05 18:06:12.117968', 12,
             '2024-08-10 17:51:51.736572', 12, '2024-07-22 12:45:20', 9,
             'FOOT', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-28 07:06:50.699128', 12,
             '2024-08-14 11:38:08.384302', 12, '2024-04-10 09:15:57', 3,
             'PICKUP', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-27 12:22:49.811508', 12,
             '2024-02-22 05:13:50.226559', 12, '2024-03-30 09:16:52', 2,
             'TRUCK', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-02 17:45:23.114265', 12, '2024-07-24 05:47:51.754124', 12,
         '2024-05-15 21:41:31', 6, 'TRUCK', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-21 03:07:46.513113', 12,
             '2024-10-09 21:20:54.032450', 12, '2024-09-03 02:58:00', 5,
             'MOTORBIKE', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-01 08:43:31.191960', 12,
             '2024-03-24 04:27:33.274232', 12, '2024-04-09 20:04:20', 6,
             'TRUCK', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-21 01:59:42.164989', 12, '2024-06-01 15:43:30.211631', 12,
         '2024-09-28 09:40:08', 6, 'MOTORBIKE', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-08 19:46:46.281892', 12, '2024-06-12 05:18:20.678772', 12,
         '2024-10-24 09:20:23', 3, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-19 06:52:55.185324', 12,
             '2024-07-26 09:01:41.134260', 12, '2024-04-11 21:24:49', 6,
             'FOOT', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-30 01:19:08.457849', 12, '2024-09-09 02:07:33.809375', 12,
         '2024-07-27 16:04:25', 10, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-03 23:35:46.624783', 12,
             '2024-05-31 05:42:43.836793', 12, '2024-04-18 10:23:32', 5,
             'CAR', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'FX5691');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-07 01:53:23.506075', 12, '2024-02-26 05:50:59.525442', 12,
         '2024-06-27 22:40:16', 4, 'TRUCK', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-20 10:30:56.909308', 12, '2024-09-20 15:52:11.806489', 12,
         '2024-08-22 09:49:01', 7, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'KL4525');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-29 08:15:35.602344', 12, '2024-04-05 17:03:46.474009', 12,
         '2024-03-10 18:42:48', 1, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'BL9629');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-26 14:17:15.049114', 12, '2024-09-16 04:36:33.401024', 12,
         '2024-10-30 12:43:52', 2, 'MOTORBIKE', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-25 17:43:23.693251', 12,
             '2024-10-13 16:08:42.315387', 12, '2024-02-28 18:28:29', 8,
             'CAR', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-16 04:27:17.531928', 12, '2024-02-08 04:31:35.158131', 12,
         '2024-06-18 14:29:32', 7, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-04 01:44:36.741278', 12, '2024-02-07 12:23:21.237296', 12,
         '2024-02-25 03:06:48', 8, 'MOTORBIKE', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-24 10:38:18.704049', 12,
             '2024-07-10 01:37:20.054825', 12, '2024-06-11 02:14:33', 10,
             'PICKUP', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-04 23:27:01.184098', 12, '2024-09-02 12:27:23.249368', 12,
         '2024-08-04 04:24:53', 7, 'FOOT', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-02 17:45:23.114265', 12,
             '2024-07-27 11:10:59.486563', 12, '2024-05-16 01:41:31', 7,
             'TRUCK', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-14 15:23:54.264033', 12, '2024-07-28 17:38:21.012332', 12,
         '2024-05-21 13:19:56', 4, 'CAR', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'FB7763');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-04 01:44:36.741278', 12,
             '2024-06-04 05:52:14.040518', 12, '2024-02-25 05:06:48', 7,
             'MOTORBIKE', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-09 16:54:00.806163', 12, '2024-08-20 10:35:26.353301', 12,
         '2024-11-01 14:05:24', 5, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-14 17:38:46.258530', 12, '2024-02-27 07:27:46.705567', 12,
         '2024-10-24 08:09:59', 2, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-04 05:16:50.402310', 12, '2024-04-04 13:44:06.702600', 12,
         '2024-01-28 14:11:55', 8, 'FOOT', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-04 12:18:55.029933', 12,
             '2024-08-14 16:49:44.136421', 12, '2024-08-10 23:41:15', 7,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-01 17:52:39.462528', 12, '2024-07-23 02:22:55.230081', 12,
         '2024-07-06 19:25:35', 8, 'MOTORBIKE', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-04 22:06:55.658370', 12, '2024-08-16 10:57:33.457837', 12,
         '2024-05-16 04:08:54', 2, 'MOTORBIKE', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-07 01:53:23.506075', 12,
             '2024-10-14 21:54:36.199048', 12, '2024-06-27 23:40:16', 8,
             'TRUCK', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-18 07:10:24.885564', 12, '2024-03-25 01:51:29.066736', 12,
         '2024-02-22 17:22:42', 5, 'FOOT', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-10 22:27:04.314565', 12,
             '2024-01-21 10:54:47.125106', 12, '2024-10-11 12:04:36', 5,
             'FOOT', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-09 11:44:13.148887', 12, '2024-01-15 01:38:06.992600', 12,
         '2024-10-23 03:55:11', 5, 'MOTORBIKE', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-29 14:16:01.737431', 12,
             '2024-06-06 23:37:20.843403', 12, '2024-06-06 17:53:06', 10,
             'PICKUP', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-14 08:58:15.533698', 12, '2024-03-24 13:39:01.550121', 12,
         '2024-10-07 23:23:37', 10, 'FOOT', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-28 06:10:10.008104', 12, '2024-05-11 23:45:51.348273', 12,
         '2024-05-24 04:52:32', 5, 'MOTORBIKE', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-05 12:05:44.037947', 12, '2024-07-11 08:43:36.904327', 12,
         '2024-08-10 23:07:14', 10, 'CAR', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NP8299');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-05 12:05:44.037947', 12,
             '2024-08-19 11:11:12.456087', 12, '2024-08-11 02:07:14', 1,
             'CAR', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NP8299');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-02 06:45:53.777795', 12, '2024-10-19 01:40:32.779673', 12,
         '2024-09-18 03:39:48', 5, 'FOOT', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-20 17:33:20.956504', 12, '2024-06-08 13:20:05.440938', 12,
         '2024-06-20 14:40:29', 3, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-12 13:07:08.811527', 12,
             '2024-09-05 07:54:04.135126', 12, '2024-08-16 05:00:07', 8,
             'FOOT', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-05 15:05:11.032119', 12, '2024-09-13 01:46:15.456937', 12,
         '2024-07-03 19:51:33', 7, 'PICKUP', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-26 14:59:53.954434', 12,
             '2024-04-14 14:34:10.770014', 12, '2024-02-04 18:01:08', 9,
             'CAR', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'SE1275');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-20 23:55:37.360918', 12, '2024-01-16 06:08:27.961020', 12,
         '2024-02-25 12:26:34', 1, 'FOOT', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-04 05:16:50.402310', 12,
             '2024-05-17 08:55:44.487404', 12, '2024-01-28 17:11:55', 6,
             'FOOT', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-12 04:12:59.344930', 12, '2024-06-25 22:21:58.628922', 12,
         '2024-08-04 01:45:11', 7, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'CQ8823');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-16 19:44:06.303382', 12,
             '2024-07-25 10:52:51.084994', 12, '2024-06-04 13:50:46', 2,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-07 21:43:56.398673', 12, '2024-08-12 05:38:06.545637', 12,
         '2024-10-12 22:50:44', 10, 'MOTORBIKE', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-22 02:29:59.400983', 12, '2024-07-17 05:40:14.324495', 12,
         '2024-06-07 20:22:58', 2, 'MOTORBIKE', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-16 11:25:11.590224', 12, '2024-09-18 09:10:07.787084', 12,
         '2024-05-28 19:31:46', 5, 'CAR', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'CS4456');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-27 06:45:48.576424', 12, '2024-05-14 06:57:58.205172', 12,
         '2024-10-19 01:15:24', 10, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'IE2682');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-13 13:47:07.604827', 12, '2024-08-10 23:03:09.042808', 12,
         '2024-02-16 13:19:32', 6, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-31 09:46:14.771872', 12,
             '2024-02-23 03:45:53.870560', 12, '2024-02-16 04:58:25', 3,
             'TRUCK', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-24 16:15:31.546575', 12, '2024-07-16 22:46:09.471263', 12,
         '2024-01-14 15:48:34', 1, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'DS4898');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-07 01:13:50.528886', 12, '2024-04-05 15:18:21.723329', 12,
         '2024-02-28 16:42:49', 7, 'PICKUP', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-27 19:32:46.532361', 12, '2024-05-09 08:08:41.201479', 12,
         '2024-01-15 13:14:37', 2, 'CAR', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'PS6303');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-13 11:56:42.252628', 12, '2024-07-28 19:31:26.402990', 12,
         '2024-01-17 19:33:15', 7, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-28 08:16:59.303004', 12, '2024-06-10 13:31:24.567615', 12,
         '2024-02-08 13:38:10', 10, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-05-27 19:32:46.532361', 12,
             '2024-03-07 21:57:08.141774', 12, '2024-01-15 17:14:37', 4,
             'CAR', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-20 17:33:20.956504', 12,
             '2024-07-08 19:00:37.178837', 12, '2024-06-20 15:40:29', 6,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-16 11:25:11.590224', 12,
             '2024-09-14 15:54:10.248448', 12, '2024-05-28 23:31:46', 9,
             'CAR', 8, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-06 15:48:40.577946', 12, '2024-04-13 23:58:21.787736', 12,
         '2024-06-17 12:55:11', 7, 'TRUCK', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-06 17:41:17.081463', 12, '2024-07-19 10:52:32.715122', 12,
         '2024-02-28 23:28:34', 6, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'RP1008');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-20 10:30:56.909308', 12,
             '2024-06-05 05:06:12.288414', 12, '2024-08-22 10:49:01', 10,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'KL4525');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-24 16:15:31.546575', 12,
             '2024-04-25 05:47:20.065982', 12, '2024-01-14 19:48:34', 2,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-11 08:51:18.048863', 12, '2024-03-14 12:21:54.825341', 12,
         '2024-10-22 11:40:51', 8, 'CAR', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'LE9882');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-28 22:21:18.608538', 12,
             '2024-07-12 04:40:40.883868', 12, '2024-09-01 02:00:23', 8,
             'CAR', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-02-27 13:58:25.336275', 12,
             '2024-02-25 20:25:48.297470', 12, '2024-06-17 22:11:08', 1,
             'FOOT', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-17 17:20:22.003591', 12, '2024-06-19 04:14:05.987284', 12,
         '2024-02-22 15:46:15', 5, 'FOOT', 7, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-15 10:19:42.330492', 12, '2024-05-10 21:29:49.440935', 12,
         '2024-05-05 06:49:44', 9, 'TRUCK', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-11 16:30:19.894781', 12, '2024-06-11 03:28:11.122605', 12,
         '2024-07-27 02:02:42', 6, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-17 11:19:01.259886', 12, '2024-07-10 01:05:47.938926', 12,
         '2024-04-29 16:13:44', 7, 'CAR', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'DH1313');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-30 03:43:17.616138', 12, '2024-07-13 08:18:29.136641', 12,
         '2024-06-23 01:18:59', 8, 'PICKUP', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-28 21:33:35.227525', 12, '2024-04-04 12:26:40.838056', 12,
         '2024-03-12 09:40:39', 4, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-10-13 11:45:46.139120', 12, '2024-09-18 08:07:43.249590', 12,
         '2024-05-08 16:30:17', 9, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-11 15:09:43.889626', 12, '2024-09-24 09:45:10.302149', 12,
         '2024-04-29 17:31:09', 4, 'TRUCK', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-01 03:12:14.377989', 12, '2024-02-28 07:46:08.207705', 12,
         '2024-09-07 00:12:17', 1, 'CAR', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'UV8822');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-01 03:12:14.377989', 12,
             '2024-02-29 17:06:08.912209', 12, '2024-09-07 02:12:17', 6,
             'CAR', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-13 11:56:42.252628', 12,
             '2024-08-06 12:34:48.103097', 12, '2024-01-17 21:33:15', 10,
             'FOOT', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-01-26 22:31:14.245238', 12,
             '2024-06-23 03:40:26.569454', 12, '2024-10-06 06:56:46', 3,
             'CAR', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-13 01:24:04.614751', 12,
             '2024-08-10 23:55:00.269205', 12, '2024-01-03 10:45:59', 10,
             'FOOT', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-02 07:50:07.178941', 12,
             '2024-09-11 11:39:59.678132', 12, '2024-03-04 04:11:28', 6,
             'PICKUP', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-16 01:02:09.743246', 12,
             '2024-02-29 01:00:17.810579', 12, '2024-05-26 14:27:11', 3,
             'MOTORBIKE', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-23 14:02:07.649906', 12, '2024-01-16 23:26:55.309221', 12,
         '2024-01-26 11:59:53', 3, 'CAR', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'YU3184');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-07 01:13:50.528886', 12,
             '2024-09-18 21:39:58.707949', 12, '2024-02-28 18:42:49', 9,
             'PICKUP', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-23 02:10:52.673370', 12,
             '2024-08-28 06:28:49.629120', 12, '2024-07-07 00:35:42', 2,
             'TRUCK', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-16 00:12:07.124109', 12,
             '2024-09-03 17:49:52.964567', 12, '2024-03-20 18:35:04', 6,
             'MOTORBIKE', 2, 'EXIT', false,
             true, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-06-17 17:27:35.849452', 12, '2024-01-22 09:28:26.415597', 12,
         '2024-06-21 14:40:17', 8, 'FOOT', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-14 23:40:42.513381', 12, '2024-05-25 11:41:13.343849', 12,
         '2024-08-23 19:12:40', 2, 'CAR', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'DK5970');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-14 15:23:54.264033', 12,
             '2024-01-31 04:44:04.162576', 12, '2024-05-21 16:19:56', 4,
             'CAR', 7, 'EXIT', false,
             false, NULL, NULL, NULL, 'FB7763');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-10-29 08:15:35.602344', 12,
             '2024-08-31 01:55:27.098529', 12, '2024-03-10 22:42:48', 5,
             'CAR', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'BL9629');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-08-14 23:40:42.513381', 12,
             '2024-06-22 13:53:03.190008', 12, '2024-08-23 21:12:40', 4,
             'CAR', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-13 06:17:39.584816', 12, '2024-03-09 08:17:03.873266', 12,
         '2024-03-01 11:04:47', 10, 'PICKUP', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-28 03:21:18.305026', 12, '2024-07-04 23:53:50.437078', 12,
         '2024-02-17 10:35:45', 7, 'FOOT', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-27 21:40:24.048826', 12,
             '2024-09-18 02:37:54.094400', 12, '2024-04-27 01:40:44', 7,
             'FOOT', 6, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-28 21:48:24.204624', 12, '2024-08-15 17:50:26.299152', 12,
         '2024-01-07 16:48:45', 7, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'YA5300');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-31 20:01:51.820530', 12, '2024-09-08 21:25:58.114602', 12,
         '2024-06-03 07:37:47', 7, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-07 00:33:32.707590', 12,
             '2024-06-09 06:30:16.460384', 12, '2024-08-04 08:02:36', 1,
             'CAR', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'HT3387');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-12 07:38:25.254692', 12, '2024-04-21 21:34:06.313305', 12,
         '2024-07-09 23:57:09', 4, 'TRUCK', 1, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-04-22 19:12:36.805373', 12, '2024-05-30 04:48:28.615498', 12,
         '2024-02-05 04:20:37', 5, 'FOOT', 3, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-03-05 07:19:26.961889', 12, '2024-09-03 08:48:36.721979', 12,
         '2024-05-27 08:03:22', 3, 'PICKUP', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-31 20:01:51.820530', 12,
             '2024-02-22 22:42:40.431342', 12, '2024-06-03 10:37:47', 3,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-12 14:59:54.541545', 12,
             '2024-03-27 13:40:38.069051', 12, '2024-08-12 02:18:25', 4,
             'TRUCK', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-07 23:52:13.730826', 12,
             '2024-10-17 12:16:05.730289', 12, '2024-07-22 16:05:15', 3,
             'TRUCK', 4, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-16 00:43:15.495722', 12, '2024-06-11 09:49:55.832969', 12,
         '2024-03-26 04:40:42', 10, 'CAR', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'IP9438');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-05 06:26:24.837189', 12, '2024-09-08 15:42:53.067386', 12,
         '2024-08-29 19:26:17', 4, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-21 19:46:31.078440', 12, '2024-06-17 20:33:47.025938', 12,
         '2024-05-16 07:59:54', 4, 'CAR', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'SE6426');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-05 04:49:27.255911', 12, '2024-05-07 18:01:55.369713', 12,
         '2024-10-21 00:50:06', 8, 'TRUCK', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-07-28 08:16:59.303004', 12,
             '2024-08-17 10:03:47.534070', 12, '2024-02-08 17:38:10', 8,
             'FOOT', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-05-11 18:42:14.975223', 12, '2024-03-19 02:42:40.429175', 12,
         '2024-03-10 02:47:25', 5, 'PICKUP', 10, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-17 02:11:35.413721', 12,
             '2024-08-28 23:24:24.711056', 12, '2024-08-30 07:15:56', 6,
             'MOTORBIKE', 2, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-13 09:26:22.364332', 12, '2024-08-12 17:35:36.006906', 12,
         '2024-07-07 08:05:25', 10, 'MOTORBIKE', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-03-28 21:33:35.227525', 12,
             '2024-02-10 07:02:26.718713', 12, '2024-03-12 12:40:39', 9,
             'FOOT', 5, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-27 21:52:23.094387', 12, '2024-04-15 03:46:27.281341', 12,
         '2024-05-12 21:01:07', 3, 'TRUCK', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-12 07:38:25.254692', 12,
             '2024-11-01 09:19:08.404743', 12, '2024-07-10 01:57:09', 3,
             'TRUCK', 1, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-09-12 16:22:03.560365', 12, '2024-09-24 21:42:39.884549', 12,
         '2024-09-23 22:04:42', 4, 'CAR', 4, 'ENTRY',
         false, false, NULL, NULL, NULL, 'ZQ9746');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-06-26 14:17:15.049114', 12,
             '2024-06-09 07:46:40.492957', 12, '2024-10-30 14:43:52', 4,
             'MOTORBIKE', 10, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-08-27 20:54:29.586970', 12, '2024-09-12 14:37:29.904135', 12,
         '2024-01-25 18:56:24', 5, 'FOOT', 5, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-09-17 11:19:01.259886', 12,
             '2024-02-24 02:39:12.360473', 12, '2024-04-29 18:13:44', 10,
             'CAR', 9, 'EXIT', false,
             false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-04 07:11:53.810149', 12, '2024-05-25 14:54:14.893577', 12,
         '2024-01-14 15:56:40', 7, 'PICKUP', 6, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-07-07 23:14:39.860562', 12, '2024-08-06 20:43:03.545854', 12,
         '2024-07-04 01:59:04', 2, 'TRUCK', 9, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-01-09 21:02:48.362022', 12, '2024-01-07 11:13:29.099507', 12,
         '2024-04-19 22:26:42', 1, 'FOOT', 8, 'ENTRY',
         false, false, NULL, NULL, NULL, 'NULL');


        INSERT INTO accesses
        (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
         vehicle_type, auth_id, action, is_inconsistent, notified, comments,
         supplier_employee_id, vehicle_description, vehicle_reg)
        VALUES
        ('2024-02-25 19:05:48.881422', 12, '2024-01-08 04:07:01.932758', 12,
         '2024-07-22 20:17:56', 1, 'CAR', 2, 'ENTRY',
         false, false, NULL, NULL, NULL, 'ZW9014');


            INSERT INTO accesses
            (created_date, created_user, last_updated_date, last_updated_user, action_date, plot_id,
             vehicle_type, auth_id, action, is_inconsistent, notified, comments,
             supplier_employee_id, vehicle_description, vehicle_reg)
            VALUES
            ('2024-04-22 19:12:36.805373', 12,
             '2024-05-15 00:59:03.743782', 12, '2024-02-05 06:20:37', 6,
             'FOOT', 3, 'EXIT', false,
             false, NULL, NULL, NULL, 'None');

GO

WITH numbered_accesses AS (
  SELECT
    id,
    auth_id,
    action,
    action_date,
    @row_num := IF(
      @prev_auth = auth_id AND @prev_action = action,
      @row_num + 1,
      1
    ) AS consecutive_count,
    @prev_auth := auth_id AS dummy1,
    @prev_action := action AS dummy2
  FROM (
    SELECT id, auth_id, action, action_date
    FROM accesses
    ORDER BY auth_id, action_date
  ) ordered_accesses
  CROSS JOIN (SELECT @row_num := 1, @prev_auth := NULL, @prev_action := NULL) vars
)
UPDATE accesses a
INNER JOIN numbered_accesses na ON a.id = na.id
SET a.is_inconsistent = TRUE
WHERE na.consecutive_count > 1;