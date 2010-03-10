package dbtool;

import java.sql.ResultSet;

/**
 * 测站一览表
 * 此表单独处理，以提高效率
 * @author wzhang
 *
 */
public class HY_STSC_ABean {

	private String STCD;	//站码
	private String STNM;	//站名
	private String STCT;	//站别
	private String BSHNCD;	//流域名称
	private String HNNM;	//水系名称
	private String RVNM;	//河流名称
	private String OBITMCD;	//实测项目码
	private String ADDVCD;	//行政区划码
	private String WRRGCD;	//水资源分区码
	private String ESSTYR;	//设站年份
	private String ESSTMTH;	//设站月份
	private String WDSTYR;	//撤站年份
	private String WDSTMTH;	//撤站月份
	private String DRAR;	//集水面积
	private String FLTO;	//流入何处
	private String DSTRVM;	//至河口距离
	private String FDTMNM;	//基准基面名称
	private String ADMAG;	//领导机关
	private String ADMNST;	//管理单位
	private String STLC;	//站址
	private String LGTD;	//东经
	private String LTTD;	//北纬
	private String STGRD;	//测站等级
	private String FRGRD;	//报汛站等级
	private String NT;	//备注
	public String getSTCD() {
		return STCD;
	}
	public void setSTCD(String sTCD) {
		STCD = sTCD;
	}
	public String getSTNM() {
		return STNM;
	}
	public void setSTNM(String sTNM) {
		STNM = sTNM;
	}
	public String getSTCT() {
		return STCT;
	}
	public void setSTCT(String sTCT) {
		STCT = sTCT;
	}
	public String getBSHNCD() {
		return BSHNCD;
	}
	public void setBSHNCD(String bSHNCD) {
		BSHNCD = bSHNCD;
	}
	public String getHNNM() {
		return HNNM;
	}
	public void setHNNM(String hNNM) {
		HNNM = hNNM;
	}
	public String getRVNM() {
		return RVNM;
	}
	public void setRVNM(String rVNM) {
		RVNM = rVNM;
	}
	public String getOBITMCD() {
		return OBITMCD;
	}
	public void setOBITMCD(String oBITMCD) {
		OBITMCD = oBITMCD;
	}
	public String getADDVCD() {
		return ADDVCD;
	}
	public void setADDVCD(String aDDVCD) {
		ADDVCD = aDDVCD;
	}
	public String getWRRGCD() {
		return WRRGCD;
	}
	public void setWRRGCD(String wRRGCD) {
		WRRGCD = wRRGCD;
	}
	public String getESSTYR() {
		return ESSTYR;
	}
	public void setESSTYR(String eSSTYR) {
		ESSTYR = eSSTYR;
	}
	public String getESSTMTH() {
		return ESSTMTH;
	}
	public void setESSTMTH(String eSSTMTH) {
		ESSTMTH = eSSTMTH;
	}
	public String getWDSTYR() {
		return WDSTYR;
	}
	public void setWDSTYR(String wDSTYR) {
		WDSTYR = wDSTYR;
	}
	public String getWDSTMTH() {
		return WDSTMTH;
	}
	public void setWDSTMTH(String wDSTMTH) {
		WDSTMTH = wDSTMTH;
	}
	public String getDRAR() {
		return DRAR;
	}
	public void setDRAR(String dRAR) {
		DRAR = dRAR;
	}
	public String getFLTO() {
		return FLTO;
	}
	public void setFLTO(String fLTO) {
		FLTO = fLTO;
	}
	public String getDSTRVM() {
		return DSTRVM;
	}
	public void setDSTRVM(String dSTRVM) {
		DSTRVM = dSTRVM;
	}
	public String getFDTMNM() {
		return FDTMNM;
	}
	public void setFDTMNM(String fDTMNM) {
		FDTMNM = fDTMNM;
	}
	public String getADMAG() {
		return ADMAG;
	}
	public void setADMAG(String aDMAG) {
		ADMAG = aDMAG;
	}
	public String getADMNST() {
		return ADMNST;
	}
	public void setADMNST(String aDMNST) {
		ADMNST = aDMNST;
	}
	public String getSTLC() {
		return STLC;
	}
	public void setSTLC(String sTLC) {
		STLC = sTLC;
	}
	public String getLGTD() {
		return LGTD;
	}
	public void setLGTD(String lGTD) {
		LGTD = lGTD;
	}
	public String getLTTD() {
		return LTTD;
	}
	public void setLTTD(String lTTD) {
		LTTD = lTTD;
	}
	public String getSTGRD() {
		return STGRD;
	}
	public void setSTGRD(String sTGRD) {
		STGRD = sTGRD;
	}
	public String getFRGRD() {
		return FRGRD;
	}
	public void setFRGRD(String fRGRD) {
		FRGRD = fRGRD;
	}
	public String getNT() {
		return NT;
	}
	public void setNT(String nT) {
		NT = nT;
	}

	public static HY_STSC_ABean getBeanFromRs(ResultSet rs){
		HY_STSC_ABean bean =  new HY_STSC_ABean();
		try{
			bean.setADDVCD(rs.getString("ADDVCD")==null?"":rs.getString("ADDVCD").trim());//
			bean.setADMAG(rs.getString("ADMAG")==null?"":rs.getString("ADMAG").trim());//
			bean.setADMNST(rs.getString("ADMNST")==null?"":rs.getString("ADMNST").trim());//
			bean.setBSHNCD(rs.getString("BSHNCD")==null?"":rs.getString("BSHNCD").trim());//
			bean.setDRAR(rs.getString("DRAR")==null?"":rs.getString("DRAR").trim());//
			bean.setDSTRVM(rs.getString("DSTRVM")==null?"":rs.getString("DSTRVM").trim());//
			bean.setESSTMTH(rs.getString("ESSTMTH")==null?"":rs.getString("ESSTMTH").trim());//
			bean.setESSTYR(rs.getString("ESSTYR")==null?"":rs.getString("ESSTYR").trim());//
			bean.setFDTMNM(rs.getString("FDTMNM")==null?"":rs.getString("FDTMNM").trim());//
			bean.setFLTO(rs.getString("FLTO")==null?"":rs.getString("FLTO").trim());//
			bean.setFRGRD(rs.getString("FRGRD")==null?"":rs.getString("FRGRD").trim());//
			bean.setHNNM(rs.getString("HNNM")==null?"":rs.getString("HNNM").trim());//
			bean.setLGTD(rs.getString("LGTD")==null?"":rs.getString("LGTD").trim());//
			bean.setLTTD(rs.getString("LTTD")==null?"":rs.getString("LTTD").trim());//
			bean.setNT(rs.getString("NT")==null?"":rs.getString("NT").trim());//
			bean.setOBITMCD(rs.getString("OBITMCD")==null?"":rs.getString("OBITMCD").trim());//
			bean.setRVNM(rs.getString("RVNM")==null?"":rs.getString("RVNM").trim());//
			bean.setSTCD(rs.getString("STCD")==null?"":rs.getString("STCD").trim());//
			bean.setSTCT(rs.getString("STCT")==null?"":rs.getString("STCT").trim());//
			bean.setSTGRD(rs.getString("STGRD")==null?"":rs.getString("STGRD").trim());//
			bean.setSTLC(rs.getString("STLC")==null?"":rs.getString("STLC").trim());//
			bean.setSTNM(rs.getString("STNM")==null?"":rs.getString("STNM").trim());//
			bean.setWDSTMTH(rs.getString("WDSTMTH")==null?"":rs.getString("WDSTMTH").trim());//
			bean.setWDSTYR(rs.getString("WDSTYR")==null?"":rs.getString("WDSTYR").trim());//
			bean.setWRRGCD(rs.getString("WRRGCD")==null?"":rs.getString("WRRGCD").trim());//
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bean;
	}

}
