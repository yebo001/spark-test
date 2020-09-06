package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 双Value交互型
 * 1. 作用： 计算差的一种函数，去除两个 RDD 中相同的元素，不同的 RDD 将保留下来
 * 2. 需求：创建两个 RDD，求第一个 RDD 与第二个 RDD 的差集
 */
object Demo15_Subtract {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1: RDD[Int] = sc  .parallelize(3 to 8)
    val rdd2: RDD[Int] = sc.parallelize(1 to 5)

    rdd1.subtract(rdd2)
      .collect
      .foreach(println)

  }
}
