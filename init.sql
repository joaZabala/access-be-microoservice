CREATE DATABASE accesses;

USE accesses;

CREATE TABLE vehicle_types (
    id BIGINT AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE authorized_types (
    id BIGINT AUTO_INCREMENT,
    description VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE visitors(
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    doc_number INT,
    birth_date DATE,
    is_active BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accesses (
    id BIGINT AUTO_INCREMENT,
    auth_range_id BIGINT,
    entry_date DATETIME,
    exit_date DATETIME,
    vehicle_type_id BIGINT,
    vehicle_reg VARCHAR(7),
    vehicle_description VARCHAR(100),
    owner_id BIGINT,
    visitor_id BIGINT,
    comments VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (visitor_id) REFERENCES visitors(id),
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(id)
);

CREATE TABLE authorized_ranges (
    id BIGINT AUTO_INCREMENT,
    auth_type_id BIGINT NOT NULL,
    visitor_id BIGINT,
    external_id BIGINT,
    date_from DATE,
    date_to DATE,
    hour_from TIME,
    hour_to TIME,
    days VARCHAR(100),
    plot_id BIGINT,
    comments VARCHAR(500),
    is_active BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (visitor_id) REFERENCES visitors(id),
    FOREIGN KEY (auth_type_id) REFERENCES authorized_types(id)
);


INSERT INTO vehicle_types (description) VALUES ('Automóvil'), ('Motocicleta'), ('Camión'), ('Utilitario'), ('Colectivo');

INSERT INTO authorized_types (description) VALUES ('Empleado'), ('Visitante'), ('Proveedor'), ('Dueño'), ('Trabajador') ;

INSERT INTO visitors (name, lastname, doc_number, birth_date, is_active)
VALUES ('John', 'Doe', 123456, '1980-01-01', true),
       ('Jane', 'Smith', 234567, '1990-02-02', true),
       ('Bob', 'Brown', 345678, '1975-03-03', true);

INSERT INTO authorized_ranges (auth_type_id, visitor_id, plot_id, external_id, date_from, date_to, hour_from, hour_to,days, is_active)
VALUES (2, 1, 1001, null, '2024-09-01', null,'08:00:00', '18:00:00', 'MONDAY-WEDNESDAY-FRIDAY', true),
       (2, 2, 1002, null, '2024-09-02', null, null, null,null, true),
       (2, 3, 1003, null, '2024-09-03', '2024-12-31', null, null,null, true),
       (1, null, null, 5, '2020-01-01', '2030-01-01', '08:00:00', '18:00:00','MONDAY-TUESDAY-WEDNESDAY-THURSDAY-FRIDAY', true);

INSERT INTO accesses (auth_range_id, entry_date, exit_date, vehicle_type_id, vehicle_reg, vehicle_description, comments, owner_id, visitor_id)
VALUES (1, '2024-09-17 08:00:00', '2024-09-17 18:00:00', 1, 'ABC123', 'Toyota Corolla', 'No issues', 1, null),
       (2, '2024-09-17 09:00:00', '2024-09-17 17:00:00', 2, 'XYZ789', 'Harley Davidson', 'Late entry', 1, null),
       (3, '2024-09-17 10:00:00', '2024-09-17 16:00:00', 3, 'LMN456', 'Ford F150', 'Damaged gate on entry', null, null),
       (null, '2024-09-22 10:00:00', '2024-09-22 16:00:00',1, 'ABC123', 'Toyota Corolla', 'No issues',1,1),
       (4, '2024-09-23 07:58:05', '2024-09-23 17:59:23', 1, 'LKY852', 'Ford Fiesta', null, null,null),
       (4, '2024-09-24 08:01:32', '2024-09-23 18:06:52', 1, 'LKY852', 'Ford Fiesta', null, null,null);