package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Value 类型
 * 1. 作用： 将每一个分区形成一个数组，形成新的 RDD 类型时 RDD[Array[T]]
 * 2. 需求：创建一个 4 个分区的 RDD，并将每个分区的数据放到一个数组
 *
 */
object Demo05_Glom {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 16,4)

    rdd.glom()
      .collect()
      .foreach(x => println(x.mkString(" ")))

  }
}
