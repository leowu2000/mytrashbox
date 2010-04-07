/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

/**
 *
 * @author wzhang
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class HY_DBFP_JDao {

    public static List<List<Map<String, String>>> findAllList(String tbid, String colSql, String subSql, 
            int type, DBTool dbTool,int version,int dbtype,boolean isTurnChar) {
        List<List<Map<String, String>>> resultList = new ArrayList<List<Map<String, String>>>();
        JdbcTemplate conn = null;
        if (type == 0) {
            conn = dbTool.getJt1();
        } else {
            conn = dbTool.getJt2();
        }
        String sSQL="";
//        String checkSQL = "";
//
//        if(version==0){
//            if(type==0)
//                checkSQL = "select * from FIELDS WHERE tablename='" + tbid + "' and fielde='STCD'";
//            else
//                checkSQL = "select * from HY_DBFP_J WHERE TBID='" + tbid + "' and upper(FLID)='STCD'";
//        }else{
//            checkSQL = "select * from HY_DBFP_J WHERE TBID='" + tbid + "' and upper(FLID)='STCD'";
//        }

//        List rows = conn.queryForList(checkSQL);
//        if ("HY_STSC_A".trim().equalsIgnoreCase(tbid)
//                ||"STHD".trim().equalsIgnoreCase(tbid)
//                ||"HY_DATBDL_I".trim().equalsIgnoreCase(tbid)) {
            if(dbtype==2)
                  sSQL = "set rowcount 500 select " + colSql + " from " + tbid.toUpperCase();
            else
                sSQL = "select top 500 " + colSql + " from " + tbid.toUpperCase();
//        } else {
//            if (rows != null && rows.size() > 0) {
//                sSQL = "select top 500 " + colSql + " from " + tbid.toUpperCase();
//            } else {
//                sSQL = "select top 500 " + colSql + " from " + tbid.toUpperCase();
//            }
//        }
        List rows2 = conn.queryForList(sSQL);
        Iterator it = rows2.iterator();
        while (it.hasNext()) {
            Map resultmap = (Map) it.next();
            String arr[] = colSql.split(",");
            List<Map<String, String>> colList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < arr.length; i++) {
                Map<String, String> map = new HashMap<String, String>();
                if(isTurnChar){
                    try{
                    map.put(arr[i], resultmap.get(arr[i]) == null ? "" : new String(resultmap.get(arr[i]).toString().getBytes("iso-8859-1"),"GBK"));
                    }catch(Exception e){e.printStackTrace();}
                }else
                    map.put(arr[i], resultmap.get(arr[i]) == null ? "" : resultmap.get(arr[i]).toString());
                colList.add(map);
            }
            resultList.add(colList);
        }

        return resultList;
    }

    public static String colSqlFactory(String tbid, String orderby, DBTool dbTool,int type) {
        String resultStr = "";
        String codeStr = "";
        String nameStr = "";
        JdbcTemplate jt_target = dbTool.getJt2();

        List rows = jt_target.queryForList("select FLID,FLDCNNM from HY_DBFP_J where TBID=(select tbid from HY_DBTP_J WHERE TBCNNM='"
                + tbid + "') order by " + orderby);
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map userMap = (Map) it.next();
            if (codeStr.trim().equals("")) {
                codeStr = userMap.get("FLID").toString().trim();
            } else {
                codeStr += "," + userMap.get("FLID").toString().trim();
            }
            if (nameStr.trim().equals("")) {
                nameStr = userMap.get("FLDCNNM").toString().trim();
            } else {
                nameStr += "," + userMap.get("FLDCNNM").toString().trim();
            }
        }
        resultStr = codeStr + ";" + nameStr;
        return resultStr;
    }

    public static String readToExcelFactory(String tbid, String orderby, JdbcTemplate jt_target) {
        String resultStr = "";
        String codeStr = "";
        String nameStr = "";
        List rows = jt_target.queryForList("select FLID,FLDCNNM from HY_DBFP_J where TBID=(select tbid from HY_DBTP_J WHERE TBCNNM='"
                + tbid + "') order by " + orderby);
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map userMap = (Map) it.next();
            if (codeStr.trim().equals("")) {
                codeStr = userMap.get("FLID").toString().trim();
            } else {
                codeStr += "," + userMap.get("FLID").toString().trim();
            }
            if (nameStr.trim().equals("")) {
                nameStr = userMap.get("FLDCNNM").toString().trim();
            } else {
                nameStr += "," + userMap.get("FLDCNNM").toString().trim();
            }
        }
        resultStr = codeStr + ";" + nameStr;
        return resultStr;
    }

    /**
     *
     * @param tbid
     * @return
     */
    public static List<HY_DBFP_JBean> colColumnBeanList(String tbid, String isWhere,
            String orderby, DBTool dbtool,int type) {
        List<HY_DBFP_JBean> resultList = new ArrayList<HY_DBFP_JBean>();
        JdbcTemplate jt_source = dbtool.getJt2();
        List rows = jt_source.queryForList("select * from HY_DBFP_J where TBID=(select tbid from HY_DBTP_J WHERE TBCNNM='" + tbid + "') "
                + isWhere + " order by " + orderby);
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map userMap = (Map) it.next();
            resultList.add(HY_DBFP_JBean.getBeanFromMap(userMap));
        }
        return resultList;
    }

   

    public static String getStscd(String stnm, DBTool dbTool) {
        String stcd = "";
        String sql = "";
        if("".trim().equals(stnm))
            sql = "select STCD from HY_STSC_A";
        else
            sql = "select STCD from HY_STSC_A WHERE STCD IN (" + stnm + ")";
        JdbcTemplate jt_source = dbTool.getJt1();
        List rows = jt_source.queryForList(sql);
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map userMap = (Map) it.next();
            if (stcd.trim().equals("")) {
                stcd = "'" + userMap.get("STCD").toString().trim() + "'";
            } else {
                stcd += "," + "'" + userMap.get("STCD").toString().trim() + "'";
            }
        }
        return stcd;
    }
}
