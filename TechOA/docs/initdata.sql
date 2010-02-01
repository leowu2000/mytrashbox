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

INSERT INTO EMPLOYEE VALUES('1','admin','1','1','001','系统管理员','0','管理','管理','无','','','','','','','','','','');
INSERT INTO EMPLOYEE VALUES('2','yuangong1','1','2','002','一级部门领导','1','审批','监督','副处','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('3','yuangong2','1','3','003','一级部门员工','1','干活','干活','科员','','','','','','','','100003','200003','300001');
INSERT INTO EMPLOYEE VALUES('4','yuangong3','1','4','002','二级部门领导','2','审批','监督','科员','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('5','yuangong4','1','5','003','二级部门员工','2','干活','干活','科员','','','','','','','','100002','200004','300002');

INSERT INTO DEPARTMENT VALUES('1','1','一级部门','0','',1);
INSERT INTO DEPARTMENT VALUES('2','2','二级部门','1','1',2);

INSERT INTO PROJECT VALUES('1','1','工程一','1','2','2,3',500,300,'2010-01-01','2010-06-01','这里是备注');
INSERT INTO PROJECT VALUES('2','2','工程二','1','3','3',200,150,'2010-01-01','2010-04-01','这里是备注');
INSERT INTO PROJECT VALUES('3','3','工程三','1','4','3,4',100,90,'2010-01-01','2010-03-01','这里是备注');

INSERT INTO DEP_PJ VALUES('2','1');
INSERT INTO DEP_PJ VALUES('3','1');
INSERT INTO DEP_PJ VALUES('3','2');
INSERT INTO DEP_PJ VALUES('3','3');

INSERT INTO WORKREPORT VALUES('1','工作报告一','3','2010-2-1','2010-2-3','1','500001',10,'研究研究',0,'1');
INSERT INTO WORKREPORT VALUES('2','工作报告二','3','2010-2-1','2010-2-3','2','500002',2,'研究研究2',1,'1');
INSERT INTO WORKREPORT VALUES('3','工作报告三','3','2010-2-1','2010-2-3','3','500003',4,'研究研究3',1,'1');
INSERT INTO WORKREPORT VALUES('4','工作报告四','3','2010-2-1','2010-2-3','1','500004',6,'研究研究4',2,'1');
INSERT INTO WORKREPORT VALUES('5','工作报告五','3','2010-2-1','2010-2-3','2','500005',8,'研究研究5',2,'1');

insert into workcheck values('3','2010-1-1','400001',0);
insert into workcheck values('3','2010-1-2','400002',1);
insert into workcheck values('3','2010-1-3','400003',2);
insert into workcheck values('3','2010-1-4','400004',8);
insert into workcheck values('3','2010-1-5','400005',4);
insert into workcheck values('3','2010-1-6','400006',3);
insert into workcheck values('3','2010-1-7','400007',5);

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


