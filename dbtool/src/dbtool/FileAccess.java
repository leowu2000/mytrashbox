/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtool;

import java.io.File;

/**
 *
 * @author User
 */
public class FileAccess {

        public static boolean deleteFile(String fileName){
       File file = new File(fileName);
        if(file.isFile() && file.exists()){
            file.delete();
            return true;
       }else{
            return false;
        }
   }
public static boolean deleteDirectory(String dir){
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
       if(!dir.endsWith(File.separator)){
           dir = dir+File.separator;
      }
       File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
       if(!dirFile.exists() || !dirFile.isDirectory()){
           return true;
       }
       boolean flag = true;
       //删除文件夹下的所有文件(包括子目录)
       File[] files = dirFile.listFiles();
        for(int i=0;i<files.length;i++){
          //删除子文件
           if(files[i].isFile()){
                flag = deleteFile(files[i].getAbsolutePath());
              if(!flag){
                   break;
                }
          }
            //删除子目录
           else{
               flag = deleteDirectory(files[i].getAbsolutePath());
              if(!flag){
                    break;
               }
            }
        }

       if(!flag){
           System.out.println("删除目录失败");
            return false;
        }

       //删除当前目录
       if(dirFile.delete()){
            return true;
        }else{
           return false;
        }
    }

}
