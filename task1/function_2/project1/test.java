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
        System.out.println("========下面显示HashMap信息:========\n" + hash.hashADirectory.hashMap + "\n");
    }
}

