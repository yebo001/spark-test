package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 * 1. 作用： 缩减分区数，用于大数据集过滤后，提高小数据集的执行效率。
 * 2. 需求：创建一个 4 个分区的 RDD，对其缩减分区
 *
 * 1. coalesce 重新分区，可以选择是否进行 shuffle 过程。由参数 shuffle: Boolean = false/true 决定。
 * 2. repartition 实际上是调用的 coalesce，默认是进行 shuffle 的。 源码如下：
 * def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
 * coalesce(numPartitions, shuffle = true)
 * }
 */
object Demo10_Coalesce {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 10,4)

    println("原始分区:"+ rdd.partitions.size)

    val rdd2: RDD[Int] = rdd.coalesce(3)

    print("缩减分区:"+ rdd2.partitions.size)

  }
}
