
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
/* Table: DEP_PJ                                                */
/*==============================================================*/
create table DEP_PJ  (
   DEPCODE              VARCHAR(20),
   PJCODE               VARCHAR(20)
);

comment on table DEP_PJ is
'工程-部门表';

comment on column DEP_PJ.DEPCODE is
'部门编码';

comment on column DEP_PJ.PJCODE is
'项目编码';

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

/*==============================================================*/
/* Table: FUNCTION                                              */
/*==============================================================*/
create table FUNCTION  (
   FUNCCODE             VARCHAR(20)                     not null,
   FUNCNAME             VARCHAR(50),
   FUNCTYPE             VARCHAR(1),
   FUNCURL              VARCHAR(100),
   ORDERCODE            INTEGER,
   STATUS               VARCHAR(1),
   constraint PK_FUNCTION primary key (FUNCCODE)
);

comment on table FUNCTION is
'功能表';

comment on column FUNCTION.FUNCCODE is
'功能编码';

comment on column FUNCTION.FUNCNAME is
'功能名称';

comment on column FUNCTION.FUNCTYPE is
'功能类型(1:菜单功能项;2:其他)';

comment on column FUNCTION.FUNCURL is
'功能链接';

comment on column FUNCTION.ORDERCODE is
'排序号';

comment on column FUNCTION.STATUS is
'功能状态(0:关闭;1:开启)';


/*==============================================================*/
/* Table: PJ_COST                                               */
/*==============================================================*/
create table PJ_COST  (
   ID                   VARCHAR(32)                     not null,
   PJCODE               VARCHAR(20),
   RQ                   DATE,
   BILLCODE             VARCHAR(20),
   FXT                  VARCHAR(20),
   ZJH                  VARCHAR(20),
   XHGG                 VARCHAR(20),
   CODE                 VARCHAR(20),
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

comment on column PJ_COST.PJCODE is
'工程编码';

comment on column PJ_COST.RQ is
'日期';

comment on column PJ_COST.BILLCODE is
'单据编号';

comment on column PJ_COST.FXT is
'分系统';

comment on column PJ_COST.ZJH is
'整件号';

comment on column PJ_COST.XHGG is
'型号规格';

comment on column PJ_COST.CODE is
'编码';

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
'工程编码';

comment on column WORKREPORT.STAGECODE is
'阶段编码';

comment on column WORKREPORT.AMOUNT is
'投入工作量';

comment on column WORKREPORT.BZ is
'备注';

comment on column WORKREPORT.FLAG is
'审批状态(0:待审批;1:审批通过;2:审批退回)';

comment on column WORKREPORT.DEPARTCODE is
'部门编码';

/*==============================================================*/
/* Table: FUNCROLE                                              */
/*==============================================================*/
create table FUNCROLE  (
   ROLECODE             VARCHAR(20),
   FUNCCODE             VARCHAR(20)
);

comment on table FUNCROLE is
'角色功能表';

comment on column FUNCROLE.ROLECODE is
'角色编码';

comment on column FUNCROLE.FUNCCODE is
'功能编码';

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
