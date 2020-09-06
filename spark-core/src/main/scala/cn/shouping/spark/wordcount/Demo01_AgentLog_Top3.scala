package cn.shouping.spark.wordcount

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 1. 数据结构： 时间戳，省份，城市，用户， 广告，中间字段使用空格分割。
 * 1516609143867 6 7 64 16
 * 1516609143869 9 4 75 18
 * 1516609143869 1 7 87 12
 *
 * 需求： 统计出每一个省份广告被点击次数的 TOP3
 */
object Demo01_AgentLog_Top3 {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    //1.初始化 spark 配置信息并建立与 spark 的连接
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    //2.读取数据生成 RDD： TS， Province， City， User， AD
    val agentRDD: RDD[String] = sc.textFile("E:\\MyCode\\spark-parent\\spark-core\\src\\main\\resources\\agent.log")

    //3.按照最小粒度聚合： ((Province,AD),1)
    val provinceAdToOne: RDD[((String, String), Int)] = agentRDD.map { line =>
      val agent: Array[String] = line.split(" ")
      val province: String = agent(1)
      val ad: String = agent(4)
      ((province, ad), 1)
    }

    //4.计算每个省中每个广告被点击的总数： ((Province,AD),sum)
    val provinceAdToOneSum: RDD[((String, String), Int)] = provinceAdToOne.reduceByKey(_+_)

    //5.将省份作为 key，广告加点击数为 value： (Province,(AD,sum))
    val provinceToAdSum: RDD[(String, (String, Int))] = provinceAdToOneSum.map(x => (x._1._1,(x._1._1,x._2)))

    //6.将同一个省份的所有广告进行聚合(Province,List((AD1,sum1),(AD2,sum2)...))
    val provinceGroup: RDD[(String, Iterable[(String, Int)])] = provinceToAdSum.groupByKey()

    //7.对同一个省份所有广告的集合进行排序并取前 3 条，排序规则为广告点击总数
    val top3: RDD[(String, List[(String, Int)])] = provinceGroup.mapValues { iterable =>
      iterable.toList.sortWith((x, y) => x._2 > y._2).take(3)
    }

    //8.将数据拉取到 Driver 端并打印
    top3.collect().foreach(println)

    //9.关闭与 spark 的连接
    sc.stop()

  }
}
