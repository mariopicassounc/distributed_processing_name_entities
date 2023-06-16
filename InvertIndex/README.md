# Informe Laboratorio 3 Grupal

## Integrantes

- Mario Picasso

- Matias Scantamburlo

- Mateo Malpassi

### Aclaración: Como ejecutar este programa

Utiliza las librerias:

gson 2.10 https://search.maven.org/remotecontent?filepath=com/google/code/gson/gson/2.10.1/gson-2.10.1.jar

org.json https://search.maven.org/remotecontent?filepath=org/json/json/20230227/json-20230227.jar

Y todos los jars que vienen en la carpeta Jars de la instalacion de Spark. Instructivo abajo.

**Warning**: Al agregar todos los jars se va a agrega una version vieja de gson que puede pisar la anteriormente agregada.

### Selección de código base

Para este laboratorio, debíamos empezar por elegir una resolución de las 3 hechas para la parte 1 de este laboratorio. Las 3 soluciones dadas coinciden en varios aspectos: 

1. Se crea una lista con todos los artículos que tengan los feeds que se procesan, creando una List<String> con todas las palabras de todos los artículos. Luego, se le aplican filtros y se la manipula para la tarea que sigue.

2. Se mapea cada palabra a pares: (palabra, 1)

3. Luego, con reduceByKey se efectúa la suma de cada par creado para llevar poder llevar la cuenta de cuántas veces aparece dicha palabra

4. Se crea la lista de entidades nombradas usando la clase FactoryNamedEntity, donde a cada palabra de esta lista antes creada, se la compara con la heurística que define si dicha palabra es namedEntity o no

5. Por último, se crea dicha namedEntity con su respectivo constructor y su respectivo Theme; luego se suma la frecuencia de cada una, para después mostrar los resultados en pantalla con un prettyPrint

> Como detalle a aclarar, la clase FactoryNamedEntity podría estar modulada en dos clases diferentes para ser más prolijo y claro el código; de igual manera, el código es totalmente funcional 

Como las soluciones coinciden, elegimos trabajar en el código base de Mario.

### Implementación 

Para este laboratorio se pide:

- Recuperar los documentos que contengan una determinada palabra o entidad nombrada, ordenados desde el documento que tiene la mayor cantidad de ocurrencias de la misma al documento que tiene la menor cantidad de ocurrencias. Para hacerlo deberán crear un índice invertido de la colección de documentos.

La implementación de nuestra solución fue:















