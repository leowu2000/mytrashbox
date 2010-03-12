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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class HY_DBTP_JDao {

    public static List<HY_DBTP_JBean> getAllTabs(DBTool dbTool) {
        List<HY_DBTP_JBean> tpBeanList = new ArrayList<HY_DBTP_JBean>();
        JdbcTemplate jt_source = dbTool.getJt2();
        List rows = jt_source.queryForList("select * from HY_DBTP_J where TBNO <>'999'");
        Iterator it = rows.iterator();
        while (it.hasNext()) {
            Map userMap = (Map) it.next();
            tpBeanList.add(HY_DBTP_JBean.getBeanFromMap(userMap));
        }
        return tpBeanList;
    }

    public static String getTabid(String tbname, DBTool dbTool) {
        String tabid = "";
        JdbcTemplate jt_source = dbTool.getJt2();
        Map map = jt_source.queryForMap("select tbid from HY_DBTP_J where tbcnnm='" + tbname + "'");
        if (map != null) {
            tabid = map.get("tbid").toString().trim();
        }
        return tabid;
    }
    public static String getDatevVlues(String stcd,String tables,String col,boolean flag, DBTool dbTool) {
        String values = "";
        String sql = "";
        JdbcTemplate jt_source = dbTool.getJt2();
        if(flag)
            sql = "select max("+col+") as col from "+tables+" where stcd='"+stcd+"'";
        else
            sql = "select min("+col+") as col from "+tables+" where stcd='"+stcd+"'";
        Map map = jt_source.queryForMap(sql);
        if (map != null) {
            if(map.get("col")!=null)
                values = map.get("col").toString().trim();
        }
        return values;
    }
    public static String getStscName(String stcd, DBTool dbTool) {
        String tabid = "";
        JdbcTemplate jt_source = dbTool.getJt1();
        Map map = jt_source.queryForMap("select stnm from HY_STSC_A where stcd='" + stcd + "'");
        if (map != null) {
            tabid = map.get("stnm").toString().trim();
        }
        return tabid;
    }
}
