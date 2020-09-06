package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


/**
 * Value 类型
 * 1. 作用： 根据分区数， 重新通过网络随机洗牌所有数据。
 * 2. 需求：创建一个 4 个分区的 RDD，对其重新分区
 *
 * 1. coalesce 重新分区，可以选择是否进行 shuffle 过程。由参数 shuffle: Boolean = false/true 决定。
 * 2. repartition 实际上是调用的 coalesce，默认是进行 shuffle 的。 源码如下：
 * def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
 * coalesce(numPartitions, shuffle = true)
 * }
 */
object Demo11_Repartition {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 16, 4)

    println("原始分区:"+ rdd.partitions.size)

    val rdd2: RDD[Int] = rdd.repartition(6)

    println("repartition分区:"+ rdd2.partitions.size)

  }
}
