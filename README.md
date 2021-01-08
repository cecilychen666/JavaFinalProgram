# JavaFinalProgram-Group16
* 此项目为第16小组的Java期末课程项目<br>
* 【task2--project1文件夹】：这是最终全部完善后的项目代码<br>
* task1文件夹：存放的是第一周提交的作业；其他文件为mac上传时自动生成的，与项目无关<br>

## 课程项目要求
* 命令行工具<br>
* 参考git实现原理，实现blob,tree,commit核心存储结构<br>
* 功能点：<br>
  * 可以提交commit，可以进行“git log”查看commit历史
  * 可以进行“git reset”回滚到指定commit
  * 可创建多分支，可在分支之间切换
  * 时间充足可考虑merge功能(加分)、远程仓库功能(不要求)<br>

## 开发方式
* 每个组长创建一个github仓库，添加组员的push权限
* 永远使用Pull Request来更新主分支
* Commit描述和PR描述尽可能详细
* 使用issue来讨论/记录开发计划、分工及问题/bug<br>

## Git实现原理
* Key-value存储
  * value：Object的内容
  * Key：Object内容的hash<br>
  
* Object的三种类型
  * Blob：文件
    * Blob的Value：文件内容，没有文件名信息
    * Blob的Key：文件内容的hash值
  * Tree：文件夹
    * Tree的Value：1.子文件夹和子文件名称；2.每个子文件Blob key；3.每个子文件夹tree的key；
    * Tree的Key：以上内容的hash值
  * Commit：提交
    * Commit的Value：1.项目根目录tree对象的key；2.前驱commit对象的key；3.代码author；4.代码commiter；5.commit时间戳；6.commit备注/注释；
    * Commit的Key：以上内容的hash值<br>
    
* Git object的存储
  * 每个object存成一个文件
  * 在.git/objects目录下
  * 文件夹名+文件名就是key值
  * Value被编码进了文件内容，暂时不用了解<br>
  
* 分支切换与回滚
  * 本质上是把commit对应的根目录Tree对象恢复成一个文件夹
    * 根据commit key查询得到commit的value
    * 从commit value中解析得到根目录tree的key
    * 恢复(path)：
      * 根据tree的key查询得到value
      * 解析value中的每一条记录，即这个tree对象所代表的文件夹内的子文件与子文件夹名称以及对应的blob/tree key
      * 对于blob，在path中创建文件，命名为相应的文件名，写入blob的value
      * 对于tree，在path中创建文件夹，命名为相应的文件夹名，递归调用恢复(path+文件夹名)
  * 根目录tree恢复成文件夹后，可以直接替换原先工作区的根目录
    * 优化空间：不替换整个根目录，只替换发生变动的子目录/子文件
  * 切换分支/回滚至某个commit后，需要更新HEAD指针<br>
  
* 命令行交互
  * 两种选择
    * Scanner接收用户指令
    * 通过main函数命令行参数String[] args接收用户指令<br>



  
## TASK

### Task 1<br>
#### Function 1:实现key-value储存方式。<br>
  * 实现给定value向储存中添加对应key-value功能
  * 实现给定key,查找得到对应的value值的功能
  * 封装成class对外提供接口
  * 单元测试<br>
  
#### Function 2:将一个文件夹转化为key-value<br>
  * 给定一个文件夹目录，将其转化为若干tree和blob
    * 深度优先遍历此目录
        * 遇到子文件就转化为blob并保存
        * 遇到子文件就递归调用其内部的子文件最后构造tree并保存
  * 使用任务1的接口--hash表
  * 单元测试<br>

### Task 2<br>
* 完善、优化已有的代码
  * 最终小组内每位同学都需要贡献至少3次commit，可以是完善注释、设计文档和单元测试（使用代码来自动化实现测试用例的生成以及验证测试结果是否正确）

* 实现Commit
  * 给定一个工作区目录，生成对应的blob和tree(上周已完成)以及commit
  * 写代码之前先理清思路，写设计文档
  * 提示：
    * 需要存储指向当前最新commit的HEAD指针
    * 每次新生成一个commit前，需要把根目录的tree key与已有的最新commit的tree key进行比较，发现不相同时（即文件发生了变动）才添加这个commit
    
### Task 3
* 至少完成：
  * 前两周的阶段性任务(key-value、Blob、Tree、Commit)
  * 对后续任务(分支、回滚、命令行交互)如何实现的设计文档<br>
* 尽量多做：
  * 后续任务(分支、回滚、命令行交互)的代码实现
  
## Idea
### 1. 类<br>
* Repository<br>
  *  setPath():设置仓库路径
  * repository(): 根据给定路径和仓库名创建仓库：
  * writeFile(): 将指定内容写入文件：
  * renameFile(): 文件重命名：
  * search():根据哈希值返回对应内容<br>

* keyvalueobject<br>
  * 是⼀个键值对的类，其中包含了对键、值的初始化；键、值的赋值以及读取的方法；
  * content():被子类继承并复写，得到对应类文件的内容
  * genKey(File file)：生成文件的哈希值
  * genKey(String content)：生成content的哈希值，调用hash类里的sha1方法
  * 它是下述三种方法的⽗类：<br>
    * blob类<br>
      * blob类的实例，就是在检索目录的过程中发现的txt⽂件；<br>
      * 它的value是txt文件存储的内容，key是由value生成的哈希值；<br>
    * tree类<br>
      * tree类的实例，就是在检索目录的过程中发现的子文件夹；<br>
      * 它的value是该⽂件夹下的目录（包括：对象属性（tree/blob）、对象的key、对象的内容）；<br>
      * 它的key是由value生成的哈希值；<br>
    * commit类<br>
      * commit类的实例，本质就是根目录tree的信息；<br>
      * 它的value就是根部的tree的目录+上⼀次commit的哈希值；<br>
      * 它的key就是它value的哈希值<br>
      
* hash<br>
⽤于产生哈希值。其中包含两个方法:<br>
  * SHA1Checksum(InputStream is): 对blob（txt文件）的哈希方法: 
  * sha1(String data): 对tree（子文件夹）的哈希方法 <br>
  
* formTree<br>
  * form_tree(String path, String directoryContents)：参数：路径；目录（文件夹用的）
  * 深度优先遍历，供做测试的test类调用。按照深度优先遍历的原则，如果在当前目录中扫描到了文件，那么就调用blob类给该文件生成对应的key-value;<br>
  * 如果在当前目录中扫描到了文件夹，那么一方面调用tree类给该文件生成对应的key，另一方面文件夹的value与它目录下包含的内容息息相关，所以递归调用dfs深度遍历。<br>
  
* head<br>
head的实例其实也是⼀个txt文件，用以存放最新⼀次commit的哈希值；<br>
  * head()：创建一个head文件，存放最新的commit哈希值
        创建一个oldhead文件，存放上一次commit哈希值
  * updateOldHead() ：更新oldhead里面的哈希
  * writeHead（）：把最新的commit的哈希值写进去
  * getHead() ：读出head
  * getOldHead(): 读出oldhead
  * recordCommit(): 记录commit历史，文件名是第i次commit的哈希，内容是i-1次的哈希<br>
  
* copy
将历史版本存入history
  * copyDir(String fromDir,String toDir) :从前一个目录copy到后一个目录；从当前仓库到history
  * copyFile(String fromFile,String toFile)：被copyDir调用<br>

* branch
构建多个分支 ，head指针可以随时选择指向任何一个分支
  * initBranch()：初始化分支仓库，相当于是存储每一个分支指向的当前版本的哈希值,第一次commit会调用该方法。
  * branch(): 新建默认分支（master）,会在分支仓库里以文件的形式存储这个分支当前版本的哈希值，文件名就是分支名
  * branch（name）:新建给定名字的分支，会在分支仓库里以文件的形式存储这个分支当前版本的哈希值，文件名就是分支名
  * chooseBranch(name)：更改工作分支，将head和oldhead改为name分支的当前版本和上一个版本的哈希值，将本地仓库中的文件改为name分支的当前版本<br>
  
* rollback
实现版本的回滚，将仓库里面的内容恢复到上一个版本，同时对head和oldhead内容的更改
  * deleteFile(file)：清空工作区的内容
  * rollBack(): 首先在oldhead读取上一个版本的哈希值，调用deleteFile(file)清空工作区的内容，在history里找到这个哈希值对应的版本内容，调用copy.copyDir()方法复制到工作区内
  * 读取recordCommit()方法生成的文件，将head里存储的哈希值改成oldhead里的哈希值，将oldhead改成oldhead对应的上一次的哈希值<br>



### 2. 测试逻辑<br>

* commit:
  * 程序以测试类test作为入口，给定测试文件所在目录，然后调用dfs，开始深度优先搜索，通过递归将路径下的文件内部文件与文件夹排布的逻辑顺序整理出来。这个过程中调用了上述的tree、blob、
  hash；<br>
  * 通过遍历后，程序自动调用head的哈希值，与当前遍历完成的新哈希值做比较，如果一致，则提示用户文件无改动，不需要新的commit;如果不一致，则提示用户文件已经改动，输⼊“commit"以生成新的commit；<br>
  
* rollback
  * 程序以测试类test作为入口，通过输入指令调用rollback()方法；
  * 读取oldhead中的内容，得到上一个版本的哈希值；
  * 清空工作区中的所有内容，在history版本库文件夹中找到之前版本哈希值对应的工作区内容，复制到工作区中，实现了工作区中内容的回滚；
  * 更改head和oldhead中的内容，分别改为上次版本的哈希值和上上次版本的哈希值，实现了head指针的回滚。



