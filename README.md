# Sistema de Gestión Organizacional

Aplicación de escritorio desarrollada con Java 17, Swing, JDBC y MySQL. El proyecto
usa la estructura **Java with Ant > Java Application** de Apache NetBeans y puede
abrirse directamente con **File > Open Project**.

## Requisitos

- Apache NetBeans 17 o posterior.
- JDK 17 configurado como plataforma Java del proyecto.
- MySQL Server 8.0 o posterior en ejecución.

El controlador oficial MySQL Connector/J 9.7.0 está incluido en `lib/`.

## Configurar MySQL

Edite `settings.properties` antes de ejecutar el sistema:

```properties
db.host=localhost
db.port=3306
db.name=sistema_gestion_organizacional
db.user=root
db.password=
db.useSSL=false
db.allowPublicKeyRetrieval=true
db.createIfNotExists=true
```

Use un usuario MySQL con permisos sobre el esquema indicado. Con
`db.createIfNotExists=true`, el sistema intenta crear la base de datos durante el
splash screen. Si el usuario no tiene permiso `CREATE DATABASE`, cree previamente
el esquema y cambie la propiedad a `false`.

Para producción, utilice un usuario exclusivo con privilegios mínimos y no guarde
una contraseña real en un repositorio público.

## Abrir y ejecutar

1. Descomprima el archivo entregado.
2. Abra Apache NetBeans.
3. Seleccione **File > Open Project**.
4. Elija la carpeta `SistemaGestionOrganizacional`.
5. Presione **Run Project**.

En el primer inicio, el script `database/database.sql` crea las tablas MySQL,
claves foráneas, restricciones, índices y datos iniciales de demostración.

Antes del login se muestra una pantalla de bienvenida mientras se prepara la base
de datos. Utilice estas credenciales iniciales:

```text
Usuario: admin
Contraseña: Admin123*
Rol: ADMINISTRADOR
```

La contraseña no se almacena como texto: se protege mediante PBKDF2-HMAC-SHA256,
salt individual y 210,000 iteraciones.

## Formularios editables

Todos los formularios tienen su archivo `.java` y `.form` correspondiente:

- `FrmSplash`: pantalla de carga e inicialización.
- `FrmLogin`: acceso seguro con validación y opción de mostrar contraseña.
- `FrmPrincipal`: ventana MDI (`JFrame`, `JDesktopPane` y menús).
- `FrmPais`: CRUD de países.
- `FrmDepartamento`: CRUD de departamentos y relación con país.
- `FrmCargo`: CRUD de cargos y rangos salariales.
- `FrmEmpleado`: CRUD de empleados, departamento y cargo.
- `FrmProyecto`: CRUD de proyectos.
- `FrmAsignacion`: asignación de empleados a proyectos.

Para editar un formulario visualmente, ábralo desde **Source Packages** y seleccione
la pestaña **Design**. Los diseños usan administradores estándar compatibles con
Matisse (`BorderLayout`, `GridLayout` y `FlowLayout`), sin posiciones absolutas.

## Arquitectura

```text
com.gestionorganizacional
├── modelo       Entidades y agregado Empresa
├── dao          Persistencia JDBC y consultas preparadas
├── controlador  Validaciones y coordinación de casos de uso
├── vista        Formularios Swing MDI y archivos .form
├── conexion     Configuración, conexión e inicialización de MySQL
├── util         Mensajes, conversión y validaciones reutilizables
└── principal    Punto de entrada de la aplicación
```

## Reglas incluidas

- Valores obligatorios, formatos de correo y fechas `AAAA-MM-DD`.
- Código ISO de dos o tres letras.
- Salarios y presupuestos no negativos.
- Salario máximo igual o mayor que el mínimo.
- Fecha final del proyecto no anterior a la fecha inicial.
- Entre 1 y 200 horas por asignación.
- Una sola asignación por combinación empleado/proyecto.
- Integridad referencial y mensajes comprensibles al intentar eliminar datos en uso.
- Búsqueda parcial en todos los mantenimientos.
- Autenticación contra MySQL, sesión activa y cierre de sesión.

## Compilar desde consola

Si Apache Ant está instalado:

```text
ant clean jar
```

El JAR y sus bibliotecas se generan en `dist/`. Para ejecutar desde la raíz del
proyecto:

```text
java -jar dist/SistemaGestionOrganizacional.jar
```

## Prueba de integración

La clase `test/com/gestionorganizacional/IntegrationTest.java` verifica el ciclo
completo de inserción, consulta, actualización y eliminación de todas las entidades.
La prueba utiliza la conexión configurada en `settings.properties`. Con Ant puede
ejecutarse mediante:

```text
ant test
```
