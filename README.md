# invirtual-backend

## Entorno local

Requisitos: JDK 21, Docker Compose y el Maven Wrapper incluido (`mvnw.cmd` en Windows o `./mvnw` en macOS/Linux). El wrapper fija Maven 3.9.10.

Desde esta carpeta, `docker-compose.yml` es el archivo Compose único. PostgreSQL 15 inicia por defecto:

```powershell
Copy-Item .env.example .env
docker compose up -d postgres
```

La base se publica solo en `127.0.0.1:5432`. El volumen persistente se llama `postgres_data`, igual que antes. No uses `docker compose down -v` si quieres conservar la base.

Las credenciales predeterminadas son únicamente para desarrollo local. Para cambiarlas, define `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` y, opcionalmente, `POSTGRES_PORT` en `.env`; la contraseña de la aplicación debe coincidir con la de PostgreSQL. Spring Boot iniciado desde el host no carga `.env` automáticamente: configura sus variables también en la terminal o IDE. Si cambias `POSTGRES_PORT`, ajusta `SPRING_DATASOURCE_URL`. No uses estos valores en un entorno compartido o desplegado.

Spring Boot puede arrancar desde el host con los valores locales predeterminados de `application.properties`. Define `SUPABASE_ISSUER_URI` para usar otro proyecto Supabase y `JWT_SECRET` con un secreto local de al menos 32 bytes. Stripe no se necesita para iniciar la API; las operaciones de pago requieren `STRIPE_SECRET_KEY` y el webhook `STRIPE_WEBHOOK_SECRET`. Las URLs se configuran mediante `FRONTEND_URL`, `STRIPE_SUCCESS_URL` y `STRIPE_CANCEL_URL`; el entorno de Stripe y moneda mediante `STRIPE_ENVIRONMENT` y `STRIPE_DEFAULT_CURRENCY`.

Los servicios `app` y `pgadmin` están disponibles en los perfiles `app` y `tools`; no arrancan junto con PostgreSQL por defecto. Al seleccionar uno directamente desde Run Service, Compose activa su perfil. Antes de arrancar `app` en un contenedor, genera el JAR que espera el `Dockerfile` (`target/*.jar`) y copia `.env.example` a `.env`; luego selecciona `app` en Run Service o ejecuta `docker compose up -d app`. Para pgAdmin, selecciona `pgadmin` del mismo modo. El servicio `app` fuerza `ddl-auto=validate` y desactiva la inicialización SQL antigua para que Flyway gestione el esquema.

Arranque manual desde PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

El perfil se puede elegir con `SPRING_PROFILES_ACTIVE`. Hibernate conserva `ddl-auto=validate`; Flyway crea o actualiza el esquema antes de la validación.

## Migraciones y datos existentes

Las migraciones versionadas viven en `src/main/resources/db/migration`. Una base vacía recibe el esquema de `V1` y los índices y catálogos iniciales de `V2`. Las inserciones de catálogos usan claves naturales e `ON CONFLICT DO NOTHING`, de modo que conservan los IDs existentes y se pueden aplicar una sola vez por Flyway.

No se activa un baseline automático. En una base no vacía sin historial de Flyway, la API se detendrá en vez de asumir que el esquema coincide. Para adoptar una instalación antigua, primero haz una copia de seguridad y compara sus tablas/columnas con `V1`. Si ya contiene el esquema completo compatible con V1, configura temporalmente `SPRING_FLYWAY_BASELINE_ON_MIGRATE=true` y `SPRING_FLYWAY_BASELINE_VERSION=1`, e inicia la API una vez: Flyway registra esa base y aplica `V2` (catálogos e índices). Después quita esas variables. Si el esquema está incompleto o difiere, no lo baselinees: requiere una migración de transición específica que conserve y adapte esos datos. Si los catálogos existentes tienen nombres duplicados, la creación de los índices únicos se detendrá sin borrar datos; resuelve esos duplicados antes de volver a migrar.

El archivo histórico `src/main/resources/db/schema.sql` ya no es un script ejecutable: las migraciones contienen sus índices corregidos. `stripe_session_id` pertenece a `orders`; el índice anterior apuntaba a una columna/tabla inexistente. El límite de entitlement no consumido se aplica a `entitlements(user_id)`.

Validación autorizada del 16 de octubre de 2026: Maven inició la API contra PostgreSQL 15.19. Flyway validó las dos migraciones y reportó la versión 2 al día; no fue necesario aplicar cambios. Hibernate pasó `ddl-auto=validate` y Spring Boot inició correctamente. Esta comprobación cubre la base local existente, no una base vacía ni la repetición de migraciones. No se verificó el arranque de Compose/Run Service ni se ejecutaron pruebas. Maven indicó que las clases estaban actualizadas, por lo que este arranque no demuestra que los cambios Java hayan sido recompilados.
