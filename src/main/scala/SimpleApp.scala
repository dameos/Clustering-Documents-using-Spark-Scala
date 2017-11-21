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
        // Se Define el nombre de la aplicación
        val conf = new SparkConf().setAppName("Clustering Spark") 
        // Se carga la variable de contexto de spark
        val sc = new SparkContext(conf) 
        // Se crea la sesión para spark SQL
        val spark = SparkSession.builder().getOrCreate()

        //Se crea el struct para la lectura de los archivos (path, texto)
        val struct = StructType(
                        StructField("path", StringType, true)::
                        StructField("text", StringType, true):: Nil)
        
        // Se leen los archivos y se convierten a Row(path, text) para la creación del dataframe
    	val files = sc.wholeTextFiles("hdfs:///user/dmedina7/datasets/gutenberg/*.txt").map(data => Row(data._1, data._2))
        val dataFrame = spark.createDataFrame(files, struct)
        dataFrame.show()

        // Se tokenizan los texto
        val tokenizer = new Tokenizer()
            .setInputCol("text")
            .setOutputCol("words")
        
        // Se quitan las stopWords para hacer más preciso el Peso TF-IDF
        val remover = new StopWordsRemover()
            .setInputCol(tokenizer.getOutputCol)
            .setOutputCol("cleanedWords")

        // Se hacen las estimación TF-IDF que describe el articulo del enunciado de la práctica   
        val hashingTF = new HashingTF()
            .setNumFeatures(1000)
            .setInputCol(remover.getOutputCol)
            .setOutputCol("features")
        val idf = new IDF()
            .setInputCol(hashingTF.getOutputCol)
            .setOutputCol("newFeatures")
            .setMinDocFreq(3)
        
        // Se hace el KMeans con un k de 4 
        val kmeans = new KMeans()
            .setK(4)
        
        // Se usan Pipelines para definar las etapas de limpieza, peso y clustering de documentos
        val pipeline = new Pipeline()
            .setStages(Array(tokenizer, remover, hashingTF, idf, kmeans))
        
        // Se encaja el modelo para seguir la pipeline
        val model = pipeline.fit(dataFrame)

        // Se transforma el modelo para seguir el pipeline
        val results = model.transform(dataFrame)

        // Los resultados son mostrados en el stdout
        results.show()
   }
}
