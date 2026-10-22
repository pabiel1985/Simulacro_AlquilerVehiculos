DROP DATABASE IF EXISTS alquiler_db;
CREATE DATABASE alquiler_db;
USE alquiler_db;

CREATE TABLE vehiculos (
    patente            VARCHAR(10)   NOT NULL PRIMARY KEY,
    tipo               VARCHAR(15)   NOT NULL,
    marca              VARCHAR(50)   NOT NULL,
    modelo             VARCHAR(50)   NOT NULL,
    anio               INT           NOT NULL,
    precio_base_diario DECIMAL(10,2) NOT NULL,
    combustible        VARCHAR(15)   NOT NULL,
    estado             VARCHAR(20)   NOT NULL,
    cantidad_puertas   INT           NULL,
    cilindrada         INT           NULL,
    traccion_4x4       BOOLEAN       NULL
);
