/*==============================================================*/
/* Table: ASSETS                                                */
/*==============================================================*/
create table ASSETS  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   MODEL                VARCHAR(50),
   BUYDATE              DATE,
   PRODUCDATE           DATE,
   BUYCOST              NUMERIC(15,2),
   NOWCOST              NUMERIC(15,2),
   LIFE                 INTEGER,
   STATUS               VARCHAR(10),
   DEPARTCPDE           VARCHAR(20),
   EMPCODE              VARCHAR(20),
   LENDDATE             DATE,
   constraint PK_ASSETS primary key (ID)
);

comment on table ASSETS is
'�̶��ʲ���';

comment on column ASSETS.ID is
'ID';

comment on column ASSETS.CODE is
'����';

comment on column ASSETS.NAME is
'�豸����';

comment on column ASSETS.MODEL is
'�豸�ͺ�';

comment on column ASSETS.BUYDATE is
'��������';

comment on column ASSETS.PRODUCDATE is
'��������';

comment on column ASSETS.BUYCOST is
'����۸�';

comment on column ASSETS.NOWCOST is
'�۾ɼ۸�';

comment on column ASSETS.LIFE is
'Ԥ��ʹ������';

comment on column ASSETS.STATUS is
'�豸״̬(1:����;2:���;3:��)';

comment on column ASSETS.DEPARTCPDE is
'�������';

comment on column ASSETS.EMPCODE is
'�����';

comment on column ASSETS.LENDDATE is
'�������';

/*==============================================================*/
/* Table: ATTACHMENT                                            */
/*==============================================================*/
create table ATTACHMENT  (
   ID                   VARCHAR(32)                     not null,
   RTABLE               VARCHAR(50),
   RCOLUMN              VARCHAR(50),
   RVALUE               VARCHAR(50),
   TYPE                 VARCHAR(20),
   FNAME                VARCHAR(100),
   FTYPE                VARCHAR(10),
   CONTENT              BLOB,
   FSIZE                INTEGER,
   constraint PK_ATTACHMENT primary key (ID)
);

comment on table ATTACHMENT is
'������';

comment on column ATTACHMENT.RTABLE is
'������';

comment on column ATTACHMENT.RCOLUMN is
'�����ֶ�';

comment on column ATTACHMENT.RVALUE is
'����ֵ';

comment on column ATTACHMENT.TYPE is
'��������(1:��Ƭ;2:����)';

comment on column ATTACHMENT.FNAME is
'�ļ���';

comment on column ATTACHMENT.FTYPE is
'�ļ�����';

comment on column ATTACHMENT.CONTENT is
'�ļ�����';

comment on column ATTACHMENT.FSIZE is
'�ļ���С';

/*==============================================================*/
/* Table: DEPARTMENT                                            */
/*==============================================================*/
create table DEPARTMENT  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(20),
   PARENT               VARCHAR(20),
   ALLPARENTS           VARCHAR(500),
   "LEVEL"              INTEGER,
   constraint PK_DEPARTMENT primary key (ID)
);

comment on table DEPARTMENT is
'���ű�';

comment on column DEPARTMENT.ID is
'ID';

comment on column DEPARTMENT.CODE is
'����';

comment on column DEPARTMENT.NAME is
'��������';

comment on column DEPARTMENT.PARENT is
'�ϼ�����';

comment on column DEPARTMENT.ALLPARENTS is
'�����ϼ�����(�ö��Ÿ���)';

comment on column DEPARTMENT."LEVEL" is
'�ȼ�';

/*==============================================================*/
/* Table: DEP_PJ                                                */
/*==============================================================*/
create table DEP_PJ  (
   DEPCODE              VARCHAR(20),
   PJCODE               VARCHAR(20)
);

comment on table DEP_PJ is
'����-���ű�';

comment on column DEP_PJ.DEPCODE is
'���ű���';

comment on column DEP_PJ.PJCODE is
'��Ŀ����';

/*==============================================================*/
/* Table: DICT                                                  */
/*==============================================================*/
create table DICT  (
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   TYPE                 VARCHAR(1)
);

comment on table DICT is
'�ֵ��';

comment on column DICT.CODE is
'����';

comment on column DICT.NAME is
'����';

comment on column DICT.TYPE is
'����(1:רҵ;2:ѧλ;3:ְ��;4:����;5:���̽׶�)';

/*==============================================================*/
/* Table: EMPLOYEE                                              */
/*==============================================================*/
create table EMPLOYEE  (
   ID                   VARCHAR(32)                     not null,
   LOGINID              VARCHAR(50),
   PASSWORD             VARCHAR(50),
   CODE                 VARCHAR(20),
   ROLECODE             VARCHAR(20),
   NAME                 VARCHAR(20),
   DEPARTCODE           VARCHAR(20),
   MAINJOB              VARCHAR(20),
   SECJOB               VARCHAR(20),
   "LEVEL"              VARCHAR(20),
   EMAIL                VARCHAR(100),
   BLOG                 VARCHAR(100),
   SELFWEB              VARCHAR(100),
   STCPHONE             VARCHAR(20),
   MOBPHONE             VARCHAR(20),
   ADDRESS              VARCHAR(500),
   POST                 VARCHAR(20),
   MAJORCODE            VARCHAR(20),
   DEGREECODE           VARCHAR(20),
   PROCODE              VARCHAR(20),
   DESCRIBE             VARCHAR(500),
   constraint PK_EMPLOYEE primary key (ID)
);

comment on table EMPLOYEE is
'��Ա������Ϣ��';

comment on column EMPLOYEE.ID is
'ID';

comment on column EMPLOYEE.LOGINID is
'��½��';

comment on column EMPLOYEE.PASSWORD is
'����';

comment on column EMPLOYEE.CODE is
'��Ա����';

comment on column EMPLOYEE.ROLECODE is
'��ɫ����';

comment on column EMPLOYEE.NAME is
'����';

comment on column EMPLOYEE.DEPARTCODE is
'���ű���';

comment on column EMPLOYEE.MAINJOB is
'����';

comment on column EMPLOYEE.SECJOB is
'����';

comment on column EMPLOYEE."LEVEL" is
'ְ�񼶱�';

comment on column EMPLOYEE.EMAIL is
'�����ʼ�';

comment on column EMPLOYEE.BLOG is
'��������';

comment on column EMPLOYEE.SELFWEB is
'������ҳ';

comment on column EMPLOYEE.STCPHONE is
'�̶��绰';

comment on column EMPLOYEE.MOBPHONE is
'�ֻ�����';

comment on column EMPLOYEE.ADDRESS is
'��ͥסַ';

comment on column EMPLOYEE.POST is
'��������';

comment on column EMPLOYEE.MAJORCODE is
'רҵ����';

comment on column EMPLOYEE.DEGREECODE is
'ѧ������';

comment on column EMPLOYEE.PROCODE is
'ְ�Ʊ���';

comment on column EMPLOYEE.DESCRIBE is
'����';

/*==============================================================*/
/* Table: MENU                                                  */
/*==============================================================*/
create table MENU  (
   MENUCODE             VARCHAR(20)                     not null,
   MENUNAME             VARCHAR(50),
   MENUTYPE             VARCHAR(1),
   MENUURL              VARCHAR(100),
   ORDERCODE            INTEGER,
   STATUS               VARCHAR(1),
   PARENT               VARCHAR(20),
   constraint PK_MENU primary key (MENUCODE)
);

comment on table MENU is
'�˵���';

comment on column MENU.MENUCODE is
'�˵�����';

comment on column MENU.MENUNAME is
'�˵�����';

comment on column MENU.MENUTYPE is
'�˵�����(1:�˵�������;2:���˵���;3:����)';

comment on column MENU.MENUURL is
'��������';

comment on column MENU.ORDERCODE is
'�����';

comment on column MENU.STATUS is
'����״̬(0:�ر�;1:����)';

comment on column MENU.PARENT is
'�����ܱ���';

/*==============================================================*/
/* Table: PJ_COST                                               */
/*==============================================================*/
create table PJ_COST  (
   ID                   VARCHAR(32)                     not null,
   RQ                   DATE,
   DJBH                 VARCHAR(20),
   GZLH                 VARCHAR(20),
   FXT                  VARCHAR(20),
   ZJH                  VARCHAR(20),
   BM                   VARCHAR(20),
   XHGG                 VARCHAR(20),
   DW                   VARCHAR(20),
   SL                   INTEGER,
   JE                   NUMERIC(10,2),
   XM                   VARCHAR(20),
   LLDW                 VARCHAR(20),
   JSDW                 VARCHAR(20),
   YT                   VARCHAR(50),
   constraint PK_PJ_COST primary key (ID)
);

comment on table PJ_COST is
'������ñ�';

comment on column PJ_COST.ID is
'ID';

comment on column PJ_COST.RQ is
'����';

comment on column PJ_COST.DJBH is
'���ݱ��';

comment on column PJ_COST.GZLH is
'�������';

comment on column PJ_COST.FXT is
'��ϵͳ';

comment on column PJ_COST.ZJH is
'������';

comment on column PJ_COST.BM is
'����';

comment on column PJ_COST.XHGG is
'�ͺŹ��';

comment on column PJ_COST.DW is
'��λ';

comment on column PJ_COST.SL is
'����';

comment on column PJ_COST.JE is
'���';

comment on column PJ_COST.XM is
'����';

comment on column PJ_COST.LLDW is
'���ϵ�λ';

comment on column PJ_COST.JSDW is
'���㵥λ';

comment on column PJ_COST.YT is
'��;';

/*==============================================================*/
/* Table: PROJECT                                               */
/*==============================================================*/
create table PROJECT  (
   ID                   VARCHAR(32)                     not null,
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(20),
   STATUS               VARCHAR(1),
   MANAGER              VARCHAR(20),
   MEMBER               VARCHAR(500),
   PLANEDWORKLOAD       INTEGER,
   NOWWORKLOAD          INTEGER,
   STARTDATE            DATE,
   ENDDATE              DATE,
   NOTE                 VARCHAR(500),
   constraint PK_PROJECT primary key (ID)
);

comment on table PROJECT is
'����/�������';

comment on column PROJECT.ID is
'ID';

comment on column PROJECT.CODE is
'����';

comment on column PROJECT.NAME is
'����';

comment on column PROJECT.STATUS is
'��Ŀ״̬(0:�ر�;1:����;2:����)';

comment on column PROJECT.MANAGER is
'��Ŀ����';

comment on column PROJECT.MEMBER is
'��Ŀ������Ա';

comment on column PROJECT.PLANEDWORKLOAD is
'�ƻ�������(Сʱ)';

comment on column PROJECT.NOWWORKLOAD is
'��Ͷ�빤����(Сʱ)';

comment on column PROJECT.STARTDATE is
'��Ŀ��ʼʱ��';

comment on column PROJECT.ENDDATE is
'��Ŀ��ֹʱ��';

comment on column PROJECT.NOTE is
'��Ŀ����';

/*==============================================================*/
/* Table: USER_MENU                                             */
/*==============================================================*/
create table USER_MENU  (
   EMPCODE              VARCHAR(20),
   MENUCODE             VARCHAR(20),
   TYPE                 VARCHAR(1)
);

comment on table USER_MENU is
'�û��˵���';

comment on column USER_MENU.EMPCODE is
'��Ա���';

comment on column USER_MENU.MENUCODE is
'�˵�����';

comment on column USER_MENU.TYPE is
'���(1:�����˵�;2:�ղز˵�)';

/*==============================================================*/
/* Table: WORKCHECK                                             */
/*==============================================================*/
create table WORKCHECK  (
   EMPCODE              VARCHAR(20),
   CHECKDATE            DATE,
   CHECKRESULT          VARCHAR(20),
   EMPTYHOURS           INTEGER
);

comment on table WORKCHECK is
'�������ڱ�';

comment on column WORKCHECK.EMPCODE is
'Ա������';

comment on column WORKCHECK.CHECKDATE is
'��������';

comment on column WORKCHECK.CHECKRESULT is
'���ڽ��';

comment on column WORKCHECK.EMPTYHOURS is
'ȱ��С��(Сʱ)';

/*==============================================================*/
/* Table: WORKREPORT                                            */
/*==============================================================*/
create table WORKREPORT  (
   ID                   VARCHAR(32)                     not null,
   NAME                 VARCHAR(100),
   EMPCODE              VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PJCODE               VARCHAR(20),
   STAGECODE            VARCHAR(20),
   AMOUNT               INTEGER,
   BZ                   VARCHAR(2000),
   FLAG                 INTEGER,
   DEPARTCODE           VARCHAR(20),
   constraint PK_WORKREPORT primary key (ID)
);

comment on table WORKREPORT is
'������������';

comment on column WORKREPORT.ID is
'ID';

comment on column WORKREPORT.NAME is
'�ܱ�����';

comment on column WORKREPORT.EMPCODE is
'��Ա����';

comment on column WORKREPORT.STARTDATE is
'��ʼ����';

comment on column WORKREPORT.ENDDATE is
'��ֹ����';

comment on column WORKREPORT.PJCODE is
'���̱���';

comment on column WORKREPORT.STAGECODE is
'�׶α���';

comment on column WORKREPORT.AMOUNT is
'Ͷ�빤����';

comment on column WORKREPORT.BZ is
'��ע';

comment on column WORKREPORT.FLAG is
'����״̬(0:δ�ύ;1:������;2:����ͨ��3:�����˻�)';

comment on column WORKREPORT.DEPARTCODE is
'���ű���';