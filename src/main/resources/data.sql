--UNA VEZ QUE CORRA EL PROYECTO COMENTAR PORFAVOR LOS INSERTS
INSERT INTO cuentas (numero_cuenta, saldo, tipo_cuenta, cliente_id)
VALUES
('0001-0001', 1500.00, 'Ahorro', 1),
('0001-0002', 3000.00, 'Corriente', 1),
('0002-0001', 2000.00, 'Ahorro', 2),
('0003-0001', 500.00, 'Corriente', 3)
ON CONFLICT (numero_cuenta) DO NOTHING;