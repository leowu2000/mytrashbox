/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

/**
 *
 * @author wzhang
 */
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionPool {

    public static Connection getConnection() {
        Connection conn = null;
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("c://config.properties"));
            Class.forName(p.getProperty("source.datasource.driverClass"));
            String url = p.getProperty("source.datasource.jdbcUrl");
            conn = DriverManager.getConnection(url, p.getProperty("source.datasource.username"),
                    p.getProperty("source.datasource.password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getTargetConnection() {
        Connection conn = null;
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("c://config.properties"));
            Class.forName(p.getProperty("target.datasource.driverClass"));
            String url = p.getProperty("target.datasource.jdbcUrl");
            conn = DriverManager.getConnection(url, p.getProperty("target.datasource.username"),
                    p.getProperty("target.datasource.password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            Class.forName("com.sybase.jdbc3.jdbc.SybDriver").newInstance();
//            String url = "jdbc:sybase:Tds:192.168.2.19:5000/SWDB";
//            Properties sysProps = System.getProperties();
//            sysProps.put("user", "sa");
//            sysProps.put("password", "");
//            Connection conn = DriverManager.getConnection(url, sysProps);
//            Class.forName("oracle.jdbc.driver.OracleDriver"); // ORACLE
//            String url = "jdbc:oracle:thin:@192.168.2.19:1521:oracl";
//            Connection conn = DriverManager
//                            .getConnection(url, "nyb", "nyb");
//            Class.forName("net.sourceforge.jtds.jdbc.Driver"); // ORACLE
            String url = "jdbc:sybase:Tds:192.168.2.253:5000/12313213?charset=cp850";
            Connection conn = DriverManager
                            .getConnection(url, "sa", "");
            if(conn!=null)
                System.out.println("连接成功");
//            Statement stmt = conn.createStatement();
//            String sSQL = " USE HYDB";
//            stmt.execute(sSQL);
//            sSQL="select * from STHD";
//            ResultSet rs = stmt.executeQuery(sSQL);
//            while (rs.next()) {
//                System.out.println(rs.getString("STNM")+"===="+new String(rs.getString("STNM").getBytes("ISO-8859-1"),"GBK"));
//            }
//            while (rs.next()) {
//                    System.out.println("\"INSERT INTO HY_DBFP_J(TBID,FLID,FLDCNNM,FLDENNM,FLDTPL)VALUES('"+rs.getString("tablename")+"','"+rs.getString("fielde")+"','"+rs.getString("fieldc")+"','"+rs.getString("fielde")+"',"+rs.getString("format")+")\",");
//            }

//            Connection con;
//             Statement statement;
//             ResultSet rs;

//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//            String dburl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=f:\\hpjdb.mdb";
//             String Driver="jdbc:odbc:driver={Microsoft Visual FoxPro Driver};SourceType=DBF;SourceDB=D:\\Microsoft Visual Studio\\Vfp98";
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
//            String dburl = "jdbc:odbc:hydb";
//            String user="";
//            String password="";
//
//            con = DriverManager.getConnection(Driver,user,password);
//            statement=con.createStatement();
//            rs = statement.executeQuery("SELECT * from STHD");
//            while (rs.next()) {
//                String name = rs.getString("stnm");
//			System.out.println(name);
//		}

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

