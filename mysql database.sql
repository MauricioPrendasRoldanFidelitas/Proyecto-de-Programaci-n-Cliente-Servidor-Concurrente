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
    fecha datetime,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
    FOREIGN KEY (id_producto) REFERENCES Producto(id)
);

CREATE TABLE Paquete (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descuento DOUBLE NOT NULL,
    precio_final DOUBLE NOT NULL
);

CREATE TABLE Paquete_Producto (
    paquete_id INT,
    producto_id INT,
    PRIMARY KEY (paquete_id, producto_id),
    FOREIGN KEY (paquete_id) REFERENCES Paquete(id),
    FOREIGN KEY (producto_id) REFERENCES Producto(id)
);


USE FVGames;
SELECT * FROM Cliente;
SELECT * FROM Producto;
SELECT * FROM Compra;
SELECT * FROM Paquete;
SELECT * FROM Paquete_Producto;