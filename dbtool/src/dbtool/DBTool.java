package dbtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    String saveDirs="";
    DefaultListModel expSuccessModel = new DefaultListModel();
    String errorTab = "";
    Map dataIndexMap = new HashMap();//�����Ƿ���������
    Map dataDescMap = new HashMap();//վ������
    Map resultStscMap = new HashMap();//ÿ��վ�������ͳ�� ���ɽ��: վ��   ��ʼ��-������  ������
    String[] rpareStsc = null;
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

    public boolean copyTable(final String table, String stsc,
            String saveDir, JList logList, int expType,
            int version,boolean isTurnChar,int dbtype) {

        String searChsql = "";
        ((DefaultListModel) (logList.getModel())).addElement("���ڷ�������" + getTabCnnm(jt2, table) + "��_" + table + "������ ......");
        try{
            String result[] = makeFiledsAndParamets(table,dbtype);
            if(result==null){
                outputError("\r\n"+table, "=copyTable=",getTabCnnm(jt2, table)+"���ݱ�����Ϊ��ϵͳ����");
            }
            else{
                   //������������ิ�Ʊ�����
//                String createSql = "CREATE TABLE "+table.toUpperCase()+" (";
//                for(String tbFld:result[0].split(",")){
//                    createSql+=" "+tbFld.toUpperCase()+" CHAR(255),";
//                }
//                createSql = createSql.substring(0,createSql.length()-1)+")";
//                outputError("\r\n"+table, "=copyTable=������",createSql);
//                jt2.execute(createSql);
                //�������
                
                ((DefaultListModel) (logList.getModel())).addElement("         �����ڵ�����" + getTabCnnm(jt2, table) + "�������ݣ���ȴ�...");
                if (isHaveStcdCol(table)) {
                    String resultstcd[] = makeStcdSqlCol(stsc,200,false);
                    for(String search_stcdsql:resultstcd){
                        searChsql = "select * from " + table.toUpperCase()+" "+search_stcdsql;
                        wirteToDatabase(table,saveDir,searChsql,logList,expType,version,isTurnChar,result);
                    }
                } else {
                    searChsql = "select * from " + table.toUpperCase();
                    wirteToDatabase(table,saveDir,searChsql,logList,expType,version,isTurnChar,result);
                }
                if (expType == 2) {
                    createExcelTable(table, rpareStsc, saveDir, logList, expType,isTurnChar);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public void wirteToDatabase(final String table,
            String saveDir, String searChsql,
            JList logList, int expType,
            int version,boolean isTurnChar
            ,String result[]){
        outputError("\r\n"+table,"copyTable=searChsql:",searChsql);
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            expSuccessModel.removeElement(getTabCnnm(jt2, table));
            if ("".trim().equals(errorTab)) {
                errorTab = getTabCnnm(jt2, table);
            } else {
                errorTab += "," + getTabCnnm(jt2, table);
            }
            ((DefaultListModel) (logList.getModel())).addElement("         ��ϵͳ��⵽��ṹ�����޷�ִ�С�" + getTabCnnm(jt2, table) + "���ĵ���������");
            outputError("\r\n"+table, "=copyTable=",e.getMessage());
        }
    }
    public boolean isHaveStcdCol(String table) {
        boolean flag = false;
        if ("HY_STSC_A".trim().equalsIgnoreCase(table)
                || "HY_DATBDL_I".trim().equalsIgnoreCase(table)
                || "STHD".trim().equalsIgnoreCase(table)
                || "ADDV".trim().equalsIgnoreCase(table)) {
            flag = false;
        } else {
            List rows = jt2.queryForList("select * from HY_DBFP_J WHERE upper(TBID)='" + table.toUpperCase() + "' and upper(FLID)='STCD'");
            if (rows.size() > 0) {
                flag = true;
            }
        }
        return flag;
    }
    /**
     * ����վ�������ӵ�����������,��Ҫ������֯,
     * ��ֹ���ݿ�parameters���������������
     * @param stsc
     * @return
     */
    public String[] makeStcdSqlCol(String stsc,int cutNum,boolean flg) {
        String stscSql = "";
        String result[] = null;
        String stscArr[] = stsc.split(",");
        int count = stscArr.length;
        if(count>cutNum){
            int p = count/cutNum;
            if(count%cutNum!=0)
                p = count/cutNum+1;
            result = new String[p];
            for(int k=0;k<p;k++){
                int endInt = (k+1)*cutNum;
                if(endInt>count)
                    endInt = count;
                for(int i=k*cutNum;i<endInt;i++){
                    if(flg){
                        if(stscSql.trim().equals(""))
                            stscSql = stscArr[i];
                         else
                            stscSql +=","+ stscArr[i];
                    }else{
                        if(stscSql.trim().equals(""))
                            stscSql = " WHERE STCD="+stscArr[i];
                         else
                            stscSql +=" OR STCD="+stscArr[i];
                    }
                }
                result[k]=stscSql;
                stscSql = "";
            }
        }else{
            result = new String[1];
            if(flg)
                stscSql=stsc;
            else{
                for(int i=0;i<count;i++){
                    if(stscSql.trim().equals(""))
                        stscSql = " WHERE STCD="+stscArr[i];
                    else
                        stscSql +=" OR STCD="+stscArr[i];
                }
            }
            result[0]=stscSql;
        }
        return result;
    }

    /**
     * ��ѯ���ر�����������ֶ�����
     * @param table
     * @return
     */
    public String getIndexFiled(String table) {
        Map colmap = null;
        if ("HY_STSC_A".trim().equals(table) || "STHD".trim().equals(table)||"HY_DATBDL_I".trim().equals(table)) {
            return "";
        } else {
            try {
                colmap = jt2.queryForMap("SELECT FILDID FROM INDEX_DESC WHERE upper(TBENNM)='" + table.toUpperCase() + "'");
            } catch (EmptyResultDataAccessException e) {
                return "";
            }
            return colmap.get("FILDID").toString();
        }
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
        int result = 0;
        if (flg) {
            for(String search_stsc:rpareStsc){
                int count = jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase() + " "+search_stsc);
                result = result+count;
            }
            return result;
        } else {
          return jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase());
        }
        
    }

    public Integer getCountForStsc(JdbcTemplate jdbcTemplate, String table, String stsc) {
        return jdbcTemplate.queryForInt("select count(*) from " + table.toUpperCase() + " where STCD ��'" + stsc + "'");
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
    /**
     * ���Զ����ɵ�Ŀ����в�ѯ�����ز�վ����
     * @param jdbcTemplate
     * @param stcd
     * @return
     */
    public String getStscName(JdbcTemplate jdbcTemplate, String stcd) {
        Map map = null;
        try {
            map = jdbcTemplate.queryForMap("SELECT STNM FROM TABLE_STCD where STCD='" + stcd + "'");
        } catch (Exception e) {
            return "";
        }
        return map.get("STNM").toString();
    }
    /**
     * ��Դ���в�ѯ�����ز�վ����
     * @param jdbcTemplate
     * @param stcd ��վ����
     * @param version ���ݰ汾 3.0  or  4.0
     * @return
     */
    public Object getStscName2(JdbcTemplate jdbcTemplate, String stcd, int version) {
        Map map = null;
        String searchSQL = "";
        if (version == 0) {
            searchSQL = "select STNM from STHD where STCD='" + stcd + "'";
        } else {
            searchSQL = "select STNM from HY_STSC_A where STCD='" + stcd + "'";
        }
        try {
            map = jdbcTemplate.queryForMap(searchSQL);
        } catch (Exception e) {
            return "";
        }
        return map.get("STNM");
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
            int version,boolean isTurnChar,int dbtype) {

        expSuccessModel = expModel;
        String[] tables = new String[expModel.size()];
        String stscStr = "";
        String stnameStr = "";
        if (!selectedSnameModel.isEmpty()) {

            for (int i = 0; i < selectedSnameModel.size(); i++) {
                //��������Ϊ���룫���ƣ������õ�����
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
        /**
         * ����վ����������֯,��ֹSQL
         * ��parameters����
         */
        rpareStsc = makeStcdSqlCol(stscStr,200,false);//������֯��Ĳ�վ����
        // ��������
        int i = 0;
        for (String table : tables) {
            for(int timer=0;timer<3000;timer++){boolean temflg = true;}
            i++;
//            Long sdate = System.currentTimeMillis();
            boolean flg = false;
            if (expType == 0) {
                ((DefaultListModel) (logList.getModel())).addElement("���ڷ�������" + getTabCnnm(jt2, table) + "��_" + table + "������ ......");
                flg = createExcelTable(table, rpareStsc, saveDir, logList, expType,isTurnChar);
            } else {
                flg = copyTable(table, stscStr, saveDir, logList, expType, version,isTurnChar,dbtype);
            }
            if (flg) {
                outputInfoExcel(table, logList, selectedSnameModel, stscStr);
            }
            //������������
            insertDataIndexTable(table, stscStr, stnameStr, logList, version, expType, saveDir,isTurnChar,dbtype);
            if (i == tables.length) {
                ((DefaultListModel) (logList.getModel())).addElement("�������ɵ������� ......");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(DBTool.class.getName()).log(Level.SEVERE, null, ex);
                outputError("", "===process=Thread=",ex.getMessage());
            }
        }
        //���浼������
        if(stscStr.split(",").length>450){
            ExcelService.writeReportHtmlHead(saveDir,errorTab,dbTool,listTablesModel,stscStr);
            ExcelService.createReportHtmlCutStcd(saveDir, expSuccessModel, errorTab, dbTool, selectedStscModel, selectedSnameModel,
                    listTablesModel, expType, stscStr,dataIndexMap,dataDescMap,resultStscMap,isTurnChar,version);
            ExcelService.writeReportHtmlDetail(saveDir);
        }else{
            ExcelService.createReportHtml2(saveDir, expSuccessModel, errorTab, dbTool, selectedStscModel, selectedSnameModel,
                    listTablesModel, expType, stscStr,dataIndexMap,dataDescMap,resultStscMap,isTurnChar,version);
        }
        
        ((DefaultListModel) (logList.getModel())).addElement("==���������ɹ�������==");
    }
    /**
     * �����������������ɵ�����������ı�ҪԪ��
     * @param table ������TBID
     * @param stcdStr   �Ѿ�ѡ������в�վ
     * @param stnameStr ��ѡ������в�վ����
     * @param logList   ��ӡ���������־
     * @param version   ���ݿ�汾 3.0   or  4.0
     * @param expType   ������ʽ
     * @param saveDir   ����Ŀ¼
     * @param isTurnChar    �Ƿ����ת�룬���sybase
     * @param dbtype    ���ݿ����� oracle��sybase��sqlserver ִ�в�ѯ���ʱ�򣬲�ͬ���ݿ��sql��׼֧�ֲ�ͬ����Ҫ��������sybase���ݿ�
     */
     public void insertDataIndexTable(String table, String stcdStr,
            String stnameStr, JList logList, int version, int expType, String saveDir,boolean isTurnChar,int dbtype) {
        String indexFiled = getIndexFiled(table);
        
        if (!indexFiled.trim().equals("")) {//���ȱ�֤���ű����������������
            outputError("\r\n"+table,"===׼�����ֶ�-"+indexFiled+"-����������","");
            try {
                int row = jt1.queryForInt("select count(*) from " + table.toUpperCase());
                if (row > 0) {
                    Map colmap = null;
                    try {
                        colmap = jt2.queryForMap("select FILDTPL from INDEX_DESC where upper(TBENNM)='" + table.toUpperCase() + "'");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    String fltp = colmap.get("FILDTPL").toString().trim().toUpperCase();
                    String searchSQL = "";
                    String resultDesc = "";
                    /**
                     * ��������������д���ļ�
                     * dataindex.txt
                     */
                    try {
                        if (fltp.trim().equals("T")) {
                            searchSQL = "SELECT DISTINCT STCD,DATEPART (yyyy," + indexFiled.toUpperCase() + ") as YEARS,COUNT(*) AS TOTAL FROM " + table.toUpperCase()
                                    + " GROUP BY STCD,DATEPART (yyyy," + indexFiled.toUpperCase() + ")"
                                    + " ORDER BY STCD,YEARS ASC ";
                            if(dbtype==2)
                                searchSQL = "SELECT DISTINCT STCD,DATEPART (yy," + indexFiled.toUpperCase() + ") as YEARS,COUNT(*) AS TOTAL FROM " + table.toUpperCase()
                                    + " GROUP BY STCD,DATEPART (yy," + indexFiled.toUpperCase() + ")"
                                    + " ORDER BY STCD,YEARS ASC ";
                        }else{
                             searchSQL = "SELECT DISTINCT STCD," + indexFiled.toUpperCase() + " as YEARS,COUNT(*) AS TOTAL FROM " + table.toUpperCase()
                                    + " GROUP BY STCD," + indexFiled.toUpperCase()
                                    + " ORDER BY STCD,YEARS ASC ";

                        }
                        outputError("\r\n"+table, "===insertDataIndexTable===searchSQL",searchSQL);
                         List rows = jt1.queryForList(searchSQL);
                         ToOrder order = new ToOrder();
                        List beforList = new ArrayList();
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            beforList.add(order.getTem(map.get("STCD").toString(), map.get("YEARS").toString(), map.get("TOTAL").toString()));
                        }
                        List afterList = ToOrder.toOrder(beforList);
                        if (expType == 0 || expType == 2) {
                            outPutIndexToFile(table, saveDir, afterList, version,isTurnChar);
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
                        dataIndexMap.put(table, "�ɹ�");
                    }catch (Exception ex) {
                        dataIndexMap.put(table, "����");
                        ex.printStackTrace();
                        outputError("\r\n"+table, "===insertDataIndexTable_new=",ex.getMessage());
                    }
                    /**
                     * ����վ����������֯,��ֹSQL ��parameters����
                     */
                     List stscList = new ArrayList();
                     String stscSQL="";
                     int stcdYear = 0;
                    for(int i=0;i< rpareStsc.length;i++){
                        String stscForSQL = rpareStsc[i];
                        try {
                            /**
                             * ����������
                             */
                            if (fltp.trim().equals("T")) {
                                resultDesc= "SELECT SUM(A.INDEXY) AS RESU FROM (SELECT STCD,COUNT("
                                        + "DISTINCT (DATEPART(yyyy,"+indexFiled.toUpperCase()+"))) "
                                        +"AS INDEXY FROM "+table.toUpperCase()+" "+stscForSQL+" GROUP BY STCD) AS A";

                                stscSQL =  "SELECT TAB.STCD,min(TAB.YEARS) as MINY,max(TAB.YEARS) AS MAXY,sum(TAB.TOTAL) AS ALLSUM FROM "
                                            +"(SELECT STCD,DATEPART (yyyy,"+indexFiled.toUpperCase()+") as YEARS,"
                                            +" COUNT(*) AS TOTAL FROM "+table.toUpperCase()+" "+stscForSQL
                                            + " GROUP BY STCD,DATEPART "
                                            +"(yyyy,"+indexFiled.toUpperCase()+") ) TAB GROUP BY TAB.STCD";
                                /**
                                 * ������ݿ�����Ϊsybase
                                 * ��Ϊsybase��֧��:as���Ƕ�ײ�ѯ
                                 */
                                if(dbtype==2){
                                    resultDesc= "SELECT SUM(COUNT("
                                            + "DISTINCT (DATEPART(yy,"+indexFiled.toUpperCase()+")))) "
                                            +"AS RESU FROM "+table.toUpperCase()+" "+stscForSQL+" GROUP BY STCD";
                                    stscSQL = "SELECT STCD,MIN(DATEPART (yy,"+indexFiled.toUpperCase()+")) AS MINY, MAX(DATEPART "
                                                +"(yy,"+indexFiled.toUpperCase()+")) AS MAXY,"
                                                +"COUNT(*) AS ALLSUM FROM "+table.toUpperCase()+" "+stscForSQL
                                                + " GROUP BY STCD";
                                }
                            } else {//��ֵ������
                                //ͳ��վ��
                                resultDesc= "SELECT SUM(A.INDEXY) AS RESU FROM (SELECT STCD,COUNT("
                                        + "DISTINCT "+indexFiled.toUpperCase()+") "
                                        +"AS INDEXY FROM "+table.toUpperCase()+" "+stscForSQL+" GROUP BY STCD) AS A";
                                //ͳ��ÿ��վ�ľ������,��ʼ��-������=��������
                               stscSQL =  "SELECT TAB.STCD,min(TAB.YEARS) as MINY,max(TAB.YEARS) AS MAXY,sum(TAB.TOTAL) AS ALLSUM FROM "
                                            +"(SELECT STCD,"+indexFiled.toUpperCase()+" as YEARS,"
                                            +" COUNT(*) AS TOTAL FROM "+table.toUpperCase()+" "+stscForSQL
                                            + " GROUP BY STCD,"+indexFiled.toUpperCase()+" "
                                            +" ) TAB GROUP BY TAB.STCD";
                               /**
                                 * ������ݿ�����Ϊsybase
                                 * ��Ϊsybase��֧��:as���Ƕ�ײ�ѯ
                                 */
                                if(dbtype==2){
                                    resultDesc= "SELECT SUM(COUNT("
                                            + "DISTINCT "+indexFiled.toUpperCase()+")) "
                                            +"AS RESU FROM "+table.toUpperCase()+" "+stscSQL+" GROUP BY STCD";
                                    stscSQL = "SELECT STCD,MIN("+indexFiled.toUpperCase()+") AS MINY, MAX("+indexFiled.toUpperCase()+") AS MAXY,"
                                                +"COUNT(*) AS ALLSUM FROM "+table.toUpperCase()+" "+stscForSQL
                                                + " GROUP BY STCD";
                                }
                            }
                            
                            outputError("\r\n"+table, "===insertDataIndexTable===resultDesc",resultDesc);
                            outputError("\r\n"+table, "===insertDataIndexTable===stscSQL",stscSQL);
                            List rowsStsc = jt1.queryForList(stscSQL);
                           
                            Iterator itStsc = rowsStsc.iterator();
                            while (itStsc.hasNext()) {
                                Map map = (Map) itStsc.next();
                                Map stcdMap = new HashMap();
                                stcdMap.put(map.get("STCD").toString().trim(), map.get("MINY")+","+map.get("MAXY")+","+map.get("ALLSUM"));
                                stscList.add(stcdMap);
                            }
                           
                            Map resuMap = jt1.queryForMap(resultDesc);

                            if(resuMap!=null)
                                if(resuMap.get("RESU")!=null)
                                stcdYear += (Integer)resuMap.get("RESU");
                            if((i+1)==rpareStsc.length){
                                resultStscMap.put(table, stscList);
                                dataDescMap.put(table,stcdYear);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            outputError("\r\n"+table, "===insertDataIndexTable===",ex.getMessage());
                             dataDescMap.put(table,"");
                        }
                    }
                }else{
                    dataIndexMap.put(table, "");
                }
            } catch (Exception ex) {
                dataIndexMap.put(table, "����");
                ex.printStackTrace();
                outputError("\r\n"+table, "===insertDataIndexTable_new=",ex.getMessage());
            }
        }else{
            outputError("\r\n"+table,"===û�������ֶζ��壬�Թ�����","");
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
        selectedSnameModel.removeElement("����������");
        selectedSnameModel.removeElement("����һ����");
        int realCount = getCountToExcel(jt1, table, stsc, isHaveStcdCol(table));
        if (realCount == 0) {
            ((DefaultListModel) (logList.getModel())).addElement("         �����ݱ� ��" + getTabCnnm(jt2, table) + "�� �Ŀɵ�������Ϊ�ա�");
        } else {
            ((DefaultListModel) (logList.getModel())).addElement("         �̳ɹ��������� ��" + getTabCnnm(jt2, table) + "�� �����ݣ���������������" + getCount(jt1, table) + "������������" + realCount);
        }
    }
    /**
     * ����ı��ļ�
     * @param table ������
     * @param stsc  ��վ����
     * @param saveDir ���Ŀ¼
     * @param logList ������Ϣ
     * @param expType ��������
     * @param isTurnChar �Ƿ����ת���־�����sybase12��ǰ�İ汾��Ҫת��
     * @return
     */
    public boolean createExcelTable(final String table, String[] resultSQL, String saveDir, final JList logList, int expType,final boolean isTurnChar) {
        String searChsql = "";
        String countChsql = "";
        
        final String tablename = getTabCnnm(jt2, table);
            if (isHaveStcdCol(table)) {
                int filecount=0;
                for(String stscSQL: resultSQL){
                    try{
                        searChsql = "select * from " + table.toUpperCase() + stscSQL.toUpperCase();
                        countChsql = "select count(*) from " + table.toUpperCase() + stscSQL.toUpperCase();
                        final int totalRecords = jt1.queryForInt(countChsql);
                        if(filecount==0 && totalRecords>0)//ֻ��ӡ���һ��
                            ((DefaultListModel) (logList.getModel())).addElement("         ������д���ļ���" + saveDir + "\\excel\\" + tablename + ".txt��,��ȴ�...");
                        if(totalRecords>0)
                            writeToFile(table,saveDir,countChsql,searChsql,totalRecords,expType,isTurnChar,tablename,filecount);
                       filecount++;
                    }catch(Exception e){
                        e.printStackTrace();
                        if(filecount==0){//ֻ��ӡ���һ��
                            expSuccessModel.removeElement(getTabCnnm(jt2, table));
                            if ("".trim().equals(errorTab)) {
                                errorTab = getTabCnnm(jt2, table);
                            } else {
                                errorTab += "," + getTabCnnm(jt2, table);
                            }
                            outputError("\r\n"+table, "===createExcelTable=",e.getMessage());
                            ((DefaultListModel) (logList.getModel())).addElement("         �����ݷ��������쳣���޷�ִ�е�����");
                        }
                        return false;
                    }
                }
            } else {
                try{
                    searChsql = "select * from " + table.toUpperCase();
                    countChsql = "select count(*) from " + table.toUpperCase();
                    final int totalRecords = jt1.queryForInt(countChsql);
                    if(totalRecords>0){
                        ((DefaultListModel) (logList.getModel())).addElement("         ������д���ļ���" + saveDir + "\\excel\\" + tablename + ".txt��,��ȴ�...");
                        writeToFile(table,saveDir,countChsql,searChsql,totalRecords,expType,isTurnChar,tablename,0);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    expSuccessModel.removeElement(getTabCnnm(jt2, table));
                    if ("".trim().equals(errorTab)) {
                        errorTab = getTabCnnm(jt2, table);
                    } else {
                        errorTab += "," + getTabCnnm(jt2, table);
                    }
                    outputError("\r\n"+table, "===createExcelTable=",e.getMessage());
                    ((DefaultListModel) (logList.getModel())).addElement("         �����ݷ��������쳣���޷�ִ�е�����");
                    return false;
                }
            }
            outputError("\r\n"+table,"createExcelTable=searChsql==countChsql:",searChsql);
        return true;
    }
    /**
      * д�����
     * @param table
     * @param saveDir
     * @param countChsql
     * @param searChsql
     * @param totalRecords
     * @param expType
     * @param isTurnChar
     * @param tablename
     */
    public void writeToFile(final String table, String saveDir, String countChsql,
           String searChsql,final int totalRecords,
            int expType,final boolean isTurnChar,
            final String tablename,final int filecount){
         try {
                //�ܼ�¼��
                final String path = saveDir;
                final File thefile = new File(path + "\\excel\\" + tablename +".txt");
                OutputStreamWriter opsw = null;
                final boolean newFlag = thefile.exists();
//                if(thefile.exists())newFlag=true;
                if(newFlag)
                    opsw = new OutputStreamWriter(new FileOutputStream(path + "\\excel\\" + tablename +".txt",true),"GBK");
                else
                    opsw = new OutputStreamWriter(new FileOutputStream(path + "\\excel\\" + tablename +".txt"),"GBK");
                if(totalRecords>0){
                    final OutputStreamWriter fw = opsw;
                    jt1.query(searChsql,  new RowMapper() {
                        int k=0;
                        public Object mapRow(final ResultSet rs, int rowNum) throws SQLException {
                            StringBuffer fields = new StringBuffer("");
                            ResultSetMetaData meta = rs.getMetaData();
                            int cols = meta.getColumnCount();
                            if(k==0){
                                try{
                                  if(!newFlag){
                                        for (int i = 1; i <=cols; i++) {
                                            if (fields.toString().trim().equals("")) {
                                                fields = new StringBuffer(meta.getColumnName(i));
                                            } else {
                                                fields = new StringBuffer(fields + "\t" + meta.getColumnName(i));
                                            }
                                        }
                                    fw.write(fields.toString() + "\r\n");
                                    fields=new StringBuffer("");
                                }
                                for (int i = 1; i <= cols; i++) {
                                     Object obj = rs.getObject(i);
                                     obj=obj==null?"null":obj;
                                    if (fields.toString().trim().equals("")) {
                                        if(isTurnChar)
                                            fields = new StringBuffer(new String(obj.toString().getBytes("ISO-8859-1"),"GBK"));
                                        else
                                            fields = new StringBuffer(obj.toString());

                                    } else {
                                         if(isTurnChar)
                                             fields = new StringBuffer(fields + "\t" + new StringBuffer(new String(obj.toString().getBytes("ISO-8859-1"),"GBK")));
                                         else
                                             fields = new StringBuffer(fields + "\t" + obj.toString());
                                    }
                                }
                                fw.write(fields.toString() + "\r\n");
                                fields=new StringBuffer("");
                                }catch(Exception ex){ex.printStackTrace();}
                            }else{
                                try {
                                for (int i = 1; i <= cols; i++) {
                                     Object obj = rs.getObject(i);
                                     obj=obj==null?"null":obj;
                                    if (fields.toString().trim().equals("")) {
                                        if(isTurnChar)
                                            fields = new StringBuffer(new String(obj.toString().getBytes("ISO-8859-1"),"GBK"));
                                        else
                                            fields = new StringBuffer(obj.toString());
                                    } else {
                                         if(isTurnChar)
                                            fields = new StringBuffer(fields + "\t" +new String(obj.toString().getBytes("ISO-8859-1"),"GBK"));
                                        else
                                            fields = new StringBuffer(fields + "\t" + obj.toString());
                                    }
                                }
                                fw.write(fields.toString() + "\r\n");
                                fields=new StringBuffer("");
                            } catch (IOException ex) {
                                 ex.printStackTrace();
                                 outputError("\r\n"+table,"createExcelTable=error:",ex.getMessage());
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
            }
    }
    /**
     * ��������Ϣд���ļ�����������ļ�������׷��
     * @param table
     * @param saveDir
     * @param afterList
     * @param version
     * @param isTurnChar
     */
    public void outPutIndexToFile(String table, String saveDir, List afterList, int version,boolean isTurnChar) {
        try {
            File files = new File(saveDir + "\\excel\\dataIndex.txt");
            OutputStreamWriter  fw = null;
            if (files.exists()) {
                fw = new OutputStreamWriter (new FileOutputStream(saveDir + "\\excel\\dataIndex.txt", true),"GB2312");
            } else {
                fw = new OutputStreamWriter (new FileOutputStream(saveDir + "\\excel\\dataIndex.txt"),"GB2312");
                fw.write("����\t������\t��վ����\t��վ����\t���\t������\r\n");
            }
            for (int i = 0; i < afterList.size(); i++) {
                String[] values = ((String) afterList.get(i).toString()).split(",");
                String str = table + "\t" + getTabCnnm(jt2, table) + "\t";
                for (int k = 0; k < values.length; k++) {
                    if (k == 0) {
                        String stnm = getStscName(jt2, values[0]);
                        if("".trim().equals(stnm)){
                            Object obj = getStscName2(jt1, values[0],version);
                            if(obj==null)
                                stnm="";
                            else
                                stnm = obj.toString();
                            if(isTurnChar)
                                stnm = new String(stnm.getBytes("ISO-8859-1"),"GBK");
                        }
                        str += stnm + "\t" + values[k] + "\t";
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
            outputError("\r\n"+table, "===outPutIndexToFile=",ex.getMessage());
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

    public String[] makeFiledsAndParamets(final String tablename,int dbtype){
        String searchSQL = "";
        if(dbtype==2)
            searchSQL = "SET rowcount 1 SELECT column FROM "+tablename.toUpperCase();
        else
            searchSQL = "SELECT TOP 1 * FROM "+tablename.toUpperCase();
        List rows = jt1.query(searchSQL, new RowMapper() {
                public Object mapRow(final ResultSet rs, int rowNum) throws SQLException {
                    String result[] = new String[2];
                    String fields="";
                    String params="";
                    ResultSetMetaData meta = rs.getMetaData();
                    final int cols = meta.getColumnCount();
                    for (int i = 0; i < cols; i++) {
                        String colname = meta.getColumnName(i + 1);
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
    public String getFileds(final String tablename,DBTool dbTool,int type,int dbType){
        JdbcTemplate jt = null;
        if(type==0)
            jt = dbTool.getJt1();//Դ
        else
            jt = dbTool.getJt2();//���
        List rows = null;
        String searSQL = "select top 1 * from "+tablename.toUpperCase();
        if(dbType==2)
            searSQL = "set rowcount 1 select * from "+tablename.toUpperCase();
        try{
            rows = jt.query(searSQL, new RowMapper() {
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
            JOptionPane.showMessageDialog(null, "��ȷ�����ݱ�["+getTabCnnm(jt2,tablename)+"_"+tablename+"]�Ƿ����", "����", 0);
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
