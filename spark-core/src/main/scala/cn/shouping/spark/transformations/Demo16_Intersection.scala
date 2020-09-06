package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 双Value交互型
 * 1. 作用： 对源 RDD 和参数 RDD 求交集后返回一个新的 RDD
 * 2. 需求：创建两个 RDD，求两个 RDD 的交集
 */
object Demo16_Intersection {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1: RDD[Int] = sc.parallelize(3 to 8)
    val rdd2: RDD[Int] = sc.parallelize(1 to 5)

    val rdd: RDD[Int] = rdd1.intersection(rdd2)

    rdd.collect()
      .foreach(println)

  }
}
