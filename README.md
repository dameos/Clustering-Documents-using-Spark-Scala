# proyecto4

## Autores
- David Medina Ospina - dmedina7@eafit.edu.co
- Daniel Alejandro Mártinez Montealegre - dmarti25@eafit.edu.co

## Datasets 

## Procesamiento
Para procesar la aplicación se usa SBT y se creó un script de bash, este compila todo el proyecto y procede a ejecutarlo en cluster Yarn con 4 workers
```bash 
$ sh run.sh
```
Si se desea solo generar el .jar de la aplicación se puede usar el comando
```bash
$ sbt package
```
Para limpiar los archivos de la compilación se usa
```bash
$ sbt clean
```
Para revisar el resultado de la ejecución se debe de mirar el log
```bash
$ yarn logs -aplicationId <appID>
```

## Depencdencias
Para la compilación, el package y la ejecución se necitan las siguientes dependencias
- [Scala(2.11.4):](https://www.scala-lang.org/) lenguaje en el que está programado el proyecto
- [SBT(1.0.3):](http://www.scala-sbt.org/) manejador de proyectos para Scala y dependencias internas de la aplicación
- [Spark(2.1.1):](https://spark.apache.org/) necesario para la ejecución de la aplicación, además se requiere la libreria de Machine Learning de Spark