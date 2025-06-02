CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL
);

INSERT INTO usuarios (email, senha, role) VALUES
  ('user@example.com', 'senha123', 'USER'),
  ('admin@example.com', 'admin123', 'ADMIN');
