package cn.shouping.spark.jdbc

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD

/**
 * 向mysql数据库写入
 */
object JDBC2MysqlWrite {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[(Int, String, Int)] = sc.parallelize(List((4,"yb4",23)))

    rdd.foreachPartition(insertData)

  }

  def insertData(iterator: Iterator[(Int,String,Int)]): Unit={
    Class.forName("com.mysql.jdbc.Driver").newInstance()
     val connection: Connection = DriverManager.getConnection("jdbc:mysql://sp035:3306/test", "root","123456")

    iterator.foreach(data => {
      val statement: PreparedStatement = connection.prepareStatement("insert into test(id,name,age) values (?,?,?)")
      statement.setInt(1,data._1)
      statement.setString(2,data._2)
      statement.setInt(3,data._3)
      statement.executeUpdate()
    })

  }

}
