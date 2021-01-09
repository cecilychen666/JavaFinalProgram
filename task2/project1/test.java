package project1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static project1.hash.sha1;

public class test {
    //主函数
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        head HEAD = new head();             //建立HEAD指针
        formTree test = new formTree("/Users/philiphan/Desktop/test!");
        try {
            String directorycontents = "";

            test.form_tree("/Users/philiphan/Desktop/test!", directorycontents);


            if (!(HEAD.getHead()).equals(sha1(test.willCommitKey + HEAD.getOldHead())))//若二者不相等
            {
                System.out.println("HEAD指向的Commit为:" + HEAD.getHead());
                System.out.println("----当前文件的哈希值:" + test.willCommitKey);

                System.out.println("文件已有改动，需要生成新的Commit！");

                //commit相关操作
                System.out.print("输入您想执行的操作（commit/...)：");
                Scanner input = new Scanner(System.in);
                String str = input.next();
                if (str.equals("commit")) {

                    HEAD.recordCommit(HEAD.getHead());
                    commit Commit = new commit(test.willCommitKey, HEAD.getHead());
                    System.out.println("============新Commit已经生成！=============\n" + "Commit的Key是：" + Commit.key + "\nCommit的Value是：" + Commit.content);
                    HEAD.writeHead(Commit.key);       //【框架】将这个commit写入HEAD指针；这个commit还不是真正实例化成key-value的commit
                }


            } else System.out.println("文件无改动，无需生成新的Commit！");


        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("========下面显示HashMap信息:========\n" + hash.hashADirectory.hashMap + "\n");
    }
}

