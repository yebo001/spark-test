package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Value 类型
 * 1. 作用：类似于 mapPartitions，但 func 带有一个整数参数表示分片的索引值，因此在类型为 T 的 RDD 上运行时， func 的函数类型必须是(Int, Interator[T]) => Iterator[U]；
 * 2. 需求： 创建一个 RDD，使每个元素跟所在分区形成一个元组组成一个新的 RDD
 */
object Demo03_MapPartitionsWithIndex {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 10)

    rdd.mapPartitionsWithIndex((index,items)=> (items.map((index,_))))
      .collect()
      .foreach(print)

  }
}
