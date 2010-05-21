DROP VIEW VIEW_EMP;
DROP VIEW VIEW_TRAIN;
DROP VIEW VIEW_WORKCHECK;
DROP VIEW VIEW_WORKREPORT;

ALTER TABLE EMPLOYEE ALTER COLUMN MAINJOB TYPE VARCHAR(200);
ALTER TABLE EMPLOYEE ALTER COLUMN SECJOB TYPE VARCHAR(200);
ALTER TABLE EMP_POS ALTER COLUMN POSCODE TYPE VARCHAR(20);
ALTER TABLE PLAN ALTER COLUMN EMPCODE TYPE VARCHAR(200);
ALTER TABLE PLAN ALTER COLUMN EMPNAME TYPE VARCHAR(200);
ALTER TABLE PLAN ALTER COLUMN ORDERCODE TYPE VARCHAR(20);

DELETE FROM WORKREPORT;
/** 工作报告表 **/
INSERT INTO WORKREPORT VALUES('1','工作报告2.1','000002','2010-5-3','2010-5-3','100040','F01','500001',9,'研究研究1',0,'1');
INSERT INTO WORKREPORT VALUES('2','工作报告2.2','000002','2010-5-2','2010-5-2','100041','0','500002',2,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('3','工作报告2.3','000002','2010-5-1','2010-5-1','100042','0','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('4','工作报告2.4','000002','2010-5-28','2010-5-28','100040','F02','500004',6,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('5','工作报告2.5','000002','2010-5-27','2010-5-27','100041','0','500005',8,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('6','工作报告4.1','000004','2010-5-3','2010-5-3','100042','0','500006',12,'研究研究1',2,'2');
INSERT INTO WORKREPORT VALUES('7','工作报告4.2','000004','2010-5-2','2010-5-2','100041','0','500007',13,'研究研究2',2,'2');
INSERT INTO WORKREPORT VALUES('8','工作报告4.3','000004','2010-5-1','2010-5-1','100040','0','500008',15,'研究研究3',1,'2');
INSERT INTO WORKREPORT VALUES('9','工作报告4.4','000004','2010-5-28','2010-5-28','100042','0','500009',3,'研究研究4',0,'2');
INSERT INTO WORKREPORT VALUES('10','工作报告4.5','000004','2010-5-27','2010-5-27','110031','0','500001',1,'研究研究5',2,'2');

INSERT INTO WORKREPORT VALUES('11','工作报告5.1','000005','2010-5-3','2010-5-3','110032','0','500007',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('12','工作报告5.2','000005','2010-5-2','2010-5-2','110033','0','500005',5,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('13','工作报告5.3','000005','2010-5-1','2010-5-1','110034','0','500003',8,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('14','工作报告5.4','000005','2010-5-28','2010-5-28','110035','0','500002',3,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('15','工作报告5.5','000005','2010-5-27','2010-5-27','110036','0','500001',1,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('16','工作报告5.1','000005','2010-5-3','2010-5-3','110037','F03','500002',4,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('17','工作报告5.2','000005','2010-5-2','2010-5-2','110038','0','500003',2,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('18','工作报告5.3','000005','2010-5-1','2010-5-1','110038','0','500005',3,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('19','工作报告5.4','000005','2010-5-28','2010-5-28','110037','0','500008',5,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('20','工作报告5.5','000005','2010-5-27','2010-5-27','110036','0','500003',8,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('21','工作报告6.1','000006','2010-5-3','2010-5-3','110035','0','500005',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('22','工作报告6.2','000006','2010-5-2','2010-5-2','110034','0','500008',1,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('23','工作报告6.3','000006','2010-5-1','2010-5-1','110033','F04','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('24','工作报告6.4','000006','2010-5-28','2010-5-28','110032','0','500001',2,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('25','工作报告6.5','000006','2010-5-27','2010-5-27','110031','0','500004',3,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('26','工作报告7.1','000007','2010-5-3','2010-5-3','110031','0','500005',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('27','工作报告7.2','000007','2010-5-2','2010-5-2','110032','0','500008',1,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('28','工作报告7.3','000007','2010-5-1','2010-5-1','110033','0','500003',4,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('29','工作报告7.4','000007','2010-5-28','2010-5-28','110034','0','500001',2,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('30','工作报告7.5','000007','2010-5-27','2010-5-27','110035','F01','500004',3,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('31','工作报告8.1','000008','2010-5-3','2010-5-3','110036','0','500005',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('32','工作报告8.2','000008','2010-5-2','2010-5-2','110037','0','500008',1,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('33','工作报告8.3','000008','2010-5-1','2010-5-1','110038','0','500003',4,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('34','工作报告8.4','000008','2010-5-28','2010-5-28','110037','0','500001',2,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('35','工作报告8.5','000008','2010-5-27','2010-5-27','110038','F02','500004',3,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('36','工作报告9.1','000009','2010-5-3','2010-5-3','110036','0','500002',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('37','工作报告9.2','000009','2010-5-2','2010-5-2','110035','0','500003',1,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('38','工作报告9.3','000009','2010-5-1','2010-5-1','110034','0','500005',4,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('39','工作报告9.4','000009','2010-5-28','2010-5-28','110033','0','500001',2,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('40','工作报告9.5','000009','2010-5-27','2010-5-27','110032','0','500009',3,'研究研究5',2,'1');

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
INSERT INTO MENU VALUES('034','员工投入分析','1','em.do?action=frame_ygtrfx',17,'1','003','3.png');
INSERT INTO MENU VALUES('004','人事管理','2','',18,'1','0','4.png');
INSERT INTO MENU VALUES('041','人事管理','1','em.do?action=frame_manage',19,'1','004','4.png');
INSERT INTO MENU VALUES('042','财务管理','1','finance.do?action=frame_manage',20,'1','004','4.png');
INSERT INTO MENU VALUES('043','一卡通管理','1','card.do?action=frame_manage',21,'1','004','4.png');
INSERT INTO MENU VALUES('047','班车管理','1','car.do?action=frame_manage',22,'1','004','4.png');
INSERT INTO MENU VALUES('048','班车预约统计','1','car.do?action=frame_order_manage',23,'1','004','4.png');
INSERT INTO MENU VALUES('044','班车刷卡信息','1','pos.do?action=frame_manage',24,'1','004','4.png');
INSERT INTO MENU VALUES('045','培训管理','1','train.do?action=list_manage',25,'1','004','4.png');
INSERT INTO MENU VALUES('046','培训统计','1','train.do?action=frame_pxtj',26,'1','004','4.png');
INSERT INTO MENU VALUES('005','固定资产管理','2','',27,'1','0','5.png');
INSERT INTO MENU VALUES('051','信息设备维护','1','infoequip.do?action=manage',28,'1','005','5.png');
INSERT INTO MENU VALUES('052','固定资产维护','1','assets.do?action=frame_info&manage=1',29,'1','005','5.png');
INSERT INTO MENU VALUES('053','年检提醒','1','assets.do?action=remind',30,'1','005','5.png');
INSERT INTO MENU VALUES('006','物资管理','2','',31,'1','0','6.png');
INSERT INTO MENU VALUES('061','领料管理','1','goods.do?action=frame_list',32,'1','006','6.png');
INSERT INTO MENU VALUES('062','物资管理','1','goods.do?action=frame_list_price',32,'1','006','6.png');
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

UPDATE MENU SET STATUS='0' WHERE MENUCODE IN ('110','111','112','051');
INSERT INTO USER_MENU VALUES('006','062','1');

/*==============================================================*/
/* Table: GOODS_PRICE                                           */
/*==============================================================*/
create table GOODS_PRICE  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   TYPE                 VARCHAR(100),
   PRICE                NUMERIC(8, 2),
   constraint PK_GOODS_PRICE primary key (ID)
);

comment on table GOODS_PRICE is
'物资表';

comment on column GOODS_PRICE.ID is
'ID';

comment on column GOODS_PRICE.CODE is
'编码';

comment on column GOODS_PRICE.NAME is
'名称';

comment on column GOODS_PRICE.TYPE is
'型号规格';

comment on column GOODS_PRICE.PRICE is
'单价';
