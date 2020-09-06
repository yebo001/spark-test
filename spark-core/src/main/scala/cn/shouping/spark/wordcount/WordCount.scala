package cn.shouping.spark.wordcount

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {
//    //参数校验
    if (args == null || args.length != 2) {
      println(
        """
          |Usage : <Input>
          |""".stripMargin
      )
      System.exit(-1)
    }

    val Array(input,output) = args
//    System.setProperty("HADOOP_USER_NAME","hdfs")
    //创建SparkConf并设置app名称

//    System.setProperty("hadoop.home.dir", "D:\\software\\hadoop-2.7.2")
//    System.setProperty("java.security.krb5.conf", "E:\\MyCode\\spark-parent\\spark-core\\src\\main\\resources\\krb5.conf")
//    System.setProperty("HADOOP_USER_NAME", "yb5")
//    System.setProperty("user.name", "yb5");
//    UserGroupInformation.loginUserFromKeytab("yb5@SPCLUSTER.COM", "E:\\MyCode\\spark-parent\\spark-core\\src\\main\\resources\\yb5User.keytab")
//    System.out.println(UserGroupInformation.getLoginUser())

    val conf: SparkConf = new SparkConf().setAppName("WordCount_first")
//      .setMaster("local[*]")
    //创建SparkContext 提交app的入口
    val sc: SparkContext = new SparkContext(conf)
    //3.使用 sc 创建 RDD 并执行相应的 transformation 和 action
//    val list: List[String] = List("i am you","hello hello hello")
    val line: RDD[String] = sc.textFile(args(0))
    line.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .sortBy(_._2,false)
      .saveAsTextFile(args(1))
    //关闭资源
    sc.stop()
  }
}
