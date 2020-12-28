package project1;

import java.io.*;


public class head{
    public head() throws IOException {
        File file = new File("/Users/philiphan/Desktop"+"/"+"HEAD");
        if(!file.exists())
        {file.mkdir();}
        File head = new File("/Users/philiphan/Desktop/HEAD/head.txt");
        if(!head.exists()) {
            head.createNewFile();
        }
        File oldhead = new File("/Users/philiphan/Desktop/HEAD/head.txt");
        if(!oldhead.exists()){
            oldhead.createNewFile();
        }

    }

    public void updateOldHead() throws IOException {
        BufferedWriter out2 = new BufferedWriter(new FileWriter("/Users/philiphan/Desktop/HEAD/oldhead.txt"));
        out2.write(this.getHead());
        out2.close();
    }

    public void writeHead(String commit) throws IOException { //将最新commit写入head文件内
        BufferedWriter out1 = new BufferedWriter(new FileWriter("/Users/philiphan/Desktop/HEAD/head.txt"));

        out1.write(commit);
        out1.close();
    }



    public String getHead() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("/Users/philiphan/Desktop/HEAD/head.txt"));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = in.readLine()) != null) {
            sb.append(line + "\n");
        }
        in.close();
        String str = sb.toString().replace("\n","");//【注意】：写入文件的时候会自动产生一个"\n"！

        //以UTF-8编码保存，需要加入兼容代码
        //return deleteUTF8Bom(str);
        return str;
    }

    public String getOldHead() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("/Users/philiphan/Desktop/HEAD/oldhead.txt"));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = in.readLine()) != null) {
            sb.append(line + "\n");
        }
        in.close();
        String str = sb.toString().replace("\n","");//【注意】：写入文件的时候会自动产生一个"\n"！

        //以UTF-8编码保存，需要加入兼容代码
        //return deleteUTF8Bom(str);
        return str;
    }


    /*public static String deleteUTF8Bom(String fileStr) {
        byte[] UTF8_BOM_BYTES = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        byte[] bytes = fileStr.getBytes();
        if (bytes[0] == UTF8_BOM_BYTES[0]
                && bytes[1] == UTF8_BOM_BYTES[1]
                && bytes[2] == UTF8_BOM_BYTES[2]) {
            return new String(bytes, 3, bytes.length - 3);
        }
        System.out.println(Arrays.toString(fileStr.getBytes())+"head类中：");
        return fileStr;
    }*/



}
