package cn.shouping.spark.utils

import org.apache.hadoop.security.UserGroupInformation

object KerberosUtils {

  /**
   * Windows_Kerberos认证
   */
  def kerberos_init :Unit={
        System.setProperty("hadoop.home.dir", "D:\\software\\hadoop-2.7.2")
        System.setProperty("java.security.krb5.conf", "E:\\MyCode\\spark-parent\\spark-core\\src\\main\\resources\\krb5.conf")
        System.setProperty("HADOOP_USER_NAME", "yb5")
        System.setProperty("user.name", "yb5");
        UserGroupInformation.loginUserFromKeytab("yb5@SPCLUSTER.COM", "E:\\MyCode\\spark-parent\\spark-core\\src\\main\\resources\\yb5User.keytab")
        System.out.println(UserGroupInformation.getLoginUser())
  }

}
