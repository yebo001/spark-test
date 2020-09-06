package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 * 1. 作用： 对源 RDD 进行去重后返回一个新的 RDD。 默认情况下，只有 8 个并行任务来操作，但是可以传入一个可选的 numTasks 参数改变它。
 * 2. 需求：创建一个 RDD， 使用 distinct()对其去重。
 */
object Demo09_Distinct {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(Array(1,2,1,2,3,4,5,6,7,8))

    rdd.distinct().collect().foreach(println)

  }
}
