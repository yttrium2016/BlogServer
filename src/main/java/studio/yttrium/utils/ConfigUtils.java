package studio.yttrium.utils;

import studio.yttrium.constant.ConstantValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置保存
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/11
 * Time: 10:18
 */
public class ConfigUtils {


    private static Properties prop = null;
    private static File file = null;

    private static File getFile() {
        if (file == null) {
            file = new File(ConstantValue.getConfigPath());
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("文件创建失败...");
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private static Properties getProp() {
        if (prop == null) {
            prop = new Properties();
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(getFile());
            prop.load(in);
        } catch (IOException e) {
            System.out.println("文件读取出错:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        String is = getProp().getProperty(key);
        if (StringUtils.isBlank(is)){
            return defValue;
        }
        return "true".equals(is);
    }

    public static void putBoolean(String key, boolean value) {
        getProp().setProperty(key, String.valueOf(value));
        FileOutputStream in = null;
        try {
            in = new FileOutputStream(file);
            getProp().store(in, "Config");
        } catch (IOException e) {
            System.out.println("保存Boolean出错:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getInt(String key, int defValue) {
        String intStr = getProp().getProperty(key);
        try {
            return Integer.parseInt(intStr);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static void putInt(String key, int value) {
        getProp().setProperty(key, String.valueOf(value));
        FileOutputStream in = null;
        try {
            in = new FileOutputStream(file);
            getProp().store(in, "Config");
        } catch (IOException e) {
            System.out.println("保存Int出错:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getString(String key, String defValue) {
        String str = getProp().getProperty(key);
        if (StringUtils.isNotBlank(str)) {
            return str;
        }
        return defValue;
    }

    public static void putString(String key, String value) {
        getProp().setProperty(key, value);
        FileOutputStream in = null;
        try {
            in = new FileOutputStream(file);
            getProp().store(in, "Config");
        } catch (IOException e) {
            System.out.println("保存String出错:" + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
