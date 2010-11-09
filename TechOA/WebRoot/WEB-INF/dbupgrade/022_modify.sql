CREATE TABLE "PUBLIC"."ANNOUNCE"(
"ID" CHARACTER VARYING(32 CHAR) NOT NULL ,
"TYPE" CHARACTER VARYING(10 CHAR),
"TITLE" CHARACTER VARYING(500 CHAR),
"CONTENT" CHARACTER VARYING(2000 CHAR),
"PUBDATE" DATE,
PRIMARY KEY("ID")
);


INSERT INTO MENU VALUES('019', '公告管理', '1', 'announce.do?action=frame', 5, '1', '001', '1.png');
INSERT INTO MENU VALUES('107', '公告查看', '1', 'announce.do?action=list_view', 7, '1', '100', '10.png');

INSERT INTO MENU VALUES('140', '整件管理', '2', '', 54, '1', '11', '11.png');
INSERT INTO MENU VALUES('141', '整件组成管理', '1', '/zjgl.do?action=zjzc_frame', 55, '1', '140', '11.png');
INSERT INTO MENU VALUES('142', '投产管理', '1', '/tcgl.do?action=tc_frame', 56, '1', '140', '11.png');
INSERT INTO MENU VALUES('143', '整件汇总', '1', '/tcgl.do?action=zjhz_frame', 57, '1', '140', '11.png');
INSERT INTO MENU VALUES('144', '全程管理跟踪', '1', '/zjgl.do?action=tsgz_frame', 58, '1', '140', '11.png');
INSERT INTO MENU VALUES('145', '调试情况统计', '1', '/zjgl.do?action=tstj_frame', 59, '1', '140', '11.png');

INSERT INTO MENU VALUES('150', '经费管理', '2', '', 60, '1', '', '11.png');
INSERT INTO MENU VALUES('151', '预算', '1', '/jfgl.do?action=ys_frame', 61, '1', '150', '11.png');
INSERT INTO MENU VALUES('152', '费用', '1', '/jfgl.do?action=fy_frame', 62, '1', '150', '11.png');
INSERT INTO MENU VALUES('153', '月统计', '1', '/jfgl.do?action=ytj_frame', 62, '1', '150', '11.png');

INSERT INTO MENU VALUES('054', '计算机管理', '1', '/computer.do?action=frame', 61, '1', '005', '5.png');

INSERT INTO MENU VALUES('072', '考勤明细', '1', '/workcheck.do?action=detail_frame', 61, '1', '007', '7.png');
INSERT INTO MENU VALUES('073', '就餐明细', '1', '/workcheck.do?action=eat_frame', 62, '1', '007', '7.png');

INSERT INTO USER_MENU VALUES('001', '019', '1');

INSERT INTO USER_MENU VALUES('002', '107', '1');
INSERT INTO USER_MENU VALUES('003', '107', '1');
INSERT INTO USER_MENU VALUES('004', '107', '1');
INSERT INTO USER_MENU VALUES('005', '107', '1');
INSERT INTO USER_MENU VALUES('006', '107', '1');
INSERT INTO USER_MENU VALUES('007', '107', '1');
INSERT INTO USER_MENU VALUES('008', '107', '1');
INSERT INTO USER_MENU VALUES('009', '107', '1');

INSERT INTO USER_MENU VALUES('002','140','1');
INSERT INTO USER_MENU VALUES('002','141','1');
INSERT INTO USER_MENU VALUES('002','142','1');
INSERT INTO USER_MENU VALUES('002','143','1');
INSERT INTO USER_MENU VALUES('002','144','1');
INSERT INTO USER_MENU VALUES('002','145','1');
INSERT INTO USER_MENU VALUES('004','140','1');
INSERT INTO USER_MENU VALUES('004','141','1');
INSERT INTO USER_MENU VALUES('004','142','1');
INSERT INTO USER_MENU VALUES('004','143','1');
INSERT INTO USER_MENU VALUES('004','144','1');
INSERT INTO USER_MENU VALUES('004','145','1');

INSERT INTO USER_MENU VALUES('003','143','1');
INSERT INTO USER_MENU VALUES('003','144','1');

INSERT INTO USER_MENU VALUES('002','150','1');
INSERT INTO USER_MENU VALUES('002','151','1');
INSERT INTO USER_MENU VALUES('002','152','1');
INSERT INTO USER_MENU VALUES('002','153','1');

INSERT INTO USER_MENU VALUES('006','054','1');

INSERT INTO USER_MENU VALUES('002','007','1');
INSERT INTO USER_MENU VALUES('002','071','1');
INSERT INTO USER_MENU VALUES('002','072','1');
INSERT INTO USER_MENU VALUES('005','072','1');
INSERT INTO USER_MENU VALUES('002','073','1');
INSERT INTO USER_MENU VALUES('005','073','1');
INSERT INTO USER_MENU VALUES('007','007','1');
INSERT INTO USER_MENU VALUES('007','071','1');
INSERT INTO USER_MENU VALUES('007','072','1');
INSERT INTO USER_MENU VALUES('007','073','1');