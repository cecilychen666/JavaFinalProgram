package gitProject;

import java.io.File;
import java.io.IOException;

public class Branch {
	public static String name = "master";
	public static String path = "C:\\Users\\WBZ\\Desktop\\Branch";
	private static String HEADPath =  "C:\\Users\\WBZ\\Desktop\\HEAD";
	private static String headPath = "C:\\Users\\WBZ\\Desktop\\HEAD\\head.txt";
	private static String oldheadPath = "C:\\Users\\WBZ\\Desktop\\HEAD\\oldhead.txt";
	private static String branchPath = path + File.separator + name + ".txt";
	private static String historyPath = "C:\\Users\\WBZ\\Desktop\\myRepository\\history";
	private static String localRepositoryPath = "C:\\Users\\WBZ\\Desktop\\localRepository";
	
	public static void initBranch() {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdir();
			System.out.println("分支初始化完成！");
		}
	}
	
	public static void branch() throws IOException {
		File file = new File(branchPath);
		if(!file.exists())
			file.createNewFile();
		String branchHash = rollBack.readFile(headPath);
		Repository.writeFile(branchPath, branchHash);
	}
	
	public static void branch(String newname) throws IOException {
		name = newname;
		branchPath = path + File.separator + name + ".txt";
		File file = new File(branchPath);
		if(!file.exists()) 
			file.createNewFile();
		String branchHash = rollBack.readFile(headPath);
		Repository.writeFile(branchPath, branchHash);
	}
	
	public static boolean chooseBranch(String newname) throws IOException {
		File dir = new File(path);
		File[] list = dir.listFiles();
		boolean flag = false;
		for(File file : list) {
			if((newname + ".txt").equals(file.getName())) {
				flag = true;
				break;
			}
		}
		if(!flag) {
			System.out.println("无此分支！");
			return false;
		}
		else {
			name = newname;
			branchPath =  path + File.separator + name + ".txt";
			Repository.writeFile(headPath, rollBack.readFile(branchPath));
			Repository.writeFile(oldheadPath, rollBack.readFile(HEADPath + File.separator + rollBack.readFile(branchPath) + ".txt"));
	    	rollBack.deleteFile(new File(localRepositoryPath));
	    	copy.copyDir(historyPath + File.separator + rollBack.readFile(branchPath), localRepositoryPath);
	    	System.out.println("分支切换成功！");
			return true;
		}
	}
	
	public static void branchUpdate() throws IOException {
		String currHead = rollBack.readFile(headPath);
		Repository.writeFile(branchPath, currHead);
	}
}
