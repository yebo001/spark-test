package cn.shouping.spark.rdd

import org.apache.spark.rdd.RDD

class Search() extends  Serializable {
  //过滤出包含字符串的数据
  def isMatch(s: String): Boolean = {
    s.contains("query")
  }

  //过滤出包含字符串的 RDD
  def getMatch1 (rdd: RDD[String]): RDD[String] = {
    rdd.filter(isMatch)
  }

  //过滤出包含字符串的 RDD
  def getMatche2(rdd: RDD[String]): RDD[String] = {
    rdd.filter(x => x.contains("query"))
  }

}