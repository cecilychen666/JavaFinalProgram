package gitProject;

import java.io.File;

public class tree extends keyvalueobject {
    public String name;
    public String path;

    //不能和blob共用一个genKey！
    public tree(File f, String path) throws Exception {
        this.type = "tree";
        this.name = f.getName();
        this.path = path;

        ///仅仅为了显示，后期可删///////////////////////
//        for (int j = 0; j < hash.hashADirectory.blank; j++)
//            System.out.print(" ");
//        hash.hashADirectory.blank++;
//        System.out.println(this.type + " " + this.name);
        //////////////////////////////////////////////

        this.content();             //先在当前文件夹下得到应有的值，再根据值进行genKey()动作
        genKey(this.content);       //生成文件夹的哈希值，即Key
    }

    @Override
    public void content() throws Exception {
        this.content += formTree.form_tree(path + File.separator + this.name, this.content);
//        System.out.println("===此时当前文件夹" + this.name + "的value是：" + this.content);
    }

    public String getContent() {     //返回文件夹内容值
        return this.content;
    }

    public String getKey() {     //返回tree的键
        return this.key;
    }


}

