import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}

object Hi {
	
    def main(args: Array[String]){
        val conf = new SparkConf().setAppName("word count")
        val sc = new SparkContext(conf)
    	val files = sc.textFile("hdfs:///user/dmedina7/datasets/gutenberg/*.txt").map(_.split(" ").toSeq)
	    val hashingTF = new HashingTF()
	    val tf: RDD[Vector] = hashingTF.transform(files)
	    tf.cache()
    	val idf = new IDF().fit(tf)
    	val tfidf: RDD[Vector] = idf.transform(tf)
        val model = KMeans.train(tfidf, 4, 20)

        val cost = model.computeCost(tfidf)
        println("Cost = " + cost)
        
        println("Cluster Centers: ")
        val centers = model.clusterCenters
        centers.foreach(println)
   }
}
