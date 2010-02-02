/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

/**
 *
 * @author wzhang
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class HY_DBFP_JDao {

    public static List<List<Map<String, String>>> findAllList(String tbid, String colSql, String subSql, int type, DBTool dbTool,int version) {
        List<List<Map<String, String>>> resultList = new ArrayList<List<Map<String, String>>>();
        JdbcTemplate conn = null;
        if (type == 0) {
            conn = dbTool.getJt1();
        } else {
            conn = dbTool.getJt2();
        }
        String sSQL="";
        String checkSQL = "";
//        if(version==0){
//            checkSQL = "select * from fields WHERE tablename='" + tbid + "' and fielde='STCD'";
//        }else{
            checkSQL = "select * from HY_DBFP_J WHERE TBID='" + tbid + "' and upper(FLID)='STCD'";
//        }

        List rows = conn.queryForList(checkSQL);
        if ("HY_STSC_A".trim().equalsIgnoreCase(tbid)||"STHD".trim().equalsIgnoreCase(tbid)) {
            sSQL = "select top 500 " + colSql + " from " + tbid;
        } else {
            if (rows != null && rows.size() > 0) {
                sSQL = "select top 500 " + colSql + " from " + tbid + " WHERE STCD IN(" + subSql + ")";
            } else {
                sSQL = "select top 500 " + colSql + " from " + tbid;
            }
        }
        List rows2 = conn.queryForList(sSQL);
        Iterator it = rows2.iterator();
        while (it.hasNext()) {
            Map resultmap = (Map) it.next();
            String arr[] = colSql.split(",");
            List<Map<String, String>> colList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < arr.length; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(arr[i], resultmap.get(arr[i]) == null ? "" : resultmap.get(arr[i]).toString());
                colList.add(map);
            }
            resultList.add(colList);
        }

        return resultList;
    }

    /**
     * @param tbid
     * @return
     */
    public static List<List<Map<String, String>>> findAllList(String tbid, String colSql, String subSql, int type) {
        List<List<Map<String, String>>> resultList = new ArrayList<List<Map<String, String>>>();
        Connection conn = null;
        try {
            if (type == 0) {
                conn = ConnectionPool.getConnection();
            } else {
                conn = ConnectionPool.getTargetConnection();
            }
            Statement stmt = conn.createStatement();
            String sSQL = "";
            String checkSql = "select * from HY_DBFP_J WHERE TBID='" + tbid + "' and FLID='STCD'";
            ResultSet rss = stmt.executeQuery(checkSql);
            if ("HY_STSC_A".trim().equalsIgnoreCase(tbid)) {
                sSQL = "select " + colSql + " from " + tbid;
            } else {
                if (rss.next()) {
                    sSQL = "select " + colSql + " from " + tbid + " WHERE STCD IN(" + subSql + ")";
                } else {
                    sSQL = "select " + colSql + " from " + tbid;
                }
            }
//            System.out.println("==HY_DBFP_JDao:findAllList::" + sSQL);
            ResultSet rs = stmt.executeQuery(sSQL);
            while (rs.next()) {
                String arr[] = colSql.split(",");
                List<Map<String, String>> colList = new ArrayList<Map<String, String>>();
                for (int i = 0; i < arr.length; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(arr[i], rs.getString(arr[i]) == null ? "" : rs.getString(arr[i]));
                    colList.add(map);
                }
                resultList.add(colList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     *
     * @param tbid
     * @return
     */
    public static String colSqlFactory(String tbid, String orderby) {
        String resultStr = "";
        String codeStr = "";
        String nameStr = "";
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String sSQL = "select FLID,FLDCNNM from HY_DBFP_J where TBID=(select tbid from HY_DBTP_J WHERE TBCNNM='"
                    + tbid + "') order by " + orderby;
//                        String sSQL = "select FLID,FLDCNNM from HY_DBFP_J where TBID='"
//					+ tbid + "' order by "+orderby;
//            System.out.println("==colSqlFactory==:" + sSQL);
            ResultSet rs = stmt.executeQuery(sSQL);
            while (rs.next()) {
                if (codeStr.trim().equals("")) {
                    codeStr = rs.getString("FLID").trim();
                } else {
                    codeStr += "," + rs.getString("FLID").trim();
                }
                if (nameStr.trim().equals("")) {
                    nameStr = rs.getString("FLDCNNM").trim();
                } else {
                    nameStr += "," + rs.getString("FLDCNNM").trim();
                }
            }
            resultStr = codeStr + ";" + nameStr;
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // System.out.println("==HY_DBFP_JDao:colSqlFactory::" + resultStr);
        return resultStr;
    }

    public static String colSqlFactory(String tbid, String orderby, DBTool dbTool) {
        String resultStr = "";
        String codeStr = "";
        String nameStr = "";
        Connection conn = null;
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
            String orderby, DBTool dbtool) {
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

    public static List<HY_DBFP_JBean> colColumnBeanList(String tbid, String isWhere,
            String orderby) {
        List<HY_DBFP_JBean> resultList = new ArrayList<HY_DBFP_JBean>();
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String sSQL = "select * from HY_DBFP_J where TBID=(select tbid from HY_DBTP_J WHERE TBCNNM='" + tbid + "') "
                    + isWhere + " order by " + orderby;
            ResultSet rs = stmt.executeQuery(sSQL);
            while (rs.next()) {
                HY_DBFP_JBean bean = HY_DBFP_JBean.getBeanFromRs(rs);
                resultList.add(bean);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     *
     * @param tbid
     * @return
     */
    public static HY_DBFP_JBean getDbfpBean(String tbid, String flid) {
        HY_DBFP_JBean bean = new HY_DBFP_JBean();
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String sSQL = "select * from HY_DBFP_J where TBID='" + tbid
                    + "' and FLID='" + flid + "'";
            ResultSet rs = stmt.executeQuery(sSQL);
            if (rs.next()) {
                bean = HY_DBFP_JBean.getBeanFromRs(rs);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return bean;
    }

    /**
     *
     * @param tbid
     * @return
     */
    public static String getPKName(String tbid) {
        String pkStr = "";
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String sSQL = "select FLID from HY_DBFP_J where TBID='" + tbid
                    + "' and PKAT is not null";
            ResultSet rs = stmt.executeQuery(sSQL);
            while (rs.next()) {
                if (pkStr.trim().equals("")) {
                    pkStr = rs.getString("FLID").trim();
                } else {
                    pkStr += "," + rs.getString("FLID").trim();
                }
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return pkStr;
    }

    public static String getPKName(String tbid, String sept) {
        String pkStr = "";
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String sSQL = "select FLID from HY_DBFP_J where TBID='" + tbid
                    + "' and PKAT is not null";
            ResultSet rs = stmt.executeQuery(sSQL);
            while (rs.next()) {
                if (pkStr.trim().equals("")) {
                    pkStr = rs.getString("FLID").trim();
                } else {
                    pkStr += sept + rs.getString("FLID").trim();
                }
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return pkStr;
    }

    /**
     *
     * @param tbid
     * @param pkname
     * @param pkvalues
     */
    public static void deleteRecordid(String tbid, String pkname,
            String[] pkvalues) {
        String isWhere = "";

        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String pName[] = pkname.split(",");
            for (int k = 0; k < pkvalues.length; k++) {
                String pValue[] = pkvalues[k].split(";");
                for (int i = 0; i < pName.length; i++) {
                    if (isWhere.trim().equals("")) {
                        isWhere = " where "
                                + pName[i]
                                + "='"
                                + new String(pValue[i].getBytes("ISO-8859-1"),
                                "UTF-8") + "'";
                    } else {
                        isWhere += " and "
                                + pName[i]
                                + "='"
                                + new String(pValue[i].getBytes("ISO-8859-1"),
                                "UTF-8") + "'";
                    }
                }
                String sSQL = "delete from " + tbid.toUpperCase() + " "
                        + isWhere;
                stmt.executeUpdate(sSQL);
                isWhere = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean checkTabType(String tbid, String type) {
        boolean flag = false;
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from HY_DBFP_J where TBID='" + tbid
                    + "' and (FLDTPL like '%" + type.toUpperCase()
                    + "%' or  FLDTPL like '%" + type.toLowerCase() + "%')";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                flag = true;
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }

    public static void addRecord(String addSql) {
        Connection conn = null;
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(addSql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String checkColType(String type) {

        String result = "0";
        if (type != null && type.length() > 0) {
            result = type.substring(0, 1).toUpperCase();
        }
        return result;
    }

    public static String checkColTypeFromId(String flid, String tbid) {

        String result = "0";
        String type = "";
        Connection conn = null;
        String sSQL = "select FLDTPL FROM HY_DBFP_J WHERE TBID='" + tbid + "' AND FLID='" + flid + "'";
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sSQL);
            if (rs.next()) {
                type = rs.getString("FLDTPL") == null ? "" : rs.getString("FLDTPL").trim();
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (type != null && type.length() > 0) {
            result = type.substring(0, 1).toUpperCase();
        }
        return result;
    }

    public static String getColLenth(String type) {
        String result = "";
        if ("VCHAR()".trim().equalsIgnoreCase(type)) {
            result = "0";
            return result;
        } else {

            char[] chs = type.toCharArray();
            long number = -1;
            boolean isNumber = false;
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] >= 48 && chs[i] <= 57) {
                    if (number < 0) {
                        number = 0;
                    }
                    number = number * 10 + chs[i] - 0x30;
                    isNumber = true;
                } else {
                    if (number >= 0 && isNumber) {
                        if (result.trim().equals("")) {
                            result = String.valueOf(number);
                        } else {
                            result += "," + String.valueOf(number);
                        }
                        number = -1;
                        isNumber = false;
                    }
                }

            }
            if (number >= 0) {
                if (result.trim().equals("")) {
                    result = String.valueOf(number);
                } else {
                    result += "," + String.valueOf(number);
                }
            }
        }
        return result;
    }

    public static List<HY_DBFP_JBean> getTypesFLID(String tbid, String type) {
        List<HY_DBFP_JBean> colList = new ArrayList<HY_DBFP_JBean>();
        Connection conn = null;
        String sSQL = "";
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            if (type != null && type.length() > 0) {
                sSQL = "SELECT * FROM HY_DBFP_J WHERE TBID='" + tbid
                        + "' AND FLDTPL LIKE '%" + type.toUpperCase()
                        + "%' OR FLDTPL LIKE '%" + type.toLowerCase() + "%'";
            } else {
                sSQL = "SELECT * FROM HY_DBFP_J WHERE TBID='" + tbid + "'";
            }
            ResultSet rs = stmt.executeQuery(sSQL);
            while (rs.next()) {
                HY_DBFP_JBean bean = HY_DBFP_JBean.getBeanFromRs(rs);
                colList.add(bean);
            }
            stmt.close();
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }
        return colList;
    }

    /**
     * 构造数据表信息
     * @param IDs,tbid字符串
     * @author wzhang
     * @version
     */
    public static Map<String, List<List<HY_DBFP_JBean>>> getTableStructure(String IDs) {
        Connection conn = null;
        IDs = IDs == null ? "" : IDs;
        String sSQL = "";
        Map<String, List<List<HY_DBFP_JBean>>> map = new HashMap<String, List<List<HY_DBFP_JBean>>>();
        try {
            conn = ConnectionPool.getTargetConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String tbidArr[] = IDs.split(",");
            if (tbidArr != null && tbidArr.length > 0) {

                List<List<HY_DBFP_JBean>> tableList = new ArrayList<List<HY_DBFP_JBean>>();
                for (int i = 0; i < tbidArr.length; i++) {
                    List<HY_DBFP_JBean> colList = new ArrayList<HY_DBFP_JBean>();
                    sSQL = "select A.TBID,A.FLID,A.FLDCNNM,A.FLDENNM,substr(A.FLDTPL,0,1) FLDTP,A.FLDTPL,A.NLATT,A.UNNM,A.VLRG,A.PKAT,B.TBCNNM "
                            + "from HY_DBFP_J A,HY_DBTP_J B where A.TBID ='" + tbidArr[i] + "'  AND A.TBID=B.TBID ";
//					System.out.println(sSQL);
                    rs = stmt.executeQuery(sSQL);
                    if (rs.next()) {
                        HY_DBFP_JBean bean = new HY_DBFP_JBean();
                        bean.setFLDCNNM(rs.getString("FLDCNNM") == null ? "" : rs.getString("FLDCNNM").trim());
                        bean.setFLDENNM(rs.getString("FLDENNM") == null ? "" : rs.getString("FLDENNM").trim());
                        bean.setFLDTPL(rs.getString("FLDTPL") == null ? "" : rs.getString("FLDTPL").trim());
                        bean.setFLID(rs.getString("FLID") == null ? "" : rs.getString("FLID").trim());
                        bean.setNLATT(rs.getString("NLATT") == null ? "" : rs.getString("NLATT").trim());
                        bean.setPKAT(rs.getString("PKAT") == null ? "" : rs.getString("PKAT").trim());
                        bean.setTBID(rs.getString("TBID") == null ? "" : rs.getString("TBID").trim());
                        bean.setFLDTP(rs.getString("FLDTP") == null ? "" : rs.getString("FLDTP").trim());
                        bean.setTBCNNM(rs.getString("TBCNNM") == null ? "" : rs.getString("TBCNNM").trim());
                        bean.setUNNM(rs.getString("UNNM") == null ? "" : rs.getString("UNNM").trim());
                        bean.setVLRG(rs.getString("VLRG") == null ? "" : rs.getString("VLRG").trim());
                        colList.add(bean);
                        while (rs.next()) {
                            HY_DBFP_JBean bean2 = new HY_DBFP_JBean();
                            bean2.setFLDCNNM(rs.getString("FLDCNNM") == null ? "" : rs.getString("FLDCNNM").trim());
                            bean2.setFLDENNM(rs.getString("FLDENNM") == null ? "" : rs.getString("FLDENNM").trim());
                            bean2.setFLDTPL(rs.getString("FLDTPL") == null ? "" : rs.getString("FLDTPL").trim());
                            bean2.setFLID(rs.getString("FLID") == null ? "" : rs.getString("FLID").trim());
                            bean2.setNLATT(rs.getString("NLATT") == null ? "" : rs.getString("NLATT").trim());
                            bean2.setPKAT(rs.getString("PKAT") == null ? "" : rs.getString("PKAT").trim());
                            bean2.setTBID(rs.getString("TBID") == null ? "" : rs.getString("TBID").trim());
                            bean2.setFLDTP(rs.getString("FLDTP") == null ? "" : rs.getString("FLDTP").trim());
                            bean2.setTBCNNM(rs.getString("TBCNNM") == null ? "" : rs.getString("TBCNNM").trim());
                            bean2.setUNNM(rs.getString("UNNM") == null ? "" : rs.getString("UNNM").trim());
                            bean2.setVLRG(rs.getString("VLRG") == null ? "" : rs.getString("VLRG").trim());
                            colList.add(bean2);
                        }
                        tableList.add(colList);
                        map.put(tbidArr[i], tableList);
                    }

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 从excel导入的数据插入数据库，返回未能顺利入库的list
     * @param insertList
     * insertList[0]:表名称_TBID
     * insertList[1]:表字段名称_FLID
     * insertList[2]:表字段类型_FLTP
     * insertList[4]:表字段类型和长度_FLTPL
     * insertList[5]:表字段中文名称_FLCNNM
     * insertList[6]:实际数据List<String[]>
     * @return List<String[]>
     */
    public static List<String[]> insertDBFromExcel(List<?> insertList, String realPath, String savePath) {
        List<String[]> errorList = new ArrayList<String[]>();
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();

            if (insertList != null && insertList.size() > 0) {
                String PKname = "";
                Statement stmt = conn.createStatement();
                String sSQL = "";
                String name[] = (String[]) insertList.get(0);
                if (name.length > 0) {
                    PKname = name[1];
                }
                sSQL += "INSERT INTO " + name[0].trim();

                String flid[] = (String[]) insertList.get(1);
                sSQL += " " + Arrays.toString(flid);

                String fltp[] = (String[]) insertList.get(2);//字段类型

                String fltpl[] = (String[]) insertList.get(3);//字段类型和长度

                String cnnm[] = (String[]) insertList.get(4);//字段中文描述

                List<?> valueList = (List<?>) insertList.get(5);
                Map<String, Object> errorMap = new HashMap<String, Object>();
                List<Object> errVal = new ArrayList<Object>();
                List errDesc = new ArrayList();
                boolean isCreate = true;
                for (int i = 0; i < valueList.size(); i++) {
                    String insertSql = "";


                    String value[] = (String[]) valueList.get(i);
                    insertSql = sSQL + " VALUES " + Arrays.toString(value);

                    insertSql = insertSql.replaceAll("\\[", "(");
                    insertSql = insertSql.replaceAll("\\]", ")");
                    stmt.addBatch(insertSql);
//					System.out.println(insertSql);
//					List<?> resuList = checkValuesTPL(PKname,flid,fltp,fltpl,cnnm,value);
//					System.out.println(resuList.size());
                    //错误处理
//					if(resuList!=null && resuList.size()>0){
//						if(isCreate){
//							errorMap.put("tbid", name[0]);
//							errorMap.put("flid", flid);
//							errorMap.put("fltp", fltp);
//							errorMap.put("fltpl", fltpl);
//							errorMap.put("cnnm", cnnm);
//							isCreate = false;
//						}
//						errVal.add(value);
//						errDesc.add(resuList);
//
//					}else{
//						stmt.addBatch(insertSql);
//					}
                }
                //将错误的数据写入excel errVal errDesc
//				errorMap.put("errVal", errVal);
//				errorMap.put("errDesc", errDesc);
//				ExcelService.writeErrorToXLS(realPath+"/dbfiles/error/",errorMap);
                //将正确数据入库
                stmt.executeBatch();
                stmt.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }
        return errorList;
    }

    /**
     * 检验导入数据是否符合规范，
     * @param flid[]
     * @param fltp[]
     * @param fltpl[]
     * @param value[]
     * @param cnnm[]
     */
    public static List<Map<String, String>> checkValuesTPL(String pkname, String[] flid, String fltp[], String[] fltpl, String cnnm[], String[] value) {
        List<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();
        //检验主键是否为空
        if (pkname != null && pkname.length() > 0) {
            String pks[] = pkname.split(";");
            for (int i = 0; i < pks.length; i++) {
                for (int k = 0; k < flid.length; k++) {

                    if (pks[i].trim().equalsIgnoreCase(flid[k])) {
                        value[k] = value[k].replaceAll("'", "").trim();
                    }
                    if (value[k] == null || value[k].trim().equalsIgnoreCase("null") || value[k].trim().equals("")) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("index", flid[k]);
                        map.put("desc", "主键--" + cnnm[k] + "--为空");
                        arrayList.add(map);
                        return arrayList;
                    }
                }
            }
        }
        //检验字段类型和长度是否符合数据规范
        for (int i = 0; i < flid.length; i++) {
            value[i] = value[i].replaceAll("'", "").trim();
            String len = HY_DBFP_JDao.getColLenth(fltpl[i]);
            if ("C".trim().equalsIgnoreCase(fltp[i].trim().toUpperCase())) {
                if (value[i] != null) {
//					System.out.println(len+"=="+value[i]+"=="+cnnm[i]+"=="+flid[i]);
                    if (value[i].length() > new Integer(len).intValue()) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("index", flid[i]);
                        map.put("desc", "字段--" + cnnm[i] + "--长度超过限制");
                        arrayList.add(map);
                        return arrayList;
                    }
                }
            }
            if ("T".trim().equalsIgnoreCase(fltp[i].trim().toUpperCase())) {
                if (value[i] != null) {
                    if (value[i].indexOf("-") < 0) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("index", flid[i]);
                        map.put("desc", "字段--" + cnnm[i] + "--日期格式错误");
                        arrayList.add(map);
                        return arrayList;
                    }
                }
            }
            if ("N".trim().equalsIgnoreCase(fltp[i].trim().toUpperCase())) {
                try {
                    double dd = new Double(value[i]).doubleValue();
                } catch (Exception ex) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("index", flid[i]);
                    map.put("desc", "字段--" + cnnm[i] + "--应该是数值型数据");
                    arrayList.add(map);
                    return arrayList;
                }
                String lens[] = len.split(",");
                if (lens.length > 0) {//规范中允许有小数
                    if (value[i].length() > new Integer(lens[0]).intValue()) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("index", flid[i]);
                        map.put("desc", "字段--" + cnnm[i] + "--长度超过限制");
                        arrayList.add(map);
                        return arrayList;
                    }
                    if (value[i].lastIndexOf(".") > 0) {//输入中有小数
                        //取小数
                        int poi = value[i].lastIndexOf(".");
                        String temp = value[i].substring(poi, value[i].length());
                        if (temp.length() > new Integer(lens[1]).intValue()) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("index", flid[i]);
                            map.put("desc", "字段--" + cnnm[i] + "--小数位数过长");
                            arrayList.add(map);
                            return arrayList;
                        }
                    }
                }
            }

        }
        return arrayList;
    }

    public static int getReadColNum(String tbid) {
        int num = 0;
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            String sSQL = "select count(*) as num from HY_DBFP_J WHERE TBID='" + tbid + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sSQL);
            if (rs.next()) {
                num = rs.getInt("num");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }
        return num;
    }

    public static String getWriteTbs(String tbid) {
        String realIds = "";
        String tbid1 = "'" + tbid + "'";
        String tbid2 = tbid1.replaceAll(",", "','");
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            String sSQL = "select distinct(tbid) from HY_DBFP_J WHERE TBID in(" + tbid2 + ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sSQL);

            while (rs.next()) {
                if (realIds.trim().equals("")) {
                    realIds = rs.getString("tbid").trim();
                } else {
                    realIds += "," + rs.getString("tbid").trim();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }
        return realIds;
    }

    public static String getStscd(String stnm) {
        String stcd = "";
        Connection conn = null;
        String sql = "select STCD from HY_STSC_A WHERE STNM IN (" + stnm + ")";
//        System.out.println(sql);
        try {
            conn = ConnectionPool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (stcd.trim().equals("")) {
                    stcd = "'" + rs.getString("STCD").trim() + "'";
                } else {
                    stcd += "," + "'" + rs.getString("STCD").trim() + "'";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ConnectionPool.freeConnection(conn);
            } catch (Exception fe) {
                fe.printStackTrace();
            }
        }
        return stcd;
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
