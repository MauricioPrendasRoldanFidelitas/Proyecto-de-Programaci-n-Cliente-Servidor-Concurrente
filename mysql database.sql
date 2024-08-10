CREATE DATABASE FVGames;

CREATE TABLE Cliente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    cedula VARCHAR(20) UNIQUE,
    contrasena VARCHAR(50),
    saldo DOUBLE
);

