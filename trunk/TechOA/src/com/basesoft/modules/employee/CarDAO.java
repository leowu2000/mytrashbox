package com.basesoft.modules.employee;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class CarDAO extends CommonDAO {

	/**
	 * 获取班车列表
	 * @param page
	 * @return
	 */
	public PageList findAllCar(int page){
		PageList pageList = new PageList();
		String sql = "select * from CAR order by CARCODE";
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
	 * 获取所有班车列表
	 * @return
	 */
	public List findAll(){
		return jdbcTemplate.queryForList("select * from CAR order by CARCODE");
	}
	
	/**
	 * 根据班车车次获取发车时间
	 * @param carid
	 * @return
	 */
	public List findSendtimeList(String carid){
		return jdbcTemplate.queryForList("select * from CAR_SENDTIME where CARID='" + carid + "'");
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public Car findById(String id){
		Car car = new Car();
		
		List list = jdbcTemplate.queryForList("select * from CAR where ID='" + id + "'");
		if(list.size()>0){
			Map map = (Map)list.get(0);
			car.setId(id);
			car.setCarcode(map.get("CARCODE")==null?"":map.get("CARCODE").toString());
			car.setCarno(map.get("CARNO")==null?"":map.get("CARNO").toString());
			car.setWay(map.get("WAY")==null?"":map.get("WAY").toString());
			car.setDrivername(map.get("DRIVERNAME")==null?"":map.get("DRIVERNAME").toString());
			car.setPhone(map.get("PHONE")==null?"":map.get("PHONE").toString());
			car.setSendlocate(map.get("SENDLOCATE")==null?"":map.get("SENDLOCATE").toString());
		}
		
		return car;
	}
	
	/**
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public CarSendtime findBySendtimeId(String id){
		Map map = jdbcTemplate.queryForMap("select * from CAR_SENDTIME where ID='" + id + "'");
		CarSendtime carSendtime = new CarSendtime();
		
		carSendtime.setId(id);
		carSendtime.setCarcode(map.get("CARCODE")==null?"":map.get("CARCODE").toString());
		carSendtime.setSendtime(map.get("SENDTIME")==null?"":map.get("SENDTIME").toString());
		
		return carSendtime;
	}
	
	/**
	 * 获取预约列表
	 * @param empcode 员工编号
	 * @param page
	 * @return
	 */
	public PageList findOrderList(String empcode, int page){
		PageList pageList = new PageList();
		String sql = "select * from CAR_ORDER where EMPCODE='" + empcode + "' order by ORDERDATE desc, ORDERSENDTIME desc";
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
	 * 根据id获取实例
	 * @param id
	 * @return
	 */
	public CarOrder findByOrderId(String id){
		Map map = jdbcTemplate.queryForMap("select * from CAR_ORDER where ID='" + id + "'");
		
		CarOrder carOrder = new CarOrder();
		carOrder.setId(id);
		carOrder.setCarid(map.get("CARID")==null?"":map.get("CARID").toString());
		carOrder.setEmpcode(map.get("EMPCODE")==null?"":map.get("EMPCODE").toString());
		carOrder.setOrdersendtime(map.get("ORDERSENDTIME")==null?"":map.get("ORDERSENDTIME").toString());
		carOrder.setOrderdate(map.get("ORDERDATE")==null?"":map.get("ORDERDATE").toString());
		carOrder.setStatus(map.get("STATUS")==null?"":map.get("STATUS").toString());
		
		return carOrder;
	}
	
	/**
	 * 获取预约列表
	 * @param carid 班车id
	 * @param datepick 日期
	 * @return
	 */
	public PageList findOrderList(String carid, String datepick, int page){
		PageList pageList = new PageList();
		String sql = "select * from CAR_ORDER where ";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		if("0".equals(carid)){//全部班车
			sql = sql + "ORDERDATE='" + datepick + "'";
		}else {
			sql = sql + "CARID='" + carid + "' and ORDERDATE='" + datepick + "'";
		}
		
		sql = sql + "  order by ORDERDATE desc, ORDERSENDTIME desc";
		
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
	 * 判断是否存在此班车
	 * @param cardno 一卡通号
	 * @param empcode 人员编码
	 * @return
	 */
	public boolean haveCar(String carcode){
		boolean haveCar= false;
		
		List list = jdbcTemplate.queryForList("select * from CAR where CARCODE='" + carcode + "'");
		
		if(list.size()>0){
			haveCar = true;
		}
		
		return haveCar;
	}
}
