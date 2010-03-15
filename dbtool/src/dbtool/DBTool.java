package dbtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class DBTool {

    Connection cnSource, cnTarget;
    JdbcTemplate jt1, jt2;
//    PageTemplate jt1, jt2;
    String saveDirs="";
    DefaultListModel expSuccessModel = new DefaultListModel();
    String errorTab = "";
    Map dataIndexMap = new HashMap();
    public DBTool(String configFile) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.config(p);
    }

    public DBTool(String sDriver, String sUrl, String sUser, String sPass, String tDataDir) {
        Properties p = new Properties();
        p.setProperty("source.datasource.driverClass", sDriver);
        p.setProperty("target.datasource.driverClass", "org.h2.Driver");
        p.setProperty("source.datasource.jdbcUrl", sUrl);
        p.setProperty("source.datasource.username", sUser);
        p.setProperty("source.datasource.password", sPass);
        p.setProperty("target.datasource.dataDir", tDataDir);
        p.setProperty("target.datasource.jdbcUrl", "jdbc:h2:file:" + tDataDir + "/app");
        p.setProperty("target.datasource.username", "sa");
        p.setProperty("target.datasource.password", "");
        this.config(p);
    }

    public boolean copyTable(final String table, String stsc, String saveDir, JList logList, int expType, int version) {

        String searChsql = "";
        if (isHaveStcdCol(table)) {
            searChsql = "select * from " + table.toUpperCase() + makeStcdSqlCol(stsc);
        } else {
            searChsql = "select * from " + table.toUpperCase();
        }
        if(expType==1||expType==2)
            ((DefaultListModel) (logList.getModel())).addElement("正在分析表：【" + getTabCnnm(jt2, table) + "】_" + table + "的数据 ......");
        outputError(table,"copyTable=searChsql:",searChsql);
        try {
            if (expType != 0) {
                clearTable(true, table.toUpperCase());
            }
            String result[] = makeFiledsAndParamets(table);
            if(result==null){
                outputError(table, "=copyTable=",getTabCnnm(jt2, table)+"数据表数据为空系统跳过");
            }
            else{
                if(expType==0)
                    ((DefaultListModel) (logList.getModel())).addElement("正在分析表：【" + getTabCnnm(jt2, table) + "】_" + table + "的数据 ......");
                final String fields = result[0];
                final String params = result[1];
                jt1.query(searChsql, new RowMapper() {
                    public Object mapRow(final ResultSet rs, int rowNum) throws SQLException {
                        ResultSetMetaData meta = rs.getMetaData();
                        final int cols = meta.getColumnCount();
                        String sql = "insert into " + table.toUpperCase() + " (" + fields.substring(0, fields.length() - 1) + ") values (" + params.substring(0, params.length() - 1) + ")";
                        jt2.execute(sql, new PreparedStatementCallback() {
                            public Object doInPreparedStatement(PreparedStatement pStat) throws SQLException,
                                    DataAccessException {
                                for (int i = 1; i <= cols; i++) {
                                    Object o = rs.getObject(i);
                                    if (o instanceof Blob) {
                                        pStat.setObject(i, ((Blob) o).getBytes(1, 100000000));//100mb
                                    } else {
                                        pStat.setObject(i, rs.getObject(i));
                                    }
                                }
                                pStat.execute();
                                return null;
                            }
                        });
                        return null;
                    }
                });

                ((DefaultListModel) (logList.getModel())).addElement("         ↓正在导出【" + getTabCnnm(jt2, table) + "】的数据，请等待...");
                if (expType == 2) {
                    createExcelTable(table, stsc, saveDir, logList, expType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            expSuccessModel.removeElement(getTabCnnm(jt2, table));
            if ("".trim().equals(errorTab)) {
                errorTab = getTabCnnm(jt2, table);
            } else {
                errorTab += "," + getTabCnnm(jt2, table);
            }
            ((DefaultListModel) (logList.getModel())).addElement("         ※系统检测到表结构错误，无法执行【" + getTabCnnm(jt2, table) + "】的入库操作，请修改导出方式为【只导出文本文件】※");
            outputError(table, "=copyTable=",e.getMessage());
            return false;
        }
        return true;
    }

    public boolean isHaveStcdCol(String table) {
        boolean flag = false;
        if ("HY_STSC_A".trim().equalsIgnoreCase(table) || "HY_DATBDL_I".trim().equalsIgnoreCase(table) || "STHD".trim().equalsIgnoreCase(table) || "ADDV".trim().equalsIgnoreCase(table)) {
            flag = false;
        } else {
            List rows = jt2.queryForList("select * from HY_DBFP_J WHERE TBID='" + table + "' and upper(FLID)='STCD'");
            if (rows.size() > 0) {
                flag = true;
            }
        }
        return flag;
    }

    public String makeStcdSqlCol(String stsc) {
        String stscSql = "";
        for(String val:stsc.split(",")){
            if(stscSql.trim().equals(""))
                stscSql = " where stcd="+val;
            else
                stscSql +=" or stcd="+val;
        }
        return stscSql;
    }

    public String getIndexFiled(String table) {
        Map colmap = null;
        if ("HY_STSC_A".trim().equals(table) || "STHD".trim().equals(table)) {
            return "";
        } else {
            try {
                colmap = jt2.queryForMap("select FILDID from INDEX_DESC where TBENNM='" + table + "'");
                
            } catch (EmptyResultDataAccessException e) {
                return "";
            }
            return colmap.get("FILDID").toString();
        }
    }

    public boolean isHaveStcdColFor3(String table) {
        boolean flag = false;
        if ("STHD".trim().equalsIgnoreCase(table) || "ADDV".trim().equalsIgnoreCase(table)) {
            flag = false;
        } else {
            String sql = "SELECT count(*) FROM INFORMATION_SCHEMA.columns where column_name='stcd' and table_name='" + table + "'";
            int row2 = jt1.queryForInt(sql);
            if (row2 > 0) {
                flag = true;
            }
        }
        return flag;
    }

    public Connection getConnection(String URL, String user, String password) {
        Connection con = null;
        try {
            if (user == null) {
                con = DriverManager.getConnection(URL);
            } else {
                con = DriverManager.getConnection(URL, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void config(Properties p) {
        try {
            DriverManager.registerDriver((Driver) Class.forName(p.getProperty("source.datasource.driverClass")).newInstance());
            DriverManager.registerDriver((Driver) Class.forName(p.getProperty("target.datasource.driverClass")).newInstance());
            this.cnSource = DriverManager.getConnection(p.getProperty("source.datasource.jdbcUrl"), p.getProperty("source.datasource.username"), p.getProperty("source.datasource.password"));
            this.cnTarget = DriverManager.getConnection(p.getProperty("target.datasource.jdbcUrl"), p.getProperty("target.datasource.username"), p.getProperty("target.datasource.password"));
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "未发现此数据库驱动类型，请与开发商联系！", "提示", 0);
        }
        this.saveDirs=p.getProperty("target.datasource.dataDir");
        this.jt1 = new PageTemplate(new SingleConnectionDataSource(cnSource, false));
        this.jt2 = new PageTemplate(new SingleConnectionDataSource(cnTarget, false));

    }

    public void printVerifyInfo(String table) {
        System.out.println(table + ":\t" + getCount(jt1, table) + "/" + getCount(jt2, table));
    }

    public Integer getCount(JdbcTemplate jdbcTemplate, String table) {
        return jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase());
    }

    public Integer getCountToExcel(JdbcTemplate jdbcTemplate, String table, String stsc, boolean flg) {
        if (flg) {
            return jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase() + " where STCD in(" + stsc + ")");
        } else {
            return jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase());
        }
    }

    public Integer getCountForStsc(JdbcTemplate jdbcTemplate, String table, String stsc) {
        return jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase() + " where STCD like'%" + stsc + "%'");
    }

    public String getTabCnnm(JdbcTemplate jdbcTemplate, String table) {
        Map map = null;
        try {
            map = jdbcTemplate.queryForMap("select TBCNNM from HY_DBTP_J where TBENNM='" + table + "'");
        } catch (Exception e) {
            return "";
        }
        return map.get("TBCNNM").toString();
    }

    public String getStscName(JdbcTemplate jdbcTemplate, String stcd) {
        Map map = null;
        try {

            map = jdbcTemplate.queryForMap("select stnm from TABLE_STCD where STCD='" + stcd + "'");
        } catch (Exception e) {
            return "";
        }
        return map.get("stnm").toString();
    }

    public String getStscName2(JdbcTemplate jdbcTemplate, String stcd, int version) {
        Map map = null;
        String searchSQL = "";
        if (version == 0) {
            searchSQL = "select stnm from STHD where STCD='" + stcd + "'";
        } else {
            searchSQL = "select stnm from HY_STSC_A where STCD='" + stcd + "'";
        }
        try {
            map = jdbcTemplate.queryForMap(searchSQL);
        } catch (Exception e) {
            return "";
        }
        return map.get("stnm").toString();
    }

    public void clearTable(boolean isTarget, String table) {
        if (isTarget) {
            jt2.execute("delete from " + table.toUpperCase());
        }
    }



    public void shutdown() {
        getJt2().execute("SHUTDOWN");
    }

    public void process(JList logList, DefaultListModel listTablesModel, DefaultListModel expModel,
            DefaultListModel selectedStscModel,
            DefaultListModel selectedYearsModel,
            DefaultListModel selectedSnameModel,
            DBTool dbTool, String saveDir, int expType,
            int version) {

        expSuccessModel = expModel;
        String[] tables = new String[expModel.size()];
        String stscStr = "";
        String stnameStr = "";
        if (!selectedSnameModel.isEmpty()) {

            for (int i = 0; i < selectedSnameModel.size(); i++) {
                //返回内容为编码＋名称，处理后得到编码
                String stcdandname = selectedSnameModel.get(i).toString();
                int poi = stcdandname.lastIndexOf("]");
                String stcd = stcdandname.substring(1, poi);//编码
                String stname = stcdandname.substring(poi + 1, stcdandname.length());//名称
                if (stscStr.trim().equals("")) {
                    stscStr = "'" + stcd + "'";
                    stnameStr = stname;
                } else {
                    stscStr += "," + "'" + stcd + "'";
                    stnameStr += "," + stname;
                }
            }
        }
        if (!expModel.isEmpty()) {
            for (int i = 0; i < expModel.size(); i++) {
                tables[i] = HY_DBTP_JDao.getTabid(expModel.get(i).toString(), dbTool);
            }
        }
        if (expType != 0) {
            ((DefaultListModel) (logList.getModel())).addElement("清空目标数据库完成");
        }
        // 拷贝数据
        int i = 0;
        for (String table : tables) {
            for(int timer=0;timer<3000;timer++){boolean temflg = true;}
            i++;
            Long sdate = System.currentTimeMillis();

            boolean flg = false;
            if (expType == 0) {
                ((DefaultListModel) (logList.getModel())).addElement("正在分析表：【" + getTabCnnm(jt2, table) + "】_" + table + "的数据 ......");
                flg = createExcelTable(table, stscStr, saveDir, logList, expType);
            } else {
                flg = copyTable(table, stscStr, saveDir, logList, expType, version);
            }
            if (flg) {
                outputInfoExcel(table, logList, selectedSnameModel, stscStr);
            }
            //生成数据索引
            Long indexsdate = System.currentTimeMillis();

            insertDataIndexTable(table, stscStr, stnameStr, logList, version, expType, saveDir);
            outputLog(table, saveDir, sdate, indexsdate, true);
            if (i == tables.length) {
                ((DefaultListModel) (logList.getModel())).addElement("正在生成导出报告 ......");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DBTool.class.getName()).log(Level.SEVERE, null, ex);
                outputError("", "===process=Thread=",ex.getMessage());
            }
            outputLog(table, saveDir, sdate, indexsdate, false);
        }
        //保存导出报告
        ExcelService.createReportHtml(saveDir, expSuccessModel, errorTab, dbTool, selectedStscModel, selectedSnameModel, listTablesModel, expType, stscStr,dataIndexMap);
        ((DefaultListModel) (logList.getModel())).addElement("==导出工作成功结束！==");
    }

     public void insertDataIndexTable(String table, String stcdStr,
            String stnameStr, JList logList, int version, int expType, String saveDir) {
        String indexFiled = getIndexFiled(table);
        if (!indexFiled.trim().equals("")) {//首先保证这张表可以生成数据索引
            try {
                int row = jt1.queryForInt("select count(*) from " + table.toUpperCase());
                if (row > 0) {
                    Map colmap = null;
                    try {
                        colmap = jt2.queryForMap("select FILDTPL from INDEX_DESC where TBENNM='" + table + "'");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    try {
                        String fltp = colmap.get("FILDTPL").toString().trim();
                        String searchSQL = "";
                        if (fltp.trim().equals("T")) {
                            searchSQL = "SELECT DISTINCT STCD,datepart (yyyy," + indexFiled + ") as years,COUNT(*) AS TOTAL from " + table.toUpperCase()
                                    + " GROUP BY STCD,datepart (yyyy," + indexFiled + ")"
                                    + " ORDER BY STCD,YEARS ASC ";
                        } else {
                            searchSQL = "SELECT DISTINCT STCD," + indexFiled + " as years,COUNT(*) AS TOTAL from " + table.toUpperCase()
                                    + " GROUP BY STCD," + indexFiled
                                    + " ORDER BY STCD,YEARS ASC ";
                        }
                        outputError(table,"insertDataIndexTable_New=s数据索引:",searchSQL);
                        List rows = jt1.queryForList(searchSQL);
                        ToOrder order = new ToOrder();
                        List beforList = new ArrayList();
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            beforList.add(order.getTem(map.get("STCD").toString(), map.get("years").toString(), map.get("TOTAL").toString()));
                        }
                        List afterList = ToOrder.toOrder(beforList);
                        if (expType == 0 || expType == 2) {
                            outPutIndexToFile(table, saveDir, afterList, version);
                        }
                        if (expType == 1 || expType == 2) {
                            if (afterList != null && afterList.size() > 0) {
                                for (int w = 0; w < afterList.size(); w++) {
                                    String[] values = ((String) afterList.get(w).toString()).split(",");
                                    String insertSql = "INSERT INTO DATA_INDEX_A(TBID,ATBCNM,BSTCD,CSTNM,DTOTAL,EYEAR)VALUES('" + table + "','"
                                            + getTabCnnm(jt2, table) + "'," + values[0] + ",'" + getStscName2(jt1, values[0], version) + "','" + values[2] + "','" + values[1] + "')";
                                    jt2.execute(insertSql);
                                }
                            }
                        }
//                        ((DefaultListModel) (logList.getModel())).addElement("         √成功生成【" + getTabCnnm(jt2, table) + "】的数据索引");
                        dataIndexMap.put(table, "成功");
                    } catch (Exception ex) {
                        ex.printStackTrace();
//                        ((DefaultListModel) (logList.getModel())).addElement("         ※无法生成表数据索引，请确认日期字段【"+indexFiled+"】是否存在,若不存在请修改【c:\\tables.xls】※");
                         dataIndexMap.put(table, "失败");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                outputError(table, "===insertDataIndexTable_new=",ex.getMessage());
//                ((DefaultListModel) (logList.getModel())).addElement("         ※无法生成【" + getTabCnnm(jt2, table) + "】的数据索引，请确认日期字段【"+indexFiled+"】是否存在※");
            }
        }
    }

    public Connection getCnSource() {
        return cnSource;
    }

    public Connection getCnTarget() {
        return cnTarget;
    }
    public String getSaveDirs() {
        return saveDirs;
    }
    public JdbcTemplate getJt1() {
        return jt1;
    }

    public JdbcTemplate getJt2() {
        return jt2;
    }

    private void outputInfoExcel(String table, JList logList,
            DefaultListModel selectedSnameModel, String stsc) {
        selectedSnameModel.removeElement("数据索引表");
        selectedSnameModel.removeElement("数据一览表");
        int realCount = getCountToExcel(jt1, table, stsc, isHaveStcdCol(table));
        if (realCount == 0) {
            ((DefaultListModel) (logList.getModel())).addElement("         ◇数据表 【" + getTabCnnm(jt2, table) + "】 的可导出数据为空◇");
        } else {
            ((DefaultListModel) (logList.getModel())).addElement("         √成功结束导出 【" + getTabCnnm(jt2, table) + "】 的数据，共有数据条数：" + getCount(jt1, table) + "，导出条数：" + realCount);
        }
    }

    public boolean createExcelTable(final String table, String stsc, String saveDir, final JList logList, int expType) {
        String searChsql = "";
        String countChsql = "";
        if (isHaveStcdCol(table)) {
            searChsql = "select * from " + table.toUpperCase() + makeStcdSqlCol(stsc ).toUpperCase();
            countChsql = "select count(*) from " + table.toUpperCase() + makeStcdSqlCol(stsc ).toUpperCase();
        } else {
            searChsql = "select * from " + table.toUpperCase();
            countChsql = "select count(*) from " + table.toUpperCase();
        }
        outputError(table,"createExcelTable=searChsql:",searChsql);
        outputError(table,"createExcelTable=countChsql:",countChsql);
        try {
            final String tablename = getTabCnnm(jt2, table);
            //总记录数
            final int totalRecords = jt1.queryForInt(countChsql);
            final String path = saveDir;
            if(totalRecords>0){
                final FileWriter fw = new FileWriter(path + "\\excel\\" + tablename +".txt");
                ((DefaultListModel) (logList.getModel())).addElement("         →正在写入文件【" + saveDir + "\\excel\\" + tablename + ".txt】,请等待...");
                jt1.query(searChsql,  new RowMapper() {

                    int k=0;
                    public Object mapRow(final ResultSet rs, int rowNum) throws SQLException {

                        StringBuffer fields = new StringBuffer("");
                        ResultSetMetaData meta = rs.getMetaData();
                        int cols = meta.getColumnCount();
                        if(k==0){
                            try{
                                for (int i = 1; i <=cols; i++) {
                                    if (fields.toString().trim().equals("")) {
                                        fields = new StringBuffer(meta.getColumnName(i));
                                    } else {
                                        fields = new StringBuffer(fields + "\t" + meta.getColumnName(i));
                                    }
                                }
                            fw.write(fields.toString() + "\r\n");
                            fields=new StringBuffer("");
                            for (int i = 1; i <= cols; i++) {
                                 Object obj = rs.getObject(i);
                                 obj=obj==null?"null":obj;
                                if (fields.toString().trim().equals("")) {
                                    fields = new StringBuffer(obj.toString());
                                } else {
                                    fields = new StringBuffer(fields + "\t" + obj.toString());
                                }
                            }
                            fw.write(fields.toString() + "\r\n");
                            fields=new StringBuffer("");

                            }catch(Exception ex){ex.printStackTrace();}
                        }else{
                            for (int i = 1; i <= cols; i++) {
                                 Object obj = rs.getObject(i);
                                 obj=obj==null?"null":obj;
                                if (fields.toString().trim().equals("")) {
                                    fields = new StringBuffer(obj.toString());
                                } else {
                                    fields = new StringBuffer(fields + "\t" + obj.toString());
                                }
                            }
                            try {
                                    fw.write(fields.toString() + "\r\n");
                                    fields=new StringBuffer("");
                                } catch (IOException ex) {
                                     ex.printStackTrace();
                                }
                        }
                        k++;
                         return null;
                    }

                });
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            expSuccessModel.removeElement(getTabCnnm(jt2, table));
            if ("".trim().equals(errorTab)) {
                errorTab = getTabCnnm(jt2, table);
            } else {
                errorTab += "," + getTabCnnm(jt2, table);
            }
            if(expType==0)
                ((DefaultListModel) (logList.getModel())).addElement("         ※无法执行导出， 请确认数据表【" + getTabCnnm(jt2, table) + "】_" + table + " 是否存在※");
            else
                ((DefaultListModel) (logList.getModel())).addElement("         ※无法执行导出， 请确认数据表【" + getTabCnnm(jt2, table) + "】_" + table + "是否存在，或者字段类型是否正确※");
            outputError(table, "===createExcelTable=",e.getMessage());
            return false;
        }
        return true;
    }

    public void outPutIndexToFile(String table, String saveDir, List afterList, int version) {
        try {
            File files = new File(saveDir + "\\excel\\dataIndex.txt");
            FileWriter fw = null;
            if (files.exists()) {
                fw = new FileWriter(saveDir + "\\excel\\dataIndex.txt", true);
            } else {
                fw = new FileWriter(saveDir + "\\excel\\dataIndex.txt");
                fw.write("表编号\t表名称\t测站名称\t测站编码\t年份\t数据量\r\n");
            }
            for (int i = 0; i < afterList.size(); i++) {
                String[] values = ((String) afterList.get(i).toString()).split(",");
                String str = table + "\t" + getTabCnnm(jt2, table) + "\t";
                for (int k = 0; k < values.length; k++) {
                    if (k == 0) {
                        str += getStscName2(jt1, values[0], version) + "\t" + values[k] + "\t";
                    } else {
                        if ((k + 1) == values.length) {
                            str += values[k] + "\r\n";
                        } else {
                            str += values[k] + "\t";
                        }
                    }
                }
                fw.write(str);
            }
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void outputLog(String table, String saveDir, long sdate, long indexsdate, boolean flg) {
        try {
            File files = new File(saveDir + "\\excel\\expLog.txt");
            FileWriter fw = null;
            BigDecimal nowdate = new BigDecimal(String.valueOf(System.currentTimeMillis()));
            if (files.exists()) {
                fw = new FileWriter(saveDir + "\\excel\\expLog.txt", true);
            } else {
                fw = new FileWriter(saveDir + "\\excel\\expLog.txt");
            }
            if (flg) {
                fw.write("生成索引----:" + getTabCnnm(jt2, table) + "\r\n");
                fw.write("用时----:" + nowdate.subtract(new BigDecimal(indexsdate)) + "--毫秒\r\n");
            } else {
                fw.write("导出数据----:" + getTabCnnm(jt2, table) + "\r\n");
                fw.write("用时----:" + nowdate.subtract(new BigDecimal(sdate)) + "--毫秒\r\n");
            }
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void outputError(String table, String methodname, String errormsg) {
         String saveDir=this.getSaveDirs();
        try {
            File files = new File(saveDir + "\\excel\\expErrorLog.txt");
            FileWriter fw = null;
            if (files.exists()) {
                fw = new FileWriter(saveDir + "\\excel\\expErrorLog.txt", true);
            } else {
                fw = new FileWriter(saveDir + "\\excel\\expErrorLog.txt");
            }
            fw.write(getTabCnnm(jt2, table) + "\r\n");
            fw.write(methodname + "\r\n");
            fw.write(errormsg);
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 比对字段名称YR和YEAR
     * 如果字段为YR并且描述中有YR字段，那么返回YR
     * 否则查看数据库中是否有YEAR字段，如果有则反回YEAR
     */
    public String compareYearColumn(String table){
        List rows = jt2.queryForList("select * from HY_DBFP_J WHERE TBID='" + table + "' and upper(FLID)='YR'");
        if(rows.size()>0)
            return "YR";
        else{
            List rows2 = jt2.queryForList("select * from HY_DBFP_J WHERE TBID='" + table + "' and upper(FLID)='YEAR'");
            if(rows2.size()>0)
                return "YEAR";
            else
                return "";
        }
    }
    public String[] makeFiledsAndParamets(final String tablename){
        List rows = jt1.query("select top 1 * from "+tablename.toUpperCase(), new RowMapper() {
                public Object mapRow(final ResultSet rs, int rowNum) throws SQLException {
                    String result[] = new String[2];
                    String fields="";
                    String params="";
                    ResultSetMetaData meta = rs.getMetaData();
                    final int cols = meta.getColumnCount();
                    for (int i = 0; i < cols; i++) {
                        String colname = meta.getColumnName(i + 1);
                        if(colname.toUpperCase().equals("YR"))
                            colname = compareYearColumn(tablename);
                        fields += colname + ",";
                        params += "?,";
                    }
                    result[0]=fields;
                    result[1]=params;
                    return result;
                }
        });
        if(rows.size()==0)
            return null;
        else
            return (String[])rows.get(0);
    }
    public String getFileds(final String tablename,DBTool dbTool,int type){
        JdbcTemplate jt = null;
        if(type==0)
            jt = dbTool.getJt1();//源
        else
            jt = dbTool.getJt2();//结果
        List rows = null;
        try{
            rows = jt.query("select top 1 * from "+tablename.toUpperCase(), new RowMapper() {
                    public Object mapRow(final ResultSet rs, int rowNum) throws SQLException {
                        String fields="";
                        ResultSetMetaData meta = rs.getMetaData();
                        final int cols = meta.getColumnCount();
                        for (int i = 0; i < cols; i++) {
                            String colname = meta.getColumnName(i + 1);
                            if(fields.trim().equals(""))
                                fields = colname;
                            else
                                fields += ","+colname;
                        }
                        return fields;
                    }
            });
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "请确认数据表["+getTabCnnm(jt2,tablename)+"_"+tablename+"]是否存在", "错误", 0);
        }
        if(rows.size()==0)
            return null;
        else
            return (String)rows.get(0);
    }
    public String getFiledsCNNM(String tablename,String fildEnnm){

        Map colmap = null;
        try {
            colmap = jt2.queryForMap("select FLDCNNM from HY_DBFP_J where upper(TBID)='" + tablename.toUpperCase() + "' and upper(FLID)='"+fildEnnm.toUpperCase() +"'");
        } catch (EmptyResultDataAccessException e) {
            return fildEnnm;
        }
        return colmap.get("FLDCNNM").toString();
    }
     public static void main(String[] args) {
        DBTool dbTool = new DBTool("c://config.properties");
    }
}
