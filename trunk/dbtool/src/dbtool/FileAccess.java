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
        //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
       if(!dir.endsWith(File.separator)){
           dir = dir+File.separator;
      }
       File dirFile = new File(dir);
        //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
       if(!dirFile.exists() || !dirFile.isDirectory()){
           return true;
       }
       boolean flag = true;
       //ɾ���ļ����µ������ļ�(������Ŀ¼)
       File[] files = dirFile.listFiles();
        for(int i=0;i<files.length;i++){
          //ɾ�����ļ�
           if(files[i].isFile()){
                flag = deleteFile(files[i].getAbsolutePath());
              if(!flag){
                   break;
                }
          }
            //ɾ����Ŀ¼
           else{
               flag = deleteDirectory(files[i].getAbsolutePath());
              if(!flag){
                    break;
               }
            }
        }

       if(!flag){
           System.out.println("ɾ��Ŀ¼ʧ��");
            return false;
        }

       //ɾ����ǰĿ¼
       if(dirFile.delete()){
           System.out.println("ɾ��Ŀ¼�ɹ�");
            return true;
        }else{
           return false;
        }
    }

}
