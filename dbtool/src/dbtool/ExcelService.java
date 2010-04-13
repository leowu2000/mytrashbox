package dbtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.jdbc.core.JdbcTemplate;

public class ExcelService {
    private static int cuntNumbert = 450;
    @SuppressWarnings({"unchecked", "deprecation"})
    public static void createXls(String reportName, String savePath, String colAndName, List rowList) {
        if (colAndName != null && !"".trim().equals(colAndName) && reportName != null && !"".trim().equals(reportName)) {
            String codeStr = colAndName.split(";")[0];
            String nameStr = colAndName.split(";")[1];

            String codeArr[] = codeStr.split(",");
            String nameArr[] = nameStr.split(",");

            String filename = savePath + "\\excel\\" + reportName + ".xls";

            HSSFWorkbook workbook = new HSSFWorkbook();
            // 在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1
            HSSFSheet sheet = workbook.createSheet();
            // 创建字体
            HSSFFont font = workbook.createFont();
            // 把字体颜色设 置
            font.setColor(HSSFColor.BLUE.index);
            // 把字体设置为粗体
            // 创建格式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            // 把创建的字体付加于格式
            cellStyle.setLocked(true);// 锁定单元格
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);// 单元格根据内容自动调整
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //工作表中创建一行
            HSSFRow row_Title = sheet.createRow(0);
            row_Title.setHeight((short) 600);
            HSSFCell cell_title = row_Title.createCell((short) 0);
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
            HSSFFont titleFont = workbook.createFont(); // 创建字体格式
            titleFont.setColor(HSSFColor.BLUE.index);

            titleFont.setFontHeight((short) 400); // 设置字体大小
            titleStyle.setFont(titleFont);

            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell_title.setCellStyle(titleStyle);
            cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell_title.setCellValue(reportName);
            sheet.addMergedRegion(new Region(0, (short) 0, 0,
                    (short) (codeArr.length)));

            //工作表中创建一行
            HSSFRow row_Code = sheet.createRow(1);
            row_Code.setHeight((short) 0);

            HSSFRow row_Name = sheet.createRow(2);
            row_Code.setHeight((short) 0);

            for (int i = 0; i < codeArr.length; i++) {
                // 在一行中创建一个表格 row_ENM
                HSSFCell cell_code = row_Code.createCell((short) i);
                HSSFCell cell_cnnm = row_Name.createCell((short) i);
                // 把上面的格式付加于一个单元格
                cell_cnnm.setCellStyle(cellStyle);
                cell_code.setCellStyle(cellStyle);
                // 设置此单元格中存入的是字符串
                cell_cnnm.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // 设置编码 这个是用来处理中文问题的
                cell_cnnm.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // 向此单元格中放入值
                cell_cnnm.setCellValue(new HSSFRichTextString(nameArr[i]));
                cell_code.setCellValue(new HSSFRichTextString(codeArr[i]));
            }
            int beginRow = 3;
            Iterator ita = rowList.iterator();
            while (ita.hasNext()) {
                HSSFRow row_Val = sheet.createRow(beginRow);

                Map objMap = (Map) ita.next();
                for (int i = 0; i < codeArr.length; i++) {
                    HSSFCell cell_val = row_Val.createCell((short) i);
//                    cell_val.setCellStyle(cellStyle);
                    cell_val.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell_val.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell_val.setCellValue(new HSSFRichTextString(objMap.get(codeArr[i]) == null ? "" : objMap.get(codeArr[i]).toString()));
                }
                beginRow++;
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(filename);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                        outputStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    public static void saveExportReport(String savePath, JList logList) {
        DefaultListModel model = (DefaultListModel) logList.getModel();
        String filename = savePath + "\\excel\\导出报告.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        // 在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1
        HSSFSheet sheet = workbook.createSheet();
        // 创建字体
        HSSFFont font = workbook.createFont();
        // 把字体颜色设 置
        font.setColor(HSSFColor.BLUE.index);
        // 把字体设置为粗体
        // 创建格式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 把创建的字体付加于格式
        cellStyle.setLocked(true);// 锁定单元格
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);// 单元格根据内容自动调整
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        //工作表中创建一行
        HSSFRow row_Title = sheet.createRow(0);
        row_Title.setHeight((short) 600);
        HSSFCell cell_title = row_Title.createCell((short) 0);
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
        HSSFFont titleFont = workbook.createFont(); // 创建字体格式
        titleFont.setColor(HSSFColor.BLUE.index);

        titleFont.setFontHeight((short) 400); // 设置字体大小
        titleStyle.setFont(titleFont);

        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        cell_title.setCellStyle(titleStyle);
        cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell_title.setCellValue("数据导出报告");
        sheet.addMergedRegion(new Region(0, (short) 0, 0,
                (short) (8)));
        if (!model.isEmpty()) {
            int beginRow = 1;
            for (int i = 0; i < model.size(); i++) {
                //工作表中创建一行
                HSSFRow row_val = sheet.createRow(beginRow);
                // 在一行中创建一个表格 row_ENM
                HSSFCell cell_code = row_val.createCell((short) 0);
                // 把上面的格式付加于一个单元格
                cell_code.setCellStyle(cellStyle);
                // 设置此单元格中存入的是字符串
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // 设置编码 这个是用来处理中文问题的
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // 向此单元格中放入值
                cell_code.setCellValue(new HSSFRichTextString(model.getElementAt(i).toString()));
                sheet.addMergedRegion(new Region(beginRow, (short) 0, beginRow,
                        (short) (8)));
                beginRow++;
            }
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static void createReportHtml_Old(String savePath,
            DefaultListModel expSuccessModel,
            String errorTab,
            DBTool dbTool,
            DefaultListModel selectedStscModel,
            DefaultListModel selectedSnameModel,
            DefaultListModel listTabModel,int type,
            String stscstr,Map dataIndexMap,
            Map dataDescMap,Map resultStscMap) {
        JdbcTemplate jt1 = dbTool.getJt1();
        JdbcTemplate jt2 = dbTool.getJt2();
        String[] tables = null;
        if (!expSuccessModel.isEmpty()) {
            tables = new String[expSuccessModel.size()];
            for (int i = 0; i < expSuccessModel.size(); i++) {
                tables[i] = HY_DBTP_JDao.getTabid(expSuccessModel.get(i).toString(), dbTool);
            }
        }
        listTabModel.removeElement("数据索引表");
        String desFile = savePath + "\\excel\\Report.html";
        StringBuffer strContent_head = new StringBuffer("<html><head><title>REPORT</title><meta http-equiv='Content-Type' content='text/html; charset=gbk'></head>");
        strContent_head.append("<style type='text/css'>");
        strContent_head.append(".title {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;}"
                              +".title2 {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;}</style><body>");
        StringBuffer strContent_table = new StringBuffer("<table width='85%' align='center' border='0' 'cellspacing='1' bgcolor='#CCCCCC'>");
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 14pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;' colspan='6'>数据导出报告</td></tr>");
        if (!listTabModel.isEmpty()) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='6'>以下为未选择报表</td></tr>");
            for (int i = 0; i < listTabModel.size(); i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td >" + HY_DBTP_JDao.getTabid(listTabModel.get(i).toString(), dbTool) + "</td><td colspan='5'>" + listTabModel.get(i) + "</td></tr>");
            }
        }
        if (errorTab != null && errorTab.length() > 0) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='6'>以下报表和导出结构标准不一致未能成功导出</td></tr>");
            for (int i = 0; i < errorTab.split(",").length; i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td colspan='3'>" + HY_DBTP_JDao.getTabid(errorTab.split(",")[i],dbTool) + "</td><td colspan='3'>" + errorTab.split(",")[i] + "</td></tr>");
            }
        }
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'>");
        strContent_table.append("<td class='title'>表编号</td>");
        strContent_table.append("<td class='title'>表名称</td>");
//        strContent_table.append("<td class='title'>测站名称</td>");
        strContent_table.append("<td class='title'>源数据条数</td>");
        strContent_table.append("<td class='title'>导出数据条数</td>");
        strContent_table.append("<td class='title'>导出站年</td>");
        strContent_table.append("<td class='title'>生成数据索引</td>");
        strContent_table.append("</tr>");
        if (tables != null && tables.length > 0) {
            for (String table : tables) {
                strContent_table.append("<tr bgcolor='#FFFFFF' height='20'>");
                strContent_table.append("<td>" + table + "</td>");
                strContent_table.append("<td>" + dbTool.getTabCnnm(jt2, table) + "</td>");
                strContent_table.append("<td>" + dbTool.getCount(jt1, table) + "</td>");
                if(type==0)
                    strContent_table.append("<td>" + dbTool.getCountToExcel(jt1, table,stscstr,dbTool.isHaveStcdCol(table)) + "</td>");
                else
                    strContent_table.append("<td>" + dbTool.getCount(jt2, table) + "</td>");
                Object resultdesc = dataDescMap.get(table);
                if (resultdesc==null) resultdesc="";
                strContent_table.append("<td>"+resultdesc+"</td>");
                Object result=dataIndexMap.get(table);
                if (result==null) result="";
                strContent_table.append("<td>" + result+ "</td></tr>");
                List resultList = (List)resultStscMap.get(table);
                if(resultList!=null && resultList.size()>0){
                    strContent_table.append("<tr class='title2'><td colspan='6'>["+dbTool.getTabCnnm(jt2, table)+"]_导出数据详细列表:</tr>");
                    for(int m=0;m<resultList.size();m++){
                        String tempValue[] = resultList.get(m).toString().split(",");
                        strContent_table.append("<tr  bgcolor='#FFFFFF' height='20'><td>&nbsp;</td><td>"
                                            +tempValue[0]+"</td><td>"
                                            +dbTool.getStscName(jt2, tempValue[0])+"</td><td>"
                                            +tempValue[3]+"</td><td colspan=2>"
                                            +tempValue[1]+"-"+tempValue[2]+"</td></tr>");

                    }
                }
            }
        }
        StringBuffer strContent_detail = new StringBuffer("<tr bgcolor='#E8EFFF' height='30'><td colspan=6 aling='center'>如果生成数据索引失败，请对照c盘下的tables.xls文件，确认数据表存在，并且索引字段名称跟您的数据库对应。</td></tr></table></body></html>");
        StringBuffer strContent = new StringBuffer("");
        strContent.append(strContent_head);
        strContent.append(strContent_table);
        strContent.append(strContent_detail);
        File file = new File(desFile);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileWriter resultFile = new FileWriter(file);
            PrintWriter printFile = new PrintWriter(resultFile);

            printFile.println(strContent);
            resultFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createXlsOnly(String reportName, String savePath, List valueDate) {
        Object[] codeArr = null;
        if (reportName != null && !"".trim().equals(reportName)) {

            if (valueDate != null && valueDate.size() > 0) {
                List valueList = (List) valueDate.get(0);
                codeArr = (Object[]) valueList.get(0);

            }

            String filename = savePath + "\\excel\\" + reportName + ".xls";

            HSSFWorkbook workbook = new HSSFWorkbook();
            // 在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1
            HSSFSheet sheet = workbook.createSheet();
            // 创建字体
            HSSFFont font = workbook.createFont();
            // 把字体颜色设 置
            font.setColor(HSSFColor.BLUE.index);
            // 把字体设置为粗体
            // 创建格式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            // 把创建的字体付加于格式
            cellStyle.setLocked(true);// 锁定单元格
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);// 单元格根据内容自动调整
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //工作表中创建一行
            HSSFRow row_Title = sheet.createRow(0);
            row_Title.setHeight((short) 600);
            HSSFCell cell_title = row_Title.createCell((short) 0);
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
            HSSFFont titleFont = workbook.createFont(); // 创建字体格式
            titleFont.setColor(HSSFColor.BLUE.index);

            titleFont.setFontHeight((short) 400); // 设置字体大小
            titleStyle.setFont(titleFont);

            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell_title.setCellStyle(titleStyle);
            cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell_title.setCellValue(reportName);
            sheet.addMergedRegion(new Region(0, (short) 0, 0,
                    (short) (codeArr.length)));

            //工作表中创建一行
            HSSFRow row_Code = sheet.createRow(1);

            for (int i = 0; i < codeArr.length; i++) {
                // 在一行中创建一个表格 row_ENM
                HSSFCell cell_code = row_Code.createCell((short) i);
                // 把上面的格式付加于一个单元格
                cell_code.setCellStyle(cellStyle);
                // 设置此单元格中存入的是字符串
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // 设置编码 这个是用来处理中文问题的
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // 向此单元格中放入值
                cell_code.setCellValue(new HSSFRichTextString(codeArr[i] == null ? "" : codeArr[i].toString()));
            }
            int beginRow = 2;
            for (int i = 0; i < valueDate.size(); i++) {
                HSSFRow row_Val = sheet.createRow(beginRow);
                List hst = (List) valueDate.get(i);
                Object valueArr[] = new Object[codeArr.length];
                if (i == 0) {
                    valueArr = (Object[]) hst.get(1);
                    for (int k = 0; k < valueArr.length; k++) {
                        HSSFCell cell_val = row_Val.createCell((short) k);
                        cell_val.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell_val.setEncoding(HSSFCell.ENCODING_UTF_16);
                        cell_val.setCellValue(new HSSFRichTextString(valueArr[k] == null ? "" : valueArr[k].toString()));
                    }
                } else {
                    valueArr = (Object[]) hst.get(0);
                    for (int k = 0; k < valueArr.length; k++) {
                        HSSFCell cell_val = row_Val.createCell((short) k);
                        cell_val.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell_val.setEncoding(HSSFCell.ENCODING_UTF_16);
                        cell_val.setCellValue(new HSSFRichTextString(valueArr[k] == null ? "" : valueArr[k].toString()));
                    }
                }
                beginRow++;
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(filename);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                        outputStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    public static String[] readStcdFromExcel(String filepath) throws Exception {

        FileInputStream fis = null;
        fis = new FileInputStream(filepath);
        POIFSFileSystem pss = new POIFSFileSystem(fis);
        HSSFWorkbook workbook = new HSSFWorkbook(pss);
        //读取Sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //读取行号
        int rows = sheet.getPhysicalNumberOfRows();

        String[] stcdList = new String[rows - 1];
        for (int i = 1; i < rows; i++) {
            HSSFRow row_Val = sheet.getRow(i);
            HSSFCell cell_stcd = row_Val.getCell((short) 0);
            HSSFCell cell_stnm = row_Val.getCell((short) 1);
            HSSFCell cell_parent = row_Val.getCell((short) 2);
            try {

                stcdList[i - 1] = "INSERT INTO TABLE_STCD(STCD,STNM,PARENT)VALUES('" + getStringCellValue(cell_stcd) + "','" + getStringCellValue(cell_stnm) + "','" + getStringCellValue(cell_parent) + "')";
//                System.out.println(stcdList[i - 1]);
            } catch (Exception e) {
                continue;
            }
        }
        return stcdList;

    }

     public static String[] readIndexMsgFromExcel(String filepath) throws Exception {

        FileInputStream fis = null;
        fis = new FileInputStream(filepath);
        POIFSFileSystem pss = new POIFSFileSystem(fis);
        HSSFWorkbook workbook = new HSSFWorkbook(pss);
        //读取Sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //读取行号
        int rows = sheet.getPhysicalNumberOfRows();

        String[] stcdList = new String[rows - 1];
        for (int i = 1; i < rows; i++) {
            HSSFRow row_Val = sheet.getRow(i);
            HSSFCell cell_tbcnnm = row_Val.getCell((short) 0);
            HSSFCell cell_tbennm = row_Val.getCell((short) 1);
            HSSFCell cell_tbtype = row_Val.getCell((short) 2);
            HSSFCell cell_indexf = row_Val.getCell((short) 3);
            HSSFCell cell_indexflp = row_Val.getCell((short) 4);
            try {
                stcdList[i - 1] = "INSERT INTO INDEX_DESC(TBCNNM,TBENNM,TBTYPE,FILDID,FILDTPL)VALUES('"
                        + getStringCellValue(cell_tbcnnm).trim() + "','"
                        + getStringCellValue(cell_tbennm).trim()  + "','"
                        + getStringCellValue(cell_tbtype).trim()  + "','"
                        + getStringCellValue(cell_indexf).trim()  + "','"
                        +getStringCellValue(cell_indexflp).trim()  + "')";
            } catch (Exception e) {
                continue;
            }
        }
        return stcdList;

    }

    private static String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf((long) cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    public static void Copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 导出并生成excel模版
     * @param dbTool
     */
    public static void createTemplateXls(DBTool dbTool,String savePath){
        List rows = dbTool.jt2.queryForList("SELECT * FROM HY_DBTP_J ");
        Iterator iter = rows.iterator();
        while(iter.hasNext()){
            Map tabMap = (Map)iter.next();
            String reportName = tabMap.get("TBCNNM").toString();
            String reportId = tabMap.get("TBID").toString();
            List rowCol = dbTool.jt2.queryForList("SELECT * FROM HY_DBFP_J WHERE TBID='"+reportId+"'");
//            System.out.println(tabMap.get("TBID")+"--"+reportName);
            String filename = "c:\\template\\" + reportName + ".xls";

            HSSFWorkbook workbook = new HSSFWorkbook();
            // 在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1
            HSSFSheet sheet = workbook.createSheet();
            // 创建字体
            HSSFFont font = workbook.createFont();
            // 把字体颜色设 置
            font.setColor(HSSFColor.BLUE.index);
            // 把字体设置为粗体
            // 创建格式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            // 把创建的字体付加于格式
            cellStyle.setLocked(true);// 锁定单元格
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);// 单元格根据内容自动调整
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //工作表中创建一行
            HSSFRow row_Title = sheet.createRow(0);
            row_Title.setHeight((short) 600);
            HSSFCell cell_title = row_Title.createCell((short) 0);
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
            HSSFFont titleFont = workbook.createFont(); // 创建字体格式
            titleFont.setColor(HSSFColor.BLUE.index);

            titleFont.setFontHeight((short) 400); // 设置字体大小
            titleStyle.setFont(titleFont);

            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell_title.setCellStyle(titleStyle);
            cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell_title.setCellValue(reportName);
            sheet.addMergedRegion(new Region(0, (short) 0, 0,(short) (rowCol.size()-1)));
            HSSFRow row_Code = sheet.createRow(1);
            HSSFRow row_Name = sheet.createRow(2);

            Iterator iterCol = rowCol.iterator();
            int i=0;
            while(iterCol.hasNext()){
                Map colMap = (Map)iterCol.next();
                HSSFCell cell_code = row_Code.createCell((short) i);
                HSSFCell cell_cnnm = row_Name.createCell((short) i);
                // 把上面的格式付加于一个单元格
                cell_cnnm.setCellStyle(cellStyle);
                cell_code.setCellStyle(cellStyle);
                // 设置此单元格中存入的是字符串
                cell_cnnm.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // 设置编码 这个是用来处理中文问题的
                cell_cnnm.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // 向此单元格中放入值
                cell_cnnm.setCellValue(new HSSFRichTextString(colMap.get("FLDCNNM").toString()));
                cell_code.setCellValue(new HSSFRichTextString(colMap.get("FLDENNM").toString()));
                i++;
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(filename);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                        outputStream = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    public static void createReportHtml2(String savePath,
            DefaultListModel expSuccessModel,
            String errorTab,
            DBTool dbTool,
            DefaultListModel selectedStscModel,
            DefaultListModel selectedSnameModel,
            DefaultListModel listTabModel,int type,
            String stscstr,Map dataIndexMap,
            Map dataDescMap,Map resultStscMap,
            boolean isTurnChar,int version) {
        JdbcTemplate jt1 = dbTool.getJt1();
        JdbcTemplate jt2 = dbTool.getJt2();
        String[] tables = null;
        String stsc[] = stscstr.split(",");
        int colNum = stsc.length*2+5;
        if (!expSuccessModel.isEmpty()) {
            tables = new String[expSuccessModel.size()];
            for (int i = 0; i < expSuccessModel.size(); i++) {
                tables[i] = HY_DBTP_JDao.getTabid(expSuccessModel.get(i).toString(), dbTool);
            }
        }
        listTabModel.removeElement("数据索引表");
        String desFile = savePath + "\\excel\\Report.html";
        StringBuffer strContent_head = new StringBuffer("<html><head><title>REPORT</title><meta http-equiv='Content-Type' content='text/html; charset=gbk'></head>");
        strContent_head.append("<style type='text/css'>");
        strContent_head.append(".title {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;}"
                              +".title2 {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;}"
                              +"a {text-decoration: none;color: #484833;}a:hover {text-decoration: underline;color: #6B6B4B;}</style><body scroll=auto>");
        StringBuffer strContent_table = new StringBuffer("<table width='98%' align='center' border='0' 'cellspacing='1' bgcolor='#CCCCCC'>");
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 14pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;' colspan='"+colNum+"'>数据导出报告</td></tr>");
        if (!listTabModel.isEmpty()) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='"+colNum+"'>以下为未选择报表</td></tr>");
            for (int i = 0; i < listTabModel.size(); i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td colspan='2'>" + HY_DBTP_JDao.getTabid(listTabModel.get(i).toString(), dbTool) + "</td><td colspan='"+(colNum-2)+"'>" + listTabModel.get(i) + "</td></tr>");
            }
        }
        if (errorTab != null && errorTab.length() > 0) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='"+colNum+"'>以下报表和导出结构标准不一致未能成功导出</td></tr>");
            for (int i = 0; i < errorTab.split(",").length; i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td colspan='3'>" + HY_DBTP_JDao.getTabid(errorTab.split(",")[i],dbTool) + "</td><td colspan='"+(colNum-3)+"'>" + errorTab.split(",")[i] + "</td></tr>");
            }
        }
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'>");
        strContent_table.append("<td rowspan='2' class='title' nowrap>表名称</td>");
//        strContent_table.append("<td class='title'>测站名称</td>");
        strContent_table.append("<td rowspan='2' class='title' nowrap>源数据条数</td>");
        strContent_table.append("<td rowspan='2' class='title' nowrap>导出条数</td>");
        strContent_table.append("<td rowspan='2' class='title' nowrap> 索  引 </td>");
        for(String stcd:stsc){
            String stnm = dbTool.getStscName(jt2, stcd.replaceAll("'",""));
            if("".trim().equals(stnm)){
                        Object obj = dbTool.getStscName2(jt1, stcd.replaceAll("'",""),version);
                        if(obj==null)
                            stnm="";
                        else{
                            if(isTurnChar){
                                try {
                                    stnm = new String(obj.toString().getBytes("ISO-8859-1"),"GBK");
                                } catch (UnsupportedEncodingException ex) {
                                    Logger.getLogger(ExcelService.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else{
                                stnm=obj.toString();
                            }
                        }
                    }
                if("".trim().equals(stnm))
                    stnm=stcd.replaceAll("'","");
            
            strContent_table.append("<td class='title' colspan='2'>"+stnm+"<br>["+stcd.replaceAll("'","")+"]</br></td>");
        }
        strContent_table.append("<td rowspan='2' class='title' nowrap>合计(站年)</td>");
        strContent_table.append("</tr><tr bgcolor='#E8EFFF' height='20'>");
        for(String stcd:stsc){
            strContent_table.append("<td class='title' nowrap>  年   份 </td>");
            strContent_table.append("<td class='title' nowrap>导出条数</td>");
        }
        strContent_table.append("</tr>");
        if (tables != null && tables.length > 0) {
            for (String table : tables) {
                strContent_table.append("<tr bgcolor='#FFFFFF' height='20'>");
                strContent_table.append("<td nowrap><a href='#' onclick='return false;' title='"+table+"'>"+ dbTool.getTabCnnm(jt2, table) +"</a></td>");
                strContent_table.append("<td>" + dbTool.getCount(jt1, table) + "</td>");
                strContent_table.append("<td>" + dbTool.getCountToExcel(jt1, table,stscstr,dbTool.isHaveStcdCol(table)) + "</td>");
                Object result=dataIndexMap.get(table);
                if (result==null) result="";
                if("失败".trim().equals(result))//getIndexFiled(table)
                    strContent_table.append("<td><a href='#' onclick='return false;' title='请确认表－"+table+"－中存在索引字段:"+dbTool.getIndexFiled(table)+"'>" + result+ "</td>");
                else
                    strContent_table.append("<td>" + result+ "</td>");
                List resultList = (List)resultStscMap.get(table);
                if(resultList!=null && resultList.size()>0){
                    for(int k = 0; k<stsc.length;k++){
                        boolean nullflg = true;
                        for(int n=0;n<resultList.size();n++){
                         Map stscMap = (Map)resultList.get(n);
                         Object stscobj=(Object)stscMap.get(stsc[k].replaceAll("'","").trim());
                         if(stscobj==null){
                            nullflg = false;
                         }else{
                            String stscStr[] = stscobj.toString().split(",");
                            strContent_table.append("<td bgcolor='#FFFFFF'>"+stscStr[0]+"-"+stscStr[1]+"</td>");
                            strContent_table.append("<td bgcolor='#FFFFFF'>"+stscStr[2]+"</td>");
                             nullflg = true;break;
                         }
                        }
                        if(!nullflg){
                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                            nullflg = true;
                        }
                    }
                    Object resultdesc = dataDescMap.get(table);
                    if (resultdesc==null) resultdesc="";
                    strContent_table.append("<td>"+resultdesc+"</td>");
                }
                else{
                    for(int k = 0; k<stsc.length;k++){
                        strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                        strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                    }
                    Object resultdesc = dataDescMap.get(table);
                    if (resultdesc==null) resultdesc="";
                    strContent_table.append("<td>"+resultdesc+"</td>");
                }
                strContent_table.append("</tr>");
            }
        }
        StringBuffer strContent_detail = new StringBuffer("<tr bgcolor='#E8EFFF' height='30'><td colspan='"+colNum+"' align='center'>如果生成数据索引失败，请对照c盘下的tables.xls文件，确认数据表存在，并且索引字段名称跟您的数据库对应。</td></tr></table></body></html>");
        StringBuffer strContent = new StringBuffer("");
        strContent.append(strContent_head);
        strContent.append(strContent_table);
        strContent.append(strContent_detail);
        File file = new File(desFile);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileWriter resultFile = new FileWriter(file);
            PrintWriter printFile = new PrintWriter(resultFile);

            printFile.println(strContent);
            resultFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            String[] stcdList = readStcdFromExcel(null);
            for (String aa : stcdList) {
                System.out.println(aa);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void createReportHtmlCutStcd(String savePath,
            DefaultListModel expSuccessModel,
            String errorTab,
            DBTool dbTool,
            DefaultListModel selectedStscModel,
            DefaultListModel selectedSnameModel,
            DefaultListModel listTabModel,int type,
            String stscstr,Map dataIndexMap,
            Map dataDescMap,Map resultStscMap,
            boolean isTurnChar,int version) {
        JdbcTemplate jt1 = dbTool.getJt1();
        JdbcTemplate jt2 = dbTool.getJt2();
        String[] tables = null;
        String reportStcd[] = dbTool.makeStcdSqlCol(stscstr,cuntNumbert,true);
        

        int tabNameRowspan = reportStcd.length*3;
        if (!expSuccessModel.isEmpty()) {
            tables = new String[expSuccessModel.size()];
            for (int i = 0; i < expSuccessModel.size(); i++) {
                tables[i] = HY_DBTP_JDao.getTabid(expSuccessModel.get(i).toString(), dbTool);
            }
        }
        StringBuffer strContent_table=new StringBuffer("");
        String desFile = savePath + "\\excel\\Report.html";
        listTabModel.removeElement("数据索引表");
        if (tables != null && tables.length > 0) {
                for (String table : tables) {
                   
                    if(reportStcd!=null){
                        for(int kk=0;kk<reportStcd.length;kk++ ){
                            String realStcdToHtml=reportStcd[kk];
                            String stsc[] = realStcdToHtml.split(",");
                                if(kk==0){
                                    strContent_table.append("<tr bgcolor='#FFFFFF' height='20'>");
                                    strContent_table.append("<td rowspan='"+tabNameRowspan+"'><a href='#' onclick='return false;' title='"+table+"'>"+ dbTool.getTabCnnm(jt2, table) +"</a></td>");
                                    strContent_table.append("<td rowspan='"+tabNameRowspan+"'>" + dbTool.getCount(jt1, table) + "</td>");
                                    strContent_table.append("<td rowspan='"+tabNameRowspan+"'>" + dbTool.getCountToExcel(jt1, table,stscstr,dbTool.isHaveStcdCol(table)) + "</td>");
                                    Object result=dataIndexMap.get(table);
                                    if (result==null) result="";
                                    if("失败".trim().equals(result))//getIndexFiled(table)
                                        strContent_table.append("<td rowspan='"+tabNameRowspan+"'><a href='#' onclick='return false;' title='请确认表－"+table+"－中存在索引字段:"+dbTool.getIndexFiled(table)+"'>" + result+ "</td>");
                                    else
                                        strContent_table.append("<td rowspan='"+tabNameRowspan+"'>" + result+ "</td>");
                                    for(String stcd:stsc){
                                        String stnm = dbTool.getStscName(jt2, stcd.replaceAll("'",""));
                                        if("".trim().equals(stnm)){
                                                    Object obj = dbTool.getStscName2(jt1, stcd.replaceAll("'",""),version);
                                                    if(obj==null)
                                                        stnm="";
                                                    else{
                                                        if(isTurnChar){
                                                            try {
                                                                stnm = new String(obj.toString().getBytes("ISO-8859-1"),"GBK");
                                                            } catch (UnsupportedEncodingException ex) {
                                                                Logger.getLogger(ExcelService.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                        }else{
                                                            stnm=obj.toString();
                                                        }
                                                    }
                                                }
                                            if("".trim().equals(stnm))
                                                stnm=stcd.replaceAll("'","");

                                        strContent_table.append("<td class='title' colspan='2'>"+stnm+"<br>["+stcd.replaceAll("'","")+"]</br></td>");
                                    }

                                    Object resultdesc = dataDescMap.get(table);
                                    if (resultdesc==null) resultdesc="";
                                    strContent_table.append("<td rowspan='"+tabNameRowspan+"'>"+resultdesc+"</td>");
                                    strContent_table.append("</tr><tr bgcolor='#E8EFFF' height='20'>");
                                    for(String stcd:stsc){
                                        strContent_table.append("<td class='title' nowrap>  年   份 </td>");
                                        strContent_table.append("<td class='title' nowrap>导出条数</td>");
                                    }
                                    strContent_table.append("</tr>");
                                    strContent_table.append("<tr>");
                                    List resultList = (List)resultStscMap.get(table);
                                    if(resultList!=null && resultList.size()>0){
                                        for(int k = 0; k<stsc.length;k++){
                                            boolean nullflg = true;
                                            for(int n=0;n<resultList.size();n++){
                                             Map stscMap = (Map)resultList.get(n);
                                             Object stscobj=(Object)stscMap.get(stsc[k].replaceAll("'","").trim());
                                             if(stscobj==null){
                                                nullflg = false;
                                             }else{
                                                String stscStr[] = stscobj.toString().split(",");
                                                strContent_table.append("<td bgcolor='#FFFFFF'>"+stscStr[0]+"-"+stscStr[1]+"</td>");
                                                strContent_table.append("<td bgcolor='#FFFFFF'>"+stscStr[2]+"</td>");
                                                 nullflg = true;break;
                                             }
                                            }
                                            if(!nullflg){
                                                strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                                strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                                nullflg = true;
                                            }
                                        }
                                        if(stsc.length<cuntNumbert){
                                            for(int cc=0;cc<cuntNumbert-stsc.length;cc++){
                                                strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                                strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                            }
                                        }
                                    }
                                    else{
                                        for(int k = 0; k<stsc.length;k++){
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                        }
                                        if(reportStcd.length>1){
                                            if(stsc.length<cuntNumbert){
                                                for(int cc=0;cc<cuntNumbert-stsc.length;cc++){
                                                    strContent_table.append("<td class='title' colspan='2'>&nbsp;</td>");
                                                }
                                            }
                                        }
                                        
                                    }
                                    
                                    strContent_table.append("</tr>");
                                }else{
                                strContent_table.append("<tr>");
                               
                                //再次输出测站编码名称
                                for(String stcd:stsc){
                                    String stnm = dbTool.getStscName(jt2, stcd.replaceAll("'",""));
                                    if("".trim().equals(stnm)){
                                                Object obj = dbTool.getStscName2(jt1, stcd.replaceAll("'",""),version);
                                                if(obj==null)
                                                    stnm="";
                                                else{
                                                    if(isTurnChar){
                                                        try {
                                                            stnm = new String(obj.toString().getBytes("ISO-8859-1"),"GBK");
                                                        } catch (UnsupportedEncodingException ex) {
                                                            Logger.getLogger(ExcelService.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                    }else{
                                                        stnm=obj.toString();
                                                    }
                                                }
                                            }
                                        if("".trim().equals(stnm))
                                            stnm=stcd.replaceAll("'","");

                                    strContent_table.append("<td class='title' colspan='2'>"+stnm+"<br>["+stcd.replaceAll("'","")+"]</br></td>");
                                }
                                if(stsc.length<cuntNumbert){
                                    for(int cc=0;cc<cuntNumbert-stsc.length;cc++){
                                        strContent_table.append("<td class='title' colspan='2'>&nbsp;</td>");
                                    }
                                }
                                strContent_table.append("</tr><tr bgcolor='#E8EFFF' height='20'>");
                                for(String stcd:stsc){
                                    strContent_table.append("<td class='title' nowrap>  年   份 </td>");
                                    strContent_table.append("<td class='title' nowrap>导出条数</td>");
                                }
                                if(stsc.length<cuntNumbert){
                                    for(int cc=0;cc<cuntNumbert-stsc.length;cc++){
                                        strContent_table.append("<td class='title' nowrap>&nbsp;</td>");
                                        strContent_table.append("<td class='title' nowrap>&nbsp;</td>");
                                    }
                                }
                                strContent_table.append("</tr>");
                                strContent_table.append("<tr>");
//                                strContent_table.append("<td>" + dbTool.getCountToExcel(jt1, table,realStcdToHtml,dbTool.isHaveStcdCol(table)) + "</td>");
                                //表格实际导出数据详细信息
                                List resultList = (List)resultStscMap.get(table);
                                if(resultList!=null && resultList.size()>0){
                                    for(int k = 0; k<stsc.length;k++){
                                        boolean nullflg = true;
                                        for(int n=0;n<resultList.size();n++){
                                         Map stscMap = (Map)resultList.get(n);
                                         Object stscobj=(Object)stscMap.get(stsc[k].replaceAll("'","").trim());
                                         if(stscobj==null){
                                            nullflg = false;
                                         }else{
                                            String stscStr[] = stscobj.toString().split(",");
                                            strContent_table.append("<td bgcolor='#FFFFFF'>"+stscStr[0]+"-"+stscStr[1]+"</td>");
                                            strContent_table.append("<td bgcolor='#FFFFFF'>"+stscStr[2]+"</td>");
                                             nullflg = true;break;
                                         }
                                        }
                                        if(!nullflg){
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                            nullflg = true;
                                        }
                                    }
                                    if(stsc.length<cuntNumbert){
                                        for(int cc=0;cc<cuntNumbert-stsc.length;cc++){
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                        }
                                    }
                                }else{
                                    for(int k = 0; k<stsc.length;k++){
                                        strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                        strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                    }
                                    if(stsc.length<cuntNumbert){
                                        for(int cc=0;cc<cuntNumbert-stsc.length;cc++){
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                            strContent_table.append("<td bgcolor='#FFFFFF'>&nbsp;</td>");
                                        }
                                    }
                                }
                                strContent_table.append("</tr>");
                            }

                        }
                        try {
//                            FileOutputStream resultFile = new  FileOutputStream(desFile,true);
                            OutputStreamWriter opsw =  new OutputStreamWriter(new FileOutputStream(desFile,true),"GBK");
//                            PrintWriter printFile =  new PrintWriter(resultFile);
                            opsw.write(strContent_table.toString());
                            strContent_table= new StringBuffer("");
//                            resultFile.close();
                            opsw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                }
            }
            
        }//
    public static void writeReportHtmlDetail(String savePath){
            String desFile = savePath + "\\excel\\Report.html";
            int colNum = 2*cuntNumbert+5;
            StringBuffer strContent_detail = new StringBuffer("<tr bgcolor='#E8EFFF' height='30'><td colspan='"+colNum+"' align='center'>如果生成数据索引失败，请对照c盘下的tables.xls文件，确认数据表存在，并且索引字段名称跟您的数据库对应。</td></tr></table></body></html>");
            try {
               FileOutputStream resultFile = new  FileOutputStream(desFile,true);
                PrintWriter printFile =  new PrintWriter(resultFile,true);
                printFile.println(strContent_detail);
                resultFile.close();
                printFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public static void writeReportHtmlHead(String savePath,
            String errorTab,
            DBTool dbTool,
            DefaultListModel listTabModel,
            String stscstr) {
        String reportStcd[] = dbTool.makeStcdSqlCol(stscstr,cuntNumbert,true);
        StringBuffer strContent_table=new StringBuffer("");
        StringBuffer strContent_head = new StringBuffer("");
        int colNum = 2*cuntNumbert+5;
        String desFile = savePath + "\\excel\\Report.html";
        listTabModel.removeElement("数据索引表");
        strContent_head.append("<html><head><title>REPORT</title><meta http-equiv='Content-Type' content='text/html; charset=gbk'></head>");
        strContent_head.append("<style type='text/css'>");
        strContent_head.append(".title {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;}"
                              +".title2 {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;}"
                              +"a {text-decoration: none;color: #484833;}a:hover {text-decoration: underline;color: #6B6B4B;}</style><body scroll=auto>");
        strContent_table.append("<table width='98%' align='center' border='0' 'cellspacing='1' bgcolor='#CCCCCC'>");
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 14pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;' colspan='"+colNum+"'>数据导出报告</td></tr>");
        if (!listTabModel.isEmpty()) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='"+colNum+"'>以下为未选择报表</td></tr>");
            for (int i = 0; i < listTabModel.size(); i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td colspan='2'>" + HY_DBTP_JDao.getTabid(listTabModel.get(i).toString(), dbTool) + "</td><td colspan='"+(colNum-2)+"'>" + listTabModel.get(i) + "</td></tr>");
            }
        }
        if (errorTab != null && errorTab.length() > 0) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='"+colNum+"'>以下报表和导出结构标准不一致未能成功导出</td></tr>");
            for (int i = 0; i < errorTab.split(",").length; i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td colspan='3'>" + HY_DBTP_JDao.getTabid(errorTab.split(",")[i],dbTool) + "</td><td colspan='"+(colNum-3)+"'>" + errorTab.split(",")[i] + "</td></tr>");
            }
        }
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'>");
        strContent_table.append("<td class='title' nowrap>表名称</td>");
        strContent_table.append("<td class='title' nowrap>源数据条数</td>");
        strContent_table.append("<td class='title' nowrap>导出条数</td>");
        strContent_table.append("<td class='title' nowrap> 索  引 </td>");
        if(reportStcd.length>1)
            strContent_table.append("<td class='title' colspan='"+2*cuntNumbert+"' nowrap> 导出数据详细信息 </td>");
        else
            strContent_table.append("<td class='title' colspan='"+2*reportStcd[0].split(",").length+"' nowrap> 导出数据详细信息 </td>");

            strContent_table.append("<td class='title' nowrap>导出合计(站年)</td>");
            strContent_table.append("</tr>");

            StringBuffer strContent = new StringBuffer("");
            strContent.append(strContent_head);
            strContent.append(strContent_table);
            File file = new File(desFile);
            if (file.exists()) {
                file.delete();
            }
            try {
                FileWriter resultFile = new FileWriter(file);
                PrintWriter printFile =  new PrintWriter(resultFile);
                printFile.println(strContent);
                resultFile.close();
                printFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
