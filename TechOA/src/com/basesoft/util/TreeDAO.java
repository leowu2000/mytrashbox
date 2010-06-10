package com.basesoft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeDAO extends com.basesoft.modules.depart.DepartmentDAO {
	/**
	 * 生成tree  list
	 * @param state 1:只有部门;2:部门加人员
	 * @return
	 */
	public List<CheckBoxTree> getDepartEmpTree(String state, String checkedEmp, String departcode) {
		String[] checkedEmps = checkedEmp.split(",");
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		List<?> listDepart = getSelf(departcode);
		for (int i=0;i<listDepart.size();i++) {//一级部门
			Map mapDepart = (Map)listDepart.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapDepart.get("CODE").toString());
			tree.setText(mapDepart.get("NAME").toString());
			tree.setLeaf(false);
		
			//装有下级部门及本部门人员的list
			List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
			
			//设置本部门人员
			List<CheckBoxTree> leafListEMP = getLeafEMP(mapDepart.get("CODE").toString(), checkedEmp, tree);
			//下级部门
			List<CheckBoxTree> leafListDepart = new ArrayList<CheckBoxTree>();
			
			List listDepartChild = getChild(mapDepart.get("CODE").toString());
			for(int j=0;j<listDepartChild.size();j++){//二级部门
				Map mapChild = (Map)listDepartChild.get(j);
				
				CheckBoxTree leaf = new CheckBoxTree();
				leaf.setId(mapChild.get("CODE").toString());
				leaf.setText(mapChild.get("NAME").toString());
				
				for(int l=0;l<checkedEmps.length;l++){
					if(leaf.getId().equals(checkedEmps[l])){
						leaf.setChecked(true);
						break;
					}
				}
				
				leaf.setLeaf(false);
				
				
				//装有下级部门及本部门人员的list
				List<CheckBoxTree> leafList2 = new ArrayList<CheckBoxTree>();
					
				//本部门人员
				List<CheckBoxTree> leafListEMP2 = getLeafEMP(mapChild.get("CODE").toString(), checkedEmp, leaf);
				if(leaf.getChecked()){
					tree.setChecked(true);
				}
				//下级部门
				List<CheckBoxTree> leafListDepart2 = new ArrayList<CheckBoxTree>();
				
				List listDepartChild2 = getChild(mapChild.get("CODE").toString());		
				for(int k=0;k<listDepartChild2.size();k++){//三级部门
					Map mapChild2 = (Map)listDepartChild2.get(k);
					
					CheckBoxTree leaf2 = new CheckBoxTree();
					leaf2.setId(mapChild2.get("CODE").toString());
					leaf2.setText(mapChild2.get("NAME").toString());
					if("1".equals(state)){
						leaf2.setLeaf(false);
					}else if("2".equals(state)){
						for(int m=0;m<checkedEmps.length;m++){
							if(leaf2.getId().equals(checkedEmps[m])){
								leaf2.setChecked(true);
								leaf.setChecked(true);
								break;
							}else {
								leaf2.setChecked(false);
							}
						}
						leaf2.setLeaf(true);
					}
					
					//设置部门下人员
					List<CheckBoxTree> leafListEMP3 = getLeafEMP(mapChild2.get("CODE").toString(), checkedEmp, leaf2);
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
	public List<CheckBoxTree> getLeafEMP(String departcode, String checkedEmp, CheckBoxTree tree){
		String[] checkedEmps = checkedEmp.split(",");
		//设置节点下人员
		List listEmp = this.findEmpsByDepart(departcode);
		List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
		for(int j=0;j<listEmp.size();j++){
			Map mapEmp = (Map)listEmp.get(j);
			CheckBoxTree leaf = new CheckBoxTree();
			leaf.setId(mapEmp.get("CODE").toString());
			leaf.setText(mapEmp.get("NAME").toString());
			for(int k=0;k<checkedEmps.length;k++){
				if(leaf.getId().equals(checkedEmps[k])){
					leaf.setChecked(true);
					tree.setChecked(true);
					break;
				}else {
					leaf.setChecked(false);
				}
			}
			leaf.setLeaf(true);
			
			leafList.add(leaf);
		}
		
		return leafList;
	}
	
	
	/**
	 * 获取部门工作令号树
	 * @param checkedEmp
	 * @return
	 */
	public List<CheckBoxTree> getProjectTree(String checkedPj) {
		String[] checkedPjs = checkedPj.split(",");
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		List<?> listProject = getProject();
		for (int i=0;i<listProject.size();i++) {//工作令号
			Map mapDepart = (Map)listProject.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapDepart.get("CODE").toString());
			tree.setText(mapDepart.get("NAME").toString());
			tree.setLeaf(true);
			for(int j=0;j<checkedPjs.length;j++){
				if(tree.getId().equals(checkedPjs[j])){
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
}