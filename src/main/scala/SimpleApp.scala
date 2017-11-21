import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer, StopWordsRemover}
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.Pipeline

object Proyecto4 {
	
    def main(args: Array[String]){
        case class Data(path: String, text:String)
        val conf = new SparkConf().setAppName("word count")
        val sc = new SparkContext(conf)
        val spark = SparkSession.builder().getOrCreate()
        val struct = StructType(
                        StructField("path", StringType, true)::
                        StructField("text", StringType, true):: Nil)

    	val files = sc.wholeTextFiles("hdfs:///user/dmedina7/datasets/gutenberg/*.txt").map(data => Row(data._1, data._2))
        val df = spark.createDataFrame(files, struct)
        df.show()

        val tokenizer = new Tokenizer()
            .setInputCol("text")
            .setOutputCol("words")
        val remover = new StopWordsRemover()
            .setInputCol(tokenizer.getOutputCol)
            .setOutputCol("cleanedWords")
        val hashingTF = new HashingTF()
            .setNumFeatures(1000)
            .setInputCol(remover.getOutputCol)
            .setOutputCol("features")
        val idf = new IDF()
            .setInputCol(hashingTF.getOutputCol)
            .setOutputCol("newFeatures")
            .setMinDocFreq(3)
        val kmeans = new KMeans()
            .setK(4)
        val pipeline = new Pipeline()
            .setStages(Array(tokenizer, remover, hashingTF, idf, kmeans))
        
        val model = pipeline.fit(df)
        val results = model.transform(df)
        results.write.json("hdfs:///user/dmedina7/resultados")
        results.show()
   }
}
