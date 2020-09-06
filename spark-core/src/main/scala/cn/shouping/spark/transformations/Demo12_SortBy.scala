package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 * 1. 作用；使用 func 先对数据进行处理，按照处理后的数据比较结果排序，默认为正序。
 * 2. 需求：创建一个 RDD，按照不同的规则进行排序
 */
object Demo12_SortBy {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(List(3,4,1,5,6342,1,4325,62),1)

    rdd
      .map((_,1))
      .sortBy(_._1,false)
      .collect()
      .foreach(println)

  }
}
