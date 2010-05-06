package com.basesoft.modules.excel;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.basesoft.modules.employee.Car;
import com.basesoft.modules.employee.CarDAO;
import com.basesoft.modules.employee.EmployeeDAO;
import com.basesoft.modules.plan.PlanDAO;
import com.basesoft.util.StringUtil;

public class ExportExcel {
	
	/**
	 * 工时统计汇总表写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @param excelDAO
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_GSTJHZ(List<Map<String, String>> list, ExcelDAO excelDAO, String imagepath) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		String[] str = new String[1];
		str[0] = "工时统计汇总";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		List listDepart = excelDAO.getDepartment();
		int size = 3 + listDepart.size();
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, size-1, 0);
		
		//插入表头
		String[] str1 = new String[size];
		str1[0] = "序号";
		str1[1] = "工作令号";
		str1[2] = "本月合计";
		for(int i=0;i<listDepart.size();i++){
			Map mapDepart = (Map)listDepart.get(i);
			str1[i + 3] = mapDepart.get("NAME").toString();
		}
		insertRowData(sheet, 1, str1);
		
		//插入内容部分
		for (int i = 0; i < list.size(); i++) {
			String[] str2 = new String[size];
			Map<String, String> map = (Map<String, String>) list.get(i);
			str2[0] = (i + 1) + "";
			str2[1] = map.get("NAME");
			str2[2] = String.valueOf(map.get("totalCount"));
			for(int j=0;j<listDepart.size();j++){
				str2[j + 3] = String.valueOf(map.get("departCount" + j));
			}
			
			insertRowData(sheet, i + 2, str2);
		}	
		//导出图片
		WritableImage ri=new WritableImage(0,list.size() + 3,listDepart.size() + 4,list.size() + 5,new File(imagepath));   
		sheet.addImage(ri);   
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 科研工时统计表写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @param excelDAO
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_KYGSTJ(List<Map<String, String>> list , ExcelDAO excelDAO, String imagepath) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		String[] str = new String[1];
		str[0] = "科研工时统计";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		List listPeriod = excelDAO.getDICTByType("5");
		int size = 3 + listPeriod.size();
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, size-1, 0);
		
		//插入表头
		String[] str1 = new String[size];
		str1[0] = "序号";
		str1[1] = "工作令号";
		str1[2] = "合计";
		for(int i=0;i<listPeriod.size();i++){
			Map mapPeriod = (Map)listPeriod.get(i);
			str1[i + 3] = mapPeriod.get("NAME").toString();
		}
		
		insertRowData(sheet, 1, str1);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str2 = new String[size];
			Map<String, String> map = (Map<String, String>) list.get(i);
			str2[0] = (i + 1) + "";
			str2[1] = map.get("PJCODE");
			str2[2] = String.valueOf(map.get("TOTALCOUNT"));
			for(int j=0;j<listPeriod.size();j++){
				Map mapPeriod = (Map)listPeriod.get(j);
				str2[j + 3] = String.valueOf(map.get(mapPeriod.get("CODE")));
			}
			
			insertRowData(sheet, i + 2, str2);
		}
		
		//导出图片
		WritableImage ri=new WritableImage(0,list.size() + 3,listPeriod.size() + 4,list.size() + 5,new File(imagepath));   
		sheet.addImage(ri); 
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 承担任务情况表写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_CDRWQK(List<Map<String, String>> list, String imagepath) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		String[] str = new String[1];
		str[0] = "承担任务情况";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";;
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, 10, 0);
		
		//插入第一行表头
		String[] str1 = new String[3];
		str1[0] = "序号";
		str1[1] = "工作令号";
		str1[2] = "本期末安排人数及专业分类(人)";
		insertRowData(sheet, 1, str1);
		sheet.mergeCells(2, 1, 10, 1);
		
		//插入第二行表头
		String[] str2 = new String[6];
		str2[0] = "";
		str2[1] = "";
		str2[2] = "合计";
		str2[3] = "本科及以上学历或中高级职称人员";
		str2[4] = "大、中专学历或初级职称人员";
		str2[5] = "合计中按专业分";
		insertRowData(sheet, 2, str2);
		sheet.mergeCells(5, 2, 10, 2);
		
		//插入第三行表头
		String[] str3 = new String[11];
		str3[0] = "";
		str3[1] = "";
		str3[2] = "";
		str3[3] = "";
		str3[4] = "";
		str3[5] = "电讯";
		str3[6] = "计算机硬件";
		str3[7] = "结构";
		str3[8] = "工艺";
		str3[9] = "软件开发";
		str3[10] = "其他";
		insertRowData(sheet, 3, str3);
		sheet.mergeCells(0, 1, 0, 3);
		sheet.mergeCells(1, 1, 1, 3);
		sheet.mergeCells(2, 2, 2, 3);
		sheet.mergeCells(3, 2, 3, 3);
		sheet.mergeCells(4, 2, 4, 3);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str4 = new String[11];
			Map<String, String> map = (Map<String, String>) list.get(i);
			str4[0] = (i + 1) + "";
			str4[1] = map.get("PJCODE");
			str4[2] = String.valueOf(map.get("TOTALCOUNT"));
			str4[3] = String.valueOf(map.get("C1"));
			str4[4] = String.valueOf(map.get("C2"));
			str4[5] = String.valueOf(map.get("C3"));
			str4[6] = String.valueOf(map.get("C4"));
			str4[7] = String.valueOf(map.get("C5"));
			str4[8] = String.valueOf(map.get("C6"));
			str4[9] = String.valueOf(map.get("C7"));
			str4[10] = String.valueOf(map.get("C8"));
			
			insertRowData(sheet, i + 4, str4);
		}
		//导出图片
		WritableImage ri=new WritableImage(0, list.size() + 5, 13, list.size() + 8,new File(imagepath));   
		sheet.addImage(ri); 
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 员工投入分析excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_YGTRFX(List<Map<String, String>> list, String imagepath, String selpjname) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		String[] str = new String[1];
		str[0] = "员工投入分析";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";;
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, 6, 0);
		
		//插入第一行表头
		String[] str1 = new String[7];
		str1[0] = "姓名";
		str1[1] = "部门";
		str1[2] = "职位";
		str1[3] = "学历";
		str1[4] = "职称";
		str1[5] = "工作令号";
		str1[6] = "投入工时";
		insertRowData(sheet, 1, str1);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str2 = new String[7];
			Map<String, String> map = (Map<String, String>) list.get(i);
			str2[0] = map.get("NAME");
			str2[1] = map.get("DEPARTNAME");
			str2[2] = map.get("LEVEL");
			str2[3] = map.get("DEGREENAME");
			str2[4] = map.get("PRONAME");
			str2[5] = selpjname;
			str2[6] = String.valueOf(map.get("AMOUNT"));
			
			insertRowData(sheet, i + 1, str2);
		}
		//导出图片
		WritableImage ri=new WritableImage(0, list.size() + 5, 13, list.size() + 8,new File(imagepath));   
		sheet.addImage(ri); 
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 计划考核统计表写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @param planDAO
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_PLAN(List<Map<String, String>> list, PlanDAO planDAO, String datepick) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		String[] str = new String[1];
		str[0] = datepick + "综合考核及产品计划完成率考核统计结果";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, 11, 0);
		
		//插入表头
		String str1[] = new String[12];
		str1[0] = "产品令号";
		str1[1] = "计划要求";
		str1[2] = "计划完成情况";
		str1[3] = "考核";
		str1[4] = "内因/外因";
		str1[5] = "分管部领导";
		str1[6] = "责任单位";
		str1[7] = "责任人";
		str1[8] = "计划员";
		str1[9] = "分管室领导";
		str1[10] = "完成率(%)";
		str1[11] = "备注";
		
		insertRowData(sheet, 1, str1);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str2 = new String[12];
			Map<String, String> map = (Map<String, String>) list.get(i);
		
			//任务完成情况和完成率计算
			Date now = new Date();
			Date startdate = map.get("STARTDATE")==null?new Date():StringUtil.StringToDate(String.valueOf(map.get("STARTDATE")),"yyyy-MM-dd");
			Date enddate = map.get("ENDDATE")==null?new Date():StringUtil.StringToDate(String.valueOf(map.get("ENDDATE")),"yyyy-MM-dd");
			
			int plandays = StringUtil.getBetweenDays(startdate, enddate);
			int passdays = StringUtil.getBetweenDays(startdate, now);
			//完成率
			float daypersent = plandays==0?0:passdays*100/plandays;
			if(daypersent>100){
				daypersent = 100;
			}
			//完成情况
			Map mapPersent = planDAO.getPersent(daypersent);
			String state = mapPersent.get("NAME")==null?"":mapPersent.get("NAME").toString();
			
			str2[0] = map.get("PJNAME")==null?"":map.get("PJNAME");
            str2[1] = map.get("NOTE")==null?"":map.get("NOTE");
            str2[2] = state;
            str2[3] = map.get("ASSESS")==null?"":map.get("ASSESS");
            str2[4] = "";
            str2[5] = map.get("LEADER_SECTION")==null?"":map.get("LEADER_SECTION");
            str2[6] = map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME");
            str2[7] = map.get("EMPNAME")==null?"":map.get("EMPNAME");
            str2[8] = map.get("PLANNERNAME")==null?"":map.get("PLANNERNAME");
            str2[9] = map.get("LEADER_ROOM")==null?"":map.get("LEADER_ROOM");
            str2[10] = String.valueOf(daypersent);
            str2[11] = map.get("REMARK")==null?"":map.get("REMARK");
            
            insertRowData(sheet, i + 2, str2);
		}
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 员工加班费表写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @param planDAO
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_JBF(List<Map<String, String>> list, String datepick) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		String[] str = new String[1];
		str[0] = datepick + "加班费统计结果";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, 13, 0);
		
		//插入表头
		String str1[] = new String[14];
		str1[0] = "人员编号";
		str1[1] = "姓名";
		str1[2] = "部门";
		str1[3] = "加班费";
		str1[4] = "评审费";
		str1[5] = "稿酬";
		str1[6] = "酬金";
		str1[7] = "外场补贴";
		str1[8] = "车公里补贴";
		str1[9] = "劳保";
		str1[10] = "过江补贴";
		str1[11] = "返聘补贴";
		str1[12] = "项目名称";
		str1[13] = "备注";
		
		insertRowData(sheet, 1, str1);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str2 = new String[14];
			Map<String, String> map = (Map<String, String>) list.get(i);
		
			str2[0] = map.get("EMPCODE")==null?"":map.get("EMPCODE");
            str2[1] = map.get("EMPNAME")==null?"":map.get("EMPNAME");
            str2[2] = map.get("DEPARTNAME")==null?"":map.get("DEPARTNAME");
            str2[3] = map.get("JBF")==null?"":String.valueOf(map.get("JBF"));
            str2[4] = map.get("PSF")==null?"":String.valueOf(map.get("PSF"));
            str2[5] = map.get("GC")==null?"":String.valueOf(map.get("GC"));
            str2[6] = map.get("CJ")==null?"":String.valueOf(map.get("CJ"));
            str2[7] = map.get("WCBT")==null?"":String.valueOf(map.get("WCBT"));
            str2[8] = map.get("CGLBT")==null?"":String.valueOf(map.get("CGLBT"));
            str2[9] = map.get("LB")==null?"":String.valueOf(map.get("LB"));
            str2[10] = map.get("GJBT")==null?"":String.valueOf(map.get("GJBT"));
            str2[11] = map.get("FPBT")==null?"":String.valueOf(map.get("FPBT"));
            str2[12] = map.get("XMMC")==null?"":map.get("XMMC");
            str2[13] = map.get("BZ")==null?"":map.get("BZ");
            
            insertRowData(sheet, i + 2, str2);
		}
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 考勤记录表写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @param planDAO
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_KQJL(List<Map<String, String>> list, String datepick) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		
		
		String[] str = new String[1];
		str[0] = datepick + "职工考勤记录";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		String start = "";
		String end = datepick + "-25";
		
		if("01".equals(datepick.split("-")[1])){
			start = (Integer.parseInt(datepick.split("-")[0])-1) + "-12-25";
		}else {
			start = datepick.split("-")[0] + "-" + (Integer.parseInt(datepick.split("-")[1])-1) + "-25";
		}
		int length = 7 + StringUtil.getBetweenDays(StringUtil.StringToDate(start, "yyyy-MM-dd"), StringUtil.StringToDate(end, "yyyy-MM-dd"));
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, length, 0);
		
		//插入表头
		String str1[] = new String[length+1];
		str1[0] = "姓名";
		str1[1] = (Integer.parseInt(datepick.split("-")[1]) -1) + "月份";
		str1[length - 31] = Integer.parseInt(datepick.split("-")[1]) + "月份";
		str1[length-6] = "缺勤小结（小时）";
		
		insertRowData(sheet, 1, str1);
		sheet.mergeCells(1, 1, length - 31, 1);
		sheet.mergeCells(length - 30, 1, length - 7, 1);
		sheet.mergeCells(length - 6, 1, length, 1);
		
		String str2[] = new String[length+1];
		for(int i=1;i<=length-31;i++){//上个月25号以后
			str2[i] = String.valueOf(24 + i);
		}
		int j = 1;
		for(int i=length-30;i<=length-7;i++){//本月1到25号
			str2[i] = String.valueOf(j);
			j = j + 1;
		}
		str2[length-6] = "迟到";
		str2[length-5] = "早退";
		str2[length-4] = "病假";
		str2[length-3] = "事假";
		str2[length-2] = "旷工";
		
		insertRowData(sheet, 2, str2);
		sheet.mergeCells(0, 1, 0, 2);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str3 = new String[length+1];
			Map<String, String> map = (Map<String, String>) list.get(i);
		
			str3[0] = map.get("NAME")==null?"":map.get("NAME");
			for(int k=1;k<=length-31;k++){//上个月25号以后的
				String date = start.split("-")[0] + "-0" + start.split("-")[1] + "-" + (24 + k);
				str3[k] = map.get(date);
			}
			int l = 1;
			for(int k=length-30;k<=length-7;k++){//本月1日至25日
				String date = end.split("-")[0] + "-" + end.split("-")[1] + "-";
				if(l>10){
					date = date + l;
				}else {
					date = date + "0" + l;
				}
				str3[k] = map.get(date);
				l = l + 1;
			}
            
            insertRowData(sheet, i + 3, str3);
		}
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 班车预约信息写成一个excel文件，返回这个文件的路径
	 * @param list 数据列表
	 * @param car 班车
	 * @param datepick 日期
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 * @throws IndexOutOfBoundsException
	 */
	public String exportExcel_BCYY(List<Map<String, String>> list, String carid, String datepick, CarDAO carDAO) throws IOException, BiffException, WriteException, IndexOutOfBoundsException {
		Car car = carDAO.findById(carid);
		String carcode = car.getCarcode()==null?"":car.getCarcode();
		
		String[] str = new String[1];
		str[0] = datepick + "   " + carcode + "班车预约记录";
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1)) + str[0] + ".xls";
		
		WritableWorkbook wb = readExcel(path);
		WritableSheet sheet = wb.getSheet(0);
		
		//插入标题
		insertRowData(sheet, 0, str);
		sheet.mergeCells(0, 0, 5, 0);
		
		//插入表头
		String str1[] = new String[6];
		str1[0] = "员工姓名";
		str1[1] = "预约日期";
		str1[2] = "发车时间";
		str1[3] = "班车编号";
		str1[4] = "班车车牌号";
		str1[5] = "状态";
		
		insertRowData(sheet, 1, str1);
		
		for (int i = 0; i < list.size(); i++) {
			String[] str2 = new String[6];
			Map<String, String> map = (Map<String, String>) list.get(i);
		
			String empcode = map.get("EMPCODE")==null?"":map.get("EMPCODE");
			String ordercarid = map.get("CARID")==null?"":map.get("CARID");
			Car orderCar = carDAO.findById(ordercarid);
			String status = map.get("STATUS")==null?"":map.get("STATUS");
			if("0".equals(status)){
				status = "新增加";
			}else if("1".equals(status)){
				status = "已确认";
			}
			
			str2[0] = carDAO.findNameByCode("EMPLOYEE", empcode);
			str2[1] = map.get("ORDERDATE")==null?"":String.valueOf(map.get("ORDERDATE"));
			str2[2] = map.get("ORDERSENDTIME")==null?"":String.valueOf(map.get("ORDERSENDTIME"));
			str2[3] = orderCar.getCarcode();
			str2[4] = orderCar.getCarno();
			str2[5] = status;
            
            insertRowData(sheet, i + 2, str2);
		}
		
		wb.write();
		wb.close();
		
		return path;
	}
	
	/**
	 * 读取excel模版，实例化WritableWorkbook
	 * @param pathTemp 模版路径
	 * @param path 实际下载文件路径
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	private WritableWorkbook readExcel(String pathTemp, String path) throws IOException, BiffException {
		
		File excel = new File(pathTemp);
	
		Workbook wbTemp = Workbook.getWorkbook(excel);
		WritableWorkbook wb = Workbook.createWorkbook(new File(path), wbTemp);
			
		return wb;
	}
	
	/**
	 * 在路径下生成文件，实例化WritableWorkbook
	 * @param path 实际下载文件路径
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	private WritableWorkbook readExcel(String path) throws IOException, BiffException {
		
		WritableWorkbook wb = Workbook.createWorkbook(new File(path));
		wb.createSheet("sheet", 0);
		
		return wb;
	}
	
	/**
	 * 获取地址
	 * @param model
	 * @return
	 */
	private String getPath(String model){
		String path = "/" + java.net.URLDecoder.decode(ExportExcel.class.getResource("").getPath().substring(1));
		
		if("GSTJHZ".equals(model)){//工时统计汇总
			path = path + "工时统计汇总.xls";
		}else if("KYGSTJ".equals(model)){//科研工时统计
			path = path + "科研工时统计.xls";
		}else if("CDRWQK".equals(model)){//承担任务情况
			path = path + "承担任务情况.xls";
		}
		
		return path;
	}
	
	private void insertRowData(WritableSheet sheet, int row, String[] dataArr) throws WriteException {
		
		insertRowData(sheet, row, dataArr, 0);
	}
	
	private void insertRowData(WritableSheet sheet, int row, String[] dataArr, int startCol) throws WriteException {
		
     	for(int i = 0; i < dataArr.length; i++){
     		insertCell(sheet, startCol + i, row,  dataArr[i]);
     	}   
	}
	
	private void insertRowData(WritableSheet sheet, int row, String[] dataArr, int startCol, WritableCellFormat wcf) throws WriteException {
		
     	for(int i = 0; i < dataArr.length; i++){
     		insertCell(sheet, startCol + i, row,  dataArr[i], wcf);
     	}   
	}
	
	private void insertCell(WritableSheet sheet, int col, int row, String data) throws WriteException {
		
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false);
 		WritableCellFormat wcf = new WritableCellFormat(wf);
 		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
 		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
 		wcf.setAlignment(Alignment.CENTRE);
 		wcf.setWrap(true);
    	   
 		Label label = new Label(col, row, data, wcf);   
 		sheet.addCell(label);
	}
	
	private void insertCell(WritableSheet sheet, int col, int row, String data, WritableCellFormat wcf) throws WriteException {
    	   
 		Label label = new Label(col, row, data, wcf);   
 		sheet.addCell(label);
	}
}
