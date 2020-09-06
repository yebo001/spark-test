package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 * map(func)案例
 * 作用： 返回一个新的 RDD，该 RDD 由每一个输入元素经过 func 函数转换后组成
 * 需求： 创建一个 1-10 数组的 RDD，将所有元素*2 形成新的 RDD
 *
 * 1. map()： 每次处理一条数据。
 * 2. mapPartition()： 每次处理一个分区的数据，这个分区的数据处理完后，原 RDD 中分区的数据才能释放，可能导致 OOM。
 * 3. 开发指导：当内存空间较大的时候建议使用 mapPartition()，以提高处理效率。
 */
object Demo01_Map {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 10)
    rdd.map((_,1))
      .collect()
      .foreach(println)

  }
}
