package gitProject;

import java.io.File;

public class formTree extends Repository {
    public static String path;        //给定工作目录
    public static String willCommitKey="";      //待定的key值，需要进一步和HEAD比对才知道是否是新的哈希值

    public formTree(String path) {     //这个directorycontents 是将来给commit的
        this.path = path;
    }


    //打开文件夹，并判别当前对象为文件/子文件夹
    public static String form_tree(String path, String directoryContents) throws Exception {       //path其实就是指向test文件夹


        File dir = new File(path);                               //将path赋给File类的对象——dir
        File[] fs = dir.listFiles();                             //File类的数组用于存放将dir指向的文件夹打开后的各个File类的对象

        for (int i = 0; i < fs.length; i++) {                    //逐一访问
            if (fs[i].isFile()) {
                blob newblob = new blob(fs[i]);                  //在blob里传给行参f；

                newblob.content();                               //用于显示blob的内容，即Value值

                //Repository.writeFile(newblob.getKey(), newblob.getContent());//写入文件夹
                //存入HashMap中
                hash.hashADirectory.hashMap.put(newblob.getKey(), newblob.getContent());//左边是文件的键：哈希值；右边是文件的值：文件内容
//                System.out.println(newblob.getKey());

                directoryContents += "blob " + "{" + newblob.getKey() + "} " + fs[i].getName() + "||";     //将当前文件的类型（blob）、名称、哈希值存放在directoryContents里面

            }

            //如果当前路径是文件夹
            if (fs[i].isDirectory()) {
                tree newtree = new tree(fs[i], path);
//                System.out.println("文件夹" + newtree.name + "的哈希值是：" + newtree.key);

                //便于理解，方便直接把commitkey赋值给commit的key
                willCommitKey = newtree.key;

                //Repository.writeFile(newtree.getKey(), newtree.getContent());
                //存入HashMap中
                hash.hashADirectory.hashMap.put(newtree.key, newtree.content);     //将文件夹的键值对放入HashMap内

                directoryContents += newtree.type + " " + "{" + newtree.getKey() + "} " + fs[i].getName() + "||";

            }
        }
        return directoryContents;

    }


}