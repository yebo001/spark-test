package cn.shouping.spark.actions

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 1. 作用： 针对(K,V)类型的 RDD，返回一个(K,Int)的 map，表示每一个 key 对应的元素个数。
 * 2. 需求：创建一个 PairRDD，统计每种 key 的个数
 */
object Demo10_CountByKey {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(Int, Int)] = sc.parallelize(List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8)),3)

    val intToLong: collection.Map[Int, Long] = rdd.countByKey()

    for (e <- intToLong) {
      println("key:" + e._1 + ",value:" + e._2)
    }

  }

}
