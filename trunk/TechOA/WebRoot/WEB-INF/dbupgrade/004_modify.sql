DELETE FROM MENU;

INSERT INTO MENU VALUES('001','系统维护','2','',1,'1','0','1.png');
INSERT INTO MENU VALUES('011','工作令管理','1','pj.do?action=list',2,'1','001','1.png');
INSERT INTO MENU VALUES('012','部门管理','1','depart.do?action=list',3,'1','001','1.png');
INSERT INTO MENU VALUES('013','用户管理','1','em.do?action=frame_infolist',4,'1','001','1.png');
INSERT INTO MENU VALUES('014','菜单管理','1','menu.do?action=manage',5,'1','001','1.png');
INSERT INTO MENU VALUES('002','工作报告','2','',8,'1','0','2.png');
INSERT INTO MENU VALUES('021','个人工作报告','1','workreport.do?action=list',9,'1','002','2.png');
INSERT INTO MENU VALUES('022','审核工作报告','1','workreport.do?action=auditlist',10,'1','002','2.png');
INSERT INTO MENU VALUES('003','工时统计汇总','2','',11,'1','0','3.png');
INSERT INTO MENU VALUES('031','工时统计汇总','1','modules/pj/frame_gstjhz.jsp',12,'1','003','3.png');
INSERT INTO MENU VALUES('032','科研工时统计','1','pj.do?action=frame_kygstj',13,'1','003','3.png');
INSERT INTO MENU VALUES('033','承担任务情况','1','pj.do?action=frame_cdrwqk',14,'1','003','3.png');
INSERT INTO MENU VALUES('004','人事管理','2','',15,'1','0','4.png');
INSERT INTO MENU VALUES('041','人事管理','1','em.do?action=frame_manage',16,'1','004','4.png');
INSERT INTO MENU VALUES('042','财务管理','1','finance.do?action=frame_manage',17,'1','004','4.png');
INSERT INTO MENU VALUES('043','一卡通管理','1','card.do?action=frame_manage',18,'1','004','4.png');
INSERT INTO MENU VALUES('044','班车信息','1','pos.do?action=frame_manage',19,'1','004','4.png');
INSERT INTO MENU VALUES('005','固定资产管理','2','',20,'1','0','5.png');
INSERT INTO MENU VALUES('051','固定资产查询','1','assets.do?action=frame_info&manage=0',21,'1','005','5.png');
INSERT INTO MENU VALUES('052','固定资产维护','1','assets.do?action=frame_info&manage=1',22,'1','005','5.png');
INSERT INTO MENU VALUES('053','年检提醒','1','assets.do?action=remind',23,'1','005','5.png');
INSERT INTO MENU VALUES('006','物资管理','2','',24,'1','0','6.png');
INSERT INTO MENU VALUES('061','物资管理','1','goods.do?action=list',25,'1','006','6.png');
INSERT INTO MENU VALUES('007','考勤管理','2','',26,'1','0','7.png');
INSERT INTO MENU VALUES('071','员工考勤记录','1','em.do?action=frame_workcheck',27,'1','007','7.png');
INSERT INTO MENU VALUES('008','计划管理','2','',28,'1','0','8.png');
INSERT INTO MENU VALUES('081','计划管理','1','plan.do?action=list_frame',29,'1','008','8.png');
INSERT INTO MENU VALUES('082','考核统计','1','plan.do?action=result_frame',30,'1','008','8.png');
INSERT INTO MENU VALUES('009','收藏管理','2','',31,'1','0','9.png');
INSERT INTO MENU VALUES('091','收藏菜单','1','menu.do?action=manage_favor',32,'1','009','9.png');
INSERT INTO MENU VALUES('100','综合查询','2','',6,'1','0','10.png');
INSERT INTO MENU VALUES('101','人员信息综合查询','1','em.do?action=search_multi',7,'1','100','10.png');

INSERT INTO USER_MENU VALUES('007','042','1');
INSERT INTO USER_MENU VALUES('007','043','1');
INSERT INTO USER_MENU VALUES('007','044','1');
INSERT INTO USER_MENU VALUES('000006','042','1');
INSERT INTO USER_MENU VALUES('000006','043','1');
INSERT INTO USER_MENU VALUES('000006','044','1');

drop table EMP_FINANCIAL;

/*==============================================================*/
/* Table: EMP_FINANCIAL                                         */
/*==============================================================*/
create table EMP_FINANCIAL  (
   ID                   VARCHAR(32),
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
   BZ                   VARCHAR(500)
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
'项目 名称';

comment on column EMP_FINANCIAL.BZ is
'备注';
