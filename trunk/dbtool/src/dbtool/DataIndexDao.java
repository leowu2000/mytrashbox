/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author User
 */
public class DataIndexDao {

    public static List<DataIndexBean> getDataIndexBean(DefaultListModel stscmodel, DBTool dbTool) {
        List dataindexBean = new ArrayList();
        
        JdbcTemplate jtm_source = dbTool.getJt1();
        JdbcTemplate jtm_Target = dbTool.getJt2();
        if (!stscmodel.isEmpty()) {
            
            for (int i = 0; i < stscmodel.size(); i++) {
                String[] year = new String[]{"0", "0"};
                DataIndexBean bean = new DataIndexBean();
                bean.setSTCD(stscmodel.get(i).toString());
                //水位、流量、含沙量、输沙率
                for (int k = 0; k < Consts.SLS_tables.length; k++) {
                    int count = jtm_source.queryForInt("select count(*) from " + Consts.SLS_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                    String datecol = "";
                    Map colmap = null;
                    try {
                        colmap = jtm_Target.queryForMap("select top 1 flid from HY_DBFP_J where tbid='" + Consts.SLS_tables[k] + "' and FLDTPL='T' ");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    if (colmap != null) {
                        datecol = colmap.get("flid").toString().trim();
                        List rows = jtm_source.queryForList("SELECT MIN(" + datecol + ") AS minyear, MAX(" + datecol + ") AS maxyear from " + Consts.SLS_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                String val1 = map.get("minyear").toString().substring(0, 4);
                                String val2=map.get("maxyear").toString().substring(0, 4);
                                if ("0".trim().equals(year[0])) {
                                    year[0] = val1;
                                } else {
                                    if (new Integer(year[0]) > new Integer(val1)) {
                                        year[0] = val1;
                                    }
                                }
                                if ("".trim().equals(year[1])) {
                                    year[0] = val2;
                                } else {
                                    if (new Integer(year[1]) < new Integer(val2)) {
                                        year[1] = val2;
                                    }
                                }
                            }
                        }
                    }
                    if (count > 0) {
                        bean.setSLS(count);
//                        break;
                    }
                }
                //降水
                for (int k = 0; k < Consts.JS_tables.length; k++) {
                    int count = jtm_source.queryForInt("select count(*) from " + Consts.JS_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                    String datecol = "";

                    Map colmap = null;
                    try {
                        colmap = jtm_Target.queryForMap("select top 1 flid from HY_DBFP_J where tbid='" + Consts.JS_tables[k] + "' and FLDTPL='T' ");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }

                    if (colmap != null) {
                        datecol = colmap.get("flid").toString().trim();
                        List rows = jtm_source.queryForList("SELECT MIN(" + datecol + ") AS minyear, MAX(" + datecol + ") AS maxyear  from " + Consts.JS_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            
                            if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                String val1 = map.get("minyear").toString().substring(0, 4);
                                String val2=map.get("maxyear").toString().substring(0, 4);
                                if ("0".trim().equals(year[0])) {
                                    year[0] = val1;
                                } else {
                                    if (new Integer(year[0]) > new Integer(val1)) {
                                        year[0] = val1;
                                    }
                                }
                                if ("".trim().equals(year[1])) {
                                    year[0] = val2;
                                } else {
                                    if (new Integer(year[1]) < new Integer(val2)) {
                                        year[1] = val2;
                                    }
                                }
                            }
                        }
                    }
                    if (count > 0) {
                        bean.setJS(count);
//                        break;
                    }
                }
                //"水温"
                for (int k = 0; k < Consts.SW_tables.length; k++) {
                    int count = jtm_source.queryForInt("select count(*) from " + Consts.SW_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                    String datecol = "";
                    Map colmap = null;
                    try {
                        colmap = jtm_Target.queryForMap("select top 1 flid from HY_DBFP_J where tbid='" + Consts.SW_tables[k] + "' and FLDTPL='T' ");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    if (colmap != null) {
                        datecol = colmap.get("flid").toString().trim();
                        List rows = jtm_source.queryForList("SELECT MIN(" + datecol + ") AS minyear, MAX(" + datecol + ") AS maxyear from " + Consts.SW_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                if ("0".trim().equals(year[0])) {
                                    year[0] = map.get("minyear").toString().substring(0, 4);
                                } else {
                                    if (new Integer(year[0]) > new Integer(map.get("minyear").toString().substring(0, 4))) {
                                        year[0] = map.get("minyear").toString().substring(0, 4);
                                    }
                                }
                                if ("".trim().equals(year[1])) {
                                    year[0] = map.get("maxyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[1]) < new Integer(map.get("maxyear").toString().substring(0,4))) {
                                        year[1] = map.get("maxyear").toString().substring(0, 4);
                                    }
                                }
                            }
                        }
                    }
                    if (count > 0) {
                        bean.setSW(count);
//                        break;
                    }
                }
                //蒸发
                for (int k = 0; k < Consts.ZF_tables.length; k++) {
                    int count = jtm_source.queryForInt("select count(*) from " + Consts.ZF_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                    String datecol = "";
                    Map colmap = null;
                    try {
                        colmap = jtm_Target.queryForMap("select top 1 flid from HY_DBFP_J where tbid='" + Consts.ZF_tables[k] + "' and FLDTPL='T' ");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    if (colmap != null) {
                        datecol = colmap.get("flid").toString().trim();
                        List rows = jtm_source.queryForList("SELECT MIN(" + datecol + ") AS minyear, MAX(" + datecol + ") AS maxyear from " + Consts.ZF_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                if ("0".trim().equals(year[0])) {
                                    year[0] = map.get("minyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[0]) > new Integer(map.get("minyear").toString().substring(0,4))) {
                                        year[0] = map.get("minyear").toString().substring(0,4);
                                    }
                                }
                                if ("".trim().equals(year[1])) {
                                    year[0] = map.get("maxyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[1]) < new Integer(map.get("maxyear").toString().substring(0,4))) {
                                        year[1] = map.get("maxyear").toString().substring(0,4);
                                    }
                                }
                            }
                        }
                    }
                    if (count > 0) {
                        bean.setZF(count);
//                        break;
                    }
                }
                // 冰凌
                for (int k = 0; k < Consts.BL_tables.length; k++) {
                    int count = jtm_source.queryForInt("select count(*) from " + Consts.BL_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                    String datecol = "";
                    Map colmap = null;
                    try {
                        colmap = jtm_Target.queryForMap("select top 1 flid from HY_DBFP_J where tbid='" + Consts.BL_tables[k] + "' and FLDTPL='T' ");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    if (colmap != null) {
                        datecol = colmap.get("flid").toString().trim();
                        List rows = jtm_source.queryForList("SELECT MIN(" + datecol + ") AS minyear, MAX(" + datecol + ") AS maxyear from " + Consts.BL_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                if ("0".trim().equals(year[0])) {
                                    year[0] = map.get("minyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[0]) > new Integer(map.get("minyear").toString().substring(0,4))) {
                                        year[0] = map.get("minyear").toString().substring(0,4);
                                    }
                                }
                                if ("".trim().equals(year[1])) {
                                    year[0] = map.get("maxyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[1]) < new Integer(map.get("maxyear").toString().substring(0,4))) {
                                        year[1] = map.get("maxyear").toString().substring(0,4);
                                    }
                                }
                            }
                        }
                    }
                    if (count > 0) {
                        bean.setBL(count);
//                        break;
                    }
                }
                //潮位、潮流量
                for (int k = 0; k < Consts.CW_tables.length; k++) {
                    int count = jtm_source.queryForInt("select count(*) from " + Consts.CW_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                    String datecol = "";
                    Map colmap = null;
                    try {
                        colmap = jtm_Target.queryForMap("select top 1 flid from HY_DBFP_J where tbid='" + Consts.CW_tables[k] + "' and FLDTPL='T' ");
                    } catch (EmptyResultDataAccessException e) {
                        colmap = null;
                    }
                    if (colmap != null) {
                        datecol = colmap.get("flid").toString().trim();
                        List rows = jtm_source.queryForList("SELECT MIN(" + datecol + ") AS minyear, MAX(" + datecol + ") AS maxyear from " + Consts.CW_tables[k] + " where stcd='" + stscmodel.get(i).toString() + "'");
                        Iterator it = rows.iterator();
                        while (it.hasNext()) {
                            Map map = (Map) it.next();
                            if (map.get("minyear") != null && map.get("minyear").toString().length() > 0) {
                                if ("0".trim().equals(year[0])) {
                                    year[0] = map.get("minyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[0]) > new Integer(map.get("minyear").toString().substring(0,4))) {
                                        year[0] = map.get("minyear").toString().substring(0,4);
                                    }
                                }
                                if ("".trim().equals(year[1])) {
                                    year[0] = map.get("maxyear").toString().substring(0,4);
                                } else {
                                    if (new Integer(year[1]) < new Integer(map.get("maxyear").toString().substring(0,4))) {
                                        year[1] = map.get("maxyear").toString().substring(0,4);
                                    }
                                }
                            }
                        }
                    }
                    if (count > 0) {
                        bean.setCW(count);
//                        break;
                    }
                }
                //年份
                if("0".trim().equals(year[0]))
                    bean.setQZYEAR("");
                else
                    bean.setQZYEAR(year[0] + "-" + year[1]);
                dataindexBean.add(bean);
            }
        }
        return dataindexBean;
    }
}
