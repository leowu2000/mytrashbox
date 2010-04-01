ALTER TABLE EMPLOYEE ADD COLUMN STCPHONE2 VARCHAR(20) NULL;

ALTER TABLE PLAN ADD COLUMN LEVEL VARCHAR(20) NULL;
ALTER TABLE PLAN ADD COLUMN TYPE VARCHAR(20) NULL;

DROP VIEW VIEW_PLAN;
create or replace view VIEW_PLAN as 
select a.*,b.NAME as PJNAME,c.NAME as PJNAME_D,d.NAME as STAGENAME,e.NAME as LEVELNAME,f.NAME as TYPENAME
	from PLAN a, PROJECT b, PROJECT_D c, DICT d, DICT e, DICT f
	where b.CODE=a.PJCODE and c.CODE=a.PJCODE_D and d.CODE=a.STAGECODE and d.TYPE='5' and e.CODE=a.LEVEL and e.TYPE='6' and f.CODE=a.TYPE and f.TYPE='7';

drop table EMP_FINANCIAL;

/*==============================================================*/
/* Table: EMP_FINANCIAL                                         */
/*==============================================================*/
create table EMP_FINANCIAL  (
   ID                   VARCHAR(32)                     not null,
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   RQ                   DATE,
   JBF                  NUMERIC(10, 2),
   PSF                  NUMERIC(10, 2),
   GC                   NUMERIC(10, 2),
   CJ                   NUMERIC(10, 2),
   WCBT                 NUMERIC(10, 2),
   CGLBT                NUMERIC(10, 2),
   LB                   NUMERIC(10, 2),
   GJBT                 NUMERIC(10, 2),
   FPBT                 NUMERIC(10, 2),
   XMMC                 VARCHAR(50),
   BZ                   VARCHAR(500),
   constraint PK_EMP_FINANCIAL primary key (ID)
);

comment on table EMP_FINANCIAL is
'员工财务表';

comment on column EMP_FINANCIAL.ID is
'ID';

comment on column EMP_FINANCIAL.EMPCODE is
'人员编号';

comment on column EMP_FINANCIAL.EMPNAME is
'姓名';

comment on column EMP_FINANCIAL.DEPARTCODE is
'部门编码';

comment on column EMP_FINANCIAL.DEPARTNAME is
'部门名称';

comment on column EMP_FINANCIAL.RQ is
'日期';

comment on column EMP_FINANCIAL.JBF is
'加班费';

comment on column EMP_FINANCIAL.PSF is
'评审费';

comment on column EMP_FINANCIAL.GC is
'稿酬';

comment on column EMP_FINANCIAL.CJ is
'酬金';

comment on column EMP_FINANCIAL.WCBT is
'外场补贴';

comment on column EMP_FINANCIAL.CGLBT is
'车公里补贴';

comment on column EMP_FINANCIAL.LB is
'劳保';

comment on column EMP_FINANCIAL.GJBT is
'过江补贴';

comment on column EMP_FINANCIAL.FPBT is
'返聘补贴';

comment on column EMP_FINANCIAL.XMMC is
'项目名称';

comment on column EMP_FINANCIAL.BZ is
'备注';

drop table EMP_CARD;

/*==============================================================*/
/* Table: EMP_CARD                                              */
/*==============================================================*/
create table EMP_CARD  (
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   SEX                  VARCHAR(1),
   CARDNO               VARCHAR(20)                     not null,
   PHONE1               VARCHAR(20),
   PHONE2               VARCHAR(20),
   ADDRESS              VARCHAR(500),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   constraint PK_EMP_CARD primary key (CARDNO)
);

comment on table EMP_CARD is
'员工一卡通表';

comment on column EMP_CARD.EMPCODE is
'员工编号 ';

comment on column EMP_CARD.EMPNAME is
'员工姓名';

comment on column EMP_CARD.SEX is
'员工性别(1:男性;2:女性)';

comment on column EMP_CARD.CARDNO is
'卡号';

comment on column EMP_CARD.PHONE1 is
'电话1';

comment on column EMP_CARD.PHONE2 is
'电话2';

comment on column EMP_CARD.ADDRESS is
'地址';

comment on column EMP_CARD.DEPARTCODE is
'部门编码';

comment on column EMP_CARD.DEPARTNAME is
'部门名称';

drop table EMP_POS;

/*==============================================================*/
/* Table: EMP_POS                                               */
/*==============================================================*/
create table EMP_POS  (
   ID                   VARCHAR(32),
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   CARDNO               VARCHAR(20),
   POSMACHINE           VARCHAR(50),
   SWIPETIME            TIMESTAMP,
   COST                 NUMERIC(10,2),
   POSCODE              INTEGER
);

comment on table EMP_POS is
'班车刷卡表';

comment on column EMP_POS.ID is
'ID';

comment on column EMP_POS.EMPCODE is
'人员编号';

comment on column EMP_POS.EMPNAME is
'人员姓名';

comment on column EMP_POS.DEPARTCODE is
'部门编号';

comment on column EMP_POS.DEPARTNAME is
'部门名称';

comment on column EMP_POS.CARDNO is
'卡号';

comment on column EMP_POS.POSMACHINE is
'车载POS机';

comment on column EMP_POS.SWIPETIME is
'刷卡时间';

comment on column EMP_POS.COST is
'刷卡金额';

comment on column EMP_POS.POSCODE is
'POS流水号';

/*==============================================================*/
/* Table: TRAIN                                                 */
/*==============================================================*/
create table TRAIN  (
   ID                   VARCHAR(32)                     not null,
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   TRAINID              VARCHAR(32),
   TRAINNAME            VARCHAR(100),
   ASSESS               VARCHAR(20),
   constraint PK_TRAIN primary key (ID)
);

comment on table TRAIN is
'培训表';

comment on column TRAIN.ID is
'ID';

comment on column TRAIN.EMPCODE is
'员工编号';

comment on column TRAIN.EMPNAME is
'员工姓名';

comment on column TRAIN.TRAINID is
'培训ID';

comment on column TRAIN.TRAINNAME is
'培训名称';

comment on column TRAIN.ASSESS is
'培训考核';

/*==============================================================*/
/* Table: TRAIN_D                                               */
/*==============================================================*/
create table TRAIN_D  (
   ID                   VARCHAR(32)                     not null,
   NAME                 VARCHAR(100),
   TARGET               VARCHAR(1000),
   PLAN                 VARCHAR(2000),
   RECORD               VARCHAR(2000),
   RESULT               VARCHAR(2000),
   COST                 NUMERIC(12,2),
   STARTDATE            DATE,
   ENDDATE              DATE,
   constraint PK_TRAIN_D primary key (ID)
);

comment on table TRAIN_D is
'培训详细表';

comment on column TRAIN_D.ID is
'ID';

comment on column TRAIN_D.NAME is
'培训名称';

comment on column TRAIN_D.TARGET is
'培训目标';

comment on column TRAIN_D.PLAN is
'培训计划';

comment on column TRAIN_D.RECORD is
'培训过程记录';

comment on column TRAIN_D.RESULT is
'培训结果记录';

comment on column TRAIN_D.COST is
'培训成本';

comment on column TRAIN_D.STARTDATE is
'培训开始时间';

comment on column TRAIN_D.ENDDATE is
'培训结束时间';

create or replace view VIEW_TRAIN as
select a.*,b.NAME,b.TARGET,b.PLAN,b.RECORD,b.RESULT,b.COST from TRAIN a,TRAIN_D b where b.ID=a.TRAINID;

/*==============================================================*/
/* Table: PLAN_PERSENT                                          */
/*==============================================================*/
create table PLAN_PERSENT  (
   ID                   VARCHAR(32),
   NAME                 VARCHAR(20),
   STARTPERSENT         NUMERIC(5,2),
   ENDPERSENT           NUMERIC(5,2),
   COLOR                VARCHAR(10),
   ORDERCODE            INTEGER
);

comment on table PLAN_PERSENT is
'计划完成情况维护表';

comment on column PLAN_PERSENT.ID is
'ID';

comment on column PLAN_PERSENT.NAME is
'名称';

comment on column PLAN_PERSENT.STARTPERSENT is
'起始百分率';

comment on column PLAN_PERSENT.ENDPERSENT is
'截止百分率';

comment on column PLAN_PERSENT.COLOR is
'颜色';

comment on column PLAN_PERSENT.ORDERCODE is
'排序号';

/*==============================================================*/
/* Table: HOLIDAY                                               */
/*==============================================================*/
create table HOLIDAY  (
   HOLIDAY              DATE                            not null,
   NAME                 VARCHAR(50),
   VALID                VARCHAR(10),
   constraint PK_HOLIDAY primary key (HOLIDAY)
);

comment on table HOLIDAY is
'节假日';

comment on column HOLIDAY.HOLIDAY is
'节假日';

comment on column HOLIDAY.NAME is
'节日名称';

comment on column HOLIDAY.VALID is
'是否有效';


DELETE FROM MENU;

INSERT INTO MENU VALUES('001','系统维护','2','',1,'1','0','1.png');
INSERT INTO MENU VALUES('011','工作令管理','1','pj.do?action=list',2,'1','001','1.png');
INSERT INTO MENU VALUES('012','部门管理','1','depart.do?action=list',3,'1','001','1.png');
INSERT INTO MENU VALUES('013','用户管理','1','em.do?action=frame_infolist',4,'1','001','1.png');
INSERT INTO MENU VALUES('014','菜单管理','1','menu.do?action=manage',5,'1','001','1.png');
INSERT INTO MENU VALUES('100','综合查询','2','',6,'1','0','10.png');
INSERT INTO MENU VALUES('101','人员信息综合查询','1','search.do?action=frame_search',7,'1','100','10.png');
INSERT INTO MENU VALUES('102','个人信息综合查询','1','search.do?action=self_search',9,'1','100','10.png');
INSERT INTO MENU VALUES('002','工作报告','2','',9,'1','0','2.png');
INSERT INTO MENU VALUES('021','个人工作报告','1','workreport.do?action=list',10,'1','002','2.png');
INSERT INTO MENU VALUES('022','审核工作报告','1','workreport.do?action=auditlist',11,'1','002','2.png');
INSERT INTO MENU VALUES('003','统计汇总','2','',12,'1','0','3.png');
INSERT INTO MENU VALUES('031','工时统计汇总','1','modules/pj/frame_gstjhz.jsp',13,'1','003','3.png');
INSERT INTO MENU VALUES('032','科研工时统计','1','pj.do?action=frame_kygstj',14,'1','003','3.png');
INSERT INTO MENU VALUES('033','承担任务情况','1','pj.do?action=frame_cdrwqk',15,'1','003','3.png');
INSERT INTO MENU VALUES('004','人事管理','2','',17,'1','0','4.png');
INSERT INTO MENU VALUES('041','人事管理','1','em.do?action=frame_manage',17,'1','004','4.png');
INSERT INTO MENU VALUES('042','财务管理','1','finance.do?action=frame_manage',18,'1','004','4.png');
INSERT INTO MENU VALUES('043','一卡通管理','1','card.do?action=frame_manage',19,'1','004','4.png');
INSERT INTO MENU VALUES('044','班车信息','1','pos.do?action=frame_manage',20,'1','004','4.png');
INSERT INTO MENU VALUES('045','培训管理','1','train.do?action=list_manage',21,'1','004','4.png');
INSERT INTO MENU VALUES('046','培训统计','1','train.do?action=frame_pxtj',21,'1','004','4.png');
INSERT INTO MENU VALUES('005','固定资产管理','2','',22,'1','0','5.png');
INSERT INTO MENU VALUES('051','固定资产查询','1','assets.do?action=frame_info&manage=0',23,'1','005','5.png');
INSERT INTO MENU VALUES('052','固定资产维护','1','assets.do?action=frame_info&manage=1',24,'1','005','5.png');
INSERT INTO MENU VALUES('053','年检提醒','1','assets.do?action=remind',25,'1','005','5.png');
INSERT INTO MENU VALUES('006','物资管理','2','',26,'1','0','6.png');
INSERT INTO MENU VALUES('061','物资管理','1','goods.do?action=list',27,'1','006','6.png');
INSERT INTO MENU VALUES('007','考勤管理','2','',28,'1','0','7.png');
INSERT INTO MENU VALUES('071','员工考勤记录','1','em.do?action=frame_workcheck',29,'1','007','7.png');
INSERT INTO MENU VALUES('008','计划管理','2','',30,'1','0','8.png');
INSERT INTO MENU VALUES('081','计划管理','1','plan.do?action=list_frame',31,'1','008','8.png');
INSERT INTO MENU VALUES('082','考核统计','1','plan.do?action=remind_frame',32,'1','008','8.png');
INSERT INTO MENU VALUES('083','考核结果','1','plan.do?action=result_list',33,'1','008','8.png');
INSERT INTO MENU VALUES('009','收藏管理','2','',34,'1','0','9.png');
INSERT INTO MENU VALUES('091','收藏菜单','1','menu.do?action=manage_favor',35,'1','009','9.png');


DELETE FROM USER_MENU;

/** 用户-菜单表 **/
/** 系统管理员 **/
INSERT INTO USER_MENU VALUES('001','001','1');
INSERT INTO USER_MENU VALUES('001','011','1');
INSERT INTO USER_MENU VALUES('001','012','1');
INSERT INTO USER_MENU VALUES('001','013','1');
INSERT INTO USER_MENU VALUES('001','014','1');
INSERT INTO USER_MENU VALUES('001','009','1');
INSERT INTO USER_MENU VALUES('001','091','1');

/** 部领导 **/
INSERT INTO USER_MENU VALUES('002','003','1');
INSERT INTO USER_MENU VALUES('002','031','1');
INSERT INTO USER_MENU VALUES('002','032','1');
INSERT INTO USER_MENU VALUES('002','033','1');
INSERT INTO USER_MENU VALUES('002','009','1');
INSERT INTO USER_MENU VALUES('002','091','1');
INSERT INTO USER_MENU VALUES('002','100','1');
INSERT INTO USER_MENU VALUES('002','101','1');

/** 普通人员 **/
INSERT INTO USER_MENU VALUES('003','002','1');
INSERT INTO USER_MENU VALUES('003','021','1');
INSERT INTO USER_MENU VALUES('003','100','1');
INSERT INTO USER_MENU VALUES('003','102','1');

/** 计划员 **/
INSERT INTO USER_MENU VALUES('004','008','1');
INSERT INTO USER_MENU VALUES('004','081','1');
INSERT INTO USER_MENU VALUES('004','082','1');
INSERT INTO USER_MENU VALUES('004','009','1');
INSERT INTO USER_MENU VALUES('004','091','1');

/** 组长 **/
INSERT INTO USER_MENU VALUES('005','002','1');
INSERT INTO USER_MENU VALUES('005','021','1');
INSERT INTO USER_MENU VALUES('005','022','1');
INSERT INTO USER_MENU VALUES('005','007','1');
INSERT INTO USER_MENU VALUES('005','071','1');
INSERT INTO USER_MENU VALUES('005','008','1');
INSERT INTO USER_MENU VALUES('005','081','1');
INSERT INTO USER_MENU VALUES('005','082','1');
INSERT INTO USER_MENU VALUES('005','009','1');
INSERT INTO USER_MENU VALUES('005','091','1');
INSERT INTO USER_MENU VALUES('005','100','1');
INSERT INTO USER_MENU VALUES('005','101','1');

/** 固定资产管理员 **/
INSERT INTO USER_MENU VALUES('006','005','1');
INSERT INTO USER_MENU VALUES('006','052','1');
INSERT INTO USER_MENU VALUES('006','006','1');
INSERT INTO USER_MENU VALUES('006','061','1');
INSERT INTO USER_MENU VALUES('006','009','1');
INSERT INTO USER_MENU VALUES('006','091','1');

/** 人事管理员 **/
INSERT INTO USER_MENU VALUES('007','004','1');
INSERT INTO USER_MENU VALUES('007','041','1');
INSERT INTO USER_MENU VALUES('007','042','1');
INSERT INTO USER_MENU VALUES('007','043','1');
INSERT INTO USER_MENU VALUES('007','044','1');
INSERT INTO USER_MENU VALUES('007','045','1');
INSERT INTO USER_MENU VALUES('007','046','1');
INSERT INTO USER_MENU VALUES('007','009','1');
INSERT INTO USER_MENU VALUES('007','091','1');
INSERT INTO USER_MENU VALUES('007','100','1');
INSERT INTO USER_MENU VALUES('007','101','1');

INSERT INTO USER_MENU VALUES('000000','001','2');
INSERT INTO USER_MENU VALUES('000000','011','2');
INSERT INTO USER_MENU VALUES('000000','012','2');
INSERT INTO USER_MENU VALUES('000000','013','2');
INSERT INTO USER_MENU VALUES('000000','014','2');

INSERT INTO USER_MENU VALUES('000001','003','2');
INSERT INTO USER_MENU VALUES('000001','031','2');
INSERT INTO USER_MENU VALUES('000001','032','2');
INSERT INTO USER_MENU VALUES('000001','033','2');
INSERT INTO USER_MENU VALUES('000001','100','2');
INSERT INTO USER_MENU VALUES('000001','101','2');


INSERT INTO USER_MENU VALUES('000003','008','2');
INSERT INTO USER_MENU VALUES('000003','081','2');
INSERT INTO USER_MENU VALUES('000003','082','2');

INSERT INTO USER_MENU VALUES('000004','002','2');
INSERT INTO USER_MENU VALUES('000004','022','2');
INSERT INTO USER_MENU VALUES('000004','007','2');
INSERT INTO USER_MENU VALUES('000004','071','2');

INSERT INTO USER_MENU VALUES('000005','005','2');
INSERT INTO USER_MENU VALUES('000005','052','2');
INSERT INTO USER_MENU VALUES('000005','006','2');
INSERT INTO USER_MENU VALUES('000005','061','2');

INSERT INTO USER_MENU VALUES('000006','004','2');
INSERT INTO USER_MENU VALUES('000006','041','2');
INSERT INTO USER_MENU VALUES('000006','042','2');
INSERT INTO USER_MENU VALUES('000006','043','2');
INSERT INTO USER_MENU VALUES('000006','044','2');
INSERT INTO USER_MENU VALUES('000006','045','2');
INSERT INTO USER_MENU VALUES('000006','046','2');
INSERT INTO USER_MENU VALUES('000006','100','2');
INSERT INTO USER_MENU VALUES('000006','101','2');

INSERT INTO USER_MENU VALUES('000002','002','2');
INSERT INTO USER_MENU VALUES('000002','021','2');
INSERT INTO USER_MENU VALUES('000002','100','2');
INSERT INTO USER_MENU VALUES('000002','102','2');
INSERT INTO USER_MENU VALUES('000007','002','2');
INSERT INTO USER_MENU VALUES('000007','021','2');
INSERT INTO USER_MENU VALUES('000007','100','2');
INSERT INTO USER_MENU VALUES('000007','102','2');
INSERT INTO USER_MENU VALUES('000008','002','2');
INSERT INTO USER_MENU VALUES('000008','021','2');
INSERT INTO USER_MENU VALUES('000008','100','2');
INSERT INTO USER_MENU VALUES('000008','102','2');
INSERT INTO USER_MENU VALUES('000009','002','2');
INSERT INTO USER_MENU VALUES('000009','021','2');
INSERT INTO USER_MENU VALUES('000009','100','2');
INSERT INTO USER_MENU VALUES('000009','102','2');

INSERT INTO EMP_CARD VALUES('000001','张三','1','0558000001','','','','1','三部');
INSERT INTO EMP_CARD VALUES('000002','赵六','1','0558000002','','','','1','三部');
INSERT INTO EMP_CARD VALUES('000003','李四','1','0558000003','','','','1','三部');
INSERT INTO EMP_CARD VALUES('000004','王五','1','0558000004','','','','1','三部');
INSERT INTO EMP_CARD VALUES('000005','孙七','1','0558000005','','','','1','三部');
INSERT INTO EMP_CARD VALUES('000006','周八','1','0558000006','','','','1','三部');
INSERT INTO EMP_CARD VALUES('000007','吴九','1','0558000007','','','','1','三部');

INSERT INTO EMP_FINANCIAL VALUES('1','000001','张三','1','三部','2010-03-15',100,20,2000,3000,500,500,50,0,0,'工作令一','');
INSERT INTO EMP_FINANCIAL VALUES('2','000002','赵六','1','三部','2010-03-15',120,30,1000,3000,400,500,50,0,0,'工作令二','');
INSERT INTO EMP_FINANCIAL VALUES('3','000003','李四','1','三部','2010-03-15',140,40,3000,3000,500,500,50,0,0,'工作令三','');
INSERT INTO EMP_FINANCIAL VALUES('4','000004','王五','1','三部','2010-03-15',160,60,2000,3000,200,500,50,0,0,'工作令四','');
INSERT INTO EMP_FINANCIAL VALUES('5','000005','孙七','1','三部','2010-03-15',180,50,4000,3000,300,500,50,0,0,'工作令五','');
INSERT INTO EMP_FINANCIAL VALUES('6','000006','周八','1','三部','2010-03-15',200,70,200,3000,100,500,50,0,0,'工作令六','');
INSERT INTO EMP_FINANCIAL VALUES('7','000007','吴九','1','三部','2010-03-15',220,80,20,3000,50,500,50,0,0,'工作令七','');

INSERT INTO EMP_POS VALUES('1','000001','张三','1','三部','0558000001','所POS1','2010-03-05 11:48:50',0.00,1);
INSERT INTO EMP_POS VALUES('2','000002','赵六','1','三部','0558000002','所POS1','2010-03-05 11:49:50',0.00,2);
INSERT INTO EMP_POS VALUES('3','000003','李四','1','三部','0558000003','所POS1','2010-03-05 11:50:50',0.00,3);
INSERT INTO EMP_POS VALUES('4','000004','王五','1','三部','0558000004','所POS1','2010-03-05 11:51:50',0.00,4);
INSERT INTO EMP_POS VALUES('5','000005','孙七','1','三部','0558000005','所POS1','2010-03-05 11:52:50',0.00,5);
INSERT INTO EMP_POS VALUES('6','000006','周八','1','三部','0558000006','所POS1','2010-03-05 11:53:50',0.00,6);
INSERT INTO EMP_POS VALUES('7','000007','吴九','1','三部','0558000007','所POS1','2010-03-05 11:54:50',0.00,7);

DELETE FROM GOODS;
INSERT INTO GOODS VALUES('1',2009,'01','CC090119299',704.65,'三部','1','三部','1','赵六','000002','MR091122845','集成电路 AM29','AM29','3','00001','块儿',16,'44.040625','拼装','0560340027');
INSERT INTO GOODS VALUES('2',2009,'01','CC090119311',240.30,'三部','1','三部','1','吴九','000007','MR091092637','电容器 CA45E','CA45E','4','00001','个',50,'4.806','拼装','06714135710120');

DELETE FROM PLAN;
INSERT INTO PLAN VALUES('1','000002','赵六','1','三部','1','F01','500003','2010-03-10','2010-03-30',50,'科研生产','标志','考核','备注','所领导','部领导','室领导','000003','李四',1);

INSERT INTO DICT VALUES('','',5);

UPDATE ASSETS SET DEPARTCODE='1' WHERE ID='5';

INSERT INTO PLAN_PERSENT VALUES('1','初始运行',0,20,'green',1);
INSERT INTO PLAN_PERSENT VALUES('2','正常运行',20,60,'blue',2);
INSERT INTO PLAN_PERSENT VALUES('3','后期运行',60,80,'orange',3);
INSERT INTO PLAN_PERSENT VALUES('4','结尾阶段',80,100,'red',4);

INSERT INTO DICT VALUES('600001','一星','6');
INSERT INTO DICT VALUES('600002','二星','6');
INSERT INTO DICT VALUES('600003','三星','6');
INSERT INTO DICT VALUES('','','6');
INSERT INTO DICT VALUES('700001','机载','7');
INSERT INTO DICT VALUES('700002','舰载','7');
INSERT INTO DICT VALUES('','','7');