package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * Spark 目前支持 Hash 分区和 Range 分区，用户也可以自定义分区， Hash 分区为当前的默认分区，
 * Spark 中分区器直接决定了 RDD 中分区的个数、 RDD 中每条数据经过 Shuffle过程属于哪个分区和 Reduce 的个数
 *
 * 注意：
 * (1)只有 Key-Value 类型的 RDD 才有分区器的，非 Key-Value 类型的 RDD 分区器的值是 None
 * (2)每个 RDD 的分区 ID 范围： 0~numPartitions-1，决定这个值是属于那个分区的。
 */
object Partitioner_RDD {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(Int, Int)] = sc.parallelize(List((1,1),(2,2),(3,3)))

    //查看RDD分区器
    println(rdd.partitioner)//None

    //使用HashPartitioner对RDD进行重新分区
    val rdd2: RDD[(Int, Int)] = rdd.partitionBy(new HashPartitioner(2))

    println(rdd2.partitioner)//Some(org.apache.spark.HashPartitioner@2)

  }
}
