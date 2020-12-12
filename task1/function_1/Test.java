package task1;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		// 利用有参构造方法新建一个仓库
		new Repository("C:/Users/WBZ/Desktop", "r1");
		// 设置仓库路径，在新建过仓库后，需要设定仓库地址对仓库进行操作
//		Repository.setPath("C:/Users/WBZ/Desktop/r1");
		// 上传内容
		Repository.upload("helloEveryone");
		Repository.upload("goodbye\nimgone");
		Repository.upload("helloEveryone");
		// 通过hash值返回内容
//		System.out.print(Repository.search("7c51603df7dddf74445b9930b068d7ea379a4224"));
	}
}

