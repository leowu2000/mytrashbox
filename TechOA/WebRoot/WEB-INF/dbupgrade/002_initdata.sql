/** 字典表 **/
INSERT INTO DICT VALUES('100001','电讯','1');
INSERT INTO DICT VALUES('100002','计算机硬件','1');
INSERT INTO DICT VALUES('100003','结构','1');
INSERT INTO DICT VALUES('100004','工艺','1');
INSERT INTO DICT VALUES('100005','软件开发','1');
INSERT INTO DICT VALUES('100006','其他','1');

INSERT INTO DICT VALUES('200001','中专','2');
INSERT INTO DICT VALUES('200002','大专','2');
INSERT INTO DICT VALUES('200003','本科','2');
INSERT INTO DICT VALUES('200004','硕士','2');
INSERT INTO DICT VALUES('200005','博士','2');
INSERT INTO DICT VALUES('200006','博士后','2');

INSERT INTO DICT VALUES('300001','初级职称','3');
INSERT INTO DICT VALUES('300002','中级职称','3');
INSERT INTO DICT VALUES('300003','高级职称','3');

INSERT INTO DICT VALUES('400001','出勤','4');
INSERT INTO DICT VALUES('400002','迟到','4');
INSERT INTO DICT VALUES('400003','早退','4');
INSERT INTO DICT VALUES('400004','病假','4');
INSERT INTO DICT VALUES('400005','事假','4');
INSERT INTO DICT VALUES('400006','旷工','4');
INSERT INTO DICT VALUES('400007','工伤','4');
INSERT INTO DICT VALUES('400008','探亲','4');
INSERT INTO DICT VALUES('400009','婚假','4');
INSERT INTO DICT VALUES('400010','产假','4');
INSERT INTO DICT VALUES('400011','丧假','4');
INSERT INTO DICT VALUES('400012','其他','4');

INSERT INTO DICT VALUES('500001','确定任务与方案论证','5');
INSERT INTO DICT VALUES('500002','专题研究与体制试验','5');
INSERT INTO DICT VALUES('500003','工程设计','5');
INSERT INTO DICT VALUES('500004','配合加工装配','5');
INSERT INTO DICT VALUES('500005','分系统调试','5');
INSERT INTO DICT VALUES('500006','整架联试','5');
INSERT INTO DICT VALUES('500007','交付使用','5');
INSERT INTO DICT VALUES('500008','其他','5');
INSERT INTO DICT VALUES('500009','例行试验','5');

/** 员工表 **/
INSERT INTO EMPLOYEE VALUES('0','admin','1','000000','001','系统管理员','0','管理','管理','无','','','','','','','','','','','管理员');
INSERT INTO EMPLOYEE VALUES('1','leader','1','000001','002','张三','1','审批','监督','副处','','','','','','','','100001','200004','300003','领导');
INSERT INTO EMPLOYEE VALUES('2','employee','1','000002','003','赵六','1','助理','编辑','科员','','','','','','','','100006','200003','300001','技术骨干');
INSERT INTO EMPLOYEE VALUES('3','planner','1','000003','004','李四','1','审批','监督','正科','','','','','','','','100001','200004','300003','领导');
INSERT INTO EMPLOYEE VALUES('4','team-manager','1','000004','005','王五','1','干活','干活','科员','','','','','','','','100002','200004','300002','技术人员');
INSERT INTO EMPLOYEE VALUES('5','assets-admin','1','000005','006','孙七','1','文员','文员','科员','','','','','','','','100005','200004','300002','行政人员');
INSERT INTO EMPLOYEE VALUES('6','employee-admin','1','000006','007','周八','1','干活','干活','科员','','','','','','','','100004','200003','300001','技术人员');
INSERT INTO EMPLOYEE VALUES('7','employee2','1','000007','003','吴九','1','干活','干活','科员','','','','','','','','100003','200002','300001','技术骨干');
INSERT INTO EMPLOYEE VALUES('8','employee3','1','000008','003','郑十','1','干活','干活','科员','','','','','','','','100002','200003','300001','技术人员');
INSERT INTO EMPLOYEE VALUES('9','employee4','1','000009','003','刘一','1','干活','干活','科员','','','','','','','','100001','200004','300002','行政人员');

/** 员工一卡通表 **/
INSERT INTO EMP_CARD VALUES('000001','张三','1','0558000001','','','','2','304室');
INSERT INTO EMP_CARD VALUES('000002','赵六','1','0558000002','','','','2','304室');
INSERT INTO EMP_CARD VALUES('000003','李四','1','0558000003','','','','2','304室');
INSERT INTO EMP_CARD VALUES('000004','王五','1','0558000004','','','','2','304室');
INSERT INTO EMP_CARD VALUES('000005','孙七','1','0558000005','','','','2','304室');
INSERT INTO EMP_CARD VALUES('000006','周八','1','0558000006','','','','2','304室');
INSERT INTO EMP_CARD VALUES('000007','吴九','1','0558000007','','','','2','304室');

/** 员工财务信息表 **/
INSERT INTO EMP_FINANCIAL VALUES('000001','张三','2','304室',100,20,2000,3000,500,500,50,0,0,'工作令一','');
INSERT INTO EMP_FINANCIAL VALUES('000002','赵六','2','304室',120,30,1000,3000,400,500,50,0,0,'工作令二','');
INSERT INTO EMP_FINANCIAL VALUES('000003','李四','2','304室',140,40,3000,3000,500,500,50,0,0,'工作令三','');
INSERT INTO EMP_FINANCIAL VALUES('000004','王五','2','304室',160,60,2000,3000,200,500,50,0,0,'工作令四','');
INSERT INTO EMP_FINANCIAL VALUES('000005','孙七','2','304室',180,50,4000,3000,300,500,50,0,0,'工作令五','');
INSERT INTO EMP_FINANCIAL VALUES('000006','周八','2','304室',200,70,200,3000,100,500,50,0,0,'工作令六','');
INSERT INTO EMP_FINANCIAL VALUES('000007','吴九','2','304室',220,80,20,3000,50,500,50,0,0,'工作令七','');

/** 员工班车刷卡表 **/
INSERT INTO EMP_POS VALUES('000001','张三','2','304室','0558000001','所POS1','2010-03-05 11:48:50',0.00,1);
INSERT INTO EMP_POS VALUES('000002','赵六','2','304室','0558000002','所POS1','2010-03-05 11:49:50',0.00,2);
INSERT INTO EMP_POS VALUES('000003','李四','2','304室','0558000003','所POS1','2010-03-05 11:50:50',0.00,3);
INSERT INTO EMP_POS VALUES('000004','王五','2','304室','0558000004','所POS1','2010-03-05 11:51:50',0.00,4);
INSERT INTO EMP_POS VALUES('000005','孙七','2','304室','0558000005','所POS1','2010-03-05 11:52:50',0.00,5);
INSERT INTO EMP_POS VALUES('000006','周八','2','304室','0558000006','所POS1','2010-03-05 11:53:50',0.00,6);
INSERT INTO EMP_POS VALUES('000007','吴九','2','304室','0558000007','所POS1','2010-03-05 11:54:50',0.00,7);

/** 部门表 **/
INSERT INTO DEPARTMENT VALUES('1','1','三部','0','',1);
INSERT INTO DEPARTMENT VALUES('2','2','304室','1','1',2);
INSERT INTO DEPARTMENT VALUES('3','3','小组一','2','1,2',3);
INSERT INTO DEPARTMENT VALUES('4','4','小组二','2','1,2',3);
INSERT INTO DEPARTMENT VALUES('5','5','小组三','2','1,2',3);

/** 工作令表 **/
INSERT INTO PROJECT VALUES('1','1','工作令一','1','000002','000002,000005,000006,000007,000008',500,300,'2010-01-01','2010-06-01','这里是备注');
INSERT INTO PROJECT VALUES('2','2','工作令二','1','000003','000003',200,150,'2010-01-01','2010-04-01','这里是备注');
INSERT INTO PROJECT VALUES('3','3','工作令三','1','000004','000003,000004',100,90,'2010-01-01','2010-03-01','工作令三');
INSERT INTO PROJECT VALUES('4','4','工作令四','1','000001','000002,000005,000006,000007,000008',230,90,'2010-01-01','2010-03-01','工作令四');
INSERT INTO PROJECT VALUES('5','5','工作令五','1','000001','000002,000005,000006,000007,000009',180,90,'2010-01-01','2010-03-01','工作令五');
INSERT INTO PROJECT VALUES('6','6','工作令六','1','000003','000003,000004',340,90,'2010-01-01','2010-03-01','工作令六');
INSERT INTO PROJECT VALUES('7','7','工作令七','1','000001','000002,000005,000006,000007,000009',310,90,'2010-01-01','2010-03-01','工作令七');
INSERT INTO PROJECT VALUES('8','8','工作令八','1','000003','000003,000004',350,90,'2010-01-01','2010-03-01','工作令八');

/** 子系统表 **/
INSERT INTO PROJECT_D VALUES('0','0','0','','',null,null,null,null);
INSERT INTO PROJECT_D VALUES('1','1','F01','F01','000004','2010-03-01','2010-03-10',50,'工作令一，分系统F01');
INSERT INTO PROJECT_D VALUES('2','1','F02','F02','000004','2010-02-01','2010-03-01',60,'工作令一，分系统F02');
INSERT INTO PROJECT_D VALUES('3','1','F03','F03','000004','2010-02-01','2010-03-10',70,'工作令一，分系统F03');
INSERT INTO PROJECT_D VALUES('4','1','F04','F04','000004','2010-02-01','2010-03-20',80,'工作令一，分系统F04');

/** 工作报告表 **/
INSERT INTO WORKREPORT VALUES('1','工作报告2.1','000002','2010-3-3','2010-3-3','1','F01','500001',9,'研究研究1',0,'1');
INSERT INTO WORKREPORT VALUES('2','工作报告2.2','000002','2010-3-2','2010-3-2','2','0','500002',2,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('3','工作报告2.3','000002','2010-3-1','2010-3-1','3','0','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('4','工作报告2.4','000002','2010-2-28','2010-2-28','1','F02','500004',6,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('5','工作报告2.5','000002','2010-2-27','2010-2-27','2','0','500005',8,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('6','工作报告4.1','000004','2010-3-3','2010-3-3','4','0','500006',12,'研究研究1',2,'2');
INSERT INTO WORKREPORT VALUES('7','工作报告4.2','000004','2010-3-2','2010-3-2','4','0','500007',13,'研究研究2',2,'2');
INSERT INTO WORKREPORT VALUES('8','工作报告4.3','000004','2010-3-1','2010-3-1','5','0','500008',15,'研究研究3',1,'2');
INSERT INTO WORKREPORT VALUES('9','工作报告4.4','000004','2010-2-28','2010-2-28','6','0','500009',3,'研究研究4',0,'2');
INSERT INTO WORKREPORT VALUES('10','工作报告4.5','000004','2010-2-27','2010-2-27','7','0','500001',1,'研究研究5',2,'2');

INSERT INTO WORKREPORT VALUES('11','工作报告5.1','000005','2010-3-3','2010-3-3','3','0','500007',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('12','工作报告5.2','000005','2010-3-2','2010-3-2','5','0','500005',5,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('13','工作报告5.3','000005','2010-3-1','2010-3-1','8','0','500003',8,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('14','工作报告5.4','000005','2010-2-28','2010-2-28','3','0','500002',3,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('15','工作报告5.5','000005','2010-2-27','2010-2-27','9','0','500001',1,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('16','工作报告5.1','000005','2010-3-3','2010-3-3','1','F03','500002',4,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('17','工作报告5.2','000005','2010-3-2','2010-3-2','4','0','500003',2,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('18','工作报告5.3','000005','2010-3-1','2010-3-1','2','0','500005',3,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('19','工作报告5.4','000005','2010-2-28','2010-2-28','3','0','500008',5,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('20','工作报告5.5','000005','2010-2-27','2010-2-27','5','0','500003',8,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('21','工作报告6.1','000006','2010-3-3','2010-3-3','8','0','500005',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('22','工作报告6.2','000006','2010-3-2','2010-3-2','9','0','500008',1,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('23','工作报告6.3','000006','2010-3-1','2010-3-1','1','F04','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('24','工作报告6.4','000006','2010-2-28','2010-2-28','4','0','500001',2,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('25','工作报告6.5','000006','2010-2-27','2010-2-27','2','0','500004',3,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('26','工作报告7.1','000007','2010-3-3','2010-3-3','3','0','500005',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('27','工作报告7.2','000007','2010-3-2','2010-3-2','5','0','500008',1,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('28','工作报告7.3','000007','2010-3-1','2010-3-1','8','0','500003',4,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('29','工作报告7.4','000007','2010-2-28','2010-2-28','9','0','500001',2,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('30','工作报告7.5','000007','2010-2-27','2010-2-27','1','F01','500004',3,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('31','工作报告8.1','000008','2010-3-3','2010-3-3','3','0','500005',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('32','工作报告8.2','000008','2010-3-2','2010-3-2','5','0','500008',1,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('33','工作报告8.3','000008','2010-3-1','2010-3-1','8','0','500003',4,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('34','工作报告8.4','000008','2010-2-28','2010-2-28','9','0','500001',2,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('35','工作报告8.5','000008','2010-2-27','2010-2-27','1','F02','500004',3,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('36','工作报告9.1','000009','2010-3-3','2010-3-3','4','0','500002',3,'研究研究1',2,'1');
INSERT INTO WORKREPORT VALUES('37','工作报告9.2','000009','2010-3-2','2010-3-2','2','0','500003',1,'研究研究2',2,'1');
INSERT INTO WORKREPORT VALUES('38','工作报告9.3','000009','2010-3-1','2010-3-1','3','0','500005',4,'研究研究3',2,'1');
INSERT INTO WORKREPORT VALUES('39','工作报告9.4','000009','2010-2-28','2010-2-28','5','0','500001',2,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('40','工作报告9.5','000009','2010-2-27','2010-2-27','8','0','500009',3,'研究研究5',2,'1');

/** 考勤表 **/
INSERT INTO WORKCHECK VALUES('000002','2010-2-27','400003',5);
INSERT INTO WORKCHECK VALUES('000002','2010-2-28','400005',8);
INSERT INTO WORKCHECK VALUES('000002','2010-3-4','400008',8);
INSERT INTO WORKCHECK VALUES('000002','2010-3-2','400009',4);
INSERT INTO WORKCHECK VALUES('000002','2010-3-3','400001',3);
INSERT INTO WORKCHECK VALUES('000002','2010-3-1','400004',5);

INSERT INTO WORKCHECK VALUES('000004','2010-2-27','400002',1);
INSERT INTO WORKCHECK VALUES('000004','2010-2-28','400003',2);
INSERT INTO WORKCHECK VALUES('000004','2010-3-4','400005',8);
INSERT INTO WORKCHECK VALUES('000004','2010-3-2','400008',4);
INSERT INTO WORKCHECK VALUES('000004','2010-3-3','400009',3);
INSERT INTO WORKCHECK VALUES('000004','2010-3-1','400001',5);

INSERT INTO WORKCHECK VALUES('000005','2010-2-27','400004',1);
INSERT INTO WORKCHECK VALUES('000005','2010-2-28','400002',2);
INSERT INTO WORKCHECK VALUES('000005','2010-3-4','400003',8);
INSERT INTO WORKCHECK VALUES('000005','2010-3-2','400005',4);
INSERT INTO WORKCHECK VALUES('000005','2010-3-3','400008',3);
INSERT INTO WORKCHECK VALUES('000005','2010-3-1','400002',5);

INSERT INTO WORKCHECK VALUES('000006','2010-2-27','400001',1);
INSERT INTO WORKCHECK VALUES('000006','2010-2-28','400004',2);
INSERT INTO WORKCHECK VALUES('000006','2010-3-4','400002',8);
INSERT INTO WORKCHECK VALUES('000006','2010-3-2','400003',4);
INSERT INTO WORKCHECK VALUES('000006','2010-3-3','400005',3);
INSERT INTO WORKCHECK VALUES('000006','2010-3-1','400008',5);

INSERT INTO WORKCHECK VALUES('000007','2010-2-27','400002',1);
INSERT INTO WORKCHECK VALUES('000007','2010-2-28','400001',2);
INSERT INTO WORKCHECK VALUES('000007','2010-3-4','400004',8);
INSERT INTO WORKCHECK VALUES('000007','2010-3-2','400002',4);
INSERT INTO WORKCHECK VALUES('000007','2010-3-3','400003',3);
INSERT INTO WORKCHECK VALUES('000007','2010-3-1','400005',5);

INSERT INTO WORKCHECK VALUES('000008','2010-2-27','400008',1);
INSERT INTO WORKCHECK VALUES('000008','2010-2-28','400002',2);
INSERT INTO WORKCHECK VALUES('000008','2010-3-4','400001',8);
INSERT INTO WORKCHECK VALUES('000008','2010-3-2','400004',4);
INSERT INTO WORKCHECK VALUES('000008','2010-3-3','400002',3);
INSERT INTO WORKCHECK VALUES('000008','2010-3-1','400003',5);

INSERT INTO WORKCHECK VALUES('000009','2010-2-27','400005',1);
INSERT INTO WORKCHECK VALUES('000009','2010-2-28','400001',2);
INSERT INTO WORKCHECK VALUES('000009','2010-3-4','400003',8);
INSERT INTO WORKCHECK VALUES('000009','2010-3-2','400002',4);
INSERT INTO WORKCHECK VALUES('000009','2010-3-3','400004',3);
INSERT INTO WORKCHECK VALUES('000009','2010-3-1','400007',5);

/** 菜单表 **/
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
INSERT INTO MENU VALUES('005','固定资产管理','2','',17,'1','0','5.png');
INSERT INTO MENU VALUES('051','固定资产查询','1','assets.do?action=frame_info&manage=0',18,'1','005','5.png');
INSERT INTO MENU VALUES('052','固定资产维护','1','assets.do?action=frame_info&manage=1',19,'1','005','5.png');
INSERT INTO MENU VALUES('053','年检提醒','1','assets.do?action=remind',20,'1','005','5.png');
INSERT INTO MENU VALUES('006','物资管理','2','',21,'1','0','6.png');
INSERT INTO MENU VALUES('061','物资管理','1','goods.do?action=list',22,'1','006','6.png');
INSERT INTO MENU VALUES('007','考勤管理','2','',23,'1','0','7.png');
INSERT INTO MENU VALUES('071','员工考勤记录','1','em.do?action=frame_workcheck',24,'1','007','7.png');
INSERT INTO MENU VALUES('008','计划管理','2','',25,'1','0','8.png');
INSERT INTO MENU VALUES('081','计划管理','1','plan.do?action=list_frame',26,'1','008','8.png');
INSERT INTO MENU VALUES('082','考核统计','1','plan.do?action=result_frame',27,'1','008','8.png');
INSERT INTO MENU VALUES('009','收藏管理','2','',28,'1','0','9.png');
INSERT INTO MENU VALUES('091','收藏菜单','1','menu.do?action=manage_favor',29,'1','009','9.png');
INSERT INTO MENU VALUES('100','综合查询','2','',6,'1','0','10.png');
INSERT INTO MENU VALUES('101','人员信息综合查询','1','em.do?action=search_multi',7,'1','100','10.png');

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
INSERT INTO USER_MENU VALUES('006','009','1');
INSERT INTO USER_MENU VALUES('006','091','1');

/** 人事管理员 **/
INSERT INTO USER_MENU VALUES('007','004','1');
INSERT INTO USER_MENU VALUES('007','041','1');
INSERT INTO USER_MENU VALUES('007','009','1');
INSERT INTO USER_MENU VALUES('007','091','1');

INSERT INTO USER_MENU VALUES('000000','001','2');
INSERT INTO USER_MENU VALUES('000000','011','2');
INSERT INTO USER_MENU VALUES('000000','012','2');
INSERT INTO USER_MENU VALUES('000000','013','2');
INSERT INTO USER_MENU VALUES('000000','014','2');

INSERT INTO USER_MENU VALUES('000001','003','2');
INSERT INTO USER_MENU VALUES('000001','031','2');
INSERT INTO USER_MENU VALUES('000001','032','2');
INSERT INTO USER_MENU VALUES('000001','033','2');

INSERT INTO USER_MENU VALUES('000002','002','2');
INSERT INTO USER_MENU VALUES('000002','021','2');

INSERT INTO USER_MENU VALUES('000003','008','2');
INSERT INTO USER_MENU VALUES('000003','081','2');
INSERT INTO USER_MENU VALUES('000003','082','2');

INSERT INTO USER_MENU VALUES('000004','002','2');
INSERT INTO USER_MENU VALUES('000004','022','2');
INSERT INTO USER_MENU VALUES('000004','007','2');
INSERT INTO USER_MENU VALUES('000004','071','2');

INSERT INTO USER_MENU VALUES('000005','005','2');
INSERT INTO USER_MENU VALUES('000005','052','2');

INSERT INTO USER_MENU VALUES('000006','004','1');
INSERT INTO USER_MENU VALUES('000006','041','1');

INSERT INTO USER_MENU VALUES('000007','002','2');
INSERT INTO USER_MENU VALUES('000007','021','2');
INSERT INTO USER_MENU VALUES('000008','002','2');
INSERT INTO USER_MENU VALUES('000008','021','2');
INSERT INTO USER_MENU VALUES('000009','002','2');
INSERT INTO USER_MENU VALUES('000009','021','2');

/** 物资资产表 **/
INSERT INTO GOODS VALUES('1',2009,'01','CC090119299',704.65,'304室','2','304室','2','赵六','000002','MR091122845','集成电路 AM29','AM29','3','00001','块儿',16,'44.040625','拼装','0560340027');
INSERT INTO GOODS VALUES('2',2009,'01','CC090119311',240.30,'304室','2','304室','2','吴九','000007','MR091092637','电容器 CA45E','CA45E','4','00001','个',50,'4.806','拼装','06714135710120');

/** 固定资产表 **/
INSERT INTO ASSETS VALUES('1','1','仪器1','10-01','2009-10-01','2009-08-08',80000,75000,10,'1',null,null,null,'2009-12-20',3);
INSERT INTO ASSETS VALUES('2','2','仪器2','01-10','2009-01-10','2009-01-01',180000,16000,50,'2','1','000002','2010-01-28','2010-01-01',1);
INSERT INTO ASSETS VALUES('3','3','仪表1','08-10','2002-08-10','2001-02-08',30000,5000,10,'3',null,null,null,'2006-02-24',5);
INSERT INTO ASSETS VALUES('4','4','仪表2','08-10','2002-08-10','2001-02-08',30000,5000,10,'2','1','000005','2010-01-09','2009-03-25',1);
INSERT INTO ASSETS VALUES('5','5','仪表3','08-10','2002-08-10','2001-02-08',30000,5000,10,'2','2','000004','2010-01-09','2009-03-10',1);

/** 计划表 **/
INSERT INTO PLAN VALUES('1','000002','赵六','2','304室','1','F01','500003','2010-03-10','2010-03-30',50,'科研生产','标志','考核','备注','所领导','部领导','室领导','000003','李四',1);