INSERT INTO MENU VALUES('130','预算报表','2','',49,'1','0','5.png');
INSERT INTO MENU VALUES('131','增量预算','1','b_increase.do?action=frame',50,'1','130','5.png');
INSERT INTO MENU VALUES('132','所留预算','1','b_remain.do?action=frame',51,'1','130','5.png');
INSERT INTO MENU VALUES('133','预计合同','1','b_contract.do?action=frame',52,'1','130','5.png');
INSERT INTO MENU VALUES('134','预计到款','1','b_credited.do?action=frame',53,'1','130','5.png');

INSERT INTO USER_MENU VALUES('009','130','1');
INSERT INTO USER_MENU VALUES('009','131','1');
INSERT INTO USER_MENU VALUES('009','132','1');
INSERT INTO USER_MENU VALUES('009','133','1');
INSERT INTO USER_MENU VALUES('009','134','1');

/*==============================================================*/
/* Table: BUDGET_CONTRACT                                       */
/*==============================================================*/
create table BUDGET_CONTRACT  (
   ID                   VARCHAR(32)                     not null,
   YEAR                 INTEGER,
   ORDERCODE            INTEGER,
   NAME                 VARCHAR(200),
   PJCODE               VARCHAR(200),
   LEADER_STATION       VARCHAR(200),
   LEADER_TOP           VARCHAR(200),
   LEADER_SECTION       VARCHAR(200),
   MANAGER              VARCHAR(200),
   CONFIRM              VARCHAR(10),
   FUNDS                NUMERIC(14,2),
   FUNDS1               NUMERIC(14,2),
   FUNDS2               NUMERIC(14,2),
   FUNDS3               NUMERIC(14,2),
   FUNDS4               NUMERIC(14,2),
   NOTE                 VARCHAR(1000),
   constraint PK_BUDGET_CONTRACT primary key (ID)
);

comment on table BUDGET_CONTRACT is
'预计合同表';

comment on column BUDGET_CONTRACT.ID is
'ID';

comment on column BUDGET_CONTRACT.YEAR is
'年份';

comment on column BUDGET_CONTRACT.ORDERCODE is
'序号';

comment on column BUDGET_CONTRACT.NAME is
'项目名称';

comment on column BUDGET_CONTRACT.PJCODE is
'令号';

comment on column BUDGET_CONTRACT.LEADER_STATION is
'分管所领导';

comment on column BUDGET_CONTRACT.LEADER_TOP is
'分管首席';

comment on column BUDGET_CONTRACT.LEADER_SECTION is
'分管处领导';

comment on column BUDGET_CONTRACT.MANAGER is
'项目主管';

comment on column BUDGET_CONTRACT.CONFIRM is
'基本确定';

comment on column BUDGET_CONTRACT.FUNDS is
'金额';

comment on column BUDGET_CONTRACT.FUNDS1 is
'第一季度';

comment on column BUDGET_CONTRACT.FUNDS2 is
'第二季度';

comment on column BUDGET_CONTRACT.FUNDS3 is
'第三季度';

comment on column BUDGET_CONTRACT.FUNDS4 is
'第四季度';

comment on column BUDGET_CONTRACT.NOTE is
'说明';

/*==============================================================*/
/* Table: BUDGET_CREDITED                                       */
/*==============================================================*/
create table BUDGET_CREDITED  (
   ID                   VARCHAR(32)                     not null,
   YEAR                 INTEGER,
   ORDERCODE            INTEGER,
   NAME                 VARCHAR(200),
   PJCODE               VARCHAR(200),
   LEADER_STATION       VARCHAR(200),
   LEADER_TOP           VARCHAR(200),
   LEADER_SECTION       VARCHAR(200),
   MANAGER              VARCHAR(200),
   CONFIRM              VARCHAR(10),
   TRY                  VARCHAR(200),
   FUNDS                NUMERIC(14,2),
   FUNDS1               NUMERIC(14,2),
   FUNDS1_A             NUMERIC(14,2),
   FUNDS2               NUMERIC(14,2),
   FUNDS2_A             NUMERIC(14,2),
   FUNDS3               NUMERIC(14,2),
   FUNDS3_A             NUMERIC(14,2),
   FUNDS4               NUMERIC(14,2),
   FUNDS4_A             NUMERIC(14,2),
   NOTE                 VARCHAR(1000),
   constraint PK_BUDGET_CREDITED primary key (ID)
);

comment on table BUDGET_CREDITED is
'预计到款表';

comment on column BUDGET_CREDITED.ID is
'ID';

comment on column BUDGET_CREDITED.YEAR is
'年份';

comment on column BUDGET_CREDITED.ORDERCODE is
'序号';

comment on column BUDGET_CREDITED.NAME is
'项目名称';

comment on column BUDGET_CREDITED.PJCODE is
'令号';

comment on column BUDGET_CREDITED.LEADER_STATION is
'分管所领导';

comment on column BUDGET_CREDITED.LEADER_TOP is
'分管首席';

comment on column BUDGET_CREDITED.LEADER_SECTION is
'分管处领导';

comment on column BUDGET_CREDITED.MANAGER is
'项目主管';

comment on column BUDGET_CREDITED.CONFIRM is
'基本确定';

comment on column BUDGET_CREDITED.TRY is
'争取';

comment on column BUDGET_CREDITED.FUNDS is
'到款';

comment on column BUDGET_CREDITED.FUNDS1 is
'第一季度';

comment on column BUDGET_CREDITED.FUNDS1_A is
'第一季度已到款';

comment on column BUDGET_CREDITED.FUNDS2 is
'第二季度';

comment on column BUDGET_CREDITED.FUNDS2_A is
'第二季度已到款';

comment on column BUDGET_CREDITED.FUNDS3 is
'第三季度';

comment on column BUDGET_CREDITED.FUNDS3_A is
'第三季度已到款';

comment on column BUDGET_CREDITED.FUNDS4 is
'第四季度';

comment on column BUDGET_CREDITED.FUNDS4_A is
'第四季度已到款';

comment on column BUDGET_CREDITED.NOTE is
'说明';

/*==============================================================*/
/* Table: BUDGET_INCREASE                                       */
/*==============================================================*/
create table BUDGET_INCREASE  (
   ID                   VARCHAR(32)                     not null,
   YEAR                 INTEGER,
   ORDERCODE            INTEGER,
   NAME                 VARCHAR(200),
   LEADER_STATION       VARCHAR(200),
   LEADER_TOP           VARCHAR(200),
   BUDGET_FUNDS         NUMERIC(14,2),
   TYPE                 VARCHAR(20),
   AMOUNT               INTEGER,
   PLAN_NODE            VARCHAR(200),
   BUDGET_INCREASE      NUMERIC(14,2),
   FUNDS1               NUMERIC(14,2),
   FUNDS2               NUMERIC(14,2),
   FUNDS3               NUMERIC(14,2),
   FUNDS4               NUMERIC(14,2),
   PREFUNDS             NUMERIC(14,2),
   DEPART3              NUMERIC(14,2),
   DEPART5              NUMERIC(14,2),
   DEPART6              NUMERIC(14,2),
   DEPART4              NUMERIC(14,2),
   DEPART2              NUMERIC(14,2),
   DEPART9              NUMERIC(14,2),
   DEPART1              NUMERIC(14,2),
   DEPART10             NUMERIC(14,2),
   MANAGER              VARCHAR(200),
   constraint PK_BUDGET_INCREASE primary key (ID)
);

comment on table BUDGET_INCREASE is
'增量预算表';

comment on column BUDGET_INCREASE.ID is
'ID';

comment on column BUDGET_INCREASE.YEAR is
'年份';

comment on column BUDGET_INCREASE.ORDERCODE is
'序号';

comment on column BUDGET_INCREASE.NAME is
'名称';

comment on column BUDGET_INCREASE.LEADER_STATION is
'分管所领导';

comment on column BUDGET_INCREASE.LEADER_TOP is
'分管首席';

comment on column BUDGET_INCREASE.BUDGET_FUNDS is
'预算总经费';

comment on column BUDGET_INCREASE.TYPE is
'类别(研制/生产)';

comment on column BUDGET_INCREASE.AMOUNT is
'数量';

comment on column BUDGET_INCREASE.PLAN_NODE is
'计划节点';

comment on column BUDGET_INCREASE.BUDGET_INCREASE is
'十年预算增量';

comment on column BUDGET_INCREASE.FUNDS1 is
'第一季度';

comment on column BUDGET_INCREASE.FUNDS2 is
'第二季度';

comment on column BUDGET_INCREASE.FUNDS3 is
'第三季度';

comment on column BUDGET_INCREASE.FUNDS4 is
'第四季度';

comment on column BUDGET_INCREASE.PREFUNDS is
'预投';

comment on column BUDGET_INCREASE.DEPART3 is
'三部';

comment on column BUDGET_INCREASE.DEPART5 is
'五部';

comment on column BUDGET_INCREASE.DEPART6 is
'六部';

comment on column BUDGET_INCREASE.DEPART4 is
'四部';

comment on column BUDGET_INCREASE.DEPART2 is
'二部';

comment on column BUDGET_INCREASE.DEPART9 is
'九部';

comment on column BUDGET_INCREASE.DEPART1 is
'一部';

comment on column BUDGET_INCREASE.DEPART10 is
'装备部';

comment on column BUDGET_INCREASE.MANAGER is
'项目主管';

/*==============================================================*/
/* Table: BUDGET_REMAIN                                         */
/*==============================================================*/
create table BUDGET_REMAIN  (
   ID                   VARCHAR(32)                     not null,
   YEAR                 INTEGER,
   ORDERCODE            INTEGER,
   NAME                 VARCHAR(200),
   PJCODE               VARCHAR(200),
   REMAIN_PJ            NUMERIC(14,2),
   REMAIN_10YEAR        NUMERIC(14,2),
   REMAIN_10YEAR1       NUMERIC(14,2),
   REMAIN_10YEAR2       NUMERIC(14,2),
   LEADER_STATION       VARCHAR(200),
   LEADER_TOP           VARCHAR(200),
   LEADER_SECTION       VARCHAR(200),
   MANAGER              VARCHAR(200),
   constraint PK_BUDGET_REMAIN primary key (ID)
);

comment on table BUDGET_REMAIN is
'所留预算表';

comment on column BUDGET_REMAIN.ID is
'ID';

comment on column BUDGET_REMAIN.YEAR is
'年份';

comment on column BUDGET_REMAIN.ORDERCODE is
'序号';

comment on column BUDGET_REMAIN.NAME is
'项目名称';

comment on column BUDGET_REMAIN.PJCODE is
'令号';

comment on column BUDGET_REMAIN.REMAIN_PJ is
'令号所留';

comment on column BUDGET_REMAIN.REMAIN_10YEAR is
'十年所留';

comment on column BUDGET_REMAIN.REMAIN_10YEAR1 is
'其中分承包';

comment on column BUDGET_REMAIN.REMAIN_10YEAR2 is
'其中其他';

comment on column BUDGET_REMAIN.LEADER_STATION is
'分管所领导';

comment on column BUDGET_REMAIN.LEADER_TOP is
'分管首席专家';

comment on column BUDGET_REMAIN.LEADER_SECTION is
'分管处领导';

comment on column BUDGET_REMAIN.MANAGER is
'项目主管';
