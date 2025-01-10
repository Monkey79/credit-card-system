-- Base de datos
CREATE DATABASE IF NOT EXISTS credit_card_system_eldar;
USE credit_card_system_eldar;

-- Tabla card_holder
CREATE TABLE IF NOT EXISTS card_holder (
    ch_id INT AUTO_INCREMENT PRIMARY KEY,
    ch_first_name VARCHAR(50) NOT NULL,
    ch_last_name VARCHAR(50) NOT NULL,
    ch_dni VARCHAR(20) UNIQUE NOT NULL,
    ch_date_of_birth DATE NOT NULL,
    ch_email VARCHAR(100) UNIQUE NOT NULL
);

-- Tabla tarjeta_credito (un usuario puede tener muchas tarjetas)
CREATE TABLE IF NOT EXISTS credit_cards (
    cc_id INT AUTO_INCREMENT PRIMARY KEY,
    cc_card_number VARCHAR(16) UNIQUE NOT NULL,
    cc_brand ENUM('VISA', 'NARA', 'AMEX') NOT NULL,
    cc_expiration_date DATE NOT NULL,
    cc_cvv CHAR(3) NOT NULL,
    cc_card_holder_id INT NOT NULL,
    FOREIGN KEY (cc_card_holder_id) REFERENCES card_holder(ch_id) ON DELETE CASCADE
);

-- Tabla transacciones (un tarjeta puede tener muchas transacciones)
CREATE TABLE IF NOT EXISTS transactions (
    ts_id INT AUTO_INCREMENT PRIMARY KEY,
    ts_amount DECIMAL(10, 2) NOT NULL,
    ts_detail VARCHAR(255) NOT NULL,
    ts_transaction_date DATE,
    ts_card_id INT NOT NULL,
    FOREIGN KEY (ts_card_id) REFERENCES credit_cards(cc_id) ON DELETE CASCADE
);

-- Indeces ---
CREATE INDEX idx_card_holder_dni ON card_holder (ch_dni);
CREATE INDEX idx_credit_cards_card_holder_id ON credit_cards (cc_card_holder_id);
CREATE INDEX idx_transactions_card_id ON transactions (ts_card_id);

-- Data sets ---
INSERT INTO card_holder (ch_first_name, ch_last_name, ch_dni, ch_date_of_birth, ch_email)
VALUES
('Juan', 'Perez', '12345678', '1990-05-15', 'juan.perez@example.com'),
('María', 'Gomez', '87654321', '1985-03-22', 'maria.gomez@example.com');

-- note: hash cvv
INSERT INTO credit_cards (cc_card_number, cc_brand, cc_expiration_date, cc_cvv, cc_card_holder_id)
VALUES
('1234567812345678', 'VISA', '2025-12-31', '123', 1),
('8765432187654321', 'NARA', '2024-06-30', '456', 2),
('1111222233334444', 'AMEX', '2026-03-15', '789', 1);

INSERT INTO transactions (ts_amount, ts_detail,ts_transaction_date, ts_card_id)
VALUES
(500.00, 'Compra en tienda A','2026-12-31', 1),
(10000.00, 'Compra en tienda B','2026-12-31', 2),
(1200.50, 'Compra online C','2026-12-31', 3);