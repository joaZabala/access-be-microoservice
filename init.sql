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
    owner_id BIGINT,
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
    visitor_id BIGINT,
    comments VARCHAR(500),
    PRIMARY KEY (id),
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


INSERT INTO vehicle_types (description) VALUES ('Car'), ('Motorcycle'), ('Truck');

INSERT INTO authorized_types (description) VALUES ('Employee'), ('Visitor'), ('Contractor');

INSERT INTO visitors (name, lastname, doc_number, birth_date, owner_id, is_active)
VALUES ('John', 'Doe', 123456, '1980-01-01',1, true),
       ('Jane', 'Smith', 234567, '1990-02-02',2, true),
       ('Bob', 'Brown', 345678, '1975-03-03',3, true);

INSERT INTO authorized_ranges (auth_type_id, visitor_id, plot_id, external_id, date_from, date_to, hour_from, hour_to,days, is_active)
VALUES (1, 1, 1001, 2001, '2024-09-01', null,'08:00:00', '18:00:00', 'MONDAY-WEDNESDAY-FRIDAY', true),
       (2, 2, 1002, 2002, '2024-09-02', null, null, null,null, true),
       (3, 3, 1003, 2003, '2024-09-03', '2024-12-31', null, null,null, true);

INSERT INTO accesses (auth_range_id, entry_date, exit_date, vehicle_type_id, vehicle_reg, vehicle_description, comments)
VALUES (1, '2024-09-17 08:00:00', '2024-09-17 18:00:00', 1, 'ABC123', 'Toyota Corolla', 'No issues'),
       (2, '2024-09-17 09:00:00', '2024-09-17 17:00:00', 2, 'XYZ789', 'Harley Davidson', 'Late entry'),
       (3, '2024-09-17 10:00:00', '2024-09-17 16:00:00', 3, 'LMN456', 'Ford F150', 'Damaged gate on entry');