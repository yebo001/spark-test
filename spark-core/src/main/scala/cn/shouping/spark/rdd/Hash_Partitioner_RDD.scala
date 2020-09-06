package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * HashPartitioner 分区的原理：对于给定的 key，计算其 hashCode，并除以分区的个数取余，
 * 如果余数小于 0，则用余数+分区的个数（否则加 0），最后返回的值就是这个 key 所属的分区 ID。
 */
object Hash_Partitioner_RDD {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(Int, Int)] = sc.parallelize(List((1,3),(1,2),(2,4),(2,3),(3,6),(3,8)),8)

    val rdd2: RDD[String] = rdd.mapPartitionsWithIndex((index, iter) => {
      Iterator {
        (index.toString + ":") + iter.mkString("|")
      }
    })

    rdd2.collect.foreach(println)

    println("-"*50)

    val rdd3: RDD[(Int, Int)] = rdd.partitionBy(new HashPartitioner(7))

    println(rdd3.count)

    rdd.mapPartitions(iter => Iterator(iter.length)).collect.foreach(println)

  }
}
