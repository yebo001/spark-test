package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 * 1. 作用：管道，针对每个分区，都执行一个 shell 脚本，返回输出的 RDD。
 * 注意：脚本需要放在 Worker 节点可以访问到的位置
 */
object Demo13_Pipe {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.parallelize(List("hi","Hello","how","are","you"),1)
//
//    rdd.pipe("E:\\MyCode\\spark-parent\\spark-core\\src\\main\\resources\\pipe.sh")
//      .collect()
//      .foreach(println)

  }
}
