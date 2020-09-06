package cn.shouping.spark.actions

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 1. 作用： 返回该 RDD 排序后的前 n 个元素组成的数组
 * 2. 需求：创建一个 RDD，统计该 RDD 的条数
 */
object Demo06_TakeOrdered {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(Array(2,5,4,6,8,3))

    val arr: Array[Int] = rdd.takeOrdered(3)

    println(arr.toList)

  }

}
