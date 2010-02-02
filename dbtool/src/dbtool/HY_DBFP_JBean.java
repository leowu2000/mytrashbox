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
 */
public class HY_DBFP_JBean {
	private String TBID;
	private String  FLID;
	private String FLDCNNM;
	private String  FLDENNM;
	private String FLDTPL;
	private String NLATT;
	private String UNNM;
	private String VLRG;
	private String PKAT;
	private String TBCNNM;
	private String FLDTP;

	public String getFLDTP() {
		return FLDTP;
	}
	public void setFLDTP(String fLDTP) {
		FLDTP = fLDTP;
	}
	public String getTBCNNM() {
		return TBCNNM;
	}
	public void setTBCNNM(String tBCNNM) {
		TBCNNM = tBCNNM;
	}
	public String getTBID() {
		return TBID;
	}
	public void setTBID(String tBID) {
		TBID = tBID;
	}
	public String getFLID() {
		return FLID;
	}
	public void setFLID(String fLID) {
		FLID = fLID;
	}
	public String getFLDCNNM() {
		return FLDCNNM;
	}
	public void setFLDCNNM(String fLDCNNM) {
		FLDCNNM = fLDCNNM;
	}
	public String getFLDENNM() {
		return FLDENNM;
	}
	public void setFLDENNM(String fLDENNM) {
		FLDENNM = fLDENNM;
	}
	public String getFLDTPL() {
		return FLDTPL;
	}
	public void setFLDTPL(String fLDTPL) {
		FLDTPL = fLDTPL;
	}
	public String getNLATT() {
		return NLATT;
	}
	public void setNLATT(String nLATT) {
		NLATT = nLATT;
	}
	public String getUNNM() {
		return UNNM;
	}
	public void setUNNM(String uNNM) {
		UNNM = uNNM;
	}
	public String getVLRG() {
		return VLRG;
	}
	public void setVLRG(String vLRG) {
		VLRG = vLRG;
	}
	public String getPKAT() {
		return PKAT;
	}
	public void setPKAT(String pKAT) {
		PKAT = pKAT;
	}


	public static HY_DBFP_JBean getBeanFromRs(ResultSet rs){
		HY_DBFP_JBean bean = new HY_DBFP_JBean();
		try{
			bean.setFLDCNNM(rs.getString("FLDCNNM")==null?"":rs.getString("FLDCNNM").trim());
			bean.setFLDENNM(rs.getString("FLDENNM")==null?"":rs.getString("FLDENNM").trim());
			bean.setFLDTPL(rs.getString("FLDTPL")==null?"":rs.getString("FLDTPL").trim());
			bean.setFLID(rs.getString("FLID")==null?"":rs.getString("FLID").trim());
			bean.setNLATT(rs.getString("NLATT")==null?"":rs.getString("NLATT").trim());
			bean.setPKAT(rs.getString("PKAT")==null?"":rs.getString("PKAT").trim());
			bean.setTBID(rs.getString("TBID")==null?"":rs.getString("TBID").trim());
			bean.setUNNM(rs.getString("UNNM")==null?"":rs.getString("UNNM").trim());
			bean.setVLRG(rs.getString("VLRG")==null?"":rs.getString("VLRG").trim());
		}catch(Exception ex){ex.printStackTrace();}
		return bean;
	}

        public static HY_DBFP_JBean getBeanFromMap(Map userMap){
		HY_DBFP_JBean bean = new HY_DBFP_JBean();
		try{
			bean.setFLDCNNM(userMap.get("FLDCNNM")==null?"":userMap.get("FLDCNNM").toString().trim());
			bean.setFLDENNM(userMap.get("FLDENNM")==null?"":userMap.get("FLDENNM").toString().trim());
			bean.setFLDTPL(userMap.get("FLDTPL")==null?"":userMap.get("FLDTPL").toString().trim());
			bean.setFLID(userMap.get("FLID")==null?"":userMap.get("FLID").toString().trim());
			bean.setNLATT(userMap.get("NLATT")==null?"":userMap.get("NLATT").toString().trim());
			bean.setPKAT(userMap.get("PKAT")==null?"":userMap.get("PKAT").toString().trim());
			bean.setTBID(userMap.get("TBID")==null?"":userMap.get("TBID").toString().trim());
			bean.setUNNM(userMap.get("UNNM")==null?"":userMap.get("UNNM").toString().trim());
			bean.setVLRG(userMap.get("VLRG")==null?"":userMap.get("VLRG").toString().trim());
		}catch(Exception ex){ex.printStackTrace();}
		return bean;
	}


}
