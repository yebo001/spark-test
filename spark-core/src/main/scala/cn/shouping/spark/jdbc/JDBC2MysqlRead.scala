package cn.shouping.spark.jdbc

import java.sql.DriverManager

import cn.shouping.spark.utils.KerberosUtils
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *jdbc 访问mysql数据库
 */
object JDBC2MysqlRead {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    //定义mysql连接参数
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://sp035:3306/test"
    val username = "root"
    val password = "123456"



    //创建JDBCRDD
    /**
     * sc: SparkContext,
     * getConnection: () => Connection,//获取连接
     * sql: String,//查询语句
     * lowerBound: Long,//第一个占位符的最小值
     * upperBound: Long,//第二个占位符的最大值 下界和上界是包容的。
     * *给定下界为1，上界为20,numPartitions为2，
     * *查询将执行两次，一次使用(1,10)，一次使用(11,20)
     * numPartitions: Int,//分区的数量。
     * mapRow: (ResultSet) => T = JdbcRDD.resultSetToObjectArray _)//从ResultSet到所需结果类型的单行的函数。
     * *这应该只调用getInt, getString等;RDD负责接下来的调用。
     * *默认将ResultSet映射到对象数组。
     */
    val mysqlRDD: JdbcRDD[(Int, String,Int)] = new JdbcRDD(
      sc,
      () => {
        Class.forName(driver)
        DriverManager.getConnection(url, username, password)
      }
      ,
      "select * from `test`where `id`>=? and `id`<=?;",
      1,//上界
      2,//下界
      5,//rdd分区数
      r => (r.getInt(1), r.getString(2),r.getInt(3))//获取字段个数
    )

    println(mysqlRDD.count())

    mysqlRDD.foreach(println)
    println(mysqlRDD.getNumPartitions)
    sc.stop

  }
}
