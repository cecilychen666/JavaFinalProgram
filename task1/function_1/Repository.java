package task1;

import java.io.*;
import java.security.*;

// 创建仓库类，用于存储上传的内容，并可根据哈希值找到对应的内容。
// 使用静态方法，在首次创建过仓库后，后续通过给定仓库地址便可对仓库的内容进行操作。
public class Repository {
	// 仓库路径，包含仓库名
	private static String path;
	
	
	// 设置仓库路径，应包含仓库名
	public static void setPath(String newPath) {
		path = newPath;
	}
			
	// 无参构造方法
	public Repository() {
		setPath("C:/Users/WBZ/Desktop" + '/' + "myRepository");  // 设置默认路径和名字
		// 创建仓库文件夹，并创建readme文档
		try{
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
				writeFile(path + '/' + "readme.txt", "Hello World!");
			}
			// 若仓库名存在，则新建失败
			else System.out.println("仓库名重复，新建失败！");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	// 构造方法重载，参数为新建仓库地址和仓库名
	public Repository(String myPath, String name) {
		setPath(myPath + '/' + name);
		// 根据给定路径和仓库名创建仓库，并创建readme文档
		try{
			File file = new File(path);
			if(!file.exists()) {
				file.mkdir();
				writeFile(path + '/' + "readme.txt", "Hello World!");
			}
			// 若仓库名存在，则新建失败
			else System.out.println("仓库名重复，新建失败！");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 将指定内容写入文件
	private static void writeFile(String filename, String content) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			out.write(content);
			out.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	// 上传内容，文件名命名为内容的hash值
	public static void upload(String content) {
		try {
			String currentPath = path + '/' + "initialName.txt";
			writeFile(currentPath, content);
			renameFile(currentPath, path + '/' + getHashValue(currentPath) + ".txt");
			File file = new File(currentPath);
			// 若上传内容与仓库中已有内容重复，则上传失败
			if(file.exists()) {
				file.delete();
				System.out.println("内容重复，上传失败！");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// 根据哈希值，返回对应的内容
	public static String search(String HashValue) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(path + '/' + HashValue + ".txt"));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = in.readLine()) != null) {
            sb.append(line + "\n");
        }
        in.close(); 
        String str = sb.toString();
        return str;
	}
	
	// 文件重命名方法
	private static void renameFile(String oldPathName, String newPathName) throws IOException {
        // 旧的文件或目录
        File oldName = new File(oldPathName);
        // 新的文件或目录
        File newName = new File(newPathName);
        // 确保新的文件名不存在
        if (!newName.exists())
        	oldName.renameTo(newName);
    }
	
	// 读取文件字节，得到哈希值
	private static byte[] SHA1Checksum(InputStream is) throws Exception {
		// 用于计算hash值的文件缓冲区
		byte[] buffer = new byte[1024];
		// 使用SHA1哈希/摘要算法
		MessageDigest complete = MessageDigest.getInstance("SHA-1");
		int numRead = 0;
		do {
			// 读出numRead字节到buffer中
			numRead = is.read(buffer);
			if(numRead > 0) {
				// 根据buffer[0:numRead]的内容更新hash值
				complete.update(buffer, 0, numRead);
			}
			// 文件已读完，退出循环
		} while (numRead != -1);
		// 关闭输入流
		is.close();
		// 返回SHA1哈希值
		return complete.digest();
	}
	
	// 计算文件哈希值，返回结果
	private static String getHashValue(String path) throws Exception {
		// 建立文件对象
		File file = new File(path);
		// 为文件建立输入流
		FileInputStream is = new FileInputStream(file);
		// 将计算得到的文件哈希值（字节）传入数组中
		byte[] sha1 = SHA1Checksum(is);
			
		String result = "";
		for(int i = 0; i < sha1.length; i++) {
			// 将得到的整型字节哈希值计算补码，并转换为16进制
			result += Integer.toString(sha1[i] & 0xFF, 16);
		}
		return result;
	}
	
}
