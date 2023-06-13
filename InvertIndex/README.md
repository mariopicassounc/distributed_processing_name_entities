# Informe

### Como ejecutar este programa
Utiliza las librerias:

gson 2.10 https://search.maven.org/remotecontent?filepath=com/google/code/gson/gson/2.10.1/gson-2.10.1.jar

org.json https://search.maven.org/remotecontent?filepath=org/json/json/20230227/json-20230227.jar

Y todos los jars que vienen en la carpeta Jars de la instalacion de Spark. Instructivo abajo.

### ¿Como se instala spark en una computadora personal?

Utilicé un video de youtube para instalar spark.
https://www.youtube.com/watch?v=YanzUI-30pI

1) Debemos tener instalado Java: sudo apt-get install default-jdk

En el caso de Ubuntu 22 se instala el Openjdk 17 que es la última versión LTS.

2) Nos dirigimos a https://spark.apache.org/downloads.html, seleccionamos la version que queremos descargar y clickeamos en el hipervínculo luego de “Download spark: ”

3) Antes de descargar creamos la carpeta destino donde descargaremos los archivos.

~~~
sudo su - 
mkdir -p /opt/scala
cd /opt/scala
wget <link que nos da spark>
tar -xvf <archivo>
~~~

4) Seteo las variables de entorno: 
Para no usar vim abro otra terminal y abro ~/.bashrc
~~~
gedit ~/.bashrc
~~~

Agrego al final del archivo esto:
SPARK_HOME=opt/spark/<nombre de la carpeta que descomprimimos>
export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin

Guardamos y:
~~~
source .bashrc
~~~

Luego spark ya estaria listo para usar.  Probamos ejecutando el software con::
~~~
spark-shell
~~~

Luego para importarlo en Java y usar la JAVA API de Spark solo vamos a necesitar todos los .jar que nos vienen (estarían en opt/spark/spark-<version>/jars)

Fuente
https://www.tutorialkart.com/apache-spark/create-java-project-with-apache-spark/
En ese post lo hace con Eclipse, pero con VS Code es lo mismo.

Abierto nuestro proyecto en VS Code hay abajo a la izquierda una pestañita que dice "JAVA PROJECTS".

La abrimos y nos dirgimos a "Referenced Libraries" y clikeamos "+".

Seleccionamos todos los jars previamente mencionados y los agregamos.

Es probable que para usar las clases de RDD no sea necesario importar todas las librerias, pero para no perder tiempo lo dejamos asi.
  
### ¿Qué estructura tiene un programa en Spark?
Lo primero que debe hacer un programa Spark es crear un objeto SparkContext, que le dice a Spark cómo acceder a un clúster. Para crear un SparkContext primero necesitas construir un objeto SparkConf que contiene información sobre tu aplicación.

Luego haremos uso de RDD (Resilient Distributed Datasets), que es una colección de elementos <em>tolerantes a fallas</em> que se pueden operar en paralelo. 
Hay dos formas de crear una RDD: paralelizando una colección existente en el programa, o haciendo referencia a un conjunto de datos en un sistema de almacenamiento externo, como un sistema de archivos compartido, HDFS, HBase o cualquier fuente de datos que ofrezca un formato de entrada Hadoop.

Tolerancia al fallo es un tema principal en el área de sistemas distribuidos.
No es un tema a profundizar aquí, ya que Spark resuelve este problema por nosotros, pudiendo trabajar con bases de datos distribuidas y haciendo un procesamiento distribuido de estos datos sin pensar en las complicaciones inherentes.

Los RDD admiten dos tipos de operaciones: transformaciones, que crean un nuevo conjunto de datos a partir de uno existente, y acciones, que devuelven un valor después de ejecutar un cálculo en el conjunto de datos. 
Por ejemplo, map es una transformación que pasa cada elemento del conjunto de datos a través de una función y devuelve un nuevo RDD que representa los resultados. Por otro lado, reduce es una acción que agrega todos los elementos del RDD usando alguna función y devuelve el resultado final.

Generalmente primero hacemos transformaciones (map) para darle la forma que queremos a nuestros datos, y despues realizamos las acciones para obtener un resultado a partir del procesamiento paralelo del conjunto de datos.
Algo piola es que spark hace las transformaciones "lazy", es decir, no se hacen hasta que haya una accion. Cuando ejecutamos la accion recien ahi se realiza la trasformacion previamente especificada. 


### ¿Qué estructura tiene un programa de conteo de palabras en diferentes documentos en Spark?
```java
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.examples;

import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class JavaWordCount {
  private static final Pattern SPACE = Pattern.compile(" ");

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.err.println("Usage: JavaWordCount <file>");
      System.exit(1);
    }

    SparkSession spark = SparkSession
      .builder()
      .appName("JavaWordCount")
      .getOrCreate();

    JavaRDD<String> lines = spark.read().textFile(args[0]).javaRDD();

    JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

    JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

    JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

    List<Tuple2<String, Integer>> output = counts.collect();
    for (Tuple2<?,?> tuple : output) {
      System.out.println(tuple._1() + ": " + tuple._2());
    }
    spark.stop();
  }
}
```
Este código viene en la carpeta examples de Spark. 
Para correrlo el quickstart de la documentacion (https://spark.apache.org/docs/latest/quick-start.html) dice que hay que hacer:
~~~
run-example JavaWordCount <file>
~~~

### ¿Cómo adaptar el código del Laboratorio 2 a la estructura del programa objetivo en Spark?

Leyendo:

https://spark.apache.org/docs/1.0.2/configuration.html

https://spark.apache.org/docs/1.0.2/programming-guide.html

Los cambios que debo hacerle al ejemplo para integrarlo en mi Main son leves, basicamente en vez de adquirir el texto de un archivo, tengo que paralelizar una coleccion existente en mi programa.

En el codigo del laboratorio 2 cada articulo tenia su lista de entidades nombradas, y la clase Article tenia un metodo justamente para rellenar esta lista.
En este lab no haré uso ni de ese metodo ni de esa lista.

1) Yo tengo una lista de articulos, por cada feed. Estaria bueno concatenar todas estas listas, para tener un array con todos los articulos. 

2) Esos articulos mapearlos y converitrlos en una lista de strings donde me guardo solo el titulo y el texto.

3) Ahora a la lista de strings, aplicarle la funcion parallelalize para comenzar a trabajar con Spark.

4) Partir todo esos strings en una lista mas larga que tiene todas las palabras separadas.

5) Mapear dicha lista para convertir la lista de palabras en una lista de tuplas (palabra, frecuencia) donde la frecuencia seria 1

6) Reducir a partir de la clave la lista, para que quede cada palabra una sola vez con su respectiva frecuencia (reduceByKey).

Los pasos donde se involucra spark son 3,4,5,6.

### ¿Cómo se integra una estructura orientada a objetos con la estructura funcional de map-reduce?
En Java se puede programar secciones de código funcionales en cierto punto, siempre se puede crear una clase sin atributos y con métodos que se comporten como funciones deterministicas.
En mi caso como las operaciones de spark eran pocas las deje en el Main, pero se podria hacer una clase sin atributo con un metodo "funcional" que le des la lista de articulos y te devuelva la lista tuplas (palabra, frecuencia).

Tambien se podría hacer uso de interfaces: no tienen estado y a partir de Java 8 pueden tener la implementacion de sus metodos. Pero para los objetivos de este lab simplemente aumentaría la verbosidad y la dificultad para entender el código.

### Posible refactor que haría al codigo entregado
La clase FactoryNamedEntities podria ser dividida en 2 clases: 
    ListNamedEntities la cual se encarga solamente de procesar las tuplas y crear la lista, y tambien de mantener un conteo de aparicion de NE por clases y subclases dentro de esa lista.
    FactoryNameEntity la cual simplemente se encarga de ejecutar los constructores correspondientes para la NE.

Este diseño es el ideal ya que separa bien los objetivos de cada clase, pero para los objetivos de este lab me convenia juntarlas, ya que hacer ambas me traia la consecuencia de realizar dos veces pattern matching.

El pattern matching de FactoryNameEntity hubiese sido para ejecutar el constructor correcto segun tipo de entidad y tema.
Y el pattern matching de ListNameEntities hubiese sido para actualizar los contadores de aparicion por clase y subclase de entidad nombrada

### Invert Index
Para poder procesar los Articles con Spark RDD debo implementar la interfaz Serializable en la clase Article.

Fuente: https://www.tutorialkart.com/apache-spark/spark-rdd-with-custom-class-objects/

Para hacer este inverted index tengo que:
	
	1) Crear un hash map donde la key es la palabra y el value es una lista que contiene tuplas(indice del articulo en el array, frecuencia)
	2) Esta lista habra que ordenarla de mayor a menor segun frecuencia
	3) Cuando hago una busqueda por una palabra (key) simplemente recorro la lista (value) e imprimo los articulos.
	OPCIONAL) pedir al usuario la palabra por stdin "Que palabra desea buscar?"
Fuente: https://es.wikipedia.org/wiki/%C3%8Dndice_invertido
	













