CREATE TABLE "PUBLIC"."AUDIT"(
"ID" CHARACTER VARYING(32) NOT NULL ,
"TYPE" INTEGER,
"DATE" DATE,
"TIME" VARCHAR(20),
"IP" VARCHAR(20),
"SUCCESS" INTEGER,
"EMPCODE" VARCHAR(20),
"DESCRIPTION" VARCHAR(2000),
PRIMARY KEY("ID")
);
CREATE TABLE "PUBLIC"."EMP_STATUS"(
"ID" CHARACTER VARYING(32 CHAR) NOT NULL ,
"EMPCODE" CHARACTER VARYING(20 CHAR),
"STATUS" INTEGER,
PRIMARY KEY("ID")
);

INSERT INTO MENU VALUES('018','审计记录查询','1','audit.do?action=frame_search',5,'1','001','1.png');

INSERT INTO USER_MENU VALUES('001','018','1');