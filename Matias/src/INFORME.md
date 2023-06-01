## Interacciones con StarChat

#### Preguntas sobre como incluir las librerias de Spark:
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

#### Preguntas sobre la clase SparkConf:
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

#### Preguntas sobre JavaRDD y parallelize:
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

#### Pregunta sobre JavaPairRDD y map():

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
## Código importante para la implementación:

### SparkConf.class

En **SparkConf.class** podemos encontrar la configuración para la aplicación de Spark. Se menciona que se utiliza un objeto SparkConf para establecer diferentes parámetros de Spark como pares clave-valor. Normalmente, se crea un objeto SparkConf con `new SparkConf()`, lo cual carga los valores de las propiedades del sistema Java que comienzan con `spark.*`. Sin embargo, los parámetros establecidos directamente en el objeto SparkConf tienen prioridad sobre las propiedades del sistema.

Todos los métodos establecedores en esta clase admiten el encadenamiento. Por ejemplo, puedes escribir `new SparkConf().setMaster("local").setAppName("Mi aplicación")`.

* **setMaster("local")**: Establece el modo de ejecución en local. Por ejemplo, "local[4]" corre localmente con 4 cores.

* **setAppName("Mi aplicación")**: Establece el nombre de la aplicación, que se muestra en la interfaz de usuario web de Spark.

### JavaSparkContext
Una versión compatible con Java de org.apache.spark.SparkContext que devuelve org.apache.spark.api.java.JavaRDD y funciona con colecciones de Java en lugar de las de Scala.
Solo un `SparkContext` debe estar activo por JVM. Debe `stop()` el `SparkContext` activo antes de crear uno nuevo.