package project1;

public class test {
    //主函数
    public static void main(String[] args) {
        try {
            String directorycontents = "";
            dfs test = new dfs("/Users/philiphan/Desktop/test!");
            test.DFS("/Users/philiphan/Desktop/test!", directorycontents);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //这里显示hashmap信息只是为了方便自己开发，非必要功能，开发完成后可删除
        System.out.println("========下面显示HashMap信息:========\n" + hash.hashADirectory.hashMap + "\n");
    }
}

