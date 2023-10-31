// https://www.bilibili.com/video/BV1pX4y1S7Dq

// 1. git是开源的分布式版本控制系统
版本控制 -> 记录你在什么时候写了什么代码
分布式 -> 区别于集中式 
       -> 集中式是只有一个档案馆
       -> 分布式是可以每一个人有个档案馆 -> 你自己管理自己的版本需要的时候可以拿着自己的版本和别人的做一个合并

两个好处 -> ① 协同工作
        -> ② 出事故之后可以精准的找人背锅

// 2. gitee, github, gitlab, git的区别
git的代码上传到gitee, github, gitlab等代码托管平台

// 3. 使用git
// 3.1 身份配置 -> 这个配置只是说明性的, 你想配啥就可以配啥 -> 这跟登录注册还是有区别的
git config --global user.name "baiyao"
git config --global user.email 18303620306@163.com

// 3.2 新建项目
新建文件夹 -> 在文件夹里面右键 -> Git Bash Here -> git init -> 不需要联网, 接着你就可以随便使用任何你想使用的git命令了

// 3.3 克隆项目
git clone https://github.com/....(项目的地址)

// 3.4 修改我们的文件, 创建我们的版本
当咱们创建好仓库之后, 咱们会赋予这个仓库每一个文件都有的状态.

如果是新建的仓库, 那么里面的每个文件都是未被跟踪的. 当你生成一个版本后, 未被跟踪的文件是不会在这个版本里的. 他之前的状态以及他之前做的修改是不能被追溯的. 现在可以通过'git add <文件或者目录name>'来跟踪文件或者目录. 一般来说, 如果一个文件或者目录被跟踪, 那么它这辈子都是会被跟踪的. 如果不想它被跟踪的话, 可以通过'git rm <name>'删掉, 亦或者使用'git rm --cache <name>'让它保留在我们的目录里面, 但是不被跟踪.

修改文件, 接着可以使用'git add <file-name>'将其状态设置成缓存状态. 取消设置成缓存状态, 'git reset HEAD <file-name>'. 如果说已经设置成缓存状态的话, 接着就可以使用'git commmit'命令来提交此次修改.

总结, 文件的四个状态:
    未跟踪 -> 未修改 -> 已修改 -> 暂存

    未跟踪 -> (git add <name>) -> 暂存

    未跟踪未修改 -> (git commit) -> 暂存(形成了一个新的版本, 文件此时已经变成了未修改已跟踪状态) -> (git rm <name>) -> 将其变成未跟踪状态

// 3.5 'git commit'命令补充
输入'git commit' -> 回车(会进入编辑器) -> i -> 输入此次要提交的信息 -> ESC, :wq -> 版本提交完成

或者

`git commit -m '本次修改的信息' `

假设现在本不想提交了 -> 'git reset head~ --soft' -> 这个操作不能撤销第一次提交, 可能咱们提交了第二第三次, 但是第一次提交是不能被撤销的

// 3.6 查看文件状态 -> 想看哪个文件有修改但是没有暂存? 哪个文件暂存了?
'git status' -> 能够查看哪个文件是修改了但是未被暂存(终端显示是红色) -> 'git add <name>' -> 之后查看就是绿色了, 表示被修改并且已经被暂存了 -> 接着可以`git commit -m '巴拉巴拉'`提交一下, 让其成为一个新的版本 -> 接着'git status', 就能看到提示: On branch master nothing to commit, working tree clean -> 表示没有被修改未提交的了.

// 3.7 想查看更细致的东西, 比如说想看看这个文件哪里被修改了, 第几行第几个字母
'git diff'

// 3.8 查看历史提交
'git log'

commit后面的一串字母数字组合就是历史提交的哈希值 -> 唯一的 -> 可以通过这个哈希值来找到某次的提交
Author -> 后面是提交作者
DAte -> 后面是提交时间, 包括后面还有咱们提交时的一些信息

'git log --pretty' -> 美化输出 -> 'git log --pretty=oneline' -> 把每次提交的信息都变成一行
                              -> 'git log --pretty=format:"%h-%an, %ar:%s"'自定义格式来让显示信息来符合我们的格式
                                                        -> %h 简化哈希
                                                        -> %an 作者名字
                                                        -> %ar 修订日期(距今)
                                                        -> %ad 修订日期
                                                        -> %s  提交说明  

'git log --graph' -> 让历史提交信息以分支图形化的方式呈现


// 4. 远程仓库 / remote -> 讲解远程仓库的使用
github为例 -> '+' -> 'New repository' -> Owner(baiyao)/Repository name(test) -> 设置公开或者私有(Public或者Private) -> Create repository 
           -> HTTPS或者SSH复制远程仓库的链接地址 https://github.com/baiyao/test.git


链接远程的仓库 -> git remote add origin(远程仓库的名字, 一般就叫这个) 'https://github.com/baiyao/test.git'
              -> git remote -> 显示: remote -> 可以通过`git remote rename origin 'new_name'`来修改远程仓库的名字 -> 'git remote'查看新名字

              想把本地代码推送到远程的仓库?

              -> git push origin(选择的一个远程仓库) master(将本地的master分支给推送上去) -> 接着会让输入用户名和密码, 但是github已经禁止制种方式了 
              
              -> github头像 -> Settings -> 尾部Developer settings -> Personal access tokens -> Tokens(classic) 
              
              -> 需要新建令牌, 用令牌当密码'Generate new token' -> 'Generate new token(classic - 普通的, 经典的)'
              -> Note: login(随便写) -> Expiration(有效期): 30days -> 勾选repo(就是把有关仓库的权限都给打开) -> Generate token

              -> 复制生成的令牌

              -> git push origin master 
              'Username for https://github.com: baiyao'
              'Password for https://baiyao@github.com: (此时粘贴复制的令牌)' -> 接着就上传成功了

              -> 令牌鉴权不方便, 可以通过ssh方式进行鉴权 
              -> cd ~/.ssh (这是进入用户的ssh目录) -> 进来之后 'ssh-keygen -t rsa -b 4096 -C "个人邮箱"'
                                                 -> -t指定密钥生成算法, -b指定大小, -C添加评论-github推荐填写个人邮箱
                                                 -> test -> ls -> test(私钥) test.pub(公钥) -> 查看公钥'cat test.pub' 复制公钥
                                                 -> github头像 -> Settings -> SSH and GPG keys -> SSH keys 的 New SSH key
                                                 -> key:(粘贴你复制的公钥内容) Title: test(随便起名字) -> add SSH key 


                                                 -> 项目地址处 -> Code -> SSH -> 复制远程仓库地址
                                                 -> git clone <刚刚复制的地址>

// 5. branch -> 分支概念
咱们每次提交生成新版本的时候都会生成一个新的提交对象

(提交对象1-914cb) -> (提交对象2-31ac3) -> (提交对象3-ju9u6) 

每一个提交对象都有一个独一无二的哈希值914cb, 31ac3, ju9u6等等

其实分支就是包含这个哈希值的一个文件, 或者可以理解为指向一个提交对象的指针, 可以说是咱们能在一个提交对象上新建多个分支,
因为分支是包含提交对象哈希值的一个文件, 所以想加多少就可以加多少. 

咱们最开始在初始化本地仓库时就新建了一个master分支, 而咱们每次的提交操作都是在这个master分支上进行的, 当咱们提交的时候, 分支也会跟着提交对象一起
向前移动

    master      
      ↓
    首次提交对象 -> 第二次提交对象

                      master      
                        ↓
    首次提交对象 -> 第二次提交对象  

    // 接着咱们在master上继续做了修改, 然后提交一个新的提交对象

                                      master      
                                        ↓
    首次提交对象 -> 第二次提交对象 -> 第三次提交对象


    // 接着咱们在第二次提交对象那儿新建的分支上做修改并提交, 那么第二次提交对象就会分叉, 这两个叉就是两个分支


// 6. 为什么新建这么多分支? 一个分支不好吗?
feature分支(开发新特性) ->(将几个feature分支一块儿合并到develop分支)-> develop分支'手机不同的feature分支并对其进行测试, 没问题后就可以发布了, 接着新建release分支' -> release分支'功能验证没问题就可以把release分支合并到master分支' -> master分支
                  -> hot fixes分支'修复bug用'

// 7. 如何操作分支
在操作分支之前, 咱们得知道咱们在哪个分支上 -> 'git log' -> commit 0dd28b...(历史提交的哈希) (HEAD -> master, origin/master)
    -> 'HEAD -> master' -> 表明咱们在master分支上, HEAD表示现在所处的分之就在master分支上
    -> 'origin/master' -> 咱们之前添加过origin远程仓库而这个远程仓库里面也有一个master

-> 'git status' -> On branch master -> 表示现在就在master分支
-> 'git branch --list' -> * master -> 前面带星的就是我们当前所处的分支, 由于现在只有一个master分支, 所以现在就在master分支上

// 8. 创建分支
'git branch feature1' -> 'git branch --list' -> feature1 
                                                * master
                                            -> 切换分支'git checkout feature1' -> -> 'git branch --list' -> * feature1 
                                                                                                            master
                                            -> vi code.txt 修改代码 -> 'git commit -am '巴拉巴拉''(-a将未暂存的文件都提交了)

                                            -> git checkout master(切换回master分支) -> 新建分支并切到这个新建分支上'git checkout -b feature2'

                                            -> vi code.txt 修改代码 -> 'git commit -am '巴拉巴拉''

                                            -> 查看所有分支的提交'git log --all', 'git log --all --graph'

                                            -> git checkout master(切换回master分支)


// 9. 合并分支
让master分支与feature1分支合并 -> 'git merge feature1'
                            -> 'git merge feature1' 继续将master分支与feature2分支合并
                            -> 发现会报冲突错误, 因为feature1分支与feature2分支修改了同一个地方, 会显示冲突文件
                            -> vim 冲突文件

                                <<<<<<< HEAD
                                Laotie          // 这里就代表咱们当前分支对这个文件进行的修改
                                =======
                                Laotie666       // 这下面就代表想要合并进来的分支进行的修改
                                laotiefeature2

                                >>>>>>> feature2


                                多余的东西都删掉, 保留下面的

                                Laotie666      
                                laotiefeature2

                            -> git add code.txt -> git commit -m '' -> git log --all --graph

                            -> git push -u origin master -> 将分支推送到远程 -> 以后想推送一样的话, 直接git push就行, 因为前面push的时候加了-u
                            -> git push origin feature1 -> 可以将feature1分支推送上去

                            -> git fetch 拉取远程在本地没有的分支


