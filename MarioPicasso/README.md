# Informe

### ¿Como se instala spark en una computadora personal?

Utilice este recurso para instalar spark.
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
Como no se usar vim voy abro otra terminal y abro ~/.bashrc
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

Luego para importarlo en Java y usar la JAVA API de Spark solo vamos a necesitar todos los .jar que nos vienen (estarian en opt/spark/spark-<version>/jars)
Enterarme de esto me costo googlear bastante:
https://www.tutorialkart.com/apache-spark/create-java-project-with-apache-spark/
En ese post lo hace con Eclipse, pero con VS Code es lo mismo.
Abierto nuestro proyecto en VS Code hay abajo a la izquierda una pestañita que dice "JAVA PROJECTS".
La abrimos y nos dirgimos a "Referenced Libraries" y clikeamos "+"
  
### ¿Qué estructura tiene un programa en Spark?


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

No queda claro como voy a aprovechar los 2 cores de mi computadora.

Leyendo:

https://spark.apache.org/docs/1.0.2/configuration.html

https://spark.apache.org/docs/1.0.2/programming-guide.html



Necesitaria ver que hace .read() y .tesxtFile(file).
En mi caso voy a necesitar cambiarlo por el string de mi interes.

### ¿Cómo adaptar el código del Laboratorio 2 a la estructura del programa objetivo en Spark?

Yo tengo una lista de articulos, por cada feed. Estaria bueno concatenar todas estas listas, para tener un array con todos los articulos. 
A este array aplicarle la funcion parallelalize para comenzar a trabajar con spark.
Esos articulos mapearlos y converitrlos en una lista de strings donde me guardo solo el titulo y el texto.
Partir todo esos strings en una lista mas larga que tiene todas las palabras separadas.





### ¿Cómo se integra una estructura orientada a objetos con la estructura funcional de map-reduce?



