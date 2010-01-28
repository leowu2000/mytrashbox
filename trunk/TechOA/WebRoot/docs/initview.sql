/*人员基本信息报表*/
create or replace view VIEW_EMP as
select DISTINCT
a.name,a.empcode,b.name as depart,a.mainjob,a.secjob,a.level,a.email,a.blog,a.selfweb,a.stcphone,a.mobphone,a.address,a.post,c.name as major,d.name as degree,e.name as pro 
        from employee a,department b,dict c,dict d,dict e
	where b.code=a.departcode and c.code=a.majorcode and d.code=a.degreecode and e.code=a.procode
	

/*职工考勤记录*/

create or replace view VIEW_WORKCHECK as
select DISTINCT
	a.*,b.DEPARTCODE,b.NAME as EMPNAME,c.NAME as RESULTNAME
	from WORKCHECK a,EMPLOYEE b,DICT c
	where b.EMPCODE=a.EMPCODE and c.CODE=a.CHECKRESULT

/*工作报告记录*/

create or replace view VIEW_WORKREPORT as
select a.*,b.name as PJNAME,c.name as STAGENAME,d.name as EMPNAME 
	from WORKREPORT a, PROJECT b, DICT c, EMPLOYEE d
	where b.CODE=a.PJCODE and c.CODE=a.STAGECODE and d.EMPCODE=a.EMPCODE