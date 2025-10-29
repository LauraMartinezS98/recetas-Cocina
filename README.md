# 🍲 Recetas de Cocina

Una aplicación web (o proyecto) dedicada a gestionar, visualizar y compartir deliciosas recetas de cocina. Este proyecto forma parte del módulo de Programación de Aplicaciones Web (DAW2) / Desarrollo de Software (o el contexto que aplique).

## 🚀 Tecnologías Utilizadas

Este proyecto fue desarrollado utilizando las siguientes tecnologías:

* **Lenguajes de Back-end:** Java / JSP (según los nombres de archivos que se ven en la imagen)
* **Servidor:** Apache Tomcat (Asunción)
* **Base de Datos:** [Indicar tu base de datos: MySQL, PostgreSQL, etc.]
* **Front-end:** HTML, CSS, JavaScript
* **Gestión de Dependencias:** [Si usas Maven, Gradle, etc.]

## 💻 Instalación y Ejecución

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

* [Indicar versión de Java, ej: JDK 17 o superior]
* [Indicar versión del servidor, ej: Apache Tomcat v9 o superior]
* [Indicar software de base de datos]

### Pasos

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/LauraMartinezS98/recetas-Cocina.git](https://github.com/LauraMartinezS98/recetas-Cocina.git)
    ```

2.  **Configurar la Base de Datos:**
    * Crea una base de datos con el nombre `recetas_db`.
    * Ejecuta el script SQL ubicado en `[RUTA/AL/SCRIPT.sql]` para crear las tablas necesarias.
    * Asegúrate de actualizar los parámetros de conexión (usuario y contraseña) en el archivo `[RUTA/AL/ARCHIVO_DE_CONFIGURACIÓN_DE_DB]`.

3.  **Desplegar en el Servidor:**
    * Importa el proyecto a tu IDE (ej: NetBeans, Eclipse, IntelliJ).
    * Compila y despliega el archivo `.war` (o la carpeta del proyecto) en la instancia de Apache Tomcat.

4.  **Acceder a la Aplicación:**
    Abre tu navegador y ve a `http://localhost:[PUERTO]/recetas-Cocina`

## 📄 Estructura del Proyecto

* `src/main/java/`: Clases Java para la lógica de negocio y controladores.
* `src/main/webapp/`: Archivos web (JSP, CSS, JS).
    * `listaringredientes.jsp`
    * `veringrediente.jsp`
    * [Otros archivos, ej: `index.jsp`]

## 🙋‍♀️ Contribución

Si encuentras errores o quieres proponer mejoras, por favor:

1.  Crea un **Fork** de este repositorio.
2.  Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3.  Realiza tus cambios y haz `commit` (`git commit -m 'feat: Agrega nueva funcionalidad X'`).
4.  Sube tu rama (`git push origin feature/nueva-funcionalidad`).
5.  Abre un **Pull Request**.

## 📧 Contacto

* **Laura Martínez** - [@LauraMartinezS98](https://github.com/LauraMartinezS98) - [TuCorreo@ejemplo.com]
