CREATE DATABASE accesses;

USE accesses;

CREATE TABLE vehicle_types (
    vehicle_type_id BIGINT AUTO_INCREMENT,
    description VARCHAR(50),
    PRIMARY KEY (vehicle_type_id)
);

CREATE TABLE authorized_types (
    auth_type_id BIGINT AUTO_INCREMENT,
    description VARCHAR(100),
    PRIMARY KEY (auth_type_id)
);

CREATE TABLE authorized (
    auth_id BIGINT AUTO_INCREMENT,
    name VARCHAR(50),
    lastname VARCHAR(50),
    doc_number INT,
    birth_date DATE,
    PRIMARY KEY (auth_id)
);

CREATE TABLE accesses (
    accesses_id BIGINT AUTO_INCREMENT,
    auth_range_id BIGINT NOT NULL,
    entry_date DATETIME,
    exit_date DATETIME,
    vehicle_type_id BIGINT,
    vehicle_reg VARCHAR(7),
    vehicle_description VARCHAR(100),
    comments VARCHAR(500),
    PRIMARY KEY (accesses_id),
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(vehicle_type_id)
);

CREATE TABLE authorized_ranges (
    auth_range_id BIGINT AUTO_INCREMENT,
    auth_type_id BIGINT,
    auth_id BIGINT,
    parcel_id BIGINT,
    external_id BIGINT,
    date_from DATETIME,
    PRIMARY KEY (auth_range_id),
    FOREIGN KEY (auth_id) REFERENCES authorized(auth_id),
    FOREIGN KEY (auth_type_id) REFERENCES authorized_types(auth_type_id)
);


INSERT INTO vehicle_types (description) VALUES ('Car'), ('Motorcycle'), ('Truck');

INSERT INTO authorized_types (description) VALUES ('Employee'), ('Visitor'), ('Contractor');

INSERT INTO authorized (name, lastname, doc_number, birth_date) 
VALUES ('John', 'Doe', 123456, '1980-01-01'),
       ('Jane', 'Smith', 234567, '1990-02-02'),
       ('Bob', 'Brown', 345678, '1975-03-03');

INSERT INTO authorized_ranges (auth_type_id, auth_id, parcel_id, external_id, date_from)
VALUES (1, 1, 1001, 2001, '2024-09-01'),
       (2, 2, 1002, 2002, '2024-09-02'),
       (3, 3, 1003, 2003, '2024-09-03');

INSERT INTO accesses (auth_range_id, entry_date, exit_date, vehicle_type_id, vehicle_reg, vehicle_description, comments)
VALUES (1, '2024-09-17 08:00:00', '2024-09-17 18:00:00', 1, 'ABC123', 'Toyota Corolla', 'No issues'),
       (2, '2024-09-17 09:00:00', '2024-09-17 17:00:00', 2, 'XYZ789', 'Harley Davidson', 'Late entry'),
       (3, '2024-09-17 10:00:00', '2024-09-17 16:00:00', 3, 'LMN456', 'Ford F150', 'Damaged gate on entry');