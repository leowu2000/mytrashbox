/*==============================================================*/
/* Table: ASSETS_INFO                                           */
/*==============================================================*/
create table ASSETS_INFO  (
   ID                   VARCHAR(32)                     not null,
   NAME                 VARCHAR(200),
   TYPE                 VARCHAR(20),
   CODE                 VARCHAR(200),
   MJJBH                VARCHAR(200),
   XHGG                 VARCHAR(200),
   YZ                   VARCHAR(200),
   SYBM                 VARCHAR(200),
   SYDD                 VARCHAR(200),
   SBBGR                VARCHAR(200),
   TRSYRQ               DATE,
   SBZT                 VARCHAR(20),
   CZXTAZRQ             DATE,
   KTJKLX               VARCHAR(20),
   YT                   VARCHAR(20),
   IP                   VARCHAR(50),
   MAC                  VARCHAR(50),
   YPXH                 VARCHAR(50),
   YPXLH                VARCHAR(50),
   constraint PK_ASSETS_INFO primary key (ID)
);

comment on table ASSETS_INFO is
'信息设备管理';

comment on column ASSETS_INFO.ID is
'ID';

comment on column ASSETS_INFO.NAME is
'设备名称';

comment on column ASSETS_INFO.TYPE is
'资产属性';

comment on column ASSETS_INFO.CODE is
'固定资产编号';

comment on column ASSETS_INFO.MJJBH is
'密级及编号';

comment on column ASSETS_INFO.XHGG is
'型号及规格';

comment on column ASSETS_INFO.YZ is
'原值';

comment on column ASSETS_INFO.SYBM is
'使用部门';

comment on column ASSETS_INFO.SYDD is
'使用地点';

comment on column ASSETS_INFO.SBBGR is
'设备保管人';

comment on column ASSETS_INFO.TRSYRQ is
'投入使用日期';

comment on column ASSETS_INFO.SBZT is
'设备状态';

comment on column ASSETS_INFO.CZXTAZRQ is
'操作系统安装日期';

comment on column ASSETS_INFO.KTJKLX is
'开通接口类型';

comment on column ASSETS_INFO.YT is
'用途';

comment on column ASSETS_INFO.IP is
'IP地址';

comment on column ASSETS_INFO.MAC is
'MAC地址';

comment on column ASSETS_INFO.YPXH is
'硬盘型号';

comment on column ASSETS_INFO.YPXLH is
'硬盘序列号';

/*==============================================================*/
/* Table: ASSETS_INFO_REPAIR                                    */
/*==============================================================*/
create table ASSETS_INFO_REPAIR  (
   ID                   VARCHAR(32)                     not null,
   I_ID                 VARCHAR(32),
   ASSETSCODE           VARCHAR(200),
   R_COST               NUMERIC(11,2),
   R_DATE               DATE,
   R_REASON             VARCHAR(1000),
   R_NOTE               VARCHAR(1000),
   constraint PK_ASSETS_INFO_REPAIR primary key (ID)
);

comment on table ASSETS_INFO_REPAIR is
'信息设备维修表';

comment on column ASSETS_INFO_REPAIR.ID is
'ID';

comment on column ASSETS_INFO_REPAIR.I_ID is
'信息设备ID';

comment on column ASSETS_INFO_REPAIR.ASSETSCODE is
'固定资产编码';

comment on column ASSETS_INFO_REPAIR.R_COST is
'维修费用';

comment on column ASSETS_INFO_REPAIR.R_DATE is
'维修日期';

comment on column ASSETS_INFO_REPAIR.R_REASON is
'维修原因';

comment on column ASSETS_INFO_REPAIR.R_NOTE is
'维修情况简述';

INSERT INTO DICT VALUES('800001','固定资产类','8');
INSERT INTO DICT VALUES('800002','实物管理类','8');
INSERT INTO DICT VALUES('800003','装机待出所','8');

INSERT INTO DICT VALUES('900001','在用','9');
INSERT INTO DICT VALUES('900002','维修','9');
INSERT INTO DICT VALUES('900003','待报废','9');
INSERT INTO DICT VALUES('900004','外场','9');

update menu set menuurl='contract.do?action=frame_contract' where menucode='111';
update menu set menuurl='c_apply.do?action=frame_apply' where menucode='112';
update menu set menuurl='c_budget.do?action=frame_budget' where menucode='113';
update menu set menuurl='c_pay.do?action=frame_pay' where menucode='114';
delete from menu where menucode in ('120', '121');