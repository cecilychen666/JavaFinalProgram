package gitProject;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class hash {

    public static class hashADirectory {
        static Map<String, String> hashMap = new HashMap<String, String>();         //左-键｜｜右-值；
        public static int blank = 0;                                   //用于后面打印文件夹内结构而设置的"空格"
        static MessageDigest directoryComplete;                        //用于给文件夹生成哈希值

        static {
            try {
                directoryComplete = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    //文件hash
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
            }
        } while (numRead != -1);
        is.close();
        return complete.digest();
    }

    //文件夹hash
    public static String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }
}
