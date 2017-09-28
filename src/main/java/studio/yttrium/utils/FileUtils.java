package studio.yttrium.utils;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/13
 * Time: 13:38
 */
public class FileUtils {

    @Test
    public void test() {
        String a = "ab.cde.jsp";
        System.out.println(a.substring(0,a.lastIndexOf(".")));
    }


    /**
     * 删除文件或者文件夹以下所有文件
     *
     * @param file
     */
    public static void deleteAllFilesOfDir(File file) {
        if (!file.exists())
            return;
        if (file.isFile()) {
            boolean delete = file.delete();
            if (!delete) {
                System.gc();    //回收资源
                boolean f = file.delete();
                if (!f)
                    System.out.println(file.getName() + "删除失败");
            }
            return;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        file.delete();
    }

    /**
     * 获取所有文件的名字
     *
     * @param file
     * @return
     */
    public static List<String> getFileNames(File file) {
        List<String> result = new ArrayList<>();
        if (!file.exists())
            return null;
        if (file.isFile()) {
            result.add(file.getName().substring(0,file.getName().lastIndexOf(".")));
            return result;
        }
        File[] files = file.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile())
                    result.add(files[i].getName().substring(0,files[i].getName().lastIndexOf(".")));
            }
            return result;
        }
        return null;
    }


    /**
     * 删除单个文件
     *
     * @param file
     * @return
     */
    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile())
            return file.delete();
        else
            return false;
    }

    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String File2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
//            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                //"<br>"+
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

}
