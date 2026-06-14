TRABAJO PRÁCTICO N° 5: GESTIÓN DE INVENTARIO INTELIGENTE

Integrantes

Martin Ignacio Caniza Astorga EISI1288

Peredo Yucra José David EISI1422  

Neyra Molina Lucas Ismael EISI1535  

Alberto Morales Aveiro EISI1528

Cómo ejecutar el proyecto
Para poner en marcha la aplicación, se deben seguir los siguientes pasos desde la terminal en la carpeta raíz del proyecto:

Primero hay que compilar el proyecto usando el comando: mvn clean package. Es necesario tener instalado Java 21 y configurado en las variables de entorno del sistema.

Una vez que termina de compilar, se puede levantar la aplicación ejecutando el comando: mvn spring-boot:run (o directamente dándole play a la clase principal desde el entorno de desarrollo que se use, como IntelliJ).

Con el servidor corriendo, se puede ingresar desde el navegador a la dirección http://localhost:8081/swagger-ui.html para ver y probar todos los endpoints que armamos, los cuales quedaron documentados con Swagger.

Nota adicional de ejecución
Ademas de Intellij, el proyecto puede ejecutarse sin problemas desde visual Studio Code utilizando la terminal integrada.
Los mismos comandos (mvn clean package y mvn spring-boot:run) funcionan allí, lo que facilita a quienes prefieren VS Code como entorno de desarrollo.  
También se recomienda probar los endpoints con Postman, además de Swagger UI, para validar las respuestas en distintos escenarios.

Observación técnica adicional 
Durante las pruebas de rendimiento notamos que el uso de Streams en Java introduce un costo inicial mayor en datasets pequeños, pero escala correctamente en conjuntos grandes.  
Esto explica parte de las diferencias observadas en los tiempos medidos.

Desarrollo del proyecto y requerimientos
Cumplimos con todos los puntos solicitados en la consigna. A continuación resumimos cómo resolvimos los temas principales:

Repositorios genéricos: Diseñamos la interfaz IGenericRepository y la clase abstracta GenericInMemoryRepository. De esta forma evitamos duplicar el código CRUD básico en cada repositorio. Para el almacenamiento de los datos usamos un ConcurrentHashMap y para los IDs un AtomicLong, asegurando que el sistema no tenga problemas si se hacen varias peticiones al mismo tiempo.

Manejo del stock: El stock dentro de la clase Producto lo definimos como un AtomicInteger. Esto lo hicimos para evitar errores de consistencia en los datos si coinciden un ingreso y un egreso de mercadería en el mismo milisegundo desde hilos diferentes.

Patrón Strategy: Implementamos el patrón Strategy para la lógica de las alertas de stock. El sistema lee los límites (mínimo y crítico) directamente desde el archivo application.yml. Si en el futuro se quiere cambiar la forma de calcular cuándo un producto está en alerta, se puede hacer agregando otra estrategia sin tocar el código que ya funciona.

Arquitectura y SOLID: Respetamos la separación de capas. No usamos la anotación @Autowired en los atributos, sino que todas las dependencias se inyectan a través de los constructores. Además, los controladores nunca exponen las entidades directas del modelo, sino que la comunicación hacia afuera se maneja estrictamente con Records DTO.

Documentación: Generamos los comentarios JavaDoc para todas las clases e interfaces públicas, detallando el uso de los métodos, sus parámetros, retornos y las excepciones que pueden lanzar. El comando mvn javadoc:javadoc compila limpio y sin errores.

Reporte de performance
Para cumplir con la parte de rendimiento, habilitamos el endpoint administrativo GET /api/admin/performance-report. Al pegarle a esa dirección, el sistema procesa conjuntos de datos de 1k, 10k y 100k registros para medir los tiempos de respuesta reales.

Todos estos resultados, junto con el análisis teórico de la complejidad Big O de cada operación y la explicación de las diferencias encontradas en las mediciones, los dejamos detallados en el archivo PERFORMANCE.md que guardamos en la raíz del proyecto.

Trabajo Práctico 4 - Resumen de lo que hicimos
Alumnos
Martin Ignacio Caniza Astorga EISI1288 ||  
Peredo Yucra José David EISI1422 ||
Neyra Molina Lucas Ismael EISI1535 ||  
Alberto Morales Aveiro EISI1528
URL Repositorio:

Tabla de Tiempos de Carga
Los tiempos están en nanosegundos (ns). Son los promedios que sacamos para ver qué tan rápido andaba nuestro programa.

| Endpoint | 1k registros | 10k registros | 100k registros | Escala observada |
|----------|:---:|:---:|:---:|:---:|
| `GET /api/productos` | 2,100,000 ns | 18,500,000 ns | 200,000,000 ns | O(n) |
| `GET /api/productos/{id}` | 1,200 ns | 1,500 ns | 1,800 ns | O(1) |
| `POST /api/productos` | 3,000 ns | 3,200 ns | 3,500 ns | O(1) |
| `GET /api/productos/buscar?q=` | 2,800,000 ns | 25,000,000 ns | 260,000,000 ns | O(n*m) |
| `GET /api/productos/ordenados` | 5,000,000 ns | 60,000,000 ns | 800,000,000 ns | O(n log n) |
| `GET /api/alertas/stock-bajo` | 2,200,000 ns | 19,000,000 ns | 210,000,000 ns | O(n) |

Por qué dan esos tiempos (lo que notamos)

1. La forma de armar el código en Java: Cuando traemos todos los productos o buscamos algo, usamos una cosa de Java que se llama Stream. Nos dimos cuenta de que cuando hay poquitos datos (1k) tarda un poco en arrancar y preparar todo. Si hubiéramos usado un ciclo for normal seguro era más rápido al principio. Pero cuando ya son 100 mil datos, ahí sí se nota que el tiempo va subiendo normal, a medida que hay más cosas que cargar.
2. El problema de buscar textos: Cuando buscamos productos por el nombre, el tiempo sube un montón en comparación a solo traer la lista. Esto pasa porque la compu no solo tiene que pasar por cada producto, sino que tiene que ir leyendo letra por letra adentro del texto para ver si coincide con lo que buscamos. Como hace doble trabajo, se vuelve re pesado.
3. Los tirones por la limpieza de memoria: Cuando hicimos la prueba de meter 100.000 cosas juntas, vimos que de la nada el tiempo pegaba unos saltos re locos y se trababa. Buscamos y parece que es porque al crear tantas cosas de golpe, Java tiene un sistema que borra la basura de la memoria (el Garbage Collector) y cuando se prende para limpiar, congela el programa un segundito y por eso nos dio esos tiempos raros.
