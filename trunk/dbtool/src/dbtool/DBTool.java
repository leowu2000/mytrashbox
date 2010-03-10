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
        outputError(table,"copyTable=searChsql:",searChsql);
        try {
            if (expType != 0) {
                clearTable(true, table.toUpperCase());
            }
            String result[] = makeFiledsAndParamets(table);
            if(result==null){
//                ((DefaultListModel) (logList.getModel())).addElement("::���ݱ���" + table + "  ��" + getTabCnnm(jt2, table) + "������Ϊ��ϵͳ���� ");
                outputError(table, "=copyTable=",getTabCnnm(jt2, table)+"���ݱ�����Ϊ��ϵͳ����");
            }
            else{
                ((DefaultListModel) (logList.getModel())).addElement("���ڷ�����" + table + "  ��" + getTabCnnm(jt2, table) + "�������� ......");
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

                ((DefaultListModel) (logList.getModel())).addElement("           ...���ڵ�������" + table + "  ��" + getTabCnnm(jt2, table) + "�� �����ݣ���ȴ�...");
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
            JOptionPane.showMessageDialog(null, "δ���ִ����ݿ��������ͣ����뿪������ϵ��", "��ʾ", 0);
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
                //��������Ϊ���룫���ƣ�������õ�����
                String stcdandname = selectedSnameModel.get(i).toString();
                int poi = stcdandname.lastIndexOf("]");
                String stcd = stcdandname.substring(1, poi);//����
                String stname = stcdandname.substring(poi + 1, stcdandname.length());//����
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
            ((DefaultListModel) (logList.getModel())).addElement("���Ŀ�����ݿ����");
        }
        // ��������
        int i = 0;
        for (String table : tables) {
            i++;
            Long sdate = System.currentTimeMillis();

            boolean flg = false;
            if (expType == 0) {
                ((DefaultListModel) (logList.getModel())).addElement("���ڷ�����" + table + "  ��" + getTabCnnm(jt2, table) + "�������� ......");
                flg = createExcelTable(table, stscStr, saveDir, logList, expType);

            } else {
                flg = copyTable(table, stscStr, saveDir, logList, expType, version);
//                if(expType==1){
//                    flg = copyTable(table, stscStr, saveDir, logList, expType, version);
//                }else{
//                    ((DefaultListModel) (logList.getModel())).addElement("���ڷ�����" + table + "  ��" + getTabCnnm(jt2, table) + "�������� ......");
//                    flg = copyTablePS(table, stscStr, saveDir, logList, expType, version);
//                }
            }
            if (flg) {
//                if (expType == 0) {
                    outputInfoExcel(table, logList, selectedStscModel, selectedSnameModel, stscStr);
//                }
            }
            //������������
            Long indexsdate = System.currentTimeMillis();

            insertDataIndexTable_New(table, stscStr, stnameStr, logList, version, expType, saveDir);
            outputLog(table, saveDir, sdate, indexsdate, true);
            if (i == tables.length) {
                ((DefaultListModel) (logList.getModel())).addElement("�������ɵ������� ......");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DBTool.class.getName()).log(Level.SEVERE, null, ex);
                outputError("", "===process=Thread=",ex.getMessage());
            }
            outputLog(table, saveDir, sdate, indexsdate, false);
        }
        //���浼������
        ExcelService.createReportHtml(saveDir, expSuccessModel, errorTab, dbTool, selectedStscModel, selectedSnameModel, listTablesModel, expType, stscStr);
        ((DefaultListModel) (logList.getModel())).addElement("==���������ɹ�������==");
    }

    public void insertDataIndexTable(String table, String stcdStr,
            String stnameStr, JList logList) {
        String stcd[] = stcdStr.split(",");
        String stname[] = stnameStr.split(",");
        String indexFiled = getIndexFiled(table);
        if (!indexFiled.trim().equals("")) {//���ȱ�֤���ű�����������������
            try {
                int row = jt1.queryForInt("select count(*) from " + table + " where stcd in(" + stcdStr + ")");
                if (row > 0) {
                    ((DefaultListModel) (logList.getModel())).addElement("�������ɱ���" + getTabCnnm(jt2, table) + "������������ ......");

                    for (int i = 0; i < stcd.length; i++) {
                        int stcdrow = jt1.queryForInt("select count(*) from " + table + " where stcd =" + stcd[i] + "");
                        if (stcdrow > 0) {
                            String insertSql = "";
                            Map colmap = null;
                            try {
                                colmap = jt2.queryForMap("select FILDTPL from INDEX_DESC where TBENNM='" + table + "'");
                            } catch (EmptyResultDataAccessException e) {
                                colmap = null;
                            }
                            if (colmap != null) {
                                try {
                                    String fltp = colmap.get("FILDTPL").toString().trim();

                                    List rows = jt1.queryForList("SELECT MIN(" + indexFiled + ") AS minyear, MAX(" + indexFiled + ") AS maxyear from " + table + " where stcd=" + stcd[i] + "");
                                    Iterator it = rows.iterator();
                                    while (it.hasNext()) {
                                        Map map = (Map) it.next();
                                        if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                            if (fltp.trim().equals("T")) {
                                                String val1 = map.get("minyear").toString().substring(0, 4);
                                                String val2 = map.get("maxyear").toString().substring(0, 4);
                                                int s_year = new Integer(val1).intValue();
                                                int z_year = new Integer(val2).intValue();
                                                ToOrder order = new ToOrder();
                                                List beforList = new ArrayList();
                                                for (int year = s_year; year <= z_year; year++) {
//                                                    System.out.println("select count(*) from " + table + " where stcd=" + stcd[i] + " and datepart(yyyy," + indexFiled + ")='" + year + "'");
                                                    int p = jt1.queryForInt("select count(*) from " + table + " where stcd=" + stcd[i] + " and datepart(yyyy," + indexFiled + ")='" + year + "'");
//                                                    beforList.add(order.getTem(String.valueOf(year), String.valueOf(p)));
                                                }
                                                List afterList = order.combination(beforList);
                                                if (afterList != null && afterList.size() > 0) {
                                                    for (int w = 0; w < afterList.size(); w++) {
                                                        String[] years = ((String) afterList.get(w).toString()).split("-");
                                                        int count = jt1.queryForInt("select count(*) from " + table + " where stcd=" + stcd[i] + " and datepart(yyyy," + indexFiled + ")>='" + years[0] + "' and datepart(yyyy," + indexFiled + ")<='" + years[1] + "'");
                                                        insertSql = "INSERT INTO DATA_INDEX_A(ATBCNM,BSTCD,CSTNM,DTOTAL,EYEAR)VALUES('"
                                                                + getTabCnnm(jt2, table) + "'," + stcd[i] + ",'" + stname[i] + "','" + count + "','" + years[0] + "-" + years[1] + "')";
                                                        jt2.execute(insertSql);
                                                    }
                                                }
                                            } else {
                                                String val1 = map.get("minyear").toString();
                                                String val2 = map.get("maxyear").toString();
                                                int s_year = new Integer(val1).intValue();
                                                int z_year = new Integer(val2).intValue();
                                                ToOrder order = new ToOrder();
                                                List beforList = new ArrayList();
                                                for (int year = s_year; year <= z_year; year++) {
                                                    int p = jt1.queryForInt("select count(*) from " + table + " where stcd=" + stcd[i] + " and " + indexFiled + "='" + year + "'");
//                                                    beforList.add(order.getTem(String.valueOf(year), String.valueOf(p)));
                                                }
                                                List afterList = order.combination(beforList);
                                                if (afterList != null && afterList.size() > 0) {
                                                    for (int w = 0; w < afterList.size(); w++) {
                                                        String[] years = ((String) afterList.get(w).toString()).split("-");
                                                        int count = jt1.queryForInt("select count(*) from " + table + " where stcd=" + stcd[i] + " and " + indexFiled + ">='" + years[0] + "' and " + indexFiled + "<='" + years[1] + "'");
                                                        insertSql = "INSERT INTO DATA_INDEX_A(ATBCNM,BSTCD,CSTNM,DTOTAL,EYEAR)VALUES('"
                                                                + getTabCnnm(jt2, table) + "'," + stcd[i] + ",'" + stname[i] + "','" + count + "','" + years[0] + "-" + years[1] + "')";
                                                        jt2.execute(insertSql);
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    ((DefaultListModel) (logList.getModel())).addElement("�ɹ����ɱ���" + getTabCnnm(jt2, table) + "������������");

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                 outputError(table, "===insertDataIndexTable=��������",ex.getMessage());
            }
        }
    }

    public void insertDataIndexTable_New(String table, String stcdStr,
            String stnameStr, JList logList, int version, int expType, String saveDir) {
        String indexFiled = getIndexFiled(table);
        if (!indexFiled.trim().equals("")) {//���ȱ�֤���ű�����������������
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
                        outputError(table,"insertDataIndexTable_New=s��������:",searchSQL);
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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
//                    ((DefaultListModel) (logList.getModel())).addElement("�ɹ����ɱ���" + getTabCnnm(jt2, table) + "������������");

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                outputError(table, "===insertDataIndexTable_new=",ex.getMessage());
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

    private void outputInfo(String table, JList logList, DefaultListModel selectedStscModel, DefaultListModel selectedSnameModel) {
        selectedSnameModel.removeElement("����������");
        selectedSnameModel.removeElement("����һ����");

        ((DefaultListModel) (logList.getModel())).addElement("���ڵ�������" + table + "  ��" + getTabCnnm(jt2, table) + "��   ��������������" + getCount(jt1, table) + "������������" + getCount(jt2, table));

    }

    private void outputInfoExcel(String table, JList logList, DefaultListModel selectedStscModel,
            DefaultListModel selectedSnameModel, String stsc) {
        selectedSnameModel.removeElement("����������");
        selectedSnameModel.removeElement("����һ����");
        int realCount = getCountToExcel(jt1, table, stsc, isHaveStcdCol(table));
        if (realCount == 0) {
            ((DefaultListModel) (logList.getModel())).addElement("���ݱ���" + table + "  ��" + getTabCnnm(jt2, table) + "���ɵ�������Ϊ�գ�");
        } else {
            ((DefaultListModel) (logList.getModel())).addElement("�ɹ�������������" + table + "  ��" + getTabCnnm(jt2, table) + "��   ��������������" + getCount(jt1, table) + "������������" + realCount);
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
            //�ܼ�¼��
            final int totalRecords = jt1.queryForInt(countChsql);
            final String path = saveDir;
//            final int allpage = totalPage;
            if(totalRecords>0){
                final FileWriter fw = new FileWriter(path + "\\excel\\" + tablename +".txt");
                ((DefaultListModel) (logList.getModel())).addElement("           ...����д���ļ�����" + saveDir + "\\excel\\" + tablename + ".txt��,��ȴ�...");
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

            /**
            for (int p = 0; p < totalPage; p++) {

                FileWriter fw = new FileWriter(saveDir + "\\excel\\" + tablename + "_" + p + ".txt");

                List rowList = jt1.querySP(searChsql, p * 50000 + 1, 50000);

                Iterator it = rowList.iterator();
                while (it.hasNext()) {
                    String[] themap = (String[]) it.next();
                    String str = "";
                    for (int i = 0; i < themap.length; i++) {
                        if ((i + 1) == themap.length) {
                            str += themap[i] + "\r\n";
                        } else {
                            str += themap[i] + "\t";
                        }
                    }
                    fw.write(str);
                    str = "";
                }
                fw.close();
                if (p + 1 == totalPage) {
                    ((DefaultListModel) (logList.getModel())).addElement("           ...���ڴ���  " + p * 50000 + "  ��   " + totalRecords + "  ����¼���ļ����ƣ���" + saveDir + "\\excel\\" + tablename + "_" + p + ".txt��");
                } else {
                    ((DefaultListModel) (logList.getModel())).addElement("           ...���ڴ���  " + p * 50000 + "  ��   " + (p + 1) * 50000 + "  ����¼���ļ����ƣ���" + saveDir + "\\excel\\" + tablename + "_" + p + ".txt��");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DBTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            expSuccessModel.removeElement(getTabCnnm(jt2, table));
            if ("".trim().equals(errorTab)) {
                errorTab = getTabCnnm(jt2, table);
            } else {
                errorTab += "," + getTabCnnm(jt2, table);
            }
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
                fw.write("�����\t������\t��վ����\t��վ����\t���\t������\r\n");
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
                fw.write("��������----:" + getTabCnnm(jt2, table) + "\r\n");
                fw.write("��ʱ----:" + nowdate.subtract(new BigDecimal(indexsdate)) + "--����\r\n");
            } else {
                fw.write("��������----:" + getTabCnnm(jt2, table) + "\r\n");
                fw.write("��ʱ----:" + nowdate.subtract(new BigDecimal(sdate)) + "--����\r\n");
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
    public boolean copyTablePS(final String table, String stsc, String saveDir, JList logList, int expType, int version) {

        String searChsql = "";
        String countChsql = "";
        if (isHaveStcdCol(table)) {
            searChsql = "select * from " + table + " where stcd in(" + stsc + ")";
            countChsql = "select count(*) from " + table + " where stcd in(" + stsc + ")";
        } else {
            searChsql = "select * from " + table;
            countChsql = "select count(*) from " + table;
        }
        try {
            if (expType != 0) {
                clearTable(true, table);
            }
            final String tablename = getTabCnnm(jt2, table);
            //�ܼ�¼��
            int totalRecords = jt1.queryForInt(countChsql);
            if (totalRecords == 0) {
                ((DefaultListModel) (logList.getModel())).addElement("-------��" + table + "  ��" + getTabCnnm(jt2, table) + "��   ��������������" + getCount(jt1, table) + "���赼������������0");
            }
            int totalPage = 1;
            if (totalRecords % 10000 == 0) {
                totalPage = totalRecords / 50000;
            } else {
                totalPage = totalRecords / 50000 + 1;
            }
            for (int p = 0; p < totalPage; p++) {

                List rowList = jt1.querySP(searChsql, p * 50000 + 1, 50000);
                String fields = "";
                String params = "";
                Iterator it = rowList.iterator();
                int c = 0;
                String sql = "";
                while (it.hasNext()) {

                    final String[] themap = (String[]) it.next();
                    String str = "";
                    if (c == 0) {
                        FileWriter fw = new FileWriter(saveDir + "\\excel\\" + tablename + "_" + p + ".txt");
                        for (int i = 0; i < themap.length; i++) {
                            fields += themap[i] + ",";
                            params += "?,";
                            if ((i + 1) == themap.length) {
                                str += themap[i] + "\r\n";
                            } else {
                                str += themap[i] + "\t";
                            }
                        }
                        fw.write(str);
                        fw.close();
                        sql = "insert into " + table + " (" + fields.substring(0, fields.length() - 1) + ") values (" + params.substring(0, params.length() - 1) + ")";
                    } else {
                        final FileWriter fw2 = new FileWriter(saveDir + "\\excel\\" + tablename + "_" + p + ".txt", true);
                        jt2.execute(sql, new PreparedStatementCallback() {

                            public Object doInPreparedStatement(PreparedStatement pStat) throws SQLException,
                                    DataAccessException {

                                String substr = "";
                                for (int i = 1; i <= themap.length; i++) {

                                    if (i == themap.length) {
                                        substr += themap[i - 1] + "\r\n";
                                    } else {
                                        substr += themap[i - 1] + "\t";
                                    }
                                    try {
                                        fw2.write(substr);
                                        substr = "";
                                    } catch (IOException ex) {
                                        Logger.getLogger(DBTool.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    Object o = themap[i - 1];
                                    if (o instanceof Blob) {
                                        pStat.setObject(i, ((Blob) o).getBytes(1, 100000000));//100mb
                                    } else {
                                        pStat.setObject(i, themap[i - 1]);
                                    }
                                }
                                pStat.execute();
                                return null;
                            }
                        });
                        fw2.close();
                    }
                    c++;
                }
                if (p == 0) {
                    ((DefaultListModel) (logList.getModel())).addElement("-------��" + table + "  ��" + getTabCnnm(jt2, table) + "��   ��������������" + getCount(jt1, table) + "���赼������������" + totalRecords);
                    ((DefaultListModel) (logList.getModel())).addElement("���ڵ���------ " + p * 50000 + " �� " + (p + 1) * 50000 + "    �����ļ�:[" + saveDir + "\\excel\\" + tablename + "_" + p + ".txt]");
                } else {
                    ((DefaultListModel) (logList.getModel())).addElement("���ڵ���------ " + p * 50000 + " �� " + (p + 1) * 50000 + "    �����ļ�:[" + saveDir + "\\excel\\" + tablename + "_" + p + ".txt]");
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
             outputError(table, "===copyTablePS=",e.getMessage());
            return false;
        }
        return true;
    }
**/
    /**
    public boolean copyTablePSOnly(final String table, String stsc, String saveDir, JList logList, int expType, int version) {

        String searChsql = "";
        String countChsql = "";
        if (isHaveStcdCol(table)) {
            searChsql = "select * from " + table + " where stcd in(" + stsc + ")";
            countChsql = "select count(*) from " + table + " where stcd in(" + stsc + ")";
        } else {
            searChsql = "select * from " + table;
            countChsql = "select count(*) from " + table;
        }
        try {
            if (expType != 0) {
                clearTable(true, table);
            }
            //�ܼ�¼��
            int totalRecords = jt1.queryForInt(countChsql);
            int totalPage = 1;
            if (totalRecords % 10000 == 0) {
                totalPage = totalRecords / 50000;
            } else {
                totalPage = totalRecords / 50000 + 1;
            }
            outputError(table, "===ȡ����Ч��������=",String.valueOf(totalPage));
            for (int p = 0; p < totalPage; p++) {

                List rowList = jt1.querySP(searChsql, p * 50000 + 1, 50000);
                String fields = "";
                String params = "";
                Iterator it = rowList.iterator();
                int c = 0;
                String sql = "";
                while (it.hasNext()) {

                    final String[] themap = (String[]) it.next();
                    String str = "";
                    if (c == 0) {
                        for (int i = 0; i < themap.length; i++) {
                            fields += themap[i] + ",";
                            params += "?,";
                        }
                        sql = "insert into " + table + " (" + fields.substring(0, fields.length() - 1) + ") values (" + params.substring(0, params.length() - 1) + ")";
                    } else {
                        jt2.execute(sql, new PreparedStatementCallback() {

                            public Object doInPreparedStatement(PreparedStatement pStat) throws SQLException,
                                    DataAccessException {
                                for (int i = 1; i <= themap.length; i++) {
                                    Object o = themap[i - 1];
                                    if (o instanceof Blob) {
                                        pStat.setObject(i, ((Blob) o).getBytes(1, 100000000));//100mb
                                    } else {
                                        pStat.setObject(i, themap[i - 1]);
                                    }
                                }
                                pStat.execute();
                                return null;
                            }
                        });
                    }
                    c++;
                }
                if (p == 0) {
                    ((DefaultListModel) (logList.getModel())).addElement("���ڵ�������" + table + "  ��" + getTabCnnm(jt2, table) + "��   ��������������" + getCount(jt1, table) + "���赼������������" + totalRecords);
                    ((DefaultListModel) (logList.getModel())).addElement("���ڵ���---------------------------- " + p * 50000 + " �� " + (p + 1) * 50000);
                } else {
                    ((DefaultListModel) (logList.getModel())).addElement("���ڵ���---------------------------- " + p * 50000 + " �� " + (p + 1) * 50000);
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
            outputError(table, "===copyTablePSOnly=",e.getMessage());
            return false;
        }
        return true;
    }
     * */
    /**
     * �ȶ��ֶ�����YR��YEAR
     * ����ֶ�ΪYR������������YR�ֶΣ���ô����YR
     * ����鿴���ݿ����Ƿ���YEAR�ֶΣ�������򷴻�YEAR
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
     public static void main(String[] args) {
        DBTool dbTool = new DBTool("c://config.properties");
    }
}