name := "proyecto4"

version := "1.0.0"

scalaVersion := "2.11.4"

val spark = "org.apache.spark" %% "spark-core" % "1.4.1" % "provided"

libraryDependencies ++= Seq(spark)
