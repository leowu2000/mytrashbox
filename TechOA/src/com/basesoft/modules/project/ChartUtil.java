package com.basesoft.modules.project;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import com.basesoft.util.StringUtil;

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
	public static String createChart(String method, List<?> list, String path, ProjectDAO pjDAO) throws Exception{
		path = path + "\\" + method + ".png";
		String title = "";
		if("gstjhz".equals(method)){//工时统计汇总
			title = "工时统计汇总";
			CategoryDataset dataset = createDatasetGstjhz(pjDAO, list);   
	        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置   
	        JFreeChart freeChart = createChart(dataset, title);   
	        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等   
	        saveAsFile(freeChart, path, 800, 330);   
		}else if("kygstj".equals(method)){
			title = "科研工时统计";
			CategoryDataset dataset = createDatasetKygstj(pjDAO, list);   
	        JFreeChart freeChart = createChart(dataset, title);   
	        saveAsFile(freeChart, path, 800, 330);   
		}else if("cdrwqk".equals(method)){
			title = "承担任务情况";
			CategoryDataset dataset = createDatasetCdrwqk(pjDAO, list);   
	        JFreeChart freeChart = createChart(dataset, title);   
	        saveAsFile(freeChart, path, 800, 330);   
		}
		
		return path;
	}
	
	 /** 
     * 工时统计汇总
	 * @param pjDAO
	 * @param datepick
     */ 
    public static CategoryDataset createDatasetGstjhz(ProjectDAO pjDAO, List listData) {   
    	List listPj = pjDAO.getProject();
    	List listDepart = pjDAO.getDepartment();
    	
    	//每根柱子
        String[] rowKeys = new String[listDepart.size() + 1];
        rowKeys[0] = "本月合计";
        for(int i=1;i<=listDepart.size();i++){
        	Map mapDepart = (Map)listDepart.get(i-1);
        	rowKeys[i] = mapDepart.get("NAME").toString();
        }
        //横坐标
        String[] colKeys = new String[listPj.size()];
        for(int i=0;i<listPj.size();i++){
        	Map mapPj = (Map)listPj.get(i);
        	colKeys[i] = mapPj.get("NAME").toString();
        }
        
        double [][] data = new double[listDepart.size() + 1][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	data[0][i] = Double.parseDouble(mapData.get("totalCount").toString());
        	
        	for(int j=1;j<=listDepart.size();j++){
        		data[j][i] = Double.parseDouble(mapData.get("departCount" + (j -1)).toString());
        	}
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    }  
    
    /** 
     * 科研工时统计 
	 * @param pjDAO
	 * @param datepick
     */ 
    public static CategoryDataset createDatasetKygstj(ProjectDAO pjDAO, List listData) {   
    	List listPj = pjDAO.getProject();
    	List listPeriod = pjDAO.getDICTByType("5");
    	
    	//每根柱子
        String[] rowKeys = new String[listPeriod.size() + 1];
        rowKeys[0] = "合计";
        for(int i=1;i<=listPeriod.size();i++){
        	Map mapPeriod = (Map)listPeriod.get(i-1);
        	rowKeys[i] = mapPeriod.get("NAME").toString();
        }
        //横坐标
        String[] colKeys = new String[listPj.size() + 1];
        colKeys[listPj.size()] = "合计";
        for(int i=0;i<listPj.size();i++){
        	Map mapPj = (Map)listPj.get(i);
        	colKeys[i] = mapPj.get("NAME").toString();
        }
        
        double [][] data = new double[listPeriod.size() + 1][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	data[0][i] = Double.parseDouble(mapData.get("TOTALCOUNT").toString());
        	
        	for(int j=1;j<=listPeriod.size();j++){
        		Map mapPeriod = (Map)listPeriod.get(j-1);
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
    public static CategoryDataset createDatasetCdrwqk(ProjectDAO pjDAO, List listData) {   
    	List listPj = pjDAO.getProject();
    	List listDepart = pjDAO.getDepartment();
    	
    	//每根柱子
        String[] rowKeys = {"合计", "本科及以上学历或中高级职称人员", "大、中专学历或初级职称人员", "电讯", "计算机硬件", "结构", "工艺", "软件开发", "其他"};
        
        //横坐标
        String[] colKeys = new String[listPj.size()];
        for(int i=0;i<listPj.size();i++){
        	Map mapPj = (Map)listPj.get(i);
        	colKeys[i] = mapPj.get("NAME").toString();
        }
        
        double [][] data = new double[9][listData.size()]; 
        
        for(int i=0;i<listData.size();i++){
        	Map mapData = (Map)listData.get(i);
        	data[0][i] = Double.parseDouble(mapData.get("TOTALCOUNT").toString());
        	
        	for(int j=1;j<9;j++){
        		data[j][i] = Double.parseDouble(mapData.get("C" + j).toString());
        	}
        }
           
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);           
    } 
	
	/**
	 * 根据CategoryDataset生成JFreeChart对象  
	 * @param dataset
	 * @return
	 */ 
    public static JFreeChart createChart(CategoryDataset dataset, String title) throws Exception{   
        JFreeChart jfreechart = ChartFactory.createBarChart3D(title,    //标题   
                "",    //categoryAxisLabel （category轴，横轴，X轴的标签）   
                "",    //valueAxisLabel（value轴，纵轴，Y轴的标签）   
                dataset, // dataset   
                PlotOrientation.VERTICAL,   
                true, // legend   
                false, // tooltips   
                false); // URLs   
           
        //以下的设置可以由用户定制，也可以省略   
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();   
        //背景色　透明度   
        plot.setBackgroundAlpha(0.5f);   
        //前景色　透明度   
        plot.setForegroundAlpha(0.5f);   
        //水平标题
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,18));
        domainAxis.setTickLabelFont(new Font("黑体",Font.BOLD,12));
        //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,18));
        rangeAxis.setTickLabelFont(new Font("黑体",Font.BOLD,12));
        //标题
        TextTitle textTitle = jfreechart.getTitle();
        textTitle.setFont(new Font("宋体", Font.BOLD,20));
        //图例
        jfreechart.getLegend().setItemFont(new Font("宋体",Font.BOLD,12));  
        
        //柱子显示
        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
        //显示条目标签
        renderer.setBaseItemLabelsVisible(true);
        //设置条目标签生成器
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        //设置条目标签显示的位置,outline表示在条目区域外,baseline_center表示基于基线且居中
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

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
            ChartUtilities.writeChartAsPNG(out, chart, 800, 330);   
            //保存为JPEG文件   
            //ChartUtilities.writeChartAsJPEG(out, chart, 800, 330);   
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
                    //do nothing   
                }   
            }   
        }
    }


}
