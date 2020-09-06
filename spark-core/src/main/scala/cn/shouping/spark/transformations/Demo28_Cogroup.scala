package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 1. 作用：在类型为(K,V)和(K,W)的 RDD 上调用，返回一个(K,(Iterable<V>,Iterable<W>))类型的 RDD
 * 2. 需求：创建两个 pairRDD，并将 key 相同的数据聚合到一个迭代器。
 */
object Demo28_Cogroup {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1: RDD[(Int, String)] = sc.parallelize(Array((1,"a"),(2,"b"),(3,"c")))
    val rdd2: RDD[(Int, Int)] = sc.parallelize(Array((1,4),(2,5),(3,6)))

    rdd1.cogroup(rdd2)
      .collect()
      .foreach(println)

  }
}
