package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 参数： (zeroValue:U,[partitioner: Partitioner]) (seqOp: (U, V) => U,combOp: (U, U) => U)
 * 1. 作用： 在 kv 对的 RDD 中，，按 key 将 value 进行分组合并，合并时，将每个 value 和初始值作为 seq 函数的参数，进行计算，
 * 返回的结果作为一个新的 kv 对，然后再将结果按照key 进行合并，最后将每个分组的 value 传递给 combine 函数进行计算
 * （先将前两个 value进行计算，将返回结果和下一个 value 传给 combine 函数，以此类推），将 key 与计算结果作为一个新的 kv 对输出。
 * 2. 参数描述：
 * （ 1） zeroValue： 给每一个分区中的每一个 key 一个初始值；
 * （ 2） seqOp： 函数用于在每一个分区中用初始值逐步迭代 value；
 * （ 3） combOp： 函数用于合并每个分区中的结果。
 * 3. 需求： 创建一个 pairRDD， 取出每个分区相同 key 对应值的最大值，然后相加
 */
object Demo22_AggregateByKey {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(String, Int)] = sc.parallelize(List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8)),2)

    rdd.aggregateByKey(0)(math.max(_,_),_+_)
      .collect()
      .foreach(println)

  }
}
