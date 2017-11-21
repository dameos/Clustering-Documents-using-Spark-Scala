#!bin/bash
sbt package
spark-submit --master yarn --deploy-mode cluster --num-executors 4 target/scala-2.11/proyecto4_2.11-1.0.0.jar
