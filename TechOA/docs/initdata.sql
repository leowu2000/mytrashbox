INSERT INTO DICT VALUES('100001','��Ѷ','1');
INSERT INTO DICT VALUES('100002','�����Ӳ��','1');
INSERT INTO DICT VALUES('100003','�ṹ','1');
INSERT INTO DICT VALUES('100004','����','1');
INSERT INTO DICT VALUES('100005','�������','1');
INSERT INTO DICT VALUES('100006','����','1');

INSERT INTO DICT VALUES('200001','��ר','2');
INSERT INTO DICT VALUES('200002','��ר','2');
INSERT INTO DICT VALUES('200003','����','2');
INSERT INTO DICT VALUES('200004','˶ʿ','2');
INSERT INTO DICT VALUES('200005','��ʿ','2');
INSERT INTO DICT VALUES('200006','��ʿ��','2');

INSERT INTO DICT VALUES('300001','����ְ��','3');
INSERT INTO DICT VALUES('300002','�м�ְ��','3');
INSERT INTO DICT VALUES('300003','�߼�ְ��','3');

INSERT INTO DICT VALUES('400001','����','4');
INSERT INTO DICT VALUES('400002','�ٵ�','4');
INSERT INTO DICT VALUES('400003','����','4');
INSERT INTO DICT VALUES('400004','����','4');
INSERT INTO DICT VALUES('400005','�¼�','4');
INSERT INTO DICT VALUES('400006','����','4');
INSERT INTO DICT VALUES('400007','����','4');
INSERT INTO DICT VALUES('400008','̽��','4');
INSERT INTO DICT VALUES('400009','���','4');
INSERT INTO DICT VALUES('400010','����','4');
INSERT INTO DICT VALUES('400011','ɥ��','4');
INSERT INTO DICT VALUES('400012','����','4');

INSERT INTO DICT VALUES('500001','ȷ�������뷽����֤','5');
INSERT INTO DICT VALUES('500002','ר���о�����������','5');
INSERT INTO DICT VALUES('500003','�������','5');
INSERT INTO DICT VALUES('500004','��ϼӹ�װ��','5');
INSERT INTO DICT VALUES('500005','��ϵͳ����','5');
INSERT INTO DICT VALUES('500006','��������','5');
INSERT INTO DICT VALUES('500007','����ʹ��','5');
INSERT INTO DICT VALUES('500008','����','5');
INSERT INTO DICT VALUES('500009','��������','5');

INSERT INTO EMPLOYEE VALUES('1','admin','1','1','001','ϵͳ����Ա','0','����','����','��','','','','','','','','','','');
INSERT INTO EMPLOYEE VALUES('2','yuangong1','1','2','002','һ�������쵼','1','����','�ල','����','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('3','yuangong2','1','3','003','һ������Ա��','1','�ɻ�','�ɻ�','��Ա','','','','','','','','100003','200003','300001');
INSERT INTO EMPLOYEE VALUES('4','yuangong3','1','4','002','���������쵼','2','����','�ල','��Ա','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('5','yuangong4','1','5','003','��������Ա��','2','�ɻ�','�ɻ�','��Ա','','','','','','','','100002','200004','300002');

INSERT INTO DEPARTMENT VALUES('1','1','һ������','0','',1);
INSERT INTO DEPARTMENT VALUES('2','2','��������','1','1',2);

INSERT INTO PROJECT VALUES('1','1','����һ','1','2','2,3',500,300,'2010-01-01','2010-06-01','�����Ǳ�ע');
INSERT INTO PROJECT VALUES('2','2','���̶�','1','3','3',200,150,'2010-01-01','2010-04-01','�����Ǳ�ע');
INSERT INTO PROJECT VALUES('3','3','������','1','4','3,4',100,90,'2010-01-01','2010-03-01','�����Ǳ�ע');

INSERT INTO DEP_PJ VALUES('2','1');
INSERT INTO DEP_PJ VALUES('3','1');
INSERT INTO DEP_PJ VALUES('3','2');
INSERT INTO DEP_PJ VALUES('3','3');

INSERT INTO WORKREPORT VALUES('1','��������һ','3','2010-2-1','2010-2-3','1','500001',10,'�о��о�',0,'1');
INSERT INTO WORKREPORT VALUES('2','���������','3','2010-2-1','2010-2-3','2','500002',2,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('3','����������','3','2010-2-1','2010-2-3','3','500003',4,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('4','����������','3','2010-2-1','2010-2-3','1','500004',6,'�о��о�4',2,'1');
INSERT INTO WORKREPORT VALUES('5','����������','3','2010-2-1','2010-2-3','2','500005',8,'�о��о�5',2,'1');

insert into workcheck values('3','2010-1-1','400001',0);
insert into workcheck values('3','2010-1-2','400002',1);
insert into workcheck values('3','2010-1-3','400003',2);
insert into workcheck values('3','2010-1-4','400004',8);
insert into workcheck values('3','2010-1-5','400005',4);
insert into workcheck values('3','2010-1-6','400006',3);
insert into workcheck values('3','2010-1-7','400007',5);

INSERT INTO FUNCTION VALUES('001','��Ա������Ϣ��','1','em.do?action=frame_info',1,'1');
INSERT INTO FUNCTION VALUES('002','ְ�����ڼ�¼','1','em.do?action=frame_workcheck',2,'1');
INSERT INTO FUNCTION VALUES('003','��ʱͳ�ƻ��ܱ�','1','modules/pj/frame_gstjhz.jsp',3,'1');
INSERT INTO FUNCTION VALUES('004','�������','1','pj.do?action=cost',4,'1');
INSERT INTO FUNCTION VALUES('005','���й�ʱͳ��','1','pj.do?action=frame_kygstj',5,'1');
INSERT INTO FUNCTION VALUES('006','���̼�����Ա�е��������','1','pj.do?action=frame_cdrwqk',6,'1');
insert into FUNCTION VALUES('007','Ա����������','1','workreport.do?action=list',1,'7');
insert into FUNCTION VALUES('008','��˹�������','1','workreport.do?action=auditlist','1',8);
insert into FUNCTION VALUES('009','���Ź���','1','depart.do?action=list','1',9);

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


