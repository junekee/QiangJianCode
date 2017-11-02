import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class WeblogicEncryptStr {

    /**
     * @param args
     *            需要设置java环境变量-Dweblogic.RootDirectory=C:/bea/user_projects/ltais
     *            需要用到java环境和weblogic环境
     */
    public static void main(String[] args) {
            String passwdStr = null;
            String operateType = "";
            System.out.println("=====本脚本用来生成/破解weblogic的boot.properties内容=====");
            System.out.println("==============需要放到weblogic真实环境下运行==============");
            System.out.println("=================@AUTHER：Z.X.T=======================");
            System.out.println("==========java环境变量 -Dweblogic.RootDirectory=========");
            System.out.println("============需要在CLASSPATH中加上weblogic.jar===========");
            EncryptionService es = SerializedSystemIni
                            .getExistingEncryptionService();
            if (es == null) {
                    System.err.println("需要设置环境变量： -Dweblogic.RootDirectory");
                    System.exit(0);
            }
            ClearOrEncryptedService t = new ClearOrEncryptedService(es);
            try {
                    while (true) {
                            System.out.println("请选择解密/加密/退出<1/2/3>:");
                            operateType = new BufferedReader(new InputStreamReader(
                                            System.in)).readLine();
                            if (operateType.equals("1")) {
                                    System.out.println("请输入要加密的内容");
                                    passwdStr = new BufferedReader(new InputStreamReader(
                                                    System.in)).readLine();
                                    if (passwdStr != null) {
                                            System.out.println("加密结果为：" + t.encrypt("weblogic"));
                                    }
                            } else if (operateType.equals("2")) {
                                    System.out.println("请输入要解密的密码:");
                                    passwdStr = new BufferedReader(new InputStreamReader(
                                                    System.in)).readLine();
                                    if (passwdStr != null) {
                                            System.out.println("解密结果为：" + t.decrypt(passwdStr));
                                    }
                            } else if (operateType.equals("3")) {
                                    System.exit(0);
                            } else {
                                    System.out.println("输入操作类型错误！应该为<1 or 2>,重新输入：");
                            }
                    }
            } catch (IOException e) {
                    e.printStackTrace();
            }

    }
}
