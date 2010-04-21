/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author User
 */
public class ToOrder {

    public List combination(List beforList) {
		List afterList = new ArrayList();
		String str1 = "";
		String str2 = "";
		boolean begin = false;
		for (Iterator iterator = beforList.iterator(); iterator.hasNext();) {
			TemClass obj = (TemClass) iterator.next();
			if(!obj.getTotal().trim().equals("0")) {
				if(!begin) {
					str1 = obj.getYear();
					str2 = obj.getYear();
					begin = true;
				}else {
					str2 = obj.getYear();
				}
				if(!iterator.hasNext() && begin) {
					afterList.add(str1+"-"+str2);
				}
			}else {
				if(begin) {
					afterList.add(str1+"-"+str2);
				}
				str1="";
				str2="";
				begin = false;
			}
		}
		return afterList;
	}

	public TemClass getTem(String stcd,String year,String total) {
		return new TemClass(stcd,year,total);
	}

	public class TemClass{
		private String stcd ;

		private String year;

                private String total;

		public TemClass(String stcd,String year,String total) {
			this.stcd = stcd;
			this.year = year;
                        this.total = total;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getStcd() {
			return stcd;
		}

		public void setStcd(String stcd) {
			this.stcd = stcd;
		}

                public String getTotal(){
                        return total;
                }
                public void setTotal(String total){
                        this.total = total;
                }

	}

	public static void main(String arg[]) {
		ToOrder action = new ToOrder();
		List befor = new ArrayList();
                befor.add(action.getTem("10400151","1985","1"));
		befor.add(action.getTem("10400150","1985","1"));
		befor.add(action.getTem("10400150","1986","2"));
		befor.add(action.getTem("10400150","1987","3"));
		befor.add(action.getTem("10400150","2004","4"));
                befor.add(action.getTem("10400150","2005","4"));
		befor.add(action.getTem("10400171","1985","5"));
		befor.add(action.getTem("10400171","1987","6"));
		befor.add(action.getTem("10400171","1988","7"));
		befor.add(action.getTem("10400171","2008","8"));
		befor.add(action.getTem("10400171","2009","9"));
		befor.add(action.getTem("10400171","2010","10"));
                befor.add(action.getTem("10400171","2011","10"));
                befor.add(action.getTem("10400172","2012","10"));
		List after = action.toOrder(befor);
                for(int i=0;i<after.size();i++){
                    System.out.println(after.get(i));
                }
//                   Date   date   =   Calendar.getInstance().getTime();
//                  SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//                  String   sDate   =   sdf.format(date);
//                System.out.println(sDate);

	}
        public static List toOrder(List beforeOrder){
            List afterOrder=new ArrayList();

            String sDate = "";
            String eDate = "";
            String stcd = "";
            int total=0;
            boolean isBegin = true;
            if(beforeOrder!=null && beforeOrder.size()>0){
                for(int i=0;i<beforeOrder.size();i++){
                    TemClass obj = (TemClass) beforeOrder.get(i);
                    stcd = obj.getStcd();
                    if(i+1!=beforeOrder.size()){
                        TemClass objnext = (TemClass) beforeOrder.get(i+1);
                        if(stcd.trim().equals(objnext.getStcd())){
                            if(isBegin){
                               total = new Integer(obj.getTotal()).intValue();
                               sDate =  obj.getYear();
                               eDate =  obj.getYear();
                            }else{
                                total += new Integer(obj.getTotal()).intValue();
                                eDate = obj.getYear();
                            }
//                            if(i+1!=beforeOrder.size()){
                            int s_year = new Integer(obj.getYear()).intValue();
                            int e_year = new Integer(objnext.getYear()).intValue();
                            if(s_year+1==e_year){
                                isBegin = false;
                                continue;
                            }else{
                                afterOrder.add(stcd+","+sDate+"-"+eDate+","+total);
                                isBegin = true;
                                continue;
                            }
//                            }else{
//                                afterOrder.add(stcd+","+sDate+"-"+eDate+","+total);
//                                isBegin = false;
//                            }
                        }else{
                            if(isBegin){
                                total = new Integer(obj.getTotal()).intValue();
                                sDate = obj.getYear();
                            }else{
                                total += new Integer(obj.getTotal()).intValue();
                            }
                            eDate =  obj.getYear();
                            afterOrder.add(stcd+","+sDate+"-"+eDate+","+total);
                            isBegin = true;
                        }
                    }else{
                        TemClass objPer = (TemClass) beforeOrder.get(i-1);
                        if(stcd.trim().equals(objPer.getStcd())){
                            int e_year = new Integer(obj.getYear()).intValue();
                            int s_year = new Integer(objPer.getYear()).intValue();
                            if(s_year+1==e_year){
                                total += new Integer(obj.getTotal()).intValue();
                                afterOrder.add(stcd+","+sDate+"-"+e_year+","+total);
                            }else{
                                sDate =  obj.getYear();
                                eDate =  obj.getYear();
                                total = new Integer(obj.getTotal()).intValue();
                                afterOrder.add(stcd+","+sDate+"-"+eDate+","+total);
                            }
                        }else{
                            sDate =  obj.getYear();
                            eDate =  obj.getYear();
                            total = new Integer(obj.getTotal()).intValue();
                            afterOrder.add(stcd+","+sDate+"-"+eDate+","+total);
                        }
                        
                    }
                    
                }
            }
            return afterOrder;
        }

}
