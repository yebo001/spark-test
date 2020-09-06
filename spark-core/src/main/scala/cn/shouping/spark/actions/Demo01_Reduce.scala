package cn.shouping.spark.actions

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 触发算子
 * reduce
 * collect
 * count
 * first
 * take
 * tableOrdered
 * aggregate
 * flod
 * saveAsTextFile
 * saveAsSequenceFile
 * saveAsObjectFile
 * countByKey
 * foreach
 */
object Demo01_Reduce {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(1 to 10,2)

    //reduce
    val reduceNum: Int = rdd.reduce(_+_)
    println(reduceNum)

  }
}
