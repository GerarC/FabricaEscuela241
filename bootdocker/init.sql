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

-- Examples values
INSERT INTO Airplane_Model (airplane_model, family, capacity, cargo_capacity, volume_capacity)
VALUES
    ('Boeing737', 'Boeing 737 Family', 150, 20000.0, 5000.0),
    ('AirbusA320', 'Airbus A320 Family', 180, 18000.0, 4800.0),
    ('Boeing777', 'Boeing 777 Family', 300, 30000.0, 8000.0),
    ('AirbusA350', 'Airbus A350 Family', 250, 25000.0, 7000.0),
    ('EmbraerE190', 'Embraer E190 Family', 100, 12000.0, 4000.0),
    ('BombardierCRJ900', 'Bombardier CRJ Series', 90, 10000.0, 3500.0);

INSERT INTO Airport (airport_code, name, type, city, country, runways)
VALUES
    ('JFK', 'John F. Kennedy International Airport', 'International', 'New York City', 'USA', 4),
    ('LHR', 'Heathrow Airport', 'International', 'London', 'UK', 2),
    ('CDG', 'Charles de Gaulle Airport', 'International', 'Paris', 'France', 4),
    ('HND', 'Haneda Airport', 'International', 'Tokyo', 'Japan', 3),
    ('SYD', 'Sydney Kingsford Smith Airport', 'International', 'Sydney', 'Australia', 3),
    ('DXB', 'Dubai International Airport', 'International', 'Dubai', 'UAE', 2);

INSERT INTO Flight (flight_number, base_price, tax_percent, surcharge)
VALUES
    ('BA123', 500.0, 15.0, 50.0),
    ('AA456', 600.0, 18.0, 60.0),
    ('LH789', 700.0, 20.0, 70.0),
    ('EK654', 800.0, 22.0, 80.0),
    ('QF321', 550.0, 16.0, 55.0),
    ('EK987', 750.0, 21.0, 75.0),
    ('UA123', 650.0, 17.0, 65.0);

INSERT INTO Scale (flight_id, airplane_model, origin_airport, destination_airport, departure_date, arrival_date)
VALUES
    (1, 'Boeing737', 'JFK', 'LHR', '2024-04-20 08:00:00', '2024-04-20 12:00:00'),
    (2, 'AirbusA320', 'LHR', 'JFK', '2024-04-21 10:00:00', '2024-04-21 14:00:00'),
    (3, 'Boeing777', 'CDG', 'HND', '2024-04-22 12:00:00', '2024-04-22 18:00:00'),
    (4, 'AirbusA350', 'HND', 'CDG', '2024-04-23 14:00:00', '2024-04-23 20:00:00'),
    (5, 'EmbraerE190', 'SYD', 'DXB', '2024-04-24 16:00:00', '2024-04-24 22:00:00'),
    (6, 'BombardierCRJ900', 'DXB', 'SYD', '2024-04-25 18:00:00', '2024-04-26 02:00:00'),
    (7, 'Boeing737', 'JFK', 'LHR', '2024-04-26 08:00:00', '2024-04-26 12:00:00'),
    (7, 'AirbusA320', 'LHR', 'CDG', '2024-04-26 14:00:00', '2024-04-26 16:00:00'),
    (7, 'Boeing777', 'CDG', 'HND', '2024-04-26 18:00:00', '2024-04-27 00:00:00');
