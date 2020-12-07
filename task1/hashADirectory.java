import java.io.*;
import java.lang.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;

public class hashADirectory {
    static int blank=0;         //用于后面打印文件夹内结构而设置的"空格"
    static String directoryContents="";         //用于存放当前文件夹的value
    static MessageDigest directoryComplete;     //用于给文件夹生成哈希值
    static {
        try {
            directoryComplete = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    static Map<String, String> hashMap = new HashMap<String, String>();//左-键｜｜右-值；


    //hash部分(给文件专用）
    public static byte[] SHA1Checksum(InputStream is) throws Exception {
        //用于计算hash值的文件缓冲区
        byte[] buffer = new byte[1024];
        //使用SHA1哈希/摘要算法
        MessageDigest complete = MessageDigest.getInstance("SHA-1");
        int numRead;
        do {
            numRead = is.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);        //complete用于存储当前文件的message
                directoryComplete.update(buffer,0,numRead);     //directoryComplete用于给文件夹存放/更新其内部文件的message
            }
        } while (numRead != -1);
        is.close();
        return complete.digest();
    }

    //打开文件夹，并判别当前对象为文件/子文件夹
    public static String dfs(String path) throws Exception {       //path其实就是指向test文件夹
        File dir = new File(path);                               //将path赋给File类的对象——dir
        File[] fs = dir.listFiles();                             //File类的数组用于存放将dir指向的文件夹打开后的各个File类的对象

        for(int i = 0; i < fs.length; i++) {                     //逐一访问
            if(fs[i].isFile()) {
                //得到文件内容
                BufferedReader in = new BufferedReader(new FileReader(fs[i]));
                String str = in.readLine();

                //打印，告诉用户这是一个blob，即文件（后期可删）
                for(int j=0;j<blank;j++)
                    System.out.print(" ");
                System.out.print("blob " + fs[i].getName()+"   ");

                //计算文件的哈希值
                FileInputStream is = new FileInputStream(fs[i]);
                byte[] sha1 = SHA1Checksum(is);

                //哈希值存成string形式
                String result = "";
                for(int j=0;j<sha1.length;j++){
                    result +=Integer.toString(sha1[j]&0xFF,16);
                }
                //存入HashMap中
                hashMap.put(result,str);//左边是文件的键：哈希值；右边是文件的值：文件内容
                System.out.println(result);

                directoryContents+="blob-"+fs[i].getName()+"-"+result+"||";     //将当前文件的类型（blob）、名称、哈希值存放在directoryContents里面

            }

            //如果当前路径是文件夹
            if(fs[i].isDirectory()) {
                String name=fs[i].getName();
                //负责打印格式
                for(int j=0;j<blank;j++)
                    System.out.print(" ");
                blank++;
                System.out.println("tree " + fs[i].getName());

                directoryContents = "";             //如果判断到当前的路径下是一个文件夹，那么将directoryContents清空，专门用于存放这个文件夹的哈希值
                directoryContents+= dfs(path + File.separator + fs[i].getName());   //递归，遍历文件夹的文件，并放入当前文件夹的directoryContents中，形成这个文件夹的值（value)
                
                //得到文件夹的哈希值
                byte[] sha2 = directoryComplete.digest();       //此时directoryComplete里面已经存入了当前文件夹的所有内容
                String directoryResult = "";
                for(int j=0;j<sha2.length;j++){
                    directoryResult +=Integer.toString(sha2[j]&0xFF,16);    //得到文件夹的哈希值，也就是文件夹的键（key）
                }
                System.out.println("文件夹"+fs[i].getName()+"的哈希值是："+directoryResult);

                System.out.println("===此时当前文件夹"+name+"的value是："+directoryContents);
                //存入HashMap中
                hashMap.put(directoryResult,directoryContents);     //将文件夹的键值对放入HashMap内
                directoryContents+="tree-"+fs[i].getName()+"-"+directoryResult+"||";

            }
        }
        return directoryContents;
    }
    
    //主函数
    public static void main(String[] args) {
        try {
            dfs("/Users/philiphan/Desktop/test!");
        } catch (Exception e) {
            e.printStackTrace();
        }
            System.out.println("\n========下面显示HashMap信息:========\n"+hashMap+"\n");
    }
}