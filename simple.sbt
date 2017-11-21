name := "proyecto4"

version := "1.0.0"

scalaVersion := "2.11.4"

libraryDependencies ++= {
  val sparkVer = "2.1.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-mllib" % sparkVer,
    "org.apache.spark" %% "spark-mllib-local" % sparkVer,
    "org.apache.spark" %% "spark-sql" % sparkVer
  )
}
