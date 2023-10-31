// 1. 本章课程大纲
1) 环境准备
2) Tensorflow快速入门
3) 数学知识一览表
5) 实战演练

// 2. 安装最基本的深度学习所需环境
Linux操作系统, Python编程语言-2.7, 3.3, 3.5均可(推荐安装Anaconda-他已经预先安装好了python的包, 在后期训练的时候会方便很多)
显卡GTX1080(因为深度学习的神经元与神经元之间的计算中, 大多数是使用浮点型的计算, 使用GPU可以加速深度学习的模型训练, 可缩短训练时间, 也可以用云服务)
    -> 如果你安装了显卡, 就需要安装相应的显卡驱动, 可以根据型号去NVIDIA官网下载相应的驱动, 安装完之后还需要安装CUDA, CUDNN
    (为DNN设计的CPU的加速库, 能提升多种情况下你CPU的执行速度, 下载CUDNN库也需要到NVIDIA官网注册并下载相应操作系统的版本)

安装深度学习的框架 -> Tensorflow

// 3. Tensorflow的安装
如果安装了GPU, 可以选择tensorflow-gpu版本安装, 安装方法很多:
    Pip install, Virtualenv install, Docker install, 源码编译

// 4. 测试安装结果
终端, 进入python环境 -> 终端输入 python

接着输入: import tensorflow as tf, 这是把tensorflow包导进来, 没报错的话, 就证明tensorflow安装成功了

// 5. 这个系列所有配套的项目代码地址
github.com/huxiaoman7/learningdl