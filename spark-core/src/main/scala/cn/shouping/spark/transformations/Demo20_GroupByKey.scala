package cn.shouping.spark.transformations

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Key-Value 类型
 * 1. 作用： groupByKey 也是对每个 key 进行操作，但只生成一个 sequence。
 * 2. 需求：创建一个 pairRDD，将相同 key 对应值聚合到一个 sequence 中，并计算相同 key对应值的相加结果。
 *
 * 1. reduceByKey：按照 key 进行聚合，在 shuffle 之前有 combine（预聚合）操作，返回结果是 RDD[k,v].
 * 2. groupByKey：按照 key 进行分组，直接进行 shuffle。
 * 3. 开发指导： reduceByKey 比 groupByKey，建议使用。但是需要注意是否会影响业务逻辑。
 */
object Demo20_GroupByKey {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.parallelize(Array("one", "two", "two", "three", "three", "three"))

    rdd.map((_,1))
      .groupByKey()
      .collect()
      .foreach(println)

  }
}
