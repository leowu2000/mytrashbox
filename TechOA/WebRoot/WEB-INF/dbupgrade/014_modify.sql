/*==============================================================*/
/* Table: INS_COLUMN                                            */
/*==============================================================*/
create table INS_COLUMN  (
   INS_ID               VARCHAR(32)                     not null,
   INSBACK_ID           VARCHAR(32),
   COL_NAME             VARCHAR(200),
   COL_VALUE            VARCHAR(1000)
);

comment on table INS_COLUMN is
'调查字段存储表';

comment on column INS_COLUMN.INS_ID is
'调查ID';

comment on column INS_COLUMN.INSBACK_ID is
'调查反馈ID';

comment on column INS_COLUMN.COL_NAME is
'字段名称';

comment on column INS_COLUMN.COL_VALUE is
'字段反馈值';

ALTER TABLE INVESTIGATION ADD COLUMN ENDDATE DATE;
ALTER TABLE PLAN ALTER COLUMN REMARK TYPE VARCHAR(1000);

DELETE FROM MENU WHERE MENUCODE='053';
INSERT INTO MENU VALUES('053','信息设备维护','1','assets.do?action=frame_info_equip&manage=1',29,'1','005','5.png');
INSERT INTO MENU VALUES('062','物资优选','1','goods.do?action=frame_goodsdict',32,'1','006','6.png');
INSERT INTO MENU VALUES('063','申请领料统计','1','goods.do?action=frame_goodsapply',32,'1','006','6.png');
INSERT INTO USER_MENU VALUES('006','053','1');
INSERT INTO USER_MENU VALUES('006','062','1');
INSERT INTO USER_MENU VALUES('006','063','1');
INSERT INTO USER_MENU VALUES('006','002','1');
INSERT INTO USER_MENU VALUES('006','021','1');
INSERT INTO USER_MENU VALUES('004','106','1');

/*==============================================================*/
/* Table: GOODS_DICT                                            */
/*==============================================================*/
create table GOODS_DICT  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(200),
   NAME                 VARCHAR(200),
   SPEC                 VARCHAR(100),
   TYPE                 VARCHAR(20),
   constraint PK_GOODS_DICT primary key (ID)
);

comment on table GOODS_DICT is
'物资字典表';

comment on column GOODS_DICT.ID is
'ID';

comment on column GOODS_DICT.CODE is
'编码';

comment on column GOODS_DICT.NAME is
'名称';

comment on column GOODS_DICT.SPEC is
'型号规格';

comment on column GOODS_DICT.TYPE is
'优选类型';

/*==============================================================*/
/* Table: GOODS_APPLY                                           */
/*==============================================================*/
create table GOODS_APPLY  (
   ID                   VARCHAR(32)                     not null,
   XQLX                 VARCHAR(200),
   XQDJH                VARCHAR(200),
   SQRQ                 DATE,
   SQBMBM               VARCHAR(200),
   SQBM                 VARCHAR(200),
   JSBM                 VARCHAR(200),
   XMBM                 VARCHAR(200),
   CHBM                 VARCHAR(200),
   CHMC                 VARCHAR(200)                    not null,
   GGXH                 VARCHAR(200),
   YT                   VARCHAR(200),
   DW                   VARCHAR(200),
   SQSL                 INTEGER,
   SQCKSL               INTEGER,
   CKBM                 VARCHAR(200),
   CKMC                 VARCHAR(200),
   CKDJH                VARCHAR(200),
   BCYCSL               INTEGER,
   BCCKSL               INTEGER,
   PCH                  VARCHAR(200),
   DJZT                 VARCHAR(200),
   KGY                  VARCHAR(200),
   ZDR                  VARCHAR(200),
   ZDSJ                 TIMESTAMP,
   constraint PK_GOODS_APPLY primary key (ID)
);

comment on table GOODS_APPLY is
'申请领料统计表';

comment on column GOODS_APPLY.ID is
'ID';

comment on column GOODS_APPLY.XQLX is
'需求类型';

comment on column GOODS_APPLY.XQDJH is
'需求单据号';

comment on column GOODS_APPLY.SQRQ is
'申请日期';

comment on column GOODS_APPLY.SQBMBM is
'申请部门编码';

comment on column GOODS_APPLY.SQBM is
'申请部门';

comment on column GOODS_APPLY.JSBM is
'结算部门';

comment on column GOODS_APPLY.XMBM is
'项目编码';

comment on column GOODS_APPLY.CHBM is
'存货编码';

comment on column GOODS_APPLY.CHMC is
'存货名称';

comment on column GOODS_APPLY.GGXH is
'规格型号';

comment on column GOODS_APPLY.YT is
'用途';

comment on column GOODS_APPLY.DW is
'单位';

comment on column GOODS_APPLY.SQSL is
'申请数量';

comment on column GOODS_APPLY.SQCKSL is
'申请出库数量';

comment on column GOODS_APPLY.CKBM is
'仓库编码';

comment on column GOODS_APPLY.CKMC is
'仓库名称';

comment on column GOODS_APPLY.CKDJH is
'出库单据号';

comment on column GOODS_APPLY.BCYCSL is
'本次应出数量';

comment on column GOODS_APPLY.BCCKSL is
'本次出库数量';

comment on column GOODS_APPLY.PCH is
'批次号';

comment on column GOODS_APPLY.DJZT is
'单据状态';

comment on column GOODS_APPLY.KGY is
'库管员';

comment on column GOODS_APPLY.ZDR is
'制单人';

comment on column GOODS_APPLY.ZDSJ is
'制单时间';

update menu set menuurl='visit.do?action=list' where menucode='016';
