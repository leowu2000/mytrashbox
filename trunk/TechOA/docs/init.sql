/*==============================================================*/
/* Table: ASSETS                                                */
/*==============================================================*/
create table ASSETS  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   MODEL                VARCHAR(50),
   BUYDATE              DATE,
   PRODUCDATE           DATE,
   BUYCOST              NUMERIC(15,2),
   NOWCOST              NUMERIC(15,2),
   LIFE                 INTEGER,
   STATUS               VARCHAR(10),
   DEPARTCODE           VARCHAR(20),
   EMPCODE              VARCHAR(20),
   LENDDATE             DATE,
   CHECKDATE            DATE,
   CHECKYEAR            INTEGER,
   constraint PK_ASSETS primary key (ID)
);

comment on table ASSETS is
'固定资产表';

comment on column ASSETS.ID is
'ID';

comment on column ASSETS.CODE is
'编码';

comment on column ASSETS.NAME is
'设备名称';

comment on column ASSETS.MODEL is
'设备型号';

comment on column ASSETS.BUYDATE is
'购买日期';

comment on column ASSETS.PRODUCDATE is
'出厂日期';

comment on column ASSETS.BUYCOST is
'购买价格';

comment on column ASSETS.NOWCOST is
'折旧价格';

comment on column ASSETS.LIFE is
'预计使用年限';

comment on column ASSETS.STATUS is
'设备状态(1:库中;2:借出;3:损坏)';

comment on column ASSETS.DEPARTCODE is
'借出部门';

comment on column ASSETS.EMPCODE is
'借出人';

comment on column ASSETS.LENDDATE is
'借出日期';

comment on column ASSETS.CHECKDATE is
'上次检查时间';

comment on column ASSETS.CHECKYEAR is
'检查间隔';

/*==============================================================*/
/* Table: ATTACHMENT                                            */
/*==============================================================*/
create table ATTACHMENT  (
   ID                   VARCHAR(32)                     not null,
   RTABLE               VARCHAR(50),
   RCOLUMN              VARCHAR(50),
   RVALUE               VARCHAR(50),
   TYPE                 VARCHAR(20),
   FNAME                VARCHAR(100),
   FTYPE                VARCHAR(10),
   CONTENT              BLOB,
   FSIZE                INTEGER,
   constraint PK_ATTACHMENT primary key (ID)
);

comment on table ATTACHMENT is
'附件表';

comment on column ATTACHMENT.RTABLE is
'关联表';

comment on column ATTACHMENT.RCOLUMN is
'关联字段';

comment on column ATTACHMENT.RVALUE is
'关联值';

comment on column ATTACHMENT.TYPE is
'附件类型(1:照片;2:文章)';

comment on column ATTACHMENT.FNAME is
'文件名';

comment on column ATTACHMENT.FTYPE is
'文件类型';

comment on column ATTACHMENT.CONTENT is
'文件内容';

comment on column ATTACHMENT.FSIZE is
'文件大小';

/*==============================================================*/
/* Table: DEPARTMENT                                            */
/*==============================================================*/
create table DEPARTMENT  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(20),
   PARENT               VARCHAR(20),
   ALLPARENTS           VARCHAR(500),
   "LEVEL"              INTEGER,
   constraint PK_DEPARTMENT primary key (ID)
);

comment on table DEPARTMENT is
'部门表';

comment on column DEPARTMENT.ID is
'ID';

comment on column DEPARTMENT.CODE is
'编码';

comment on column DEPARTMENT.NAME is
'部门名称';

comment on column DEPARTMENT.PARENT is
'上级编码';

comment on column DEPARTMENT.ALLPARENTS is
'所有上级部门(用逗号隔开)';

comment on column DEPARTMENT."LEVEL" is
'等级';

/*==============================================================*/
/* Table: DICT                                                  */
/*==============================================================*/
create table DICT  (
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   TYPE                 VARCHAR(1)
);

comment on table DICT is
'字典表';

comment on column DICT.CODE is
'编码';

comment on column DICT.NAME is
'名称';

comment on column DICT.TYPE is
'分类(1:专业;2:学位;3:职称;4:考勤;5:工程阶段)';

/*==============================================================*/
/* Table: EMPLOYEE                                              */
/*==============================================================*/
create table EMPLOYEE  (
   ID                   VARCHAR(32)                     not null,
   LOGINID              VARCHAR(50),
   PASSWORD             VARCHAR(50),
   CODE                 VARCHAR(20),
   ROLECODE             VARCHAR(20),
   NAME                 VARCHAR(20),
   DEPARTCODE           VARCHAR(20),
   MAINJOB              VARCHAR(20),
   SECJOB               VARCHAR(20),
   "LEVEL"              VARCHAR(20),
   EMAIL                VARCHAR(100),
   BLOG                 VARCHAR(100),
   SELFWEB              VARCHAR(100),
   STCPHONE             VARCHAR(20),
   MOBPHONE             VARCHAR(20),
   ADDRESS              VARCHAR(500),
   POST                 VARCHAR(20),
   MAJORCODE            VARCHAR(20),
   DEGREECODE           VARCHAR(20),
   PROCODE              VARCHAR(20),
   DESCRIBE             VARCHAR(500),
   constraint PK_EMPLOYEE primary key (ID)
);

comment on table EMPLOYEE is
'人员基本信息表';

comment on column EMPLOYEE.ID is
'ID';

comment on column EMPLOYEE.LOGINID is
'登陆名';

comment on column EMPLOYEE.PASSWORD is
'密码';

comment on column EMPLOYEE.CODE is
'人员编码';

comment on column EMPLOYEE.ROLECODE is
'角色编码';

comment on column EMPLOYEE.NAME is
'姓名';

comment on column EMPLOYEE.DEPARTCODE is
'部门编码';

comment on column EMPLOYEE.MAINJOB is
'主岗';

comment on column EMPLOYEE.SECJOB is
'副岗';

comment on column EMPLOYEE."LEVEL" is
'职务级别';

comment on column EMPLOYEE.EMAIL is
'电子邮件';

comment on column EMPLOYEE.BLOG is
'博客链接';

comment on column EMPLOYEE.SELFWEB is
'个人网页';

comment on column EMPLOYEE.STCPHONE is
'固定电话';

comment on column EMPLOYEE.MOBPHONE is
'手机号码';

comment on column EMPLOYEE.ADDRESS is
'家庭住址';

comment on column EMPLOYEE.POST is
'邮政编码';

comment on column EMPLOYEE.MAJORCODE is
'专业编码';

comment on column EMPLOYEE.DEGREECODE is
'学历编码';

comment on column EMPLOYEE.PROCODE is
'职称编码';

comment on column EMPLOYEE.DESCRIBE is
'描述';

/*==============================================================*/
/* Table: MENU                                                  */
/*==============================================================*/
create table MENU  (
   MENUCODE             VARCHAR(20)                     not null,
   MENUNAME             VARCHAR(50),
   MENUTYPE             VARCHAR(1),
   MENUURL              VARCHAR(100),
   ORDERCODE            INTEGER,
   STATUS               VARCHAR(1),
   PARENT               VARCHAR(20),
   ICON                 INTEGER,
   constraint PK_MENU primary key (MENUCODE)
);

comment on table MENU is
'菜单表';

comment on column MENU.MENUCODE is
'菜单编码';

comment on column MENU.MENUNAME is
'菜单名称';

comment on column MENU.MENUTYPE is
'菜单类型(1:菜单功能项;2:父菜单项;3:其他)';

comment on column MENU.MENUURL is
'功能链接';

comment on column MENU.ORDERCODE is
'排序号';

comment on column MENU.STATUS is
'功能状态(0:关闭;1:开启)';

comment on column MENU.PARENT is
'父功能编码';

comment on column MENU.ICON is
'图标号';


/*==============================================================*/
/* Table: PJ_COST                                               */
/*==============================================================*/
create table PJ_COST  (
   ID                   VARCHAR(32)                     not null,
   RQ                   DATE,
   DJBH                 VARCHAR(20),
   GZLH                 VARCHAR(20),
   FXT                  VARCHAR(20),
   ZJH                  VARCHAR(20),
   BM                   VARCHAR(20),
   XHGG                 VARCHAR(20),
   DW                   VARCHAR(20),
   SL                   INTEGER,
   JE                   NUMERIC(10,2),
   XM                   VARCHAR(20),
   LLDW                 VARCHAR(20),
   JSDW                 VARCHAR(20),
   YT                   VARCHAR(50),
   constraint PK_PJ_COST primary key (ID)
);

comment on table PJ_COST is
'课题费用表';

comment on column PJ_COST.ID is
'ID';

comment on column PJ_COST.RQ is
'日期';

comment on column PJ_COST.DJBH is
'单据编号';

comment on column PJ_COST.GZLH is
'工作令号';

comment on column PJ_COST.FXT is
'分系统';

comment on column PJ_COST.ZJH is
'整件号';

comment on column PJ_COST.BM is
'编码';

comment on column PJ_COST.XHGG is
'型号规格';

comment on column PJ_COST.DW is
'单位';

comment on column PJ_COST.SL is
'数量';

comment on column PJ_COST.JE is
'金额';

comment on column PJ_COST.XM is
'姓名';

comment on column PJ_COST.LLDW is
'领料单位';

comment on column PJ_COST.JSDW is
'结算单位';

comment on column PJ_COST.YT is
'用途';

/*==============================================================*/
/* Table: PLAN                                                  */
/*==============================================================*/
create table PLAN  (
   ID                   VARCHAR(32)                     not null,
   EMPCODE              VARCHAR(20),
   PJCODE               VARCHAR(20),
   PJCODE_D             VARCHAR(20),
   STAGECODE            VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PLANEDWORKLOAD       INTEGER,
   NOTE                 VARCHAR(500),
   constraint PK_PLAN primary key (ID)
);

comment on table PLAN is
'计划表';

comment on column PLAN.ID is
'ID';

comment on column PLAN.EMPCODE is
'人员编码';

comment on column PLAN.PJCODE is
'工作令号';

comment on column PLAN.PJCODE_D is
'分系统编码';

comment on column PLAN.STAGECODE is
'阶段编码';

comment on column PLAN.STARTDATE is
'计划起始时间';

comment on column PLAN.ENDDATE is
'计划截止时间';

comment on column PLAN.PLANEDWORKLOAD is
'计划工时';

comment on column PLAN.NOTE is
'计划描述';

/*==============================================================*/
/* Table: PROJECT                                               */
/*==============================================================*/
create table PROJECT  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(20),
   STATUS               VARCHAR(1),
   MANAGER              VARCHAR(20),
   MEMBER               VARCHAR(500),
   PLANEDWORKLOAD       INTEGER,
   NOWWORKLOAD          INTEGER,
   STARTDATE            DATE,
   ENDDATE              DATE,
   NOTE                 VARCHAR(500),
   constraint PK_PROJECT primary key (ID)
);

comment on table PROJECT is
'工程/工作令表';

comment on column PROJECT.ID is
'ID';

comment on column PROJECT.CODE is
'编码';

comment on column PROJECT.NAME is
'名称';

comment on column PROJECT.STATUS is
'项目状态(0:关闭;1:开启;2:挂起)';

comment on column PROJECT.MANAGER is
'项目经理';

comment on column PROJECT.MEMBER is
'项目参与人员';

comment on column PROJECT.PLANEDWORKLOAD is
'计划工作量(小时)';

comment on column PROJECT.NOWWORKLOAD is
'已投入工作量(小时)';

comment on column PROJECT.STARTDATE is
'项目开始时间';

comment on column PROJECT.ENDDATE is
'项目截止时间';

comment on column PROJECT.NOTE is
'项目描述';

/*==============================================================*/
/* Table: PROJECT_D                                             */
/*==============================================================*/
create table PROJECT_D  (
   ID                   VARCHAR(32)                     not null,
   PJCODE               VARCHAR(20),
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   MANAGER              VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PLANEDWORKLOAD       INTEGER,
   NOTE                 VARCHAR(500),
   constraint PK_PROJECT_D primary key (ID)
);

comment on table PROJECT_D is
'分系统表';

comment on column PROJECT_D.ID is
'ID';

comment on column PROJECT_D.PJCODE is
'工作令号';

comment on column PROJECT_D.CODE is
'分系统编码';

comment on column PROJECT_D.NAME is
'分系统名称';

comment on column PROJECT_D.MANAGER is
'分系统负责人';

comment on column PROJECT_D.STARTDATE is
'起始日期';

comment on column PROJECT_D.ENDDATE is
'截止日期';

comment on column PROJECT_D.PLANEDWORKLOAD is
'计划工时';

comment on column PROJECT_D.NOTE is
'备注';

/*==============================================================*/
/* Table: USER_MENU                                             */
/*==============================================================*/
create table USER_MENU  (
   EMPCODE              VARCHAR(20),
   MENUCODE             VARCHAR(20),
   TYPE                 VARCHAR(1)
);

comment on table USER_MENU is
'用户菜单表';

comment on column USER_MENU.EMPCODE is
'人员编号';

comment on column USER_MENU.MENUCODE is
'菜单编码';

comment on column USER_MENU.TYPE is
'类别(1:正常菜单;2:收藏菜单)';

/*==============================================================*/
/* Table: WORKCHECK                                             */
/*==============================================================*/
create table WORKCHECK  (
   EMPCODE              VARCHAR(20),
   CHECKDATE            DATE,
   CHECKRESULT          VARCHAR(20),
   EMPTYHOURS           INTEGER
);

comment on table WORKCHECK is
'工作考勤表';

comment on column WORKCHECK.EMPCODE is
'员工编码';

comment on column WORKCHECK.CHECKDATE is
'考勤日期';

comment on column WORKCHECK.CHECKRESULT is
'考勤结果';

comment on column WORKCHECK.EMPTYHOURS is
'缺勤小结(小时)';

/*==============================================================*/
/* Table: WORKREPORT                                            */
/*==============================================================*/
create table WORKREPORT  (
   ID                   VARCHAR(32)                     not null,
   NAME                 VARCHAR(100),
   EMPCODE              VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PJCODE               VARCHAR(20),
   PJCODE_D             VARCHAR(20),
   STAGECODE            VARCHAR(20),
   AMOUNT               INTEGER,
   BZ                   VARCHAR(2000),
   FLAG                 INTEGER,
   DEPARTCODE           VARCHAR(20),
   constraint PK_WORKREPORT primary key (ID)
);

comment on table WORKREPORT is
'工作情况报告表';

comment on column WORKREPORT.ID is
'ID';

comment on column WORKREPORT.NAME is
'周报名称';

comment on column WORKREPORT.EMPCODE is
'雇员编码';

comment on column WORKREPORT.STARTDATE is
'起始日期';

comment on column WORKREPORT.ENDDATE is
'截止日期';

comment on column WORKREPORT.PJCODE is
'工作令号';

comment on column WORKREPORT.PJCODE_D is
'分系统编码';

comment on column WORKREPORT.STAGECODE is
'阶段编码';

comment on column WORKREPORT.AMOUNT is
'投入工作量';

comment on column WORKREPORT.BZ is
'备注';

comment on column WORKREPORT.FLAG is
'审批状态(0:未提交;1:待审批;2:审批通过3:审批退回)';

comment on column WORKREPORT.DEPARTCODE is
'部门编码';