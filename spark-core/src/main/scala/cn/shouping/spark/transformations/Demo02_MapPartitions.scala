package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Value 类型
 * 1. 作用： 类似于 map，但独立地在 RDD 的每一个分片上运行，因此在类型为 T 的 RDD 上运行时， func 的函数类型必须是 Iterator[T] => Iterator[U]。
 *    假设有 N 个元素， 有 M 个分区，那么map的函数的将被调用N次,而mapPartitions被调用M次,一个函数一次处理所有分区。
 * 2. 需求： 创建一个 RDD，使每个元素*2 组成新的 RDD
 *
 * 1. map()： 每次处理一条数据。
 * 2. mapPartition()： 每次处理一个分区的数据，这个分区的数据处理完后，原 RDD 中分区的数据才能释放，可能导致 OOM。
 * 3. 开发指导：当内存空间较大的时候建议使用 mapPartition()，以提高处理效率。
 */
object Demo02_MapPartitions {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 10)
    rdd.mapPartitions(x => x.map(_*2))
      .collect()
      .foreach(println)

  }
}
