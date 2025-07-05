# 📚 API - Documentación
## 📌 Índice


- [**Autenticación**](#-autenticación)
    - [`POST /register`](#-post-register---registrar-un-usuario)
    - [`POST /login`](#-post-login---iniciar-sesión)
- [**Usuarios**](#-usuarios)
    - [`GET /users`](#-get-users---obtener-lista-de-usuarios)
    - [`POST /users`](#-post-users---crear-un-nuevo-usuario)

---
# 🔐 AUTENTICACIÓN

#### 🔍 POST /register - Registrar un usuario

**📥 Respuesta**

`HTTP 200 OK`

```json
{
    "first_name": "string",
    "last_name": "string",
    "email": "string",
    "password_plain": "string",
}
```

**❌ Códigos de error**

- `HTTP 403 Forbidden`: Petición erronea / usuario ya registrado.
- `HTTP 500 Internal Server Error`: Si ocurre un error en el servidor.

---

#### 🔍 POST /login - Iniciar sesión

**📥 Respuesta**

`HTTP 200 OK`

```json
{
    "toker": "string"
}
```

**❌ Códigos de error**

- `HTTP 403 Forbidden`: Credenciales inválidas / petición erronea.
- `HTTP 500 Internal Server Error`: Si ocurre un error en el servidor.


---
# 👥 USUARIOS

#### 🔍 GET /users - Obtener lista de usuarios

**📥 Respuesta**

`HTTP 200 OK`

```json
[
    {
        "id": 1,
        "username": "string",
        "email": "string",
        "firstName": "string",
        "lastName": "string"
    },
    {
        "id": 2,
        "username": "string",
        "email": "string",
        "firstName": "string",
        "lastName": "string"
    }
]
```

**❌ Códigos de error**

- `HTTP 500 Internal Server Error`: Si ocurre un error en el servidor.

---

#### ➕ POST /users - Crear un nuevo usuario

**Descripción:**

Crea un nuevo usuario en el sistema.

**📤 Request Body**

```json
{
    "email": "example@mail.com",  // Correo electrónico
    "firstName": "John",          // Primer nombre
    "lastName": "Doe",            // Apellido
    "password": "strongPassword"  // Contraseña segura
}
```

</br>

**📥 Respuesta**

`HTTP 201 Created`

```json
{
    "id": 1,
    "username": "string",
    "email": "string",
    "firstName": "string",
    "lastName": "string"
}
```

**❌ Códigos de error**

- `HTTP 400 Bad Request`: Si los datos no son válidos.

- `HTTP 500 Internal Server Error`: Si ocurre un error en el servidor.

<!-- <details> <summary><strong>➕ POST /users</strong> - Crear un nuevo usuario</summary></br> -->
<!-- </details> -->