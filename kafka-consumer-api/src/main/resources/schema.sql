CREATE TABLE IF NOT EXISTS taxi_stations (
    id VARCHAR(255),
    PRIMARY KEY (id(100)),
    name VARCHAR(255),
    insee VARCHAR(255),
    address VARCHAR(255),
    emplacements VARCHAR(255),
    status VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE
    );
