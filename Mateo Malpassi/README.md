# Informe Laboratorio 3

## Autor:

- Mateo Malpassi

### Preguntas a responder:

Todas las preguntas guía de este informe fueron hechas en ChatGPT.

> Pregunta 1: ¿Cómo se instala Spark en una computadora personal?

![Parte 1](/Mateo Malpassi/Imágenes/Pregunta1/InstSpark1.png)

![Parte 2](/Mateo Malpassi/Imágenes/Pregunta1/InstSpark2.png)

![Parte 3](/Mateo Malpassi/Imágenes/Pregunta1/InstSpark3.png)

![Parte 4](/Mateo Malpassi/Imágenes/Pregunta1/InstSpark4.png)

![Parte 5](/Mateo Malpassi/Imágenes/Pregunta1/InstSpark5.png)

Luego, como este laboratorio se realiza en Java, lo que quedaría sería añadir la carpeta "jars" de spark a nuestro proyecto para que podamos utilizar Spark.

> Pregunta 2: ¿Qué estructura tiene un programa en Spark?

![Parte 1](/Mateo Malpassi/Imágenes/Pregunta2/Estructura1.png)

![Parte 2](/Mateo Malpassi/Imágenes/Pregunta2/Estructura2.png)

![Parte 3](/Mateo Malpassi/Imágenes/Pregunta2/Estructura3.png)

![Parte 4](/Mateo Malpassi/Imágenes/Pregunta2/Estructura4.png)

![Parte 5](/Mateo Malpassi/Imágenes/Pregunta2/Estructura5.png)

![Parte 6](/Mateo Malpassi/Imágenes/Pregunta2/Estructura6.png)

> Pregunta 3: ¿Qué estructura tiene un programa de conteo de palabras en diferentes documentos en Spark?

![Parte 1](/Mateo Malpassi/Imágenes/Pregunta3/Paso1-2.png)

![Parte 2](/Mateo Malpassi/Imágenes/Pregunta3/Paso3-4.png)

![Parte 3](/Mateo Malpassi/Imágenes/Pregunta3/Paso5-6.png)

> Pregunta 4: ¿Cómo adaptar el código del Laboratorio 2 a la estructura del programa objetivo en Spark?

Para esta pregunta, se copio el código que la IA devolvió. Su respuesta fué:

```
public class WordCountSpark {
    public static void main(String[] args) {
        // Crear el contexto de Spark
        JavaSparkContext sparkContext = new JavaSparkContext("local", "WordCountSpark");

        // Cargar los documentos de texto utilizando el lector de feeds
        List<String> documents = FeedReader.loadDocuments();

        // Crear un RDD a partir de la lista de documentos
        JavaRDD<String> documentsRDD = sparkContext.parallelize(documents);

        // Realizar el conteo de palabras en cada documento
        JavaRDD<String> wordsRDD = documentsRDD.flatMap((FlatMapFunction<String, String>) line ->
                Arrays.asList(line.split(" ")).iterator());

        // Realizar el conteo de palabras en todos los documentos
        JavaRDD<String> wordsCountRDD = wordsRDD.mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);

        // Recopilar los resultados
        List<Tuple2<String, Integer>> wordCountList = wordsCountRDD.collect();

        // Mostrar los resultados
        for (Tuple2<String, Integer> wordCount : wordCountList) {
            System.out.println(wordCount._1() + ": " + wordCount._2());
        }

        // Computar entidades nombradas utilizando la funcionalidad existente
        List<String> namedEntities = NamedEntityRecognizer.computeNamedEntities(documents);

        // Mostrar las entidades nombradas encontradas
        for (String entity : namedEntities) {
            System.out.println("Named Entity: " + entity);
        }

        // Detener el contexto de Spark
        sparkContext.stop();
    }
}
```

> Pregunta 5: ¿Cómo se integra una estructura orientada a objetos con la estructura funcional de map-reduce?

![Parte 1](/Mateo Malpassi/Imágenes/Pregunta5/mapReduce.png)

El ejemplo que menciona la imágen es:

```
public class WordCountAndNamedEntitiesSpark {
    public static void main(String[] args) {
        // Crear el contexto de Spark
        JavaSparkContext sparkContext = new JavaSparkContext("local", "WordCountAndNamedEntitiesSpark");

        // Cargar los documentos de texto utilizando el lector de feeds
        List<String> documents = FeedReader.loadDocuments();

        // Crear un RDD a partir de la lista de documentos
        JavaRDD<String> documentsRDD = sparkContext.parallelize(documents);

        // Realizar el conteo de palabras en cada documento
        JavaRDD<String> wordsRDD = documentsRDD.flatMap((FlatMapFunction<String, String>) line ->
                Arrays.asList(line.split(" ")).iterator());

        // Realizar el conteo de palabras en todos los documentos
        JavaRDD<String> wordsCountRDD = wordsRDD.mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);

        // Recopilar los resultados del conteo de palabras
        List<Tuple2<String, Integer>> wordCountList = wordsCountRDD.collect();

        // Mostrar los resultados del conteo de palabras
        for (Tuple2<String, Integer> wordCount : wordCountList) {
            System.out.println(wordCount._1() + ": " + wordCount._2());
        }

        // Computar entidades nombradas utilizando la funcionalidad existente
        JavaRDD<String> namedEntitiesRDD = documentsRDD.flatMap((FlatMapFunction<String, String>) line ->
                NamedEntityRecognizer.computeNamedEntities(line).iterator());

        // Realizar el conteo de entidades nombradas
        JavaRDD<String> namedEntitiesCountRDD = namedEntitiesRDD.mapToPair(entity -> new Tuple2<>(entity, 1))
                .reduceByKey(Integer::sum);

        // Recopilar los resultados del conteo de entidades nombradas
        List<Tuple2<String, Integer>> namedEntitiesCountList = namedEntitiesCountRDD.collect();

        // Mostrar los resultados del conteo de entidades nombradas
        for (Tuple2<String, Integer> namedEntityCount : namedEntitiesCountList) {
            System.out.println("Named Entity: " + namedEntityCount._1() + ", Count: " + namedEntityCount._2());
        }

        // Detener el contexto de Spark
        sparkContext.stop();
    }
}
```

### Aclaraciones finales sobre implementación

Se añade una clase denominada "CounterNE" que se encargará de crear un Map de pares <Categoría,Frecuencia>, y luego los dos métodos principales que tiene esta clase son las que se encargan de filtrar las entidades nombradas de los artículos de cada feed, añadirlas a una lista final, y por último cada vez que se crea una de estas, se aumenta la frecuencia de dicha entidad nombrada según su categoría y se actualiza el valor de frecuencia de cada en el Map.

Esta clase se utiliza en FeedReaderMain, junto al código (ligeramente modificado) de Spark que nos brindó ChatGPT, para que la tarea principal de este laboratorio pueda realizarse.