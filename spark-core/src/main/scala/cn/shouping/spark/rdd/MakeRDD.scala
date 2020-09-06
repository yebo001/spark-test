package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 创建RDD的三种方式
 */
object MakeRDD {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("MakeRDD")
    val sc: SparkContext = new SparkContext(conf)

    //从parallelize()集合中创建RDD
    val rdd1: RDD[Int] = sc.parallelize(Array(1,2,3,4,5,6))
    rdd1.collect().foreach(print)

    //使用makeRdd创建
    val rdd2: RDD[Int] = sc.makeRDD(Array(1,2,3))
    rdd2.collect().foreach(print)

    //从外部系统中读取文件
    val rdd3: RDD[String] = sc.textFile("hdfs://sp035:8020/user/yb5/data/word.txt")
    rdd3.collect().foreach(print)

  }
}
