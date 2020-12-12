package project1;

import java.io.*;

public class blob extends keyvalueobject {

    public blob(File f) throws Exception {
        genKey(f);
        this.type = "blob";
        //newfile = f;
    }

    @Override
    public void content() throws IOException {    //得到文件内容
        BufferedReader in = new BufferedReader(new FileReader(this.file));
        this.content = in.readLine();

        //打印，告诉用户这是一个blob，即文件（后期可删）
        for (int j = 0; j < hash.hashADirectory.blank; j++)
            System.out.print(" ");
        System.out.print(this.type + " " + this.file.getName() + "   ");
    }

    public String getContent() {     //返回文件内容值

        return this.content;
    }

    public String getKey() {     //返回blob的键

        return this.key;
    }

}
