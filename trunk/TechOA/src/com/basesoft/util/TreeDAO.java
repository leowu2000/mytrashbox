package com.basesoft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeDAO extends com.basesoft.modules.depart.DepartmentDAO {
	/**
	 * 生成tree  list
	 * @param state 2:只有部门;1:部门加人员
	 * @param checked 已选的项，用逗号分隔
	 * @param rolecode 角色编码
	 * @param empcode 
	 * @return
	 */
	public List<CheckBoxTree> getDepartEmpTree(String state, String checked, String empcode, String rolecode) {
		String[] checkedEmps = checked.split(",");
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		//获取第一级部门
		int maxLevel = getMaxLevel();
		List listDepart = new ArrayList();
		for(int i=1;i<=maxLevel;i++){
			listDepart = findByRoleAndEmpcode(empcode, rolecode, i, "");
			if(listDepart.size()>0){
				break;
			}
		}
		
		for (int i=0;i<listDepart.size();i++) {
			Map mapDepart = (Map)listDepart.get(i);
			String departname = mapDepart.get("NAME").toString();
			if(departname.length()>10){
				departname = departname.substring(0, 9) + "...";
			}
			//第一级部门的节点
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapDepart.get("CODE").toString());
			tree.setText(departname);
			tree.setLeaf(false);
			for(int l=0;l<checkedEmps.length;l++){
				if(tree.getId().equals(checkedEmps[l])){
					tree.setChecked(true);
					break;
				}
			}
			
			//下级部门及本部门人员的list
			List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
			//设置本部门人员
			List<CheckBoxTree> leafListEMP = new ArrayList<CheckBoxTree>();
			if("1".equals(state)){
				leafListEMP = getLeafEMP(mapDepart.get("CODE").toString(), checked, tree, new CheckBoxTree(), new CheckBoxTree());
			}
			 
			//下级部门
			List<CheckBoxTree> leafListDepart = new ArrayList<CheckBoxTree>();
			//根据登陆用户的数据权限获取第二级部门
			List listDepartChild = findByRoleAndEmpcode(empcode, rolecode, 0, mapDepart.get("CODE").toString());
			if((listDepartChild.size() == 0&&"2".equals(state))||(listDepartChild.size() == 0&&leafListEMP.size() == 0&&"1".equals(state))){//下面没东西的时候设置为叶子
				tree.setLeaf(true);
			}
			for(int j=0;j<listDepartChild.size();j++){//第二级部门
				Map mapChild = (Map)listDepartChild.get(j);
				String childname = mapChild.get("NAME").toString();
				if(childname.length()>10){
					childname = childname.substring(0, 9) + "...";
				}
				CheckBoxTree leaf = new CheckBoxTree();
				leaf.setId(mapChild.get("CODE").toString());
				leaf.setText(childname);
				leaf.setLeaf(false);
				for(int l=0;l<checkedEmps.length;l++){
					if(leaf.getId().equals(checkedEmps[l])){
						leaf.setChecked(true);
						tree.setChecked(true);
						break;
					}
				}
				//下级部门及本部门人员的list
				List<CheckBoxTree> leafList2 = new ArrayList<CheckBoxTree>();
				//本部门人员
				List<CheckBoxTree> leafListEMP2 = new ArrayList<CheckBoxTree>(); 
				if("1".equals(state)){
					leafListEMP2 = getLeafEMP(mapChild.get("CODE").toString(), checked, leaf, tree, new CheckBoxTree());
				}
					
				//下级部门
				List<CheckBoxTree> leafListDepart2 = new ArrayList<CheckBoxTree>();
				//根据登陆用户的数据权限获取第三级部门
				List listDepartChild2 = findByRoleAndEmpcode(empcode, rolecode, 0, mapChild.get("CODE").toString());
				if((listDepartChild2.size() == 0&&"2".equals(state))||(listDepartChild.size() == 0&&leafListEMP2.size() == 0&&"1".equals(state))){//下面没东西的时候设置为叶子
					leaf.setLeaf(true);
				}
				for(int k=0;k<listDepartChild2.size();k++){//第三级部门
					Map mapChild2 = (Map)listDepartChild2.get(k);
					String childname2 = mapChild2.get("NAME").toString();
					if(childname2.length()>10){
						childname2 = childname2.substring(0, 9) + "...";
					}
					CheckBoxTree leaf2 = new CheckBoxTree();
					leaf2.setId(mapChild2.get("CODE").toString());
					leaf2.setText(childname2);
					leaf2.setLeaf(false);
					if("2".equals(state)){
						for(int m=0;m<checkedEmps.length;m++){
							if(leaf2.getId().equals(checkedEmps[m])){
								leaf2.setChecked(true);
								leaf.setChecked(true);
								break;
							}
						}
					}
					//设置部门下人员
					List<CheckBoxTree> leafListEMP3 = new ArrayList<CheckBoxTree>();
					if("1".equals(state)){
						leafListEMP3 = getLeafEMP(mapChild2.get("CODE").toString(), checked, leaf2, leaf, tree);
					}
					if(("2".equals(state))||(listDepartChild.size() == 0&&leafListEMP3.size() == 0&&"1".equals(state))){//下面没东西的时候设置为叶子
						leaf2.setLeaf(true);
					}
					leaf2.setChildren(leafListEMP3);
					
					leafListDepart2.add(leaf2);
					
				}
				leafListDepart.add(leaf);
				
				//添加本部门人员和下级部门
				if("1".equals(state)){
					leafList2.addAll(leafListEMP2);
				}
				leafList2.addAll(leafListDepart2);
				
				leaf.setChildren(leafList2);
			}
				
			//添加本部门和下级部门
			if("1".equals(state)){
				leafList.addAll(leafListEMP);
			}
			leafList.addAll(leafListDepart);
			
			tree.setChildren(leafList);
			treeList.add(tree);
			
		}
		return treeList;
	}

	/**
	 * 设置部门下人员
	 * @param departcode 部门编码
	 * @return
	 */
	public List<CheckBoxTree> getLeafEMP(String departcode, String checkedEmp, CheckBoxTree tree1, CheckBoxTree tree2, CheckBoxTree tree3){
		String[] checkedEmps = checkedEmp.split(",");
		//设置节点下人员
		List listEmp = this.findEmpsByDepart(departcode);
		List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
		for(int j=0;j<listEmp.size();j++){
			Map mapEmp = (Map)listEmp.get(j);
			CheckBoxTree leaf = new CheckBoxTree();
			leaf.setId(mapEmp.get("CODE").toString());
			leaf.setText(mapEmp.get("NAME").toString());
			leaf.setLeaf(true);
			for(int k=0;k<checkedEmps.length;k++){
				if(leaf.getId().equals(checkedEmps[k])){
					leaf.setChecked(true);
					tree1.setChecked(true);
					tree2.setChecked(true);
					tree3.setChecked(true);
					break;
				}
			}
			
			leafList.add(leaf);
		}
		
		return leafList;
	}
	
	
	/**
	 * 获取部门工作令号树
	 * @param checkedEmp
	 * @return
	 */
	public List<CheckBoxTree> getProjectTree(String sel_pjname) {
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		List<?> listProject = searchProjects(sel_pjname);
		for (int i=0;i<listProject.size();i++) {//工作令号
			Map mapDepart = (Map)listProject.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapDepart.get("CODE").toString());
			tree.setText(mapDepart.get("NAME").toString());
			tree.setLeaf(true);
			treeList.add(tree);
			
		}
		return treeList;
	}
	
	/**
	 * 获取多选字段树
	 * @param listCols
	 * @param checkedEmp
	 * @return
	 */
	public List<CheckBoxTree> getColumnTree(List listCols, String checkedCol) {
		String[] checkedCols = checkedCol.split(",");
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		for (int i=0;i<listCols.size();i++) {//工作令号
			Map mapCol = (Map)listCols.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapCol.get("COLUMN_NAME").toString());
			tree.setText(mapCol.get("COL_DESCRIPTION").toString());
			tree.setLeaf(true);
			for(int j=0;j<checkedCols.length;j++){
				if(tree.getId().equals(checkedCols[j])){
					tree.setChecked(true);
					break;
				}else {
					tree.setChecked(false);
				}
			}
			treeList.add(tree);
			
		}
		return treeList;
	}
	
	/**
	 * 获取重名用户树
	 * @param id
	 * @return
	 */
	public List<CheckBoxTree> getMuiltiEMPTree(String id) {
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		Map mapPlan = jdbcTemplate.queryForMap("select * from PLAN where ID='" + id + "'");
		String empnames = mapPlan.get("EMPNAME")==null?"":mapPlan.get("EMPNAME").toString();
		empnames = StringUtil.ListToStringAdd(empnames.split(","), ",");
		List listEmp = jdbcTemplate.queryForList("select * from EMPLOYEE where name in (" + empnames + ")");
		for(int i=0;i<listEmp.size();i++){
			Map mapEmp = (Map)listEmp.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapEmp.get("CODE").toString());
			String departname = findNameByCode("DEPARTMENT", mapEmp.get("DEPARTCODE").toString());
			String describe = mapEmp.get("NAME").toString() + "(" + mapEmp.get("CODE").toString() + ")--" + departname;
			tree.setText(describe);
			tree.setLeaf(true);
		
			treeList.add(tree);
		}
			
		return treeList;
	}
	
	/**
	 * 获取菜单树
	 * @param checkedMenu 多选菜单树
	 * @return
	 */
	public List<CheckBoxTree> getMultiMenuTree(String checkedMenu) {
		String[] checkedMenus = checkedMenu.split(",");
		
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		List listMenu = jdbcTemplate.queryForList("select * from MENU where MENUTYPE='2'");
		for(int i=0;i<listMenu.size();i++){
			Map mapMenu = (Map)listMenu.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapMenu.get("MENUCODE").toString());
			tree.setText(mapMenu.get("MENUNAME").toString());
			tree.setLeaf(false);
			for(String checked:checkedMenus){
				if(checked.equals(mapMenu.get("MENUCODE"))){
					tree.setChecked(true);
				}
			}
			
			List listMenu2 = jdbcTemplate.queryForList("select * from MENU where MENUTYPE='1' and PARENT='" + mapMenu.get("MENUCODE") + "'");
			List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
			for(int j=0;j<listMenu2.size();j++){
				Map mapMenu2 = (Map)listMenu2.get(j);
				CheckBoxTree leaf = new CheckBoxTree();
				leaf.setId(mapMenu2.get("MENUCODE").toString());
				leaf.setText(mapMenu2.get("MENUNAME").toString());
				leaf.setLeaf(true);
				for(String checked:checkedMenus){
					if(checked.equals(mapMenu2.get("MENUCODE"))){
						leaf.setChecked(true);
					}
				}
				leafList.add(leaf);
			}
			tree.setChildren(leafList);
			treeList.add(tree);
		}
			
		return treeList;
	}
}