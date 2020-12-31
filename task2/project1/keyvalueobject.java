package project1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static project1.hash.SHA1Checksum;
 
public class keyvalueobject {
    protected String type;
    protected String key="";
    protected File file;
    protected String content="";       //存放Value。对于文件来说content是内容，对于文件夹来说是内部子文件&子文件夹的type+key+name
    protected keyvalueobject(){

    }

    //生成文件的哈希值——作为键
    protected void genKey(File file) throws Exception{
        this.file=file;
        FileInputStream is = new FileInputStream(file);
        byte[] sha1 = SHA1Checksum(is);

        for(int j=0;j<sha1.length;j++){               //哈希值存成string形式————赋值给result

            key +=Integer.toString(sha1[j]&0xFF,16);
        }
    }

    protected void genKey(String content) throws NoSuchAlgorithmException {
        key = hash.sha1(content);
    }

    protected void content() throws IOException, Exception {}



}
