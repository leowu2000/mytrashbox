package com.basesoft.modules.depart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.basesoft.core.CommonDAO;
import com.basesoft.util.CheckBoxTree;

public class DepartmentDAO extends CommonDAO{

	/**
	 * 根据id查找实例
	 * @param id
	 * @return
	 */
	public Department findById(String id){
		Map mapDepart = jdbcTemplate.queryForMap("select * from DEPARTMENT where ID='" + id + "'");
		
		Department depart = new Department();
		
		depart.setId(id);
		depart.setCode(mapDepart.get("CODE").toString());
		depart.setName(mapDepart.get("NAME").toString());
		depart.setParent(mapDepart.get("PARENT").toString());
		depart.setLevel(mapDepart.get("CODE").toString());
		depart.setAllparent(mapDepart.get("ALLPARENTS").toString());
		
		return depart;
	}
	
	/**
	 * 是否有子部门
	 * @param id 部门id
	 * @return
	 */
	public boolean haveChild(String id){
		boolean haveChild = false;
		
		Map mapDepart = jdbcTemplate.queryForMap("select * from DEPARTMENT where ID='" + id + "'");
		
		//获取子部门个数
		int childCount = jdbcTemplate.queryForInt("select count(*) from DEPARTMENT where PARENT='" + mapDepart.get("CODE") + "'");
		
		if(childCount>0){
			haveChild = true;
		}
		
		return haveChild;
	}
	
	/**
	 * 获取下级单位列表
	 * @param departcode 单位编码
	 * @return
	 */
	public List<?> getChild(String departcode){
		return jdbcTemplate.queryForList("select * from DEPARTMENT where PARENT='" + departcode + "'");
	}
	
	/**
	 * 按部门寻找人员
	 * @param departcode 部门编码
	 * @return
	 */
	public List<?> findEmpsByDepart(String departcode){
		return jdbcTemplate.queryForList("select * from EMPLOYEE where DEPARTCODE='" + departcode + "'");
	}
	
	/**
	 * 生成tree  list
	 * @param state 1:只有部门;2:部门加人员
	 * @return
	 */
	public List<CheckBoxTree> getDepartEmpTree(String state) {
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
			List<CheckBoxTree> leafListEMP = getLeafEMP(mapDepart.get("CODE").toString());
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
				List<CheckBoxTree> leafListEMP2 = getLeafEMP(mapChild.get("CODE").toString());
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
						leaf2.setLeaf(true);
					}
					
					//设置部门下人员
					List<CheckBoxTree> leafListEMP3 = getLeafEMP(mapChild2.get("CODE").toString());
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
	public List<CheckBoxTree> getLeafEMP(String departcode){
		//设置节点下人员
		List listEmp = this.findEmpsByDepart(departcode);
		List<CheckBoxTree> leafList = new ArrayList<CheckBoxTree>();
		for(int j=0;j<listEmp.size();j++){
			Map mapEmp = (Map)listEmp.get(j);
			CheckBoxTree leaf = new CheckBoxTree();
			leaf.setId(mapEmp.get("CODE").toString());
			leaf.setText(mapEmp.get("NAME").toString());
			leaf.setLeaf(true);
			
			leafList.add(leaf);
		}
		
		return leafList;
	}
	
}
