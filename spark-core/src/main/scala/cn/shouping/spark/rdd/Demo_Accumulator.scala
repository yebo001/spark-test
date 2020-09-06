package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器
 */
object Demo_Accumulator {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.parallelize(List("i am big big girl i am big big world"))

    val lineRDD: RDD[String] = rdd.flatMap(_.split(" "))

    //统计每个单词出现的次数
    lineRDD.map((_,1))
      .reduceByKey(_+_)
      .foreach(println)

    //额外统计big的出现次数
    println()

    sc.stop()

  }
}
