CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(60) NOT NULL UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    clave_hash VARCHAR(100) NOT NULL,
    clave_salt VARCHAR(100) NOT NULL,
    iteraciones INT NOT NULL DEFAULT 210000,
    rol VARCHAR(20) NOT NULL DEFAULT 'USUARIO',
    activo TINYINT(1) NOT NULL DEFAULT 1,
    ultimo_acceso DATETIME(6) NULL,
    CONSTRAINT chk_usuario_iteraciones CHECK (iteraciones >= 100000),
    CONSTRAINT chk_usuario_rol CHECK (rol IN ('ADMINISTRADOR', 'USUARIO')),
    CONSTRAINT chk_usuario_activo CHECK (activo IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS pais (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    codigo_iso VARCHAR(3) NOT NULL UNIQUE,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT chk_pais_codigo CHECK (CHAR_LENGTH(TRIM(codigo_iso)) BETWEEN 2 AND 3),
    CONSTRAINT chk_pais_activo CHECK (activo IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS departamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    pais_id INT NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT uq_departamento_pais UNIQUE (nombre, pais_id),
    CONSTRAINT fk_departamento_pais FOREIGN KEY (pais_id)
        REFERENCES pais(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_departamento_activo CHECK (activo IN (0, 1)),
    INDEX idx_departamento_pais (pais_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS cargo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    descripcion VARCHAR(500) NULL,
    salario_minimo DECIMAL(14,2) NOT NULL DEFAULT 0,
    salario_maximo DECIMAL(14,2) NOT NULL DEFAULT 0,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT chk_cargo_salario_minimo CHECK (salario_minimo >= 0),
    CONSTRAINT chk_cargo_salario_maximo CHECK (salario_maximo >= salario_minimo),
    CONSTRAINT chk_cargo_activo CHECK (activo IN (0, 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS empleado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    identidad VARCHAR(40) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    telefono VARCHAR(30) NULL,
    fecha_contratacion DATE NOT NULL,
    salario DECIMAL(14,2) NOT NULL,
    departamento_id INT NOT NULL,
    cargo_id INT NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_empleado_departamento FOREIGN KEY (departamento_id)
        REFERENCES departamento(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_empleado_cargo FOREIGN KEY (cargo_id)
        REFERENCES cargo(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_empleado_salario CHECK (salario >= 0),
    CONSTRAINT chk_empleado_activo CHECK (activo IN (0, 1)),
    INDEX idx_empleado_departamento (departamento_id),
    INDEX idx_empleado_cargo (cargo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS proyecto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(160) NOT NULL UNIQUE,
    descripcion VARCHAR(800) NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NULL,
    presupuesto DECIMAL(16,2) NOT NULL DEFAULT 0,
    estado VARCHAR(20) NOT NULL DEFAULT 'PLANIFICADO',
    CONSTRAINT chk_proyecto_presupuesto CHECK (presupuesto >= 0),
    CONSTRAINT chk_proyecto_estado CHECK (estado IN ('PLANIFICADO', 'ACTIVO', 'FINALIZADO')),
    CONSTRAINT chk_proyecto_fechas CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS asignacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    empleado_id INT NOT NULL,
    proyecto_id INT NOT NULL,
    fecha_asignacion DATE NOT NULL,
    horas_asignadas INT NOT NULL,
    rol VARCHAR(120) NOT NULL,
    CONSTRAINT uq_asignacion_empleado_proyecto UNIQUE (empleado_id, proyecto_id),
    CONSTRAINT fk_asignacion_empleado FOREIGN KEY (empleado_id)
        REFERENCES empleado(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_asignacion_proyecto FOREIGN KEY (proyecto_id)
        REFERENCES proyecto(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT chk_asignacion_horas CHECK (horas_asignadas BETWEEN 1 AND 200),
    INDEX idx_asignacion_empleado (empleado_id),
    INDEX idx_asignacion_proyecto (proyecto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT IGNORE INTO usuario
    (nombre_usuario, nombre_completo, clave_hash, clave_salt, iteraciones, rol, activo)
VALUES
    ('admin', 'Administrador del Sistema',
     'mW/4zievKvMth5o5gACOeOHU9IC89BmlqZVYcvKhxs4=',
     'nyOhxdAuS3eIyasQ70NSZw==',
     210000, 'ADMINISTRADOR', 1);

INSERT IGNORE INTO pais (nombre, codigo_iso, activo)
VALUES ('Honduras', 'HN', 1);

INSERT IGNORE INTO departamento (nombre, pais_id, activo)
SELECT 'Francisco Morazán', id, 1 FROM pais WHERE codigo_iso = 'HN';

INSERT IGNORE INTO cargo (nombre, descripcion, salario_minimo, salario_maximo, activo)
VALUES ('Ingeniero de Software', 'Diseño y desarrollo de soluciones', 15000, 80000, 1);

INSERT IGNORE INTO proyecto
    (nombre, descripcion, fecha_inicio, fecha_fin, presupuesto, estado)
VALUES
    ('Transformación Digital', 'Proyecto inicial de demostración',
     CURRENT_DATE, NULL, 250000, 'ACTIVO');

