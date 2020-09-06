package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 *    参数： (zeroValue: V)(func: (V, V) => V): RDD[(K, V)]
 * 1. 作用： aggregateByKey 的简化操作， seqop 和 combop 相同
 * 2. 需求：创建一个 pairRDD，计算相同 key 对应值的相加结果
 */
object Demo23_FlodByKey {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(Int, Int)] = sc.parallelize(List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8)),3)

    rdd.foldByKey(0)((_+_))
      .collect()
      .foreach(println)

  }
}
