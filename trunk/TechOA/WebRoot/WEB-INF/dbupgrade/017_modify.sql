update MENU set MENUNAME='预算汇总',MENUURL='c_budget.do?action=frame_budget_collect' where MENUCODE='113';

ALTER TABLE CONTRACT_BUDGET RENAME COLUMN CONTRACTID TO CONTRACTCODE;

/*==============================================================*/
/* Table: CONTRACT                                              */
/*==============================================================*/
create table CONTRACT  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(200),
   SUBJECT              VARCHAR(200),
   BDEPART              VARCHAR(200),
   PJCODE               VARCHAR(200),
   AMOUNT               NUMERIC(14,2),
   STAGE1               NUMERIC(14,2),
   STAGE2               NUMERIC(14,2),
   STAGE3               NUMERIC(14,2),
   STATUS               VARCHAR(10),
   constraint PK_CONTRACT primary key (ID)
);

comment on table CONTRACT is
'合同表';

comment on column CONTRACT.ID is
'ID';

comment on column CONTRACT.CODE is
'合同编号';

comment on column CONTRACT.SUBJECT is
'合同标的';

comment on column CONTRACT.BDEPART is
'对方单位';

comment on column CONTRACT.PJCODE is
'工作令号';

comment on column CONTRACT.AMOUNT is
'合同总额';

comment on column CONTRACT.STAGE1 is
'合同第一阶段付款';

comment on column CONTRACT.STAGE2 is
'合同第二阶段付款';

comment on column CONTRACT.STAGE3 is
'合同第三阶段付款';

comment on column CONTRACT.STATUS is
'状态(1:执行中;2:执行完毕)';

/*==============================================================*/
/* Table: CONTRACT_PAY                                          */
/*==============================================================*/
create table CONTRACT_PAY  (
   ID                   VARCHAR(32)                     not null,
   CONTRACTCODE         VARCHAR(200),
   BDEPART              VARCHAR(200),
   PJCODE               VARCHAR(200),
   PJCODE_D             VARCHAR(200),
   PAY                  NUMERIC(14,2),
   GOODSCODE            VARCHAR(200),
   LEADER_STATION       VARCHAR(200),
   PAYDATE              DATE,
   constraint PK_CONTRACT_PAY primary key (ID)
);

comment on column CONTRACT_PAY.ID is
'ID';

comment on column CONTRACT_PAY.CONTRACTCODE is
'合同编号';

comment on column CONTRACT_PAY.BDEPART is
'收款单位';

comment on column CONTRACT_PAY.PJCODE is
'工作令号';

comment on column CONTRACT_PAY.PJCODE_D is
'分系统';

comment on column CONTRACT_PAY.PAY is
'申请付款金额';

comment on column CONTRACT_PAY.GOODSCODE is
'归档号(物资编码)';

comment on column CONTRACT_PAY.LEADER_STATION is
'分管所领导';

comment on column CONTRACT_PAY.PAYDATE is
'付款申请日期';
