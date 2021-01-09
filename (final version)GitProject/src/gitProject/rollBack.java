package gitProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class rollBack {
	static private String historyPath = "C:\\Users\\WBZ\\Desktop\\myRepository\\history";
	static private String HEADPath = "C:\\Users\\WBZ\\Desktop\\HEAD";
	static private String headPath = "C:\\Users\\WBZ\\Desktop\\HEAD\\head.txt";
	static private String oldheadPath = "C:\\Users\\WBZ\\Desktop\\HEAD\\oldhead.txt";
	static private String localRepositoryPath = "C:\\Users\\WBZ\\Desktop\\localRepository";
	
	
	public static void rollback() throws IOException {
    	
    	// 得到上个版本的hash
    	String oldHash = readFile(oldheadPath);
    	if(oldHash == "") {
    		System.out.println("当前为第一次commit，不可回滚！");
    		return;
    	}
    	// 删除当前仓库中的文件
    	deleteFile(new File(localRepositoryPath));
    	// 复制上个版本中的文件
    	copy.copyDir(historyPath + "\\" + oldHash, localRepositoryPath);
    	// 修改head文件和oldhead文件中的内容
    	Repository.writeFile(headPath, oldHash);
    	oldHash = readFile(HEADPath + File.separator + oldHash + ".txt");
    	Repository.writeFile(oldheadPath, oldHash);
    	System.out.println("回滚成功！");
    }
    
    public static void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        file.delete();
    }
    
    public static String readFile(String filePathName) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(filePathName));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close(); 
        String str = sb.toString();
        return str;
	}
}
