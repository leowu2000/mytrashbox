package com.basesoft.util;

import java.util.List;

public class CheckBoxTree {
	
	private String id;
	private String text;
	private boolean leaf;
	private boolean checked;
	private List<CheckBoxTree> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public List<CheckBoxTree> getChildren() {
		return children;
	}
	public void setChildren(List<CheckBoxTree> children) {
		this.children = children;
	}
	
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String toJSONString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:'" + this.id + "'");
		sb.append(",");
		sb.append("text:'" + this.text + "'");
		sb.append(",");
		sb.append("leaf:" + this.leaf);
		sb.append(",");
		sb.append("checked:" + this.checked);
		sb.append(",")
	      .append("expanded:true");
		if (this.children != null && !this.leaf) {
			sb.append(",");
			sb.append("children:" + ListToJSONString(this.children));
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	private String ListToJSONString(List<CheckBoxTree> childrenList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < childrenList.size(); i++) {
			CheckBoxTree leaf = childrenList.get(i);
			if (i != 0) {
				sb.append(",");
			}
			
			sb.append("{");
			sb.append("id:'" + leaf.getId() + "'");
			sb.append(",");
			sb.append("text:'" + leaf.getText() + "'");
			sb.append(",");
			sb.append("leaf:" + leaf.getLeaf());
			sb.append(",");
			sb.append("checked:" + leaf.getChecked());
			if(leaf.getChecked()){
				sb.append(",")
				  .append("expanded:true");
			}
			if (leaf.getChildren() != null && !leaf.getLeaf()) {
				sb.append(",");
				sb.append("children:" + ListToJSONString(leaf.getChildren()));
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public String toJSONStringNoChecked() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:'" + this.id + "'");
		sb.append(",");
		sb.append("text:'" + this.text + "'");
		sb.append(",");
		sb.append("leaf:" + this.leaf);
		if (this.children != null && !this.leaf) {
			sb.append(",");
			sb.append("children:" + ListToJSONStringNoChecked(this.children));
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	private String ListToJSONStringNoChecked(List<CheckBoxTree> childrenList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < childrenList.size(); i++) {
			CheckBoxTree leaf = childrenList.get(i);
			if (i != 0) {
				sb.append(",");
			}
			
			sb.append("{");
			sb.append("id:'" + leaf.getId() + "'");
			sb.append(",");
			sb.append("text:'" + leaf.getText() + "'");
			sb.append(",");
			sb.append("leaf:" + leaf.getLeaf());
			if (leaf.getChildren() != null && !leaf.getLeaf()) {
				sb.append(",");
				sb.append("children:" + ListToJSONStringNoChecked(leaf.getChildren()));
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
}
