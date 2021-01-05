# JavaFinalProgram-Group16
此项目为第16小组的Java期末课程项目
<br>
<br>
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
实现key-value储存方式。<br>
  * 实现给定value向储存中添加对应key-value功能
  * 实现给定key,查找得到对应的value值的功能
  * 封装成class对外提供接口
  * 单元测试<br>
### Task 2<br>
将一个文件夹转化为key-value<br>
  * 给定一个文件夹目录，将其转化为若干tree和blob
    * 深度优先遍历此目录
        * 遇到子文件就转化为blob并保存
        * 遇到子文件就递归调用其内部的子文件最后构造tree并保存
  * 使用任务1的接口--hash表
  * 单元测试<br>

### Task 3<br>
* 完善、优化已有的代码
  * 最终小组内每位同学都需要贡献至少3次commit，可以是完善注释、设计文档和单元测试（使用代码来自动化实现测试用例的生成以及验证测试结果是否正确）

* 实现Commit
  * 给定一个工作区目录，生成对应的blob和tree(上周已完成)以及commit
  * 写代码之前先理清思路，写设计文档
  * 提示：
    * 需要存储指向当前最新commit的HEAD指针
    * 每次新生成一个commit前，需要把根目录的tree key与已有的最新commit的tree key进行比较，发现不相同时（即文件发生了变动）才添加这个commit


### Task 4
* 至少完成：
  * 前两周的阶段性任务(key-value、Blob、Tree、Commit)
  * 对后续任务(分支、回滚、命令行交互)如何实现的设计文档<br>
* 尽量多做：
  * 后续任务(分支、回滚、命令行交互)的代码实现
  
## Idea
### 1. 类<br>
* keyvalueobject<br>
是⼀个键值对的类，其中包含了对键、值的初始化；键、值的赋值以及读取的方法；它是下述三种方法的⽗类：<br>
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
  * 对blob（txt文件）的哈希方法
  * 对tree（子文件夹）的哈希方法。<br>
* dfs<br>
  * 深度优先遍历，供做测试的test类调⽤。按照深度优先遍历的原则，如果在当前目录中扫描到了文件，那么就调用blob类给该文件生成对应的key-value;<br>
  * 如果在当前目录中扫描到了文件夹，那么一方面调用tree类给该文件生成对应的key，另一方面文件夹的value与它目录下包含的内容息息相关，所以递归调用dfs深度遍历。<br>
* head<br>
head的实例其实也是⼀个txt文件，用以存放最新⼀次commit的哈希值；<br>
### 2. 工作逻辑<br>
程序以测试类test作为入口，给定测试文件所在目录，然后调用dfs，开始深度优先搜索，通过递归将路径下的文件内部文件与文件夹排布的逻辑顺序整理出来。这个过程中调用了上述的tree、blob、
hash；<br>
通过遍历后，程序自动调用head的哈希值，与当前遍历完成的新哈希值做比较，如果一致，则提示用户文件无改动，不需要新的commit;如果不一致，则提示用户文件已经改动，输⼊“commit"以生成新的commit；


