# Evaluacion

Repositorio de Proyectos

Este repositorio contiene cuatro proyectos independientes desarrollados como parte de diferentes evaluaciones y prácticas de desarrollo. A continuación, se describen brevemente cada uno de ellos, junto con sus especificaciones y cómo ejecutarlos.

Proyectos Incluidos

1. Gestión de Eventos con Kafka

Descripción:
Este proyecto utiliza Apache Kafka para gestionar eventos en tiempo real. Permite la producción y consumo de mensajes, y está diseñado para manejar grandes volúmenes de datos de forma eficiente.

Especificaciones:

Framework: Spring Boot

Dependencias principales: Spring Kafka, Apache Kafka

Funcionalidad:

Productor de mensajes

Consumidor de mensajes

Configuración de tópicos

Instrucciones para ejecutar:

Configura un clúster de Kafka (local o en la nube).

Modifica el archivo application.properties para incluir las credenciales del clúster.

Ejecuta la aplicación con:

mvn spring-boot:run

Utiliza herramientas como Kafka CLI o un cliente Kafka GUI para probar la funcionalidad.

2. Creación de un Archivo con 1 Millón de Registros

Descripción:
Este proyecto genera un archivo con un millón de registros simulados, optimizando el rendimiento y la gestión de memoria.

Especificaciones:

Lenguaje: Java

Formato de salida: CSV o JSON

Funcionalidad:

Generación de datos aleatorios

Escritura de datos en bloques para optimizar el rendimiento

Instrucciones para ejecutar:

Configura el archivo application.properties para definir el formato y la ruta de salida.

Ejecuta la aplicación con:

mvn spring-boot:run

Verifica el archivo generado en la ruta especificada.

3. CRUD Básico

Descripción:
Un CRUD básico implementado en Spring Boot para gestionar un catálogo de productos.

Especificaciones:

Framework: Spring Boot

Base de datos: H2 (en memoria)

Entidades: Producto (ID, nombre, descripción, precio, fecha de creación)

Endpoints principales:

GET /api/products: Lista todos los productos.

GET /api/products/{id}: Obtiene un producto por su ID.

POST /api/products: Crea un nuevo producto.

PUT /api/products/{id}: Actualiza un producto existente.

DELETE /api/products/{id}: Elimina un producto por su ID.

Instrucciones para ejecutar:

Modifica el archivo application.properties si deseas usar otra base de datos.

Ejecuta la aplicación con:

mvn spring-boot:run

Prueba los endpoints usando Postman o herramientas similares.

4. Autenticación con JWT

Descripción:
Este proyecto implementa un sistema de autenticación utilizando tokens JWT (JSON Web Tokens).

Especificaciones:

Framework: Spring Boot

Dependencias principales: Spring Security, JWT

Funcionalidad:

Registro de usuarios

Inicio de sesión y generación de tokens

Validación de tokens en endpoints protegidos

Instrucciones para ejecutar:

Configura el archivo application.properties para los parámetros de seguridad (como claves secretas y expiración de tokens).

Ejecuta la aplicación con:

mvn spring-boot:run

Usa Postman para interactuar con los endpoints:

POST /auth/register: Registra un nuevo usuario.

POST /auth/login: Obtiene un token JWT.

Endpoints protegidos requieren el token en el header Authorization.

Requisitos Previos

Java 17+

Maven 3+

IDE o editor de texto compatible con proyectos Spring Boot

Opcional: Docker (para ejecutar servicios como Kafka o bases de datos)

Cómo Clonar el Repositorio

git clone https://github.com/sergioriveraezcok/Evaluacion.git
cd Evaluacion

