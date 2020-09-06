package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 双Value交互型
 * 1. 作用： 笛卡尔积（尽量避免使用）
 * 2. 需求：创建两个 RDD，计算两个 RDD 的笛卡尔积
 */
object Demo17_Cartesian {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1: RDD[Int] = sc.parallelize(1 to 3)
    val rdd2: RDD[Int] = sc.parallelize(2 to 5)

    rdd1.cartesian(rdd2)
      .collect()
      .foreach(println)

  }
}
