package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Value 类型
 *  sample(withReplacement, fraction, seed)
 * 1. 作用： 以指定的随机种子随机抽样出数量为 fraction 的数据，
 *    withReplacement 表示是抽出的数据是否放回， true 为有放回的抽样， false 为无放回的抽样， seed 用于指定随机数生成器种子。
 * 2. 需求：创建一个 RDD（1-10），从中选择放回和不放回抽样
 */
object Demo08_Sample {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(1 to 10)

    rdd.sample(false,0.5,2)
      .collect()
      .foreach(println)
  }
}
