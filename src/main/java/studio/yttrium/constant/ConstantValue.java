package studio.yttrium.constant;

import studio.yttrium.utils.ConfigUtils;

import java.io.File;

/**
 * 常用变量
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/21
 * Time: 13:14
 */
public class ConstantValue {

    /**
     * webInfPath的绝对路径
     */
    private static String webInfPath = null;

    /**
     * blogGit地址
     */
    public static String BLOG_GIT_ADDRESS = "blog_git_address";

    /**
     * imgGit地址
     */
    public static String IMG_GIT_ADDRESS = "img_git_address";

    /**
     * Git来源类型
     */
    public static String GIT_TYPE = "git_type";

    //git的用户名密码
    public static String GIT_USERNAME = "git_username";

    public static String GIT_PASSWORD = "git_password";

    //网站相关配置
    public static String BLOG_NAME = "blog_name"; //博客名字

    public static String BLOG_CONTENT = "blog_content"; //博客简介

    public static String BLOG_AUTHOR = "blog_author"; //博客作者

    public static String BLOG_URL = "blog_url";//博客网址

    public static String BLOG_IMG_URL = "blog_img_url";//博客网址

    //是否第一次启动
    public static String BLOG_FIRST_OPEN = "blog_first_open";


    /**
     * 获取Classes目录
     *
     * @return
     */
    private static String getWebInfPath() {
        if (webInfPath == null) {
            webInfPath = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            webInfPath = webInfPath.replace('/', '\\'); // 将/换成\
            webInfPath = webInfPath.replace("file:", ""); //去掉file:
//            webInfPath = webInfPath.replace("classes\\", ""); //去掉class\(我这里不需要)
            webInfPath = webInfPath.substring(1); //去掉第一个\,如 \D:\JavaWeb...
        }
        return webInfPath;
    }

    /**
     * 获取classes下配置文件的绝对路径(config.properties)
     *
     * @return
     */
    public static String getConfigPath() {
        return getWebInfPath() + "config.properties";
    }

    /**
     * 获取classes下配置文件的绝对路径(viewConfig.xml)
     *
     * @return
     */
    public static String getXmlPath() {
        return getWebInfPath() + "viewConfig.xml";
    }

    /**
     * 获取Web-inf下git目录
     *
     * @return
     */
    public static String getBlogPath() {
        return getWebInfPath() + "blog";
    }

    /**
     * 获取Web-inf下img目录
     *
     * @return
     */
    public static String getImgPath() {
        return getWebInfPath() + "img";
    }

    /**
     * 获取网络源码地址
     *
     * @param gitUrl
     * @return
     */
    public static String getGitWeb(String gitUrl) {

        String res = "";
        String gitType = ConfigUtils.getString(ConstantValue.GIT_TYPE, "");
        String[] split = null;
        switch (gitType) {
            case "github":
                //https://github.com/yttrium2016/Blog.git
                //https://raw.githubusercontent.com/yttrium2016/Blog/master/xx.md
                split = gitUrl.split("/");
                split[split.length - 1] = split[split.length - 1].contains(".") ? split[split.length - 1].substring(0, split[split.length - 1].lastIndexOf(".")) : split[split.length - 1];
                res = "https://raw.githubusercontent.com/" + split[split.length - 2] + "/" + split[split.length - 1] + "/master/";
                break;
            case "gitee":
                //https://gitee.com/iyzy/Blog.git
                //https://gitee.com/iyzy/Blog/raw/master/xx.md
                split = gitUrl.split("/");
                split[split.length - 1] = split[split.length - 1].contains(".") ? split[split.length - 1].substring(0, split[split.length - 1].lastIndexOf(".")) : split[split.length - 1];
                res = "https://gitee.com/" + split[split.length - 2] + "/" + split[split.length - 1] + "/raw/master/";
                break;
        }


        return res;
    }

    /**
     * 获取Root目录
     *
     * @return
     */
    public static String getRootPath() {
        File file = new File("");
        String path = file.getAbsolutePath();
        path = path.contains("bin") ? path.replace("bin", "webapps\\ROOT\\conf\\") : path+"\\conf\\";
        System.out.println(path);
        return path;
    }

}
