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

INSERT INTO EMPLOYEE VALUES('0','admin','1','0','001','ϵͳ����Ա','0','����','����','��','','','','','','','','','','');
INSERT INTO EMPLOYEE VALUES('1','yuangong1','1','1','002','һ�������쵼','1','����','�ල','����','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('2','yuangong2','1','2','003','һ������Ա��1','1','����','�༭','��Ա','','','','','','','','100006','200003','300001');
INSERT INTO EMPLOYEE VALUES('5','yuangong5','1','5','003','һ������Ա��2','1','��Ա','��Ա','��Ա','','','','','','','','100005','200004','300002');
INSERT INTO EMPLOYEE VALUES('6','yuangong6','1','6','003','һ������Ա��3','1','�ɻ�','�ɻ�','��Ա','','','','','','','','100004','200003','300001');
INSERT INTO EMPLOYEE VALUES('7','yuangong7','1','7','003','һ������Ա��4','1','�ɻ�','�ɻ�','��Ա','','','','','','','','100003','200002','300001');
INSERT INTO EMPLOYEE VALUES('8','yuangong8','1','8','003','һ������Ա��5','1','�ɻ�','�ɻ�','��Ա','','','','','','','','100002','200003','300001');
INSERT INTO EMPLOYEE VALUES('9','yuangong9','1','9','003','һ������Ա��6','1','�ɻ�','�ɻ�','��Ա','','','','','','','','100001','200004','300002');
INSERT INTO EMPLOYEE VALUES('3','yuangong3','1','3','002','���������쵼','2','����','�ල','����','','','','','','','','100001','200004','300003');
INSERT INTO EMPLOYEE VALUES('4','yuangong4','1','4','003','��������Ա��','2','�ɻ�','�ɻ�','��Ա','','','','','','','','100002','200004','300002');

INSERT INTO DEPARTMENT VALUES('1','1','һ������','0','',1);
INSERT INTO DEPARTMENT VALUES('2','2','��������','1','1',2);

INSERT INTO PROJECT VALUES('1','1','����һ','1','2','2,5,6,7,8',500,300,'2010-01-01','2010-06-01','�����Ǳ�ע');
INSERT INTO PROJECT VALUES('2','2','���̶�','1','3','3',200,150,'2010-01-01','2010-04-01','�����Ǳ�ע');
INSERT INTO PROJECT VALUES('3','3','������','1','4','3,4',100,90,'2010-01-01','2010-03-01','������');
INSERT INTO PROJECT VALUES('4','4','������','1','1','2,5,6,7,8',230,90,'2010-01-01','2010-03-01','������');
INSERT INTO PROJECT VALUES('5','5','������','1','1','2,5,6,7,9',180,90,'2010-01-01','2010-03-01','������');
INSERT INTO PROJECT VALUES('6','6','������','1','3','3,4',340,90,'2010-01-01','2010-03-01','������');
INSERT INTO PROJECT VALUES('7','7','������','1','1','2,5,6,7,9',310,90,'2010-01-01','2010-03-01','������');
INSERT INTO PROJECT VALUES('8','8','���̰�','1','3','3,4',350,90,'2010-01-01','2010-03-01','���̰�');

INSERT INTO DEP_PJ VALUES('2','1');
INSERT INTO DEP_PJ VALUES('3','1');
INSERT INTO DEP_PJ VALUES('3','2');
INSERT INTO DEP_PJ VALUES('3','3');

INSERT INTO WORKREPORT VALUES('1','��������2.1','2','2010-2-3','2010-2-3','1','500001',9,'�о��о�1',0,'1');
INSERT INTO WORKREPORT VALUES('2','��������2.2','2','2010-2-2','2010-2-2','2','500002',2,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('3','��������2.3','2','2010-2-1','2010-2-1','3','500003',4,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('4','��������2.4','2','2010-1-31','2010-1-31','1','500004',6,'�о��о�4',2,'1');
INSERT INTO WORKREPORT VALUES('5','��������2.5','2','2010-1-30','2010-1-30','2','500005',8,'�о��о�5',2,'1');

INSERT INTO WORKREPORT VALUES('6','��������4.1','4','2010-2-3','2010-2-3','4','500006',12,'�о��о�1',1,'2');
INSERT INTO WORKREPORT VALUES('7','��������4.2','4','2010-2-2','2010-2-2','4','500007',13,'�о��о�2',1,'2');
INSERT INTO WORKREPORT VALUES('8','��������4.3','4','2010-2-1','2010-2-1','5','500008',15,'�о��о�3',1,'2');
INSERT INTO WORKREPORT VALUES('9','��������4.4','4','2010-1-31','2010-1-31','6','500009',3,'�о��о�4',1,'2');
INSERT INTO WORKREPORT VALUES('10','��������4.5','4','2010-1-30','2010-1-30','7','500001',1,'�о��о�5',1,'2');

INSERT INTO WORKREPORT VALUES('11','��������5.1','5','2010-2-3','2010-2-3','3','500007',3,'�о��о�1',1,'1');
INSERT INTO WORKREPORT VALUES('12','��������5.2','5','2010-2-2','2010-2-2','5','500005',5,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('13','��������5.3','5','2010-2-1','2010-2-1','8','500003',8,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('14','��������5.4','5','2010-1-31','2010-1-31','3','500002',3,'�о��о�4',1,'1');
INSERT INTO WORKREPORT VALUES('15','��������5.5','5','2010-1-30','2010-1-30','9','500001',1,'�о��о�5',1,'1');

INSERT INTO WORKREPORT VALUES('16','��������5.1','5','2010-2-3','2010-2-3','1','500002',4,'�о��о�1',1,'1');
INSERT INTO WORKREPORT VALUES('17','��������5.2','5','2010-2-2','2010-2-2','4','500003',2,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('18','��������5.3','5','2010-2-1','2010-2-1','2','500005',3,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('19','��������5.4','5','2010-1-31','2010-1-31','3','500008',5,'�о��о�4',1,'1');
INSERT INTO WORKREPORT VALUES('20','��������5.5','5','2010-1-30','2010-1-30','5','500003',8,'�о��о�5',1,'1');

INSERT INTO WORKREPORT VALUES('21','��������6.1','6','2010-2-3','2010-2-3','8','500005',3,'�о��о�1',1,'1');
INSERT INTO WORKREPORT VALUES('22','��������6.2','6','2010-2-2','2010-2-2','9','500008',1,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('23','��������6.3','6','2010-2-1','2010-2-1','1','500003',4,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('24','��������6.4','6','2010-1-31','2010-1-31','4','500001',2,'�о��о�4',1,'1');
INSERT INTO WORKREPORT VALUES('25','��������6.5','6','2010-1-30','2010-1-30','2','500004',3,'�о��о�5',1,'1');

INSERT INTO WORKREPORT VALUES('26','��������7.1','7','2010-2-3','2010-2-3','3','500005',3,'�о��о�1',1,'1');
INSERT INTO WORKREPORT VALUES('27','��������7.2','7','2010-2-2','2010-2-2','5','500008',1,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('28','��������7.3','7','2010-2-1','2010-2-1','8','500003',4,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('29','��������7.4','7','2010-1-31','2010-1-31','9','500001',2,'�о��о�4',1,'1');
INSERT INTO WORKREPORT VALUES('30','��������7.5','7','2010-1-30','2010-1-30','1','500004',3,'�о��о�5',1,'1');

INSERT INTO WORKREPORT VALUES('31','��������8.1','8','2010-2-3','2010-2-3','3','500005',3,'�о��о�1',1,'1');
INSERT INTO WORKREPORT VALUES('32','��������8.2','8','2010-2-2','2010-2-2','5','500008',1,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('33','��������8.3','8','2010-2-1','2010-2-1','8','500003',4,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('34','��������8.4','8','2010-1-31','2010-1-31','9','500001',2,'�о��о�4',1,'1');
INSERT INTO WORKREPORT VALUES('35','��������8.5','8','2010-1-30','2010-1-30','1','500004',3,'�о��о�5',1,'1');

INSERT INTO WORKREPORT VALUES('36','��������9.1','9','2010-2-3','2010-2-3','4','500002',3,'�о��о�1',1,'1');
INSERT INTO WORKREPORT VALUES('37','��������9.2','9','2010-2-2','2010-2-2','2','500003',1,'�о��о�2',1,'1');
INSERT INTO WORKREPORT VALUES('38','��������9.3','9','2010-2-1','2010-2-1','3','500005',4,'�о��о�3',1,'1');
INSERT INTO WORKREPORT VALUES('39','��������9.4','9','2010-1-31','2010-1-31','5','500001',2,'�о��о�4',1,'1');
INSERT INTO WORKREPORT VALUES('40','��������9.5','9','2010-1-30','2010-1-30','8','500009',3,'�о��о�5',1,'1');

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


