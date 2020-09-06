package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 * 1. 作用： 分组，按照传入函数的返回值进行分组。 将相同的 key 对应的值放入一个迭代器。
 * 2. 需求：创建一个 RDD，按照元素模以 2 的值进行分组。
 */
object Demo06_GroupBy {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 4)

    rdd.groupBy(_%2)
      .collect()
      .foreach(println)

  }
}
