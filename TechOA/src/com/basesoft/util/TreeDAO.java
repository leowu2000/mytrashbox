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
	public List<CheckBoxTree> getDepartEmpTree(String state, String checkedEmp) {
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		List<?> listDepart = getChild("0");
		for (int i=0;i<listDepart.size();i++) {//一级部门
			Map mapDepart = (Map)listDepart.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapDepart.get("CODE").toString());
			tree.setText(mapDepart.get("NAME").toString());
			tree.setLeaf(false);
		
			//装有下级部门及本部门人员的list
			List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
			
			//设置本部门人员
			List<CheckBoxTree> leafListEMP = getLeafEMP(mapDepart.get("CODE").toString(), checkedEmp);
			//下级部门
			List<CheckBoxTree> leafListDepart = new ArrayList<CheckBoxTree>();
			
			List listDepartChild = getChild(mapDepart.get("CODE").toString());
			for(int j=0;j<listDepartChild.size();j++){//二级部门
				Map mapChild = (Map)listDepartChild.get(j);
				
				CheckBoxTree leaf = new CheckBoxTree();
				leaf.setId(mapChild.get("CODE").toString());
				leaf.setText(mapChild.get("NAME").toString());
				leaf.setLeaf(false);
				
				
				leafListDepart.add(leaf);
				
				//装有下级部门及本部门人员的list
				List<CheckBoxTree> leafList2 = new ArrayList<CheckBoxTree>();
					
				//本部门人员
				List<CheckBoxTree> leafListEMP2 = getLeafEMP(mapChild.get("CODE").toString(), checkedEmp);
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
						if (!"".equals(checkedEmp) && checkedEmp.indexOf(leaf2.getId()) != -1) {
							leaf2.setChecked(true);
						} else {
							leaf2.setChecked(false);
						}
						leaf2.setLeaf(true);
					}
					
					//设置部门下人员
					List<CheckBoxTree> leafListEMP3 = getLeafEMP(mapChild2.get("CODE").toString(), checkedEmp);
					leaf2.setChildren(leafListEMP3);
					
					leafListDepart2.add(leaf2);
					
				}
				
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
	public List<CheckBoxTree> getLeafEMP(String departcode, String checkedEmp){
		//设置节点下人员
		List listEmp = this.findEmpsByDepart(departcode);
		List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
		for(int j=0;j<listEmp.size();j++){
			Map mapEmp = (Map)listEmp.get(j);
			CheckBoxTree leaf = new CheckBoxTree();
			leaf.setId(mapEmp.get("CODE").toString());
			leaf.setText(mapEmp.get("NAME").toString());
			if (!"".equals(checkedEmp) && checkedEmp.indexOf(leaf.getId()) != -1) {
				leaf.setChecked(true);
			} else {
				leaf.setChecked(false);
			}
			leaf.setLeaf(true);
			
			leafList.add(leaf);
		}
		
		return leafList;
	}
	
	/**
	 * 获取多选工作令号树
	 * @param checkedEmp
	 * @return
	 */
	public List<CheckBoxTree> getProjectTree(String checkedPj) {
		List<CheckBoxTree> treeList = new ArrayList<CheckBoxTree>();
		
		List<?> listProject = getProject();
		for (int i=0;i<listProject.size();i++) {//工作令号
			Map mapDepart = (Map)listProject.get(i);
			
			CheckBoxTree tree = new CheckBoxTree();
			tree.setId(mapDepart.get("CODE").toString());
			tree.setText(mapDepart.get("NAME").toString());
			tree.setLeaf(true);
			if (!"".equals(checkedPj) && checkedPj.indexOf(tree.getId()) != -1) {
				tree.setChecked(true);
			} else {
				tree.setChecked(false);
			}
		
			treeList.add(tree);
			
		}
		return treeList;
	}
}
