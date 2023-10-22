R_GE0008_20160100_H 需要配置的表有

// 一、报表定义表 -> RSP_CFG_RPTDFN

// select * from RSP_CFG_RPTDFN where RPTTYP='1';
insert into RSP_CFG_RPTDFN(
    SBJCOD,
    RPTCOD,
    CFGBSL,
    RPTNAM,
    RPTTYP,
    RPTFLG,
    CHCFLG,
    BLANKSHOW
) values (
    '001',
    'GE0008',
    '20160100',
    '信用卡总账',
    1,
    0,
    0,
    1
);

commit;

// 二、报表维度定义表 -> RSP_CFG_RPTDIMDFN
create table RSP_CFG_RPTDIMDFN_GE0008 as 
select
    distinct *
from RSP_CFG_RPTDIMDFN
where rptcod = 'GE0005' and cfgbsl = '20160100';

update RSP_CFG_RPTDIMDFN_GE0008 set rptcod = 'GE0008';

insert into RSP_CFG_RPTDIMDFN 
select 
    *
from RSP_CFG_RPTDIMDFN_GE0008;

drop table RSP_CFG_RPTDIMDFN_GE0008;

commit;

// 三、参考GE0005创建要素表D_GE0008_FACCOD
create table D_GE0008_FACCOD as 
select
    *
from D_GE0005_FACCOD where rownum < 1;

// 四、创建 R_GE0008_20160100_H 表
create table R_GE0008_20160100_H (
    DTE varchar2(8) not null,
    ORG varchar2(11) not null,
    CCY varchar2(2) not null,
    CNTFLG varchar2(1) not null,
    FACCOD varchar2(9) not null,
    FLD003 NUMBER(25, 6),
    FLD005 NUMBER(25, 6),
    FLD006 NUMBER(25, 6),
    FLD007 NUMBER(25, 6),
    FLD008 NUMBER(25, 6),
    FLD009 NUMBER(25, 6),
    FLD010 NUMBER(25, 6),
    FLD011 NUMBER(25, 6),
    FLD012 NUMBER(25, 6),
    FLD013 NUMBER(25, 6),
    FLD014 NUMBER(25, 6),
    FLD015 NUMBER(25, 6)
  // 基于DTE字段分区
) partition by list (DTE) (
    // values定义分区的值范围, 这里定义一个区只有一个值, 也就是某天的会计数据全部放在某天的分区中, 分区所属表空间为IDS
    partition pt_231014 values ('20231014') tablespace IDS,
    partition pt_231015 values ('20231015') tablespace IDS,
    partition pt_231016 values ('20231016') tablespace IDS,
    partition pt_231017 values ('20231017') tablespace IDS,
    partition pt_231018 values ('20231018') tablespace IDS,
    partition pt_231019 values ('20231019') tablespace IDS,
    partition pt_231020 values ('20231020') tablespace IDS
  // 指示插入数据时不要生成日志记录, 这样能提高插入性能, 但不利于事务恢复
) nologging;

// 五、R_GE0008_20160100_H 添加索引
create index GE0008_INDEX_14_15 on R_GE0008_20160100_H (
    DTE,
    FACCOD,
    ORG,
    CCY,
    CNTFLG,
    FLD014,
    FLD015
) local ;

// 六、跑脚本所需参数
/home/uidsusr/ap/loadidx/bin/GE0008_CREATE.sh 20231014 t t t