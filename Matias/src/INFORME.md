### SparkConf.class

En **SparkConf.class** podemos encontrar la configuración para la aplicación de Spark. Se menciona que se utiliza un objeto SparkConf para establecer diferentes parámetros de Spark como pares clave-valor. Normalmente, se crea un objeto SparkConf con `new SparkConf()`, lo cual carga los valores de las propiedades del sistema Java que comienzan con `spark.*`. Sin embargo, los parámetros establecidos directamente en el objeto SparkConf tienen prioridad sobre las propiedades del sistema.

Todos los métodos establecedores en esta clase admiten el encadenamiento. Por ejemplo, puedes escribir `new SparkConf().setMaster("local").setAppName("Mi aplicación")`.

* **setMaster("local")**: Establece el modo de ejecución en local. Por ejemplo, "local[4]" corre localmente con 4 cores.

* **setAppName("Mi aplicación")**: Establece el nombre de la aplicación, que se muestra en la interfaz de usuario web de Spark.

### JavaSparkContext
Una versión compatible con Java de org.apache.spark.SparkContext que devuelve org.apache.spark.api.java.JavaRDD y funciona con colecciones de Java en lugar de las de Scala.
Solo un `SparkContext` debe estar activo por JVM. Debe `stop()` el `SparkContext` activo antes de crear uno nuevo.


## Interacciones con StarChat

#### Librerias de Spark
\> **Matias**
I have a code in Java and I need to translate a part of the project to spark. What is the first thing I need to add to my code?

\< **StarChat**
To include Apache Spark libraries in VSCode, follow these steps: Download the library JAR files from the official website into a folder accessible by VS Code. The latest versions of Spark can usually be found under “Downloads” > “Spark”. 
Inside your pom.xml or build.sbt, specify which dependencies contain Spark classes so they get included when building your project. For instance, if you’re working with Spark SQL, you might want to add the following dependency block inside <dependencies>:

<dependency>
<groupId>org.apache.spark</groupId>
<artifactId>spark-sql_${scala.version}</artifactId>
<version>${spark.version}</version>
</dependency>

Add the downloaded JAR files to your classpath either via the -cp option in the terminal or programmatically during runtime. This ensures that all necessary classes are loaded while running your app locally.

\> **Matias**
In Vscode there is an option that allows you to add library references to the project. Adding the .jar files in this way is it correct?

\< **StarChat**
Yes, adding references to library JAR files in a Visual Studio Code project is generally considered safe because the JAR files will typically reside within the same workspace (project) as the code being developed. However, keep in mind that including third party JAR files may increase the risk of compatibility issues between different components used throughout the codebase. It’s always recommended to use managed packages whenever possible instead of manually managing individual JAR files.

#### SparkConf
\> **Matias**
¿Como crear una instancia de spark?

\< **StarChat**
Para poder crear una instancia de Spark en Java necesitamos importar las librerias correspondientes:
    import org.apache.spark.SparkConf;
    import org.apache.spark.api.java.*;</p>

Creando la configuración
// Creamos el objeto que contiene la configuracion para nuestro cluster SparkConf conf = new SparkConf().setAppName("MiAplicacion").setMaster("local[*]"); // [1] // Obtenemos una referencia al contexto de spark del cluster JavaSparkContext sc = new JavaSparkContext(conf);

Cerrando el Contexto
// Una vez terminado con nuestra aplicación cerramos el contexto y liberamos los recursos sc.close();

Notas:
[1]: En este ejemplo estamos utilizando solo un master local, sin embargo si lo deseamos podemos agregar mas workers o incluso modificar algun parametro dentro de la clase SparkConf(), por ejemplo cambiar el directorio temporal donde se almacenaran ciertos datos temporales etc…