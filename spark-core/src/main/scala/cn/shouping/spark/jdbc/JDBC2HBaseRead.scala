package cn.shouping.spark.jdbc

import cn.shouping.spark.utils.KerberosUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
 * 从HBase读取数据
 * 运行报错 local read
 */
object JDBC2HBaseRead {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  Logger.getLogger("org.apache.hadoop").setLevel(Level.WARN)
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  def main(args: Array[String]): Unit = {

    KerberosUtils.kerberos_init

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transformations")
    val sc: SparkContext = new SparkContext(conf)

    //构建HBase配置信息
    val hbaseconf: Configuration = HBaseConfiguration.create()
    hbaseconf.set("hbase.zookeeper.quorum", "sp035,sp036,sp037")
    hbaseconf.set("zookeeper.znode.parent", "/hbase-secure")
    hbaseconf.set(TableInputFormat.INPUT_TABLE,"test:people")

    //从HBase读取数据形成RDD
    val hbaseRDD: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(
      hbaseconf,
      classOf[TableInputFormat],//输入
      classOf[ImmutableBytesWritable],//key
      classOf[Result]//结果
    )

    val hbaseRDDCount: Long = hbaseRDD.count

    println(hbaseRDDCount)

    //输出打印
    hbaseRDD.foreach{
      case (_,result) =>
        val key:String = Bytes.toString(result.getRow)
        val id:Int = Bytes.toInt(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("id")))
        val name:String = Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("name")))
        val age:Int = Bytes.toInt(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("age")))
        println("RowKey:" + key + ",id:" + id + ",Name:" + name + ",age:" + age)
    }

    sc.stop()

  }
}
