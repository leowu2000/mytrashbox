package com.basesoft.modules.employee;

import java.util.List;
import java.util.Map;

import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class TrainDAO extends EmployeeDAO {
	
	/**
	 * 获取培训名称列表
	 * @return
	 */
	public List<?> getTrainList(){
		String sql = "select ID, NAME from TRAIN_D";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取考核将结果列表
	 * @return
	 */
	public List<?> getAssessList(){
		String sql = "select DISTINCT ASSESS from TRAIN";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 获取所有培训的列表
	 * @param page 页码
	 * @return
	 */
	public PageList findAllTrain(int page){
		PageList pageList = new PageList();
		String sql = "select * from TRAIN_D order by COST desc";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
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
	 * 获取所有培训记录
	 * @param type 查询的类型
	 * @param empcode 员工编号
	 * @param seltrain 培训名称
	 * @param selassess 考核情况
	 * @param cost 费用情况
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(String type, String empcode, String seltrain, String selassess, int cost, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("1".equals(type)){//人员
			if("0".equals(empcode)){//全部人员
				sql = "select * from TRAIN order by EMPCODE";
			}else {
				sql = "select * from TRAIN where EMPCODE='" + empcode + "' order by EMPCODE";
			}
		}else if("2".equals(type)){//课程
			if("".equals(seltrain)){//全部课程
				sql = "select * from TRAIN order by NAME";
			}else {
				sql = "select * from TRAIN where NAME='" + seltrain + "' order by EMPCODE";
			}
		}else if("3".equals(type)){//考核
			if("".equals(selassess)){//全部考核
				sql = "select * from TRAIN order by ASSESS";
			}else {
				sql = "select * from TRAIN where ASSESS='" + selassess + "' order by ASSESS";
			}
		}else if("4".equals(type)){//成本
			if(cost==0){//全部成本
				sql = "select * from TRAIN order by COST desc";
			}else {//成本小于所填写的
				sql = "select * from TRAIN where COST<=" + cost + " order by COST desc";
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
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Train findByTId_D(String id){
		Train train = new Train();
		
		String sql = "select * from TRAIN_D where ID='" + id + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		
		train.setId(id);
		train.setName(map.get("NAME")==null?"":map.get("NAME").toString());
		train.setCost(map.get("COST")==null?0:Float.parseFloat(map.get("COST").toString()));
		train.setTarget(map.get("TARGET")==null?"":map.get("TARGET").toString());
		train.setPlan(map.get("PLAN")==null?"":map.get("PLAN").toString());
		train.setRecord(map.get("RECORD")==null?"":map.get("RECORD").toString());
		train.setResult(map.get("RESULT")==null?"":map.get("RESULT").toString());
		train.setStartdate(map.get("STARTDATE")==null?"":map.get("STARTDATE").toString());
		train.setEnddate(map.get("ENDDATE")==null?"":map.get("ENDDATE").toString());
		
		return train;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Train findByTId(String id){
		Train train = new Train();
		
		String sql = "select * from TRAIN where ID='" + id + "'";
		Map map = jdbcTemplate.queryForMap(sql);
		
		train.setId(id);
		train.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		train.setEmpname(map.get("EMPNAME")==null?"":map.get("EMPNAME").toString());
		train.setAssess(map.get("ASSESS")==null?"":map.get("ASSESS").toString());
		
		return train;
	}
	
	/**
	 * 获取培训参与人员列表
	 * @param trainid 培训id
	 * @param page 页码
	 * @return
	 */
	public PageList findAllTrainEmp(String trainid, int page){
		PageList pageList = new PageList();
		String sql = "select * from TRAIN where TRAINID='" + trainid + "'";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		String sqlData = "select * from( select A.*, ROWNUM RN from (" + sql + ") A where ROWNUM<=" + end + ") WHERE RN>=" + start;
		String sqlCount = "select count(*) from (" + sql + ")" + "";
		
		List list = jdbcTemplate.queryForList(sqlData);
		int count = jdbcTemplate.queryForInt(sqlCount);
		
		pageList.setList(list);
		PageInfo pageInfo = new PageInfo(page, count);
		pageList.setPageInfo(pageInfo);
		
		return pageList;
	}
}
