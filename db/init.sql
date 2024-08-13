CREATE DATABASE IF NOT EXISTS test_db;

USE test_db;

CREATE TABLE personas (
	id BIGINT auto_increment NOT NULL,
	nombre VARCHAR(100) NULL,
	genero CHAR NULL,
	edad INT NULL,
	identificacion VARCHAR(13) NOT NULL,
	direccion varchar(100) NULL,
	telefono varchar(10) NULL,
	CONSTRAINT persona_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

CREATE TABLE clientes (
	id BIGINT auto_increment NOT NULL,
	id_persona BIGINT NOT NULL,
	contraseña varchar(100) NOT NULL,
	estado char(1) NOT NULL DEFAULT 'A',
	CONSTRAINT cliente_PK PRIMARY KEY (id),
	CONSTRAINT cliente_FK FOREIGN KEY (id_persona) REFERENCES test_db.persona(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

CCREATE TABLE cuentas (
	id BIGINT auto_increment NOT NULL,
	id_cliente BIGINT NOT NULL,
	numero_cuenta varchar(100) NOT NULL,
	tipo_cuenta varchar(255) NOT NULL,
	saldo_inicial DECIMAL DEFAULT 0 NOT NULL,
	saldo_disponible DECIMAL DEFAULT 0 NOT NULL,
	estado char(1) NOT NULL DEFAULT 'A',
	CONSTRAINT cuenta_PK PRIMARY KEY (id),
	CONSTRAINT cuenta_FK FOREIGN KEY (id_cliente) REFERENCES clientes(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

CREATE TABLE movimientos (
	id BIGINT auto_increment NOT NULL,
	id_cuenta BIGINT NOT NULL,
	fecha DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	tipo_movimiento VARCHAR(255) DEFAULT ' ' NOT NULL,
	valor DECIMAL DEFAULT 0 NOT NULL,
	saldo DECIMAL DEFAULT 0 NOT NULL,
	CONSTRAINT movimientos_PK PRIMARY KEY (id),
	CONSTRAINT movimientos_FK FOREIGN KEY (id_cuenta) REFERENCES cuentas(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;
