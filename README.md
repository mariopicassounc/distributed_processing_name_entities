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


