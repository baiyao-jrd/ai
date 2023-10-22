// 跑作业的时候每个脚本都必须传的4个参数 -> 0 脚本名, 1 会计日期, 2 任务名称, 3 任务链名称, 4 系统名 
// -lt -> less than -> 小于
// || -> or
// -4 -> 异常退出状态码

if [ $# -lt 4 ] || [ "$1" = "" ] || [ "$2" = "" ] || [ "$3" = "" ] || [ "$4" = "" ]
then
    echo ""
    exit -4
fi

// STRDIR -> 脚本 -> STRDIR=/home/uidsusr/ap/loadidx/bin/ANL_GE0005.sh
STRDIR=$0
// STRDIR##*/ -> ##*/模式匹配变量替换, 删除最后一个斜杠'/'及之前的内容, 这里就只剩下了文件名, 包括文件名后缀
// SH_NAME=ANL_GE0005.sh
SH_NAME=${STRDIR##*/}
// %%.* -> 模式匹配 -> 删除最后一个'.'以及其后所有内容 -> 也就是最后删除了文件后缀名
// THE_SH_NAME=ANL_GE0005
THE_SH_NAME=${SH_NAME%%.*}

// 脚本后面传的第一个参数 -> 20231022
DATE=$1
// ${DATE:2:6} -> 操作类似于substr, 下标从0开始 -> 从下标2位置处开始取, 连续取6个字符 -> date_yymmdd=231022
date_yymmdd=${DATE:2:6}
// 拼接字符串与变量 -> Pt_NAME=PT_231022
Pt_NAME="PT_${date_yymmdd}"
// STRDIR//${SH_NAME}/../etc -> '//'表示匹配替换 -> '${SH_NAME}/../etc'表示将匹配到的所有的'ANL_GE0005.sh'替换为'../etc' -> /home/uidsusr/ap/loadidx/bin/ANL_GE0005.sh -> FDM_ETC_PATH=/home/uidsusr/ap/loadidx/etc
FDM_ETC_PATH=${STRDIR//${SH_NAME}/../etc}
// 将/home/uidsusr/ap/loadidx/etc/config_properties配置文件中的环境变量加载到当前的shell中
. $FDM_ETC_PATH/config_properties

// 获取系统当前日期与时间
// +%F -> 'Full -> 完整日期' 表示输出日期部分，格式为 %Y-%m-%d，即年份-月份-日期。
// .%T -> 'Time -> '时间' 表示输出时间部分，格式为 %H:%M:%S，即小时:分钟:秒。

// 通过执行 date +%F.%T，将返回当前的日期和时间，格式为 YYYY-MM-DD.HH:MM:SS。例如，输出可能会类似于 2023-10-22.15:30:45。
echo "[ `date +%F.%T` ] ANL_GE0005 script start"

// 数据目录 -> /home/uidsusr/ap/loadidx/data/
DATA_DIR=${FDM_ENV_PATH}

// 判断DATA_DIR这个全路径是否是不存在的(-d -> -directory) -> 如果全路径中有不存在的父级目录可以递归创建(-p -> -parents)
if [ ! -d ${DATA_DIR} ]; then
    mkdir -p ${DATA_DIR}
fi

// 对所有者以及所有者所在组以及其他用户 -> 也就是所有用户 -> 赋予这个文件路径的读写执行权限, 当然这里注释了这个操作
# chmod 777 ${DATA_DIR}

// LOG=/home/uidsusr/ap/loadidx/data/log
LOG="${DATA_DIR/log}"
// /home/uidsusr/ap/loadidx/data/log -> 判断目录是否不存在 -> 不存在就递归创建目录
if [ ! -d ${LOG} ]; then
    mkdir -p ${LOG}
fi

// 对所有者以及所有者所在组以及其他用户 -> 也就是所有用户 -> 赋予这个文件路径的读写执行权限, 当然这里注释了这个操作
# chmod 777 ${LOG}

// --------------------------------------[ log日志的日期目录 ]-------------------------------------------------------
i=0
// 循环100次, 超过100次不再执行
while [ $i -lt 100 ]
do
    if [ ! -d "${LOG}/${DATE}" ]
    then 
        mkdir ${LOG}/${DATE}    
    fi
    // 获取mkdir命令执行的状态码 -> 0, 执行成功 -> 非0, 未执行成功
    retcode=$?
    if [ $recode -eq 0 ]
    then 
        // 创建成功就跳出循环 -> 创建成功了就不在继续执行创建目录的命令了
        break
    else
        // 创建不成功, 继续循环 -> expr $i + 1 -> 表达式求值, 最后将值返回给i变量
        i=`expr $i + 1`
        // 脚本休眠1s -> 控制速率
        sleep 1
    fi
done

// -ge -> 大于等于 -> greater than or equal to -> 等于100的时候证明这是第101次循环了 -> 直接异常退出, 退出前输出报错信息
if [ $i -ge 100 ]
then
    echo "创建 ${LOG}/${DATE} 目录失败"
    exit -4
fi

// 对所有者以及所有者所在组以及其他用户 -> 也就是所有用户 -> 赋予这个文件路径的读写执行权限
chmod 777 ${LOG}/${DATE}

// 创建文件全路径名变量 -> 文件名是: 文件名_20231022.log
LOGFILE="${LOG}/${DATE}/${THE_SH_NAME}_${DATE}.log"

echo "[ `date +%F.%T` ANL_GE0005 sql start ]" >> ${LOGFILE}

// 开始执行的时间: 2023-10-22 15:30:45
start_time=`date +'%Y-%m-%d %H:%M:%S'`

// --------------------------------------[ 0 step ]-------------------------------------------------------

// sqlplus -> Oracle数据库命令行交互工具
// -s -> 'silent' -> 静默模式 -> 不显示杂七八糟的各种信息 
// /nolog -> 'no login' -> 不登录数据库
// << EOF -> 从下面一行开始到遇到EOF标记为止, 中间嵌入多行文本 -> 这些文本会被传给sqlplus
// 1>>$LOGFILE 2>&1 -> 标准输出和标准错误会被重定向到${LOG}/${DATE}/${THE_SH_NAME}_${DATE}.log文件里面
sqlplus -s /nolog << EOF 1>>$LOGFILE 2>&1
// 遇到操作系统错误时, sqlplus退出并返回状态码9
WHENEVER OSERROR EXIT 9
// 遇到 SQL 错误, sqlplus退出并返回 SQL 错误码
WHENERROR SQLERROR EXIT SQL.SQLCODE
// 连接 Oracle 数据库, 使用用户名,密码,主机,SID
conn $DB_USR_A/$DB_PWD_A@//$DB_HOST/$DB_SID
// 开启 sqlplus 服务器输出功能, 可以用 DBMS_OUTPUT.PUT_LINE 打印消息
set serveroutput on
// 设置 sqlplus 行长度，设为 32766, 方便结果显示时可以用较大的行长度
set linesize 32766
// 设置显示时间信息, 在执行 SQL 语句时显示时间戳
set time on

declare
    // 声明变量 -> 数据类型声明在后面
    startTime date; 
    endTime date;
    // := 数值类型并且初始化为0
    effect_rownum NUMBER := 0;
    sql_text varchar2(10000);
    p_start_time date;
    p_end_time date;
    temp number;
begin
    // 获取系统当前日期和时间并赋值给p_start_time变量
    select sysdate INTO p_start_time FROM DUAL;
    // dbms_output.put_line, 用来输出文本行 -> || 连接字符串与变量 
    //                                     -> to_char(p_start_time, 'yyyymmdd hh24:mi:ss') -> 格式化p_start_time为固定格式的字符串
    dbms_output.put_line('Program start time: '||to_char(p_start_time, 'yyyymmdd hh24:mi:ss'));

    select sysdate INTO startTime FROM DUAL;
    dbms_output.put_line('start time: '||to_char(startTime, 'yyyymmdd hh24:mi:ss'));

    // --------------------------------------[ 0.0 step ]-------------------------------------------------------

    // 查询IDC_GE0005_V表是否存在, 存在的话count(*)结果应是1 -> 并赋值给 temp 变量
    select count(*) into temp from user_tables where table_name='IDC_GE0005_V';

    // 表不存在
    if temp=0 then
        dbms_output.put_line('table IDC_GE0005_V does not exists');
    // 表已存在
    else
        dbms_output.put_line('table IDC_GE0005_V already exists');
        // 删除该表以及和该表相关的所有约束对象
        sql_text:='drop table IDC_GE0005_V cascade constraints purge';
        dbms_output.put_line(sql_text);
        // 动态执行sql语句
        execute immediate sql_text;
        // SQL%ROWCOUNT -> 获取最近一次 SQL 语句执行后受影响的行数 -> 赋值给effect_rownum
        effect_rownum := SQL%ROWCOUNT;
        dbms_output.put_line('0.0 step effected row count: '||effect_rownum);

        dbms_output.put_line('commit');
        commit;
    end if;

    // --------------------------------------[ 0.1 step ]-------------------------------------------------------

    // 查询IDC_GE0005_T0_V表是否存在, 存在的话count(*)结果应是1 -> 并赋值给 temp 变量
    select count(*) into temp from user_tables where table_name='IDC_GE0005_T0_V';

    // 表不存在
    if temp=0 then
        dbms_output.put_line('table IDC_GE0005_T0_V does not exists');
    // 表已存在
    else
        dbms_output.put_line('table IDC_GE0005_T0_V already exists');
        // 删除该表以及和该表相关的所有约束对象 -> cascade 串联, 也就是依赖链
        sql_text:='drop table IDC_GE0005_T0_V cascade constraints purge';
        dbms_output.put_line(sql_text);
        // 动态执行sql语句
        execute immediate sql_text;
        // SQL%ROWCOUNT -> 获取最近一次 SQL 语句执行后受影响的行数 -> 赋值给effect_rownum
        effect_rownum := SQL%ROWCOUNT;
        dbms_output.put_line('0.1 step effected row count: '||effect_rownum);

        dbms_output.put_line('commit');
        commit;
    end if;

    // --------------------------------------[ 0.2 step ]-------------------------------------------------------

    // 查询IDC_GE0005_T1_V表是否存在, 存在的话count(*)结果应是1 -> 并赋值给 temp 变量
    select count(*) into temp from user_tables where table_name='IDC_GE0005_T1_V';

    // 表不存在
    if temp=0 then
        dbms_output.put_line('table IDC_GE0005_T1_V does not exists');
    // 表已存在
    else
        dbms_output.put_line('table IDC_GE0005_T1_V already exists');
        // 删除该表以及和该表相关的所有约束对象 -> cascade 串联, 也就是依赖链
        sql_text:='drop table IDC_GE0005_T1_V cascade constraints purge';
        dbms_output.put_line(sql_text);
        // 动态执行sql语句
        execute immediate sql_text;
        // SQL%ROWCOUNT -> 获取最近一次 SQL 语句执行后受影响的行数 -> 赋值给effect_rownum
        effect_rownum := SQL%ROWCOUNT;
        dbms_output.put_line('0.2 step effected row count: '||effect_rownum);

        dbms_output.put_line('commit');
        commit;
    end if;

    // --------------------------------------[ 0.3 step ]-------------------------------------------------------

    // 查询IDC_FRM_ORG_V表是否存在, 存在的话count(*)结果应是1 -> 并赋值给 temp 变量
    select count(*) into temp from user_tables where table_name='IDC_FRM_ORG_V';

    // 表不存在
    if temp=0 then
        dbms_output.put_line('table IDC_FRM_ORG_V does not exists');
    // 表已存在
    else
        dbms_output.put_line('table IDC_FRM_ORG_V already exists');
        // 删除该表以及和该表相关的所有约束对象 -> cascade 串联, 也就是依赖链
        sql_text:='drop table IDC_FRM_ORG_V cascade constraints purge';
        dbms_output.put_line(sql_text);
        // 动态执行sql语句
        execute immediate sql_text;
        // SQL%ROWCOUNT -> 获取最近一次 SQL 语句执行后受影响的行数 -> 赋值给effect_rownum
        effect_rownum := SQL%ROWCOUNT;
        dbms_output.put_line('0.3 step effected row count: '||effect_rownum);

        dbms_output.put_line('commit');
        commit;
    end if;

    // --------------------------------------[ 0.4 step ]-------------------------------------------------------

    // 查询IDC_GE0005_FRM_V表是否存在, 存在的话count(*)结果应是1 -> 并赋值给 temp 变量
    select count(*) into temp from user_tables where table_name='IDC_GE0005_FRM_V';

    // 表不存在
    if temp=0 then
        dbms_output.put_line('table IDC_GE0005_FRM_V does not exists');
    // 表已存在
    else
        dbms_output.put_line('table IDC_GE0005_FRM_V already exists');
        // 删除该表以及和该表相关的所有约束对象 -> cascade 串联, 也就是依赖链
        sql_text:='drop table IDC_GE0005_FRM_V cascade constraints purge';
        dbms_output.put_line(sql_text);
        // 动态执行sql语句
        execute immediate sql_text;
        // SQL%ROWCOUNT -> 获取最近一次 SQL 语句执行后受影响的行数 -> 赋值给effect_rownum
        effect_rownum := SQL%ROWCOUNT;
        dbms_output.put_line('0.4 step effected row count: '||effect_rownum);

        dbms_output.put_line('commit');
        commit;
    end if;

    select sysdate into endTime from DUAL;
    dbms_output.put_line('end time: '||to_char(endTime, 'yyyymmdd hh24:mi:ss'));
    // endTime - startTime相减之后在Oracle中表示天, 它是一个以天为单位的小数 -> 之后乘24得到小时数 -> 乘60的平方 -> 得到秒数
    dbms_output.put_line('0 step cost time: '||round(to_number((endTime - startTime))*24*60*60)||' s');

// 下面是异常怎么处理
exception
    // 有未知异常出现 -> 'when others then'是一个异常处理器
    when others then
    // to_char(SQLCODE) -> 最近一次执行sql的结果码转成字符类型 -> SQLERRM -> 最近一次执行SQL语句的错误信息
    dbms_output.put_line('0 step faild!'||to_char(SQLCODE)||SQLERRM);
    // 回滚事务
    rollback;
    // 将当前异常抛给更高层的异常处理器或者调用者
    raise;
// 结束异常处理
end;
// . 是用于执行已保存在文件中的代码块，而 / 是用于执行当前输入缓冲区中的代码块 -> 当前就是执行前面的代码块
/
quit
// 多行文本至此结束
EOF

// 前面sqlplus执行没有问题 -> 成功 -> 异常, 失败 -> 退出
// echo >> -> 追加
// echo > -> 覆写
if [ $? -eq 0 ];then
    echo "成功" >> ${LOGFILE}
else
    echo "失败" >> ${LOGFILE}
    exit -4;


