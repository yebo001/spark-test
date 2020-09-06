package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * RDD中的函数传递
 *
 * 在实际开发中我们往往需要自己定义一些对于 RDD 的操作，那么此时需要主要的是，初始化工作是在 Driver 端进行的，
 * 而实际运行程序是在 Executor 端进行的，这就涉及到了跨进程通信，是需要序列化的。 下面我们看几个例子：
 */
object Functions_Pass_On_RDD {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

      KerberosUtils.kerberos_init

      val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
      val sc: SparkContext = new SparkContext(conf)

      val rdd: RDD[String] = sc.parallelize(Array("hadoop", "spark", "hive", "sp"))

      val search: Search = new Search()

      val match1: RDD[String] = search.getMatch1(rdd)

      match1.collect.foreach(println)

      sc.stop()

  }
}

