CREATE TABLE "PUBLIC"."WORKCHECK_D"(
"ID" CHARACTER VARYING(32 CHAR) NOT NULL ,
"DEPART" CHARACTER VARYING(20 CHAR),
"DEPART2" CHARACTER VARYING(20 CHAR),
"EMPCODE" CHARACTER VARYING(20 CHAR),
"EMPNAME" CHARACTER VARYING(50 CHAR),
"STARTTIME" CHARACTER VARYING(20 CHAR),
"STARTLOCATION" CHARACTER VARYING(100 CHAR),
"ENDTIME" CHARACTER VARYING(20 CHAR),
"ENDLOCATION" CHARACTER VARYING(100 CHAR),
"DATE" DATE,
"EXPLAIN" CHARACTER VARYING(500 CHAR),
PRIMARY KEY("ID")
);

insert into DICT_TABLE values('WORKCHECK_D','考勤明细表','2');
insert into DICT_COL values('WORKCHECK_D', 'DEPART', '部门编号', 'CHAR', 20, '', '');
insert into DICT_COL values('WORKCHECK_D', 'DEPART2', '二级部门', 'CHAR', 20, '', '');
insert into DICT_COL values('WORKCHECK_D', 'EMPCODE', '人员编号', 'CHAR', 20, '', '');
insert into DICT_COL values('WORKCHECK_D', 'EMPNAME', '姓名', 'CHAR', 50, '', '');
insert into DICT_COL values('WORKCHECK_D', 'STARTTIME', '上班时间', 'CHAR', 20, '', '');
insert into DICT_COL values('WORKCHECK_D', 'STARTLOCATION', '上班刷卡地点', 'CHAR', 100, '', '');
insert into DICT_COL values('WORKCHECK_D', 'ENDTIME', '下班时间', 'CHAR', 100, '', '');
insert into DICT_COL values('WORKCHECK_D', 'ENDLOCATION', '下班刷卡位置', 'CHAR', 100, '', '');
insert into DICT_COL values('WORKCHECK_D', 'DATE', '日期', 'DATE', null, '', '');
insert into DICT_COL values('WORKCHECK_D', 'EXPLAIN', '说明', 'CHAR', 500, '', '');

CREATE TABLE "PUBLIC"."EAT_D"(
"ID" CHARACTER VARYING(32 CHAR) NOT NULL ,
"DEPART" CHARACTER VARYING(20 CHAR),
"DEPART2" CHARACTER VARYING(20 CHAR),
"EMPCODE" CHARACTER VARYING(20 CHAR),
"EMPNAME" CHARACTER VARYING(50 CHAR),
"EATTYPE" CHARACTER(20 CHAR),
"EATLOCATION" CHARACTER VARYING(100 CHAR),
"EATTIME" CHARACTER VARYING(50 CHAR),
"STARTTIME" CHARACTER VARYING(50 CHAR),
"ENDTIME" CHARACTER VARYING(50 CHAR),
"DATE" DATE,
"EXPLAIN" CHARACTER VARYING(500 CHAR),
PRIMARY KEY("ID")
);

insert into DICT_TABLE values('EAT_D','就餐明细表','2');
insert into DICT_COL values('EAT_D', 'DEPART', '一级部门', 'CHAR', 20, '', '');
insert into DICT_COL values('EAT_D', 'DEPART2', '二级部门', 'CHAR', 20, '', '');
insert into DICT_COL values('EAT_D', 'EMPCODE', '人员编号', 'CHAR', 20, '', '');
insert into DICT_COL values('EAT_D', 'EMPNAME', '姓名', 'CHAR', 50, '', '');
insert into DICT_COL values('EAT_D', 'EATTYPE', '用餐类型', 'CHAR', 20, '', '');
insert into DICT_COL values('EAT_D', 'EATLOCATION', '用餐地点', 'CHAR', 100, '', '');
insert into DICT_COL values('EAT_D', 'EATTIME', '用餐时间', 'CHAR', 50, '', '');
insert into DICT_COL values('EAT_D', 'STARTTIME', '上班时间', 'CHAR', 50, '', '');
insert into DICT_COL values('EAT_D', 'ENDTIME', '下班时间', 'CHAR', 50, '', '');
insert into DICT_COL values('EAT_D', 'DATE', '日期', 'DATE', null, '', '');
insert into DICT_COL values('EAT_D', 'EXPLAIN', '说明', 'CHAR', 500, '', '');

ALTER TABLE DICT_COL ADD COLUMN ORDERCODE INT;
