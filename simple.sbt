name := "proyecto4"

version := "1.0.0"

scalaVersion := "2.11.4"

libraryDependencies ++= {
  val sparkVer = "2.1.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer % "provided" withSources(),
    "org.apache.spark" %% "spark-mllib" % "2.0.1",
    "org.apache.spark" %% "spark-mllib-local" % "2.0.1"
  )
}
