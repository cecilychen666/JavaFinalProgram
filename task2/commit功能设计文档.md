## JAVA第16小组关于第二周任务——实现commit功能的设计说明

*2020年12月12日	韩祎然*

**1.关于存储方式**

​	由于blob、tree、commit三个类型本质上都是一致的，准备采用序列化的方式分别存储各个类型，需要读取时就用反序列化的方法。



**2.关于commit和HEAD指针**

* 个人认为commit B的本质就是把根目录Tree的信息提取出来，再加上前一次的commit A的类型、哈希值以及名称，做一次哈希。<u>*——功能1:commit的哈希*</u>
  * **commit的Value：**1.根目录tree对象的key；2.前驱commit对象的key；3.代码author；4.代码commiter；5.commit时间戳；commit备注和注释；
  * **commit的Key：**以上内容的hash值；

* 至于要不要生成一个新commit ，则是在commit类里先调用dfs和hash两个类，比较一下根目录Tree的哈希值是否变化，如果变化了，那就生成一个新的commit。 <u>*——功能2:再次扫描文件夹，比较先后两次哈希值*</u>

* HEAD指针准备指向最新commit的Key，也就是hash值，所以准备做一个指向文件的HEAD指针，能指向最新commit序列化后的.der文件。*<u>——功能3:HEAD指针的指向内容以及更新</u>*



*Ps：目前我正在做的是把上周任务做一次修改和class分类，准备学习其他小组的优点将blob、tree、commit统一成一个Object大类下的三种类型*