update USER_MENU set MENUCODE='102' where EMPCODE='007' and MENUCODE='101';

INSERT INTO MENU VALUES('016','系统访问管理','1','visit.do?action=frame',5,'1','001','1.png');
INSERT INTO MENU VALUES('105','临时调查管理','1','ins.do?action=frame_manage',10,'1','100','10.png');
INSERT INTO MENU VALUES('106','临时调查填写','1','ins.do?action=frame_list',10,'1','100','10.png');
INSERT INTO USER_MENU VALUES('001','016','1');
INSERT INTO USER_MENU VALUES('002','105','1');
INSERT INTO USER_MENU VALUES('003','106','1');


/*==============================================================*/
/* Table: SYS_VISIT                                             */
/*==============================================================*/
create table SYS_VISIT  (
   V_EMPCODE            VARCHAR(20),
   V_IP                 VARCHAR(100),
   TYPE                 VARCHAR(10),
   STATUS               VARCHAR(10)
);

comment on table SYS_VISIT is
'系统访问表';

comment on column SYS_VISIT.V_EMPCODE is
'访问工号';

comment on column SYS_VISIT.V_IP is
'访问IP';

comment on column SYS_VISIT.TYPE is
'访问类型(1工号;2IP)';

comment on column SYS_VISIT.STATUS is
'状态(0未启用;1启用)';

INSERT INTO SYS_VISIT VALUES('0000000','','1','0');
INSERT INTO SYS_VISIT VALUES('','127.0.0.1','2','0');

/*==============================================================*/
/* Table: INVESTIGATION                                         */
/*==============================================================*/
create table INVESTIGATION  (
   ID                   VARCHAR(32)                     not null,
   TITLE                VARCHAR(200),
   NOTE                 VARCHAR(1000),
   STARTDATE            DATE,
   STARTEMPCODE         VARCHAR(20),
   STARTEMPNAME         VARCHAR(20),
   STATUS               VARCHAR(10),
   constraint PK_INVESTIGATION primary key (ID)
);

comment on table INVESTIGATION is
'临时调查表';

comment on column INVESTIGATION.ID is
'ID';

comment on column INVESTIGATION.TITLE is
'调查标题';

comment on column INVESTIGATION.NOTE is
'调查内容';

comment on column INVESTIGATION.STARTDATE is
'发起时间';

comment on column INVESTIGATION.STARTEMPCODE is
'发起者工号';

comment on column INVESTIGATION.STARTEMPNAME is
'发起者名称';

comment on column INVESTIGATION.STATUS is
'状态(1发起;2结束)';

/*==============================================================*/
/* Table: INS_BACK                                              */
/*==============================================================*/
create table INS_BACK  (
   ID                   VARCHAR(32)                     not null,
   INS_ID               VARCHAR(32),
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(20),
   BACKDATE             DATE,
   NOTE                 VARCHAR(1000),
   constraint PK_INS_BACK primary key (ID)
);

comment on table INS_BACK is
'临时调查反馈表';

comment on column INS_BACK.ID is
'ID';

comment on column INS_BACK.INS_ID is
'临时调查ID';

comment on column INS_BACK.EMPCODE is
'反馈人工号';

comment on column INS_BACK.EMPNAME is
'反馈人编码';

comment on column INS_BACK.BACKDATE is
'反馈日期';

comment on column INS_BACK.NOTE is
'反馈内容';
