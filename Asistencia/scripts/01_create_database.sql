-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS sistema_ventas;
USE sistema_ventas;

-- Tabla de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    ciudad VARCHAR(50)
);

-- Tabla de Productos
CREATE TABLE IF NOT EXISTS productos (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    categoria VARCHAR(50),
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    costo DECIMAL(10, 2) NOT NULL
);

-- Tabla de Ventas
CREATE TABLE IF NOT EXISTS ventas (
    id_venta VARCHAR(20) PRIMARY KEY,
    numero_venta VARCHAR(20) UNIQUE NOT NULL,
    fecha VARCHAR(20) NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    producto VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL
);

-- Insertar datos de ejemplo
INSERT INTO clientes (codigo, nombre, cedula, email, telefono, direccion, ciudad) VALUES
('CLI001', 'Juan Pérez', '1234567890', 'juan@example.com', '555-1234', 'Calle 1', 'Lima'),
('CLI002', 'María García', '0987654321', 'maria@example.com', '555-5678', 'Calle 2', 'Lima');

INSERT INTO productos (codigo, nombre, descripcion, categoria, precio, stock, costo) VALUES
('PROD001', 'Laptop', 'Laptop de 15 pulgadas', 'Electrónica', 1200.00, 10, 800.00),
('PROD002', 'Mouse', 'Mouse inalámbrico', 'Accesorios', 25.00, 50, 10.00),
('PROD003', 'Teclado', 'Teclado mecánico', 'Accesorios', 80.00, 30, 40.00);

INSERT INTO ventas (id_venta, numero_venta, fecha, cliente, producto, cantidad, precio_unitario, total) VALUES
('VENTA001', 'V001', '2025-10-30', 'Juan Pérez', 'Laptop', 1, 1200.00, 1200.00),
('VENTA002', 'V002', '2025-10-30', 'María García', 'Mouse', 2, 25.00, 50.00);
