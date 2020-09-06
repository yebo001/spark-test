package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 双Value交互型
 * 1. 作用： 将两个 RDD 组合成 Key/Value 形式的 RDD,这里默认两个 RDD 的 partition 数量以及元素数量都相同，否则会抛出异常。
 * 2. 需求：创建两个 RDD，并将两个 RDD 组合到一起形成一个(k,v)RDD
 */
object Demo18_Zip {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1: RDD[Int] = sc.parallelize(Array(1,2,3),3)
    val rdd2: RDD[String] = sc.parallelize(Array("a","b","c"),3)

    rdd1.zip(rdd2)
      .collect()
      .foreach(println)

  }
}
