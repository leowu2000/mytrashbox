package com.basesoft.modules.project;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;

public class ChartUtil {

	/**
	 * 生成统计图
	 * @param method 
	 * @param list 统计数据list
	 * @param path 路径
	 * @param pjDAO
	 * @param datepick
	 * @return
	 */
	public static String createChart(String method, List<?> list, String path, ProjectDAO pjDAO, List listDepart, String pjcodes, String sel_type) throws Exception{
		path = path + "\\" + method + ".png";
		String title = "";
		if("gstjhz".equals(method)){//工时统计汇总
			title = "工时统计汇总";
			CategoryDataset dataset = createDatasetGstjhz(pjDAO, list, listDepart, pjcodes);   
	        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置   
	        JFreeChart freeChart = createChart(dataset, title, sel_type);   
	        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等   
	        saveAsFile(freeChart, path, 700, 350);   
		}else if("kygstj".equals(method)){
			title = "科研工时统计";
			CategoryDataset dataset = createDatasetKygstj(pjDAO, list, pjcodes);   
	        JFreeChart freeChart = createChart(dataset, title, sel_type);   
	        saveAsFile(freeChart, path, 700, 350);   
		}else if("cdrwqk".equals(method)){
			title = "承担任务情况";
			CategoryDataset dataset = createDatasetCdrwqk(pjDAO, list, pjcodes);   
	        JFreeChart freeChart = createChart(dataset, title, sel_type);   
	        saveAsFile(freeChart, path, 700, 350);   
		}
		
		return path;
	}
	
	/**
	 * 生成员工投入分析统计图
	 * @param listData
	 * @param selpjname
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String createChartYgtrfx(List listData, String selpjname, String path, String sel_type) throws Exception{
		path = path + "\\ygtrfx.png";
		String title = "员工投入分析";
		CategoryDataset dataset = createDatasetYgtrfx(listData, selpjname);  
		JFreeChart freeChart = createChart(dataset, title, sel_type);   
		saveAsFile(freeChart, path, 700, 350);   
		return path;
	}
	
	/**
	 * 生成员工投入分析统计图
	 * @param listData
	 * @param selpjname
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String createChartYgjbtj(List listData, String path) throws Exception{
		path = path + "\\ygjbtj.png";
		String title = "员工加班统计";
		CategoryDataset dataset = createDatasetYgjbtj(listData);  
		JFreeChart freeChart = createChart(dataset, title, "1");   
		saveAsFile(freeChart, path, 700, 350);   
		return path;
	}
	
	 /** 
     * 工时统计汇总
	 * @param pjDAO
	 * @param datepick
     */ 
    public static CategoryDataset createDatasetGstjhz(ProjectDAO pjDAO, List listData, List listDepart, String pjcodes) {   
    	List listPj = pjDAO.getProject(pjcodes);
    	
    	//每根柱子
        String[] rowKeys = new String[listDepart.size()];
        for(int i=0;i<listDepart.size();i++){
        	Map mapDepart = (Map)listDepart.get(i);
        	rowKeys[i] = mapDepart.get("NAME").toString();
        }
        //横坐标
        String[] colKeys = new String[listPj.size()];
        for(int i=0;i<listPj.size();i++){
        	Map mapPj = (Map)listPj.get(i);
        	colKeys[i] = mapPj.get("NAME").toString();
        }
        
        double [][] data = new double[listDepart.size()][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	
        	for(int j=0;j<listDepart.size();j++){
        		data[j][i] = Double.parseDouble(mapData.get("departCount" + (j)).toString());
        	}
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    }  
    
    /** 
     * 科研工时统计 
	 * @param pjDAO
	 * @param datepick
     */ 
    public static CategoryDataset createDatasetKygstj(ProjectDAO pjDAO, List listData, String pjcodes) {   
    	List listPj = pjDAO.getProject(pjcodes);
    	List listPeriod = pjDAO.getDICTByType("5");
    	
    	//每根柱子
        String[] rowKeys = new String[listPeriod.size()];
        for(int i=0;i<listPeriod.size();i++){
        	Map mapPeriod = (Map)listPeriod.get(i);
        	rowKeys[i] = mapPeriod.get("NAME").toString();
        }
        //横坐标
        String[] colKeys = new String[listPj.size()];
        for(int i=0;i<listPj.size();i++){
        	Map mapPj = (Map)listPj.get(i);
        	colKeys[i] = mapPj.get("NAME").toString();
        }
        
        double [][] data = new double[listPeriod.size()][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	
        	for(int j=0;j<listPeriod.size();j++){
        		Map mapPeriod = (Map)listPeriod.get(j);
        		data[j][i] = Double.parseDouble(mapData.get(mapPeriod.get("CODE")).toString());
        	}
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    } 

    /** 
     * 承担任务情况
	 * @param pjDAO
	 * @param datepick
     */ 
    public static CategoryDataset createDatasetCdrwqk(ProjectDAO pjDAO, List listData, String pjcodes) {   
    	List listPj = pjDAO.getProject(pjcodes);
    	List listDepart = pjDAO.getDepartment();
    	
    	//每根柱子
        String[] rowKeys = {"本科及以上学历或中高级职称人员", "大、中专学历或初级职称人员", "电讯", "计算机硬件", "结构", "工艺", "软件开发", "其他"};
        
        //横坐标
        String[] colKeys = new String[listPj.size()];
        for(int i=0;i<listPj.size();i++){
        	Map mapPj = (Map)listPj.get(i);
        	colKeys[i] = mapPj.get("NAME").toString();
        }
        
        double [][] data = new double[8][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	for(int j=1;j<9;j++){
        		data[j-1][i] = Double.parseDouble(mapData.get("C" + j).toString());
        	}
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    } 
    
    /** 
     * 员工投入分析
	 * @param listData
	 * @param selpjname
     */ 
    public static CategoryDataset createDatasetYgtrfx(List listData, String selpjname) {   
    	//柱子
        String[] rowKeys = {selpjname};
        
        //横坐标
        String[] colKeys = new String[listData.size()];
        
        double [][] data = new double[1][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	String name = mapData.get("NAME")==null?"":mapData.get("NAME").toString();
        	String code = mapData.get("CODE")==null?"":mapData.get("CODE").toString();
        	colKeys[i] = name + code;
        	data[0][i] = Double.parseDouble(mapData.get("AMOUNT").toString());
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    }
    
    /** 
     * 员工加班统计
	 * @param listData
     */ 
    public static CategoryDataset createDatasetYgjbtj(List listData) {   
    	//柱子
        String[] rowKeys = {"合计"};
        
        //横坐标
        String[] colKeys = new String[listData.size()];
        
        double [][] data = new double[1][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	String name = mapData.get("NAME")==null?"":mapData.get("NAME").toString();
        	String code = mapData.get("CODE")==null?"":mapData.get("CODE").toString();
        	colKeys[i] = name + code;
        	data[0][i] = Double.parseDouble(mapData.get("AMOUNT").toString());
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    }
	
	/**
	 * 根据CategoryDataset生成JFreeChart对象  
	 * @param dataset
	 * @return
	 */ 
    public static JFreeChart createChart(CategoryDataset dataset, String title, String sel_type) throws Exception{   
        JFreeChart jfreechart = null;
    	
        if("1".equals(sel_type)){//柱状图
        	jfreechart = ChartFactory.createBarChart3D(title,    //标题   
                    "",    //categoryAxisLabel （category轴，横轴，X轴的标签）   
                    "",    //valueAxisLabel（value轴，纵轴，Y轴的标签）   
                    dataset, // dataset   
                    PlotOrientation.VERTICAL,   
                    true, // legend   
                    false, // tooltips   
                    false); // URLs   
        }else if("2".equals(sel_type)){//饼图
        	jfreechart = ChartFactory.createPieChart3D(title, // 图表标题
        			(PieDataset)dataset, //数据集
                    true, //是否显示图例
                    true, // 是否生成工具
                    false); // 是否生成URL链接 

        }else if("3".equals(sel_type)){//折线图
        	jfreechart = ChartFactory.createLineChart3D(title, // chart title
        			"",
        			"",
        		    dataset, // data
        		    PlotOrientation.VERTICAL, // orientation
        		    true, // include legend
        		    true, // tooltips
        		    false // urls
        		    );

        }
           
        //以下的设置可以由用户定制，也可以省略   
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();   
        //背景色　透明度   
        //plot.setBackgroundAlpha(0.5f);   
        //前景色　透明度   
        //plot.setForegroundAlpha(0.5f);   
        //水平标题
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,12));
        domainAxis.setTickLabelFont(new Font("黑体",Font.BOLD,10));
        //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,12));
        rangeAxis.setTickLabelFont(new Font("黑体",Font.BOLD,10));
        //标题
        TextTitle textTitle = jfreechart.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD,20));
        //图例
        jfreechart.getLegend().setItemFont(new Font("宋体",Font.BOLD,10));  
        
        if("1".equals(sel_type)){
        	//柱子显示
            BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
            //显示条目标签
            renderer.setBaseItemLabelsVisible(true);
            //设置条目标签生成器
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            //设置条目标签显示的位置,outline表示在条目区域外,baseline_center表示基于基线且居中
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        }else if("3".equals(sel_type)){
        	LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
            DecimalFormat decimalformat1 = new DecimalFormat("##.#");//数据点显示数据值的格式
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            //设置条目标签显示的位置,outline表示在条目区域外,baseline_center表示基于基线且居中
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        }

        return jfreechart;   
    }   
    
    /**
     * 保存为文件
     * @param chart 图
     * @param outputPath 路径
     * @param weight 宽
     * @param height 高
     */
    public static void saveAsFile(JFreeChart chart, String outputPath, int weight, int height) {   
        FileOutputStream out = null;   
        try {   
            File outFile = new File(outputPath);   
            if (!outFile.getParentFile().exists()) {   
                outFile.getParentFile().mkdirs();   
            }   
            out = new FileOutputStream(outputPath);   
            //保存为PNG文件   
            ChartUtilities.writeChartAsPNG(out, chart, 700, 350);   
            //保存为JPEG文件   
            //ChartUtilities.writeChartAsJPEG(out, chart, 600, 350);   
            out.flush();   
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            if (out != null) {   
                try {   
                    out.close();   
                } catch (IOException e) {   
                	e.printStackTrace();     
                }   
            }   
        }
    }


}
