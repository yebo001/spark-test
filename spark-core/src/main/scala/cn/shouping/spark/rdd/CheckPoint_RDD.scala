package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * CheckPonit 检查点  会切断血缘 将该RDD中所有依赖于父RDD中的信息将全部被移除
 *  本质是通过将 RDD 写入 Disk 做检查点 是为了通过 lineage 做容错的辅助
 *  lineage 过长会造成容错成本过高，这样就不如在中间阶段做检查点容错，如果之后有节点出现问题而丢失分区，
 *  从做检查点的 RDD 开始重做 Lineage，就会减少开销。 检查点通过将数据写入到 HDFS文件系统实现了 RDD 的检查点功能。
 */
object CheckPoint_RDD {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    sc.setCheckpointDir("hdfs://sp035:8020/checkpoint")

    val rdd: RDD[String] = sc.parallelize(Array("sp"))

    val rdd1: RDD[String] = rdd.map(_+System.currentTimeMillis())

    rdd1.checkpoint()

    rdd1.collect.foreach(println)
    rdd1.collect.foreach(println)
    rdd1.collect.foreach(println)
    rdd1.collect.foreach(println)
    rdd1.collect.foreach(println)
    rdd1.collect.foreach(println)

  }
}
