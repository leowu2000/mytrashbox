DROP VIEW VIEW_PLAN;
DROP TABLE PLAN;
/*==============================================================*/
/* Table: PLAN                                                  */
/*==============================================================*/
create table PLAN  (
   ID                   VARCHAR(32)                     not null,
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   PJCODE               VARCHAR(20),
   PJCODE_D             VARCHAR(20),
   STAGECODE            VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PLANEDWORKLOAD       INTEGER,
   NOTE                 VARCHAR(500),
   SYMBOL               VARCHAR(50),
   ASSESS               VARCHAR(50),
   REMARK               VARCHAR(200),
   LEADER_STATION       VARCHAR(50),
   LEADER_SECTION       VARCHAR(50),
   LEADER_ROOM          VARCHAR(50),
   PLANNERCODE          VARCHAR(20),
   PLANNERNAME          VARCHAR(50),
   ORDERCODE            INTEGER,
   TYPE                 VARCHAR(20),
   TYPE2                VARCHAR(20),
   STATUS               VARCHAR(10),
   constraint PK_PLAN primary key (ID)
);

comment on table PLAN is
'计划表';

comment on column PLAN.ID is
'ID';

comment on column PLAN.EMPCODE is
'责任人编码';

comment on column PLAN.EMPNAME is
'责任人姓名';

comment on column PLAN.DEPARTCODE is
'责任单位编码';

comment on column PLAN.DEPARTNAME is
'责任单位名称';

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
'计划内容';

comment on column PLAN.SYMBOL is
'标志';

comment on column PLAN.ASSESS is
'考核';

comment on column PLAN.REMARK is
'备注';

comment on column PLAN.LEADER_STATION is
'所领导';

comment on column PLAN.LEADER_SECTION is
'部领导';

comment on column PLAN.LEADER_ROOM is
'室领导';

comment on column PLAN.PLANNERCODE is
'计划员编码';

comment on column PLAN.PLANNERNAME is
'计划员姓名';

comment on column PLAN.ORDERCODE is
'序号';

comment on column PLAN.TYPE is
'科研生产计划类别';

comment on column PLAN.TYPE2 is
'科研生产计划类别2';

comment on column PLAN.STATUS is
'计划状态(1:新下发;2:已反馈;3:已确认;4:已完成)';

	
/*==============================================================*/
/* Table: PLAN_TYPE                                             */
/*==============================================================*/
create table PLAN_TYPE  (
   CODE                 VARCHAR(20)                     not null,
   NAME                 VARCHAR(50),
   ORDERCODE            INTEGER,
   TYPE                 VARCHAR(10),
   PARENT               VARCHAR(20),
   constraint PK_PLAN_TYPE primary key (CODE)
);

comment on table PLAN_TYPE is
'计划分类表';

comment on column PLAN_TYPE.CODE is
'分类代码';

comment on column PLAN_TYPE.NAME is
'分类名称';

comment on column PLAN_TYPE.ORDERCODE is
'排序号';

comment on column PLAN_TYPE.TYPE is
'分类(1:分类1;2:分类2)';

comment on column PLAN_TYPE.PARENT is
'父分类代码';

INSERT INTO PLAN_TYPE VALUES('000001','指令性项目计划完成率',1,'1','0');
INSERT INTO PLAN_TYPE VALUES('000002','科研性项目计划完成率',2,'1','0');
INSERT INTO PLAN_TYPE VALUES('000011','X类',1,'2','000001');
INSERT INTO PLAN_TYPE VALUES('000012','XX类',2,'2','000001');
INSERT INTO PLAN_TYPE VALUES('000021','XXX类',1,'2','000002');
INSERT INTO PLAN_TYPE VALUES('000022','XXXX类',2,'2','000002');

INSERT INTO PLAN VALUES('1','000002','赵六','1','三部','1','F01','500003','2010-04-10','2010-04-30',50,'科研生产','标志','**','备注','所领导','部领导','室领导','000003','李四',1,'000001','000012','1');
INSERT INTO PLAN VALUES('2','000008','郑十','1','三部','1','F01','500003','2010-04-10','2010-04-30',50,'科研生产','标志','*','备注','所领导','部领导','室领导','000003','李四',1,'000002','000021','1');
INSERT INTO PLAN VALUES('3','000009','刘一','1','三部','1','F01','500003','2010-04-10','2010-04-30',50,'科研生产','标志','***','备注','所领导','部领导','室领导','000003','李四',1,'000001','000011','1');

DELETE FROM MENU;

INSERT INTO MENU VALUES('001','系统维护','2','',1,'1','0','1.png');
INSERT INTO MENU VALUES('011','工作令管理','1','pj.do?action=list',2,'1','001','1.png');
INSERT INTO MENU VALUES('012','部门管理','1','depart.do?action=list',3,'1','001','1.png');
INSERT INTO MENU VALUES('013','用户管理','1','em.do?action=frame_infolist',4,'1','001','1.png');
INSERT INTO MENU VALUES('014','菜单管理','1','menu.do?action=manage',5,'1','001','1.png');
INSERT INTO MENU VALUES('100','综合信息','2','',6,'1','0','10.png');
INSERT INTO MENU VALUES('101','人员信息综合查询','1','search.do?action=frame_search',7,'1','100','10.png');
INSERT INTO MENU VALUES('102','个人信息综合查询','1','search.do?action=self_search',8,'1','100','10.png');
INSERT INTO MENU VALUES('103','个人信息管理','1','em.do?action=manage_self',9,'1','100','10.png');
INSERT INTO MENU VALUES('104','班车预约','1','car.do?action=list_order',10,'1','100','10.png');
INSERT INTO MENU VALUES('002','工作报告','2','',11,'1','0','2.png');
INSERT INTO MENU VALUES('021','个人工作报告','1','workreport.do?action=list',12,'1','002','2.png');
INSERT INTO MENU VALUES('022','审核工作报告','1','workreport.do?action=auditlist',13,'1','002','2.png');
INSERT INTO MENU VALUES('003','统计汇总','2','',14,'1','0','3.png');
INSERT INTO MENU VALUES('031','工时统计汇总','1','pj.do?action=frame_gstjhz',15,'1','003','3.png');
INSERT INTO MENU VALUES('032','科研工时统计','1','pj.do?action=frame_kygstj',16,'1','003','3.png');
INSERT INTO MENU VALUES('033','承担任务情况','1','pj.do?action=frame_cdrwqk',17,'1','003','3.png');
INSERT INTO MENU VALUES('004','人事管理','2','',18,'1','0','4.png');
INSERT INTO MENU VALUES('041','人事管理','1','em.do?action=frame_manage',19,'1','004','4.png');
INSERT INTO MENU VALUES('042','财务管理','1','finance.do?action=frame_manage',20,'1','004','4.png');
INSERT INTO MENU VALUES('043','一卡通管理','1','card.do?action=frame_manage',21,'1','004','4.png');
INSERT INTO MENU VALUES('047','班车管理','1','car.do?action=list_manage',22,'1','004','4.png');
INSERT INTO MENU VALUES('048','班车预约统计','1','car.do?action=frame_order_manage',23,'1','004','4.png');
INSERT INTO MENU VALUES('044','班车刷卡信息','1','pos.do?action=frame_manage',24,'1','004','4.png');
INSERT INTO MENU VALUES('045','培训管理','1','train.do?action=list_manage',25,'1','004','4.png');
INSERT INTO MENU VALUES('046','培训统计','1','train.do?action=frame_pxtj',26,'1','004','4.png');
INSERT INTO MENU VALUES('005','固定资产管理','2','',27,'1','0','5.png');
INSERT INTO MENU VALUES('051','信息设备维护','1','infoequip.do?action=manage',28,'1','005','5.png');
INSERT INTO MENU VALUES('052','固定资产维护','1','assets.do?action=frame_info&manage=1',29,'1','005','5.png');
INSERT INTO MENU VALUES('053','年检提醒','1','assets.do?action=remind',30,'1','005','5.png');
INSERT INTO MENU VALUES('006','物资管理','2','',31,'1','0','6.png');
INSERT INTO MENU VALUES('061','物资管理','1','goods.do?action=list',32,'1','006','6.png');
INSERT INTO MENU VALUES('007','考勤管理','2','',33,'1','0','7.png');
INSERT INTO MENU VALUES('071','员工考勤记录','1','em.do?action=frame_workcheck',34,'1','007','7.png');
INSERT INTO MENU VALUES('008','计划管理','2','',35,'1','0','8.png');
INSERT INTO MENU VALUES('084','计划分类管理','1','plantype.do?action=list',36,'1','008','8.png');
INSERT INTO MENU VALUES('081','计划管理','1','plan.do?action=list_frame',37,'1','008','8.png');
INSERT INTO MENU VALUES('082','考核统计','1','plan.do?action=remind_frame',38,'1','008','8.png');
INSERT INTO MENU VALUES('083','计划反馈','1','plan.do?action=feedback',39,'1','008','8.png');
INSERT INTO MENU VALUES('110','器材定制','2','',40,'1','0','11.png');
INSERT INTO MENU VALUES('111','个人器材定制','1','customequip.do?action=list',41,'1','110','11.png');
INSERT INTO MENU VALUES('112','器材定制管理','1','customequip.do?action=manage',42,'1','110','11.png');
INSERT INTO MENU VALUES('009','收藏管理','2','',43,'1','0','9.png');
INSERT INTO MENU VALUES('091','收藏菜单','1','menu.do?action=manage_favor',44,'1','009','9.png');

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

INSERT INTO USER_MENU VALUES('000000','001','2');
INSERT INTO USER_MENU VALUES('000000','011','2');
INSERT INTO USER_MENU VALUES('000000','012','2');
INSERT INTO USER_MENU VALUES('000000','013','2');
INSERT INTO USER_MENU VALUES('000000','014','2');

/** 部领导 **/
INSERT INTO USER_MENU VALUES('002','003','1');
INSERT INTO USER_MENU VALUES('002','031','1');
INSERT INTO USER_MENU VALUES('002','032','1');
INSERT INTO USER_MENU VALUES('002','033','1');
INSERT INTO USER_MENU VALUES('002','009','1');
INSERT INTO USER_MENU VALUES('002','091','1');
INSERT INTO USER_MENU VALUES('002','100','1');
INSERT INTO USER_MENU VALUES('002','101','1');

INSERT INTO USER_MENU VALUES('000001','003','2');
INSERT INTO USER_MENU VALUES('000001','031','2');
INSERT INTO USER_MENU VALUES('000001','032','2');
INSERT INTO USER_MENU VALUES('000001','033','2');
INSERT INTO USER_MENU VALUES('000001','100','2');
INSERT INTO USER_MENU VALUES('000001','101','2');

/** 计划员 **/
INSERT INTO USER_MENU VALUES('004','008','1');
INSERT INTO USER_MENU VALUES('004','084','1');
INSERT INTO USER_MENU VALUES('004','081','1');
INSERT INTO USER_MENU VALUES('004','082','1');
INSERT INTO USER_MENU VALUES('004','009','1');
INSERT INTO USER_MENU VALUES('004','091','1');
INSERT INTO USER_MENU VALUES('004','100','1');
INSERT INTO USER_MENU VALUES('004','102','1');
INSERT INTO USER_MENU VALUES('004','103','1');
INSERT INTO USER_MENU VALUES('004','104','1');

INSERT INTO USER_MENU VALUES('000003','008','2');
INSERT INTO USER_MENU VALUES('000003','081','2');
INSERT INTO USER_MENU VALUES('000003','082','2');
INSERT INTO USER_MENU VALUES('000003','100','2');
INSERT INTO USER_MENU VALUES('000003','102','2');
INSERT INTO USER_MENU VALUES('000003','103','2');
INSERT INTO USER_MENU VALUES('000003','104','2');

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
INSERT INTO USER_MENU VALUES('005','103','1');
INSERT INTO USER_MENU VALUES('005','104','1');

INSERT INTO USER_MENU VALUES('000004','002','2');
INSERT INTO USER_MENU VALUES('000004','022','2');
INSERT INTO USER_MENU VALUES('000004','007','2');
INSERT INTO USER_MENU VALUES('000004','071','2');
INSERT INTO USER_MENU VALUES('000004','008','2');
INSERT INTO USER_MENU VALUES('000004','084','2');
INSERT INTO USER_MENU VALUES('000004','081','2');
INSERT INTO USER_MENU VALUES('000004','082','2');
INSERT INTO USER_MENU VALUES('000004','100','2');
INSERT INTO USER_MENU VALUES('000004','101','2');
INSERT INTO USER_MENU VALUES('000004','103','2');
INSERT INTO USER_MENU VALUES('000004','104','2');

/** 固定资产管理员 **/
INSERT INTO USER_MENU VALUES('006','005','1');
INSERT INTO USER_MENU VALUES('006','052','1');
INSERT INTO USER_MENU VALUES('006','006','1');
INSERT INTO USER_MENU VALUES('006','061','1');
INSERT INTO USER_MENU VALUES('006','009','1');
INSERT INTO USER_MENU VALUES('006','091','1');
INSERT INTO USER_MENU VALUES('006','100','1');
INSERT INTO USER_MENU VALUES('006','102','1');
INSERT INTO USER_MENU VALUES('006','103','1');
INSERT INTO USER_MENU VALUES('006','104','1');
INSERT INTO USER_MENU VALUES('006','110','1');
INSERT INTO USER_MENU VALUES('006','112','1');

INSERT INTO USER_MENU VALUES('000005','005','2');
INSERT INTO USER_MENU VALUES('000005','052','2');
INSERT INTO USER_MENU VALUES('000005','006','2');
INSERT INTO USER_MENU VALUES('000005','061','2');
INSERT INTO USER_MENU VALUES('000005','100','2');
INSERT INTO USER_MENU VALUES('000005','102','2');
INSERT INTO USER_MENU VALUES('000005','103','2');
INSERT INTO USER_MENU VALUES('000005','104','2');
INSERT INTO USER_MENU VALUES('000005','110','2');
INSERT INTO USER_MENU VALUES('000005','112','2');

/** 人事管理员 **/
INSERT INTO USER_MENU VALUES('007','004','1');
INSERT INTO USER_MENU VALUES('007','041','1');
INSERT INTO USER_MENU VALUES('007','042','1');
INSERT INTO USER_MENU VALUES('007','043','1');
INSERT INTO USER_MENU VALUES('007','047','1');
INSERT INTO USER_MENU VALUES('007','048','1');
INSERT INTO USER_MENU VALUES('007','044','1');
INSERT INTO USER_MENU VALUES('007','045','1');
INSERT INTO USER_MENU VALUES('007','046','1');
INSERT INTO USER_MENU VALUES('007','009','1');
INSERT INTO USER_MENU VALUES('007','091','1');
INSERT INTO USER_MENU VALUES('007','100','1');
INSERT INTO USER_MENU VALUES('007','101','1');
INSERT INTO USER_MENU VALUES('007','103','1');
INSERT INTO USER_MENU VALUES('007','104','1');

INSERT INTO USER_MENU VALUES('000006','004','2');
INSERT INTO USER_MENU VALUES('000006','041','2');
INSERT INTO USER_MENU VALUES('000006','042','2');
INSERT INTO USER_MENU VALUES('000006','043','2');
INSERT INTO USER_MENU VALUES('000006','047','2');
INSERT INTO USER_MENU VALUES('000006','048','2');
INSERT INTO USER_MENU VALUES('000006','044','2');
INSERT INTO USER_MENU VALUES('000006','045','2');
INSERT INTO USER_MENU VALUES('000006','046','2');
INSERT INTO USER_MENU VALUES('000006','100','2');
INSERT INTO USER_MENU VALUES('000006','101','2');
INSERT INTO USER_MENU VALUES('000006','103','2');
INSERT INTO USER_MENU VALUES('000006','104','2');

/** 信息设备管理员 **/
INSERT INTO USER_MENU VALUES('008','005','1');
INSERT INTO USER_MENU VALUES('008','051','1');
INSERT INTO USER_MENU VALUES('008','009','1');
INSERT INTO USER_MENU VALUES('008','091','1');
INSERT INTO USER_MENU VALUES('008','100','1');
INSERT INTO USER_MENU VALUES('008','102','1');
INSERT INTO USER_MENU VALUES('008','103','1');
INSERT INTO USER_MENU VALUES('008','104','1');

UPDATE EMPLOYEE SET ROLECODE='008' WHERE CODE='000007';
INSERT INTO USER_MENU VALUES('000007','100','2');
INSERT INTO USER_MENU VALUES('000007','102','2');
INSERT INTO USER_MENU VALUES('000007','103','2');
INSERT INTO USER_MENU VALUES('000007','104','2');

/** 普通人员 **/
INSERT INTO USER_MENU VALUES('003','002','1');
INSERT INTO USER_MENU VALUES('003','021','1');
INSERT INTO USER_MENU VALUES('003','008','1');
INSERT INTO USER_MENU VALUES('003','083','1');
INSERT INTO USER_MENU VALUES('003','009','1');
INSERT INTO USER_MENU VALUES('003','091','1');
INSERT INTO USER_MENU VALUES('003','100','1');
INSERT INTO USER_MENU VALUES('003','102','1');
INSERT INTO USER_MENU VALUES('003','103','1');
INSERT INTO USER_MENU VALUES('003','104','1');
INSERT INTO USER_MENU VALUES('003','110','1');
INSERT INTO USER_MENU VALUES('003','111','1');

INSERT INTO USER_MENU VALUES('000002','002','2');
INSERT INTO USER_MENU VALUES('000002','021','2');
INSERT INTO USER_MENU VALUES('000002','008','2');
INSERT INTO USER_MENU VALUES('000002','083','2');
INSERT INTO USER_MENU VALUES('000002','100','2');
INSERT INTO USER_MENU VALUES('000002','102','2');
INSERT INTO USER_MENU VALUES('000002','103','2');
INSERT INTO USER_MENU VALUES('000002','104','2');
INSERT INTO USER_MENU VALUES('000002','110','2');
INSERT INTO USER_MENU VALUES('000002','111','2');
INSERT INTO USER_MENU VALUES('000008','002','2');
INSERT INTO USER_MENU VALUES('000008','021','2');
INSERT INTO USER_MENU VALUES('000008','008','2');
INSERT INTO USER_MENU VALUES('000008','083','2');
INSERT INTO USER_MENU VALUES('000008','100','2');
INSERT INTO USER_MENU VALUES('000008','102','2');
INSERT INTO USER_MENU VALUES('000008','103','2');
INSERT INTO USER_MENU VALUES('000008','104','2');
INSERT INTO USER_MENU VALUES('000008','110','2');
INSERT INTO USER_MENU VALUES('000008','111','2');
INSERT INTO USER_MENU VALUES('000009','002','2');
INSERT INTO USER_MENU VALUES('000009','021','2');
INSERT INTO USER_MENU VALUES('000009','008','2');
INSERT INTO USER_MENU VALUES('000009','083','2');
INSERT INTO USER_MENU VALUES('000009','100','2');
INSERT INTO USER_MENU VALUES('000009','102','2');
INSERT INTO USER_MENU VALUES('000009','103','2');
INSERT INTO USER_MENU VALUES('000009','104','2');
INSERT INTO USER_MENU VALUES('000009','110','2');
INSERT INTO USER_MENU VALUES('000009','111','2');

/*==============================================================*/
/* Table: CAR                                                   */
/*==============================================================*/
create table CAR  (
   ID                   VARCHAR(32)                     not null,
   CARCODE              VARCHAR(20),
   CARNO                VARCHAR(20),
   WAY                  VARCHAR(1000),
   DRIVERNAME           VARCHAR(50),
   PHONE                VARCHAR(50),
   SENDLOCATE           VARCHAR(200),
   constraint PK_CAR primary key (ID)
);

comment on table CAR is
'班车表';

comment on column CAR.ID is
'ID';

comment on column CAR.CARCODE is
'车次';

comment on column CAR.CARNO is
'班车车牌号';

comment on column CAR.WAY is
'班车路线';

comment on column CAR.DRIVERNAME is
'司机姓名';

comment on column CAR.PHONE is
'司机电话';

comment on column CAR.SENDLOCATE is
'发车地点';

/*==============================================================*/
/* Table: CAR_ORDER                                             */
/*==============================================================*/
create table CAR_ORDER  (
   ID                   VARCHAR(32)                     not null,
   CARID                VARCHAR(32),
   EMPCODE              VARCHAR(20),
   ORDERSENDTIME        VARCHAR(20),
   ORDERDATE            DATE,
   STATUS               VARCHAR(10),
   constraint PK_CAR_ORDER primary key (ID)
);

comment on table CAR_ORDER is
'班车预约表';

comment on column CAR_ORDER.ID is
'ID';

comment on column CAR_ORDER.CARID is
'车次';

comment on column CAR_ORDER.EMPCODE is
'人员编码';

comment on column CAR_ORDER.ORDERSENDTIME is
'预约发车时间';

comment on column CAR_ORDER.ORDERDATE is
'预约日期';

comment on column CAR_ORDER.STATUS is
'状态';


/*==============================================================*/
/* Table: CAR_SENDTIME                                          */
/*==============================================================*/
create table CAR_SENDTIME  (
   ID                   VARCHAR(32)                     not null,
   CARID                VARCHAR(32),
   SENDTIME             VARCHAR(20),
   constraint PK_CAR_SENDTIME primary key (ID)
);

comment on table CAR_SENDTIME is
'班车发车时间表';

comment on column CAR_SENDTIME.ID is
'ID';

comment on column CAR_SENDTIME.CARID is
'班车ID';

comment on column CAR_SENDTIME.SENDTIME is
'发车时间';

INSERT INTO CAR VALUES('1','1号','苏A54321','研究所-火车站','王师傅','13912345678','研究所门口');

INSERT INTO CAR_SENDTIME VALUES('111','1','15:00');
INSERT INTO CAR_SENDTIME VALUES('112','1','17:00');
INSERT INTO CAR_SENDTIME VALUES('113','1','19:00');
INSERT INTO CAR_SENDTIME VALUES('114','1','21:00');
INSERT INTO CAR_SENDTIME VALUES('115','1','23:00');

UPDATE MENU SET STATUS='0' WHERE MENUCODE IN ('110','111','112','051');
