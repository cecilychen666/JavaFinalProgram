package gitProject;

import static gitProject.hash.sha1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class commandline {
	public commandline(){
		
	}
	
    public static boolean commandline(head HEAD, formTree test) throws Exception {
        //命令行相关操作
        System.out.print("输入您想执行的操作（commit/rollback/branch/scan/quit/...)：");
        Scanner input = new Scanner(System.in);
        String str = input.next();
        if (str.equals("branch")) {
        	System.out.print("当前所处分支为：" + Branch.name + "\n请选择需要切换的分支：");
        	String branchName = input.next();
        	if(!Branch.chooseBranch(branchName)) {
        		System.out.print("是否新建分支（yes/no）：");
        		if(input.next().equals("yes")){
        			Branch.branch(branchName);
        			System.out.println("分支新建成功！");
        		}
        		else System.out.println("取消新建！");
        	}
        }
        if (str.equals("commit")) {
        	Branch.initBranch();
            commit Commit = new commit(test.willCommitKey, HEAD.getHead());
            HEAD.recordCommit(Commit.getkey());
            System.out.println("============新Commit已经生成！=============\n" + "Commit的Key是：" + Commit.key + "\nCommit的Value是：" + Commit.content);
            HEAD.writeHead(Commit.key);       //【框架】将这个commit写入HEAD指针；这个commit还不是真正实例化成key-value的commit
            Branch.branch();
        }
        if(str.equals("rollback")){
            rollBack.rollback();
            Branch.branch();
        }
        if(str.equals("scan")) {
        	head HEAD1 = new head();             //建立HEAD指针
            formTree test1 = new formTree("C:\\Users\\WBZ\\Desktop\\localRepository");
            try {                               //指定好路径以后，自动对路径进行扫描，告诉用户文件是否有改动
                String directorycontents = "";

                test1.form_tree("C:\\Users\\WBZ\\Desktop\\localRepository", directorycontents);


                if (!(HEAD.getHead()).equals(sha1(test.willCommitKey + HEAD.getOldHead())))//若二者不相等
                {
                    System.out.println("HEAD指向的Commit为:" + HEAD.getHead());
                    System.out.println("当前文件的哈希值:" + test.willCommitKey);

                    System.out.println("文件已有改动，需要生成新的Commit！");

                } else System.out.println("文件无改动，无需生成新的Commit！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(str.equals("quit")) {
        	return false;
        }
        return true;
    }
}
