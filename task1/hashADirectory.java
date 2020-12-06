import java.io.*;
import java.security.MessageDigest;

public class hashADirectory {
    static int blank=0;
    //hash部分
    public static byte[] SHA1Checksum(InputStream is) throws Exception {
        //用于计算hash值的文件缓冲区
        byte[] buffer = new byte[1024];
        //使用SHA1哈希/摘要算法
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        int numRead;
        do {
            numRead = is.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        is.close();
        return complete.digest();
    }
    //打开文件夹，并判别当前对象为文件/子文件夹
    public static void dfs(String path) throws Exception {       //path其实就是指向test文件夹
        File dir = new File(path);                               //将path赋给File类的对象——dir
        File[] fs = dir.listFiles();                             //File类的数组用于存放将dir指向的文件夹打开后的各个File类的对象
        //System.out.println("当前文件夹有"+fs.length+"个文件");

        for(int i = 0; i < fs.length; i++) {                     //逐一访问
            if(fs[i].isFile()) {
                for(int j=0;j<blank;j++)
                    System.out.print(" ");
                System.out.print("blob " + fs[i].getName()+"   ");
                //计算文件的哈希值
                FileInputStream is = new FileInputStream(fs[i]);
                byte[] sha1 = SHA1Checksum(is);

                String result = "";
                for(int j=0;j<sha1.length;j++){
                    result +=Integer.toString(sha1[j]&0xFF,16);
                }
                System.out.println(result);
            }
            if(fs[i].isDirectory()) {
                for(int j=0;j<blank;j++)
                    System.out.print(" ");
                blank++;
                System.out.println("tree " + fs[i].getName());

                dfs(path + File.separator + fs[i].getName());
            }
        }
    }



    //主函数
    public static void main(String[] args) {
        try {
            dfs("/Users/philiphan/Desktop/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}