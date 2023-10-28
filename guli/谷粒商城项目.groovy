https://www.bilibili.com/video/BV1np4y1C7Yf/

// 1. 课程结构 - 3篇
1.1 分布式基础 -> 全栈开发篇 -> 开发一个后台管理系统 -> SpringBoot + MyBatis + SpringCloud + Docker + Vue + ElementUI -> 逆向工程方式开发
1.2 分布式高级 -> 微服务架构篇 -> 实现一整套的商城业务逻辑 -> 商品服务, 购物车, 订单, 结算, 库存, 秒杀 -> 打通在微服务开发方面的技术栈和重难点
                                                       -> SpringBoot + SpringCloud -> 作为基础的配套
                                                       -> 同时搭配SpringCloud Alibaba的注册中心, 配置中心, 流量保护和分布式事务等等
                                                       -> 也就是微服务的高级技术要学, 同时微服务的周边技术也会全套打通
                                                       -> 涉及重难点如: 网关, 远程调用, 链路追踪, 缓存, Session同步方案, 全文检索, 异步编排,
                                                                       线程池, 压力测试, 调优, redis, 分布式锁等等  
1.3 高可用集群 -> 架构师提升篇 -> 搭建K8s集群, 一主两从的方式, 会使用一个应用一站式将所有应用部署到k8s集群里面, 打通整个DevOPS的技术栈, 
    包括整套的CI/CD持续集成和持续部署流程 -> 教大家编写一个具有[参数化构建 + 手工确认模式]的[流水线].

一站式打通J2EE的全套链路

// 2. 5种电商模式
B2B -> Business to Business -> 商家之间建立关系 -> 阿里巴巴
B2C -> Business to Consumer -> 商家与用户 -> 苏宁, 京东, 天猫 -> 咱们这个课程的模式 -> 商城平台销售自营商品给客户
C2B -> Customer to Business -> 消费者对企业 -> 消费者先有需求, 企业再按照需求生产 -> 这种模式不多
C2B -> Customer to Consumer -> 客户把东西放网上卖 -> 淘宝, 闲鱼
O2O -> Online to Offline -> 线上支付, 线下服务 -> 饿了么, 美团, 淘票票

// 3. 项目技术&特色
3.1 前后分离, 基于vue的后台管理系统
3.2 SpringCloud
3.3 应用监控, 限流, 网关, 熔断降级等分布式方案
3.4 分布式事务, 分布式锁
3.5 高并发场景的编码方式, 线程池, 异步编排等的使用
3.6 压力测试, 性能优化
3.7 集群技术的区别与使用
3.8 CI/CD
....

// 4. 技术前置要求
4.1 SpringBoot以及常见整套方案
4.2 SpringCloud
4.3 git, maven
4.4 linux, redis, docker
4.5 html, css, js, vue
4.6 idea项目开发

// 5. 微服务 -> 一种比较流行的架构风格
之前咱们会把所有的sql, 页面, 代码全部写在一个应用里面, 那么可能会导致一个问题, 那就是某一块代码出问题之后, 可能整个单体应用不可用了. 
那么咱们就可以将这样一个单体应用拆成一个个的小模块, 每个服务模块独立运行在自己的进程中(这就资源隔离了), 如果某一个服务出问题的话, 
这样是不影响其他服务的正常运行的. 

比如说订单服务想要使用商品信息, 那么可以通过HTTP API的方式给商品服务发一个请求将商品信息调过来就行了.

// 6. 集群&分布式&节点
集群就是一堆计算机, 在一块儿实现一个业务 -> 比如说京东的用户系统用了10台服务器, 那么这就是个集群.
                                    -> 比如京东的用户系统, 访问压力大的时候, 一台服务器可是顶不住的, 所以就将用户系统部署到了多台服务器中形成集群 

分布式系统是不同的业务分布在不同的地方. -> 每个业务系统可以做集群化部署

总结下:
集群中的每一个服务器就叫节点. -> 分布式的每个节点(业务)都可以做集群部署 
                            -> 集群不一定是分布式的(比如说, 京东的10台服务器统一是用户系统, 这不是分布式).

// 7. 远程调用
分布式系统的各个服务是运行在不同的主机上的, 他们相互之间的信息共享需要进行远程调用, SpringCloud用HTTP+JSON方式实现远程调用.

HTTP以及JSON是各平台兼容, 非常方便, 使用http发请求, 将信息包装成json格式, 可以各平台使用.

// 8. 负载均衡
比如说订单服务需要商品服务的信息, 而商品服务压力大, 部署在了多台机器上, 而订单服务访问任何一台机器都可以拿到结果, 那么此刻就会
根据机器的繁忙程度来选择向哪一台机器发送请求, 目的是为了避免某台机器的太忙或者太闲. 

// 9. 常见负载均衡算法
轮询: 比如商品服务有五台机器, 1号, 2号 ,,,,,5号, 我每次发请求调用的时候, 按照顺序轮着来, 比如从1~5号这种顺序一次次来

最小连接: 确定一个连接数最少的服务器, 我给他发请求, 这样避免个别服务器太闲, 实现处理效率的最大化

散列: 同一个用户或者ip的请求最终会被放在一个服务器中处理

// 10. 服务注册/发现 & 注册中心
比如说你订单服务要去给商品服务发请求, 但是5台机器, 你不知道哪个是下线不能用了, 哪个机器还能用, 那么注册中心就出现了.

只要一有机器上线, 他就会把自己的服务注册到注册中心去, 相当于告诉注册中心, 我上线了.  

你订单服务要给商品服务发请求了就会到注册中心去"注册发现"一下都有哪些可用的商品服务机器, 发现1~4号机器都有, 那么就随机调用一个机器进行请求. 

// 11. 配置中心 -> 集中管理微服务配置信息
一个项目拆分成不同的模块, 也就是说拆分成了很多的微服务, 每个服务都有大量的配置, 而且是每个服务都部署在了多台机器上.

那比如说我A服务要经常变更里面的配置, 要想让所有机器都用到最新配置, 这时候就是需要机器都下线, 然后把新改好的配置部署生效到每台机器上, 
及其数量庞大的时候, 这是很麻烦的. 

这时候可以搞一个配置中心, 让每一个服务都从配置中心自动的获取自己的配置, 如果说你想改某个服务的配置, 你只需要在配置中心改一处, 
接着该服务的所有机器自动从配置中心获取新改的值, 那么这个配置就动态修改了. 

// 12. 服务熔断&服务降级
在微服务架构中, 比如说用户要下单, 那么订单服务需要调用商品服务的商品信息, 而商品服务需要去库存服务中调商品的库存信息, 整个结束以后下单操作才能完成.

如果说库存服务出现故障或者说处理响应缓慢, 那么可以说你库存服务的阻塞或者不可用, 导致了商品服务的阻塞或者不可用, 最终导致了订单服务的阻塞或者说不可用.

一个服务的不可用导致了整个调用链的阻塞与不可用.

高并发场景下, 第一个请求产生的调用链儿, 假设10s阻塞不出请求; 接着第二个, 第三个等等, 更多的请求进来就导致了请求积压, 
全部阻塞在这儿最终就会导致整个服务器的资源耗尽, 一个服务的不可用, 导致整个服务器资源的耗尽, 出现服务器的雪崩现象. 

基于这种现象引入服务的熔断与降级:

服务熔断 -> 服务的断路保护机制
        比如说你100个请求在10s内全失败了(设置阈值), 那么下一个请求再来的话我就直接不去调用这个服务了. 而商品服务可以直接返回一些默认数据, 或者返回库存查出来的结果为null等等. 这样就不会导致请求库存服务无响应导致的请求积压. 

服务降级 -> 针对整体而言
        运维期间, 系统高峰期可以将一些系统的非核心业务进行降级运行 -> 业务停机不处理
                                                              -> 或者简单处理 -> 抛异常, 返null等等

// 13. API网关
项目是前后端分离开发, 前端向后端发送HTTP请求, 这里要先经过一层Gateway网关, 这个网关可以对这些请求进行统一认证(看看哪些请求是合法的, 哪些请求是非法的), 限流(高并发场景下, 可以控制请求以恒定流速流向后台集群, 避免了短时间进来过多的请求流量压垮集群), 服务熔断, 负载均衡等等.

网关类似于商场的安检入口, 能放行的请求就是后台要处理的, 不能放行的请求后台就无需处理了.  

// 14. 项目微服务架构 -> 解释了整个项目的架构与技术组合方案
项目是前后分离开发, 分为
    内网部署 -> 整个后台的服务集群

    外网部署 -> 面向公众, 公众可以访问 -> 前端项目(app端网站, web端网站)
            -> 公众使用客户端完成相应的功能 -> 比如登录, 注册等都需要通过客户端来给后台的服务发送请求 
                                          -> 请求不是直接过来的, 完整的请求流程
                                          -> app端或者web端发送请求 -> 请求先来到Nginx集群 -> Nginx把请求转交给后台服务(非直接转交)
                                                                   -> Nginx把请求先转交给API 网关(使用SpringCloud GateWay)
                                          -> 网关完成的功能: 
                                               -> 动态路由: 根据当前请求动态路由到指定的服务, 看当前请求是想要调用商品服务, 还是说购物车或者检索服务
                                               -> 负载均衡: 假设路由过来的请求是来查询商品服务的, 而咱们的商品服务有三台机子, 你可以选择任意一台, 网关是可以做到负载均衡的调用商品服务的
                                               -> 熔断降级: 当某些服务出现问题了, 咱们可以在网关这里统一做熔断降级处理, 这里使用的是SpringCloud Alibaba提供的Sentinel
                                               -> 认证授权: 请求过来了判断是否合法, 合法了放行
                                               -> 令牌限流: 限制瞬时流量, 假设当前100万个请求瞬间来了, 怕将后台服务压垮, 那么就可以在网管处进行流量控制, 比如放行1万个过去减轻后台处理压力, 一点点的慢慢放行, 这里使用的是SpringCloud Alibaba提供的Sentinel
                                          -> 请求通过网关以后就可以直接交给后台的服务作处理了 -> 后台各个微服务是使用SpringBoot写的
                                                            -> 服务与服务之间会相互调用 -> 比如下订单的时候要调用商品服务来查询商品信息 -> 这里使用的是SpringCloud Feign组件 -> 有些请求需要登录以后才能处理 -> 所以咱们这里还有一个基于oAuth2.0的认证中心, 除了一般的登录还有基于oAuth2.0的社交登录 -> 整个应用里面的安全和权限控制使用的是SpringSecurity
                                项目中的存储服务: 
                                          -> 这些服务要使用一些数据
                                                        保存到缓存 -> 这里用的是Redis集群 -> Sentinel(哨兵集群) + Shard(分片集群)
                                                        数据持久化 -> 这里用的是Mysql集群 -> 可以做读写分离, 也可以做分库分表
                                          -> 服务与服务之间使用的是消息队列(RabbitMQ集群)进行异步解耦, 包括完成分布式事务的最终一致性
                                          -> 有些服务需要全文检索功能 -> 比如检索商品信息 -> 这里用的是ElasticSearch
                                          -> 有些服务在运行期间需要存取一些图片视频等, 这里用的是阿里云的对象存储服务OSS

                                项目上线以后, 为了快速定位项目问题
                                          -> 使用ELK对日志进行处理 -> 使用LogStash来收集业务中的各种日志, 把它存储到ES中, 然后再使用Kibana可视化界面从日志中检索出相关的日志信息, 进而快速定位线上问题的所在

                                分布式系统:
                                          -> 每个服务可能部署在很多台机器上, 服务与服务之间会互相调用, 这需要知道彼此都在哪里
                                          -> 这里推荐是让所有服务都在注册中心进行注册, 那么其他服务就可以从注册中心来发现其他服务的所在位置
                                          -> 这里使用的是SpringCloud Alibaba提供的Nacos来作为服务的注册中心

                                          -> 同样每个服务的配置众多, 要集中管理配置, 实现改一处配置, 服务自动全部修改的效果, 这里需要一个配置中心
                                          -> 这里使用的也是SpringCloud Alibaba提供的Nacos来作为服务的配置中心
                                          -> 所有服务都可以从配置中心来动态获取它的配置

                                          -> 在订单服务调用商品服务, 商品服务调用库存服务这种链式调用的情形中, 有可能某个服务是有问题的, 咱们这里使用SpringCloud提供的(sleuth + Zipkin + Metrics)进行服务链路追踪, (sleuth + Zipkin + Metrics)会将每个服务的信息交给Prometheus来进行聚合分析, 接着由Grafana来进行可视化展示, 通过Prometheus提供的Altermanager来实时的得到一些服务的告警信息, 然后把这些信息以邮件或者手机短信的方式来通知开发运维人员

                                持续集成(CI)/持续部署(CD)
                                          -> 项目发布起来之后, 由于微服务众多, 每一个都打包部署到服务器太麻烦, 有了持续集成, 开发人员可以将修改后的代码提交给Github, 而运维人员可以通过自动化工具Jenkins Pipeline从Github中获取到代码, 并将其打包成Docker镜像, 最终使用Kuberneters来集成整个Docker服务, 然后以Docker容器的方式来运行

// 15. 项目微服务的划分 -> 反应整个谷粒商城里面需要创建的微服务相关的技术组合
项目基于前后分离开发
    前端项目 -> admin-vue -> 面向工作人员开发的和使用的后台管理系统 
               shop-vue  -> 面向公众访问的web网站系统
               app
               小程序
    
    请求会经过网关到达业务的微服务群
    
    API 网关 -> 限流
                鉴权
                熔断降级
                过滤
                路由
                负载均衡

    业务微服务群 -> 商品服务    -> 商品的增删改查, 商品详情, 商品上下架
                   支付服务    -> 支付功能
                   优惠服务    -> 商品的优惠信息
                   用户服务    -> 维护用户个人中心信息, 用户收货地址列表维护
                   仓储服务    -> 每个商品的库存有多少, 存在哪一个仓库
                   秒杀服务    -> 秒杀活动
                   订单服务    -> 订单的增删改查, 用户订单的列表
                   检索服务    -> 商品的复杂检索, 使用的是Elastic Search来作商品的全文检索
                   中央认证服务 -> 登录, 注册, 统一的单点登录功能, 社交登录功能
                   购物车服务  -> 商城购物车的增删改查, 购物车结账
                   ....


                   独立的后台管理系统 -> 想要通过后台来添加一些优惠信息, 或者新增一些商品等等, 这个后台管理系统
                                        也是给其他服务发送相应的请求

    //************************[ 微服务分布式开发期间需要用到的周边配套设置 ]**********************************//

    业务微服务群运行期间所要依赖的第三方服务: 这些服务不是咱们编写的, 第三方写好咱们只需要调用即可
                -> 物流信息的检索
                -> 短信的发送
                -> 金融相关的支付, 退款, 对账等等
                -> 用户的身份认证

    众多的微服务运行, 如何将他们治理好, 让他们有条不紊的运行, 需要搭配这些技术: [ 服务治理 ]
                SpringCloud Alibaba提供的组件
                -> Nacos注册中心
                -> Nacos配置中心
                -> Seata分布式事务
                -> Sentinel服务容错, 降级, 限流
                
                SpringCloud提供的组件
                -> Feign远程调用&负载均衡
                -> Gateway网关
                -> Sleuth服务追踪
                -> Zipkin可视化追踪

    整个应用的状态监控信息:
                -> Prometheus
                -> Grafana

    //************************[ 微服务分布式开发期间需要用到的周边配套设置 ]**********************************//

    //************************[ 数据支撑层 ]**********************************//

    Redis -> 作为缓存
    Mysql -> 数据的持久化 -> 使用ShardingSphere组件来对Mysql完成分库分表操作
    RabbitMQ -> 作消息队列
    Elastic Search -> 作全文检索
    阿里云对象存储服务OSS -> 存储图片, 视频等静态文件

// 16. 项目开发环境的搭建
// 16.1 虚拟机及ip配置
虚拟机 -> virtualbox.org -> 需要CPU开启虚拟化 -> 开机启动时设置CPU Configuration -> Intel Virtualization Technology 开启CPU虚拟化技术
       -> virtualbox可能与360, 红蜘蛛, 净网大师有冲突, 你启动不起来的话, 卸载一下这些软件

Vagrant -> 只要安装了virtualbox, Vagrant可以帮助咱们快速地创建虚拟机
        -> 官方镜像仓库 -> app.vagrantup.com/boxes/search
        -> Vagrant下载 -> vagrantup.com/downloads.html

        -> 安装好以后 -> win + R -> cmd -> vagrant之后又提示就证明安装成功了

win + R -> 运行Vagrant init centos/7 -> 可初始化一个centos7系统 -> 运行vagrant up可启动虚拟机(这个过程比较漫长, 他得去官网下载镜像) -> 用户: root, 密码: vagrant
        -> vagrant ssh -> 自动使用vagrant用户连接虚拟机
        -> 使用exit可退出虚拟机的连接
        -> 停止虚拟机可以在virtual box点右键正常关机就行 -> 启动可以类似直接右键或者命令行也行
                                                      -> win + R -> cmd -> vagrant up -> 这个操作得确保你命令行窗口所在路径存在Vagrantfile文件 -> 启动好之后紧接着vagrant ssh连接虚拟机即可
        -> vagrant upload source [destination] [name|id] -> 上传文件
        -> Vagrant命令行 -> vagrantup.com/docs/cli/init.html


-> 从VirtualBox的设置中可以看到左侧网络一项 -> 网络地址转换(NAT) + 端口转发 方式

        网络地址转换和端口转发 -> 假设我们在windows电脑里面装了一个VirtualBox, virtualbox里面装的是一个linux系统, 如果后面我们装了好多软件, 
        比如说是Mysql和Redis, Mysql默认端口是3306, 而Redis的端口号是6379. 如果是端口转发方式的话, 使用Mysql或者Redis, 就需要对Virtualbox里面的
        linux中3306端口和windows主机的3333(假设)端口进行绑定, 也就是只要有用户访问了我Windows的3333端口, 就相当于访问了本机Virtualbox里面的Mysql
        的3306端口. 那么每在linux里面装一个软件就都需要做一个端口映射, 操作如下:
                -> virtualbox -> 设置 -> 网络 -> 高级 -> 端口转发 -> 
                        名称  协议 主机IP    主机端口 子系统IP 子系统端口
                        ssh   TCP 127.0.0.1 2222             22

                        mysql TCP           3333             3306

        这样比较麻烦, 希望能够让linux机子有一个固定的ip, 然后windows能够与linux机子ping通, 这样就可以直接访问ip地址加端口号就行.

默认虚拟机ip地址不固定, 不便于开发 ->
        -> 修改安装虚拟机时候主目录下的Vagrantfile文件参数 -> config.vm.network"private_network",ip:"192.168.56.10" 
                                                        -> ip用win + R -> cmd -> ipconfig找到 -> virtualbox的虚拟网卡
                                                        -> 以太网适配器 VirtualBox Host-only Network 
                                                        -> 假设IPV4地址是: 192.168.56.1 -> 那么咱们就写一个192.168.56.X即可
                                                        -> 改好后重启虚拟机 -> vagrant reload
                                                        -> vagrant ssh -> ip addr -> 查看inet那一行地址

                                                        测试windows与linux机子是否能互相ping通
                                                                -> win + R -> ipconfig -> windows ip地址 -> 互相ping一下

// 16.2 安装docker
以后像Redis或者Mysql等软件, 都使用Docker快速安装部署

-> Docker是什么? -> 虚拟化容器 -> 一般虚拟机里面或者说服务器里面咱们安装软件需要一些列步骤, 并且每个软件出故障了很容易影响到宿主机或者其它软件
                              -> Docker可以解决上述痛点

                              举例: 常规方式安装windows就是 -> 先买windows正版盘, 然后一点点装, 并且会对磁盘分区
                                                          -> windows装好以后, 咱们会接着在windows里面装各种软件, 完后就可以使用了
                                                          -> 如果说某一天windows出现了问题, 要重装系统, 那么上面这个过程就需要从头到尾再走一遍

                              Docker这个工具可以从网上下载各种镜像, 然后基于这个镜像, Docker可以秒级启动容器, 这个容器就是这个镜像的完整运行环
                              境, 类似于完整启动了一个win10系统

                              之后想装某种环境只需要给linux里面安装上Docker, 然后让Docker去软件市场去下载各种软件的镜像, 镜像市场 -> 
                              hub.docker.com -> 搜软件 -> 让Docker下载 -> 下载好以后, Docker就可以直接基于这个镜像秒级启动它的容器, 
                              这些容器就是当前软件的完整运行环境 -> 容器与容器之间都是互相隔离的 -> 假设咱们运行启动了两个Redis容器, 
                              某一个出现了问题其实是并不会影响其他容器的


-> 安装Docker -> docker.com -> Resources -> Docs -> Get Docker -> 版本一: Community 社区版, 免费 -> Linux -> CentOS
                                                                  版本二: Enterprise 企业版, 收费

-> 先安装相关依赖包 -> sudo yum install -y yum-utils device-mapper-persistent-data lvm2

-> 告诉linux从哪里安装Docker -> sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

-> 安装docker引擎, 操作docker的客户端, 容器 -> sudo yum install docker-ce docker-ce-cli containerd.io -> 一路y确认

-> 启动Docker -> sudo systemctl start docker -> docker -v(查看docker版本) -> sudo docker images(检查虚拟机里面下载了哪些镜像)

-> 设置docker的开机自启 -> sudo systemctl enable docker

// 16.3 为Docker配置镜像加速 -> 阿里云镜像加速下载
默认docker下载镜像都是从国外的网站hub.docker.com进行下载的, 速度比较慢, 可以配置一个国内的镜像加速.

-> aliyun.com -> 登录 -> 控制台 -> 左侧栏: 产品与服务 -> 容器镜像服务 -> 镜像工具 -> 镜像加速器 -> CentOS
        针对Docker客户端版本大于 1.10.0 的用户

        您可以通过修改daemon配置文件/etc/docker/daemon.json来使用加速器

        sudo mkdir -p /etc/docker
        sudo tee /etc/docker/daemon.json <<-'EOF'
        {
                "registry-mirrors": ["https://ai82huyd.mirror.aliyuncs.com"]
        }
        EOF
        sudo systemctl daemon-reload
        sudo systemctl restart docker

// 16.4 docker安装Mysql
开启虚拟机 -> Virtualbox虚拟机右键 -> 启动 -> 无界面启动 -> vagrant ssh(连接虚拟机)

hub.docker.com -> 搜索Mysql, 找到official image官方镜像 -> sudo docker pull mysql:5.7 

sudo docker images -> 检查已经下载的镜像有没有mysql -> 那么有了这个镜像之后就可以让docker基于这个Mysql镜像来帮我们启动Mysql容器了

// 16.5 创建Mysql实例并启动
切换为root身份 -> su root -> Password: vagrant(默认密码)

docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7

-> 参数解释说明: 
        -p 3306:3306 -> 将容器的3306端口映射到主机的3306端口
        --name mysql -> 为当前启动的容器起一个名字

        -v -> 目录挂载
        -v /mydata/mysql/log:/var/log/mysql -> 将日志文件夹挂载到主机
        -v /mydata/mysql/data:/var/lib/mysql -> 将配置文件夹挂载到主机 -> mysql要持久化的一些数据文件也挂载到了外部路径下
        -v /mydata/mysql/conf:/etc/mysql -> 将配置文件夹挂载到主机

        -e -> 启动的时候为组件设置一些参数
        -e MYSQL_ROOT_PASSWORD=root -> 初始化root用户的密码

        -d -> 以后台方式运行, mysql:5.7指的是使用哪一个镜像启动的这个容器

-> 查看docker中正在运行的容器 -> docker ps -> windows下的SQLyog可以链接一下 -> 
        主机: 192.168.56.10
        用户名: root
        密码: root
        端口号: 3306

// 16.5 docker容器文件挂载与端口映射
-> 每次docker run一下, 就会启动一个docker容器, 容器间互相隔离, 每个容器都是一个完整的linux运行环境, 假设使用了mysql镜像的话, 
   那么就是有一个完整的linux机器下的mysql运行环境

-> 进入容器内部 -> docker exec -it 2009870..(这里写docker ps的容器id或者容器的name, 不用写全, 只要能跟别人不一样就行) /bin/bash
               -> -it: 交互模式 -> 上面命令之后就进入了linux的控制台
               -> root@2009870e21c3:/# -> 表示以root身份进入到了容器内部
               -> 通过ls /就能发现这就是一个完整的linux目录结构 -> 而mysql就相当于装在了这个linux里面
               -> whereis mysql -> 查看mysql被装在了哪里? 
        
               注意容器内部安装的mysql有一个默认端口是3306, 如果说咱们想要访问这个容器内部的mysql的话, 就需要把内部安装的mysql端口3306映射到
               外部环境linux里面, 其中'-p 3306:3306'就是指, 现在把linux里面的3306端口与linux里面安装的docker里面的mysql的3306访问端口作映射
               那么访问linux的3306就能访问docker里面的mysql了

               -> 退出容器内部就用'exit'命令即可

-> -v参数解释: 
               -> 注意mysql是被安装在了docker内部, docker内部是一个完整的linux系统, 而其内部的'etc/mysql'放置了mysql的配置文件, 而mysql相关的日
               志是被放在了docker内部的'var/log/mysql'路径下

               注意, 如果说咱们想改mysql的配置, 按照常规思路就是每次都要进入docker内部找到相应文件路径下的文件进行更改, 这太麻烦了, 如果说将我们经
               常看的目录映射到外部linux的目录下的话, 那么就方便多了. 那么, '-v'这个命令就是来做这个事儿的.

               ':'这个就是挂载, 功能类似于把docker里面的'var/log/mysql'路径的快捷方式放在了外部linux的'/mydata/mysql/log'目录下面, 那么只要
               docker的'var/log/mysql'一有日志产生, 那么就能在外层的linux的'/mydata/mysql/log'目录下面进行实时查看

               同样的, 只要你更改了linux'/mydata/mysql/conf'路径下的配置文件, docker'/etc/mysql'路径下的配置也会被修改

// 16.6 Mysql配置
vi /mydata/mysql/conf/my.cnf

        // mysql的字符编码默认是拉丁, 这里改成utf-8

        [client]
        default-character-set=utf8

        [mysql]
        default-character-set=utf8

        [mysqld]
        init_connect='SET collation_connection=utf8_unicode_ci'
        init_connect='SET NAMES utf8'
        character-set-server=utf8
        collation-server=utf8_unicode_ci
        skip-character-set-client-handshake
        // 跳过域名解析, 可解决连接mysql慢的问题
        skip-name-resolve

-> 重启mysql的容器才能应用到配置 -> docker ps -> docker restart mysql -> docker exec -it mysql /bin/bash
                               -> cat /etc/mysql/my.cnf


// 16.7 docker安装redis
-> 下载镜像文件 -> docker pull redis -> 等效于: docker pull redis:latest

-> 创建实例并启动 

        mkdir -p /mydata/redis/conf

        // -v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf 这一步, 由于'/etc/redis/'路径下没有redis.conf文件
        // 所以预先在目录下创建好redis.conf文件再进行挂载
        touch /mydata/redis/conf/redis.conf


        docker run -p 6379:6379 --name redis \
        -v /mydata/redis/data:/data \
        -v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
        -d redis redis-server /etc/redis/redis.conf

        // '-d redis' -> 基于redis镜像启动docker
        // 'redis-server /etc/redis/redis.conf' -> 算是参数 -> 基于'/etc/redis/redis.conf'这个配置文件启动redis-server

        docker ps -> 检查一下是否运行起来 -> 连接上redis的客户端
                     docker exec -it redis redis-cli -> exit 退出

        由于redis默认没有持久化, 所有数据都存在了内存中, 你退出重启redis之后, 重新连接redis, 那么上一次你存的数据, 这一次你就访问不到了

        docker restart redis -> docker exec -it redis redis-cli

        -> 进入外部挂在目录 -> cd /mydata/redis/conf -> vi redis.conf -> appendonly yes(这是让redis启动aof的持久化方式) 
                           -> 重启redis -> docker restart redis -> docker ps -> docker exec -it redis redis-cli
                           -> set a b -> exit -> docker restart redis -> docker ps -> docker exec -it redis redis-cli -> get a -> "b"

        -> 为了方便看redis中的数据, 推荐安装redis可视化客户端 -> RedisDesktopManager
                           -> 右下角 -> 'Connect to Redis Server' -> 
                                     -> name: redis-gulimall
                                     -> host: 192.168.56.10
                                     -> port: 6379
                                     -> auth: 

        -> redis的配置文件中有哪些参数可配置?
                -> redis.conf的配置样例 -> https://redis.io/docs/management/config-file/

// 17. 开发环境统一
// 17.1 Maven
