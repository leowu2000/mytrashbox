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
		return jdbcTemplate.queryForList("select * from EMPLOYEE where CODE='" + loginid + "'");
	}
	
	/**
	 * 获取人员信息
	 * @param emid 人员id
	 * @param departcode 部门编码
	 * @param emname 模糊检索人员名称
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String departcodes, String emname, String empcode, int page, String h_year, String h_name){
		PageList pageList = new PageList();
		String sql = "select * from EMPLOYEE where ROLECODE!='000' ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if(!"''".equals(departcodes)&&!"'0'".equals(departcodes)){//部门
			sql = sql + " and DEPARTCODE in (" + departcodes + ")";
		}
		if(!"".equals(emname)){//姓名
			sql = sql + " and NAME like '%" + emname + "%'";
		}
		if(!"".equals(empcode)){//工号
			sql = sql + " and CODE like '%" + empcode + "%'";
		}
		if(!"".equals(h_year)){//荣誉年份
			sql = sql + " and CODE in (select EMPCODE from EMP_HONOR where H_YEAR=" + h_year + ")";
		}
		if(!"".equals(h_name)){//荣誉名称
			sql = sql + " and CODE in (select EMPCODE from EMP_HONOR where H_NAME like '%" + h_name + "%')";
		}
		sql = sql + " order by DEPARTCODE,NAME";
		
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
	public List<?> findEmployeeByDepart(String depart){
		if("0".equals(depart)){
			//获取下级部门列表
			List listDepart = getDepartment();
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
	 * @param empcode 员工code
	 * @return
	 */
	public List<?> findWorkCheck(String start, String end, String depart, String method, String empcode, String departcodes){
		List<Date> listDate = StringUtil.getDateList(start, end);
		List returnList = new ArrayList();

		if("search".equals(method)){//综合信息查询只查找单个人员的考勤信息
			//获取雇员信息
			Map mapEm = findByCode("EMPLOYEE", empcode);
			Map returnMap = new HashMap();
			returnMap.put("NAME", mapEm.get("NAME"));
			returnMap.put("EMPCODE", mapEm.get("CODE"));
			for(int i=0;i<listDate.size();i++){//循环日期,给出这一个月的考勤记录，没有的放空
				Date date = listDate.get(i);
				
				List l = jdbcTemplate.queryForList("select * from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE='" + StringUtil.DateToString(date, "yyyy-MM-dd") + "'");
				if(l.size()>0){
					Map m = (Map)l.get(0);
					String resultname = findNameByCode("DICT", m.get("CHECKRESULT").toString());
					returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), resultname);
				}else {
					returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), "");
				}
			}
			//给出迟到,早退,病假,事假,旷工的一个月的小结
			String queryCount = "select sum(EMPTYHOURS) as AMOUNT,CHECKRESULT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' group by CHECKRESULT";
			List listCount = jdbcTemplate.queryForList(queryCount);
			for(int i=0;i<listCount.size();i++){
				Map mapCount = (Map)listCount.get(i);
				String checkresult = mapCount.get("CHECKRESULT")==null?"":mapCount.get("CHECKRESULT").toString();
				if("400002".equals(checkresult)){
					returnMap.put("cd", mapCount.get("AMOUNT"));
				}else if("400003".equals(checkresult)){
					returnMap.put("zt", mapCount.get("AMOUNT"));
				}else if("400004".equals(checkresult)){
					returnMap.put("bj", mapCount.get("AMOUNT"));
				}else if("400005".equals(checkresult)){
					returnMap.put("sj", mapCount.get("AMOUNT"));
				}else if("400006".equals(checkresult)){
					returnMap.put("kg", mapCount.get("AMOUNT"));
				}
			}
//			returnMap.put("cd", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400002'").get("AMOUNT"));
//			returnMap.put("zt", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400003'").get("AMOUNT"));
//			returnMap.put("bj", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400004'").get("AMOUNT"));
//			returnMap.put("sj", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400005'").get("AMOUNT"));
//			returnMap.put("kg", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400006'").get("AMOUNT"));
			
			returnList.add(returnMap);
		}else {//领导和管理员看整个部门的
			List listEmployee = new ArrayList();
			if(!"".equals(departcodes)){
				listEmployee = jdbcTemplate.queryForList("select * from EMPLOYEE where DEPARTCODE in (" + departcodes + ")");
			}else {
				listEmployee = jdbcTemplate.queryForList("select * from EMPLOYEE where CODE='" + empcode + "'");
			}
			for(int i=0;i<listEmployee.size();i++){//循环部门中的雇员
				Map returnMap = new HashMap();
				Map mapEm = (Map)listEmployee.get(i);
				returnMap.put("NAME", mapEm.get("NAME"));
				returnMap.put("EMPCODE", mapEm.get("CODE"));
				for(int j=0;j<listDate.size();j++){//循环日期,给出这一个月的考勤记录，没有的放空
					Date date = listDate.get(j);
					List l = jdbcTemplate.queryForList("select * from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE='" + StringUtil.DateToString(date, "yyyy-MM-dd") + "'");
					if(l.size()>0){
						Map m = (Map)l.get(0);
						String resultname = findNameByCode("DICT", m.get("CHECKRESULT").toString());
						returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), resultname);
					}else {
						returnMap.put(StringUtil.DateToString(date, "yyyy-MM-dd"), "");
					}
				}
				
				//给出迟到,早退,病假,事假,旷工的一个月的小结
				String queryCount = "select sum(EMPTYHOURS) as AMOUNT,CHECKRESULT from WORKCHECK where EMPCODE='" + mapEm.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' group by CHECKRESULT";
				List listCount = jdbcTemplate.queryForList(queryCount);
				for(int j=0;j<listCount.size();j++){
					Map mapCount = (Map)listCount.get(j);
					String checkresult = mapCount.get("CHECKRESULT")==null?"":mapCount.get("CHECKRESULT").toString();
					if("400002".equals(checkresult)){
						returnMap.put("cd", mapCount.get("AMOUNT"));
					}else if("400003".equals(checkresult)){
						returnMap.put("zt", mapCount.get("AMOUNT"));
					}else if("400004".equals(checkresult)){
						returnMap.put("bj", mapCount.get("AMOUNT"));
					}else if("400005".equals(checkresult)){
						returnMap.put("sj", mapCount.get("AMOUNT"));
					}else if("400006".equals(checkresult)){
						returnMap.put("kg", mapCount.get("AMOUNT"));
					}
				}
//				returnMap.put("cd", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400002'").get("AMOUNT"));
//				returnMap.put("zt", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400003'").get("AMOUNT"));
//				returnMap.put("bj", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400004'").get("AMOUNT"));
//				returnMap.put("sj", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400005'").get("AMOUNT"));
//				returnMap.put("kg", jdbcTemplate.queryForMap("select sum(EMPTYHOURS) as AMOUNT from WORKCHECK where EMPCODE='" + mapEmployee.get("CODE") + "' and CHECKDATE>='" + start + "' and CHECKDATE<='" + end + "' and CHECKRESULT='400006'").get("AMOUNT"));
			
				returnList.add(returnMap);
			}
		}
		
		return returnList;
	}
	
	/**
	 * 获取考勤的统计
	 * @param departcodes 部门编码
	 * @param checkresult 考勤编码
	 * @return
	 */
	public int findWorkCheck_lead(String departcode, String checkresult){
		int r = 0;
		String querySql = "select count(EMPCODE) as AMOUNT from WORKCHECK where EMPCODE in (select CODE from EMPLOYEE where DEPARTCODE='" + departcode + "') and CHECKDATE='" + StringUtil.DateToString(new Date(), "yyyy-MM-dd") + "' and CHECKRESULT='" + checkresult + "'";
		
		List listWorkCheck = jdbcTemplate.queryForList(querySql);
		if(listWorkCheck.size()>0){
			Map mapWorkCheck = (Map)listWorkCheck.get(0);
			r = mapWorkCheck.get("AMOUNT")==null?0:Integer.parseInt(mapWorkCheck.get("AMOUNT").toString());
		}
		return r;
	}
	
	/**
	 * 获取单人某天的考勤记录
	 * @param empcode
	 * @param datepick
	 * @return
	 */
	public List findWorkCheck(String empcode, String datepick){
		String sql = "select * from WORKCHECK where EMPCODE='" + empcode + "' and CHECKDATE='" + datepick + "'";
		return jdbcTemplate.queryForList(sql);
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
		em.setLoginid(mapEm.get("LOGINID")==null?"":mapEm.get("LOGINID").toString());
		em.setPassword(mapEm.get("PASSWORD")==null?"":mapEm.get("PASSWORD").toString());
		em.setCode(mapEm.get("CODE")==null?"":mapEm.get("CODE").toString());
		em.setRolecode(mapEm.get("ROLECODE")==null?"":mapEm.get("ROLECODE").toString());
		em.setName(mapEm.get("NAME")==null?"":mapEm.get("NAME").toString());
		em.setDepartcode(mapEm.get("DEPARTCODE")==null?"":mapEm.get("DEPARTCODE").toString());
		em.setMainjob(mapEm.get("MAINJOB")==null?"":mapEm.get("MAINJOB").toString());
		em.setSecjob(mapEm.get("SECJOB")==null?"":mapEm.get("SECJOB").toString());
		em.setLevel(mapEm.get("LEVEL")==null?"":mapEm.get("LEVEL").toString());
		em.setEmail(mapEm.get("EMAIL")==null?"":mapEm.get("EMAIL").toString());
		em.setBlog(mapEm.get("BLOG")==null?"":mapEm.get("BLOG").toString());
		em.setSelfweb(mapEm.get("SELFWEB")==null?"":mapEm.get("SELFWEB").toString());
		em.setStcphone(mapEm.get("STCPHONE")==null?"":mapEm.get("STCPHONE").toString());
		em.setMobphone(mapEm.get("MOBPHONE")==null?"":mapEm.get("MOBPHONE").toString());
		em.setAddress(mapEm.get("ADDRESS")==null?"":mapEm.get("ADDRESS").toString());
		em.setPost(mapEm.get("POST")==null?"":mapEm.get("POST").toString());
		em.setMajorcode(mapEm.get("MAJORCODE")==null?"":mapEm.get("MAJORCODE").toString());
		em.setDegreecode(mapEm.get("DEGREECODE")==null?"":mapEm.get("DEGREECODE").toString());
		em.setProcode(mapEm.get("PROCODE")==null?"":mapEm.get("PROCODE").toString());
		em.setIdcard(mapEm.get("IDCARD")==null?"":mapEm.get("IDCARD").toString());
		
		String departname = findNameByCode("DEPARTMENT", mapEm.get("DEPARTCODE")==null?"":mapEm.get("DEPARTCODE").toString());
		em.setDepartname(departname);
		
		Map mapDepart = findByCode("DEPARTMENT", em.getDepartcode());
		em.setP_depart(mapDepart.get("PARENT")==null?"":mapDepart.get("PARENT").toString());
		mapDepart = findByCode("DEPARTMENT", em.getP_depart());
		em.setP_depart2(mapDepart.get("PARENT")==null?"":mapDepart.get("PARENT").toString());
		
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
	public boolean havePhoto(String empcode){
		int count = jdbcTemplate.queryForInt("select count(*)from ATTACHMENT where RTABLE='EMPLOYEE' and RCOLUMN='CODE' and RVALUE='" + empcode + "' and type='1'");
		
		if(count==1){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 获取员工投入分析结果列表
	 * @param empcodes 选择的员工
	 * @param startdate 起始日期
	 * @param enddate 截止日期
	 * @param selproject 工作令号
	 * @return
	 */
	public List getYgtrfx(String empcodes, String startdate, String enddate, String selproject){
		List listYgtrfx = new ArrayList();
		String sql = "";
		
		String[] empcode = empcodes.split(",");
		for(int i=0;i<empcode.length;i++){
			Map mapYgtrfx = findByCode("EMPLOYEE", empcode[i]);
			String degreename = findNameByCode("DICT", mapYgtrfx.get("DEGREECODE")==null?"":mapYgtrfx.get("DEGREECODE").toString());
			String proname = findNameByCode("DICT", mapYgtrfx.get("PROCODE")==null?"":mapYgtrfx.get("PROCODE").toString());
			String majorname = findNameByCode("DICT", mapYgtrfx.get("MAJORCODE")==null?"":mapYgtrfx.get("MAJORCODE").toString());
			String departname = findNameByCode("DEPARTMENT", mapYgtrfx.get("DEPARTCODE")==null?"":mapYgtrfx.get("DEPARTCODE").toString());
			
			sql = "select sum(AMOUNT) as AMOUNT from WORKREPORT where EMPCODE='" + empcode[i] + "' and FLAG=2";
			if(!"0".equals(selproject)){
				sql = sql + " and PJCODE='" + selproject + "'";
			}
			if(!"".equals(startdate)){
				sql = sql + " and STARTDATE>='" + startdate + "'";
			}
			if(!"".equals(enddate)){
				sql = sql + " and STARTDATE<='" + enddate + "'";
			}
			
			Map map = jdbcTemplate.queryForMap(sql);
			
			float amount = map.get("AMOUNT")==null?0:Float.parseFloat(map.get("AMOUNT").toString());
			mapYgtrfx.put("AMOUNT", amount);
			mapYgtrfx.put("MAJORNAME", majorname);
			mapYgtrfx.put("DEGREENAME", degreename);
			mapYgtrfx.put("PRONAME", proname);
			mapYgtrfx.put("DEPARTNAME", departname);
			listYgtrfx.add(mapYgtrfx);
		}
		return listYgtrfx;
	}
}
