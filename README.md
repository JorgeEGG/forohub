# ForoHub API

API REST desarrollada con Spring Boot para gestionar un foro de discusión. Permite crear, listar, actualizar y eliminar tópicos, con autenticación basada en JSON Web Tokens (JWT).

## 📋 Descripción

ForoHub es una API REST que simula el backend de un foro, donde los usuarios pueden publicar tópicos de discusión sobre diferentes cursos. La aplicación implementa las mejores prácticas de desarrollo con Spring Boot, incluyendo:

- Autenticación y autorización con JWT
- Persistencia de datos con JPA/Hibernate
- Migraciones de base de datos con Flyway
- Validaciones de negocio
- Eliminación lógica de registros
- Paginación de resultados
- Manejo centralizado de errores

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 4.0.3**
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Spring Validation
- **MySQL 8.0**
- **Flyway** (migraciones de base de datos)
- **JWT (Auth0)** (autenticación)
- **Lombok** (reducción de código boilerplate)
- **Maven** (gestión de dependencias)

## 📦 Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **JDK 21** o superior
- **Maven 4+**
- **MySQL 8.0** o superior
- **Postman** (para probar los endpoints)

## ⚙️ Configuración

### 1. Base de Datos

Crea una base de datos MySQL llamada `forohub`:

```sql
CREATE DATABASE forohub;
```

### 2. Variables de Entorno

Configura las siguientes variables de entorno en tu sistema:

```bash
DB_HOST=localhost:3306
DB_NAME_FOROHUB=forohub
DB_USER_MYSQL=root
DB_PASSWORD_MYSQL=tu_contraseña
JWT_SECRET_APIFOROHUB=tu_secreto_jwt
```

**Windows (PowerShell):**
```powershell
$env:DB_HOST="localhost:3306"
$env:DB_NAME_FOROHUB="forohub"
$env:DB_USER_MYSQL="root"
$env:DB_PASSWORD_MYSQL="tu_contraseña"
$env:JWT_SECRET_APIFOROHUB="mi_secreto_seguro_123"
```

**Linux/Mac:**
```bash
export DB_HOST=localhost:3306
export DB_NAME_FOROHUB=forohub
export DB_USER_MYSQL=root
export DB_PASSWORD_MYSQL=tu_contraseña
export JWT_SECRET_APIFOROHUB=mi_secreto_seguro_123
```

### 3. Ejecutar la Aplicación

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Endpoints de la API

### 🔐 Autenticación

#### Login
Obtiene un token JWT para autenticación.

```http
POST http://127.0.0.1:8080/login
Content-Type: application/json

{
  "login": "admin",
  "contrasena": "admin123"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

> **Nota:** El token debe incluirse en todas las demás peticiones en el header `Authorization: Bearer {token}`

---

### 📝 Tópicos

#### 1. Crear Tópico

```http
POST http://127.0.0.1:8080/topicos
Content-Type: application/json
Authorization: Bearer {tu_token}

{
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para implementar autenticación JWT en mi proyecto",
  "autor": "Juan Pérez",
  "curso": "Spring Boot"
}
```

**Respuesta exitosa (201 Created):**
```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para implementar autenticación JWT en mi proyecto",
  "fechaCreacion": "2026-03-09T10:30:00",
  "status": "ABIERTO",
  "autor": "Juan Pérez",
  "curso": "Spring Boot"
}
```

**Validaciones:**
- No se permite crear tópicos con el mismo título y mensaje (400 Bad Request)
- Todos los campos son obligatorios

---

#### 2. Listar Tópicos Activos

```http
GET http://127.0.0.1:8080/topicos?page=0&size=10
Authorization: Bearer {tu_token}
```

**Parámetros opcionales:**
- `page`: Número de página (default: 0)
- `size`: Tamaño de página (default: 10)
- `sort`: Campo de ordenamiento (default: fechaCreacion)

**Respuesta exitosa (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "¿Cómo usar Spring Security?",
      "mensaje": "Necesito ayuda para implementar autenticación JWT",
      "fechaCreacion": "2026-03-09T10:30:00",
      "status": "ABIERTO",
      "autor": "Juan Pérez",
      "curso": "Spring Boot"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

#### 3. Listar TODOS los Tópicos (incluidos inactivos)

```http
GET http://127.0.0.1:8080/topicos/all?page=0&size=10
Authorization: Bearer {tu_token}
```

Devuelve tanto los tópicos activos como los eliminados lógicamente.

---

#### 4. Obtener Detalle de un Tópico

```http
GET http://127.0.0.1:8080/topicos/1
Authorization: Bearer {tu_token}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para implementar autenticación JWT en mi proyecto",
  "fechaCreacion": "2026-03-09T10:30:00",
  "status": "ABIERTO",
  "autor": "Juan Pérez",
  "curso": "Spring Boot"
}
```

---

#### 5. Actualizar Tópico

```http
PUT http://127.0.0.1:8080/topicos
Content-Type: application/json
Authorization: Bearer {tu_token}

{
  "id": 1,
  "titulo": "¿Cómo implementar JWT con Spring Security?",
  "mensaje": "Necesito ayuda detallada sobre JWT",
  "status": "CERRADO",
  "autor": "Juan Pérez",
  "curso": "Spring Security"
}
```

**Notas:**
- Todos los campos son opcionales excepto `id`
- Solo se actualizan los campos enviados

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo implementar JWT con Spring Security?",
  "mensaje": "Necesito ayuda detallada sobre JWT",
  "fechaCreacion": "2026-03-09T10:30:00",
  "status": "CERRADO",
  "autor": "Juan Pérez",
  "curso": "Spring Security"
}
```

---

#### 6. Eliminar Tópico (Lógico)

```http
DELETE http://127.0.0.1:8080/topicos/1
Authorization: Bearer {tu_token}
```

**Respuesta exitosa (204 No Content)**

> **Nota:** Esta es una eliminación lógica. El tópico permanece en la base de datos pero no aparece en el listado de tópicos activos.

---

#### 7. Reactivar Tópico

```http
PUT http://127.0.0.1:8080/topicos/1/reactivar
Authorization: Bearer {tu_token}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para implementar autenticación JWT",
  "fechaCreacion": "2026-03-09T10:30:00",
  "status": "ABIERTO",
  "autor": "Juan Pérez",
  "curso": "Spring Boot"
}
```

---

## 🔒 Seguridad

- La API utiliza **JWT (JSON Web Tokens)** para autenticación
- Las contraseñas se almacenan encriptadas con **BCrypt**
- El token tiene una validez de **4 semanas**
- Solo el endpoint `/login` es público, todos los demás requieren autenticación
- Los tokens deben enviarse en el header: `Authorization: Bearer {token}`

### Usuario de Prueba

Por defecto, la aplicación incluye un usuario de prueba:

- **Login:** `admin`
- **Contraseña:** `admin123`

## 📁 Estructura del Proyecto

```
forohub/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aluracursos/forohub/
│   │   │       ├── controller/
│   │   │       │   ├── AutenticacionController.java
│   │   │       │   ├── TopicoController.java
│   │   │       │   └── TestController.java
│   │   │       ├── domain/
│   │   │       │   ├── usuario/
│   │   │       │   │   ├── Usuario.java
│   │   │       │   │   ├── UsuarioRepository.java
│   │   │       │   │   ├── DatosAutenticacion.java
│   │   │       │   │   └── AutenticacionService.java
│   │   │       │   ├── topico/
│   │   │       │   │   ├── Topico.java
│   │   │       │   │   ├── TopicoRepository.java
│   │   │       │   │   ├── DatosRegistroTopico.java
│   │   │       │   │   ├── DatosListaTopico.java
│   │   │       │   │   ├── DatosDetalleTopico.java
│   │   │       │   │   └── DatosActualizarTopico.java
│   │   │       │   └── ValidacionException.java
│   │   │       ├── infraest/
│   │   │       │   ├── security/
│   │   │       │   │   ├── SecurityConfigurations.java
│   │   │       │   │   ├── SecurityFilter.java
│   │   │       │   │   ├── TokenService.java
│   │   │       │   │   └── DatosTokenJWT.java
│   │   │       │   └── exceptions/
│   │   │       │       └── GestorDeErrores.java
│   │   │       └── ForohubApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   │           ├── V1__create-table-usuarios.sql
│   │           ├── V2__create-table-topicos.sql
│   │           ├── V3__insert-usuario-prueba.sql
│   │           └── V4__actualizar-contrasena-admin.sql
│   └── test/
│       └── java/...
├── pom.xml
└── README.md
```

## 🧪 Pruebas con Postman

### Flujo Completo de Pruebas

1. **Autenticarse:**
   - POST `/login` con credenciales `admin/admin123`
   - Copiar el token devuelto

2. **Crear tópicos:**
   - POST `/topicos` con el token en el header
   - Crear al menos 2-3 tópicos diferentes

3. **Listar tópicos activos:**
   - GET `/topicos` con el token

4. **Ver detalle:**
   - GET `/topicos/{id}` con el token

5. **Actualizar un tópico:**
   - PUT `/topicos` con el token y los datos a actualizar

6. **Eliminar un tópico:**
   - DELETE `/topicos/{id}` con el token

7. **Listar todos (incluidos inactivos):**
   - GET `/topicos/all` con el token
   - Verificar que aparece el tópico eliminado

8. **Reactivar tópico:**
   - PUT `/topicos/{id}/reactivar` con el token
   - Verificar que aparece nuevamente en el listado de activos

### Importar Colección a Postman

Para facilitar las pruebas, puedes crear una colección en Postman con:
- Una variable de entorno `{{baseURL}}` = `http://127.0.0.1:8080`
- Una variable de entorno `{{token}}` para almacenar el JWT
- Configurar la autenticación Bearer Token en la colección

## ⚠️ Solución de Problemas

### Puerto 8080 en uso
```bash
# Windows
netstat -ano | findstr :8080
taskkill /F /PID <numero_pid>
```

### Error de conexión a MySQL
- Verificar que MySQL esté ejecutándose
- Validar las credenciales en las variables de entorno
- Verificar que la base de datos `forohub` exista

### Error "getaddrinfo ENOTFOUND localhost"
- Usar `127.0.0.1` en lugar de `localhost` en las URLs de Postman

### Token expirado
- Generar un nuevo token haciendo login nuevamente
- Los tokens tienen validez de 4 semanas

## 📄 Licencia

Este proyecto fue desarrollado como parte del Challenge ForoHub de Alura Latam.

## ✨ Características Adicionales

- ✅ Validación de tópicos duplicados
- ✅ Eliminación lógica (soft delete)
- ✅ Paginación configurable
- ✅ Endpoint para listar todos los registros (incluidos inactivos)
- ✅ Endpoint para reactivar registros eliminados
- ✅ Manejo centralizado de errores
- ✅ Migraciones automáticas con Flyway
- ✅ Logs de debug para Spring Security

---

**Desarrollado con ❤️ usando Spring Boot**
