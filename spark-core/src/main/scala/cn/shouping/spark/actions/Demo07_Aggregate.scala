package cn.shouping.spark.actions

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 1. 参数： (zeroValue: U)(seqOp: (U, T) ⇒ U, combOp: (U, U) ⇒ U)
 * 2. 作用： aggregate 函数将每个分区里面的元素通过 seqOp 和初始值进行聚合，然后用combine 函数将每个分区的结果和初始值(zeroValue)进行 combine 操作。
 *    这个函数最终返回的类型不需要和 RDD 中元素类型一致。
 * 3. 需求：创建一个 RDD，将所有元素相加得到结果
 */
object Demo07_Aggregate {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(1 to 10,2)

    val i: Int = rdd.aggregate(0)(_+_,_+_)

    println(i)

  }
}
