package gitProject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static gitProject.hash.sha1;

public class test {
    //主函数
    public static void main(String[] args) throws IOException{
        head HEAD = new head();             //建立HEAD指针
        formTree test = new formTree("C:\\Users\\WBZ\\Desktop\\localRepository");
        try {                               //指定好路径以后，自动对路径进行扫描，告诉用户文件是否有改动
            String directorycontents = "";

            test.form_tree("C:\\Users\\WBZ\\Desktop\\localRepository", directorycontents);


            if (!(HEAD.getHead()).equals(sha1(test.willCommitKey + HEAD.getOldHead())))//若二者不相等
            {
                System.out.println("HEAD指向的Commit为:" + HEAD.getHead());
                System.out.println("----当前文件的哈希值:" + test.willCommitKey);

                System.out.println("文件已有改动，需要生成新的Commit！");

            } else System.out.println("文件无改动，无需生成新的Commit！");

            while(true){
                if(!commandline.commandline(HEAD, test)) break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}