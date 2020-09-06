package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * RDD可以通过Cache和persist将前面的RDD的计算结果缓存
 * Cache底层就是调用的persist 默认存储级别是在内存存储一份
 */
object RDD_Cache {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.makeRDD(Array("sp"))

    //将rdd转换为携带当前时间戳不做缓存
    val timeRDD: RDD[String] = rdd.map(_.toString+System.currentTimeMillis)

    timeRDD.collect.foreach(println)//sp1598927440289
    timeRDD.collect.foreach(println)//sp1598927440380
    timeRDD.collect.foreach(println)//sp1598927440409

    println("-"*50)

    //将rdd转换为携带当前时间戳并做缓存
    val timeRDD2: RDD[String] = rdd.map(_.toString+System.currentTimeMillis).cache()

    timeRDD2.collect.foreach(println)//sp1598927440450
    timeRDD2.collect.foreach(println)//sp1598927440450
    timeRDD2.collect.foreach(println)//sp1598927440450
  }
}
