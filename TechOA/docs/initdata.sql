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

INSERT INTO EMPLOYEE VALUES('0','admin','1','0','001','系统管理员','0','管理','管理','无','','','','','','','','','','');
INSERT INTO EMPLOYEE VALUES('1','yuangong1','1','1','002','一级部门领导','1','审批','监督','副处','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('2','yuangong2','1','2','003','一级部门员工1','1','助理','编辑','科员','','','','','','','','100006','200003','300001');
INSERT INTO EMPLOYEE VALUES('5','yuangong5','1','5','003','一级部门员工2','1','文员','文员','科员','','','','','','','','100005','200004','300002');
INSERT INTO EMPLOYEE VALUES('6','yuangong6','1','6','003','一级部门员工3','1','干活','干活','科员','','','','','','','','100004','200003','300001');
INSERT INTO EMPLOYEE VALUES('7','yuangong7','1','7','003','一级部门员工4','1','干活','干活','科员','','','','','','','','100003','200002','300001');
INSERT INTO EMPLOYEE VALUES('8','yuangong8','1','8','003','一级部门员工5','1','干活','干活','科员','','','','','','','','100002','200003','300001');
INSERT INTO EMPLOYEE VALUES('9','yuangong9','1','9','003','一级部门员工6','1','干活','干活','科员','','','','','','','','100001','200004','300002');
INSERT INTO EMPLOYEE VALUES('3','yuangong3','1','3','002','二级部门领导','2','审批','监督','正科','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('4','yuangong4','1','4','003','二级部门员工','2','干活','干活','科员','','','','','','','','100002','200004','300002');

INSERT INTO DEPARTMENT VALUES('1','1','一级部门','0','',1);
INSERT INTO DEPARTMENT VALUES('2','2','二级部门','1','1',2);

INSERT INTO PROJECT VALUES('1','1','工程一','1','2','2,5,6,7,8',500,300,'2010-01-01','2010-06-01','这里是备注');
INSERT INTO PROJECT VALUES('2','2','工程二','1','3','3',200,150,'2010-01-01','2010-04-01','这里是备注');
INSERT INTO PROJECT VALUES('3','3','工程三','1','4','3,4',100,90,'2010-01-01','2010-03-01','工程三');
INSERT INTO PROJECT VALUES('4','4','工程四','1','1','2,5,6,7,8',230,90,'2010-01-01','2010-03-01','工程四');
INSERT INTO PROJECT VALUES('5','5','工程五','1','1','2,5,6,7,9',180,90,'2010-01-01','2010-03-01','工程五');
INSERT INTO PROJECT VALUES('6','6','工程六','1','3','3,4',340,90,'2010-01-01','2010-03-01','工程六');
INSERT INTO PROJECT VALUES('7','7','工程七','1','1','2,5,6,7,9',310,90,'2010-01-01','2010-03-01','工程七');
INSERT INTO PROJECT VALUES('8','8','工程八','1','3','3,4',350,90,'2010-01-01','2010-03-01','工程八');

INSERT INTO DEP_PJ VALUES('2','1');
INSERT INTO DEP_PJ VALUES('3','1');
INSERT INTO DEP_PJ VALUES('3','2');
INSERT INTO DEP_PJ VALUES('3','3');

INSERT INTO WORKREPORT VALUES('1','工作报告2.1','2','2010-2-3','2010-2-3','1','500001',9,'研究研究1',0,'1');
INSERT INTO WORKREPORT VALUES('2','工作报告2.2','2','2010-2-2','2010-2-2','2','500002',2,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('3','工作报告2.3','2','2010-2-1','2010-2-1','3','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('4','工作报告2.4','2','2010-1-31','2010-1-31','1','500004',6,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('5','工作报告2.5','2','2010-1-30','2010-1-30','2','500005',8,'研究研究5',2,'1');

INSERT INTO WORKREPORT VALUES('6','工作报告4.1','4','2010-2-3','2010-2-3','4','500006',12,'研究研究1',1,'2');
INSERT INTO WORKREPORT VALUES('7','工作报告4.2','4','2010-2-2','2010-2-2','4','500007',13,'研究研究2',1,'2');
INSERT INTO WORKREPORT VALUES('8','工作报告4.3','4','2010-2-1','2010-2-1','5','500008',15,'研究研究3',1,'2');
INSERT INTO WORKREPORT VALUES('9','工作报告4.4','4','2010-1-31','2010-1-31','6','500009',3,'研究研究4',1,'2');
INSERT INTO WORKREPORT VALUES('10','工作报告4.5','4','2010-1-30','2010-1-30','7','500001',1,'研究研究5',1,'2');

INSERT INTO WORKREPORT VALUES('11','工作报告5.1','5','2010-2-3','2010-2-3','3','500007',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('12','工作报告5.2','5','2010-2-2','2010-2-2','5','500005',5,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('13','工作报告5.3','5','2010-2-1','2010-2-1','8','500003',8,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('14','工作报告5.4','5','2010-1-31','2010-1-31','3','500002',3,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('15','工作报告5.5','5','2010-1-30','2010-1-30','9','500001',1,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('16','工作报告5.1','5','2010-2-3','2010-2-3','1','500002',4,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('17','工作报告5.2','5','2010-2-2','2010-2-2','4','500003',2,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('18','工作报告5.3','5','2010-2-1','2010-2-1','2','500005',3,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('19','工作报告5.4','5','2010-1-31','2010-1-31','3','500008',5,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('20','工作报告5.5','5','2010-1-30','2010-1-30','5','500003',8,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('21','工作报告6.1','6','2010-2-3','2010-2-3','8','500005',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('22','工作报告6.2','6','2010-2-2','2010-2-2','9','500008',1,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('23','工作报告6.3','6','2010-2-1','2010-2-1','1','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('24','工作报告6.4','6','2010-1-31','2010-1-31','4','500001',2,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('25','工作报告6.5','6','2010-1-30','2010-1-30','2','500004',3,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('26','工作报告7.1','7','2010-2-3','2010-2-3','3','500005',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('27','工作报告7.2','7','2010-2-2','2010-2-2','5','500008',1,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('28','工作报告7.3','7','2010-2-1','2010-2-1','8','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('29','工作报告7.4','7','2010-1-31','2010-1-31','9','500001',2,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('30','工作报告7.5','7','2010-1-30','2010-1-30','1','500004',3,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('31','工作报告8.1','8','2010-2-3','2010-2-3','3','500005',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('32','工作报告8.2','8','2010-2-2','2010-2-2','5','500008',1,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('33','工作报告8.3','8','2010-2-1','2010-2-1','8','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('34','工作报告8.4','8','2010-1-31','2010-1-31','9','500001',2,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('35','工作报告8.5','8','2010-1-30','2010-1-30','1','500004',3,'研究研究5',1,'1');

INSERT INTO WORKREPORT VALUES('36','工作报告9.1','9','2010-2-3','2010-2-3','4','500002',3,'研究研究1',1,'1');
INSERT INTO WORKREPORT VALUES('37','工作报告9.2','9','2010-2-2','2010-2-2','2','500003',1,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('38','工作报告9.3','9','2010-2-1','2010-2-1','3','500005',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('39','工作报告9.4','9','2010-1-31','2010-1-31','5','500001',2,'研究研究4',1,'1');
INSERT INTO WORKREPORT VALUES('40','工作报告9.5','9','2010-1-30','2010-1-30','8','500009',3,'研究研究5',1,'1');

insert into workcheck values('2','2010-1-27','400003',5);
insert into workcheck values('2','2010-1-28','400005',8);
insert into workcheck values('2','2010-1-29','400008',8);
insert into workcheck values('2','2010-1-30','400009',4);
insert into workcheck values('2','2010-1-31','400001',3);
insert into workcheck values('2','2010-2-1','400004',5);

insert into workcheck values('4','2010-1-27','400002',1);
insert into workcheck values('4','2010-1-28','400003',2);
insert into workcheck values('4','2010-1-29','400005',8);
insert into workcheck values('4','2010-1-30','400008',4);
insert into workcheck values('4','2010-1-31','400009',3);
insert into workcheck values('4','2010-2-1','400001',5);

insert into workcheck values('5','2010-1-27','400004',1);
insert into workcheck values('5','2010-1-28','400002',2);
insert into workcheck values('5','2010-1-29','400003',8);
insert into workcheck values('5','2010-1-30','400005',4);
insert into workcheck values('5','2010-1-31','400008',3);
insert into workcheck values('5','2010-2-1','400002',5);

insert into workcheck values('6','2010-1-27','400001',1);
insert into workcheck values('6','2010-1-28','400004',2);
insert into workcheck values('6','2010-1-29','400002',8);
insert into workcheck values('6','2010-1-30','400003',4);
insert into workcheck values('6','2010-1-31','400005',3);
insert into workcheck values('6','2010-2-1','400008',5);

insert into workcheck values('7','2010-1-27','400002',1);
insert into workcheck values('7','2010-1-28','400001',2);
insert into workcheck values('7','2010-1-29','400004',8);
insert into workcheck values('7','2010-1-30','400002',4);
insert into workcheck values('7','2010-1-31','400003',3);
insert into workcheck values('7','2010-2-1','400005',5);

insert into workcheck values('8','2010-1-27','400008',1);
insert into workcheck values('8','2010-1-28','400002',2);
insert into workcheck values('8','2010-1-29','400001',8);
insert into workcheck values('8','2010-1-30','400004',4);
insert into workcheck values('8','2010-1-31','400002',3);
insert into workcheck values('8','2010-2-1','400003',5);

insert into workcheck values('9','2010-1-27','400005',1);
insert into workcheck values('9','2010-1-28','400001',2);
insert into workcheck values('9','2010-1-29','400003',8);
insert into workcheck values('9','2010-1-30','400002',4);
insert into workcheck values('9','2010-1-31','400004',3);
insert into workcheck values('9','2010-2-1','400007',5);

INSERT INTO FUNCTION VALUES('001','人员基本信息表','1','em.do?action=frame_info',1,'1');
INSERT INTO FUNCTION VALUES('002','职工考勤记录','1','em.do?action=frame_workcheck',2,'1');
INSERT INTO FUNCTION VALUES('003','工时统计汇总表','1','modules/pj/frame_gstjhz.jsp',3,'1');
INSERT INTO FUNCTION VALUES('004','课题费用','1','pj.do?action=cost',4,'1');
INSERT INTO FUNCTION VALUES('005','科研工时统计','1','pj.do?action=frame_kygstj',5,'1');
INSERT INTO FUNCTION VALUES('006','工程技术人员承担任务情况','1','pj.do?action=frame_cdrwqk',6,'1');
insert into FUNCTION VALUES('007','员工工作报告','1','workreport.do?action=list',1,'7');
insert into FUNCTION VALUES('008','审核工作报告','1','workreport.do?action=auditlist','1',8);
insert into FUNCTION VALUES('009','部门管理','1','depart.do?action=list','1',9);

INSERT INTO FUNCROLE VALUES('001','001');
INSERT INTO FUNCROLE VALUES('001','002');
INSERT INTO FUNCROLE VALUES('001','003');
INSERT INTO FUNCROLE VALUES('001','004');
INSERT INTO FUNCROLE VALUES('001','005');
INSERT INTO FUNCROLE VALUES('001','006');
INSERT INTO FUNCROLE VALUES('001','007');
INSERT INTO FUNCROLE VALUES('001','008');
INSERT INTO FUNCROLE VALUES('001','009');
INSERT INTO FUNCROLE VALUES('002','001');
INSERT INTO FUNCROLE VALUES('002','002');
INSERT INTO FUNCROLE VALUES('002','003');
INSERT INTO FUNCROLE VALUES('002','004');
INSERT INTO FUNCROLE VALUES('002','005');
INSERT INTO FUNCROLE VALUES('002','006');
INSERT INTO FUNCROLE VALUES('002','008');
INSERT INTO FUNCROLE VALUES('002','009');
INSERT INTO FUNCROLE VALUES('003','001');
INSERT INTO FUNCROLE VALUES('003','002');
INSERT INTO FUNCROLE VALUES('003','007');


