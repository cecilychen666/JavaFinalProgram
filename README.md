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
  * Key：Object内容的hash
* Object的三种类型
  * Blob：文件
    * Blob的Value：文件内容，没有文件名信息
    * Blob的Key：文件内容的hash值
  * Tree：文件夹
    * Tree的Value：1.子文件夹和子文件名称；2.每个子文件Blob key；3.每个子文件夹tree的key；
    * Tree的Key：以上内容的hash值
  * Commit：提交
    * Commit的Value：1.项目根目录tree对象的key；2.前驱commit对象的key；3.代码author；4.代码commiter；5.commit时间戳；6.commit备注/注释；
    * Commit的Key：以上内容的hash值
* Git object的存储
  * 每个object存成一个文件
  * 在.git/objects目录下
  * 文件夹名+文件名就是key值
  * Value被编码进了文件内容，暂时不用了解<br>
  
## 每周日志

#### 第一周日志
2020年12月7日 第一周日志 author：陈诗<br>
* 1.完成任务1，实现了key-value储存方式。<br>
  * 实现了给定value向储存中添加对应key-value功能
  * 实现了给定key,查找得到对应的value值的功能
  * 封装成class对外提供接口
  * 单元测试<br>
* 2.完成任务2，将一个文件夹转化为key-value<br>
  * 给定一个文件夹目录，将其转化为若干tree和blob
    * 深度优先遍历此目录
        * 遇到子文件就转化为blob并保存
        * 遇到子文件就递归调用其内部的子文件最后构造tree并保存
  * 使用任务1的接口--hash表
  * 单元测试<br>
#### 第二周日志

2020年12月12日 第二周日志 author：韩祎然<br>

1.完成了对第一周功能二的分类和优化，使blob、tree和commit成为keyvalueobject的子类，并向Github仓库做了提交；<br>

2.向GitHub仓库提交了commit的功能设计文档。<br>
