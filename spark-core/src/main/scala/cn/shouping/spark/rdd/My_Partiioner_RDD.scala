package cn.shouping.spark.rdd

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
 * 自定义分区器
 * 要实现自定义的分区器，你需要继承 org.apache.spark.Partitioner 类并实现下面三个方法。
 * （1） numPartitions: Int:返回创建出来的分区数。
 * （2） getPartition(key: Any): Int:返回给定键的分区编号(0 到 numPartitions-1)。
 * （3） equals():Java 判断相等性的标准方法。这个方法的实现非常重要， Spark 需要用这个方法来检查你的分区器对象是否和其他分区器实例相同，这样 Spark 才可以判断两个 RDD的分区方式是否相同。
 *
 * 需求： 将相同后缀的数据写入相同的文件，通过将相同后缀的数据分区到相同的分区并保存输出来实现。
 */
object My_Partiioner{
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val data: RDD[(Int, Int)] = sc.parallelize(Array((1,1),(2,2),(3,3),(4,4),(5,5),(6,6)))

    val rdd: RDD[(Int, Int)] = data.partitionBy(new My_Partiioner_RDD(2))

    rdd.mapPartitionsWithIndex((index,iterm)=> iterm.map((index,_)))
      .collect.foreach(println)

  }
}


class My_Partiioner_RDD(numParts:Int) extends Partitioner{

  //覆盖分区数
  override def numPartitions: Int = numParts

  //覆盖分区号获取函数
  override def getPartition(key: Any): Int = {
    val ckey: String = key.toString
    ckey.substring(ckey.length-1).toInt%numParts
  }

}
