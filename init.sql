SET NAMES utf8mb4;

CREATE DATABASE accesses;

USE accesses;

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
    date_from DATE,
    date_to DATE,
    hour_from TIME,
    hour_to TIME,
    days VARCHAR(50) NOT NULL,
    comment VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_auth FOREIGN KEY (auth_id) REFERENCES auths(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Insertar datos en Visitors
INSERT INTO visitors (name, lastname, doc_type, doc_number, birth_date, is_active) VALUES
('Juan', 'Pérez', 'DNI', 12345678, '1980-05-20', TRUE),
('María', 'Gómez', 'DNI', 23456789, '1992-11-15', TRUE),
('Carlos', 'Fernández', 'CUIL', 34567890, '1985-03-10', FALSE),
('Laura', 'Rodríguez', 'CUIL', 45678901, '1990-07-25', TRUE),
('Pedro', 'Martínez', 'CUIL', 56789012, '1978-12-30', TRUE);

-- Insertar datos en Auths
INSERT INTO auths (visitor_id, visitor_type, plot_id, is_active) VALUES
(1, 'OWNER', 1, TRUE),
(2, 'WORKER', 2, TRUE),
(3, 'VISITOR', 3, TRUE),
(4, 'EMPLOYEE', 4, FALSE),
(5, 'PROVIDER', 5, TRUE);

-- Insertar datos en Auth_Ranges
INSERT INTO auth_ranges (auth_id, date_from, date_to, hour_from, hour_to, days, comment, is_active) VALUES
(1, '2024-10-01', '2024-12-31', '08:00:00', '18:00:00', 'Mon,Tue,Wed,Thu,Fri', 'Autorización para oficinas', TRUE),
(1, '2024-10-01', '2024-12-31', '05:00:00', '12:00:00', 'Mon,Tue,Wed,Thu,Fri', 'Autorización para para', TRUE),
(2, '2024-09-15', '2024-10-15', '09:00:00', '17:00:00', 'Mon,Wed,Fri', 'Visita regular', TRUE),
(3, '2024-10-01', '2024-11-30', '07:00:00', '15:00:00', 'Tue,Thu', 'Acceso restringido', FALSE),
(4, '2024-10-05', '2024-10-20', '06:00:00', '14:00:00', 'Mon,Tue', 'Entrega de suministros', TRUE),
(5, '2024-10-01', '2024-12-31', '08:30:00', '16:30:00', 'Mon,Tue,Wed,Thu,Fri', 'Autorización temporal', TRUE);
