-- Insertar usuario de prueba
-- Login: admin
-- Contraseña: admin123
-- La contraseña está encriptada con BCrypt
INSERT INTO usuarios (login, contrasena) 
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye1JVGYqEpzNS2D5xHmDnwLNsE4cQxCBK');

-- Nota: La contraseña es "admin123" encriptada con BCrypt
