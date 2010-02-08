package com.basesoft.modules.employee;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.web.multipart.MultipartFile;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;
import com.basesoft.util.StringUtil;

public class EmployeeDAO extends CommonDAO{

	/**
	 * 根据登陆ID得到用户信息
	 * @param loginid
	 * @return
	 */
	public List<?> findByLoginId(String loginid){
		return jdbcTemplate.queryForList("select * from EMPLOYEE where LOGINID='" + loginid + "'");
	}
	
	/**
	 * 获取人员信息
	 * @param emid 人员id
	 * @param departcode 部门编码
	 * @param emname 模糊检索人员名称
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String emid, String departcode, String emname, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		List listChildDepart = getChildDepart(emid);
		
		if("0".equals(departcode)){//全部部门
			List listDepart = getChildDepart(emid);
			String departs = "";
			for(int i=0;i<listDepart.size();i++){
				Map map = (Map)listDepart.get(i);
				if(i==0){
					departs = "'" + map.get("CODE").toString() + "'";
				}else {
					departs = departs + ",'" + map.get("CODE").toString() + "'";
				}
			}
			if("".equals(emname)){//没有名字过滤
				sql = "select * from VIEW_EMP where CODE in (select CODE from EMPLOYEE where DEPARTCODE in (" + departs + ")) order by DEPART desc,LEVEL";
			}else {
				sql = "select * from VIEW_EMP where CODE in (select CODE from EMPLOYEE where DEPARTCODE in (" + departs + ")) and NAME like '%" + emname + "%' order by DEPART desc,LEVEL ";
			}
		}else {
			if("".equals(emname)){
				sql = "select * from VIEW_EMP where CODE in (select CODE from EMPLOYEE where DEPARTCODE = '" + departcode + "') order by DEPART desc,LEVEL";
			}else {
				sql = "select * from VIEW_EMP where CODE in (select CODE from EMPLOYEE where DEPARTCODE = '" + departcode + "') and NAME like '%" + emname + "%' order by DEPART desc,LEVEL";
			}
		}
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
	
	/**
	 * 获取部门下属 员工信息
	 * @param depart 部门
	 * @param emid 员工id
	 * @return
	 */
	public List<?> findEmployeeByDepart(String depart, String emid){
		if("0".equals(depart)){
			//获取下级部门列表
			List listDepart = getChildDepart(emid);
			String departs = "";
			for(int i=0;i<listDepart.size();i++){
				Map map = (Map)listDepart.get(i);
				if(i==0){
					departs = "'" + map.get("CODE").toString() + "'";
				}else {
					departs = departs + ",'" + map.get("CODE").toString() + "'";
				}
			}
			return jdbcTemplate.queryForList("select * from EMPLOYEE where DEPARTCODE in (" + departs + ")");
		}else {
			return jdbcTemplate.queryForList("select * from EMPLOYEE where DEPARTCODE='" + depart + "'");
		}
	}
	
	/**
	 * 获取职工考勤
	 * @param start 起始日期
	 * @param end 截止日期
	 * @param depart 单位code
	 * @param emid 员工id
	 * @return
	 */
	public List<?> findWorkCheck(String start, String end, String depart, String emid){
		List<Date> listDate = StringUtil.getDateList(start, end);
		List returnList = new ArrayList();
		
		//获取雇员信息
		Map mapEm = findByEmId(emid);
		
		if("003".equals(mapEm.get("ROLECODE").toString())){//普通员工只能看到自己的考勤
			Map returnMap = new HashMap();
			returnMap.put("NAME", mapEm.get("NAME"));
			for(int i=0;i<listDate.size();i++){//循环日期,给出这一个月的考勤记录，没有的放空
				Date date = listDate.get(i);
				
				List l = jdbcTemplate.queryForList("select * from VIEW_WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE='" + StringUtil.DateToString(date, "yyyy-MM-dd") + "'");
				if(l.size()>0){
					Map m = (Map)l.get(0);
					returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), m.get("RESULTNAME"));
				}else {
					returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), "");
				}
			}
			//给出迟到,早退,病假,事假,旷工的一个月的小结
			returnMap.put("cd", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400002'"));
			returnMap.put("zt", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400003'"));
			returnMap.put("bj", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400004'"));
			returnMap.put("sj", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400005'"));
			returnMap.put("kg", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400006'"));
			
			returnList.add(returnMap);
		}else {//领导和管理员看整个部门的
			List listEmployee = findEmployeeByDepart(depart, emid);
			for(int i=0;i<listEmployee.size();i++){//循环部门中的雇员
				Map returnMap = new HashMap();
				Map mapEmployee = (Map)listEmployee.get(i);
				returnMap.put("NAME", mapEmployee.get("NAME"));
				
				for(int j=0;j<listDate.size();j++){//循环日期,给出这一个月的考勤记录，没有的放空
					Date date = listDate.get(j);
					List l = jdbcTemplate.queryForList("select * from VIEW_WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE='" + StringUtil.DateToString(date, "yyyy-MM-dd") + "'");
					if(l.size()>0){
						Map m = (Map)l.get(0);
						returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), m.get("RESULTNAME"));
					}else {
						returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), "");
					}
				}
				
				//给出迟到,早退,病假,事假,旷工的一个月的小结
				returnMap.put("cd", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400002'"));
				returnMap.put("zt", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400003'"));
				returnMap.put("bj", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400004'"));
				returnMap.put("sj", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400005'"));
				returnMap.put("kg", jdbcTemplate.queryForInt("select sum(EMPTYHOURS) from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400006'"));
			
				returnList.add(returnMap);
			}
		}
		
		return returnList;
	}
	
	/**
	 * 获取登陆用户的菜单列表
	 * @return
	 */
	public String getMenu(String rolecode){
		StringBuffer sb = new StringBuffer();
		
		//获取功能列表中的菜单功能项
		List listMenu = jdbcTemplate.queryForList("select * from FUNCTION where FUNCCODE in (select FUNCCODE from FUNCROLE where ROLECODE='" + rolecode + "') and STATUS='1' and FUNCTYPE='1' order by ORDERCODE");
		
		for(int i=0;i<listMenu.size();i++){
			Map mapMenu = (Map)listMenu.get(i);
			
			if(i==0){
				sb.append("{text:'")
				  .append(mapMenu.get("FUNCNAME"))
				  .append("',id:'")
				  .append(i)
				  .append("',hrefTarget:'main',icon:'images/icons/")
				  .append(i+1)
				  .append(".png',href:'")
				  .append(mapMenu.get("FUNCURL"))
				  .append("',leaf:true}");
			}else {
				sb.append(",{text:'")
				  .append(mapMenu.get("FUNCNAME"))
				  .append("',id:'")
				  .append(i)
				  .append("',hrefTarget:'main',icon:'images/icons/")
				  .append(i+1)
				  .append(".png',href:'")
				  .append(mapMenu.get("FUNCURL"))
				  .append("',leaf:true}");
			}
		}
		
		if(listMenu.size()>0){
			sb.append(",{text:'")
			  .append("退出")
			  .append("',id:'")
			  .append(listMenu.size())
			  .append("',icon:'images/icons/close.png',href:'")
			  .append("login.do?action=logout")
			  .append("',leaf:true}");
		}else {
			sb.append("{text:'")
			  .append("退出")
			  .append("',id:'")
			  .append(listMenu.size())
			  .append("',icon:'images/icons/close.png',href:'")
			  .append("login.do?action=logout")
			  .append("',leaf:true}");
		}
		
		return sb.toString();
	}
	
	/**
	 * 根据id获取实例
	 * @param id 员工id
	 * @return
	 */
	public Employee findById(String id){
		Employee em = new Employee();
		
		Map mapEm = findByEmId(id);
		
		em.setId(id);
		em.setLoginid(mapEm.get("LOGINID").toString());
		em.setPassword(mapEm.get("PASSWORD").toString());
		em.setCode(mapEm.get("CODE").toString());
		em.setRolecode(mapEm.get("ROLECODE").toString());
		em.setName(mapEm.get("NAME").toString());
		em.setDepartcode(mapEm.get("DEPARTCODE").toString());
		em.setMainjob(mapEm.get("MAINJOB").toString());
		em.setSecjob(mapEm.get("SECJOB").toString());
		em.setLevel(mapEm.get("LEVEL").toString());
		em.setEmail(mapEm.get("EMAIL").toString());
		em.setBlog(mapEm.get("BLOG").toString());
		em.setSelfweb(mapEm.get("SELFWEB").toString());
		em.setStcphone(mapEm.get("STCPHONE").toString());
		em.setMobphone(mapEm.get("MOBPHONE").toString());
		em.setAddress(mapEm.get("ADDRESS").toString());
		em.setPost(mapEm.get("POST").toString());
		em.setMajorcode(mapEm.get("MAJORCODE").toString());
		em.setDegreecode(mapEm.get("DEGREECODE").toString());
		em.setProcode(mapEm.get("PROCODE").toString());
		
		return em;
	}
	
	/**
	 * 修改照片
	 * @param rtable 关联表
	 * @param rcolumn 关联字段
	 * @param rvalue 关联值
	 * @param type 类型
	 * @param fname 文件名
	 * @param f 文件
	 */
	public void updatePhoto(String rtable, String rcolumn, String rvalue, String type, String fname, MultipartFile f)throws Exception{
		final InputStream is = f.getInputStream();
		final int length = (int)f.getSize();
		final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		final String r_table = rtable;
		final String r_column = rcolumn;
		final String r_value = rvalue;
		final String _type = type;
		final String f_name = fname;
		final String f_type = fname.substring(fname.length()-3,fname.length());
		
		
		final LobHandler lobHandler=new DefaultLobHandler();
		jdbcTemplate.execute("update ATTACHMENT set FNAME=?, FTYPE=?, FSIZE=?, CONTENT=?  where RTABLE='" + r_table + "' and RCOLUMN='" + r_column + "' and RVALUE='" + r_value + "' and TYPE='" + _type + "'",
			new AbstractLobCreatingPreparedStatementCallback(lobHandler){ 
				protected void setValues(PreparedStatement pstmt,LobCreator lobCreator) throws SQLException{
					pstmt.setString(1, f_name);
					pstmt.setString(2, f_type);
					pstmt.setInt(3, length);
					lobCreator.setBlobAsBinaryStream(pstmt,4,is,length);
				}
			}
		);
		is.close();
	}
	
	/**
	 * 
	 * @param emcode 人员编码
	 * @return
	 */
	public boolean havaPhoto(String empcode){
		int count = jdbcTemplate.queryForInt("select count(*)from ATTACHMENT where RTABLE='EMPLOYEE' and RCOLUMN='CODE' and RVALUE='" + empcode + "' and type='1'");
		
		if(count==1){
			return true;
		}else {
			return false;
		}
	}
}
