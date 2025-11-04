-- Script de inicialización de la base de datos SQLite
-- Este script se ejecuta automáticamente al iniciar la aplicación

-- Tabla de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);

-- Tabla de Productos
CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    categoria VARCHAR(100),
    precio DECIMAL(10, 2) NOT NULL,
    stock INTEGER DEFAULT 0,
    costo DECIMAL(10, 2)
);

-- Tabla de Ventas
CREATE TABLE IF NOT EXISTS ventas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    numero_venta VARCHAR(50) UNIQUE,
    fecha TIMESTAMP,
    cliente_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- Insertar datos de ejemplo con valores correctos
INSERT OR IGNORE INTO clientes (id, nombre, cedula, email, telefono, direccion) VALUES 
(1, 'Juan Pérez', '12345678', 'juan@example.com', '987654321', 'Lima'),
(2, 'María García', '87654321', 'maria@example.com', '987654322', 'Arequipa');

INSERT OR IGNORE INTO productos (id, codigo, nombre, descripcion, categoria, precio, stock, costo) VALUES 
(1, 'PROD001', 'Laptop', 'Laptop de 15 pulgadas', 'Electrónica', 1500.00, 10, 1000.00),
(2, 'PROD002', 'Mouse', 'Mouse inalámbrico', 'Accesorios', 25.00, 50, 15.00),
(3, 'PROD003', 'Teclado', 'Teclado mecánico', 'Accesorios', 80.00, 30, 50.00);
