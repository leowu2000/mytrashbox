package com.basesoft.modules.menu;

import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.core.PageInfo;
import com.basesoft.core.PageList;

public class MenuDAO extends CommonDAO {

	/**
	 * 获取分页菜单列表
	 * @param page 页码
	 * @return
	 */
	public PageList findAll(int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select * from MENU where STATUS='1' order by ORDERCODE";
		
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
	 * 获取分页已收藏菜单列表
	 * @param page 页码
	 * @return
	 */
	public PageList findAllFavor(String empcode, int page){
		PageList pageList = new PageList();
		String sql = "";
		int pagesize = 20;
		int start = pagesize*(page - 1) + 1;
		int end = pagesize*page;
		
		sql = "select a.*,b.* from USER_MENU a, MENU b where a.EMPCODE='" + empcode + "' and b.MENUCODE=a.MENUCODE and b.STATUS='1' order by ordercode";
		
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
	 * 获取父菜单的编码
	 * @param menucode 菜单编码
	 * @return
	 */
	public String getParentname(String menucode){
		String code = "";
		String sql = "select MENUNAME from MENU where MENUCODE='" + menucode + "'";
		
		List list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			Map map = (Map)list.get(0);
			code = map.get("MENUNAME").toString();
		}
		
		return code;
	}
	
	/**
	 * 取得父菜单列表
	 * @return
	 */
	public List findParents(){
		String sql = "select * from MENU where MENUTYPE='2' and STATUS='1' order by ordercode";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 取得未收藏的子菜单列表
	 * @return
	 */
	public List findChilds(String empcode, String emrole){
		String sql = "select * from MENU where MENUTYPE='1' and STATUS='1' and MENUCODE not in (select MENUCODE from USER_MENU where EMPCODE='" + empcode + "' and TYPE='2') and MENUCODE in (select MENUCODE from USER_MENU where EMPCODE='" + emrole + "') order by ordercode";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 根据菜单编码得到
	 * @param menucode
	 * @return
	 */
	public Menu findByMenuCode(String menucode){
		Menu menu = new Menu();
		
		String sql = "select * from MENU where MENUCODE='" + menucode + "'";
		List list = jdbcTemplate.queryForList(sql);
		
		if(list.size()>0){
			Map map = (Map)list.get(0);
			
			menu.setMenucode(map.get("MENUCODE")==null?"":map.get("MENUCODE").toString());
			menu.setMenuname(map.get("MENUNAME")==null?"":map.get("MENUNAME").toString());
			menu.setMenutype(map.get("MENUTYPE")==null?"":map.get("MENUTYPE").toString());
			menu.setMenuurl(map.get("MENUURL")==null?"":map.get("MENUURL").toString());
			menu.setOrdercode(map.get("ORDERCODE")==null?500:Integer.parseInt(map.get("ORDERCODE").toString()));
			menu.setStatus(map.get("STATUS")==null?"":map.get("STATUS").toString());
			menu.setParent(map.get("PARENT")==null?"":map.get("PARENT").toString());
		}
		
		return menu;
	}
	
	/**
	 * 获取菜单
	 * @param emrole 角色编码
	 * @return
	 */
	public String getMenu(String code){
		StringBuffer sb = new StringBuffer();
		
		//先获取父菜单
		String sqlParent = "select * from MENU where MENUCODE in (select MENUCODE from USER_MENU where EMPCODE='" + code + "') and MENUTYPE='2' and STATUS='1' order by ORDERCODE";
		
		List listParent = jdbcTemplate.queryForList(sqlParent);
		
		sb.append("[");
		
		for(int i=0;i<listParent.size();i++){//循环父菜单
			Map mapParent = (Map)listParent.get(i);
			
			sb.append("{text:'")
			  .append(mapParent.get("MENUNAME"))
			  .append("',id:'")
			  .append(mapParent.get("MENUCODE"))
			  .append("',leaf:false,icon:'images/icons/")
			  .append(mapParent.get("ICON"))
			  .append(".png',children:[");
			
			//取子菜单
			String sqlChild = "select * from MENU where PARENT='" + mapParent.get("MENUCODE") + "' and MENUCODE in (select MENUCODE from USER_MENU where EMPCODE='" + code + "') and STATUS='1' ORDER BY ORDERCODE";
		
			List listChild = jdbcTemplate.queryForList(sqlChild);
			
			for(int j=0;j<listChild.size();j++){//循环子菜单
				Map mapChild = (Map)listChild.get(j);
				
				sb.append("{text:'")
				  .append(mapChild.get("MENUNAME"))
				  .append("',id:'")
				  .append(mapChild.get("MENUCODE"))
				  .append("',leaf:true,hrefTarget:'main',icon:'images/icons/")
				  .append(mapChild.get("ICON"))
				  .append(".png',href:'")
				  .append(mapChild.get("MENUURL"))
				  .append("'}");
				if(j<listChild.size()-1){
					sb.append(",");
				}
			}
			
			sb.append("]},");
		}
		
		sb.append("{text:'退出', id:'6', leaf:true, icon:'images/icons/close.png', href:'login.do?action=logout'}]");
		
		return sb.toString();
	}
	
	/**
	 * 是否存在收藏项
	 * @param empcode 人员编码
	 * @param menucode 菜单编码
	 * @return
	 */
	public boolean existFavor(String empcode, String menucode){
		boolean exist = false;
	
		List list = jdbcTemplate.queryForList("select * from USER_MENU where EMPCODE='" + empcode + "' and MENUCODE='" + menucode + "' and TYPE='2'");
		
		if(list.size()>0){
			exist = true;
		}
		
		return exist;
	}
}
