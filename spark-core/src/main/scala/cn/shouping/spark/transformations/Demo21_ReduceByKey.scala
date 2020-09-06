package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Key-Value 类型
 * 1. 在一个(K,V)的 RDD 上调用，返回一个(K,V)的 RDD，使用指定的 reduce 函数，将相同
 * key 的值聚合到一起， reduce 任务的个数可以通过第二个可选的参数来设置。
 * 2. 需求：创建一个 pairRDD，计算相同 key 对应值的相加结果
 *
 * 1. reduceByKey：按照 key 进行聚合，在 shuffle 之前有 combine（预聚合）操作，返回结果是 RDD[k,v].
 * 2. groupByKey：按照 key 进行分组，直接进行 shuffle。
 * 3. 开发指导： reduceByKey 比 groupByKey，建议使用。但是需要注意是否会影响业务逻辑。
 */
object  Demo21_ReduceByKey {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.parallelize(Array("one", "two", "two", "three", "three", "three"))

    rdd.map((_,1))
      .reduceByKey(_+_)
      .foreach(println)

  }
}
