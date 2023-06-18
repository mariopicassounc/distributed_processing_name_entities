# Informe Laboratorio 3 Grupal

## Integrantes

- Mario Picasso

- Matias Scantamburlo

- Mateo Malpassi

--------------------
## Índice
* [Aclaración: Como ejecutar este programa](aclaracón:_como_ejecutar)
* [Selección de código base](selección_de_código_base)
* [Implementación](implementación)
* [Conclusión](conclusión)

--------------------

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

Para lograrlo realizamos cambios, haciendo uso de las funciones propias de Spark, que después terminamos modularizando en métodos. El proyecto quedo estructurado de la siguiente manera:

1. `initSparkConfig()`: Inicializa la configuración de Spark.
2. `readSubscriptionFile()`: Lee el archivo de suscripciones.
3. `processAndShowFeeds(Subscription subscription)`: Se procesa y muestra los feeds de las suscripciones, y devuelve una lista de artículos.
4. `printNamedEntities(JavaRDD<String> RDDArticlesString)`: Se imprimen las entidades con nombre.
5. `getInvertedIndex(JavaRDD<String> RDDArticlesString)`: Se obtiene el índice invertido.

#### getInvertedIndex

Ciertamente el desarrollo de esta segunda parte se centraliza en este método. El resultado final del mismo es un RDD llamado RDDInvertIndex que contiene el índice invertido.

El índice invertido permite una búsqueda eficiente de documentos que contienen un término específico. En lugar de buscar en todos los documentos del conjunto, el índice se utiliza para identificar rápidamente los documentos relevantes que contienen el término buscado.

En el código, `getInvertedIndex()` construye un ejemplo de este tipo de indice. Cada palabra en los artículos se mapea a una lista de tuplas que contiene el índice del artículo y el recuento de la palabra en ese artículo. El resultado final es un RDD que representa el índice invertido, donde cada palabra se mapea a una lista de tuplas de (índice, recuento) que indican en qué documentos aparece y con qué frecuencia.

Más precisamente. Se comienza con un RDD (Resilient Distributed Dataset) llamado RDDArticlesString que contiene los textos de los artículos.

A continuación, se realiza un proceso de transformación para dividir cada artículo en palabras. Esto se hace utilizando la función flatMap que toma cada artículo y lo divide en palabras individuales. El resultado es un nuevo RDD llamado RDDWords.

Después, se realiza un mapeo en el RDD RDDWords para crear pares clave-valor, donde la clave es una palabra y el valor es el número 1. Esto se hace para contar la frecuencia de cada palabra. El resultado es un RDD llamado ones.

Luego, se realiza una reducción por clave en el RDD ones para sumar los valores asociados a cada palabra. Esto nos da la frecuencia total de cada palabra en el conjunto de artículos. El resultado es un RDD llamado counts.

A continuación, se recopila el resultado del RDD counts en una lista de tuplas (palabra, frecuencia) llamada output.

Se crea una instancia de una heurística llamada h utilizando la clase QuickHeuristic. Esta heurística se utiliza más adelante para identificar si una palabra es una entidad nombrada.

Luego, se crea una instancia de FactoryNamedEntity que se encarga de crear la lista de entidades nombradas a partir de la lista de tuplas output.

Por último y después de crear la lista de entidades nombradas, se imprime en la consola.

### Conclusión

Utilizar un framework como Apache Spark para la implementación de nuestro código presenta grandes beneficios, que hicieron de este lab mucho más facil de implementar que el anterior. Este devido a que Spark permite procesar grandes volúmenes de datos de manera eficiente y paralela, sumado a un gran catálogo de funciónes. Más precisamente, las virtudes que más se destacan incluyen:

*     Rendimiento rápido: Spark es capaz de procesar grandes cantidades de datos de manera rápida gracias a su capacidad para aprovechar la memoria de manera eficiente. Esto significa que nuestras operaciones y análisis se ejecutarán más rápidamente, lo que nos permite obtener resultados más rápidos.

* Fácil de usar: Spark proporciona una forma sencilla de escribir código para procesar datos a gran escala. No tenemos que preocuparnos por detalles complicados de implementación distribuida, ya que Spark se encarga de manejarlos por nosotros. Esto facilita el desarrollo de aplicaciones distribuidas sin requerir conocimientos avanzados en sistemas distribuidos.

* Funciona con diferentes lenguajes: Spark es compatible con varios lenguajes de programación, como Python, Java, Scala y R. Esto significa que podemos utilizar el lenguaje que más nos convenga o en el que tengamos más experiencia, sin tener que aprender un nuevo lenguaje específico para Spark.

* Amplio conjunto de herramientas: Spark cuenta con una variedad de herramientas integradas que amplían su funcionalidad. Estas herramientas nos permiten realizar tareas como el procesamiento en tiempo real, el análisis de datos estructurados, el aprendizaje automático y el procesamiento de gráficos. Esto nos brinda opciones adicionales y nos permite realizar análisis más sofisticados y completos.

* Escalabilidad: Spark puede crecer junto con nuestros requerimientos de datos y procesamiento. Podemos agregar más servidores y recursos al clúster de Spark para aumentar su capacidad de procesamiento. Esto significa que podemos manejar grandes volúmenes de datos sin problemas y asegurarnos de que nuestro sistema siga siendo eficiente incluso a medida que crecemos.

Si bien sabiamos que, al igual que la mayoría de framework, aprender a utilizar Spark iba a ser un desafio. Sin embargo a través de la ayuda de AI, la implementación fue simple de llevar a cabo.

Por último podemos decir que fue un laboratorio importante, en el que tuvimos que dedicar grán parte del tiempo al estudio de las herramientas usadas y como estas se operaban. Así también pensar un algoritmo eficiente en el cual poder plasmar la idea de un índices invertido que cumpla con lo pedido.