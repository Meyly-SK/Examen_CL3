DROP DATABASE IF EXISTS cl3_chihuay;
-- creamos la bd
CREATE DATABASE cl3_chihuay;
-- activamos la bd
USE cl3_chihuay;

CREATE TABLE producto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(256)
);


select * from producto;