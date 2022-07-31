Test

Resumen de la implementación, Para mayor información leer los archivos adjuntados en la carpeta /documentación/

Se utiliza arquitectura de microservicios
El lenguaje utilizado para el desarrollo es JAVA con Framework Spring Boot
Se crea un controller con dos requestMapping (/containers: POST y /stats: GET )
Se realiza una conexion a la Base de datos MySql donde se crearon tres SP y una tabla
Se hostear esa API en Heroku.

URL de la API:
http://(GET)
http://(POST)

Instrucciones de ejecución:
Ejecutar las anteriores URL por medido de cualquier ID que permita el consumo de peticiones HTTP REST. (POSTMAN)

Cosas para mejorar la implementación:
Seguridad de los consumos REST (Token).
