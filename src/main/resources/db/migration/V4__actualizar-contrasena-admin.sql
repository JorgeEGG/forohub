-- Actualizar contraseña del usuario admin
-- Contraseña: admin123
-- Hash BCrypt corregido
UPDATE usuarios 
SET contrasena = '$2a$10$FSiDXZ1MmFyLgvmCf4dDAu.J7Y.cE9LSqN7cWRN6bKV7bHgk.n1re' 
WHERE login = 'admin';
