ALTER TABLE PROJECT ALTER COLUMN CODE TYPE VARCHAR(200);
ALTER TABLE PLAN ALTER COLUMN PJCODE TYPE VARCHAR(200);
ALTER TABLE PROJECT_D ALTER COLUMN PJCODE TYPE VARCHAR(200);
ALTER TABLE GOODS ALTER COLUMN PJCODE TYPE VARCHAR(200);
ALTER TABLE WORKREPORT ALTER COLUMN PJCODE TYPE VARCHAR(200);

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE  (
   CODE                 VARCHAR(20)                     not null,
   NAME                 VARCHAR(100),
   constraint PK_USER_ROLE primary key (CODE)
);

comment on table USER_ROLE is
'用户角色表';

comment on column USER_ROLE.CODE is
'CODE';

comment on column USER_ROLE.NAME is
'角色名称';

INSERT INTO USER_ROLE VALUES('001','系统管理员');
INSERT INTO USER_ROLE VALUES('002','领导');
INSERT INTO USER_ROLE VALUES('003','普通员工');
INSERT INTO USER_ROLE VALUES('004','计划员');
INSERT INTO USER_ROLE VALUES('005','组长');
INSERT INTO USER_ROLE VALUES('006','固定资产管理员');
INSERT INTO USER_ROLE VALUES('007','人事管理员');
INSERT INTO USER_ROLE VALUES('008','信息设备管理员');
INSERT INTO USER_ROLE VALUES('009','合同管理员');

/*==============================================================*/
/* Table: ROLE_DEPART                                           */
/*==============================================================*/
create table ROLE_DEPART  (
   ROLECODE             VARCHAR(20),
   DEPARTCODE           VARCHAR(200)
);

comment on table ROLE_DEPART is
'角色部门关联表';

comment on column ROLE_DEPART.ROLECODE is
'角色编码';

comment on column ROLE_DEPART.DEPARTCODE is
'部门编码';

/*==============================================================*/
/* Table: USER_DEPART                                           */
/*==============================================================*/
create table USER_DEPART  (
   EMPCODE              VARCHAR(20),
   DEPARTCODE           VARCHAR(20),
   ROLECODE             VARCHAR(20)
);

comment on table USER_DEPART is
'用户数据权限配置表';

comment on column USER_DEPART.EMPCODE is
'用户编码';

comment on column USER_DEPART.DEPARTCODE is
'部门编码';

comment on column USER_DEPART.ROLECODE is
'角色编码';


DELETE FROM MENU;

INSERT INTO MENU VALUES('001','系统维护','2','',1,'1','0','1.png');
INSERT INTO MENU VALUES('011','工作令管理','1','pj.do?action=list',2,'1','001','1.png');
INSERT INTO MENU VALUES('012','部门管理','1','depart.do?action=list',3,'1','001','1.png');
INSERT INTO MENU VALUES('013','用户管理','1','em.do?action=frame_infolist',4,'1','001','1.png');
INSERT INTO MENU VALUES('014','菜单管理','1','menu.do?action=manage',5,'1','001','1.png');
INSERT INTO MENU VALUES('015','角色管理','1','role.do?action=list',5,'1','001','1.png');
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
INSERT INTO MENU VALUES('034','员工投入分析','1','em.do?action=frame_ygtrfx',17,'1','003','3.png');
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
INSERT INTO MENU VALUES('052','固定资产维护','1','assets.do?action=frame_info&manage=1',29,'1','005','5.png');
INSERT INTO MENU VALUES('053','年检提醒','1','assets.do?action=remind',30,'1','005','5.png');
INSERT INTO MENU VALUES('006','物资管理','2','',31,'1','0','6.png');
INSERT INTO MENU VALUES('061','物资管理','1','goods.do?action=list',32,'1','006','6.png');
INSERT INTO MENU VALUES('007','考勤管理','2','',33,'1','0','7.png');
INSERT INTO MENU VALUES('071','员工考勤记录','1','em.do?action=frame_workcheck',34,'1','007','7.png');
INSERT INTO MENU VALUES('008','计划管理','2','',35,'1','0','8.png');
INSERT INTO MENU VALUES('084','计划分类管理','1','plantype.do?action=list',36,'1','008','8.png');
INSERT INTO MENU VALUES('081','计划管理','1','plan.do?action=list_frame',37,'1','008','8.png');
INSERT INTO MENU VALUES('085','计划管理(计划员专用)','1','plan.do?action=list_frame_planner',37,'1','008','8.png');
INSERT INTO MENU VALUES('082','考核统计','1','plan.do?action=remind_frame',38,'1','008','8.png');
INSERT INTO MENU VALUES('086','考核统计(计划员专用)','1','plan.do?action=remind_frame_planner',38,'1','008','8.png');
INSERT INTO MENU VALUES('083','计划反馈','1','plan.do?action=feedback',39,'1','008','8.png');
INSERT INTO MENU VALUES('110','合同管理','2','',40,'1','0','11.png');
INSERT INTO MENU VALUES('111','合同管理','1','customequip.do?action=list',41,'1','110','11.png');
INSERT INTO MENU VALUES('112','立项申报管理','1','customequip.do?action=manage',42,'1','110','11.png');
INSERT INTO MENU VALUES('113','预算管理','1','',43,'1','110','11.png');
INSERT INTO MENU VALUES('114','下月付款申请管理','1','',44,'1','110','11.png');
INSERT INTO MENU VALUES('120','信息设备管理','2','',45,'1','0','5.png');
INSERT INTO MENU VALUES('121','信息设备维护','1','infoequip.do?action=manage',46,'1','120','5.png');
INSERT INTO MENU VALUES('009','收藏管理','2','',47,'1','0','9.png');
INSERT INTO MENU VALUES('091','收藏菜单','1','menu.do?action=manage_favor',48,'1','009','9.png');

DELETE FROM USER_MENU;
/** 用户-菜单表 **/
/** 系统管理员 **/
INSERT INTO USER_MENU VALUES('001','001','1');
INSERT INTO USER_MENU VALUES('001','011','1');
INSERT INTO USER_MENU VALUES('001','012','1');
INSERT INTO USER_MENU VALUES('001','013','1');
INSERT INTO USER_MENU VALUES('001','014','1');
INSERT INTO USER_MENU VALUES('001','015','1');
INSERT INTO USER_MENU VALUES('001','009','1');
INSERT INTO USER_MENU VALUES('001','091','1');

/** 部领导 **/
INSERT INTO USER_MENU VALUES('002','002','1');
INSERT INTO USER_MENU VALUES('002','021','1');
INSERT INTO USER_MENU VALUES('002','022','1');
INSERT INTO USER_MENU VALUES('002','003','1');
INSERT INTO USER_MENU VALUES('002','031','1');
INSERT INTO USER_MENU VALUES('002','032','1');
INSERT INTO USER_MENU VALUES('002','033','1');
INSERT INTO USER_MENU VALUES('002','034','1');
INSERT INTO USER_MENU VALUES('002','008','1');
INSERT INTO USER_MENU VALUES('002','081','1');
INSERT INTO USER_MENU VALUES('002','082','1');
INSERT INTO USER_MENU VALUES('002','009','1');
INSERT INTO USER_MENU VALUES('002','091','1');
INSERT INTO USER_MENU VALUES('002','100','1');
INSERT INTO USER_MENU VALUES('002','101','1');
INSERT INTO USER_MENU VALUES('002','103','1');
INSERT INTO USER_MENU VALUES('002','104','1');

/** 普通人员 **/
INSERT INTO USER_MENU VALUES('003','002','1');
INSERT INTO USER_MENU VALUES('003','021','1');
INSERT INTO USER_MENU VALUES('003','007','1');
INSERT INTO USER_MENU VALUES('003','071','1');
INSERT INTO USER_MENU VALUES('003','008','1');
INSERT INTO USER_MENU VALUES('003','083','1');
INSERT INTO USER_MENU VALUES('003','009','1');
INSERT INTO USER_MENU VALUES('003','091','1');
INSERT INTO USER_MENU VALUES('003','100','1');
INSERT INTO USER_MENU VALUES('003','102','1');
INSERT INTO USER_MENU VALUES('003','103','1');
INSERT INTO USER_MENU VALUES('003','104','1');
INSERT INTO USER_MENU VALUES('003','130','1');
INSERT INTO USER_MENU VALUES('003','131','1');

/** 计划员 **/
INSERT INTO USER_MENU VALUES('004','002','1');
INSERT INTO USER_MENU VALUES('004','021','1');
INSERT INTO USER_MENU VALUES('004','008','1');
INSERT INTO USER_MENU VALUES('004','084','1');
INSERT INTO USER_MENU VALUES('004','085','1');
INSERT INTO USER_MENU VALUES('004','086','1');
INSERT INTO USER_MENU VALUES('004','009','1');
INSERT INTO USER_MENU VALUES('004','091','1');
INSERT INTO USER_MENU VALUES('004','100','1');
INSERT INTO USER_MENU VALUES('004','102','1');
INSERT INTO USER_MENU VALUES('004','103','1');
INSERT INTO USER_MENU VALUES('004','104','1');
INSERT INTO USER_MENU VALUES('004','130','1');
INSERT INTO USER_MENU VALUES('004','131','1');

/** 组长 **/
INSERT INTO USER_MENU VALUES('005','002','1');
INSERT INTO USER_MENU VALUES('005','021','1');
INSERT INTO USER_MENU VALUES('005','022','1');
INSERT INTO USER_MENU VALUES('005','007','1');
INSERT INTO USER_MENU VALUES('005','071','1');
INSERT INTO USER_MENU VALUES('005','008','1');
INSERT INTO USER_MENU VALUES('005','081','1');
INSERT INTO USER_MENU VALUES('005','082','1');
INSERT INTO USER_MENU VALUES('005','083','1');
INSERT INTO USER_MENU VALUES('005','009','1');
INSERT INTO USER_MENU VALUES('005','091','1');
INSERT INTO USER_MENU VALUES('005','100','1');
INSERT INTO USER_MENU VALUES('005','101','1');
INSERT INTO USER_MENU VALUES('005','103','1');
INSERT INTO USER_MENU VALUES('005','104','1');
INSERT INTO USER_MENU VALUES('005','130','1');
INSERT INTO USER_MENU VALUES('005','131','1');

/** 固定资产管理员 **/
INSERT INTO USER_MENU VALUES('006','005','1');
INSERT INTO USER_MENU VALUES('006','052','1');
INSERT INTO USER_MENU VALUES('006','006','1');
INSERT INTO USER_MENU VALUES('006','061','1');
INSERT INTO USER_MENU VALUES('006','062','1');
INSERT INTO USER_MENU VALUES('006','009','1');
INSERT INTO USER_MENU VALUES('006','091','1');
INSERT INTO USER_MENU VALUES('006','100','1');
INSERT INTO USER_MENU VALUES('006','102','1');
INSERT INTO USER_MENU VALUES('006','103','1');
INSERT INTO USER_MENU VALUES('006','104','1');

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
INSERT INTO USER_MENU VALUES('007','130','1');
INSERT INTO USER_MENU VALUES('007','132','1');

/** 信息设备管理员 **/
INSERT INTO USER_MENU VALUES('008','009','1');
INSERT INTO USER_MENU VALUES('008','091','1');
INSERT INTO USER_MENU VALUES('008','100','1');
INSERT INTO USER_MENU VALUES('008','102','1');
INSERT INTO USER_MENU VALUES('008','103','1');
INSERT INTO USER_MENU VALUES('008','104','1');
INSERT INTO USER_MENU VALUES('008','120','1');
INSERT INTO USER_MENU VALUES('008','121','1');

/** 合同管理员 **/
INSERT INTO USER_MENU VALUES('009','009','1');
INSERT INTO USER_MENU VALUES('009','091','1');
INSERT INTO USER_MENU VALUES('009','100','1');
INSERT INTO USER_MENU VALUES('009','102','1');
INSERT INTO USER_MENU VALUES('009','103','1');
INSERT INTO USER_MENU VALUES('009','104','1');
INSERT INTO USER_MENU VALUES('009','110','1');
INSERT INTO USER_MENU VALUES('009','111','1');
INSERT INTO USER_MENU VALUES('009','112','1');
INSERT INTO USER_MENU VALUES('009','113','1');
INSERT INTO USER_MENU VALUES('009','114','1');

/*==============================================================*/
/* Table: DICT_TABLE                                            */
/*==============================================================*/
create table DICT_TABLE  (
   TB_NAME              VARCHAR(100),
   TB_COMMENT           VARCHAR(100),
   TB_TYPE              VARCHAR(100)
);

comment on table DICT_TABLE is
'数据表字典表';

comment on column DICT_TABLE.TB_NAME is
'表名';

comment on column DICT_TABLE.TB_COMMENT is
'表描述';

comment on column DICT_TABLE.TB_TYPE is
'表类型';

/*==============================================================*/
/* Table: DICT_COL                                              */
/*==============================================================*/
create table DICT_COL  (
   TB_NAME              VARCHAR(100),
   COL_NAME             VARCHAR(100),
   COL_COMMENT          VARCHAR(100),
   COL_TYPE             VARCHAR(100)
);

comment on table DICT_COL is
'数据字段字典表';

comment on column DICT_COL.TB_NAME is
'表名';

comment on column DICT_COL.COL_NAME is
'字段名';

comment on column DICT_COL.COL_COMMENT is
'字段描述';

comment on column DICT_COL.COL_TYPE is
'字段类型';

INSERT INTO ROLE_DEPART SELECT '001',CODE FROM DEPARTMENT;

alter table department add column ordercode integer;
update department set ordercode=1 where name='三部' or code in (select code from department where parent=(select code from department where name='三部'));