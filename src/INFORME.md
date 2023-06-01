## Interacciones con StarChat

### Librerias de Spark
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
