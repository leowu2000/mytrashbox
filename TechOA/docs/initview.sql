/*人员基本信息报表*/
create or replace view VIEW_EMP as
select DISTINCT
a.id,a.name,a.code,b.name as depart,a.mainjob,a.secjob,a.level,a.email,a.blog,a.selfweb,a.stcphone,a.mobphone,a.address,a.post,a.describe,c.name as major,d.name as degree,e.name as pro 
        from employee a,department b,dict c,dict d,dict e
	where (b.code=a.departcode or a.departcode is null) and (c.code=a.majorcode or a.majorcode is null) and (d.code=a.degreecode or a.degreecode is null) and (e.code=a.procode or a.procode is null);
	

/*职工考勤记录*/

create or replace view VIEW_WORKCHECK as
select DISTINCT
	a.*,b.DEPARTCODE,b.NAME as EMPNAME,c.NAME as RESULTNAME
	from WORKCHECK a,EMPLOYEE b,DICT c
	where b.CODE=a.EMPCODE and c.CODE=a.CHECKRESULT;

/*工作报告记录*/

create or replace view VIEW_WORKREPORT as
select a.*,b.name as PJNAME,c.name as STAGENAME,d.name as EMPNAME 
	from WORKREPORT a, PROJECT b, DICT c, EMPLOYEE d
	where b.CODE=a.PJCODE and c.CODE=a.STAGECODE and d.CODE=a.EMPCODE;
	
/*计划*/

create or replace view VIEW_PLAN as 
select a.*,b.NAME as PJNAME,c.NAME as PJNAME_D,d.NAME as STAGENAME
	from PLAN a, PROJECT b, PROJECT_D c, DICT d
	where b.CODE=a.PJCODE and c.CODE=a.PJCODE_D and d.CODE=a.STAGECODE and d.TYPE='5'