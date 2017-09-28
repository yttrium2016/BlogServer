package studio.yttrium.utils;

import studio.yttrium.constant.ConstantValue;
import studio.yttrium.pojo.Blog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/9/18
 * Time: 11:21
 */
public class BlogUtils {

    private static List<Blog> blogs = null;

    static {
        if (blogs == null){
            blogs = getBlogsByFileNames(FileUtils.getFileNames(new File(ConstantValue.getGitPath())));
        }
    }

    /**
     * 获取Blog列表
     * @return
     */
    public static List<Blog> getBlogs() {
        if (blogs == null){
            initBlogs();
        }
        initBlogs();
        return blogs;
    }

    /**
     * 重新初始化加载
     */
    public static void initBlogs(){
        blogs = getBlogsByFileNames(FileUtils.getFileNames(new File(ConstantValue.getGitPath())));
    }

    /**
     * 通过文件名字获取Blogs
     * @param fileNames
     * @return
     */
    public static List<Blog> getBlogsByFileNames(List<String> fileNames) {

        String url = "/blog.shtml?id=";

        List<Blog> blogs = new ArrayList<>();
        if (fileNames != null && fileNames.size() > 0) {
            for (String str : fileNames) {
                if (StringUtils.isNotBlank(str)) {
                    Blog blog = new Blog();
                    String[] split = str.split("--");
                    switch (split.length) {
                        case 1:
                            blog.setTitle(split[0]);
                            blog.setUrl(url);
                            blog.setFileName(str);
                            blog.setTime(StringUtils.getNewDate("yyyy-MM-dd"));
                            blog.setAuthor(ConfigUtils.getString(ConstantValue.BLOG_AUTHOR, "未知"));
                            blog.setContent("没有简介");
                            break;
                        case 2:
                            blog.setTime(split[0]);
                            blog.setTitle(split[1]);
                            blog.setUrl(url);
                            blog.setFileName(str);
                            blog.setAuthor(ConfigUtils.getString(ConstantValue.BLOG_AUTHOR, "未知"));
                            blog.setContent("没有简介");
                            break;
                        case 3:
                            blog.setTime(split[0]);
                            blog.setTitle(split[1]);
                            blog.setUrl(url);
                            blog.setFileName(str);
                            blog.setAuthor(split[2]);
                            blog.setContent("没有简介");
                            break;
                        case 4:
                            blog.setTime(split[0]);
                            blog.setTitle(split[1]);
                            blog.setUrl(url);
                            blog.setFileName(str);
                            blog.setAuthor(split[2]);
                            blog.setContent(split[3]);
                            break;
                    }
                    blog.setWebUrl("");
                    blogs.add(blog);
                }
            }
            return blogs;
        }
        return null;
    }
}
