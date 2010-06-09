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

DELETE FROM DEPARTMENT;
/** 部门表 **/
INSERT INTO DEPARTMENT VALUES('1','0000001','部门三','0','',1);
INSERT INTO DEPARTMENT VALUES('2','0000002','室304','0000001','0000001',2);

DELETE FROM EMPLOYEE;
/** 员工表 **/
INSERT INTO EMPLOYEE VALUES('0','admin','1','0000000','001','系统管理员','0','管理','管理','无','','','','','','','','','','','管理员');
INSERT INTO EMPLOYEE VALUES('1','leader','1','0000001','002','张三','0000001','审批','监督','副处','','','','','','','','100001','200004','300003','领导');
INSERT INTO EMPLOYEE VALUES('2','employee','1','0000002','003','赵六','0000001','助理','编辑','科员','','','','','','','','100006','200003','300001','技术骨干');
INSERT INTO EMPLOYEE VALUES('3','planner','1','0000003','004','李四','0000001','审批','监督','正科','','','','','','','','100001','200004','300003','领导');
INSERT INTO EMPLOYEE VALUES('4','team-manager','1','0000004','005','王五','0000001','干活','干活','科员','','','','','','','','100002','200004','300002','技术人员');
INSERT INTO EMPLOYEE VALUES('5','assets-admin','1','0000005','006','孙七','0000001','文员','文员','科员','','','','','','','','100005','200004','300002','行政人员');
INSERT INTO EMPLOYEE VALUES('6','employee-admin','1','0000006','007','周八','0000001','干活','干活','科员','','','','','','','','100004','200003','300001','技术人员');
INSERT INTO EMPLOYEE VALUES('7','employee2','1','0000007','003','吴九','0000001','干活','干活','科员','','','','','','','','100003','200002','300001','技术骨干');
INSERT INTO EMPLOYEE VALUES('8','employee3','1','0000008','003','郑十','0000001','干活','干活','科员','','','','','','','','100002','200003','300001','技术人员');
INSERT INTO EMPLOYEE VALUES('9','employee4','1','0000009','003','刘一','0000001','干活','干活','科员','','','','','','','','100001','200004','300002','行政人员');

DELETE FROM EMP_CARD;
INSERT INTO EMP_CARD VALUES('0000001','张三','1','0558000001','','','','0000001','部门三');
INSERT INTO EMP_CARD VALUES('0000002','赵六','1','0558000002','','','','0000001','部门三');
INSERT INTO EMP_CARD VALUES('0000003','李四','1','0558000003','','','','0000001','部门三');
INSERT INTO EMP_CARD VALUES('0000004','王五','1','0558000004','','','','0000001','部门三');
INSERT INTO EMP_CARD VALUES('0000005','孙七','1','0558000005','','','','0000001','部门三');
INSERT INTO EMP_CARD VALUES('0000006','周八','1','0558000006','','','','0000001','部门三');
INSERT INTO EMP_CARD VALUES('0000007','吴九','1','0558000007','','','','0000001','部门三');

DELETE FROM EMP_FINANCIAL;
INSERT INTO EMP_FINANCIAL VALUES('1','0000001','张三','0000001','部门三','2010-05-15',100,20,2000,3000,500,500,50,0,0,'工作令一','');
INSERT INTO EMP_FINANCIAL VALUES('2','0000002','赵六','0000001','部门三','2010-05-15',120,30,1000,3000,400,500,50,0,0,'工作令二','');
INSERT INTO EMP_FINANCIAL VALUES('3','0000003','李四','0000001','部门三','2010-05-15',140,40,3000,3000,500,500,50,0,0,'工作令三','');
INSERT INTO EMP_FINANCIAL VALUES('4','0000004','王五','0000001','部门三','2010-05-15',160,60,2000,3000,200,500,50,0,0,'工作令四','');
INSERT INTO EMP_FINANCIAL VALUES('5','0000005','孙七','0000001','部门三','2010-05-15',180,50,4000,3000,300,500,50,0,0,'工作令五','');
INSERT INTO EMP_FINANCIAL VALUES('6','0000006','周八','0000001','部门三','2010-05-15',200,70,200,3000,100,500,50,0,0,'工作令六','');
INSERT INTO EMP_FINANCIAL VALUES('7','0000007','吴九','0000001','部门三','2010-05-15',220,80,20,3000,50,500,50,0,0,'工作令七','');

DELETE FROM EMP_POS;
INSERT INTO EMP_POS VALUES('1','0000001','张三','0000001','部门三','0558000001','所POS1','2010-05-05 11:48:50',0.00,1);
INSERT INTO EMP_POS VALUES('2','0000002','赵六','0000001','部门三','0558000002','所POS1','2010-05-05 11:49:50',0.00,2);
INSERT INTO EMP_POS VALUES('3','0000003','李四','0000001','部门三','0558000003','所POS1','2010-05-05 11:50:50',0.00,3);
INSERT INTO EMP_POS VALUES('4','0000004','王五','0000001','部门三','0558000004','所POS1','2010-05-05 11:51:50',0.00,4);
INSERT INTO EMP_POS VALUES('5','0000005','孙七','0000001','部门三','0558000005','所POS1','2010-05-05 11:52:50',0.00,5);
INSERT INTO EMP_POS VALUES('6','0000006','周八','0000001','部门三','0558000006','所POS1','2010-05-05 11:53:50',0.00,6);
INSERT INTO EMP_POS VALUES('7','0000007','吴九','0000001','部门三','0558000007','所POS1','2010-05-05 11:54:50',0.00,7);

DELETE FROM GOODS;
INSERT INTO GOODS VALUES('1',2009,'01','CC090119299',704.65,'部门三','0000001','部门三','0000001','赵六','0000002','MR091122845','集成电路 AM29','AM29','3','00001','块儿',16,'44.040625','拼装','0560340027');
INSERT INTO GOODS VALUES('2',2009,'01','CC090119311',240.30,'部门三','0000001','部门三','0000001','吴九','0000007','MR091092637','电容器 CA45E','CA45E','4','00001','个',50,'4.806','拼装','06714135710120');

DELETE FROM PROJECT;
/** 工作令表 **/
INSERT INTO PROJECT VALUES('1','1','工作令一','1','0000002','0000002,0000005,0000006,0000007,0000008',500,300,'2110-05-01','2010-08-01','这里是备注');
INSERT INTO PROJECT VALUES('2','2','工作令二','1','0000003','0000003',200,150,'2110-05-01','2010-08-01','这里是备注');
INSERT INTO PROJECT VALUES('3','3','工作令三','1','0000004','0000003,0000004',100,90,'2110-05-01','2010-08-01','工作令三');
INSERT INTO PROJECT VALUES('4','4','工作令四','1','0000001','0000002,0000005,0000006,0000007,0000008',230,90,'2110-05-01','2010-08-01','工作令四');
INSERT INTO PROJECT VALUES('5','5','工作令五','1','0000001','0000002,0000005,0000006,0000007,0000009',180,90,'2110-05-01','2010-08-01','工作令五');
INSERT INTO PROJECT VALUES('6','6','工作令六','1','0000003','0000003,0000004',340,90,'2110-05-01','2010-08-01','工作令六');
INSERT INTO PROJECT VALUES('7','7','工作令七','1','0000001','0000002,0000005,0000006,0000007,0000009',310,90,'2110-05-01','2010-08-01','工作令七');
INSERT INTO PROJECT VALUES('8','8','工作令八','1','0000003','0000003,0000004',350,90,'2110-05-01','2010-08-01','工作令八');

DELETE FROM PROJECT_D;
/** 子系统表 **/
INSERT INTO PROJECT_D VALUES('0','0','0','','',null,null,null,null);
INSERT INTO PROJECT_D VALUES('1','1','F01','F01','0000004','2010-05-01','2010-05-10',50,'工作令一，分系统F01');
INSERT INTO PROJECT_D VALUES('2','1','F02','F02','0000004','2010-05-01','2010-06-01',60,'工作令一，分系统F02');
INSERT INTO PROJECT_D VALUES('3','1','F03','F03','0000004','2010-05-01','2010-07-10',70,'工作令一，分系统F03');
INSERT INTO PROJECT_D VALUES('4','1','F04','F04','0000004','2010-06-01','2010-07-20',80,'工作令一，分系统F04');

DELETE FROM WORKREPORT;
/** 工作报告表 **/
INSERT INTO WORKREPORT VALUES('1','工作报告2.1','0000002','2010-5-3','2010-5-3','1','F01','500001',9,'研究研究1',0,'0000001');
INSERT INTO WORKREPORT VALUES('2','工作报告2.2','0000002','2010-5-2','2010-5-2','2','0','500002',2,'研究研究2',1,'0000001');
INSERT INTO WORKREPORT VALUES('3','工作报告2.3','0000002','2010-5-1','2010-5-1','3','0','500003',4,'研究研究3',1,'0000001');
INSERT INTO WORKREPORT VALUES('4','工作报告2.4','0000002','2010-5-28','2010-5-28','1','F02','500004',6,'研究研究4',2,'0000001');
INSERT INTO WORKREPORT VALUES('5','工作报告2.5','0000002','2010-5-27','2010-5-27','2','0','500005',8,'研究研究5',2,'0000001');

INSERT INTO WORKREPORT VALUES('6','工作报告4.1','0000004','2010-5-3','2010-5-3','3','0','500006',12,'研究研究1',2,'0000001');
INSERT INTO WORKREPORT VALUES('7','工作报告4.2','0000004','2010-5-2','2010-5-2','2','0','500007',13,'研究研究2',2,'0000001');
INSERT INTO WORKREPORT VALUES('8','工作报告4.3','0000004','2010-5-1','2010-5-1','1','0','500008',15,'研究研究3',1,'0000001');
INSERT INTO WORKREPORT VALUES('9','工作报告4.4','0000004','2010-5-28','2010-5-28','3','0','500009',3,'研究研究4',0,'0000001');
INSERT INTO WORKREPORT VALUES('10','工作报告4.5','0000004','2010-5-27','2010-5-27','4','0','500001',1,'研究研究5',2,'0000001');

INSERT INTO WORKREPORT VALUES('11','工作报告5.1','0000005','2010-5-3','2010-5-3','2','0','500007',3,'研究研究1',2,'0000001');
INSERT INTO WORKREPORT VALUES('12','工作报告5.2','0000005','2010-5-2','2010-5-2','5','0','500005',5,'研究研究2',2,'0000001');
INSERT INTO WORKREPORT VALUES('13','工作报告5.3','0000005','2010-5-1','2010-5-1','4','0','500003',8,'研究研究3',2,'0000001');
INSERT INTO WORKREPORT VALUES('14','工作报告5.4','0000005','2010-5-28','2010-5-28','2','0','500002',3,'研究研究4',2,'0000001');
INSERT INTO WORKREPORT VALUES('15','工作报告5.5','0000005','2010-5-27','2010-5-27','3','0','500001',1,'研究研究5',2,'0000001');

INSERT INTO WORKREPORT VALUES('16','工作报告5.1','0000005','2010-5-3','2010-5-3','4','F03','500002',4,'研究研究1',2,'0000001');
INSERT INTO WORKREPORT VALUES('17','工作报告5.2','0000005','2010-5-2','2010-5-2','2','0','500003',2,'研究研究2',2,'0000001');
INSERT INTO WORKREPORT VALUES('18','工作报告5.3','0000005','2010-5-1','2010-5-1','1','0','500005',3,'研究研究3',2,'0000001');
INSERT INTO WORKREPORT VALUES('19','工作报告5.4','0000005','2010-5-28','2010-5-28','3','0','500008',5,'研究研究4',2,'0000001');
INSERT INTO WORKREPORT VALUES('20','工作报告5.5','0000005','2010-5-27','2010-5-27','4','0','500003',8,'研究研究5',2,'0000001');

INSERT INTO WORKREPORT VALUES('21','工作报告6.1','0000006','2010-5-3','2010-5-3','1','0','500005',3,'研究研究1',1,'0000001');
INSERT INTO WORKREPORT VALUES('22','工作报告6.2','0000006','2010-5-2','2010-5-2','4','0','500008',1,'研究研究2',1,'0000001');
INSERT INTO WORKREPORT VALUES('23','工作报告6.3','0000006','2010-5-1','2010-5-1','5','F04','500003',4,'研究研究3',1,'0000001');
INSERT INTO WORKREPORT VALUES('24','工作报告6.4','0000006','2010-5-28','2010-5-28','2','0','500001',2,'研究研究4',1,'0000001');
INSERT INTO WORKREPORT VALUES('25','工作报告6.5','0000006','2010-5-27','2010-5-27','4','0','500004',3,'研究研究5',1,'0000001');

INSERT INTO WORKREPORT VALUES('26','工作报告7.1','0000007','2010-5-3','2010-5-3','4','0','500005',3,'研究研究1',2,'0000001');
INSERT INTO WORKREPORT VALUES('27','工作报告7.2','0000007','2010-5-2','2010-5-2','2','0','500008',1,'研究研究2',2,'0000001');
INSERT INTO WORKREPORT VALUES('28','工作报告7.3','0000007','2010-5-1','2010-5-1','5','0','500003',4,'研究研究3',2,'0000001');
INSERT INTO WORKREPORT VALUES('29','工作报告7.4','0000007','2010-5-28','2010-5-28','4','0','500001',2,'研究研究4',2,'0000001');
INSERT INTO WORKREPORT VALUES('30','工作报告7.5','0000007','2010-5-27','2010-5-27','3','F01','500004',3,'研究研究5',2,'0000001');

INSERT INTO WORKREPORT VALUES('31','工作报告8.1','0000008','2010-5-3','2010-5-3','2','0','500005',3,'研究研究1',2,'0000001');
INSERT INTO WORKREPORT VALUES('32','工作报告8.2','0000008','2010-5-2','2010-5-2','6','0','500008',1,'研究研究2',2,'0000001');
INSERT INTO WORKREPORT VALUES('33','工作报告8.3','0000008','2010-5-1','2010-5-1','1','0','500003',4,'研究研究3',2,'0000001');
INSERT INTO WORKREPORT VALUES('34','工作报告8.4','0000008','2010-5-28','2010-5-28','6','0','500001',2,'研究研究4',2,'0000001');
INSERT INTO WORKREPORT VALUES('35','工作报告8.5','0000008','2010-5-27','2010-5-27','1','F02','500004',3,'研究研究5',2,'0000001');

INSERT INTO WORKREPORT VALUES('36','工作报告9.1','0000009','2010-5-3','2010-5-3','2','0','500002',3,'研究研究1',2,'0000001');
INSERT INTO WORKREPORT VALUES('37','工作报告9.2','0000009','2010-5-2','2010-5-2','3','0','500003',1,'研究研究2',2,'0000001');
INSERT INTO WORKREPORT VALUES('38','工作报告9.3','0000009','2010-5-1','2010-5-1','4','0','500005',4,'研究研究3',2,'0000001');
INSERT INTO WORKREPORT VALUES('39','工作报告9.4','0000009','2010-5-28','2010-5-28','5','0','500001',2,'研究研究4',2,'0000001');
INSERT INTO WORKREPORT VALUES('40','工作报告9.5','0000009','2010-5-27','2010-5-27','2','0','500009',3,'研究研究5',2,'0000001');

DELETE FROM WORKCHECK;
/** 考勤表 **/
INSERT INTO WORKCHECK VALUES('0000002','2010-5-27','400003',5);
INSERT INTO WORKCHECK VALUES('0000002','2010-5-28','400005',8);
INSERT INTO WORKCHECK VALUES('0000002','2010-5-4','400008',8);
INSERT INTO WORKCHECK VALUES('0000002','2010-5-2','400009',4);
INSERT INTO WORKCHECK VALUES('0000002','2010-5-3','400001',3);
INSERT INTO WORKCHECK VALUES('0000002','2010-5-1','400004',5);

INSERT INTO WORKCHECK VALUES('0000004','2010-5-27','400002',1);
INSERT INTO WORKCHECK VALUES('0000004','2010-5-28','400003',2);
INSERT INTO WORKCHECK VALUES('0000004','2010-5-4','400005',8);
INSERT INTO WORKCHECK VALUES('0000004','2010-5-2','400008',4);
INSERT INTO WORKCHECK VALUES('0000004','2010-5-3','400009',3);
INSERT INTO WORKCHECK VALUES('0000004','2010-5-1','400001',5);

INSERT INTO WORKCHECK VALUES('0000005','2010-5-27','400004',1);
INSERT INTO WORKCHECK VALUES('0000005','2010-5-28','400002',2);
INSERT INTO WORKCHECK VALUES('0000005','2010-5-4','400003',8);
INSERT INTO WORKCHECK VALUES('0000005','2010-5-2','400005',4);
INSERT INTO WORKCHECK VALUES('0000005','2010-5-3','400008',3);
INSERT INTO WORKCHECK VALUES('0000005','2010-5-1','400002',5);

INSERT INTO WORKCHECK VALUES('0000006','2010-5-27','400001',1);
INSERT INTO WORKCHECK VALUES('0000006','2010-5-28','400004',2);
INSERT INTO WORKCHECK VALUES('0000006','2010-5-4','400002',8);
INSERT INTO WORKCHECK VALUES('0000006','2010-5-2','400003',4);
INSERT INTO WORKCHECK VALUES('0000006','2010-5-3','400005',3);
INSERT INTO WORKCHECK VALUES('0000006','2010-5-1','400008',5);

INSERT INTO WORKCHECK VALUES('0000007','2010-5-27','400002',1);
INSERT INTO WORKCHECK VALUES('0000007','2010-5-28','400001',2);
INSERT INTO WORKCHECK VALUES('0000007','2010-5-4','400004',8);
INSERT INTO WORKCHECK VALUES('0000007','2010-5-2','400002',4);
INSERT INTO WORKCHECK VALUES('0000007','2010-5-3','400003',3);
INSERT INTO WORKCHECK VALUES('0000007','2010-5-1','400005',5);

INSERT INTO WORKCHECK VALUES('0000008','2010-5-27','400008',1);
INSERT INTO WORKCHECK VALUES('0000008','2010-5-28','400002',2);
INSERT INTO WORKCHECK VALUES('0000008','2010-5-4','400001',8);
INSERT INTO WORKCHECK VALUES('0000008','2010-5-2','400004',4);
INSERT INTO WORKCHECK VALUES('0000008','2010-5-3','400002',3);
INSERT INTO WORKCHECK VALUES('0000008','2010-5-1','400003',5);

INSERT INTO WORKCHECK VALUES('0000009','2010-5-27','400005',1);
INSERT INTO WORKCHECK VALUES('0000009','2010-5-28','400001',2);
INSERT INTO WORKCHECK VALUES('0000009','2010-5-4','400003',8);
INSERT INTO WORKCHECK VALUES('0000009','2010-5-2','400002',4);
INSERT INTO WORKCHECK VALUES('0000009','2010-5-3','400004',3);
INSERT INTO WORKCHECK VALUES('0000009','2010-5-1','400007',5);

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

INSERT INTO USER_MENU VALUES('0000000','001','2');
INSERT INTO USER_MENU VALUES('0000000','011','2');
INSERT INTO USER_MENU VALUES('0000000','012','2');
INSERT INTO USER_MENU VALUES('0000000','013','2');
INSERT INTO USER_MENU VALUES('0000000','014','2');

/** 部领导 **/
INSERT INTO USER_MENU VALUES('002','003','1');
INSERT INTO USER_MENU VALUES('002','031','1');
INSERT INTO USER_MENU VALUES('002','032','1');
INSERT INTO USER_MENU VALUES('002','033','1');
INSERT INTO USER_MENU VALUES('002','034','1');
INSERT INTO USER_MENU VALUES('002','009','1');
INSERT INTO USER_MENU VALUES('002','091','1');
INSERT INTO USER_MENU VALUES('002','100','1');
INSERT INTO USER_MENU VALUES('002','101','1');

INSERT INTO USER_MENU VALUES('0000001','003','2');
INSERT INTO USER_MENU VALUES('0000001','031','2');
INSERT INTO USER_MENU VALUES('0000001','032','2');
INSERT INTO USER_MENU VALUES('0000001','033','2');
INSERT INTO USER_MENU VALUES('0000001','034','2');
INSERT INTO USER_MENU VALUES('0000001','100','2');
INSERT INTO USER_MENU VALUES('0000001','101','2');

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

INSERT INTO USER_MENU VALUES('0000003','008','2');
INSERT INTO USER_MENU VALUES('0000003','084','2');
INSERT INTO USER_MENU VALUES('0000003','081','2');
INSERT INTO USER_MENU VALUES('0000003','082','2');
INSERT INTO USER_MENU VALUES('0000003','100','2');
INSERT INTO USER_MENU VALUES('0000003','102','2');
INSERT INTO USER_MENU VALUES('0000003','103','2');
INSERT INTO USER_MENU VALUES('0000003','104','2');

/** 组长 **/
INSERT INTO USER_MENU VALUES('005','002','1');
INSERT INTO USER_MENU VALUES('005','021','1');
INSERT INTO USER_MENU VALUES('005','022','1');
INSERT INTO USER_MENU VALUES('005','007','1');
INSERT INTO USER_MENU VALUES('005','071','1');
INSERT INTO USER_MENU VALUES('005','009','1');
INSERT INTO USER_MENU VALUES('005','091','1');
INSERT INTO USER_MENU VALUES('005','100','1');
INSERT INTO USER_MENU VALUES('005','101','1');
INSERT INTO USER_MENU VALUES('005','103','1');
INSERT INTO USER_MENU VALUES('005','104','1');

INSERT INTO USER_MENU VALUES('0000004','002','2');
INSERT INTO USER_MENU VALUES('0000004','022','2');
INSERT INTO USER_MENU VALUES('0000004','007','2');
INSERT INTO USER_MENU VALUES('0000004','071','2');
INSERT INTO USER_MENU VALUES('0000004','100','2');
INSERT INTO USER_MENU VALUES('0000004','101','2');
INSERT INTO USER_MENU VALUES('0000004','103','2');
INSERT INTO USER_MENU VALUES('0000004','104','2');

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
INSERT INTO USER_MENU VALUES('006','110','1');
INSERT INTO USER_MENU VALUES('006','112','1');

INSERT INTO USER_MENU VALUES('0000005','005','2');
INSERT INTO USER_MENU VALUES('0000005','052','2');
INSERT INTO USER_MENU VALUES('0000005','006','2');
INSERT INTO USER_MENU VALUES('0000005','061','2');
INSERT INTO USER_MENU VALUES('0000005','100','2');
INSERT INTO USER_MENU VALUES('0000005','102','2');
INSERT INTO USER_MENU VALUES('0000005','103','2');
INSERT INTO USER_MENU VALUES('0000005','104','2');
INSERT INTO USER_MENU VALUES('0000005','110','2');
INSERT INTO USER_MENU VALUES('0000005','112','2');

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

INSERT INTO USER_MENU VALUES('0000006','004','2');
INSERT INTO USER_MENU VALUES('0000006','041','2');
INSERT INTO USER_MENU VALUES('0000006','042','2');
INSERT INTO USER_MENU VALUES('0000006','043','2');
INSERT INTO USER_MENU VALUES('0000006','047','2');
INSERT INTO USER_MENU VALUES('0000006','048','2');
INSERT INTO USER_MENU VALUES('0000006','044','2');
INSERT INTO USER_MENU VALUES('0000006','045','2');
INSERT INTO USER_MENU VALUES('0000006','046','2');
INSERT INTO USER_MENU VALUES('0000006','100','2');
INSERT INTO USER_MENU VALUES('0000006','101','2');
INSERT INTO USER_MENU VALUES('0000006','103','2');
INSERT INTO USER_MENU VALUES('0000006','104','2');

/** 信息设备管理员 **/
INSERT INTO USER_MENU VALUES('008','005','1');
INSERT INTO USER_MENU VALUES('008','051','1');
INSERT INTO USER_MENU VALUES('008','009','1');
INSERT INTO USER_MENU VALUES('008','091','1');
INSERT INTO USER_MENU VALUES('008','100','1');
INSERT INTO USER_MENU VALUES('008','102','1');
INSERT INTO USER_MENU VALUES('008','103','1');
INSERT INTO USER_MENU VALUES('008','104','1');

INSERT INTO USER_MENU VALUES('0000007','100','2');
INSERT INTO USER_MENU VALUES('0000007','102','2');
INSERT INTO USER_MENU VALUES('0000007','103','2');
INSERT INTO USER_MENU VALUES('0000007','104','2');

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

INSERT INTO USER_MENU VALUES('0000002','002','2');
INSERT INTO USER_MENU VALUES('0000002','021','2');
INSERT INTO USER_MENU VALUES('0000002','008','2');
INSERT INTO USER_MENU VALUES('0000002','083','2');
INSERT INTO USER_MENU VALUES('0000002','100','2');
INSERT INTO USER_MENU VALUES('0000002','102','2');
INSERT INTO USER_MENU VALUES('0000002','103','2');
INSERT INTO USER_MENU VALUES('0000002','104','2');
INSERT INTO USER_MENU VALUES('0000002','110','2');
INSERT INTO USER_MENU VALUES('0000002','111','2');
INSERT INTO USER_MENU VALUES('0000008','002','2');
INSERT INTO USER_MENU VALUES('0000008','021','2');
INSERT INTO USER_MENU VALUES('0000008','008','2');
INSERT INTO USER_MENU VALUES('0000008','083','2');
INSERT INTO USER_MENU VALUES('0000008','100','2');
INSERT INTO USER_MENU VALUES('0000008','102','2');
INSERT INTO USER_MENU VALUES('0000008','103','2');
INSERT INTO USER_MENU VALUES('0000008','104','2');
INSERT INTO USER_MENU VALUES('0000008','110','2');
INSERT INTO USER_MENU VALUES('0000008','111','2');
INSERT INTO USER_MENU VALUES('0000009','002','2');
INSERT INTO USER_MENU VALUES('0000009','021','2');
INSERT INTO USER_MENU VALUES('0000009','008','2');
INSERT INTO USER_MENU VALUES('0000009','083','2');
INSERT INTO USER_MENU VALUES('0000009','100','2');
INSERT INTO USER_MENU VALUES('0000009','102','2');
INSERT INTO USER_MENU VALUES('0000009','103','2');
INSERT INTO USER_MENU VALUES('0000009','104','2');
INSERT INTO USER_MENU VALUES('0000009','110','2');
INSERT INTO USER_MENU VALUES('0000009','111','2');

DELETE FROM PLAN;
INSERT INTO PLAN VALUES('1','0000002','赵六','0000001','部门三','1','F01','500003','2010-04-10','2010-04-30',50,'科研生产','标志','**','备注','所领导','部领导','室领导','0000003','李四',1,'000001','000012','1');
INSERT INTO PLAN VALUES('2','0000008','郑十','0000001','部门三','1','F01','500003','2010-04-10','2010-04-30',50,'科研生产','标志','*','备注','所领导','部领导','室领导','0000003','李四',1,'000002','000021','1');
INSERT INTO PLAN VALUES('3','0000009','刘一','0000001','部门三','1','F01','500003','2010-04-10','2010-04-30',50,'科研生产','标志','***','备注','所领导','部领导','室领导','0000003','李四',1,'000001','000011','1');

DELETE FROM ASSETS;
/** 固定资产表 **/
INSERT INTO ASSETS VALUES('1','1','仪器1','10-01','2009-10-01','2009-08-08',80000,75000,10,'1',null,null,null,'2009-12-20',3);
INSERT INTO ASSETS VALUES('2','2','仪器2','01-10','2009-01-10','2009-01-01',180000,16000,50,'2','0000001','0000002','2010-01-28','2010-01-01',1);
INSERT INTO ASSETS VALUES('3','3','仪表1','08-10','2002-08-10','2001-02-08',30000,5000,10,'3',null,null,null,'2006-02-24',5);
INSERT INTO ASSETS VALUES('4','4','仪表2','08-10','2002-08-10','2001-02-08',30000,5000,10,'2','0000001','0000005','2010-01-09','2009-03-25',1);
INSERT INTO ASSETS VALUES('5','5','仪表3','08-10','2002-08-10','2001-02-08',30000,5000,10,'2','0000001','0000004','2010-01-09','2009-03-10',1);
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
