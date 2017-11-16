import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD


object Hi {
    def main(args: Array[String]) = {
        val conf = new SparkConf ().setAppName("word count")
        val sc = new SparkContext(conf)
        val files = sc.textFile("hdfs:///datasets/gutenberg")
                      .flatMap(_.split(" ").filter(_ == "error")).count
    }
}
