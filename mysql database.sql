CREATE DATABASE FVGames;

CREATE TABLE Cliente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    contrasena VARCHAR(50) NOT NULL,
    saldo DOUBLE NOT NULL
);


CREATE TABLE Producto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    cantidad INT NOT NULL
);


CREATE TABLE Compra (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    id_producto INT,
    cantidad INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
    FOREIGN KEY (id_producto) REFERENCES Producto(id)
);

USE FVGames;
SELECT * FROM Cliente;
SELECT * FROM Producto;