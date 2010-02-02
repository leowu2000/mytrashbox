/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtool;

import java.sql.ResultSet;
import java.util.Map;

/**
 *
 * @author wzhang
 *
 */
public class HY_DBTP_JBean {
	private String TBID;
	private String TBNO;
	private String TBCNNM;
	private String TBENNM;
	public String getTBID() {
		return TBID;
	}
	public void setTBID(String tBID) {
		TBID = tBID;
	}
	public String getTBNO() {
		return TBNO;
	}
	public void setTBNO(String tBNO) {
		TBNO = tBNO;
	}
	public String getTBCNNM() {
		return TBCNNM;
	}
	public void setTBCNNM(String tBCNNM) {
		TBCNNM = tBCNNM;
	}
	public String getTBENNM() {
		return TBENNM;
	}
	public void setTBENNM(String tBENNM) {
		TBENNM = tBENNM;
	}

	public static HY_DBTP_JBean getBeanFromRs(ResultSet rs){
		HY_DBTP_JBean bean = new HY_DBTP_JBean();
		try{
			bean.setTBCNNM(rs.getString("TBCNNM")==null?"":rs.getString("TBCNNM").trim());
			bean.setTBID(rs.getString("TBID")==null?"":rs.getString("TBID").trim());
			bean.setTBENNM(rs.getString("TBENNM")==null?"":rs.getString("TBENNM").trim());
			bean.setTBNO(rs.getString("TBNO")==null?"":rs.getString("TBNO").trim());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bean;
	}

        public static HY_DBTP_JBean getBeanFromMap(Map userMap){
		HY_DBTP_JBean bean = new HY_DBTP_JBean();
		try{
			bean.setTBCNNM(userMap.get("TBCNNM")==null?"":userMap.get("TBCNNM").toString().trim());
			bean.setTBID(userMap.get("TBID")==null?"":userMap.get("TBID").toString().trim());
			bean.setTBENNM(userMap.get("TBENNM")==null?"":userMap.get("TBENNM").toString().trim());
			bean.setTBNO(userMap.get("TBNO")==null?"":userMap.get("TBNO").toString().trim());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bean;
	}

}
