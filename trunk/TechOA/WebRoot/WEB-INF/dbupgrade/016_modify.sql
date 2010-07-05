/*==============================================================*/
/* Table: CONTRACT_APPLY                                        */
/*==============================================================*/
create table CONTRACT_APPLY  (
   ID                   VARCHAR(32)                     not null,
   PJCODE               VARCHAR(200),
   CODE                 VARCHAR(200),
   NAME                 VARCHAR(200),
   "LEVEL"              VARCHAR(20),
   SFXT                 VARCHAR(200),
   MJ                   VARCHAR(20),
   SFZJ                 VARCHAR(20),
   ENDDATE              DATE,
   WXSL                 INTEGER,
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(20),
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(20),
   CONTRACTID           VARCHAR(32),
   constraint PK_CONTRACT_APPLY primary key (ID)
);

comment on table CONTRACT_APPLY is
'立项申请表';

comment on column CONTRACT_APPLY.ID is
'ID';

comment on column CONTRACT_APPLY.PJCODE is
'产品令号';

comment on column CONTRACT_APPLY.CODE is
'项目编号';

comment on column CONTRACT_APPLY.NAME is
'项目名称';

comment on column CONTRACT_APPLY."LEVEL" is
'项目等级';

comment on column CONTRACT_APPLY.SFXT is
'属分系统';

comment on column CONTRACT_APPLY.MJ is
'密级';

comment on column CONTRACT_APPLY.SFZJ is
'是否装机';

comment on column CONTRACT_APPLY.ENDDATE is
'计划完成时间';

comment on column CONTRACT_APPLY.WXSL is
'外协数量';

comment on column CONTRACT_APPLY.DEPARTCODE is
'申报单位编码';

comment on column CONTRACT_APPLY.DEPARTNAME is
'申报单位名称';

comment on column CONTRACT_APPLY.EMPCODE is
'项目申报人编码';

comment on column CONTRACT_APPLY.EMPNAME is
'项目申报人姓名';

comment on column CONTRACT_APPLY.CONTRACTID is
'合同编号';

/*==============================================================*/
/* Table: CONTRACT_BUDGET                                       */
/*==============================================================*/
create table CONTRACT_BUDGET  (
   ID                   VARCHAR(32)                     not null,
   APPLYCODE            VARCHAR(200),
   CODE                 VARCHAR(200),
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(20),
   FUNDS                NUMERIC(14,2),
   CONTRACTID           VARCHAR(32),
   constraint PK_CONTRACT_BUDGET primary key (ID)
);

comment on table CONTRACT_BUDGET is
'预算表';

comment on column CONTRACT_BUDGET.ID is
'ID';

comment on column CONTRACT_BUDGET.APPLYCODE is
'项目编号';

comment on column CONTRACT_BUDGET.CODE is
'预算单号';

comment on column CONTRACT_BUDGET.EMPCODE is
'提出人编码';

comment on column CONTRACT_BUDGET.EMPNAME is
'提出人姓名';

comment on column CONTRACT_BUDGET.FUNDS is
'经费估算';

comment on column CONTRACT_BUDGET.CONTRACTID is
'合同编号';
