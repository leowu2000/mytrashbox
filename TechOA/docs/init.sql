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
   CHECKDATE            DATE,
   CHECKYEAR            INTEGER,
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

comment on column ASSETS.CHECKDATE is
'�ϴμ��ʱ��';

comment on column ASSETS.CHECKYEAR is
'�����';

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
/* Table: EMP_CARD                                              */
/*==============================================================*/
create table EMP_CARD  (
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   SEX                  VARCHAR(1),
   CARDNO               VARCHAR(20),
   PHONE1               VARCHAR(20),
   PHONE2               VARCHAR(20),
   ADDRESS              VARCHAR(500),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50)
);

comment on table EMP_CARD is
'Ա��һ��ͨ��';

comment on column EMP_CARD.EMPCODE is
'Ա����� ';

comment on column EMP_CARD.EMPNAME is
'Ա������';

comment on column EMP_CARD.SEX is
'Ա���Ա�(1:����;2:Ů��)';

comment on column EMP_CARD.CARDNO is
'����';

comment on column EMP_CARD.PHONE1 is
'�绰1';

comment on column EMP_CARD.PHONE2 is
'�绰2';

comment on column EMP_CARD.ADDRESS is
'��ַ';

comment on column EMP_CARD.DEPARTCODE is
'���ű���';

comment on column EMP_CARD.DEPARTNAME is
'��������';

/*==============================================================*/
/* Table: EMP_FINANCIAL                                         */
/*==============================================================*/
create table EMP_FINANCIAL  (
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   JBF                  NUMERIC(10, 2),
   PSF                  NUMERIC(10, 2),
   GC                   NUMERIC(10, 2),
   CJ                   NUMERIC(10, 2),
   WCBT                 NUMERIC(10, 2),
   CGLBT                NUMERIC(10, 2),
   LB                   NUMERIC(10, 2),
   GJBT                 NUMERIC(10, 2),
   FPBT                 NUMERIC(10, 2),
   XMMC                 VARCHAR(50),
   BZ                   VARCHAR(500)
);

comment on table EMP_FINANCIAL is
'Ա�������';

comment on column EMP_FINANCIAL.EMPCODE is
'��Ա���';

comment on column EMP_FINANCIAL.EMPNAME is
'����';

comment on column EMP_FINANCIAL.DEPARTCODE is
'���ű���';

comment on column EMP_FINANCIAL.DEPARTNAME is
'��������';

comment on column EMP_FINANCIAL.JBF is
'�Ӱ��';

comment on column EMP_FINANCIAL.PSF is
'�����';

comment on column EMP_FINANCIAL.GC is
'���';

comment on column EMP_FINANCIAL.CJ is
'���';

comment on column EMP_FINANCIAL.WCBT is
'�ⳡ����';

comment on column EMP_FINANCIAL.CGLBT is
'�����ﲹ��';

comment on column EMP_FINANCIAL.LB is
'�ͱ�';

comment on column EMP_FINANCIAL.GJBT is
'��������';

comment on column EMP_FINANCIAL.FPBT is
'��Ƹ����';

comment on column EMP_FINANCIAL.XMMC is
'��Ŀ ����';

comment on column EMP_FINANCIAL.BZ is
'��ע';

/*==============================================================*/
/* Table: EMP_POS                                               */
/*==============================================================*/
create table EMP_POS  (
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   CARDNO               VARCHAR(20),
   POSMACHINE           VARCHAR(50),
   SWIPETIME            DATE,
   COST                 NUMERIC(10,2),
   POSCODE              INTEGER
);

comment on table EMP_POS is
'�೵ˢ����';

comment on column EMP_POS.EMPCODE is
'��Ա���';

comment on column EMP_POS.EMPNAME is
'��Ա����';

comment on column EMP_POS.DEPARTCODE is
'���ű��';

comment on column EMP_POS.DEPARTNAME is
'��������';

comment on column EMP_POS.CARDNO is
'����';

comment on column EMP_POS.POSMACHINE is
'����POS��';

comment on column EMP_POS.SWIPETIME is
'ˢ��ʱ��';

comment on column EMP_POS.COST is
'ˢ�����';

comment on column EMP_POS.POSCODE is
'POS��ˮ��';

/*==============================================================*/
/* Table: GOODS                                                 */
/*==============================================================*/
create table GOODS  (
   ID                   VARCHAR(32)                     not null,
   KJND                 INTEGER,
   KJH                  VARCHAR(20),
   CKDH                 VARCHAR(20),
   JE                   NUMERIC(10,2),
   LLBMMC               VARCHAR(50),
   LLBMBM               VARCHAR(20),
   JSBMMC               VARCHAR(50),
   JSBMBM               VARCHAR(20),
   LLRMC                VARCHAR(50),
   LLRBM                VARCHAR(20),
   ZJH                  VARCHAR(20),
   CHMC                 VARCHAR(200),
   GG                   VARCHAR(100),
   PJCODE               VARCHAR(20),
   TH                   VARCHAR(50),
   ZJLDW                VARCHAR(50),
   SL                   INTEGER,
   DJ                   NUMERIC(15,6),
   XMYT                 VARCHAR(200),
   CHBM                 VARCHAR(20),
   constraint PK_GOODS primary key (ID)
);

comment on table GOODS is
'���ʱ�';

comment on column GOODS.ID is
'ID';

comment on column GOODS.KJND is
'������';

comment on column GOODS.KJH is
'��ƺ�';

comment on column GOODS.CKDH is
'���ⵥ��';

comment on column GOODS.JE is
'���';

comment on column GOODS.LLBMMC is
'���ϲ�������';

comment on column GOODS.LLBMBM is
'���ϲ��ű���';

comment on column GOODS.JSBMMC is
'���㲿������';

comment on column GOODS.JSBMBM is
'���㲿�ű���';

comment on column GOODS.LLRMC is
'����������';

comment on column GOODS.LLRBM is
'�����˱���';

comment on column GOODS.ZJH is
'������';

comment on column GOODS.CHMC is
'�������';

comment on column GOODS.GG is
'���';

comment on column GOODS.PJCODE is
'��Ŀ����';

comment on column GOODS.TH is
'ͼ��';

comment on column GOODS.ZJLDW is
'��������λ';

comment on column GOODS.SL is
'����';

comment on column GOODS.DJ is
'����';

comment on column GOODS.XMYT is
'��Ŀ��;';

comment on column GOODS.CHBM is
'�������';

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
   ICON                 VARCHAR(20),
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

comment on column MENU.ICON is
'ͼ������';

/*==============================================================*/
/* Table: PLAN                                                  */
/*==============================================================*/
create table PLAN  (
   ID                   VARCHAR(32)                     not null,
   EMPCODE              VARCHAR(20),
   EMPNAME              VARCHAR(50),
   DEPARTCODE           VARCHAR(20),
   DEPARTNAME           VARCHAR(50),
   PJCODE               VARCHAR(20),
   PJCODE_D             VARCHAR(20),
   STAGECODE            VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PLANEDWORKLOAD       INTEGER,
   NOTE                 VARCHAR(500),
   SYMBOL               VARCHAR(50),
   ASSESS               VARCHAR(50),
   REMARK               VARCHAR(200),
   LEADER_STATION       VARCHAR(50),
   LEADER_SECTION       VARCHAR(50),
   LEADER_ROOM          VARCHAR(50),
   PLANNERCODE          VARCHAR(20),
   PLANNERNAME          VARCHAR(50),
   ORDERCODE            INTEGER,
   constraint PK_PLAN primary key (ID)
);

comment on table PLAN is
'�ƻ���';

comment on column PLAN.ID is
'ID';

comment on column PLAN.EMPCODE is
'�����˱���';

comment on column PLAN.EMPNAME is
'����������';

comment on column PLAN.DEPARTCODE is
'���ε�λ����';

comment on column PLAN.DEPARTNAME is
'���ε�λ����';

comment on column PLAN.PJCODE is
'�������';

comment on column PLAN.PJCODE_D is
'��ϵͳ����';

comment on column PLAN.STAGECODE is
'�׶α���';

comment on column PLAN.STARTDATE is
'�ƻ���ʼʱ��';

comment on column PLAN.ENDDATE is
'�ƻ���ֹʱ��';

comment on column PLAN.PLANEDWORKLOAD is
'�ƻ���ʱ';

comment on column PLAN.NOTE is
'�ƻ�����';

comment on column PLAN.SYMBOL is
'��־';

comment on column PLAN.ASSESS is
'����';

comment on column PLAN.REMARK is
'��ע';

comment on column PLAN.LEADER_STATION is
'���쵼';

comment on column PLAN.LEADER_SECTION is
'���쵼';

comment on column PLAN.LEADER_ROOM is
'���쵼';

comment on column PLAN.PLANNERCODE is
'�ƻ�Ա����';

comment on column PLAN.PLANNERNAME is
'�ƻ�Ա����';

comment on column PLAN.ORDERCODE is
'���';

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
/* Table: PROJECT_D                                             */
/*==============================================================*/
create table PROJECT_D  (
   ID                   VARCHAR(32)                     not null,
   PJCODE               VARCHAR(20),
   CODE                 VARCHAR(20),
   NAME                 VARCHAR(50),
   MANAGER              VARCHAR(20),
   STARTDATE            DATE,
   ENDDATE              DATE,
   PLANEDWORKLOAD       INTEGER,
   NOTE                 VARCHAR(500),
   constraint PK_PROJECT_D primary key (ID)
);

comment on table PROJECT_D is
'��ϵͳ��';

comment on column PROJECT_D.ID is
'ID';

comment on column PROJECT_D.PJCODE is
'�������';

comment on column PROJECT_D.CODE is
'��ϵͳ����';

comment on column PROJECT_D.NAME is
'��ϵͳ����';

comment on column PROJECT_D.MANAGER is
'��ϵͳ������';

comment on column PROJECT_D.STARTDATE is
'��ʼ����';

comment on column PROJECT_D.ENDDATE is
'��ֹ����';

comment on column PROJECT_D.PLANEDWORKLOAD is
'�ƻ���ʱ';

comment on column PROJECT_D.NOTE is
'��ע';

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
   PJCODE_D             VARCHAR(20),
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
'�������';

comment on column WORKREPORT.PJCODE_D is
'��ϵͳ����';

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