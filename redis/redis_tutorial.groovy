课程地址: https://www.bilibili.com/video/BV1cr4y1671t?p=1&vd_source=badfee45524e073e1fe3740bd3ae835b

ReDiS -> Remote Dictionary Server -> 远程词典服务器 -> 由key拿value, 就是查字典的方式

1. redis -> 存储键值对儿 -> 键值数据库 -> NoSql数据库
         -> key: 1001 -> 用来存储用户id
         -> value: jsonObject -> 用来存储用户信息 -> { 
                                                        "id": 1001,
                                                        "name": "张三",
                                                        "age": 21
                                                    } 

2. SQL与NoSql
SQL -> 关系型数据库 -> structured query language -> 国内简写SQL: 瑟扣 -> 国外简写SEQL: sei quo

NoSql -> 非关系型数据库

3. 结构化
这个说的其实就是建表语句规则, 比如定义一条数据中有哪些字段, 每个字段类型以及是否为主键, 是否非负

结构化的问题是, 你建表后, 存的数据体量大的情况下, 更改表结构会影响到业务 -> 一般情况下是建表之后尽量不建议修改表结构信息

4. 非结构化存储的几种形式
    1) 键值类型(Redis): key-value形式存储数据
    2) 文档类型(MongoDB) -> 比如json -> { "id": 1001, "name": "张三", "age": 21 }
    3) Graph图类型(Neo4j) -> 节点与线组成, 节点是对象, 线代表节点间联系
    4) 列类型(Hbase)

5. 对象之间的关系
    1) SQL -> 使用外键存储对象间关系
        tb_user                     tb_item
        id    name    age           id    title    price
        1     张三    18            10    荣耀6     4999
        2     李四    20            20    小米11    3999

        -> 使用另一张表, 存储表数据与表数据之间的关系 -> 使用外键
        tb_order
        id    user_id    item_id
        1     1          10
        2     1          20
    2) NoSql -> 没有专门的存储对象间关系(也就是说, 数据库没有专门帮你维护数据间的关系, 需要你写程序自己维护) 
             -> 比较常见的是, 使用json形式之间存储数据, 某个人下了那些订单, 每个订单里面商品信息是啥
        {
            "id": 1,
            "name": "张三",
            "orders": [
                {
                    "id": 1,
                    "item": { "id": 10, "title": "荣耀6", "price": 4999}
                },
                {
                    "id": 2,
                    "item": { "id": 20, "title": "小米11", "price": 3999}
                }
            ]
        }

6. SQL与NoSQL取值查询方式
    1) SQL -> 统一使用sql语句 -> select
                                    id,
                                    name,
                                    age
                                from tb_user 
                                where id = 1;

    2) NoSQL -> 不统一 -> 常见三种
        Redis -> get user:1
        MongoDB -> db.users.find({_id: 1}) -> 基于函数式的调用
        elasticsearch -> GET http://localhost:9200/users/1 -> 基于请求的形式调用 

7. SQL与NoSQL在事务上的不同 -> A(原子性), C(一致性), I(隔离性), D(持久性)
    1) SQL -> 所有的关系型数据库底层帮助维护事务 -> 所有的关系型数据库都是满足ACID的

    2) NoSQL -> 要不没有事务, 要不不能满足强一致性, 只能满足基本的一致性

    如果说你对数据安全性, 一致性要求比较高就应该关系型数据库而不是非关系型数据库

8. 总结SQL与NoSQL数据库的差异
    SQL ->  数据是结构化的;
            数据与数据之间通过外键表示关系; 
            通过SQL查询数据; 
            满足事务ACID;
            存储数据也用缓存但是主要用磁盘存数据;
            数据库不能天然支持水平扩展, 想扩展得依赖于第三方组件, 这势必会影响性能(主从架构只是提升了读写性能, 并不能提升数据容量, 因为主从的数据一样, 想提升查询性能就得在单机上做文章, 优化单机性能)

    NoSQL -> 数据是非结构化的; 
             数据与数据之间是没有关系的; 
             不能通过sql方式查询;
             事务方面满足BASE, 或者没事务, 或者只能满足基本的一致性或者说最终的一致性, 存储数据用内存(查询就是快);
             天然支持水平扩展, 通过加机器来提升存储容量和计算性能, 它会将数据key哈希之后确定分布在哪一台机子上 

9. 两者使用场景
    SQL -> 存储数据结构固定的数据, 对安全性和一致性要求高的

    NoSQL -> 数据结构不固定, 对安全性和一致性要求不高的, 但是对性能要求高的

10. Redis的特征
    1) 键值型 -> value类型String、List、Set、Zset、Hash.
    2) 单线程, 是线程安全的, 所有命令串行执行, 不会说出现某个命令执行的时候, 其他命令的任务插进来这种不安全的情况, 所以说它的命令是原子性的. 
       注意, 6.0版本多线程只是在处理网络请求的时候会有, 核心的命令执行还是串行单线程的
    3) 为什么他是单线程但是性能却还是那么好呢(低延迟, 速度快)? 
        相较于数据存在磁盘上的SQL数据库来说, Redis这个NoSQL数据库的数据是存在内存中的, 这效率肯定大大提升 -> 这是这三个理由中最核心的理由
        另外, 虽然是单线程但是它基于IO多路复用, 效率高
        最后呢, redis是C写的, 而且它的源码编写的非常优秀规范, 标杆级别的
    4) 由于数据存在内存中很危险, 所以增加了持久化功能, 定期将数据持久化
    5) 主从集群 -> 从节点备份主节点数据, 那么主节点宕机, 可以直接从从节点读取数据, 这是一种安全策略
               -> 主从支持读写分离, 这可以大大增加读写效率

        分片集群 -> 将数据拆分 -> 比如说, 1TB数据, 我把它拆成n份儿来存到不同的节点上去 -> 这样就提升了存储的上限 -> 属于水平扩展
    6) 支持多语言客户端 -> 就是你各种各样的语言我都有相应的客户端来让你操作redis存取数据

    企业不用它没理由啊 -> 功能丰富, 性能好, 支持数据持久化(安全性), 另外能轻易水平扩展, 任何语言都能用
       
11. redis的安装
    linux安装gcc依赖 -> 由于redis基于C编写 -> yum install -y gcc tcl
    上传安装包并解压 -> 
        [baiyao@zoo1 redis-6.2.6]$ sudo mkdir /usr/local/src/redis
        [baiyao@zoo1 redis-6.2.6]$ sudo tar -zxvf ./redis-6.2.6.tar.gz -C /usr/local/src/redis/
        // 进入主目录, 编译并安装
        [baiyao@zoo1 redis-6.2.6]$ sudo cd /usr/local/src/redis/redis-6.2.6/ && sudo make && sudo make install
        // 默认的安装路径是 /usr/local/bin, 所以安装完成之后检查下有没有安装成功
        [baiyao@zoo1 redis-6.2.6]$ cd /usr/local/bin/ && ll -l ./
        总用量 18904
        -rwxr-xr-x. 1 root root 4829536 7月  16 10:34 redis-benchmark
        lrwxrwxrwx. 1 root root      12 7月  16 10:34 redis-check-aof -> redis-server
        lrwxrwxrwx. 1 root root      12 7月  16 10:34 redis-check-rdb -> redis-server
        -rwxr-xr-x. 1 root root 5003816 7月  16 10:34 redis-cli                             -> redis命令行客户端
        lrwxrwxrwx. 1 root root      12 7月  16 10:34 redis-sentinel -> redis-server        -> redis的哨兵启动脚本
        -rwxr-xr-x. 1 root root 9518936 7月  16 10:34 redis-server                          -> 启动redis服务的脚本

        redis-server已经加入环境变量了, 可以直接运行

        [baiyao@zoo1 bin]$ redis-server 
        57703:C 16 Jul 2023 10:40:58.345 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
        57703:C 16 Jul 2023 10:40:58.345 # Redis version=6.2.6, bits=64, commit=00000000, modified=0, pid=57703, just started
        57703:C 16 Jul 2023 10:40:58.345 # Warning: no config file specified, using the default config. In order to specify a config file use redis-server /path/to/redis.conf
        57703:M 16 Jul 2023 10:40:58.347 * monotonic clock: POSIX clock_gettime
                        _._                                                  
                _.-``__ ''-._                                             
            _.-``    `.  `_.  ''-._           Redis 6.2.6 (00000000/0) 64 bit
        .-`` .-```.  ```\/    _.,_ ''-._                                  
        (    '      ,       .-`  | `,    )     Running in standalone mode
        |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
        |    `-._   `._    /     _.-'    |     PID: 57703
        `-._    `-._  `-./  _.-'    _.-'                                   
        |`-._`-._    `-.__.-'    _.-'_.-'|                                  
        |    `-._`-._        _.-'_.-'    |           https://redis.io       
        `-._    `-._`-.__.-'_.-'    _.-'                                   
        |`-._`-._    `-.__.-'    _.-'_.-'|                                  
        |    `-._`-._        _.-'_.-'    |                                  
        `-._    `-._`-.__.-'_.-'    _.-'                                   
            `-._    `-.__.-'    _.-'                                       
                `-._        _.-'                                           
                    `-.__.-'                                               

        57703:M 16 Jul 2023 10:40:58.350 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
        57703:M 16 Jul 2023 10:40:58.350 # Server initialized
        57703:M 16 Jul 2023 10:40:58.350 # WARNING overcommit_memory is set to 0! Background save may fail under low memory condition. To fix this issue add 'vm.overcommit_memory = 1' to /etc/sysctl.conf and then reboot or run the command 'sysctl vm.overcommit_memory=1' for this to take effect.
        57703:M 16 Jul 2023 10:40:58.350 * Ready to accept connections

        -> 像这种就是直接在前台启动了, 卡在了界面上, 你要是使用ctrl + z, 那么redis服务就停止了, 这种启动方式不友好

    -> 修改配置文件, 通过配置文件的指定来实现后台启动
        [baiyao@zoo1 bin]$ cd ../src/redis/redis-6.2.6/
        [baiyao@zoo1 redis-6.2.6]$ sudo cp ./redis.conf ./redis.conf.bak
        [baiyao@zoo1 redis-6.2.6]$ sudo vim ./redis.conf
            // 监听地址, 默认是127.0.0.1 -> 也就是本机, 只有收到你本机的访问请求才响应, 现在改成任意ip都可访问, 生产环境不能这样 
            bind 0.0.0.0
            // 守护进程
            daemonize(笛闷耐Z) yes
            // 密码, 任意ip访问需密码
            requirepass 111111.a
            // 监听端口
            port 6379
            // 工作目录, 启动redis-server服务的目录, '.'代表当前, redis-server在哪儿启动的, 它的工作目录就会在哪儿
            dir .
            // 数据库数量默认是16个, 编号是0~15, 这里是设的1, 代表只用一个库
            databases 1
            // redis能使用的最大内存
            maxmemory 512mb
            // 日志文件, 默认是空 -> 也就是不记录日志, 这里指定文件名, 路径呢就是你的'.' -> 工作目录 -> redis-server在哪儿启动的, 它的工作目录就会在哪儿
            logfile "redis.log"
        [baiyao@zoo1 redis-6.2.6]$ su -
        密码：
        上一次登录：四 7月 13 06:29:08 CST 2023pts/1 上
        [root@zoo1 ~]# cd /usr/local/src/redis/redis-6.2.6/ && redis-server ./redis.conf
        [root@zoo1 redis-6.2.6]# ls -l
        -rw-rw-r--.  1 root root 93798 7月  16 11:16 redis.conf
        -rw-r--r--.  1 root root 93724 7月  16 10:56 redis.conf.bak
        -rw-r--r--.  1 root root   541 7月  16 11:20 redis.log

        [root@zoo1 redis-6.2.6]# ps -ef | grep redis
        baiyao    57703  53063  0 10:40 pts/0    00:00:01 redis-server *:6379

        可以通过kill -9 57703来关闭redis 或者 -> 
        redis-cli -u 111111.a shutdown

12. redis的开机自启
    -> 需要编写一个系统服务文件来把redis加入到系统服务中, 
        [root@zoo1 redis-6.2.6]# vim /etc/systemd/system/redis.service
        [Unit]
        Description=redis-server
        After=network.target

        [Service]
        Type=forking
        ExecStart=/usr/local/bin/redis-server /usr/local/src/redis/redis-6.2.6/redis.conf
        PrivateTmp=true

        [Install]
        WantedBy=multi-user.target
    -> 重新加载系统服务, 接着重新启动redis
        // 被系统管理
        [root@zoo1 redis-6.2.6]# systemctl daemon-reload
        [root@zoo1 redis-6.2.6]# systemctl start redis

        [root@zoo1 redis-6.2.6]# systemctl status redis
            ● redis.service - redis-server
            Loaded: loaded (/etc/systemd/system/redis.service; disabled; vendor preset: disabled)
            Active: active (running) since 日 2023-07-16 11:40:26 CST; 4s ago
            Process: 57866 ExecStart=/usr/local/bin/redis-server /usr/local/src/redis/redis-6.2.6/redis.conf (code=exited, status=0/SUCCESS)
            Main PID: 57867 (redis-server)
            CGroup: /system.slice/redis.service
                    └─57867 /usr/local/bin/redis-server 0.0.0.0:6379

            7月 16 11:40:26 zoo1 systemd[1]: Starting redis-server...
            7月 16 11:40:26 zoo1 systemd[1]: Started redis-server.
        // 可以通过这个停止或者重启redis
        [root@zoo1 redis-6.2.6]# systemctl stop/restart redis

        // 设置开机自启
        [root@zoo1 redis-6.2.6]# systemctl enable redis

13. 怎么跟redis进行交互? -> 也就是怎么使用redis客户端?
    1) 命令行客户端:
        [root@zoo1 redis-6.2.6]# redis-cli -a 111111.a
        Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
        127.0.0.1:6379> 
        [root@zoo1 redis-6.2.6]# redis-cli -h 127.0.0.1 -p 6379
        127.0.0.1:6379> ping
        (error) NOAUTH Authentication required.
        127.0.0.1:6379> 
        [root@zoo1 redis-6.2.6]# redis-cli -h 127.0.0.1 -p 6379 -a 111111.a
        Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
        127.0.0.1:6379> ping
        PONG

        [root@zoo1 redis-6.2.6]# redis-cli -a 111111.a
        Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
        127.0.0.1:6379> 

        // 命令行输入密码不安全, 换种方式
        [root@zoo1 redis-6.2.6]# redis-cli
        127.0.0.1:6379> AUTH 111111.a
        OK

        // 测试往redis里面存一下key-value键值对 -> set key value
        127.0.0.1:6379> set name baiyao
        OK
        127.0.0.1:6379> set age 27
        OK
        127.0.0.1:6379> get name
        "baiyao"
        127.0.0.1:6379> get age
        "27"

        // 0~15号库, 默认是存到了0号库, 怎么切换?
        127.0.0.1:6379> select 1
        OK
        127.0.0.1:6379[1]> set name jrd
        OK
        127.0.0.1:6379[1]> get name
        "jrd"
        127.0.0.1:6379[1]> select 0
        OK
        127.0.0.1:6379> get name
        "baiyao"
        127.0.0.1:6379> 

    2) 图形化界面客户端:
        github.com/uglide/RedisDesktopManager

        github.com/lework/RedisDesktopManager-Windows/releases

        -> 直接下载这个 -> https://github.com/lework/RedisDesktopManager-Windows/releases/download/2021.9.4/rdm-2021.9.4.zip

        是一个.exe文件, 下载下来直接安装就行了

        -> 配置
        连接设置 -> 名字: 随便起 -> 地址: ip -> 密码: 111111.a

    3) 编程客户端: 

14. Redis的常见数据结构
key是string, value类型这里例举八种, 不止这么多, 当然最常见的还是前五种(这5种是基本数据类型): 
    1) 基本类型
    -> String       -> hello
    -> Hash         -> {name: "baiyao", age: 27} -> 本质是一个哈希表
    -> List         -> [A -> B -> C -> C]        -> 本质是一个链表
    -> Set          -> {A, B, C}                 -> 无序集合, 不可重复
    -> SortedSet    -> {A: 1, B: 2, C: 3}        -> 有序集合, 不可重复

    2) 基本类型基础上处理后得到的 -> 特殊类型 -> 实现特殊的功能
    -> GEO          -> {A: (120.3, 30.5)}        -> 地理坐标
    -> BitMap       -> 0110110101110101011       -> 按位存储, 本质是字符串
    -> HyperLog     -> 0110110101110101011       -> 按位存储, 本质是字符串

-> redis的帮助文档 -> https://redis.io/commands/

-> 客户端处查看:
    127.0.0.1:6379> help @generic       -> 这个是查看通用的命令
                         @string
                         @list
                         @set
                         @sorted_set
                         @hash
                         @pubsub  
                         @transactions  
                         @connection  
                         @server  
                         @scripting     
                         @hyperloglog
                         @cluster
                         @geo
                         @stream     

    当然官方文档介绍的会更详细一些

15. Redis的通用命令 -> 对任何数据类型都可以使用的命令 -> 127.0.0.1:6379> help @generic -> 下面例举几个常用的命令
    1) keys -> 查看所有符合指令模板的key
            -> 通过help命令查找keys的用法

            127.0.0.1:6379> help keys

                KEYS pattern
                summary: Find all keys matching the given pattern
                since: 1.0.0
                group: generic

            127.0.0.1:6379> 

            redis> KEYS *name*
            1) "firstname"
            2) "lastname"
            redis> KEYS a??
            1) "age"
            redis> KEYS *
            1) "age"
            2) "firstname"
            3) "lastname"
            redis> 
            
            pattern -> redis内置的通配规则

            h?llo       -> matches hello, hallo and hxllo
            h*llo       -> matches hllo and heeeello
            h[ae]llo    -> matches hello and hallo, but not hillo
            h[^e]llo    -> matches hallo, hbllo, ... but not hello
            h[a-b]llo   -> matches hallo and hbllo


            // 查询所有key
            127.0.0.1:6379> keys *
            1) "name"
            2) "age"
            // 查询以a开头的key
            127.0.0.1:6379> keys a*
            1) "age"

            // 注意, redis的这种通配查询是模糊查询, 当你redis里面存的数据量很大的时候, 这个操作是很费资源的, 
            // 由于是单线程的, 这个模糊查询在数据量大的情况下很可能会阻塞redis服务, 所以在生产环境下是不建议使用keys来进行查询的
    2) del -> 删除指定的key
        127.0.0.1:6379> help del

            DEL key [key ...]       -> 这个[]意思是你能同时指定多个key让我去删除
            summary: Delete a key
            since: 1.0.0
            group: generic

        127.0.0.1:6379> del age
        (integer) 1
        127.0.0.1:6379> keys *
        1) "name"


        127.0.0.1:6379> set age 27
        OK
        127.0.0.1:6379> set sex 男
        OK
        127.0.0.1:6379> set mail 18303620306@163.com
        OK
        127.0.0.1:6379> keys *
        1) "mail"
        2) "name"
        3) "sex"
        4) "age"
        127.0.0.1:6379> del mail name sex
        (integer) 3
        127.0.0.1:6379> keys *
        1) "age"

        [root@zoo1 redis-6.2.6]# redis-cli -h zoo1
        zoo1:6379> auth 111111.a
        OK
        zoo1:6379> 

        // 批量插入键值对
        zoo1:6379> mset name baiyao sex man mail baiyao@123.com
        OK
        zoo1:6379> keys *
        1) "mail"
        2) "name"
        3) "sex"
        4) "age"

        // 删除不存在的key -> 返回值是0 -> 库内不受影响 -> 整数值代表删除的数量
        zoo1:6379> del bucunzai
        (integer) 0
    
    3) exists: 判断key是否存在 -> 1: 存在, 0: 不存在
        zoo1:6379> exists name
        (integer) 1
        zoo1:6379> exists position  -> 这个key是不存在的
        (integer) 0
        zoo1:6379> exists age name sex nihao    -> 传入多个key判断是否存在 -> 只存在3个, nihao这个key是不存在的
        (integer) 3

    4) expire -> 使用它给某个key设定一个有效期, 到期的时候这个key会被自动删除
              -> 由于基于内存, 你不设有效期, 时间一长内存就满了

            zoo1:6379> help expire
                // EXPIRE key seconds
                // summary: Set a key's time to live in seconds
                // since: 1.0.0
                // group: generic

            ##########################################

            zoo1:6379> keys *
            1) "mail"
            2) "name"
            3) "sex"
            4) "age"
            zoo1:6379> set position bigdata
            OK
            zoo1:6379> expire position 10
            (integer) 1
            zoo1:6379> keys * 
            1) "mail"
            2) "name"
            3) "age"
            4) "sex"
            5) "position"
            zoo1:6379> keys * 
            1) "mail"
            2) "name"
            3) "age"
            4) "sex"

            ########################################## 

    5) ttl -> 查看一个key的剩余有效期

        zoo1:6379> ttl name
        (integer) -1                -> -1代表永久有效
        zoo1:6379> expire name 10
        (integer) 1
        zoo1:6379> ttl name
        (integer) 8
        zoo1:6379> ttl name
        (integer) 6
        zoo1:6379> ttl name
        (integer) 5
        zoo1:6379> ttl name
        (integer) 4
        zoo1:6379> ttl name
        (integer) 4
        zoo1:6379> ttl name
        (integer) 2
        zoo1:6379> ttl name
        (integer) 1
        zoo1:6379> ttl name
        (integer) -2                -> -2代表已被删除
        zoo1:6379> ttl name
        (integer) -2
