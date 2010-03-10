package dbtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    @SuppressWarnings({"unchecked", "deprecation"})
    public static void createXls(String reportName, String savePath, String colAndName, List rowList) {
        if (colAndName != null && !"".trim().equals(colAndName) && reportName != null && !"".trim().equals(reportName)) {
            String codeStr = colAndName.split(";")[0];
            String nameStr = colAndName.split(";")[1];

            String codeArr[] = codeStr.split(",");
            String nameArr[] = nameStr.split(",");

            String filename = savePath + "\\excel\\" + reportName + ".xls";

            HSSFWorkbook workbook = new HSSFWorkbook();
            // �� Excel �������н���һ��������,����Ϊȱʡֵ sheet1
            HSSFSheet sheet = workbook.createSheet();
            // ��������
            HSSFFont font = workbook.createFont();
            // ��������ɫ�� ��
            font.setColor(HSSFColor.BLUE.index);
            // ����������Ϊ����
            // ������ʽ
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            // �Ѵ��������帶���ڸ�ʽ
            cellStyle.setLocked(true);// ������Ԫ��
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);// ��Ԫ����������Զ�����
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //�������д���һ��
            HSSFRow row_Title = sheet.createRow(0);
            row_Title.setHeight((short) 600);
            HSSFCell cell_title = row_Title.createCell((short) 0);
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
            HSSFFont titleFont = workbook.createFont(); // ���������ʽ
            titleFont.setColor(HSSFColor.BLUE.index);

            titleFont.setFontHeight((short) 400); // ���������С
            titleStyle.setFont(titleFont);

            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell_title.setCellStyle(titleStyle);
            cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell_title.setCellValue(reportName);
            sheet.addMergedRegion(new Region(0, (short) 0, 0,
                    (short) (codeArr.length)));

            //�������д���һ��
            HSSFRow row_Code = sheet.createRow(1);
            row_Code.setHeight((short) 0);

            HSSFRow row_Name = sheet.createRow(2);
            row_Code.setHeight((short) 0);

            for (int i = 0; i < codeArr.length; i++) {
                // ��һ���д���һ����� row_ENM
                HSSFCell cell_code = row_Code.createCell((short) i);
                HSSFCell cell_cnnm = row_Name.createCell((short) i);
                // ������ĸ�ʽ������һ����Ԫ��
                cell_cnnm.setCellStyle(cellStyle);
                cell_code.setCellStyle(cellStyle);
                // ���ô˵�Ԫ���д�������ַ���
                cell_cnnm.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // ���ñ��� ����������������������
                cell_cnnm.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // ��˵�Ԫ���з���ֵ
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
        String filename = savePath + "\\excel\\��������.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        // �� Excel �������н���һ��������,����Ϊȱʡֵ sheet1
        HSSFSheet sheet = workbook.createSheet();
        // ��������
        HSSFFont font = workbook.createFont();
        // ��������ɫ�� ��
        font.setColor(HSSFColor.BLUE.index);
        // ����������Ϊ����
        // ������ʽ
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // �Ѵ��������帶���ڸ�ʽ
        cellStyle.setLocked(true);// ������Ԫ��
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);// ��Ԫ����������Զ�����
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        //�������д���һ��
        HSSFRow row_Title = sheet.createRow(0);
        row_Title.setHeight((short) 600);
        HSSFCell cell_title = row_Title.createCell((short) 0);
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
        HSSFFont titleFont = workbook.createFont(); // ���������ʽ
        titleFont.setColor(HSSFColor.BLUE.index);

        titleFont.setFontHeight((short) 400); // ���������С
        titleStyle.setFont(titleFont);

        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        cell_title.setCellStyle(titleStyle);
        cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell_title.setCellValue("���ݵ�������");
        sheet.addMergedRegion(new Region(0, (short) 0, 0,
                (short) (8)));
        if (!model.isEmpty()) {
            int beginRow = 1;
            for (int i = 0; i < model.size(); i++) {
                //�������д���һ��
                HSSFRow row_val = sheet.createRow(beginRow);
                // ��һ���д���һ����� row_ENM
                HSSFCell cell_code = row_val.createCell((short) 0);
                // ������ĸ�ʽ������һ����Ԫ��
                cell_code.setCellStyle(cellStyle);
                // ���ô˵�Ԫ���д�������ַ���
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // ���ñ��� ����������������������
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // ��˵�Ԫ���з���ֵ
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

    public static void createReportHtml(String savePath,
            DefaultListModel expSuccessModel,
            String errorTab,
            DBTool dbTool,
            DefaultListModel selectedStscModel,
            DefaultListModel selectedSnameModel,
            DefaultListModel listTabModel,int type,
            String stscstr) {
        JdbcTemplate jt1 = dbTool.getJt1();
        JdbcTemplate jt2 = dbTool.getJt2();
        String[] tables = null;
        if (!expSuccessModel.isEmpty()) {
            tables = new String[expSuccessModel.size()];
            for (int i = 0; i < expSuccessModel.size(); i++) {
                tables[i] = HY_DBTP_JDao.getTabid(expSuccessModel.get(i).toString(), dbTool);
            }
        }
        listTabModel.removeElement("����������");
        String desFile = savePath + "\\excel\\Report.html";
        StringBuffer strContent_head = new StringBuffer("<html><head><title>REPORT</title><meta http-equiv='Content-Type' content='text/html; charset=gbk'></head>");
        strContent_head.append("<style type='text/css'>");
        strContent_head.append(".title {font-size: 10pt;padding-top: 2px;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;}</style><body>");
        StringBuffer strContent_table = new StringBuffer("<table width='85%' align='center' border='0' 'cellspacing='1' bgcolor='#CCCCCC'>");
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 14pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:center;' colspan='5'>���ݵ�������</td></tr>");
        if (!listTabModel.isEmpty()) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='5'>����Ϊδѡ�񱨱�</td></tr>");
            for (int i = 0; i < listTabModel.size(); i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td >" + HY_DBTP_JDao.getTabid(listTabModel.get(i).toString(), dbTool) + "</td><td colspan='3'>" + listTabModel.get(i) + "</td></tr>");
            }
        }
        if (errorTab != null && errorTab.length() > 0) {
            strContent_table.append("<tr bgcolor='#E8EFFF' height='30'><td align='center'style='font-size: 10pt;font-weight: bolder;color: #000000;background-color: #E8EFFF;text-align:left;' colspan='5'>���±���͵����ṹ��׼��һ��δ�ܳɹ�����</td></tr>");
            for (int i = 0; i < errorTab.split(",").length; i++) {
                strContent_table.append("<tr bgcolor='#FFFFFF'><td colspan='2'>" + errorTab.split(",")[i] + "</td><td colspan='3'>" + dbTool.getTabCnnm(jt2, errorTab.split(",")[i]) + "</td></tr>");
            }
        }
        strContent_table.append("<tr bgcolor='#E8EFFF' height='30'>");
        strContent_table.append("<td class='title'>����</td>");
        strContent_table.append("<td class='title'>������</td>");
//        strContent_table.append("<td class='title'>��վ����</td>");
        strContent_table.append("<td class='title'>Դ��������</td>");
        strContent_table.append("<td class='title'>������������</td>");
        strContent_table.append("</tr>");
        if (tables != null && tables.length > 0) {
            for (String table : tables) {
                strContent_table.append("<tr bgcolor='#FFFFFF' height='20'>");
                strContent_table.append("<td>" + table + "</td>");
                strContent_table.append("<td>" + dbTool.getTabCnnm(jt2, table) + "</td>");
//                strContent_table.append("<td></td>");
                strContent_table.append("<td>" + dbTool.getCount(jt1, table) + "</td>");
                if(type==0)
                    strContent_table.append("<td>" + dbTool.getCountToExcel(jt1, table,stscstr,dbTool.isHaveStcdCol(table)) + "</td></tr>");
                else
                    strContent_table.append("<td>" + dbTool.getCount(jt2, table) + "</td></tr>");
//                if (dbTool.isHaveStcdCol(table)) {
//                    if (!selectedStscModel.isEmpty()) {
//                        for (int i = 0; i < selectedStscModel.size(); i++) {
//                            String stsc = selectedStscModel.get(i).toString();
//                            String stname = selectedSnameModel.get(i).toString();
//                            strContent_table.append("<tr bgcolor='#FFFFFF' height='20'>");
//                            strContent_table.append("<td></td>");
//                            strContent_table.append("<td></td>");
//                            strContent_table.append("<td>" + stname + "[" + stsc + "]</td>");
//                            strContent_table.append("<td>" + dbTool.getCountForStsc(jt1, table, stsc) + "</td>");
//                            strContent_table.append("<td>" + dbTool.getCountForStsc(jt2, table, stsc) + "</td></tr>");
//                        }
//                    }
//                }
            }
        }
        StringBuffer strContent_detail = new StringBuffer("</table></body></html>");
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
            // �� Excel �������н���һ��������,����Ϊȱʡֵ sheet1
            HSSFSheet sheet = workbook.createSheet();
            // ��������
            HSSFFont font = workbook.createFont();
            // ��������ɫ�� ��
            font.setColor(HSSFColor.BLUE.index);
            // ����������Ϊ����
            // ������ʽ
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            // �Ѵ��������帶���ڸ�ʽ
            cellStyle.setLocked(true);// ������Ԫ��
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);// ��Ԫ����������Զ�����
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //�������д���һ��
            HSSFRow row_Title = sheet.createRow(0);
            row_Title.setHeight((short) 600);
            HSSFCell cell_title = row_Title.createCell((short) 0);
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
            HSSFFont titleFont = workbook.createFont(); // ���������ʽ
            titleFont.setColor(HSSFColor.BLUE.index);

            titleFont.setFontHeight((short) 400); // ���������С
            titleStyle.setFont(titleFont);

            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            cell_title.setCellStyle(titleStyle);
            cell_title.setEncoding(HSSFCell.ENCODING_UTF_16);
            cell_title.setCellValue(reportName);
            sheet.addMergedRegion(new Region(0, (short) 0, 0,
                    (short) (codeArr.length)));

            //�������д���һ��
            HSSFRow row_Code = sheet.createRow(1);

            for (int i = 0; i < codeArr.length; i++) {
                // ��һ���д���һ����� row_ENM
                HSSFCell cell_code = row_Code.createCell((short) i);
                // ������ĸ�ʽ������һ����Ԫ��
                cell_code.setCellStyle(cellStyle);
                // ���ô˵�Ԫ���д�������ַ���
                cell_code.setCellType(HSSFCell.CELL_TYPE_STRING);
                // ���ñ��� ����������������������
                cell_code.setEncoding(HSSFCell.ENCODING_UTF_16);
                // ��˵�Ԫ���з���ֵ
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
        //��ȡSheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //��ȡ�к�
        int rows = sheet.getPhysicalNumberOfRows();

        String[] stcdList = new String[rows - 1];
        for (int i = 1; i < rows; i++) {
            HSSFRow row_Val = sheet.getRow(i);
            HSSFCell cell_stcd = row_Val.getCell((short) 0);
            HSSFCell cell_stnm = row_Val.getCell((short) 1);
            HSSFCell cell_parent = row_Val.getCell((short) 2);
            try {
                stcdList[i - 1] = "INSERT INTO TABLE_STCD(STCD,STNM,PARENT)VALUES('" + getStringCellValue(cell_stcd) + "','" + getStringCellValue(cell_stnm) + "','" + getStringCellValue(cell_parent) + "')";
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
        //��ȡSheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //��ȡ�к�
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
}
