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
INSERT INTO USER_MENU VALUES('006','053','1');
INSERT INTO USER_MENU VALUES('006','002','1');
INSERT INTO USER_MENU VALUES('006','021','1');
INSERT INTO USER_MENU VALUES('004','106','1');
