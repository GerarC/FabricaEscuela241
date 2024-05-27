CREATE SCHEMA IF NOT EXISTS sitas;
SET search_path TO sitas;
SHOW search_path;

-- Create Tables
CREATE TABLE Airplane_Model (
    airplane_model VARCHAR(255) PRIMARY KEY,
    family VARCHAR(255),
    capacity INT,
    cargo_capacity FLOAT,
    volume_capacity FLOAT
);

CREATE TABLE Airport (
    airport_code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    runways INT
);

CREATE TABLE Flight (
    flight_id SERIAL PRIMARY KEY,
    flight_number VARCHAR(255),
    base_price FLOAT,
    tax_percent FLOAT,
    surcharge FLOAT
);

CREATE TABLE Scale (
    scale_id SERIAL PRIMARY KEY,
    flight_id INT REFERENCES Flight(flight_id),
    airplane_model VARCHAR(255) REFERENCES Airplane_Model(airplane_model),
    origin_airport VARCHAR(255) REFERENCES Airport(airport_code),
    destination_airport VARCHAR(255) REFERENCES Airport(airport_code),
    departure_date TIMESTAMP,
    arrival_date TIMESTAMP
);

CREATE TABLE Identification_Type (
    identification_type_id SERIAL PRIMARY KEY,
    identification_type VARCHAR(255)
);

CREATE TABLE Person (
    person_id SERIAL PRIMARY KEY,
    id_identification_type INT REFERENCES Identification_Type(identification_type_id),
    identification_number VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    genre VARCHAR(255),
    birth_date DATE,
    phone_number VARCHAR(255),
    country VARCHAR(255),
    province VARCHAR(255),
    city VARCHAR(255),
    residence VARCHAR(255),
    email VARCHAR(255),
    access_key VARCHAR(255)
);

CREATE TABLE Flight_History (
    flight_history_id SERIAL PRIMARY KEY,
    person_id INT REFERENCES Person(person_id),
    flight_id INT REFERENCES Flight(flight_id)
);

CREATE TABLE Search_History (
    search_history_id SERIAL PRIMARY KEY,
    person_id INT REFERENCES Person(person_id),
    scale_id INT REFERENCES Scale(scale_id),
    search_date DATE,
    search_query VARCHAR(255)
);

-- Example values
INSERT INTO Airplane_Model (airplane_model, family, capacity, cargo_capacity, volume_capacity) VALUES
    ('Boeing737', 'Boeing 737 Family', 150, 20000.0, 5000.0),
    ('AirbusA320', 'Airbus A320 Family', 180, 18000.0, 4800.0),
    ('Boeing777', 'Boeing 777 Family', 300, 30000.0, 8000.0),
    ('AirbusA350', 'Airbus A350 Family', 250, 25000.0, 7000.0),
    ('EmbraerE190', 'Embraer E190 Family', 100, 12000.0, 4000.0),
    ('BombardierCRJ900', 'Bombardier CRJ Series', 90, 10000.0, 3500.0);

INSERT INTO Airport (airport_code, name, type, city, country, runways) VALUES
    ('JFK', 'John F. Kennedy International Airport', 'International', 'New York City', 'USA', 4),
    ('LHR', 'Heathrow Airport', 'International', 'London', 'UK', 2),
    ('CDG', 'Charles de Gaulle Airport', 'International', 'Paris', 'France', 4),
    ('HND', 'Haneda Airport', 'International', 'Tokyo', 'Japan', 3),
    ('SYD', 'Sydney Kingsford Smith Airport', 'International', 'Sydney', 'Australia', 3),
    ('DXB', 'Dubai International Airport', 'International', 'Dubai', 'UAE', 2);

INSERT INTO Flight (flight_number, base_price, tax_percent, surcharge) VALUES
    ('BA123', 500.0, 15.0, 50.0),
    ('AA456', 600.0, 18.0, 60.0),
    ('LH789', 700.0, 20.0, 70.0),
    ('EK654', 800.0, 22.0, 80.0),
    ('QF321', 550.0, 16.0, 55.0),
    ('EK987', 750.0, 21.0, 75.0),
    ('UA123', 650.0, 17.0, 65.0);

INSERT INTO Scale (flight_id, airplane_model, origin_airport, destination_airport, departure_date, arrival_date) VALUES
    (1, 'Boeing737', 'JFK', 'LHR', '2024-04-20 08:00:00', '2024-04-20 12:00:00'),
    (2, 'AirbusA320', 'LHR', 'JFK', '2024-04-21 10:00:00', '2024-04-21 14:00:00'),
    (3, 'Boeing777', 'CDG', 'HND', '2024-04-22 12:00:00', '2024-04-22 18:00:00'),
    (4, 'AirbusA350', 'HND', 'CDG', '2024-04-23 14:00:00', '2024-04-23 20:00:00'),
    (5, 'EmbraerE190', 'SYD', 'DXB', '2024-04-24 16:00:00', '2024-04-24 22:00:00'),
    (6, 'BombardierCRJ900', 'DXB', 'SYD', '2024-04-25 18:00:00', '2024-04-26 02:00:00'),
    (7, 'Boeing737', 'JFK', 'LHR', '2024-04-26 08:00:00', '2024-04-26 12:00:00'),
    (7, 'AirbusA320', 'LHR', 'CDG', '2024-04-26 14:00:00', '2024-04-26 16:00:00'),
    (7, 'Boeing777', 'CDG', 'HND', '2024-04-26 18:00:00', '2024-04-27 00:00:00');

INSERT INTO Identification_Type (identification_type_id, identification_type) VALUES
    (1, 'Passport'),
    (2, 'Driver License'),
    (3, 'National ID');

INSERT INTO Person (id_identification_type, identification_number, first_name, last_name, genre, birth_date, phone_number, country, province, city, residence, email, access_key) VALUES
    (3, 'X1234567', 'John', 'Doe', 'Male', '1980-01-01', '1234567890', 'USA', 'New York', 'New York City', '123 Main St', 'john.doe@example.com', 'pass123'),
    (2, 'A2345678', 'Jane', 'Smith', 'Female', '1990-02-02', '2345678901', 'USA', 'California', 'Los Angeles', '456 Elm St', 'jane.smith@example.com', 'pass234');

INSERT INTO Flight_History (person_id, flight_id) VALUES
    (1, 1),
    (2, 2),
    (1, 3),
    (2, 4);

INSERT INTO Search_History (person_id, scale_id, search_date, search_query) VALUES
    (1, 1, '2024-04-01', 'Cheap flights from JFK to LHR'),
    (2, 2, '2024-04-02', 'Flights from LHR to JFK'),
    (1, 3, '2024-04-03', 'Direct flights from CDG to HND'),
    (2, 4, '2024-04-04', 'Best flights from HND to CDG');

SET search_path TO public;