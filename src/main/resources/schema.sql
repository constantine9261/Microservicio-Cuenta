CREATE TABLE IF NOT EXISTS cuentas (
    id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    saldo DECIMAL(15, 2) DEFAULT 0,
    tipo_cuenta VARCHAR(20) NOT NULL,
    cliente_id BIGINT NOT NULL
);
