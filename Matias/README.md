# Informe Laboratorio 3:
#### Programación asistida para Frameworks sobre Cálculo Distribuido
Paradigmas 2023 - FAMAF - UNC

-----------------

### Autor

- Matías Javier Scantamburlo
  
-----------------
## Índice
- [Informe Laboratorio 3:](#informe-laboratorio-3)
      - [Programación asistida para Frameworks sobre Cálculo Distribuido](#programación-asistida-para-frameworks-sobre-cálculo-distribuido)
    - [Autor](#autor)
  - [Índice](#índice)
  - [Pregunta 1](#pregunta-1)
      - [¿Cómo se instala Spark en una computadora personal?](#cómo-se-instala-spark-en-una-computadora-personal)
  - [Pregunta 2](#pregunta-2)
      - [¿Qué estructura tiene un programa en Spark?](#cómo-se-crea-una-instancia-de-spark)
  - [Pregunta 3](#pregunta-3)
      - [¿Qué estructura tiene un programa de conteo de palabras en diferentes documentos en Spark?](#qué-estructura-tiene-un-programa-de-conteo-de-palabras-en-diferentes-documentos-en-spark)
  - [Pregunta 4](#pregunta-4)
      - [¿Cómo adaptar el código del Laboratorio 2 a la estructura del programa objetivo en Spark?](#cómo-adaptar-el-código-del-laboratorio-2-a-la-estructura-del-programa-objetivo-en-spark)
  - [Pregunta 5](#pregunta-5)
      - [¿Cómo se integra una estructura orientada a objetos con la estructura funcional de map-reduce?](#cómo-se-integra-una-estructura-orientada-a-objetos-con-la-estructura-funcional-de-map-reduce)
  - [Interacciones con StarChat](#interacciones-con-starchat)
    - [Preguntas sobre como incluir las librerias de Spark:](#preguntas-sobre-como-incluir-las-librerias-de-spark)
    - [Preguntas sobre la clase SparkConf:](#preguntas-sobre-la-clase-sparkconf)
    - [Preguntas sobre JavaRDD y parallelize:](#preguntas-sobre-javardd-y-parallelize)
    - [Pregunta sobre JavaPairRDD y map():](#pregunta-sobre-javapairrdd-y-map)
  - [Código de utilidad](#código-de-utilidad)
    - [SparkConf.class](#sparkconfclass)
    - [JavaSparkContext](#javasparkcontext)

## Pregunta 1
#### ¿Cómo se instala Spark en una computadora personal?

1. Verificar que Java está instalado en la computadora. Para ello, abrir una terminal y ejecutar el comando `java -version`. En el caso de no estar instalado se debé de seguir los siguientes pasos.
   
* Instalar Java mediante el siguiente comando en Ubuntu 22 para obtener la última versión LTS, Openjdk 17:
  
```bash
sudo apt-get install default-jdk
```

2. Acceder al sitio web de descargas de Apache Spark (https://spark.apache.org/downloads.html) y seleccionar la versión deseada. Hacer clic en el enlace después de "Download spark: " para iniciar la descarga.
3. Antes de descargar, crear una carpeta de destino para almacenar los archivos descargados. Utilizar los siguientes comandos para crear la carpeta y descargar los archivos:
  
```bash
sudo su -
mkdir -p /opt/scala
cd /opt/scala
wget <enlace proporcionado por Spark>
tar -xvf <archivo descargado>

```

4. Configurar las variables de entorno. Abrir otro terminal y abrir el archivo ~/.bashrc con el siguiente comando:
  
```bash
gedit ~/.bashrc
```

5. Agregar la siguiente línea al final del archivo:

```bash
SPARK_HOME=/opt/spark/<nombre de la carpeta descomprimida>
export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin
```

6. Guardar los cambios y ejecutar el siguiente comando para aplicar los cambios en la sesión actual:
  
```bash
source .bashrc

```

7. Después de esto, Spark estará listo para su uso. Probar ejecutando el software con el siguiente comando:

```bash
spark-shell
```

## Pregunta 2
#### ¿Qué estructura tiene un programa en Spark?
En un programa Spark, el primer paso es crear un objeto SparkContext, que es responsable de establecer la comunicación entre la aplicación y el clúster de Spark. Para ello, se debe construir previamente un objeto SparkConf que almacena la configuración de la aplicación, como el nombre, la cantidad de recursos a utilizar y la dirección del clúster.

En el contexto de Spark, se utilizan los RDD (Resilient Distributed Datasets), que son estructuras de datos distribuidas y tolerantes a fallos. Un RDD representa una colección inmutable y particionada de objetos que pueden procesarse en paralelo. Estos RDD pueden crearse mediante la paralelización de una colección existente en el programa o mediante la carga de datos desde sistemas de almacenamiento externos, como HDFS o bases de datos.

La tolerancia a fallos en Spark se logra a través del registro de información sobre la estructura de los RDD y las transformaciones aplicadas a ellos. En caso de fallos, Spark puede reconstruir automáticamente los RDD perdidos a partir de los datos almacenados en disco o en memoria, lo que garantiza la disponibilidad y confiabilidad de los datos.

Las transformaciones en Spark son operaciones que generan nuevos RDD a partir de uno o más RDD existentes. Estas transformaciones son evaluadas de manera perezosa (lazy evaluation), lo que significa que no se ejecutan inmediatamente. En su lugar, Spark registra la secuencia de transformaciones aplicadas y las aplica cuando se realiza una acción. Esto permite a Spark optimizar las operaciones y ejecutarlas de manera eficiente en el clúster.

Las acciones en Spark son operaciones que devuelven un valor o generan una salida a partir de los RDD. Al realizar una acción, Spark evalúa todas las transformaciones dependientes y realiza los cálculos necesarios en el clúster para generar el resultado solicitado.

Además proporciona una amplia gama de transformaciones y operaciones de alto nivel que se pueden aplicar a los RDD, como filter, reduce, join, maps. Estas operaciones permiten a los desarrolladores realizar diversas manipulaciones en los RDD. En particular La integración de la operación map con las RDD en Spark permite realizar fácilmente operaciones de procesamiento distribuido y paralelo en grandes conjuntos de datos.

Tambien contamos con algunas extenciones de la clase RDD, como JavaRDD y JavaPairRDD, que nos permiten trabajar con colecciones de Java en lugar de las de Scala, brindando una serie de operaciones específicas para trabajar con pares clave-valor.

## Pregunta 3
#### ¿Qué estructura tiene un programa de conteo de palabras en diferentes documentos en Spark?

Un programa de conteo de palabras en diferentes documentos en Spark generalmente sigue una estructura básica que involucra las siguientes etapas:

1. Carga de datos: El primer paso es cargar los documentos de texto en Spark. Esto se puede hacer leyendo los archivos desde un sistema de almacenamiento, como HDFS, o utilizando otras fuentes de datos, como bases de datos o flujos de datos en tiempo real. Los documentos se cargan como RDD (Resilient Distributed Datasets) o como otro tipo de estructura de datos distribuida, dependiendo de la versión de Spark utilizada.

2. Preprocesamiento de datos: Una vez que los documentos están cargados, es común realizar un preprocesamiento de los datos para limpiarlos y prepararlos para el conteo de palabras. Esto puede implicar la eliminación de caracteres especiales, conversión a minúsculas, tokenización (división del texto en palabras individuales) y eliminación de palabras vacías (como artículos o conjunciones).

3. Transformaciones y conteo: A continuación, se aplican transformaciones en los datos preprocesados para realizar el conteo de palabras. Esto se puede lograr utilizando operaciones como flatMap, que transforma cada línea de texto en una secuencia de palabras, y map, que asigna una clave a cada palabra para prepararlas para el conteo. Luego, se utiliza la operación reduceByKey para agrupar las palabras por clave (es decir, por palabra) y realizar el conteo.

4. Ordenamiento y salida de resultados: Una vez que se ha realizado el conteo de palabras, es común ordenar los resultados según el número de ocurrencias de cada palabra. Esto se puede hacer utilizando la operación sortBy en el RDD resultante. Luego, los resultados se pueden guardar en un archivo, mostrar en la consola o utilizar de alguna otra manera según los requisitos de la aplicación.

## Pregunta 4
#### ¿Cómo adaptar el código del Laboratorio 2 a la estructura del programa objetivo en Spark?

Primero se realizo, con ayuda de AI, una lectura a las clases basicas de Spark. Se aprendio que era un RDD y las clases basicas como SparkConf y, su adptación a java, JavaSparkContext. Luego se realizo una lectura de la documentación de Spark para Java, donde se encontraron las clases JavaRDD y JavaPairRDD. Estas clases son las que nos brindaron los metodos necesarios para realizar el conteo de palabras en los documentos.

Esta parte se ralizo con la ayuda de StarChat, cuyas consultas quedan archivadas en la sección [Interacciones con StarChat](#interacciones-con-starchat). Así tambíen queda adjunto el código relevante para la comprención de la primera parte en [Código de utilidad](#código-de-utilidad).

## Pregunta 5
#### ¿Cómo se integra una estructura orientada a objetos con la estructura funcional de map-reduce?

## Interacciones con StarChat

### Preguntas sobre como incluir las librerias de Spark:
\> **Matias**

I have a code in Java and I need to translate a part of the project to spark. What is the first thing I need to add to my code?

< **StarChat**

To include Apache Spark libraries in VSCode, follow these steps: Download the library JAR files from the official website into a folder accessible by VS Code. The latest versions of Spark can usually be found under “Downloads” > “Spark”. 
Inside your pom.xml or build.sbt, specify which dependencies contain Spark classes so they get included when building your project. For instance, if you’re working with Spark SQL, you might want to add the following dependency block inside <dependencies>:

```
<dependency>
<groupId>org.apache.spark</groupId>
<artifactId>spark-sql_${scala.version}</artifactId>
<version>${spark.version}</version>
</dependency>
```

Add the downloaded JAR files to your classpath either via the -cp option in the terminal or programmatically during runtime. This ensures that all necessary classes are loaded while running your app locally.

\> **Matias**

In Vscode there is an option that allows you to add library references to the project. Adding the .jar files in this way is it correct?

< **StarChat**

Yes, adding references to library JAR files in a Visual Studio Code project is generally considered safe because the JAR files will typically reside within the same workspace (project) as the code being developed. However, keep in mind that including third party JAR files may increase the risk of compatibility issues between different components used throughout the codebase. It’s always recommended to use managed packages whenever possible instead of manually managing individual JAR files.

### Preguntas sobre la clase SparkConf:
\> **Matias**

¿Como crear una instancia de spark?

< **StarChat**

Para poder crear una instancia de Spark en Java necesitamos importar las librerias correspondientes:
```java
    import org.apache.spark.SparkConf;
    import org.apache.spark.api.java.*;</p>
```

Creando la configuración
// Creamos el objeto que contiene la configuracion para nuestro cluster SparkConf conf = new SparkConf().setAppName("MiAplicacion").setMaster("local[*]"); // [1] // Obtenemos una referencia al contexto de spark del cluster JavaSparkContext sc = new JavaSparkContext(conf);

Cerrando el Contexto
// Una vez terminado con nuestra aplicación cerramos el contexto y liberamos los recursos sc.close();

Notas:
[1]: En este ejemplo estamos utilizando solo un master local, sin embargo si lo deseamos podemos agregar mas workers o incluso modificar algun parametro dentro de la clase SparkConf(), por ejemplo cambiar el directorio temporal donde se almacenaran ciertos datos temporales etc…

### Preguntas sobre JavaRDD y parallelize:
\> **Matias**

What is a JavaRDD?

< **StarChat**

A Java RDD (Resilient Distributed Dataset) represents an immutable collection of elements that can be operated on in parallel. Internally, it maintains a list of partitions, which are distributed across nodes in a cluster. The dataset is split into logical chunks and these chunks are processed by different machines/processes for better performance.

\> **Matias**

What can you tell me about: org.apache.spark.api.java.JavaSparkContext.parallelize(List list)

< **StarChat**

The JavaSparkContext has several methods related to creating RDDs such as textFile, sequenceFile, etc., however there’s also a generic version called parallelize. This method takes two parameters - first one being a List containing all data items and second parameter specifying how many tasks should process those data items in parallel. For example:

```java
    // create some dummy data
    ArrayList<Integer> numbers = new ArrayList<>();
        for (int i=0;i<=1000;++i){
            numbers.add(i*7);
        }

    SparkConf conf = new SparkConf().setAppName("Parallel Processing").setMaster("local[4]");
    JavaSparkContext context = new JavaSparkContext(conf);

    // now we have 4 threads processing our data simultaneously!
    JavaRDD<Integer> rdd = context.parallelize(numbers, 4);
    System.out.println("# of Partitions : " + rdd.getNumPartitions() );
    long count = rdd.count();
    System.out.println("Count : "+count);
```

\> **Matias**

Having already created my variable: JavaRDD RDDArticles = sc.parallelize(stringArticles); How can I split each string into individual words?

< **StarChat**

You could use flatMap method after parallelize like this :
```java
    JavaRDD<String[]> wordRdd = articlesRdd.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.split(" ")).iterator());
```

### Pregunta sobre JavaPairRDD y map():

\> **Matias**

What is the correct way to create tuples with JavaRDD elements?

< **StarChat**

To create Tuples from RDD, you can use map() transformation on your existing RDD as follows:
```java
    // Create an example list of strings
    List<String> inputList = new ArrayList<>();
    for (int i=0; i < 5; ++i) {
        String s = "string_" + Integer.toString(i);
        inputList.add(s);
    }

    // Convert the string list into an RDD
    JavaRDD<String> rdd = jsc.parallelize(inputList);
    
    // Use map() to convert each entry in the RDD to a tuple containing its index and value 
    JavaPairRDD<Long, String> indexedRdd = rdd.mapToPair((Tuple2<Long, String> t) -> 
        new Tuple2<>(t._1(), t._2()));
```
The resulting indexedRdd will contain (index,value) pairs where indices are consecutive long values starting at zero.

\> **Matias**

Which JavaPairRDD functions can be useful to keep count of elements, where the first element of the tuple is an element and the second is the occurrences of it?

< **StarChat**
You may want to use reduceByKey or countByValue. Here’s how you could do that:
```java
    // Count by key using reduceByKey
Map&lt;String, Long&gt; counts = indexedRdd
   .reduceByKey( (a, b) -&gt;  { return a + b; }) // Add up occurrence counts for duplicate keys
   .collectAsMap();

System.out.println("Counts:");
counts.forEach( (key, val) -&gt; { 
    System.out.printf(&quot;%-30s %d\n&quot;, key, val); 
});

// Alternatively, count just by value with countByValue
Multimap&lt;String, String&gt; multimap = ArrayListMultimap.create();
inputList.stream().sorted().forEachOrdered(multimap::put);
List&lt;Entry&lt;String, Integer&gt;&gt; sortedEntries = new ArrayList<>(counts.entrySet());
Collections.sort(sortedEntries, comparingInt(e -&gt; e.getValue()));
int totalCount = sortedEntries.stream().mapToInt(Entry::getValue).sum();
System.out.format(&quot;\nTotal words:%d%n&quot;,totalCount );
sortedEntries.subList(0, Math.min(10, sortedEntries.size())).forEach(entry -&gt; 
        System.out.format(&quot;%-30s %6.2f%%\n&quot;, 
                entry.getKey(), 
                100. * ((double) entry.getValue()) / totalCount));
```

\> **Matias**

What functions can be useful for mapping to the JavaPairRDD class?

< **StarChat**
The following are some common transformations available when working with JavaPairRDD:

* mapToPair: Applies a given function to each element of the dataset and returns a new pair consisting of the transformed element and its index.
* reduceByKey: Combines pairs where the keys are equal according to some reduce function.
* join: Returns an RDD containing all pairs of elements having the same key in both source RDDs.
* coalesce: Decreases the number of partitions in the RDD to numPartitions. Useful after filtering down a large dataset using filter.
* distinct: Return only distinct elements from the RDD (useful after joining two data sets).
* sortByKey: Sorts the RDD by key, first shuffling the data if necessary.
* sample: Sample a fraction of the data without replacement.
* union: Union two RDDs together at any level in the directory tree.
* cartesian: Compute the cartesian product between two RDDs.
These methods will depend heavily on what type of processing you want done on your data. You should refer to the official documentation for more information: https://spark.apache.org/docs/latest/.

-----------------
## Código de utilidad 

### SparkConf.class

En **SparkConf.class** podemos encontrar la configuración para la aplicación de Spark. Se menciona que se utiliza un objeto SparkConf para establecer diferentes parámetros de Spark como pares clave-valor. Normalmente, se crea un objeto SparkConf con `new SparkConf()`, lo cual carga los valores de las propiedades del sistema Java que comienzan con `spark.*`. Sin embargo, los parámetros establecidos directamente en el objeto SparkConf tienen prioridad sobre las propiedades del sistema.

Todos los métodos establecedores en esta clase admiten el encadenamiento. Por ejemplo, puedes escribir `new SparkConf().setMaster("local").setAppName("Mi aplicación")`.

* **setMaster("local")**: Establece el modo de ejecución en local. Por ejemplo, "local[4]" corre localmente con 4 cores.

* **setAppName("Mi aplicación")**: Establece el nombre de la aplicación, que se muestra en la interfaz de usuario web de Spark.

### JavaSparkContext
Una versión compatible con Java de org.apache.spark.SparkContext que devuelve org.apache.spark.api.java.JavaRDD y funciona con colecciones de Java en lugar de las de Scala.
Solo un `SparkContext` debe estar activo por JVM. Debe `stop()` el `SparkContext` activo antes de crear uno nuevo.