package studio.yttrium.constant;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/8/21
 * Time: 13:14
 */
public class ConstantValue {

    /**
     * classes的绝对路径
     */
    private static String webInfPath = null;

    public static String GIT_ADDRESS = "git_address";

    public static String IMG_GIT_ADDRESS = "img_git_address";
    
    public static String IMG_PATH = "img_path";

    public static String BLOG_NAME = "blog_name";

    public static String BLOG_CONTENT = "blog_content";

    public static String BLOG_AUTHOR = "blog_author";

    public static String GIT_USERNAME = "git_username";

    public static String GIT_PASSWORD = "git_password";

    public static String BLOG_FIRST_OPEN = "blog_first_open";


    /**
     * 获取Classes目录
     *
     * @return
     */
    private static String getWebInfPath() {
        if (webInfPath == null) {
            webInfPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
            webInfPath = webInfPath.replace('/', '\\'); // 将/换成\
            webInfPath = webInfPath.replace("file:", ""); //去掉file:
            webInfPath = webInfPath.replace("classes\\", ""); //去掉class\(我这里不需要)
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
     * 获取classes下配置文件的绝对路径(viewConfig.xml)
     *
     * @return
     */
    public static String getGitPath() {
        return getWebInfPath() + "git";
    }



    public String getRootPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = path.replace('/', '\\'); // 将/换成\
        path = path.replace("file:", ""); //去掉file:
        path = path.replace("classes\\", ""); //去掉class\(我这里不需要)
        path = path.replace("WEB-INF\\", ""); //去掉WEB-INF\(我这里不需要)
        path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
        return path;
    }
}
